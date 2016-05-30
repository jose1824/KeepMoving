package com.example.shipp.keepmoving.ClasesAdapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shipp.keepmoving.Clases.Evento;
import com.example.shipp.keepmoving.R;

import java.util.List;

/**
 * Created by xubudesktop1 on 8/04/16.
 */
public class EventoAdapter extends RecyclerView.Adapter<EventoAdapter.EventoViewHolder> {

    private List<Evento> eventoList;

    public EventoAdapter(List<Evento> eventoList) {
        this.eventoList = eventoList;
    }

    @Override
    public int getItemCount() {
        return eventoList.size();
    }

    @Override
    public void onBindViewHolder(EventoViewHolder eventoViewHolder, int i) {
        Evento ci = eventoList.get(i);
        eventoViewHolder.vTitulo.setText(ci.titulo);
        eventoViewHolder.vFecha.setText(ci.fechaHora);
        eventoViewHolder.vDescripcion.setText(ci.descripcion);
        //eventoViewHolder.vImagen.setImageResource(ci.getImgDraw());

        /*byte[] decodedString  = Base64.decode(ci.imagenEvento64, Base64.DEFAULT);
        Bitmap decodedImage = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);*/
        //eventoViewHolder.vImagen.setImageBitmap(ci.imgEvento);
        eventoViewHolder.vImagen.setImageResource(ci.img);
    }

    @Override
    public EventoViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.cardview_eventos, viewGroup, false);

        return new EventoViewHolder(itemView);
    }


    public static class EventoViewHolder extends RecyclerView.ViewHolder {

        protected TextView vTitulo;
        protected TextView vFecha;
        protected TextView vDescripcion;
        protected ImageView vImagen;

        public EventoViewHolder(View v) {
            super(v);
            vTitulo =  (TextView) v.findViewById(R.id.tv_titulo);
            vFecha = (TextView)  v.findViewById(R.id.tv_fechahora);
            vDescripcion = (TextView)  v.findViewById(R.id.tv_descripcion);
            vImagen = (ImageView) v.findViewById(R.id.imageViewCard);
        }
    }

}
