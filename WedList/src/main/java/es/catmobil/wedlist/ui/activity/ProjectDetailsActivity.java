package es.catmobil.wedlist.ui.activity;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;

import es.catmobil.wedlist.BuildConfig;
import es.catmobil.wedlist.R;
import es.catmobil.wedlist.ui.fragment.WedsListFragment;

/**
 * Created by adria on 26/10/13.
 */
public class ProjectDetailsActivity extends ActionBarActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpFragments();
    }

    private void setUpFragments() {

        boolean tabletMode = findViewById(R.id.content2) != null;

        if (tabletMode) {
            setUpTablet();
        } else {
            setUpMobile();
        }



    }

    private void setUpTablet(){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content1, new WedsListFragment());
        ft.replace(R.id.content2, new WedsListFragment());
        ft.commit();
    }

    private void setUpMobile(){
        //startActivity(new Intent(this,null));
    }


}
