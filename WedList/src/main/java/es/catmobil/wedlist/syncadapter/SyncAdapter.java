package es.catmobil.wedlist.syncadapter;

import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.SyncResult;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import es.catmobil.wedlist.application.MyConstants;
import es.catmobil.wedlist.database.contract.DataContract;
import es.catmobil.wedlist.database.cursor.GiftCursor;
import es.catmobil.wedlist.database.cursor.ProjectCursor;
import es.catmobil.wedlist.model.Gift;
import es.catmobil.wedlist.model.Project;

public class SyncAdapter extends AbstractThreadedSyncAdapter {

    public SyncAdapter(Context context) {
        super(context, true);
    }

    @Override
    public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult) {
        downloadProjects(account);
        // user1@gmail.com
    }

    private void downloadProjects(Account account) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Invitations");
        query.whereEqualTo(MyConstants.PARSE_USER, account.name);
        query.findInBackground(new ProjectsCallback());
    }

    private class ProjectsCallback extends FindCallback<ParseObject> {

        @Override
        public void done(List<ParseObject> invitations,
                         ParseException e) {
            Log.v("PARSE", "Performing sync done ");
            if (e == null) {
                for (ParseObject po : invitations) {
                    String id = po.getString("project");
                    Log.d("PARSE", "Invitation projectid:" + id);
                    try {
                        getProjectsFromIdentifier(id);
                    } catch (ParseException e1) {
                        e1.printStackTrace();
                    }
                }
            } else {
                Log.d("PARSE", "Performing sync Error: " + e.getMessage());
            }
        }

        private void getProjectsFromIdentifier(String invitationId) throws ParseException {
            ParseQuery<ParseObject> queryProject = ParseQuery.getQuery("Project");
            queryProject.whereEqualTo("objectId", invitationId);
            parseProject(queryProject.getFirst());
        }

        private void parseProject(ParseObject object) {
            try {
                if (object == null) {
                    Log.d("PARSE", "The getFirst request failed.");
                } else {
                    ContentResolver contentResolver = getContext().getContentResolver();
                    contentResolver.delete(DataContract.ProjectTable.CONTENT_URI, null, null);
                    Project project = new Project();
                    project.setName(object.getString("name"));
                    project.setImage(object.getString("image"));
                    project.setDescription(object.getString("description"));
                    //project.setDate(object.getDate("date"));
                    project.setServerId(object.getObjectId());
                    project.setExtras(object.getString("extras"));

                    ContentValues values = new ProjectCursor(getContext()).setValues(project);
                    Uri projUri = contentResolver.insert(DataContract.ProjectTable.CONTENT_URI, values);

                    if (projUri != null) {
                        long id = ContentUris.parseId(projUri);

                        if (id > -1) {
                            Uri uri = Uri.withAppendedPath(DataContract.GiftTable.CONTENT_BY_PROJECT_URI, "" + id);
                            String serverId = object.getObjectId();
                            contentResolver.delete(uri, null, null);
                            if (serverId != null) {
                                ParseQuery<ParseObject> query = ParseQuery.getQuery("Gifts");
                                query.whereEqualTo(MyConstants.PARSE_PROJECT_ID, serverId);
                                List<ParseObject> parseGifts = query.find();

                                parseGiftLists(parseGifts, serverId, id);
                            }
                        }
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        private void parseGiftLists(List<ParseObject> parseGifts, String projectServerId, long projectInternId) {
            ContentResolver cr = getContext().getContentResolver();
            for (ParseObject po : parseGifts) {
                String serverId = po.getObjectId();

                Gift gift = new Gift();

                gift.setServerId(serverId);
                gift.setName(po.getString("name"));
                gift.setDescription(po.getString("description"));
                gift.setPicturePath(po.getString("picture_url"));
                gift.setPrice(Float.parseFloat(po.getString("price")));
                gift.setBought(po.getBoolean("bought"));

                ContentValues values = new GiftCursor(getContext()).setValues(gift);
                values.put(DataContract.GiftTable.GiftColumns.PROJECT, projectServerId);
                values.put(DataContract.GiftTable.GiftColumns.PROJECT_ID, projectInternId);

                cr.insert(DataContract.GiftTable.CONTENT_URI, values);
            }
        }
    }
}