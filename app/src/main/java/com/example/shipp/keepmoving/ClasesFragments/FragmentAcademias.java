package com.example.shipp.keepmoving.ClasesFragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.shipp.keepmoving.Clases.Academia;
import com.example.shipp.keepmoving.Clases.DataSourceUidAcademia;
import com.example.shipp.keepmoving.Clases.Evento;
import com.example.shipp.keepmoving.Clases.Tip;
import com.example.shipp.keepmoving.ClasesAdapters.AcademiaAdapter;
import com.example.shipp.keepmoving.Clases.DataSource;
import com.example.shipp.keepmoving.ClasesFirebase.FirebaseControl;
import com.example.shipp.keepmoving.ClasesViews.DetalleTips;
import com.example.shipp.keepmoving.ClasesViews.MapsActivity;
import com.example.shipp.keepmoving.R;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * Created by xubudesktop1 on 17/05/16.
 */
public class FragmentAcademias extends android.support.v4.app.Fragment {
    ListView lista;
    AcademiaAdapter adaptador;
    FirebaseControl firebaseControl;
    String prevOldestPostID;
    String oldestPostId;

    ArrayList<Academia> acas = new ArrayList<Academia>();

    CoordinatorLayout cLayout;

    Academia a;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        cLayout = (CoordinatorLayout)
                inflater.inflate(R.layout.fragment_tab_academias, container, false);

        cLayout.findViewById(R.id.academias_coordinator);

        inicializaComponentes();
        Firebase.setAndroidContext(getActivity().getApplicationContext());
        //llenarList();
        llenarList();

        adaptador = new AcademiaAdapter(getActivity().getApplicationContext(), DataSource.ACADEMIAS);


        lista.setAdapter(adaptador);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for (int i=0; i<DataSource.ACADEMIAS.size();i++){
                    if (position == i){
                        a = (Academia) DataSource.ACADEMIAS.get(i);
                    }
                }
                //Toast.makeText(getActivity().getApplicationContext(), "" + a.getLatitudAcademia(), Toast.LENGTH_LONG).show();
                Double longitud = a.getLongitudAcademia();
                Double latitud = a.getLatitudAcademia();
                String nombreAcademia = a.getNombreAcademia();

                Intent i = new Intent(getActivity().getApplicationContext(), MapsActivity.class);
                i.putExtra("nombre", nombreAcademia);
                i.putExtra("latitud", latitud);
                i.putExtra("longitud", longitud);
                startActivity(i);
            }
        });

        DataSource.ACADEMIAS.clear();
        DataSourceUidAcademia.UIDACADEMIAS.clear();
        return cLayout;
    }



    private void inicializaComponentes(){
        lista = (ListView)cLayout.findViewById(R.id.lista);
        firebaseControl = new FirebaseControl();
    }

    private void llenarList(){
        final ArrayList<Academia> academiaList = new ArrayList<Academia>();
        final Firebase ref = new Firebase("https://keep-moving-data.firebaseio.com/academias");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (final DataSnapshot data: dataSnapshot.getChildren()){
                    String uIdAcademia = data.getKey();
                    DataSourceUidAcademia.UIDACADEMIAS.add(uIdAcademia);

                    //Validacion para que no se imroima doble
                    for (int i = 0; i < DataSourceUidAcademia.UIDACADEMIAS.size(); i++) {
                        if (uIdAcademia.equals(DataSourceUidAcademia.UIDACADEMIAS.get(i))) {
                            Firebase refAcademia = new Firebase("https://keep-moving-data.firebaseio.com/academias/" + uIdAcademia);
                            refAcademia.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    String nombreAcademia = (String) dataSnapshot.child("nombreAcademia").getValue();
                                    String imagenAcademia64 = (String) dataSnapshot.child("imagenAcademia64").getValue();
                                    Double longitud = (Double) dataSnapshot.child("longitudAcademia").getValue();
                                    Double latitud = (Double) dataSnapshot.child("latitudAcademia").getValue();

                                    byte[] decodedString  = Base64.decode(imagenAcademia64, Base64.DEFAULT);
                                    Bitmap decodedImage = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

                                    DataSource.ACADEMIAS.add(new Academia(nombreAcademia, decodedImage, longitud, latitud));
                                }

                                @Override
                                public void onCancelled(FirebaseError firebaseError) {

                                }
                            });
                        }
                    }


                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }


}
