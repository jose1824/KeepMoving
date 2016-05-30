package com.example.shipp.keepmoving.Clases;

import android.graphics.Bitmap;

/**
 * Created by xubudesktop1 on 29/05/16.
 */
public class Agenda {

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

    public Agenda(String titulo, String direccionEvento, double longitudEvento,
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

    public Agenda(String titulo, int horaInicioHr, int horaInicioMin, int diaEvento, int mesEvento, String imagenEvento64) {
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

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDireccionEvento() {
        return direccionEvento;
    }

    public void setDireccionEvento(String direccionEvento) {
        this.direccionEvento = direccionEvento;
    }

    public double getLongitudEvento() {
        return longitudEvento;
    }

    public void setLongitudEvento(double longitudEvento) {
        this.longitudEvento = longitudEvento;
    }

    public double getLatitudEvento() {
        return latitudEvento;
    }

    public void setLatitudEvento(double latitudEvento) {
        this.latitudEvento = latitudEvento;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getHoraInicioHr() {
        return horaInicioHr;
    }

    public void setHoraInicioHr(int horaInicioHr) {
        this.horaInicioHr = horaInicioHr;
    }

    public int getHoraInicioMin() {
        return horaInicioMin;
    }

    public void setHoraInicioMin(int horaInicioMin) {
        this.horaInicioMin = horaInicioMin;
    }

    public int getHoraFinHr() {
        return horaFinHr;
    }

    public void setHoraFinHr(int horaFinHr) {
        this.horaFinHr = horaFinHr;
    }

    public int getHoraFinMin() {
        return horaFinMin;
    }

    public void setHoraFinMin(int horaFinMin) {
        this.horaFinMin = horaFinMin;
    }

    public int getDiaEvento() {
        return diaEvento;
    }

    public void setDiaEvento(int diaEvento) {
        this.diaEvento = diaEvento;
    }

    public int getMesEvento() {
        return mesEvento;
    }

    public void setMesEvento(int mesEvento) {
        this.mesEvento = mesEvento;
    }

    public int getAnioEvento() {
        return anioEvento;
    }

    public void setAnioEvento(int anioEvento) {
        this.anioEvento = anioEvento;
    }

    public String getImagenEvento64() {
        return imagenEvento64;
    }

    public void setImagenEvento64(String imagenEvento64) {
        this.imagenEvento64 = imagenEvento64;
    }
}
