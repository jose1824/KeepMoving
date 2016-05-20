package com.example.shipp.keepmoving.ClasesAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shipp.keepmoving.Clases.Tip;
import com.example.shipp.keepmoving.R;

/**
 * Created by xubudesktop1 on 18/05/16.
 */
public class AdapterTips extends BaseAdapter {
    private Context context;

    public AdapterTips(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return Tip.CONTENIDOS.length;
    }

    @Override
    public Object getItem(int position) {
        return Tip.CONTENIDOS[position];
    }

    @Override
    public long getItemId(int position) {
        return (long) position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.grid_item, parent, false);
        }

        ImageView imagenTip = (ImageView) convertView.findViewById(R.id.imagen_tip);
        TextView tituloTip = (TextView) convertView.findViewById(R.id.titulo_tip);

        final Tip item = (Tip) getItem(position);
        imagenTip.setImageResource(item.getIdImagen());
        tituloTip.setText(item.getTitulo());

        return convertView;
    }
}
