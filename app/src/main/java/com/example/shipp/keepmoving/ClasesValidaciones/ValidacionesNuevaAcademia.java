package com.example.shipp.keepmoving.ClasesValidaciones;

import com.example.shipp.keepmoving.Clases.ExpresionesRegulares;

/**
 * Created by shipp on 3/21/2016.
 */
public class ValidacionesNuevaAcademia {

    private ExpresionesRegulares expresionesRegulares = new ExpresionesRegulares();


    public ValidacionesNuevaAcademia() {
    }

    public boolean validacionTelefono(String telefono){
        boolean respuesta;

        if(telefono.matches(expresionesRegulares.getExpresionTelefono())){
            respuesta = true;
        }
        else {
            respuesta = false;
        }
        return respuesta;
    }

    public boolean validacionDireccion(String direccion){
        boolean respuesta;

        if(direccion.matches(expresionesRegulares.getExpresionDireccion())){
            respuesta = true;
        }
        else {
            respuesta = false;
        }
        return respuesta;
    }

    public boolean validacionDescripcion(String descripcion){
        boolean respuesta;

        if(descripcion.matches(expresionesRegulares.getExpresionDescripcion())){
            respuesta = true;
        }
        else {
            respuesta = false;
        }
        return respuesta;
    }

}
