package com.example.shipp.keepmoving.Clases;

import android.graphics.Bitmap;

/**
 * Created by shipp on 3/21/2016.
 */
public class Academia {
    private String academiaUID;
    private boolean confAcademia;
    private String nombreAcademia;
    private String correoAcademia;
    private String telefonoAcademia;
    private String direccionAcademia;
    private String encargadoAcademia;
    private String descripcionAdademia;
    private String passwordAcademia;
    private String imagenAcademia64;
    private int imagen;
    private Bitmap imagenBitmap;

    public Academia() {
    }

    public Academia(boolean confAcademia, String nombreAcademia, String correoAcademia,
                    String telefonoAcademia, String direccionAcademia, String encargadoAcademia,
                    String descripcionAdademia, String passwordAcademia, String imagenAcademia64) {

        this.confAcademia = confAcademia;
        this.nombreAcademia = nombreAcademia;
        this.correoAcademia = correoAcademia;
        this.telefonoAcademia = telefonoAcademia;
        this.direccionAcademia = direccionAcademia;
        this.encargadoAcademia = encargadoAcademia;
        this.descripcionAdademia = descripcionAdademia;
        this.passwordAcademia = passwordAcademia;
        this.imagenAcademia64 = imagenAcademia64;
    }

    public Academia(String nombreAcademia, int imagen) {
        this.nombreAcademia = nombreAcademia;
        this.imagen = imagen;
    }

    public Academia(String nombreAcademia, Bitmap imagenBitmap) {
        this.nombreAcademia = nombreAcademia;
        this.imagenBitmap = imagenBitmap;
    }

    public Bitmap getImagenBitmap() {
        return imagenBitmap;
    }

    public void setImagenBitmap(Bitmap imagenBitmap) {
        this.imagenBitmap = imagenBitmap;
    }

    public String getAcademiaUID() {
        return academiaUID;
    }

    public boolean isConfAcademia() {
        return confAcademia;
    }

    public String getNombreAcademia() {
        return nombreAcademia;
    }

    public String getCorreoAcademia() {
        return correoAcademia;
    }

    public String getTelefonoAcademia() {
        return telefonoAcademia;
    }

    public String getDireccionAcademia() {
        return direccionAcademia;
    }

    public String getEncargadoAcademia() {
        return encargadoAcademia;
    }

    public String getDescripcionAdademia() {
        return descripcionAdademia;
    }

    public String getPasswordAcademia() {
        return passwordAcademia;
    }

    public String getImagenAcademia64() {
        return imagenAcademia64;
    }

    public int getImagen() {
        return imagen;
    }

    public void setAcademiaUID(String academiaUID) {
        this.academiaUID = academiaUID;
    }

    public void setConfAcademia(boolean confAcademia) {
        this.confAcademia = confAcademia;
    }

    public void setNombreAcademia(String nombreAcademia) {
        this.nombreAcademia = nombreAcademia;
    }

    public void setCorreoAcademia(String correoAcademia) {
        this.correoAcademia = correoAcademia;
    }

    public void setTelefonoAcademia(String telefonoAcademia) {
        this.telefonoAcademia = telefonoAcademia;
    }

    public void setDireccionAcademia(String direccionAcademia) {
        this.direccionAcademia = direccionAcademia;
    }

    public void setEncargadoAcademia(String encargadoAcademia) {
        this.encargadoAcademia = encargadoAcademia;
    }

    public void setDescripcionAdademia(String descripcionAdademia) {
        this.descripcionAdademia = descripcionAdademia;
    }

    public void setPasswordAcademia(String passwordAcademia) {
        this.passwordAcademia = passwordAcademia;
    }

    public void setImagenAcademia64(String imagenAcademia64) {
        this.imagenAcademia64 = imagenAcademia64;
    }

    public void setImagen(int imagen) {
        imagen = imagen;
    }
}
