package es.catmobil.tornofici.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import es.catmobil.tornofici.R;

/**
 * Created by Bernat on 22/10/13.
 */
public class BaseFragment extends Fragment {

    private static final String ARG_EXTRA_POS = "ARG_EXTRA_POS";

    public static BaseFragment newInstance(int position) {
        BaseFragment baseFragment = new BaseFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_EXTRA_POS, (position + 1));

        baseFragment.setArguments(args);

        return baseFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.frgment_base, null);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView textView = (TextView) view.findViewById(R.id.textView);
        Bundle args = getArguments();

        if (args != null && args.containsKey(ARG_EXTRA_POS)) {
            int pos = args.getInt(ARG_EXTRA_POS, 0);
            textView.setText("Position: " + pos);
        }

    }
}
