package es.catmobil.wedlist.ui.activity;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.MenuItem;

import com.parse.ParseObject;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;

import java.math.BigDecimal;

import es.catmobil.wedlist.PayPal;
import es.catmobil.wedlist.R;
import es.catmobil.wedlist.application.AppConfig;
import es.catmobil.wedlist.database.contract.DataContract;
import es.catmobil.wedlist.database.cursor.GiftCursor;
import es.catmobil.wedlist.database.cursor.PersonCursor;
import es.catmobil.wedlist.database.cursor.ProjectCursor;
import es.catmobil.wedlist.model.Gift;
import es.catmobil.wedlist.model.Person;
import es.catmobil.wedlist.model.Project;
import es.catmobil.wedlist.ui.fragment.BaseGiftDetailFragment;
import es.catmobil.wedlist.ui.fragment.ComplexGiftDetailFragment;
import es.catmobil.wedlist.ui.fragment.GiftsListFragment;
import es.catmobil.wedlist.ui.fragment.SimpleGiftDetailFragment;
import es.catmobil.wedlist.ui.fragment.WedsDetailsFragment;

/**
 * Created by adria on 27/10/13.
 */
public class GiftDetailsActivity extends ActionBarActivity implements GiftsListFragment.ComunicationActivityFragmentGiftsList {
    public static final String Param_ID = "param_id";
    private int id;
    private String email_receptor = "";
    private String giftServerId;
    private BaseGiftDetailFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gift_details);
        id = getIntent().getIntExtra(Param_ID, -1);
        PayPal.startServicePaypal(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }

    public String getEmail() {
        return email_receptor;
    }

    private Gift g;

    @Override
    protected void onResume() {
        super.onResume();
        String where = DataContract.GiftTable.GiftColumns._ID + " = " + id;
        Cursor cursor = getContentResolver().query(DataContract.GiftTable.CONTENT_URI, null, where, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            g = new GiftCursor(this).readValues(cursor);

            this.giftServerId = g.getServerId();

            Cursor cursor2 = getContentResolver().query(DataContract.ProjectTable.CONTENT_URI, null, DataContract.ProjectTable.ProjectColumns._ID + "=" + g.getProjectId(), null, null);
            if (cursor2 != null && cursor2.moveToFirst()) {
                ProjectCursor proc = new ProjectCursor(this);
                Project proj = proc.readValues(cursor2);
                email_receptor = proj.getEmail();
            }
            boolean complex = g.isComplex();
            if (complex) {
                fragment = ComplexGiftDetailFragment.newInstance(id);
            } else {
                fragment = SimpleGiftDetailFragment.newInstance(id);
            }
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content1, fragment);
            ft.commit();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            PaymentConfirmation confirm = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
            if (confirm != null) {
                try {
                    Log.i("paymentExample", confirm.toJSONObject().toString(4));

                    String serverUserId = getAccountServerId();

                    ParseObject gameScore = new ParseObject("Payment");
                    gameScore.put("gift_Id", g.getServerId());
                    gameScore.put("person_Id", serverUserId);
                    gameScore.put("quantity", g.getPrice());
                    gameScore.put("token_paypal", confirm.getProofOfPayment().getPaymentIdentifier());
                    gameScore.saveInBackground();


                    setUpUiBuyed(confirm.getPayment().getAmount());


                } catch (JSONException e) {
                    Log.e("paymentExample", "an extremely unlikely failure occurred: ", e);
                }
            }
        } else if (resultCode == Activity.RESULT_CANCELED) {
            Log.i("paymentExample", "The user canceled.");
        } else if (resultCode == PaymentActivity.RESULT_PAYMENT_INVALID) {
            Log.i("paymentExample", "An invalid payment was submitted. Please see the docs.");
        }
    }

    private void setUpUiBuyed(BigDecimal amount) {
        String str = getAccountServerId();

        String where = DataContract.PersonTable.PersonColumns.SERVER_ID + " like '%" + str + "%'";
        Cursor cursor = getContentResolver().query(DataContract.PersonTable.CONTENT_URI, null, where, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            PersonCursor personCursor = new PersonCursor();
            Person person = personCursor.readValues(cursor);

            if (fragment != null) {
                fragment.setBuyer(person);
            }

            ContentValues values = new ContentValues();
            values.put(DataContract.PersonsInGiftTable.ComplexGiftColumns.PAYER, person.getServerId());
            values.put(DataContract.PersonsInGiftTable.ComplexGiftColumns.GIFT, person.getServerId());
            values.put(DataContract.PersonsInGiftTable.ComplexGiftColumns.AMOUNT, amount.doubleValue());

            getContentResolver().insert(DataContract.PersonsInGiftTable.CONTENT_URI, values);
        }
    }

    @Override
    public void onDestroy() {
        stopService(new Intent(this, PayPalService.class));
        super.onDestroy();
    }

    @Override
    public void clickItemWithIdG(int id) {
        Object n = new Object();
    }

    private String getAccountServerId() {
        Account acc = getAccount();

        ContentResolver cr = getContentResolver();

        String whereMail = DataContract.PersonTable.PersonColumns.PROFILE_GPLUS + " like '%" + acc.name + "%'";
        Cursor user = cr.query(DataContract.PersonTable.CONTENT_URI, null, whereMail, null, null);

        if (user != null && user.moveToFirst()) {
            return user.getString(user.getColumnIndex(DataContract.PersonTable.PersonColumns.SERVER_ID));
        }

        return "";
    }

    private Account getAccount() {
        AccountManager accountManager = AccountManager.get(this);
        if (accountManager != null) {
            return accountManager.getAccountsByType(AppConfig.ACCOUNT_TYPE)[0];
        }
        return null;
    }
}