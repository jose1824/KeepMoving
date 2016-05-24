package com.example.shipp.keepmoving.Clases;

/**
 * Created by shipp on 3/21/2016.
 */
public class ExpresionesRegulares {

    private final String expresionNombreCompleto = "[A-Za-záéíóúÁÉÍÓÚ ]{4,100}";
    private final String expresionNombreUsuario = "[A-Za-z0-9 ]{4,16}";//fALTAN ACENTOS
    private final String expresionPassword = "[A-Za-z0-9]{4,16}";
    private final String expresionEmail = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+"
            + "(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@"
            + "(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+"
            + "[a-z0-9](?:[a-z0-9-]*[a-z0-9])?";
    private final String expresionUrl = "^(https?|ftp|file)://" +
            "[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
    private final String expresionTelefono = "\\d{8,14}";
    private final String expresionDireccion = "[A-Za-záéíóúÁÉÍÓÚ0-9#-/.]{4,100}";
    private final String expresionDescripcion = "[A-Za-záéíóúÁÉÍÓÚ0-9#.-_,;:¿?!¡$()]{4,100}";
    private final String expresionEvento = "[A-Za-záéíóúÁÉÍÓÚ0!?-9#.]{4,100}";

    public ExpresionesRegulares(){    }

    public String getExpresionNombreCompleto() { return expresionNombreCompleto; }

    public String getExpresionNombreUsuario(){
        return this.expresionNombreUsuario;
    }

    public String getExpresionPassword(){
        return this.expresionPassword;
    }

    public String getExpresionEmail(){
        return this.expresionEmail;
    }

    public String getExpresionUrl(){
        return this.expresionUrl;
    }

    public String getExpresionTelefono(){
        return this.expresionTelefono;
    }

    public String getExpresionDireccion() {
        return expresionDireccion;
    }

    public String getExpresionDescripcion() {
        return expresionDescripcion;
    }

    public String getExpresionEvento() {
        return expresionEvento;
    }
}
