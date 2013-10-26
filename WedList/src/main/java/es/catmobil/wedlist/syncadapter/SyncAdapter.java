package es.catmobil.wedlist.syncadapter;

import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.Context;
import android.content.SyncResult;
import android.os.Bundle;
import android.util.Log;

import com.parse.FindCallback;
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

      {//ALL Projects from a user

            ParseQuery<ParseObject> query = ParseQuery.getQuery(DataContract.ProjectTable.TABLE);

            // Restrict to cases where the author is the current user.
            query.whereEqualTo(MyConstants.PARSE_USER, "user1");

            // Run the query


            query.findInBackground(new FindCallback<ParseObject>() {

                @Override
                public void done(List<ParseObject> projectList,
                                 ParseException e) {
                    if (e == null) {
                        // If there are results, update the list of posts
                        // and notify the adapter
                        // result.clear();
                        List<Project> projects=new ArrayList<Project>(projectList.size());
                        for (ParseObject po : projectList) {
                            //result.add(po.getString(DataContract.ProjectColumns.NAME));
                            Project project=new Project();
                            project.setName(po.getString(DataContract.ProjectTable.ProjectColumns.NAME));

                            projects.add(project);
                            Log.v("PARSE","Project:"+ project.toString());
                        }



                        // ((ArrayAdapter<String>)getListAdapter()).notifyDataSetChanged();
                    } else {
                        Log.d("Post retrieval", "Error: " + e.getMessage());
                    }
                }

            });

        }
    }
}
