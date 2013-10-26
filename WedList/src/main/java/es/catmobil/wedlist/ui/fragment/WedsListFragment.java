package es.catmobil.wedlist.ui.fragment;

import android.app.Activity;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import es.catmobil.wedlist.R;
import es.catmobil.wedlist.database.contract.DataContract;
import es.catmobil.wedlist.ui.adapter.WedsAdapter;

/**
 * Created by adria on 26/10/13.
 */
public class WedsListFragment extends Fragment {
    private int ID_LOADER = 125;
    private WedsAdapter adapter;
    private ComunicationActivityFragmentProjectList listener;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list_weds,null);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Activity ac = getActivity();
        ListView list = (ListView)ac.findViewById(R.id.list_weds);
        adapter = new WedsAdapter(ac,null);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new ListViewListener());
        Button newWed = (Button)ac.findViewById(R.id.new_wed);
        newWed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //fixme k ago?
            }
        });
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        listener = (ComunicationActivityFragmentProjectList) activity;
        activity.setTitle(R.string.ListOfWeddings);
        this.getLoaderManager().initLoader(ID_LOADER,null, new DatabaseCursorLoaderCallback());
    }

    private class DatabaseCursorLoaderCallback implements LoaderManager.LoaderCallbacks<Cursor> {

        @Override
        public android.support.v4.content.Loader<Cursor> onCreateLoader(
                int arg0, Bundle arg1) {
            return new CursorLoader(WedsListFragment.this.getActivity(), DataContract.ProjectTable.CONTENT_URI,null,null, null, null);
        }

        @Override
        public void onLoadFinished(android.support.v4.content.Loader<Cursor> arg0, Cursor arg1) {
            String[] col = new String[]{BaseColumns._ID
                ,DataContract.ProjectTable.ProjectColumns.NAME
                ,DataContract.ProjectTable.ProjectColumns.DATE
                ,DataContract.ProjectTable.ProjectColumns.IMAGE
                ,DataContract.ComplexGiftTable.ComplexGiftColumns.AMOUNT
            };
            MatrixCursor c = new MatrixCursor(col);
            MatrixCursor.RowBuilder newRow = c.newRow();
            newRow.add(1);
            newRow.add("Llibres");
            newRow.add("26/4/1991");
            newRow.add("http://img.talkandroid.com/uploads/2011/05/Apple-Bite-300x300.png");
            newRow.add(300);


            adapter.swapCursor(c);
        }

        @Override
        public void onLoaderReset(android.support.v4.content.Loader<Cursor> arg0) {
            adapter.swapCursor(null);
        }



    }

    private class ListViewListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, int position,long arg3) {
            Cursor cursor = adapter.getCursor();
            cursor.moveToPosition(position);
            int id = cursor.getInt(cursor.getColumnIndex(BaseColumns._ID));
            listener.clickItemWithId(id);
            //Toast.makeText(WedsListFragment.this.getActivity(),"ID = "+id,Toast.LENGTH_LONG).show();
        }

    }


    public interface ComunicationActivityFragmentProjectList{
        public void clickItemWithId(int id);
    }
}
