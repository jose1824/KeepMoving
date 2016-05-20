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

        AgendaAdapter ea = new AgendaAdapter(createList(30));
        recList.setAdapter(ea);

        return cLayout;

    }

    private void inicializaComponentes(){
        fab = (FloatingActionButton) cLayout.findViewById(R.id.fab);
        recList = (RecyclerView) cLayout.findViewById(R.id.recyclerViewAgenda);
    }//End inicializaComponentes

    //TYiene los mismos atributos de evento
    private List<Evento> createList(int size) {

        List<Evento> result = new ArrayList<Evento>();
        for (int i=1; i <= size; i++) {
            Evento ev = new Evento();
            ev.titulo = Evento.TITULO_PREFIX + i;
            ev.fechaHora = Evento.FECHA_PREFIX + i;
            ev.descripcion = Evento.DESCRIPCION_PREFIX + i;

            result.add(ev);

        }

        return result;
    }//end lisst


}
