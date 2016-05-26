package com.example.shipp.keepmoving.ClasesFragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.shipp.keepmoving.Clases.Evento;
import com.example.shipp.keepmoving.ClasesAdapters.EventoAdapter;
import com.example.shipp.keepmoving.ClasesFirebase.FirebaseControl;
import com.example.shipp.keepmoving.ClasesViews.PantallaAgregarEvento;
import com.example.shipp.keepmoving.R;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xubudesktop1 on 18/05/16.
 */
public class FragmentEventos  extends android.support.v4.app.Fragment{

    private TextView navTextUsuario;
    private TextView navTextCorreo;
    FloatingActionButton fab;
    FirebaseControl firebaseControl;

    RecyclerView recList;
    CoordinatorLayout cLayout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        cLayout = (CoordinatorLayout)
                inflater.inflate(R.layout.fragment_tab_evento, container, false);//El content

        cLayout.findViewById(R.id.eventos_coordinator);

        inicializaComponentes();
        Firebase.setAndroidContext(getActivity().getApplicationContext());

        recList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity().getApplicationContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);

        EventoAdapter ea = new EventoAdapter(createList(30));
        recList.setAdapter(ea);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity().getApplicationContext(), PantallaAgregarEvento.class));
            }
        });

        return cLayout;
    }



    private void inicializaComponentes(){
        fab = (FloatingActionButton) cLayout.findViewById(R.id.fab);
        recList = (RecyclerView) cLayout.findViewById(R.id.recyclerView);
        firebaseControl = new FirebaseControl();
    }//End inicializaComponentes

    private List<Evento> createList(int size) {
        final Firebase ref = new Firebase("https://keep-moving-data.firebaseio.com/");
        List<Evento> result = new ArrayList<Evento>();
        ref.limit(10).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot child: dataSnapshot.getChildren()) {
                    Evento ev = new Evento();
                    ev.titulo = Evento.TITULO_PREFIX + child.child("evento").child("titulo").getValue();
                    ev.fechaHora = Evento.FECHA_PREFIX +
                            child.child("evento").child("diaEvento").getValue() + "/" +
                            child.child("evento").child("mesEvento").getValue() + "/" +
                            child.child("evento").child("anioEvento").getValue() + "\t" +
                            child.child("evento").child("horaInicioHr").getValue() + ":" +
                            child.child("evento").child("diaEvento").getValue() + " - " +
                            child.child("evento").child("diaEvento").getValue() + ":" +
                            child.child("evento").child("diaEvento").getValue();
                    ev.descripcion = Evento.DESCRIPCION_PREFIX + child.child("evento").child("descripcion").getValue();
                    result.add(ev); //ARREGLAR
                }

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
/*
        List<Evento> result = new ArrayList<Evento>();
        for (int i=1; i <= size; i++) {
            Evento ev = new Evento();
            ev.titulo = Evento.TITULO_PREFIX + i;
            ev.fechaHora = Evento.FECHA_PREFIX + i;
            ev.descripcion = Evento.DESCRIPCION_PREFIX + i;

            result.add(ev);

        }
*/
        return result;
    }//end lisst

}
