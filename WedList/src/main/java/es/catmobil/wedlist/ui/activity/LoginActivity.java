package es.catmobil.wedlist.ui.activity;

import android.accounts.Account;
import android.accounts.AccountAuthenticatorActivity;
import android.accounts.AccountManager;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

import es.catmobil.wedlist.R;
import es.catmobil.wedlist.application.AppConfig;
import es.catmobil.wedlist.database.contract.DataContract;
import es.catmobil.wedlist.database.cursor.PersonCursor;
import es.catmobil.wedlist.model.Person;

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
                Account acc = new Account(mailEdt.getText().toString(), AppConfig.ACCOUNT_TYPE);
                if (accountManager.addAccountExplicitly(acc, passEdt.getText().toString(), null)) {
                    Intent res = new Intent();
                    res.putExtra(AccountManager.KEY_ACCOUNT_NAME, acc.name);
                    res.putExtra(AccountManager.KEY_ACCOUNT_TYPE, AppConfig.ACCOUNT_TYPE);

                    ContentResolver.setIsSyncable(acc, AppConfig.AUTHORITY, 1);

                    Bundle params = new Bundle();

                    params.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, false);
                    params.putBoolean(ContentResolver.SYNC_EXTRAS_DO_NOT_RETRY, false);
                    params.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, false);

                    ContentResolver.addPeriodicSync(acc, AppConfig.AUTHORITY, params, AppConfig.SYNC_FREQ);
                    ContentResolver.setSyncAutomatically(acc, AppConfig.AUTHORITY, true);

                    ContentResolver.requestSync(acc, AppConfig.AUTHORITY, params);


                    setAccountAuthenticatorResult(res.getExtras());
                    setResult(RESULT_OK, res);

                    setUpUser(acc);

                    finish();
                }
            }
        }
    }

    private void setUpUser(Account acc) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Persons");
        query.whereEqualTo("email", acc.name);
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject parseObject, ParseException e) {
                Person person = new Person();

                if (parseObject != null) {
                    try {
                        person.setServerId(parseObject.getObjectId());
                        person.setName(parseObject.getString("name"));
                        person.setImage(parseObject.getString("image"));
                        person.setEmail(parseObject.getString("email"));
                        ContentValues values = new PersonCursor().setValues(person);

                        getContentResolver().insert(DataContract.PersonTable.CONTENT_URI, values);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
    }
}
