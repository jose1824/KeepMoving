package com.example.shipp.keepmoving.ClasesFragments;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shipp.keepmoving.R;

/**
 * Created by xubudesktop1 on 19/05/16.
 */
public class FragmentPerfilAcademia extends android.support.v4.app.Fragment {


    CoordinatorLayout cLayout;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        cLayout = (CoordinatorLayout)
                inflater.inflate(R.layout.fragment_tab_academia_perfil, container, false);

        cLayout.findViewById(R.id.academia_perfil_coordinator);

        inicializaComponentes();


        return cLayout;
    }

    private void inicializaComponentes(){

    }

}