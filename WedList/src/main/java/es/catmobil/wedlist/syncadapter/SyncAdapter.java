package es.catmobil.wedlist.syncadapter;

import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.Context;
import android.content.SyncResult;
import android.os.Bundle;
import android.util.Log;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import es.catmobil.wedlist.application.MyConstants;
import es.catmobil.wedlist.database.contract.DataContract;
import es.catmobil.wedlist.model.Project;

public class SyncAdapter extends AbstractThreadedSyncAdapter{

    public SyncAdapter(Context context) {
        super(context, true);
    }

    @Override
    public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult) {
        Log.v("PARSE","Performing sync");
      {//ALL Projects from a user

            ParseQuery<ParseObject> query = ParseQuery.getQuery("Invitation");

            // Restrict to cases where the author is the current user.
            query.whereEqualTo(MyConstants.PARSE_USER, "user1@gmail.com");

            // Run the query


            query.findInBackground(new FindCallback<ParseObject>() {//List of invitations

                @Override
                public void done(List<ParseObject> invitations,
                                 ParseException e) {
                    Log.v("PARSE","Performing sync done ");
                    if (e == null) {
                        // If there are results, update the list of posts
                        // and notify the adapter
                        // result.clear();
                       final List<Project> projects=new ArrayList<Project>(invitations.size());
                        for (ParseObject po : invitations) {
                            //result.add(po.getString(DataContract.ProjectColumns.NAME));
                            String id=po.getString("project");
                            Log.d("PARSE", "Invitation projectid:"+id);
                            ParseQuery<ParseObject> queryProject = ParseQuery.getQuery("Project");
                            queryProject.whereEqualTo("objectId", id);
                            queryProject.getFirstInBackground(new GetCallback<ParseObject>() {
                                public void done(ParseObject object, ParseException e) {
                                    if (object == null) {
                                        Log.d("PARSE", "The getFirst request failed.");
                                    } else {
                                        Project project=new Project();
                                        project.setName(object.getString("name"));

                                        projects.add(project);
                                        Log.v("PARSE", "project :" + project.getName());
                                    }
                                }
                            });

                        }



                        // ((ArrayAdapter<String>)getListAdapter()).notifyDataSetChanged();
                    } else {
                        Log.d("PARSE", "Performing sync Error: " + e.getMessage());
                    }
                }

            });

        }
    }
}
