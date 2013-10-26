package es.catmobil.wedlist.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;

import es.catmobil.wedlist.R;
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
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content2, SimpleGiftDetailFragment.newInstance(id));
        ft.commit();
    }


}