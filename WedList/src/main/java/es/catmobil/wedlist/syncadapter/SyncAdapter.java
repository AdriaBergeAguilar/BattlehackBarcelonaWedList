package es.catmobil.wedlist.syncadapter;

import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.SyncResult;
import android.database.Cursor;
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
        downloadGifts();
    }

    private void downloadProjects(Account account) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Invitations");
        query.whereEqualTo(MyConstants.PARSE_USER, "user1@gmail.com");
        query.findInBackground(new ProjectsCallback());
    }

    private class ProjectsCallback extends FindCallback<ParseObject> {

        @Override
        public void done(List<ParseObject> invitations,
                         ParseException e) {
            Log.v("PARSE", "Performing sync done ");
            if (e == null) {
                // If there are results, update the list of posts
                // and notify the adapter
                // result.clear();
                final List<Project> projects = new ArrayList<Project>(invitations.size());
                for (ParseObject po : invitations) {
                    String id = po.getString("project");
                    Log.d("PARSE", "Invitation projectid:" + id);
                    ParseQuery<ParseObject> queryProject = ParseQuery.getQuery("Project");
                    queryProject.whereEqualTo("objectId", id);
                    queryProject.getFirstInBackground(new GetProjectCallback(projects));
                }
            } else {
                Log.d("PARSE", "Performing sync Error: " + e.getMessage());
            }
        }
    }

    private class GetProjectCallback extends GetCallback<ParseObject> {

        private final List<Project> projects;

        public GetProjectCallback(List<Project> projects) {
            this.projects = projects;
        }

        @Override
        public void done(ParseObject object, ParseException e) {
            ContentResolver contentResolver = getContext().getContentResolver();
            contentResolver.delete(DataContract.ProjectTable.CONTENT_URI, null, null);
            if (object == null) {
                Log.d("PARSE", "The getFirst request failed.");
            } else {
                Project project = new Project();
                project.setName(object.getString("name"));
                project.setImage(object.getString("image"));
                project.setDescription(object.getString("description"));
                //project.setDate(object.getDate("date"));
                project.setServerId(object.getObjectId());
                project.setExtras(object.getString("extras"));

                projects.add(project);

                Log.i("WEB-TAG", project.toString());
            }


            ContentValues[] projectsValues = new ProjectCursor(getContext()).setValuesArray(projects);
            contentResolver.bulkInsert(DataContract.ProjectTable.CONTENT_URI, projectsValues);
        }
    }

    private void downloadGifts() {
        Cursor projectsCursor = getContext().getContentResolver().query(DataContract.ProjectTable.CONTENT_URI, null, null, null, null);

        if (projectsCursor != null) {
            ContentResolver contentResolver = getContext().getContentResolver();
            contentResolver.delete(DataContract.GiftTable.CONTENT_URI, null, null);
            while (projectsCursor.moveToNext()) {
                String serverId = projectsCursor.getString(projectsCursor.getColumnIndex(DataContract.ProjectTable.ProjectColumns.SERVER_ID));
                Long id = projectsCursor.getLong(projectsCursor.getColumnIndex(DataContract.ProjectTable.ProjectColumns._ID));

                Log.i("PARSE-TAG", "Project server projectServerId: " + serverId);

                if (serverId != null) {
                    ParseQuery<ParseObject> query = ParseQuery.getQuery("Gifts");
                    query.whereEqualTo(MyConstants.PARSE_PROJECT_ID, serverId);
                    query.findInBackground(new GiftsCallback(serverId, id));
                }
            }
        }
    }

    private class GiftsCallback extends FindCallback<ParseObject> {

        private String projectServerId;
        private Long projectInternId;


        public GiftsCallback(String projectServerId, Long projectInternId) {

            this.projectServerId = projectServerId;
            this.projectInternId = projectInternId;
        }

        @Override
        public void done(List<ParseObject> gifts, ParseException e) {
            if (e == null) {
                // If there are results, update the list of posts
                // and notify the adapter
                // result.clear();
                final List<Gift> giftList = new ArrayList<Gift>(gifts.size());
                for (ParseObject po : gifts) {
                    String serverId = po.getObjectId();

                    Gift gift = new Gift();

                    gift.setServerId(serverId);
                    gift.setName(po.getString("name"));
                    gift.setDescription(po.getString("description"));
                    gift.setPicturePath(po.getString("picture_url"));
                    gift.setPrice(Float.parseFloat(po.getString("price")));
                    gift.setBought(po.getBoolean("bought"));

                    giftList.add(gift);
                }

                ContentResolver cr = getContext().getContentResolver();

                ContentValues[] values = new GiftCursor(getContext()).setValuesArray(giftList);

                for (ContentValues v : values) {
                    v.put(DataContract.GiftTable.GiftColumns.PROJECT, projectServerId);
                    v.put(DataContract.GiftTable.GiftColumns.PROJECT_ID, projectInternId);
                }

                cr.bulkInsert(DataContract.GiftTable.CONTENT_URI, values);

            } else {
                Log.d("PARSE", "Performing sync Error: " + e.getMessage());
            }
        }
    }
}
