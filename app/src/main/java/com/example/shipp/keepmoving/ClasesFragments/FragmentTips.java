package com.example.shipp.keepmoving.ClasesFragments;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;


import com.example.shipp.keepmoving.ClasesAdapters.AdapterTips;
import com.example.shipp.keepmoving.R;
/**
 * Created by xubudesktop1 on 18/05/16.
 */
public class FragmentTips extends android.support.v4.app.Fragment implements AdapterView.OnItemClickListener {

    private GridView gridView;
    private AdapterTips adaptador;

    CoordinatorLayout cLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        cLayout = (CoordinatorLayout)
                inflater.inflate(R.layout.fragment_tab_tips, container, false);

        cLayout.findViewById(R.id.tips_coordinator);

        gridView = (GridView) cLayout.findViewById(R.id.grid);
        adaptador = new AdapterTips(getActivity());
        gridView.setAdapter(adaptador);
        gridView.setOnItemClickListener(this);

        return cLayout;
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

}
