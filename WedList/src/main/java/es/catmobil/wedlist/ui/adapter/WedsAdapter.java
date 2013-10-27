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
import es.catmobil.wedlist.database.cursor.ProjectCursor;
import es.catmobil.wedlist.model.Project;

/**
 * Created by adria on 26/10/13.
 */
public class WedsAdapter extends CursorAdapter {

    private LayoutInflater vi;

    public WedsAdapter(Context context, Cursor c) {
        super(context, c, true);
        vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public void bindView(View arg0, Context arg1, Cursor arg2) {
        String name = "";
        String date = "";
        int count = 0;
        String desc = "";
        String img = "";

        TextView txtcount = (TextView) arg0.findViewById(R.id.item_weds_count);
        TextView txtname = (TextView) arg0.findViewById(R.id.item_weds_name);
        TextView txtdate = (TextView) arg0.findViewById(R.id.item_weds_date);
        TextView txtdesc = (TextView) arg0.findViewById(R.id.item_weds_desc);

        if (null != arg2) {

            Project project = new ProjectCursor(arg1).readValues(arg2);

            name = project.getName();
            date = project.getDate().toString();
            count = project.getGifts().size();
            img = project.getImage();
            desc = project.getDescription();
        }

        txtname.setText(name);
        txtdate.setText(date);
        txtdesc.setText(desc);
        txtcount.setText("" + Math.round(Math.random() * 10));
        AQuery aq = new AQuery(arg0);
        aq.id(R.id.item_weds_image).image(img, true, true);
    }

    @Override
    public View newView(Context arg0, Cursor arg1, ViewGroup arg2) {
        int pos = arg1.getPosition();
        View customListView = vi.inflate(R.layout.row_weds, null);
        return customListView;
    }
}
