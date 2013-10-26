package es.catmobil.wedlist.ui.fragment;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import es.catmobil.wedlist.R;
import es.catmobil.wedlist.ui.adapter.WedsAdapter;

/**
 * Created by adria on 26/10/13.
 */
public class WedsListFragment extends Fragment {
    private int ID_LOADER = 125;
    private WedsAdapter adapter;
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
        this.getLoaderManager().initLoader(ID_LOADER,null, new DatabaseCursorLoaderCallback());
    }

    private class DatabaseCursorLoaderCallback implements LoaderManager.LoaderCallbacks<Cursor> {

        @Override
        public android.support.v4.content.Loader<Cursor> onCreateLoader(
                int arg0, Bundle arg1) {
            //fixme poner la uri
            return new CursorLoader(WedsListFragment.this.getActivity(), Uri.parse("aki va la uri"),
                    new String[]{},
                    null, null, null);
        }

        @Override
        public void onLoadFinished(
                android.support.v4.content.Loader<Cursor> arg0, Cursor arg1) {
            adapter.swapCursor(arg1);
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
            //fixme logica
        }

    }
}
