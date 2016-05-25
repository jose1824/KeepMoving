package com.example.shipp.keepmoving.ClasesAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shipp.keepmoving.Clases.Academia;
import com.example.shipp.keepmoving.R;

import java.util.List;

/**
 * Created by xubudesktop1 on 17/05/16.
 */
public class AcademiaAdapter extends ArrayAdapter<Academia> {

    public AcademiaAdapter(Context context, List<Academia> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int posicion, View convertView, ViewGroup parent){

        LayoutInflater inflater = (LayoutInflater)getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View listItemView = convertView;

        if (null == convertView){
            listItemView = inflater.inflate(R.layout.item_list,
                    parent,
                    false);
        }

        Academia item = getItem(posicion);

        TextView titulo = (TextView) listItemView.findViewById(R.id.titulo_academia);
        ImageView imagen = (ImageView) listItemView.findViewById(R.id.imagen_academia);

        titulo.setText(item.getNombreAcademia());
        imagen.setImageBitmap(item.getImagenBitmap());

        return listItemView;
    }

}
