package es.catmobil.wedlist.ui.fragment;

import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidquery.AQuery;

import es.catmobil.wedlist.R;
import es.catmobil.wedlist.database.contract.DataContract;

/**
 * Created by adria on 26/10/13.
 */
public class WedsDetailsFragment extends Fragment {
    private static final String param = "project_id";

    public static WedsDetailsFragment newInstance(String id) {
        WedsDetailsFragment baseFragment = new WedsDetailsFragment();
        Bundle args = new Bundle();
        args.putString(param, id);
        baseFragment.setArguments(args);
        return baseFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_weds_details, null);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle args = getArguments();

        TextView text = (TextView)view.findViewById(R.id.wed_detail_descripsion);

        if (args != null && args.containsKey(param)) {
            Uri uri = Uri.parse(DataContract.ProjectTable.CONTENT_URI.getPath()+"/"+args.getString(param));
            Cursor cursor = getActivity().getContentResolver().query(uri,null,null,null,null);
            cursor.moveToFirst();
            String name = cursor.getString(cursor.getColumnIndex(DataContract.ProjectTable.ProjectColumns.DESCRIPTION));
            String img = cursor.getString(cursor.getColumnIndex(DataContract.ProjectTable.ProjectColumns.IMAGE));
            AQuery aq = new AQuery(view);
            aq.id(R.id.wed_detail_img).image(img,true,true);
            text.setText(name);
        }

    }
}
