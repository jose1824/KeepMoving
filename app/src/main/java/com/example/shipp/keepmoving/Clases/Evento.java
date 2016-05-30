package com.example.shipp.keepmoving.Clases;

import android.graphics.Bitmap;

/**
 * Created by shipp on 3/21/2016.
 */
public class Evento {


    public String titulo;
    //Direccion del evento
    public String direccionEvento;
    public double longitudEvento;
    public double latitudEvento;
    public String descripcion;
    //Hora y fin del evento
    public int horaInicioHr;
    public int horaInicioMin;
    private int horaFinHr;
    private int horaFinMin;
    //Fecha del evento
    public int diaEvento;
    public int mesEvento;
    private int anioEvento;
    //Imagen base 64
    public String imagenEvento64;
    public Bitmap imgEvento;

    public String fechaHora;

    public Evento() {
    }

    public Evento(String titulo, String direccionEvento, double longitudEvento,
                  double latitudEvento, String descripcion, int horaInicioHr,
                  int horaInicioMin, int horaFinHr, int horaFinMin, int diaEvento, int mesEvento,
                  int anioEvento, String imagenEvento64) {

        this.titulo = titulo;
        this.direccionEvento = direccionEvento;
        this.longitudEvento = longitudEvento;
        this.latitudEvento = latitudEvento;
        this.descripcion = descripcion;
        this.horaInicioHr = horaInicioHr;
        this.horaInicioMin = horaInicioMin;
        this.horaFinHr = horaFinHr;
        this.horaFinMin = horaFinMin;
        this.diaEvento = diaEvento;
        this.mesEvento = mesEvento;
        this.anioEvento = anioEvento;
        this.imagenEvento64 = imagenEvento64;
    }

    public Evento(String titulo, String fechaHora){
        this.titulo = titulo;
        this.fechaHora = fechaHora;
    }

    public Evento(String titulo, String fechaHora, String descripcion, String imagenEvento64){
        this.titulo = titulo;
        this.fechaHora = fechaHora;
        this.descripcion = descripcion;
        this.imagenEvento64 = imagenEvento64;
    }
    //Para agenda
    public Evento(String titulo, int horaInicioHr, int horaInicioMin, int diaEvento, int mesEvento, String imagenEvento64) {
        this.titulo = titulo;
        this.horaInicioHr = horaInicioHr;
        this.horaInicioMin = horaInicioMin;
        this.diaEvento = diaEvento;
        this.mesEvento = mesEvento;
        this.imagenEvento64 = imagenEvento64;
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

    public int getHoraInicioHr() {
        return horaInicioHr;
    }

    public int getHoraInicioMin() {
        return horaInicioMin;
    }

    public int getHoraFinHr() {
        return horaFinHr;
    }

    public int getHoraFinMin() {
        return horaFinMin;
    }

    public int getDiaEvento() {
        return diaEvento;
    }

    public int getMesEvento() {
        return mesEvento;
    }

    public int getAnioEvento() {
        return anioEvento;
    }

    public String getImagenEvento64() {
        return imagenEvento64;
    }

    public String getDireccionEvento() {
        return direccionEvento;
    }

    public double getLongitudEvento() {
        return longitudEvento;
    }

    public double getLatitudEvento() {
        return latitudEvento;
    }

    public Bitmap getImgenEvento() {
        return imgEvento;
    }

    public static final String TITULO_PREFIX = "Titulo_";
    public static final String FECHA_PREFIX = "Fecha y Hora_";
    public static final String DESCRIPCION_PREFIX = "Descripción_";
}
