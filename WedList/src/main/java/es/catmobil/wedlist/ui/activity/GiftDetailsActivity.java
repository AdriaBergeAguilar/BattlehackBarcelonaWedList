package es.catmobil.wedlist.ui.activity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;

import es.catmobil.wedlist.R;
import es.catmobil.wedlist.database.contract.DataContract;
import es.catmobil.wedlist.ui.fragment.ComplexGiftDetailFragment;
import es.catmobil.wedlist.ui.fragment.GiftsListFragment;
import es.catmobil.wedlist.ui.fragment.SimpleGiftDetailFragment;
import es.catmobil.wedlist.ui.fragment.WedsDetailsFragment;

/**
 * Created by adria on 27/10/13.
 */
public class GiftDetailsActivity extends ActionBarActivity{
    public static final String Param_ID = "param_id";
    private int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gift_details);
        id = getIntent().getIntExtra(Param_ID,-1);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Fragment f = null;
        String where = DataContract.GiftTable.GiftColumns._ID+" = "+id;
        Cursor cursor = getContentResolver().query(DataContract.GiftTable.CONTENT_URI,null,where,null,null);
        cursor.moveToFirst();
        String complex = "";//cursor.getString(cursor.getColumnIndex(DataContract.GiftTable.GiftColumns.COMPLEX));

        if(Boolean.parseBoolean(complex) || cursor.getCount() == 0){
            f = ComplexGiftDetailFragment.newInstance(id);
        }else{
            f = SimpleGiftDetailFragment.newInstance(id);
        }
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content1, f);
        ft.commit();
    }


}