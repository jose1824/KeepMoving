package com.example.shipp.keepmoving.ClasesValidaciones;

import com.example.shipp.keepmoving.Clases.ExpresionesRegulares;

/**
 * Created by shipp on 3/21/2016.
 */
public class ValidacionesNuevoUsuario {

    private ExpresionesRegulares expresionesRegulares = new ExpresionesRegulares();

    public ValidacionesNuevoUsuario(){  }

    public boolean validacionNombreCompleto(String nombre){
        boolean respuesta;

        if (nombre.matches(expresionesRegulares.getExpresionNombreCompleto())){
            respuesta = true;
        }
        else{
            respuesta = false;
        }

        return respuesta;
    }

    public boolean validacionNombreUsuario(String nombre){
        boolean respuesta;

        if (nombre.matches(expresionesRegulares.getExpresionNombreUsuario())){
            respuesta = true;
        }
        else{
            respuesta = false;
        }

        return respuesta;
    }

}
