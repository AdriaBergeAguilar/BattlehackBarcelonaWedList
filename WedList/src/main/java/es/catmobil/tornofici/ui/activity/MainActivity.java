package es.catmobil.tornofici.ui.activity;

import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import es.catmobil.tornofici.R;
import es.catmobil.tornofici.ui.fragment.BaseFragment;
import es.catmobil.tornofici.ui.fragment.NavigationDrawerFragment;

public class MainActivity extends ActionBarActivity implements ActionBar.TabListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUpActionBar();

        boolean tabletMode = findViewById(R.id.content2) != null;

        if (tabletMode) {
            setUpTablet();
        } else {
            setUpMobile();
        }
    }

    private void setUpActionBar() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return false;
    }

    private void setUpMobile() {
        getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        ActionBar.Tab tab1 = getSupportActionBar().newTab();
        tab1.setText("Sect 1");
        tab1.setTabListener(this);

        ActionBar.Tab tab2 = getSupportActionBar().newTab();
        tab2.setText("Sect 2");
        tab2.setTabListener(this);

        ActionBar.Tab tab3 = getSupportActionBar().newTab();
        tab3.setText("Sect 3");
        tab3.setTabListener(this);

        getSupportActionBar().addTab(tab1);
        getSupportActionBar().addTab(tab2);
        getSupportActionBar().addTab(tab3);
    }

    private void setUpTablet() {
        getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        ft.replace(R.id.content1, BaseFragment.newInstance(0));
        ft.replace(R.id.content2, BaseFragment.newInstance(1));
        ft.replace(R.id.content3, BaseFragment.newInstance(2));

        ft.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        BaseFragment baseFragment = BaseFragment.newInstance(tab.getPosition());

        fragmentTransaction.replace(R.id.content1, baseFragment);
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }
}
