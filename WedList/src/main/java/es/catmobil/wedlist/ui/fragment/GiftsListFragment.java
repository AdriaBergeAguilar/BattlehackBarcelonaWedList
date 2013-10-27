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

import es.catmobil.wedlist.R;
import es.catmobil.wedlist.database.contract.DataContract;
import es.catmobil.wedlist.ui.adapter.GiftsAdapter;
import es.catmobil.wedlist.ui.adapter.WedsAdapter;

/**
 * Created by adria on 26/10/13.
 */
public class GiftsListFragment extends Fragment {
    private static final String param = "ID_PROJECT";


    public static GiftsListFragment getInstance(int id_project){
        GiftsListFragment fragment = new GiftsListFragment();
        Bundle arguments = new Bundle();
        arguments.putInt(param,id_project);
        fragment.setArguments(arguments);
        return fragment;
    }
    private int ID_LOADER = 126;
    private ComunicationActivityFragmentGiftsList listener;
    private GiftsAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list_gifts,null);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Activity ac = getActivity();
        ListView list = (ListView)ac.findViewById(R.id.list_gifts);
        adapter = new GiftsAdapter(ac,null);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new ListViewListener());
        Button newWed = (Button)ac.findViewById(R.id.new_gift);
        newWed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //fixme ir  a la pantalla d afegir
            }
        });
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        listener = (ComunicationActivityFragmentGiftsList)activity;
        activity.setTitle(R.string.ListOfGifts);
        this.getLoaderManager().initLoader(ID_LOADER,null, new DatabaseCursorLoaderCallback());
    }

    private class DatabaseCursorLoaderCallback implements LoaderManager.LoaderCallbacks<Cursor> {

        @Override
        public android.support.v4.content.Loader<Cursor> onCreateLoader(
                int arg0, Bundle arg1) {
            //fixme poner la uri
            int id = getArguments().getInt(param,-1);

            Uri uri = Uri.withAppendedPath(DataContract.GiftTable.CONTENT_BY_PROJECT_URI, "" + id);

            //String where = DataContract.GiftTable.GiftColumns.PROJECT+" = "+id;
            return new CursorLoader(GiftsListFragment.this.getActivity(), uri,
                    null, null, null, null);
        }

        @Override
        public void onLoadFinished(
                android.support.v4.content.Loader<Cursor> arg0, Cursor arg1) {
            /**String[] col = new String[]{BaseColumns._ID
                    ,DataContract.GiftTable.GiftColumns.NAME
                    ,DataContract.GiftTable.GiftColumns.PRICE
                    ,DataContract.GiftTable.GiftColumns.PICTURE_URL
                    ,DataContract.GiftTable.GiftColumns.DESCRIPTION
                    ,DataContract.GiftTable.GiftColumns.BOUGHT
                    ,DataContract.GiftTable.GiftColumns.SERVER_ID
                    ,DataContract.GiftTable.GiftColumns.PROJECT
                    ,DataContract.GiftTable.GiftColumns.PROJECT_ID
                    ,DataContract.GiftTable.GiftColumns.COMPLEX
            };

            MatrixCursor c = new MatrixCursor(col);
            MatrixCursor.RowBuilder newRow = c.newRow();
            newRow.add(1);
            newRow.add("berni joder");
            newRow.add(150);
            newRow.add("http://battlehack.org/images/posts/axe.jpg");
            newRow.add("pfdkmerfpikn repodkmre erpiomevr poerm poermv poerm poerwmj peormdv poerdmv ");
            newRow.add("true");
            newRow.add("gfdfgvd");
            newRow.add("fdgdre");
            newRow.add(34);
            newRow.add("true");

***/

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
            listener.clickItemWithId(cursor.getInt(cursor.getColumnIndex(BaseColumns._ID)));
        }

    }
    public interface ComunicationActivityFragmentGiftsList{
        public void clickItemWithId(int id);
    }
}
