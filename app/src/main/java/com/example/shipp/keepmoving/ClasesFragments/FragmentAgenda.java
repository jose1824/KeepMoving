package com.example.shipp.keepmoving.ClasesFragments;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.shipp.keepmoving.Clases.Evento;
import com.example.shipp.keepmoving.ClasesAdapters.AgendaAdapter;
import com.example.shipp.keepmoving.ClasesAdapters.EventoAdapter;
import com.example.shipp.keepmoving.R;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xubudesktop1 on 19/05/16.
 */
public class FragmentAgenda extends android.support.v4.app.Fragment {
    CoordinatorLayout cLayout;
    FloatingActionButton fab;
    RecyclerView recList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        cLayout = (CoordinatorLayout)
                inflater.inflate(R.layout.fragment_tab_agenda, container, false);

        cLayout.findViewById(R.id.agenda_coordinator);

        inicializaComponentes();

        //Iniciar recycler view
        recList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity().getApplicationContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);

        AgendaAdapter ea = new AgendaAdapter(crearLista());
        System.out.println(crearLista());
        recList.setAdapter(ea);

        return cLayout;

    }

    private void inicializaComponentes(){
        fab = (FloatingActionButton) cLayout.findViewById(R.id.fab);
        recList = (RecyclerView) cLayout.findViewById(R.id.recyclerViewAgenda);
    }//End inicializaComponentes

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
                                evento.imagenEvento64 = imagenEvento64;
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

    //TYiene los mismos atributos de evento
/*    private List<Evento> createList() {
        System.out.println("aqui va");


        List<Evento> result = new ArrayList<Evento>();
        //Agenda1
        Evento ev = new Evento();
        ev.titulo = "007 Salsa Party !!!";
        ev.horaInicioHr = 21;
        ev.horaInicioMin = 0;
        ev.diaEvento = 17;
        ev.mesEvento = 6;
        ev.imagen = R.drawable.agenda1;
        result.add(ev);

        //Agenda2
        Evento ev1 = new Evento();
        ev1.titulo = "Bachata Pro-AM";
        ev1.horaInicioHr = 18;
        ev1.horaInicioMin = 0;
        ev1.diaEvento = 1;
        ev1.mesEvento = 7;
        ev1.imagen = R.drawable.agenda2;
        result.add(ev1);

        //Agenda3
        Evento ev2 = new Evento();
        ev2.titulo = "Retro Party";
        ev2.horaInicioHr = 16;
        ev2.horaInicioMin = 0;
        ev2.diaEvento = 12;
        ev2.mesEvento = 6;
        ev2.imagen = R.drawable.agenda3;
        result.add(ev2);

        //Agenda4
        Evento ev3 = new Evento();
        ev3.titulo = "Sunday Hot";
        ev3.horaInicioHr = 16;
        ev3.horaInicioMin = 0;
        ev3.diaEvento = 29;
        ev3.mesEvento = 5;
        ev3.imagen = R.drawable.agenda4;
        result.add(ev3);
*/
        /*
        final Firebase ref = new Firebase("https://keep-moving-data.firebaseio.com/eventototales");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot d: dataSnapshot.getChildren()){
                    String uIdEventosTotales = d.getKey();
                    System.out.println("Eventos totales:\t" + uIdEventosTotales);
                    final Firebase refEventosTotales = new Firebase("https://keep-moving-data.firebaseio.com/eventototales/" + uIdEventosTotales);
                    try {
                        refEventosTotales.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                String titulo = (String) dataSnapshot.child("titulo").getValue();
                                int diaEvento = (int) dataSnapshot.child("diaEvento").getValue();
                                int mesEvento = (int) dataSnapshot.child("mesEvento").getValue();
                                int horaInicioHr = (int) dataSnapshot.child("horaInicioHr").getValue();
                                int horaInicioMin = (int) dataSnapshot.child("horaInicioMin").getValue();
                                String imagenEvento64 = (String) dataSnapshot.child("imagenEvento64").getValue();

                                System.out.println(titulo);
                                System.out.println(diaEvento);
                                System.out.println(horaInicioHr);

                                Evento ev = new Evento();
                                ev.titulo = titulo;
                                ev.diaEvento = diaEvento;
                                //ev.mesEvento = mesEvento;
                                ev.horaInicioHr = horaInicioHr;
                                ev.horaInicioMin = horaInicioMin;

                                ev.imagenEvento64 = imagenEvento64;

                                result.add(ev);

                            }

                            @Override
                            public void onCancelled(FirebaseError firebaseError) {

                            }
                        });
                    } catch (Exception ex){

                    }

                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        for (Evento e: result){
            System.out.println("e");
        }
*/
//        return result;
//    }//end lisst

}
