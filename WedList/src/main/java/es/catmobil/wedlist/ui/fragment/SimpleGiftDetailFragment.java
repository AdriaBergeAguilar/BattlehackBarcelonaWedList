package es.catmobil.wedlist.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import es.catmobil.wedlist.R;

/**
 * Created by adria on 26/10/13.
 */
public class SimpleGiftDetailFragment  extends BaseGiftDetailFragment{
    @Override
    public int getResourcesLayout() {
        return R.layout.fragment_gift_detail_simple;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.putDateIntoItsContainer(null);
    }
}
