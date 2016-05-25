package com.example.shipp.keepmoving.Clases;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by xubudesktop1 on 24/05/16.
 */
public class Preferencia {

    private Activity contexto;

    public Preferencia() {
    }

    public Preferencia(Activity contexto){
        this.contexto = contexto;
    }

    public boolean escribePreferencia(String cve, String valor){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(contexto.getBaseContext());
        SharedPreferences.Editor editor = prefs.edit();

        try{
            editor.putString(cve, valor);
            editor.apply();
            return true;
        } catch(Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }



    public String obtenerPref(String clave){
        String fondo = "";
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(contexto.getBaseContext());
        try {
            fondo = settings.getString(clave, "");
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        return fondo;
    }

    public boolean validaLogin(String tipoUsuario){
        String prueba = "";
        boolean validar = false;
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(contexto.getBaseContext());
        try {
            prueba = settings.getString(tipoUsuario, "");
        } catch(Exception ex) {
            ex.printStackTrace();
            return validar;
        }
        if (prueba.equals("") == false)//Hay un usuario logueado
            validar = true;
        return validar;
    }

}

