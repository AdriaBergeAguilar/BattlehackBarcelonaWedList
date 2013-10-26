package es.catmobil.wedlist.ui.adapter;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.androidquery.AQuery;

import es.catmobil.wedlist.R;
import es.catmobil.wedlist.database.contract.DataContract;

/**
 * Created by adria on 26/10/13.
 */
public class GiftsAdapter extends CursorAdapter {

    private LayoutInflater vi;
    private AQuery aq;


    public GiftsAdapter(Context context,Cursor c) {
        super(context, c, true);
        vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        aq = new AQuery(context);
    }

    @Override
    public void bindView(View arg0, Context arg1, Cursor arg2) {
        String name = "";
        String price = "";
        String imageGift = "";
        if(null!=arg2){
            name = arg2.getString(arg2.getColumnIndex(DataContract.GiftTable.GiftColumns.NAME));
            price = arg2.getString(arg2.getColumnIndex(DataContract.GiftTable.GiftColumns.PRICE));
            imageGift = arg2.getString(arg2.getColumnIndex(DataContract.GiftTable.GiftColumns.PICTURE_URL));

        }
        TextView txtname = (TextView) arg0.findViewById(R.id.item_gift_name);
        TextView txtprice = (TextView) arg0.findViewById(R.id.item_gift_price);

        txtname.setText(name);
        txtprice.setText(price);

        aq.id(R.id.item_gift_image_gift).image(imageGift);
        Uri sinleUri = ContentUris.withAppendedId(DataContract.PersonsInGiftTable.CONTENT_URI,arg2.getLong(arg2.getColumnIndex(BaseColumns._ID)));
        Cursor cursor = arg1.getContentResolver().query(sinleUri,null,null,null,null);

        if(cursor.moveToFirst() && cursor.getCount() > 1){
             aq.id(R.id.item_gift_image_user).image(R.drawable.ic_launcher);
        }else if(cursor.moveToFirst() && cursor.getCount() == 1){
            String image = cursor.getString(cursor.getColumnIndex(DataContract.PersonTable.PersonColumns.PROFILE_IMAGE_URL));//PROFILE_URL
            aq.id(R.id.item_gift_image_user).image(image,true,true);
        }else{
            aq.id(R.id.item_gift_image_user).image(R.drawable.ic_person_default);
        }

    }

    @Override
    public View newView(Context arg0, Cursor arg1, ViewGroup arg2) {
        View customListView;
        customListView = vi.inflate(R.layout.row_gifts,null);
        return customListView;
    }
}