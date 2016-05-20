package com.example.shipp.keepmoving.ClasesFragments;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.shipp.keepmoving.ClasesAdapters.AcademiaAdapter;
import com.example.shipp.keepmoving.Clases.DataSource;
import com.example.shipp.keepmoving.R;

/**
 * Created by xubudesktop1 on 17/05/16.
 */
public class FragmentAcademias extends android.support.v4.app.Fragment {
    ListView lista;
    AcademiaAdapter adaptador;

    CoordinatorLayout cLayout;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {



        cLayout = (CoordinatorLayout)
                inflater.inflate(R.layout.fragment_tab_academias, container, false);

        cLayout.findViewById(R.id.academias_coordinator);

        inicializaComponentes();

        adaptador = new AcademiaAdapter(getActivity().getApplicationContext(), DataSource.ACADEMIAS);
        lista.setAdapter(adaptador);

        return cLayout;
    }

    private void inicializaComponentes(){
        lista = (ListView)cLayout.findViewById(R.id.lista);
    }

}
