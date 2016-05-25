package com.example.shipp.keepmoving.ClasesFragments;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.shipp.keepmoving.Clases.Academia;
import com.example.shipp.keepmoving.Clases.Evento;
import com.example.shipp.keepmoving.ClasesAdapters.AcademiaAdapter;
import com.example.shipp.keepmoving.Clases.DataSource;
import com.example.shipp.keepmoving.ClasesFirebase.FirebaseControl;
import com.example.shipp.keepmoving.R;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by xubudesktop1 on 17/05/16.
 */
public class FragmentAcademias extends android.support.v4.app.Fragment {
    ListView lista;
    AcademiaAdapter adaptador;
    FirebaseControl firebaseControl;
    String prevOldestPostID;
    String oldestPostId;

    CoordinatorLayout cLayout;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        cLayout = (CoordinatorLayout)
                inflater.inflate(R.layout.fragment_tab_academias, container, false);

        cLayout.findViewById(R.id.academias_coordinator);

        inicializaComponentes();
        Firebase.setAndroidContext(getActivity().getApplicationContext());
        llenarList();

        adaptador = new AcademiaAdapter(getActivity().getApplicationContext(), DataSource.ACADEMIAS);
        lista.setAdapter(adaptador);

        return cLayout;
    }

    private void inicializaComponentes(){
        lista = (ListView)cLayout.findViewById(R.id.lista);
        firebaseControl = new FirebaseControl();
    }

    private void llenarList(){
        final ArrayList<Academia> academiaList = new ArrayList<Academia>();
        final Firebase ref = new Firebase("https://keep-moving-data.firebaseio.com/");
        ref.limit(10).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child: dataSnapshot.getChildren()) {
                    String academiaNombre = (String) child.child("academia").getValue();

                    String academiaImagen = (String) child.child("academia").getValue();
                    byte[] decodedString = Base64.decode(academiaImagen, Base64.URL_SAFE);
                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

                    DataSource.ACADEMIAS.add(new Academia(academiaNombre, decodedByte));
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

    }


}
