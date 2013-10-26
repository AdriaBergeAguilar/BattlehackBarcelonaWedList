package es.catmobil.tornofici.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.List;

import es.catmobil.tornofici.ui.activity.MainActivity;

/**
 * Created by Bernat on 22/10/13.
 */
public class CasesFragment extends ListFragment {

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        List<String> items = new ArrayList<String>();
        for (int i = 0; i < 20; i++) {
            items.add("Case " + (i + 1));
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, items);

        setListAdapter(adapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
    }
}
