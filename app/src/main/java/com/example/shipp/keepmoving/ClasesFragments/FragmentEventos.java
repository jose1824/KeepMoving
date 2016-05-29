package com.example.shipp.keepmoving.ClasesFragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
import com.example.shipp.keepmoving.ClasesViews.PantallaConfiguracionAcademia;
import com.example.shipp.keepmoving.ClasesViews.PantallaTabsAcademia;
import com.example.shipp.keepmoving.ClasesViews.PantallaTabsUsuario;
import com.example.shipp.keepmoving.R;
import com.firebase.client.AuthData;
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
    FloatingActionButton fab;
    FirebaseControl firebaseControl;

    RecyclerView recList;
    CoordinatorLayout cLayout;

    private String uId;
    boolean confAcademia;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        cLayout = (CoordinatorLayout)
                inflater.inflate(R.layout.fragment_tab_evento, container, false);//El content

        cLayout.findViewById(R.id.eventos_coordinator);

        inicializaComponentes();
        Firebase.setAndroidContext(getActivity().getApplicationContext());
        //validarFloating();

        recList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity().getApplicationContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);

        EventoAdapter ea;
        if (confAcademia) {
            ea = new EventoAdapter(createListAcademia(30));
            Snackbar.make(cLayout, "academia", Snackbar.LENGTH_SHORT).show();
            System.out.println("academia");
        }
        else {
            ea = new EventoAdapter(createList(30));
            Snackbar.make(cLayout, "no academia", Snackbar.LENGTH_SHORT).show();
            System.out.println("no academia");
        }
        System.out.println("\t\t\t\t" + uId + confAcademia);
        ea = new EventoAdapter(createListAcademia(30));
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

    /*private void validarFloating(){
        Firebase ref = new Firebase("https://keep-moving-data.firebaseio.com/");
        ref.addAuthStateListener(new Firebase.AuthStateListener() {
            @Override
            public void onAuthStateChanged(AuthData authData) {
                if (authData != null) {
                    uId = authData.getUid();
                    comprobacionTipoUsuario(uId);

                } else {
                    Snackbar.make(cLayout, R.string.conf_in_usuario,
                            Snackbar.LENGTH_LONG).show();
                    Intent i = new Intent(getActivity().getApplicationContext(), PantallaConfiguracionAcademia.class);
                    startActivity(i);
                    getActivity().finish();
                }
            }
        });
    }*/

    /*private void comprobacionTipoUsuario(String uId){
        Firebase ref = new Firebase(firebaseControl.obtieneUrlFirebase() + "usuarios/" + uId);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                confAcademia = (boolean) dataSnapshot.child("confAcademia").getValue();
                if (confAcademia){
                    fab.hide();
                }
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }*/

    private List<Evento> createListAcademia(int size) {
        final Firebase ref = new Firebase("https://keep-moving-data.firebaseio.com/eventos/" + uId);
        final List<Evento> result = new ArrayList<Evento>();
        Snackbar.make(cLayout, "Estas en academias", Snackbar.LENGTH_SHORT).show();
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()){
                    String titulo = (String) dataSnapshot.child("titulo").getValue();
                    String descripcion = (String) dataSnapshot.child("descripcion").getValue();
                    String diaEvento = (String) dataSnapshot.child("diaEvento").getValue();
                    String mesEvento = (String) dataSnapshot.child("mesEvento").getValue();
                    String anioEvento = (String) dataSnapshot.child("anioEvento").getValue();
                    String horaInicioHr = (String) dataSnapshot.child("horaInicioHr").getValue();
                    String horaInicioMin = (String) dataSnapshot.child("horaInicioMin").getValue();
                    String horaFinHr = (String) dataSnapshot.child("horaFinHr").getValue();
                    String horaFinMin = (String) dataSnapshot.child("horaFinMin").getValue();
                    Snackbar.make(cLayout, titulo, Snackbar.LENGTH_SHORT).show();
                    System.out.println("\t\t\t\t\t" + titulo);

                    Evento ev = new Evento();
                    ev.titulo = Evento.TITULO_PREFIX + titulo;
                    ev.fechaHora = Evento.FECHA_PREFIX + diaEvento + "/" + mesEvento + "/" +
                            anioEvento + "\t" + horaInicioHr + ":" + horaInicioMin + " - " +
                            horaFinHr + ":" + horaFinMin;
                    ev.descripcion = Evento.DESCRIPCION_PREFIX + descripcion;


                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        return result;
    }

    private List<Evento> createList(int size) {
        final Firebase ref = new Firebase("https://keep-moving-data.firebaseio.com/eventos/");
        final List<Evento> result = new ArrayList<Evento>();
        Snackbar.make(cLayout, "Estas en no academias", Snackbar.LENGTH_SHORT).show();
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()){
                    String titulo = (String) dataSnapshot.child("titulo").getValue();
                    String descripcion = (String) dataSnapshot.child("descripcion").getValue();
                    String diaEvento = (String) dataSnapshot.child("diaEvento").getValue();
                    String mesEvento = (String) dataSnapshot.child("mesEvento").getValue();
                    String anioEvento = (String) dataSnapshot.child("anioEvento").getValue();
                    String horaInicioHr = (String) dataSnapshot.child("horaInicioHr").getValue();
                    String horaInicioMin = (String) dataSnapshot.child("horaInicioMin").getValue();
                    String horaFinHr = (String) dataSnapshot.child("horaFinHr").getValue();
                    String horaFinMin = (String) dataSnapshot.child("horaFinMin").getValue();
                    Snackbar.make(cLayout, titulo, Snackbar.LENGTH_SHORT).show();

                    Evento ev = new Evento();
                    ev.titulo = Evento.TITULO_PREFIX + titulo;
                    ev.fechaHora = Evento.FECHA_PREFIX + diaEvento + "/" + mesEvento + "/" +
                            anioEvento + "\t" + horaInicioHr + ":" + horaInicioMin + " - " +
                            horaFinHr + ":" + horaFinMin;
                    ev.descripcion = Evento.DESCRIPCION_PREFIX + descripcion;


                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        return result;
    }//end lisst

}
/*
    final Firebase ref = new Firebase("https://keep-moving-data.firebaseio.com/");
    final List<Evento> result = new ArrayList<Evento>();
ref.limit(10).addListenerForSingleValueEvent(new ValueEventListener() {
@Override
public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot child: dataSnapshot.getChildren()) {
                    Evento ev = new Evento();
                    Evento evento = dataSnapshot.getValue(Evento.class);
                    System.out.println(child.child("evento").getChildrenCount());

                    ev.titulo = Evento.TITULO_PREFIX  + evento.getTitulo();
                    ev.fechaHora = Evento.FECHA_PREFIX + evento.getDiaEvento() + "/" +
                            evento.getMesEvento() + "/" + evento.getAnioEvento() + "\t" +
                            evento.getHoraInicioHr() + ":" + evento.getHoraInicioMin() + " - " +
                            evento.getHoraFinHr() + ":" + evento.getHoraFinMin();
                    ev.descripcion = Evento.DESCRIPCION_PREFIX + evento.getDescripcion();
                    result.add(ev);

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

                }

        }

@Override
public void onCancelled(FirebaseError firebaseError) {

        }
        });

        List<Evento> result = new ArrayList<Evento>();
        for (int i=1; i <= size; i++) {
            Evento ev = new Evento();
            ev.titulo = Evento.TITULO_PREFIX + i;
            ev.fechaHora = Evento.FECHA_PREFIX + i;
            ev.descripcion = Evento.DESCRIPCION_PREFIX + i;

            result.add(ev);

        }

*/