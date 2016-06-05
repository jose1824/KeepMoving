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

        EventoAdapter ea = new EventoAdapter(createList());
        recList.setAdapter(ea);


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


    private List<Evento> createList() {

        List<Evento> result = new ArrayList<Evento>();
        //for (int i = 1; i <= tamano; i++) {
            Evento ev = new Evento();
            ev.titulo = Evento.TITULO_PREFIX + "Retro party";
            ev.fechaHora = Evento.FECHA_PREFIX + "12 de Junio a las 16:00";
            ev.descripcion = Evento.DESCRIPCION_PREFIX + "Un evento chidogro.";
            String mDrawableName = "myappicon";
            //Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.evento1);
            //ev.imgEvento = largeIcon;
            ev.img = R.drawable.agenda1;

            result.add(ev);

            Evento ev1 = new Evento();
            ev1.titulo = Evento.TITULO_PREFIX + "Sunday Hot";
            ev1.fechaHora = Evento.FECHA_PREFIX + "29 de Junio a las 14:00";
            ev1.descripcion = Evento.DESCRIPCION_PREFIX + "Festejando el día internacional de la bachata.";
            //Bitmap largeIcon2 = BitmapFactory.decodeResource(getResources(), R.drawable.evento2);
            ev1.img = R.drawable.agenda2;

            result.add(ev1);

            Evento ev2 = new Evento();
            ev2.titulo = Evento.TITULO_PREFIX + "DNL";
            ev2.fechaHora = Evento.FECHA_PREFIX + "16 de Junio a las 18:00";
            ev2.descripcion = Evento.DESCRIPCION_PREFIX + "Competencia bachatera.";
            //Bitmap largeIcon3 = BitmapFactory.decodeResource(getResources(), R.drawable.evento3);
            //ev2.imgEvento = largeIcon3;
            ev2.img = R.drawable.agenda4;

            result.add(ev2);
        //}
        return result;
    }
}
        /*System.out.println("aqui va");
        final Firebase ref = new Firebase("https://keep-moving-data.firebaseio.com/eventototales");
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    final List<Evento> list = new ArrayList<Evento>();
                        for (DataSnapshot d: dataSnapshot.getChildren()){
                            String uIdEventosTotales = d.getKey();
                            System.out.println("Eventos totales:\t" + uIdEventosTotales);
                            final Firebase refEventosTotales = new Firebase("https://keep-moving-data.firebaseio.com/eventototales/" + uIdEventosTotales);
                            refEventosTotales.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    String titulo = (String) dataSnapshot.child("titulo").getValue();
                                    String descripcion = (String) dataSnapshot.child("descripcion").getValue();
                                    String diaEvento = String.valueOf(dataSnapshot.child("diaEvento").getValue());
                                    String mesEvento = String.valueOf(dataSnapshot.child("mesEvento").getValue());
                                    String anioEvento = String.valueOf(dataSnapshot.child("anioEvento").getValue());
                                    String horaInicioHr = String.valueOf(dataSnapshot.child("horaInicioHr").getValue());
                                    String horaInicioMin = String.valueOf(dataSnapshot.child("horaInicioMin").getValue());
                                    String horaFinHr = String.valueOf(dataSnapshot.child("horaFinHr").getValue());
                                    String horaFinMin = String.valueOf(dataSnapshot.child("horaFinMin").getValue());
                                    String imagenEvento64 = (String) dataSnapshot.child("imagenEvento64").getValue();

                                    System.out.println(titulo);
                                    System.out.println(descripcion);
                                    System.out.println(diaEvento);
                                    System.out.println(mesEvento);
                                    System.out.println(anioEvento);
                                    System.out.println(horaInicioHr);
                                    System.out.println(horaFinMin);

                                    //Evento ev = new Evento();
                                    String tituloE = Evento.TITULO_PREFIX + titulo;
                                    String fechaHoraE = Evento.FECHA_PREFIX + diaEvento + "/" + mesEvento + "/" +
                                            anioEvento + "\t" + horaInicioHr + ":" + horaInicioMin + " - " +
                                            horaFinHr + ":" + horaFinMin;
                                    String descripcionE = Evento.DESCRIPCION_PREFIX + descripcion;
                                    String imagenEvento64E = imagenEvento64;

                                    try {
                                        EventosDataSource.EVENTOS.add(new Evento(titulo, fechaHoraE, descripcion, imagenEvento64));
                                        list.add(new Evento(titulo, fechaHoraE, descripcion, imagenEvento64));
                                        System.out.println("Se añadio");
                                    }catch (Exception e){
                                        System.out.println("Excepcion");
                                        System.out.println(e);
                                    }


                                }

                                @Override
                                public void onCancelled(FirebaseError firebaseError) {

                                }
                            });

                        }
                }
                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });*/
    //end lisst

    /*private List<Evento> createListAcademia(int size) {
        final Firebase ref = new Firebase("https://keep-moving-data.firebaseio.com/eventos/" + uId);
        final List<Evento> result = new ArrayList<Evento>();
        //Snackbar.make(cLayout, "Estas en academias", Snackbar.LENGTH_SHORT).show();
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
    }*/

    /*private List<Evento> createList(int size) {
        final Firebase ref = new Firebase("https://keep-moving-data.firebaseio.com/eventos/");
        final List<Evento> result = new ArrayList<Evento>();
        //Snackbar.make(cLayout, "Estas en no academias", Snackbar.LENGTH_SHORT).show();
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

}*/
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
