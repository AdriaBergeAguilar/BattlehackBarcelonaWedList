package es.catmobil.wedlist;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;

import java.math.BigDecimal;

/**
 * Created by Bernat on 26/10/13.
 */
public class PayPal {

    // set to PaymentActivity.ENVIRONMENT_PRODUCTION to move real money.
    // set to PaymentActivity.ENVIRONMENT_SANDBOX to use your test credentials from https://developer.paypal.com
    // set to PaymentActivity.ENVIRONMENT_NO_NETWORK to kick the tires without communicating to PayPal's servers.
    private static final String CONFIG_ENVIRONMENT = PaymentActivity.ENVIRONMENT_SANDBOX;

    // note that these credentials will differ between live & sandbox environments.
    private static final String CONFIG_CLIENT_ID = BuildConfig.CLIENT_ID;
    // when testing in sandbox, this is likely the -facilitator email address.
    private static final String CONFIG_RECEIVER_EMAIL = BuildConfig.SANDBOX_ACCOUNT;

    public static void startServicePaypal(Context context) {
        Intent intent = new Intent(context, PayPalService.class);

        intent.putExtra(PaymentActivity.EXTRA_PAYPAL_ENVIRONMENT, CONFIG_ENVIRONMENT);
        intent.putExtra(PaymentActivity.EXTRA_CLIENT_ID, CONFIG_CLIENT_ID);
        intent.putExtra(PaymentActivity.EXTRA_RECEIVER_EMAIL, CONFIG_RECEIVER_EMAIL);

        context.startService(intent);
    }

    public static void startActivityPaypal(Activity context) {
        PayPalPayment thingToBuy = new PayPalPayment(new BigDecimal("1.75"), "USD", "hipster jeans");

        Intent intent = new Intent(context, PaymentActivity.class);

        intent.putExtra(PaymentActivity.EXTRA_PAYPAL_ENVIRONMENT, CONFIG_ENVIRONMENT);
        intent.putExtra(PaymentActivity.EXTRA_CLIENT_ID, CONFIG_CLIENT_ID);
        intent.putExtra(PaymentActivity.EXTRA_RECEIVER_EMAIL, CONFIG_RECEIVER_EMAIL);

        // It's important to repeat the clientId here so that the SDK has it if Android restarts your
        // app midway through the payment UI flow.
        intent.putExtra(PaymentActivity.EXTRA_CLIENT_ID, CONFIG_CLIENT_ID);
        intent.putExtra(PaymentActivity.EXTRA_PAYER_ID, CONFIG_RECEIVER_EMAIL);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, thingToBuy);

        context.startActivityForResult(intent, 6660);
    }
}
