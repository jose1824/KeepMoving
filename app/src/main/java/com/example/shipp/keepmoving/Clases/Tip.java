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
            new Tip("nombre del Tip 1", R.mipmap.ic_add_black_24dp),
            new Tip("nombre del Tip 2", R.mipmap.ic_book_black_24dp),
            new Tip("nombre del Tip 3", R.mipmap.ic_account_circle_black_24dp),
            new Tip("jwsduiud", R.mipmap.ic_done_black_24dp)
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
