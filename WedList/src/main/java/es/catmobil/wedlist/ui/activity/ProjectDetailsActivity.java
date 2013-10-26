package es.catmobil.wedlist.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;

import es.catmobil.wedlist.R;
import es.catmobil.wedlist.ui.fragment.GiftsListFragment;
import es.catmobil.wedlist.ui.fragment.WedsDetailsFragment;

/**
 * Created by adria on 26/10/13.
 */
public class ProjectDetailsActivity extends ActionBarActivity implements GiftsListFragment.ComunicationActivityFragmentGiftsList {
    public static final String Param_ID = "param_id";
    private String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_details);
        id = getIntent().getStringExtra(Param_ID);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpFragments();
    }

    private void setUpFragments() {

        boolean tabletMode = findViewById(R.id.content2) != null;

        if (tabletMode) {
            //......................
        } else {
            setUpMobile();
        }



    }



    private void setUpMobile(){
        getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        listenerTabs lis = new listenerTabs();
        ActionBar.Tab tab1 = getSupportActionBar().newTab();
        tab1.setText("Details");
        tab1.setTabListener(lis);

        ActionBar.Tab tab2 = getSupportActionBar().newTab();
        tab2.setText("List");
        tab2.setTabListener(lis);

        getSupportActionBar().addTab(tab1);
        getSupportActionBar().addTab(tab2);
    }

    @Override
    public void clickItemWithId(int id) {
        Intent intent = new Intent(this,null);
        intent.putExtra("",id);
        startActivity(intent);
    }

    private class listenerTabs implements ActionBar.TabListener{

        @Override
        public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
            Fragment baseFragment = null;
            if(tab.getPosition() == 0){
                baseFragment = WedsDetailsFragment.newInstance(id);
            }else{
                baseFragment = GiftsListFragment.getInstance(id);
            }

            fragmentTransaction.replace(R.id.content1, baseFragment);
        }

        @Override
        public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

        }

        @Override
        public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

        }
    }

}
