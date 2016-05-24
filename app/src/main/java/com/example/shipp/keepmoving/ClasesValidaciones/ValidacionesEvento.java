package com.example.shipp.keepmoving.ClasesValidaciones;

import com.example.shipp.keepmoving.Clases.ExpresionesRegulares;

/**
 * Created by xubudesktop1 on 23/05/16.
 */
public class ValidacionesEvento {

    private ExpresionesRegulares expresionesRegulares = new ExpresionesRegulares();

    public ValidacionesEvento() {
    }

    public boolean validarTitulo(String titulo){
        boolean respuesta;

        if (titulo.matches(expresionesRegulares.getExpresionEvento())){
            respuesta = true;
        }
        else { respuesta = false; }

        return respuesta;
    }
}
