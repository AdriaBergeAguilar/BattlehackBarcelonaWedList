package es.catmobil.wedlist.ui.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import es.catmobil.wedlist.R;

/**
 * Created by adria on 26/10/13.
 */
public class GiftsAdapter extends CursorAdapter {

    private LayoutInflater vi;

    public GiftsAdapter(Context context,Cursor c) {
        super(context, c, true);
        vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public void bindView(View arg0, Context arg1, Cursor arg2) {
        String name = "";
        String price = "";

        if(null!=arg2){
            name = arg2.getString(arg2.getColumnIndex(""));
            price = arg2.getString(arg2.getColumnIndex(""));

        }
        ImageView imgGift = (ImageView) arg0.findViewById(R.id.item_gift_image_gift);
        ImageView imgUser = (ImageView) arg0.findViewById(R.id.item_gift_image_user);
        TextView txtname = (TextView) arg0.findViewById(R.id.item_gift_name);
        TextView txtprice = (TextView) arg0.findViewById(R.id.item_gift_price);

        txtname.setText(name);
        txtprice.setText(price);

        imgGift.setImageDrawable(arg1.getResources().getDrawable(R.drawable.ic_launcher));
        imgUser.setImageDrawable(arg1.getResources().getDrawable(R.drawable.ic_launcher));
    }

    @Override
    public View newView(Context arg0, Cursor arg1, ViewGroup arg2) {
        int pos = arg1.getPosition();
        View customListView;
        customListView = vi.inflate(R.layout.row_gifts,null);
        return customListView;
    }
}