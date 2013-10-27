package es.catmobil.wedlist.ui.fragment;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidquery.AQuery;

import es.catmobil.wedlist.R;
import es.catmobil.wedlist.database.contract.DataContract;
import es.catmobil.wedlist.database.cursor.GiftCursor;
import es.catmobil.wedlist.model.Gift;

/**
 * Created by adria on 26/10/13.
 */
public class SimpleGiftDetailFragment extends BaseGiftDetailFragment {
    private static final String param = "id";

    public static SimpleGiftDetailFragment newInstance(int id) {
        SimpleGiftDetailFragment fragment = new SimpleGiftDetailFragment();
        Bundle arg = new Bundle();
        arg.putInt(param, id);
        fragment.setArguments(arg);
        return fragment;
    }

    @Override
    public int getResourcesLayout() {
        return R.layout.fragment_gift_detail_simple;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String where = DataContract.GiftTable.GiftColumns._ID + " = " + getArguments().getInt(param);

        Cursor cursor = getActivity().getContentResolver().query(DataContract.GiftTable.CONTENT_URI, null, where, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            String image = cursor.getString(cursor.getColumnIndex(DataContract.GiftTable.GiftColumns.PICTURE_URL));
            ImageView avatar = (ImageView) getView().findViewById(R.id.detail_icon_person);

            Gift gift = new GiftCursor(getActivity()).readValues(cursor);
            new AQuery(imageView).id(this.imageView).image(image, true, true);

            AQuery aQuery = new AQuery(avatar);

            getView().findViewById(R.id.detail_gift_pay).setEnabled(false);

            if (gift.getBuyers().size() == 0) {
                getView().findViewById(R.id.detail_gift_pay).setEnabled(true);
                aQuery.id(avatar).image(R.drawable.icon_person_undefined);
            } else if (gift.getBuyers().size() == 1) {
                aQuery.id(avatar).image(gift.getBuyers().get(0).getImage(), true, true);
            } else {
                getView().findViewById(R.id.detail_gift_pay).setEnabled(true);

                aQuery.id(avatar).image(R.drawable.icon_persons_undefined);
            }

            this.putDateIntoItsContainer(cursor);
        }
    }


}