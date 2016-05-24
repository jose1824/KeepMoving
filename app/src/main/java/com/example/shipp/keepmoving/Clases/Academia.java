package com.example.shipp.keepmoving.Clases;

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
    private int Imagen;

    public Academia() {
    }

    public Academia(boolean confAcademia, String nombreAcademia, String correoAcademia,
                    String telefonoAcademia, String direccionAcademia, String encargadoAcademia,
                    String descripcionAdademia, String passwordAcademia) {
        this.confAcademia = confAcademia;
        this.nombreAcademia = nombreAcademia;
        this.correoAcademia = correoAcademia;
        this.telefonoAcademia = telefonoAcademia;
        this.direccionAcademia = direccionAcademia;
        this.encargadoAcademia = encargadoAcademia;
        this.descripcionAdademia = descripcionAdademia;
        this.passwordAcademia = passwordAcademia;
    }

    public Academia(String nombreAcademia, int imagen) {
        this.nombreAcademia = nombreAcademia;
        Imagen = imagen;
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

    public int getImagen() {
        return Imagen;
    }
}
