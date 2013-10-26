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
import android.widget.EditText;
import android.widget.ListView;

import es.catmobil.wedlist.R;
import es.catmobil.wedlist.ui.adapter.WedsAdapter;

/**
 * Created by adria on 26/10/13.
 */
public class AddPayGiftFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_gift,null);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Activity ac = getActivity();

        EditText nom = (EditText)ac.findViewById(R.id.add_name);
        EditText description = (EditText)ac.findViewById(R.id.add_description);
        EditText price = (EditText)ac.findViewById(R.id.add_price);

        EditText image = (EditText)ac.findViewById(R.id.add_image);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //fixme k ago?
            }
        });

        Button btnpay = (Button)ac.findViewById(R.id.btn_pay);
        btnpay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //fixme k ago?
            }
        });
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        activity.setTitle(R.string.AddGift);

    }




}
