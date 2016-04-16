package com.example.shipp.keepmoving.ClasesViews;

import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.shipp.keepmoving.ClasesValidaciones.ValidacionesLogin;
import com.example.shipp.keepmoving.R;

public class PantallaConfPassword extends AppCompatActivity {
    private TextInputLayout txtPasswordActual;
    private TextInputLayout txtPasswordNueva;
    private TextInputLayout txtPasswordConf;
    CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_conf_password);

        //Inicializacion de la toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.configuracion));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), PantallaConfiguracion.class);
                startActivity(i);
                finish();
            }
        });//End toolbar listener
        inicializaComponentes();

    }//End on create

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_solo_palomita, menu);
        return true;
    }//End oncreate options

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //Al apretar el icono de hecho en toolbar
        if (id == R.id.action_aceptar) {
            cambiarPassword();
            return true;
        }//end if

        return super.onOptionsItemSelected(item);
    }//End on options

    private void inicializaComponentes(){
        txtPasswordActual = (TextInputLayout) findViewById(R.id.confPassword_et_1);
        txtPasswordNueva = (TextInputLayout) findViewById(R.id.confPassword_et_2);
        txtPasswordConf = (TextInputLayout) findViewById(R.id.confPassword_et_3);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.pantalla_confPassword_coordinator);
    }//End inicializa componentes

    private void cambiarPassword(){
        final String passActual = txtPasswordActual.getEditText().getText().toString();
        final String passNueva = txtPasswordNueva.getEditText().getText().toString();
        final String passConf = txtPasswordConf.getEditText().getText().toString();

        //Instancia para acceder a las validaciones propias de los campos
        ValidacionesLogin validacionesLogin = new ValidacionesLogin();

        if (validacionesLogin.validacionContrasena(passActual) &&
                validacionesLogin.validacionContrasena(passNueva) &&
                passNueva.equals(passConf)){

            Snackbar.make(coordinatorLayout, R.string.java_contra_cambiada_snack,
                    Snackbar.LENGTH_LONG).show();

        }
        else{

            if (validacionesLogin.validacionContrasena(passActual) == false){

                limpiarCampos();
                txtPasswordActual.setError("" + R.string.java_error_contra);
                Snackbar.make(coordinatorLayout, R.string.java_contra_rechazada_snack,
                        Snackbar.LENGTH_LONG).show();
            }
            if (validacionesLogin.validacionContrasena(passNueva) == false){

                limpiarCampos();
                txtPasswordNueva.setError("" + R.string.java_error_contra);
                Snackbar.make(coordinatorLayout, R.string.java_contra_rechazada_snack,
                        Snackbar.LENGTH_LONG).show();
            }
            if (passNueva.equals(passConf) == false){

                limpiarCampos();
                Snackbar.make(coordinatorLayout, R.string.java_contra_diferente_snack,
                        Snackbar.LENGTH_LONG).show();
            }
        }

    }//End cambia password

    private void limpiarCampos(){
        txtPasswordConf.getEditText().setText("");
        txtPasswordNueva.getEditText().setText("");
        txtPasswordActual.getEditText().setText("");
    }

}
