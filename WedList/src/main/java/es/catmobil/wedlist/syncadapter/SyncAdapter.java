package es.catmobil.wedlist.syncadapter;

import android.accounts.Account;
import android.accounts.AccountManager;
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

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

import es.catmobil.wedlist.application.AppConfig;
import es.catmobil.wedlist.application.MyConstants;
import es.catmobil.wedlist.database.contract.DataContract;
import es.catmobil.wedlist.database.cursor.GiftCursor;
import es.catmobil.wedlist.database.cursor.PersonCursor;
import es.catmobil.wedlist.database.cursor.ProjectCursor;
import es.catmobil.wedlist.model.Gift;
import es.catmobil.wedlist.model.Person;
import es.catmobil.wedlist.model.Project;

public class SyncAdapter extends AbstractThreadedSyncAdapter {

    public SyncAdapter(Context context) {
        super(context, true);
    }

    @Override
    public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult) {
        try {
            downloadProjects(account);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void downloadProjects(Account account) throws Exception {
        Log.d("PARSE", "load projects for user:"+account.name);
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Invitations");
        query.whereEqualTo(MyConstants.PARSE_USER, account.name);

        parseInvitations(query.find());
    }

    private void parseInvitations(List<ParseObject> invitations) throws Exception {
        ContentResolver contentResolver = getContext().getContentResolver();
        contentResolver.delete(DataContract.ProjectTable.CONTENT_URI, null, null);
        contentResolver.delete(DataContract.PersonTable.CONTENT_URI, null, null);
        contentResolver.delete(DataContract.PersonsInGiftTable.CONTENT_URI, null, null);


        setUpUser(AccountManager.get(getContext()).getAccountsByType(AppConfig.ACCOUNT_TYPE)[0]);

        for (ParseObject po : invitations) {
            String id = po.getString("project");
            Log.d("PARSE", "Invitation projectid:" + id);
            getProjectsFromIdentifier(id);
        }
    }

    private void setUpUser(Account acc) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Persons");
        query.whereEqualTo("email", acc.name);
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject parseObject, ParseException e) {
                Person person = new Person();

                if (parseObject != null) {
                    try {
                        person.setServerId(parseObject.getObjectId());
                        person.setName(parseObject.getString("name"));
                        person.setImage(parseObject.getString("image"));
                        person.setEmail(parseObject.getString("email"));
                        ContentValues values = new PersonCursor().setValues(person);

                        getContext().getContentResolver().insert(DataContract.PersonTable.CONTENT_URI, values);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
    }

    private void getProjectsFromIdentifier(String invitationId) throws Exception {
        ParseQuery<ParseObject> queryProject = ParseQuery.getQuery("Project");
        queryProject.whereEqualTo("objectId", invitationId);
        parseProject(queryProject.find());
    }

    private void parseProject(List<ParseObject> objects) throws Exception {
        ContentResolver contentResolver = getContext().getContentResolver();
        if (objects == null) {
            Log.d("PARSE", "The getFirst request failed.");
        } else {
            for (ParseObject object : objects) {
                Project project = new Project();
                project.setName(object.getString("name"));
                project.setImage(object.getString("image"));
                project.setDescription(object.getString("description"));
                project.setEmail(object.getString("email"));
                project.setDate(object.getString("date"));
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
        }
    }

    private void parseGiftLists(List<ParseObject> parseGifts, String projectServerId, long projectInternId) throws Exception {
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

            ParseQuery<ParseObject> query = ParseQuery.getQuery("Payment");
            query.whereEqualTo("gift_Id", serverId);
            List<ParseObject> parsePayments = query.find();

            personPayments(parsePayments, serverId);
        }
    }

    private void personPayments(List<ParseObject> parsePayments, String giftServerId) throws Exception {

        PersonCursor personCursor = new PersonCursor();
        ContentResolver cr = getContext().getContentResolver();
        for (ParseObject po : parsePayments) {

            ParseQuery<ParseObject> query = ParseQuery.getQuery("Persons");
            query.whereEqualTo("objectId", po.getString("person_Id"));
            List<ParseObject> parsePersons = query.find();

            for (ParseObject poPer : parsePersons) {
                Person person = new Person();

                person.setServerId(poPer.getObjectId());
                person.setName(poPer.getString("name"));
                person.setImage(poPer.getString("image"));
                person.setEmail(poPer.getString("email"));
                ContentValues values = personCursor.setValues(person);

                cr.insert(DataContract.PersonTable.CONTENT_URI, values);

                ContentValues paymentValues = new ContentValues();
                paymentValues.put(DataContract.PersonsInGiftTable.ComplexGiftColumns.GIFT, giftServerId);
                paymentValues.put(DataContract.PersonsInGiftTable.ComplexGiftColumns.PAYER, poPer.getObjectId());
                paymentValues.put(DataContract.PersonsInGiftTable.ComplexGiftColumns.AMOUNT, po.getDouble("quantity"));
                paymentValues.put(DataContract.PersonsInGiftTable.ComplexGiftColumns.DATE, po.getString("date"));

                cr.insert(DataContract.PersonsInGiftTable.CONTENT_URI, paymentValues);
            }
        }

        Cursor c = cr.query(DataContract.PersonTable.CONTENT_URI, null, null, null, null);

        if (c != null && c.moveToFirst()) {
            Log.i("PAY-TAG", "Cursor: " + c.getCount());
        }
    }
}