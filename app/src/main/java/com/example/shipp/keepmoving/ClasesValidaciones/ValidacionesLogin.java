package com.example.shipp.keepmoving.ClasesValidaciones;

import com.example.shipp.keepmoving.Clases.ExpresionesRegulares;

/**
 * Created by shipp on 3/21/2016.
 */

/*Se utiliza true si se cumple la validación, false si hay algún error*/
public class ValidacionesLogin {

    private ExpresionesRegulares expresionesRegulares = new ExpresionesRegulares();

    public ValidacionesLogin(){    }

    public boolean validacionEmail(String email){
        boolean respuesta;

        if (email.matches(expresionesRegulares.getExpresionEmail())){
            respuesta = true;
        }
        else { respuesta = false; }

        return respuesta;
    }

    public boolean validacionContrasena(String contrasena){
        boolean respuesta;

        if (contrasena.matches(expresionesRegulares.getExpresionPassword())){
            respuesta = true;
        }
        else { respuesta = false; }

        return respuesta;
    }

}
