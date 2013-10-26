package es.catmobil.wedlist.ui.activity;

import android.accounts.Account;
import android.accounts.AccountAuthenticatorActivity;
import android.accounts.AccountManager;
import android.content.ContentResolver;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import es.catmobil.wedlist.BuildConfig;
import es.catmobil.wedlist.R;

/**
 * Created by Bernat on 26/10/13.
 */
public class LoginActivity extends AccountAuthenticatorActivity implements View.OnClickListener {

    private EditText mailEdt;
    private EditText passEdt;

    public static final String ARG_IS_ADDING_NEW_ACCOUNT = "ARG_IS_ADDING_NEW_ACCOUNT";
    public static final String ARG_ACCOUNT_TYPE = "ARG_ACCOUNT_TYPE";

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_login);

        findViews();

    }

    private void findViews() {
        mailEdt = (EditText) findViewById(R.id.mailEdt);
        passEdt = (EditText) findViewById(R.id.passwEdt);

        findViewById(R.id.btn).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn) {

            AccountManager accountManager = AccountManager.get(this);

            if (accountManager != null) {
                Account acc = new Account(mailEdt.getText().toString(), BuildConfig.ACCOUNT_TYPE);
                if (accountManager.addAccountExplicitly(acc, passEdt.getText().toString(), null)) {
                    Intent res = new Intent();
                    res.putExtra(AccountManager.KEY_ACCOUNT_NAME, acc.name);
                    res.putExtra(AccountManager.KEY_ACCOUNT_TYPE, BuildConfig.ACCOUNT_TYPE);


                    ContentResolver.setIsSyncable(acc, BuildConfig.AUTHORITY, 1);

                    Bundle params = new Bundle();

                    params.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, false);
                    params.putBoolean(ContentResolver.SYNC_EXTRAS_DO_NOT_RETRY, false);
                    params.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, false);

                    ContentResolver.addPeriodicSync(acc, BuildConfig.AUTHORITY, params, BuildConfig.SYNC_FREQ);
                    ContentResolver.setSyncAutomatically(acc, BuildConfig.AUTHORITY, true);

                    ContentResolver.requestSync(acc, BuildConfig.AUTHORITY, params);



                    setAccountAuthenticatorResult(res.getExtras());
                    setResult(RESULT_OK, res);
                    finish();
                }
            }
        }
    }
}
