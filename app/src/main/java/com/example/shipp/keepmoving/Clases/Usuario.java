package com.example.shipp.keepmoving.Clases;

/**
 * Created by shipp on 3/21/2016.
 */
public class Usuario {
    private String usuarioUID;
    private boolean confAcademia;
    private String nombreUsuario;
    private String passwordUsuario;
    private String emailUsuario;
    private String aliasUsuario;
    private String imagenUsuario64;

    public Usuario(){

    }

    public Usuario(boolean confAcademia, String nombreUsuario, String passwordUsuario,
                   String emailUsuario, String aliasUsuario, String imagenUsuario64) {
        this.confAcademia = confAcademia;
        this.nombreUsuario = nombreUsuario;
        this.passwordUsuario = passwordUsuario;
        this.emailUsuario = emailUsuario;
        this.aliasUsuario = aliasUsuario;
        this.imagenUsuario64 = imagenUsuario64;
    }

    public String getUsuarioUID() {
        return usuarioUID;
    }

    public boolean isConfAcademia() {
        return confAcademia;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public String getPasswordUsuario() {
        return passwordUsuario;
    }

    public String getEmailUsuario() {
        return emailUsuario;
    }

    public String getAliasUsuario() {
        return aliasUsuario;
    }

    public String getImagenUsuario64() {
        return imagenUsuario64;
    }
}
