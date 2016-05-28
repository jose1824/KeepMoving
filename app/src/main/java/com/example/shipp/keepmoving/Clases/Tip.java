package com.example.shipp.keepmoving.Clases;

import com.example.shipp.keepmoving.R;

/**
 * Created by xubudesktop1 on 18/05/16.
 */
public class Tip {
    private String titulo;
    private int idImagen;

    public Tip(String titulo, int idImagen) {
        this.titulo = titulo;
        this.idImagen = idImagen;
    }

    public int getIdImagen() {
        return idImagen;
    }

    public String getTitulo() {
        return titulo;
    }

    public int getId() {
        return titulo.hashCode();
    }

    //El diseño no lo marca dinamico así que aqui se agregarán las imagenes con sus titulos
    public static Tip[]  CONTENIDOS= {
            /*new Tip("Nuestro Tip # 1", R.drawable.t1),
            new Tip("Nuestro Tip # 2", R.drawable.t2),
            new Tip("Nuestro Tip # 3", R.drawable.t3),
            new Tip("Nuestro Tip # 4", R.drawable.t4),
            new Tip("Nuestro Tip # 5", R.drawable.t5),
            new Tip("Nuestro Tip # 6", R.drawable.t6),*/
    };

    //El item es la imagen con su titulo
    //Se obtiene el item con el id
    public static Tip getItem(int id){
        for (Tip item : CONTENIDOS){
            if (item.getId() == id){
                return item;
            }
        }
        return null;
    }

}
