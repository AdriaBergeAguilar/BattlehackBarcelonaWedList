package es.catmobil.wedlist.ui.activity;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.parse.ParseAnalytics;

import es.catmobil.wedlist.R;
import es.catmobil.wedlist.application.AppConfig;
import es.catmobil.wedlist.ui.fragment.GiftsListFragment;
import es.catmobil.wedlist.ui.fragment.WedsDetailsFragment;
import es.catmobil.wedlist.ui.fragment.WedsListFragment;

/**
 * Created by Bernat on 22/10/13.
 */
public class FirstActivity extends ActionBarActivity implements GiftsListFragment.ComunicationActivityFragmentGiftsList, WedsListFragment.ComunicationActivityFragmentProjectList{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ParseAnalytics.trackAppOpened(getIntent());
        setContentView(R.layout.activity_first);
    }

    @Override
    protected void onResume() {
        super.onResume();

        AccountManager accountManager = AccountManager.get(this);

        if (accountManager != null) {
            Account[] accountsByType = accountManager.getAccountsByType(AppConfig.ACCOUNT_TYPE);

            if (accountsByType.length > 0) {
                setUpFragments();
            } else {
                Intent loginIntent = new Intent(this, LoginActivity.class);
                startActivity(loginIntent);
            }
        }
    }
    private boolean isTablet(){
        return findViewById(R.id.content2) != null;
    }
    private void setUpFragments() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content1, new WedsListFragment());
        ft.commit();

    }

    private void setUpTablet(int id){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content2, WedsDetailsFragment.newInstance(id));
        ft.replace(R.id.content3, GiftsListFragment.getInstance(id));
        ft.commit();
    }

    private void setUpMobile(int id){
        Intent intent = new Intent(this, ProjectDetailsActivity.class);
        intent.putExtra(ProjectDetailsActivity.Param_ID,id);
        startActivity(intent);
    }

    @Override
    public void clickItemWithId(int id) {
        if (isTablet()) {
            setUpTablet(id);
        } else {
            setUpMobile(id);
        }
    }

    @Override
    public void clickItemWithIdG(int id) {
        Intent intent = new Intent(this, GiftDetailsActivity.class);
        intent.putExtra(GiftDetailsActivity.Param_ID, id);
        startActivity(intent);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {

            case R.id.action_create_project:
               Intent intent=new Intent(this,NewProjectActivity.class);
                startActivity(intent);

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
