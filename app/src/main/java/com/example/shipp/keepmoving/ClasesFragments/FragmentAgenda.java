package com.example.shipp.keepmoving.ClasesFragments;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
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
    List<Evento> result = new ArrayList<Evento>();

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

        AgendaAdapter ea = new AgendaAdapter(createList());
        recList.setAdapter(ea);

        return cLayout;

    }

    private void inicializaComponentes(){
        fab = (FloatingActionButton) cLayout.findViewById(R.id.fab);
        recList = (RecyclerView) cLayout.findViewById(R.id.recyclerViewAgenda);
    }//End inicializaComponentes

    //TYiene los mismos atributos de evento
    private List<Evento> createList() {
        System.out.println("aqui va");

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
        return result;
    }//end lisst
/*
    public String convertirMes(int mes){
        String nombreMes = "";

        switch(mes) {
            case 1:
                nombreMes = getResources().getString(R.string.agenda_mes1);
                break;
            case 2:
                nombreMes = getResources().getString(R.string.agenda_mes2);
                break;
            case 3:
                nombreMes = getResources().getString(R.string.agenda_mes3);
                break;
            case 4:
                nombreMes = getResources().getString(R.string.agenda_mes4);
                break;
            case 5:
                nombreMes = getResources().getString(R.string.agenda_mes5);
                break;
            case 6:
                nombreMes = getResources().getString(R.string.agenda_mes6);
                break;
            case 7:
                nombreMes = getResources().getString(R.string.agenda_mes7);
                break;
            case 8:
                nombreMes = getResources().getString(R.string.agenda_mes8);
                break;
            case 9:
                nombreMes = getResources().getString(R.string.agenda_mes9);
                break;
            case 10:
                nombreMes = getResources().getString(R.string.agenda_mes10);
                break;
            case 11:
                nombreMes = getResources().getString(R.string.agenda_mes11);
                break;
            case 12:
                nombreMes = getResources().getString(R.string.agenda_mes12);
                break;
            default:
                nombreMes = getResources().getString(R.string.agenda_mes_novalido);
                break;
        }

        return nombreMes;
    }
*/

}
