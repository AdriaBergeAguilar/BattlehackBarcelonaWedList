package es.catmobil.wedlist.ui.fragment;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.paypal.android.sdk.aC;

import es.catmobil.wedlist.PayPal;
import es.catmobil.wedlist.R;
import es.catmobil.wedlist.database.contract.DataContract;
import es.catmobil.wedlist.ui.activity.GiftDetailsActivity;
import es.catmobil.wedlist.ui.adapter.WedsAdapter;

/**
 * Created by adria on 26/10/13.
 */
public abstract class BaseGiftDetailFragment extends Fragment{
    protected TextView TxtDescription;
    protected TextView TxtPrice;
    protected Button BtnPay;
    private String Title = "";
    private String email;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(getResourcesLayout(),null);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Activity ac = getActivity();
        TxtDescription = (TextView) ac.findViewById(R.id.detail_gift_description);
        TxtPrice = (TextView) ac.findViewById(R.id.detail_gift_price);
        BtnPay = (Button) ac.findViewById(R.id.detail_gift_pay);
        BtnPay.setOnClickListener(new OnClick());

    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        activity.setTitle(Title);
    }

    public abstract int getResourcesLayout();
    public void putDateIntoItsContainer(Cursor cursor){
        cursor.moveToFirst();
        if(cursor.getCount() > 0){
            String desc = cursor.getString(cursor.getColumnIndex(DataContract.GiftTable.GiftColumns.DESCRIPTION));
            String price = cursor.getString(cursor.getColumnIndex(DataContract.GiftTable.GiftColumns.PRICE));
            Title = cursor.getString(cursor.getColumnIndex(DataContract.GiftTable.GiftColumns.NAME));
            TxtDescription.setText(desc);
            TxtPrice.setText(price);


        }
    }
    private class OnClick implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            GiftDetailsActivity act = ((GiftDetailsActivity) getActivity());
            PayPal.startActivityPaypal(getActivity(),TxtPrice.getText(),TxtDescription.getText(),act.getEmail());
        }
    }
}
