package es.catmobil.wedlist.ui.fragment;

import android.database.Cursor;
import android.os.Bundle;

import es.catmobil.wedlist.R;
import es.catmobil.wedlist.database.contract.DataContract;
import es.catmobil.wedlist.model.Person;

/**
 * Created by adria on 27/10/13.
 */
public class ComplexGiftDetailFragment extends BaseGiftDetailFragment{
    private static final String param = "id";

    public static ComplexGiftDetailFragment newInstance(int id) {
        ComplexGiftDetailFragment fragment = new ComplexGiftDetailFragment();
        Bundle arg = new Bundle();
        arg.putInt(param,id);
        fragment.setArguments(arg);
        return fragment;
    }
    @Override
    public int getResourcesLayout() {
        return R.layout.fragment_gift_detail_complex;
    }

    @Override
    public void setBuyer(Person person) {

    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String where = DataContract.GiftTable.GiftColumns._ID+" = "+getArguments().getInt(param);

        Cursor cursor = getActivity().getContentResolver().query(DataContract.GiftTable.CONTENT_URI,null,where,null,null);
        this.putDateIntoItsContainer(cursor);
    }
}