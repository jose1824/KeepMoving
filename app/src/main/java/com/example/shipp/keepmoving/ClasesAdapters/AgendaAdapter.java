package com.example.shipp.keepmoving.ClasesAdapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.shipp.keepmoving.Clases.Evento;
import com.example.shipp.keepmoving.R;

import java.util.List;

/**
 * Created by xubudesktop1 on 19/05/16.
 */
public class AgendaAdapter extends RecyclerView.Adapter<AgendaAdapter.AgendaViewHolder> {

    private List<Evento> eventoList;

    public AgendaAdapter(List<Evento> eventoList) {
        this.eventoList = eventoList;
    }

    @Override
    public AgendaAdapter.AgendaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.cardview_agenda, parent, false);

        return new AgendaViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AgendaAdapter.AgendaViewHolder agendaViewHolder, int position) {
        Evento ci = eventoList.get(position);
        agendaViewHolder.vTitulo.setText(ci.titulo);
        agendaViewHolder.vFecha.setText(ci.fechaHora);
    }

    @Override
    public int getItemCount() {
        return eventoList.size();
    }

    public static class AgendaViewHolder extends RecyclerView.ViewHolder {

        protected TextView vTitulo;
        protected TextView vFecha;

        public AgendaViewHolder(View v) {
            super(v);
            vTitulo =  (TextView) v.findViewById(R.id.tv_titulo);
            vFecha = (TextView)  v.findViewById(R.id.tv_hora);
        }
    }

}
