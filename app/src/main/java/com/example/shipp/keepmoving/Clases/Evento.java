package com.example.shipp.keepmoving.Clases;

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
    private int horaInicioHr;
    private int horaInicioMin;
    private int horaFinHr;
    private int horaFinMin;
    //Fecha del evento
    private int diaEvento;
    private int mesEvento;
    private int anioEvento;
    //Imagen base 64
    private String imagenEvento64;

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

    public static final String TITULO_PREFIX = "Titulo_";
    public static final String FECHA_PREFIX = "Fecha y Hora_";
    public static final String DESCRIPCION_PREFIX = "Descripción_";

}
