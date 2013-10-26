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
public class WedsAdapter extends CursorAdapter{

    private LayoutInflater vi;

    public WedsAdapter(Context context,Cursor c) {
        super(context, c, true);
        vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public void bindView(View arg0, Context arg1, Cursor arg2) {
        String name = "";
        String date = "";
        String count = "";

        if(null!=arg2){
            name = arg2.getString(arg2.getColumnIndex(""));
            date = arg2.getString(arg2.getColumnIndex(""));
            count = arg2.getString(arg2.getColumnIndex(""));

        }
        ImageView img = (ImageView) arg0.findViewById(R.id.item_weds_image);
        TextView txtcount = (TextView) arg0.findViewById(R.id.item_weds_count);
        TextView txtname = (TextView) arg0.findViewById(R.id.item_weds_name);
        TextView txtdate = (TextView) arg0.findViewById(R.id.item_weds_date);

        txtname.setText(name);
        txtdate.setText(date);
        txtcount.setText(count);

        img.setImageDrawable(arg1.getResources().getDrawable(R.drawable.ic_launcher));
    }

    @Override
    public View newView(Context arg0, Cursor arg1, ViewGroup arg2) {
        int pos = arg1.getPosition();
        View customListView;
        if(pos % 2 == 0){
            customListView = vi.inflate(R.layout.row_weds_left,null);
        }else{
            customListView = vi.inflate(R.layout.row_weds_rigth,null);
        }
        return customListView;
    }
}
