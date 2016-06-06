package com.example.shipp.keepmoving.ClasesFragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.shipp.keepmoving.Clases.DataSource;
import com.example.shipp.keepmoving.Clases.Evento;
import com.example.shipp.keepmoving.Clases.EventosDataSource;
import com.example.shipp.keepmoving.ClasesAdapters.EventoAdapter;
import com.example.shipp.keepmoving.ClasesFirebase.FirebaseControl;
import com.example.shipp.keepmoving.ClasesViews.PantallaAgregarEvento;
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
public class FragmentEventos  extends android.support.v4.app.Fragment {
    FloatingActionButton fab;
    FirebaseControl firebaseControl;

    RecyclerView recList;
    CoordinatorLayout cLayout;
    boolean confAcademia;
    //List<Evento> result = new ArrayList<Evento>();


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
        validarFloating();

        System.out.println("eventos moshos");

        //EventoAdapter ea = new EventoAdapter(crearLista());
        System.out.println("AQUIIIIIII ARRAY");
        //recList.setAdapter(ea);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (confAcademia == true)
                    startActivity(new Intent(getActivity().getApplicationContext(), PantallaAgregarEvento.class));
                else
                    Snackbar.make(cLayout, "No puedes crear eventos.", Snackbar.LENGTH_SHORT).show();
            }
        });

        for (Object e : EventosDataSource.EVENTOS) {
            System.out.println(e);
        }

        return cLayout;
    }

    private void inicializaComponentes() {
        fab = (FloatingActionButton) cLayout.findViewById(R.id.fab);
        recList = (RecyclerView) cLayout.findViewById(R.id.recyclerView);
        firebaseControl = new FirebaseControl();
    }//End inicializaComponentes

    private void validarFloating() {
        Firebase ref = new Firebase("https://keep-moving-data.firebaseio.com/");
        ref.addAuthStateListener(new Firebase.AuthStateListener() {
            @Override
            public void onAuthStateChanged(AuthData authData) {
                if (authData != null) {
                    String uId = authData.getUid();
                    comprobacionTipoUsuario(uId);

                }
            }
        });
    }

    private void comprobacionTipoUsuario(String uId) {
        Firebase ref = new Firebase("https://keep-moving-data.firebaseio.com/usuarios/" + uId);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                confAcademia = (boolean) dataSnapshot.child("confAcademia").getValue();
                System.out.println(confAcademia);
                if (!confAcademia) {
                    fab.hide();
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    public ArrayList<Evento> crearLista() {
        final ArrayList<Evento> result = new ArrayList<Evento>();//AQUI A VER
        final Firebase ref = new Firebase("https://keep-moving-data.firebaseio.com/eventototales");
        ref.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot d: dataSnapshot.getChildren()) {
                    String uIdEventosTotales = d.getKey();
                    System.out.println("Eventos totales:\t" + uIdEventosTotales);
                    final Firebase refEventosTotales = new Firebase("https://keep-moving-data.firebaseio.com/eventototales/" + uIdEventosTotales);
                    System.out.println(refEventosTotales);
                    try {
                        refEventosTotales.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                String titulo = (String) dataSnapshot.child("titulo").getValue();
                                Long diaEvento = (Long) dataSnapshot.child("diaEvento").getValue();
                                Long mesEvento = (Long) dataSnapshot.child("mesEvento").getValue();
                                Long horaInicioHr = (Long) dataSnapshot.child("horaInicioHr").getValue();
                                Long horaInicioMin = (Long) dataSnapshot.child("horaInicioMin").getValue();
                                String imagenEvento64 = (String) dataSnapshot.child("imagenEvento64").getValue();
                                System.out.println(titulo);
                                System.out.println(diaEvento + "");
                                System.out.println(mesEvento + "");
                                System.out.println(horaInicioHr + "");
                                System.out.println(horaInicioMin + "");

                                Evento evento = new Evento();
                                evento.titulo = titulo;
                                evento.diaEvento = (int) (long) diaEvento;
                                evento.mesEvento = (int) (long) mesEvento;
                                evento.horaInicioHr = (int) (long) horaInicioHr;
                                evento.horaInicioMin = (int) (long) horaInicioMin;
                                //evento.imagenEvento64 = imagenEvento64;
                                System.out.println("\n\t" + evento.titulo + "\t" + evento.horaInicioMin);

                                result.add(evento);
                                System.out.println(result);
                            }

                            @Override
                            public void onCancelled(FirebaseError firebaseError) {

                            }
                        });
                    } catch (Exception ex) {

                        System.out.println(ex.getMessage() + "\n" + ex.getCause() );
                    }
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });


        return result;
    }

}
