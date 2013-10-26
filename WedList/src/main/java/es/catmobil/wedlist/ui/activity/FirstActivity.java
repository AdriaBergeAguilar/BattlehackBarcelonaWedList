package es.catmobil.wedlist.ui.activity;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;

import es.catmobil.wedlist.BuildConfig;
import es.catmobil.wedlist.R;
import es.catmobil.wedlist.ui.fragment.CasesFragment;

/**
 * Created by Bernat on 22/10/13.
 */
public class FirstActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
    }

    @Override
    protected void onResume() {
        super.onResume();

        AccountManager accountManager = AccountManager.get(this);

        if (accountManager != null) {
            Account[] accountsByType = accountManager.getAccountsByType(BuildConfig.ACCOUNT_TYPE);

            if (accountsByType.length > 0) {
                setUpFragments();
            } else {
                Intent loginIntent = new Intent(this, LoginActivity.class);
                startActivity(loginIntent);
            }
        }
    }

    private void setUpFragments() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content1, new CasesFragment());
        ft.commit();
    }
}
