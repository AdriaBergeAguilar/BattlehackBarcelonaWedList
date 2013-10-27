package es.catmobil.wedlist.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;

import es.catmobil.wedlist.PayPal;
import es.catmobil.wedlist.R;
import es.catmobil.wedlist.database.contract.DataContract;
import es.catmobil.wedlist.database.cursor.GiftCursor;
import es.catmobil.wedlist.database.cursor.ProjectCursor;
import es.catmobil.wedlist.model.Gift;
import es.catmobil.wedlist.model.Project;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gift_details);
        id = getIntent().getIntExtra(Param_ID, -1);
        PayPal.startServicePaypal(this);
    }

    public String getEmail() {
        return email_receptor;
    }

    @Override
    protected void onResume() {
        super.onResume();
        Fragment f = null;
        String where = DataContract.GiftTable.GiftColumns._ID + " = " + id;
        Cursor cursor = getContentResolver().query(DataContract.GiftTable.CONTENT_URI, null, where, null, null);

        if (cursor.moveToFirst()) {
            Gift g = new GiftCursor(this).readValues(cursor);
            Cursor cursor2 = getContentResolver().query(DataContract.ProjectTable.CONTENT_URI, null, DataContract.ProjectTable.ProjectColumns._ID + "=" + g.getProjectId(), null, null);
            cursor2.moveToFirst();
            ProjectCursor proc = new ProjectCursor(this);
            Project proj =proc.readValues(cursor2);
            email_receptor = proj.getEmail();

            boolean complex = g.isComplex();
            if (complex) {
                f = ComplexGiftDetailFragment.newInstance(id);
            } else {
                f = SimpleGiftDetailFragment.newInstance(id);
            }
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content1, f);
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

                    // TODO: send 'confirm' to your server for verification.
                    // see https://developer.paypal.com/webapps/developer/docs/integration/mobile/verify-mobile-payment/
                    // for more details.

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

    @Override
    public void onDestroy() {
        stopService(new Intent(this, PayPalService.class));
        super.onDestroy();
    }

    @Override
    public void clickItemWithId(int id) {
        Object n = new Object();
    }
}