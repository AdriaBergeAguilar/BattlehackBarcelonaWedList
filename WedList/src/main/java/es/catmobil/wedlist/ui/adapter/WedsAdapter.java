package es.catmobil.wedlist.ui.adapter;

import android.content.Context;
import android.database.Cursor;
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
        String img = "";

        if(null!=arg2){
            name = arg2.getString(arg2.getColumnIndex(DataContract.ProjectTable.ProjectColumns.NAME));
            date = arg2.getString(arg2.getColumnIndex(DataContract.ProjectTable.ProjectColumns.DATE));
            count = arg2.getString(arg2.getColumnIndex(DataContract.ComplexGiftTable.ComplexGiftColumns.AMOUNT));
            img = arg2.getString(arg2.getColumnIndex(DataContract.ProjectTable.ProjectColumns.IMAGE));
        }
        TextView txtcount = (TextView) arg0.findViewById(R.id.item_weds_count);
        TextView txtname = (TextView) arg0.findViewById(R.id.item_weds_name);
        TextView txtdate = (TextView) arg0.findViewById(R.id.item_weds_date);

        txtname.setText(name);
        txtdate.setText(date);
        txtcount.setText(count);
        AQuery aq = new AQuery(arg0);
        aq.id(R.id.item_weds_image).image(img,true,true);
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
