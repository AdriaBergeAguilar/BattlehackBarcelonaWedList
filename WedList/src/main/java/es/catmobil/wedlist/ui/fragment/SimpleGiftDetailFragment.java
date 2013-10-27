package es.catmobil.wedlist.ui.fragment;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.Button;
import android.widget.TextView;

import es.catmobil.wedlist.R;
import es.catmobil.wedlist.database.contract.DataContract;

/**
 * Created by adria on 26/10/13.
 */
public class SimpleGiftDetailFragment  extends BaseGiftDetailFragment{
    private static final String param = "id";
    public static SimpleGiftDetailFragment newInstance(int id) {
        SimpleGiftDetailFragment fragment = new SimpleGiftDetailFragment();
        Bundle arg = new Bundle();
        arg.putInt(param,id);
        fragment.setArguments(arg);
        return fragment;
    }
    @Override
    public int getResourcesLayout() {
        return R.layout.fragment_gift_detail_simple;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String where = DataContract.GiftTable.GiftColumns._ID+" = "+getArguments().getInt(param);

        Cursor cursor = getActivity().getContentResolver().query(DataContract.GiftTable.CONTENT_URI,null,where,null,null);
        this.putDateIntoItsContainer(cursor);
    }


}