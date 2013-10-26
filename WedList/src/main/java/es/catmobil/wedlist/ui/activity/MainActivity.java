package es.catmobil.wedlist.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import java.math.BigDecimal;

import es.catmobil.wedlist.BuildConfig;
import es.catmobil.wedlist.R;
import es.catmobil.wedlist.ui.fragment.BaseFragment;

public class MainActivity extends ActionBarActivity implements ActionBar.TabListener {

    // set to PaymentActivity.ENVIRONMENT_PRODUCTION to move real money.
    // set to PaymentActivity.ENVIRONMENT_SANDBOX to use your test credentials from https://developer.paypal.com
    // set to PaymentActivity.ENVIRONMENT_NO_NETWORK to kick the tires without communicating to PayPal's servers.
    private static final String CONFIG_ENVIRONMENT = PaymentActivity.ENVIRONMENT_SANDBOX;

    // note that these credentials will differ between live & sandbox environments.
    private static final String CONFIG_CLIENT_ID = "bernatbor15-facilitator@gmail.com";
    // when testing in sandbox, this is likely the -facilitator email address.
    private static final String CONFIG_RECEIVER_EMAIL = BuildConfig.ADRI_SANDBOX_ACCOUNT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        startServicePaypal();
        startActivityPaypal();


        setUpActionBar();

        boolean tabletMode = findViewById(R.id.content2) != null;

        if (tabletMode) {
            setUpTablet();
        } else {
            setUpMobile();
        }
    }


    private void startServicePaypal() {
        Intent intent = new Intent(this, PayPalService.class);

        intent.putExtra(PaymentActivity.EXTRA_PAYPAL_ENVIRONMENT, CONFIG_ENVIRONMENT);
        intent.putExtra(PaymentActivity.EXTRA_CLIENT_ID, CONFIG_CLIENT_ID);
        intent.putExtra(PaymentActivity.EXTRA_RECEIVER_EMAIL, CONFIG_RECEIVER_EMAIL);

        startService(intent);
    }

    private void startActivityPaypal() {
        PayPalPayment thingToBuy = new PayPalPayment(new BigDecimal("1.75"), "USD", "hipster jeans");

        Intent intent = new Intent(this, PaymentActivity.class);

        intent.putExtra(PaymentActivity.EXTRA_PAYPAL_ENVIRONMENT, CONFIG_ENVIRONMENT);
        intent.putExtra(PaymentActivity.EXTRA_CLIENT_ID, CONFIG_CLIENT_ID);
        intent.putExtra(PaymentActivity.EXTRA_RECEIVER_EMAIL, CONFIG_RECEIVER_EMAIL);

        // It's important to repeat the clientId here so that the SDK has it if Android restarts your
        // app midway through the payment UI flow.
        intent.putExtra(PaymentActivity.EXTRA_CLIENT_ID, CONFIG_CLIENT_ID);
        intent.putExtra(PaymentActivity.EXTRA_PAYER_ID, CONFIG_RECEIVER_EMAIL);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, thingToBuy);

        startActivityForResult(intent, 6660);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {

            PaymentConfirmation confirmation = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);

            if (confirmation != null) {

                Log.i("PAYPAL-TAG", "" + confirmation.toJSONObject().toString(2));
                if (requestCode == 666) {
                    Log.i("PAYPAL-TAG", "" + confirmation.toJSONObject().toString(3));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
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

    @Override
    public void onDestroy() {
        stopService(new Intent(this, PayPalService.class));
        super.onDestroy();
    }
}
