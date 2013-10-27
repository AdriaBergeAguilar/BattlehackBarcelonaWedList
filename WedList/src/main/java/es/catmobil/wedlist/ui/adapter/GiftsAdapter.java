package es.catmobil.wedlist.ui.adapter;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;
import android.support.v4.widget.CursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidquery.AQuery;

import es.catmobil.wedlist.R;
import es.catmobil.wedlist.database.contract.DataContract;
import es.catmobil.wedlist.database.cursor.GiftCursor;
import es.catmobil.wedlist.database.cursor.ProjectCursor;
import es.catmobil.wedlist.model.Gift;
import es.catmobil.wedlist.model.Project;

/**
 * Created by adria on 26/10/13.
 */
public class GiftsAdapter extends CursorAdapter {

    private LayoutInflater vi;
    private AQuery aq;


    public GiftsAdapter(Context context, Cursor c) {
        super(context, c, true);
        vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        aq = new AQuery(context);
    }

    @Override
    public void bindView(View arg0, Context arg1, Cursor arg2) {

        Gift gift = new GiftCursor(arg1).readValues(arg2);

        TextView txtname = (TextView) arg0.findViewById(R.id.item_gift_name);
        TextView txtprice = (TextView) arg0.findViewById(R.id.item_gift_price);
        ImageView imgImage = (ImageView) arg0.findViewById(R.id.item_gift_image_gift);
        ImageView imgUser = (ImageView) arg0.findViewById(R.id.item_gift_image_user);

        txtname.setText(gift.getName());
        txtprice.setText(gift.getPrice() + " USD");

        aq.id(imgImage).image(gift.getPicturePath(), true, true);

        if (gift.getBuyers().size() > 1) {
            aq.id(imgUser).image(R.drawable.ic_launcher);
        } else if (gift.getBuyers().size() == 1) {
            aq.id(imgUser).image(gift.getBuyers().get(0).getImage(), true, true);
        } else {
            aq.id(imgUser).image(R.drawable.ic_person_default);
        }

    }

    @Override
    public View newView(Context arg0, Cursor arg1, ViewGroup arg2) {
        View customListView;
        customListView = vi.inflate(R.layout.row_gifts, null);
        return customListView;
    }
}