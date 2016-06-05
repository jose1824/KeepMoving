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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xubudesktop1 on 19/05/16.
 */
public class AgendaAdapter extends RecyclerView.Adapter<AgendaAdapter.AgendaViewHolder> {

    private ArrayList<Evento> eventoList;

    public AgendaAdapter(ArrayList<Evento> eventoList) {
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
        agendaViewHolder.vHora.setText(añadirCero(ci.horaInicioHr) + ":" + añadirCero(ci.horaInicioMin));
        agendaViewHolder.vDia.setText(añadirCero(ci.diaEvento) + "");
        agendaViewHolder.vMes.setText(convertirMes(ci.mesEvento));

        //byte[] decodedString  = Base64.decode(ci.ima, Base64.DEFAULT);
        //Bitmap decodedImage = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        agendaViewHolder.vImagen.setImageResource(ci.imagen);
    }

    @Override
    public int getItemCount() {
        return eventoList.size();
    }

    public static class AgendaViewHolder extends RecyclerView.ViewHolder {

        public TextView vTitulo;
        public TextView vHora;
        public TextView vDia;
        public TextView vMes;
        public ImageView vImagen;

        public AgendaViewHolder(View v) {
            super(v);
            vTitulo =  (TextView) v.findViewById(R.id.tv_titulo);
            vHora = (TextView)  v.findViewById(R.id.tv_hora);
            vDia = (TextView) v.findViewById(R.id.tv_dia);
            vMes = (TextView) v.findViewById(R.id.tv_mes);
            vImagen = (ImageView) v.findViewById(R.id.tv_imageViewAgenda);

        }
    }

    public String añadirCero(int num){
        String numero = "";
        if (num >= 0 && num < 10) {
            numero ="0" + num;
        }
        else {
            numero = num + "";
        }
        return numero;
    }

    public String convertirMes(int mes){
        String nombreMes = "";

        switch(mes) {
            case 1:
                nombreMes = "Ene";
                break;
            case 2:
                nombreMes = "Feb";
                break;
            case 3:
                nombreMes = "Mar";
                break;
            case 4:
                nombreMes = "Abr";
                break;
            case 5:
                nombreMes = "May";
                break;
            case 6:
                nombreMes = "Jun";
                break;
            case 7:
                nombreMes = "Jul";
                break;
            case 8:
                nombreMes = "Ago";
                break;
            case 9:
                nombreMes = "Sep";
                break;
            case 10:
                nombreMes = "Oct";
                break;
            case 11:
                nombreMes = "Nov";
                break;
            case 12:
                nombreMes = "Dic";
                break;
            default:
                nombreMes = "Mes no valido";
                break;
        }

        return nombreMes;
    }

}
