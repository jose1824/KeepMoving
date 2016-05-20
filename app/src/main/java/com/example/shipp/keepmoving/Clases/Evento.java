package com.example.shipp.keepmoving.Clases;

/**
 * Created by shipp on 3/21/2016.
 */
public class Evento {

    public Evento() {
    }

    public String titulo;
    public String fechaHora;
    public String descripcion;
    public int imagen;

    public Evento(String titulo, String fechaHora, String descripcion, int imagen) {
        this.titulo = titulo;
        this.fechaHora = fechaHora;
        this.descripcion = descripcion;
        this.imagen = imagen;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getFechaHora() {
        return fechaHora;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public int getImagen() {
        return imagen;
    }

    public static final String TITULO_PREFIX = "Titulo_";
    public static final String FECHA_PREFIX = "Fecha y Hora_";
    public static final String DESCRIPCION_PREFIX = "Descripción_";

}
