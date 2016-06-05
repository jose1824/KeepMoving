package com.example.shipp.keepmoving.ClasesViews;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.shipp.keepmoving.R;

public class PantallaConfiguracion extends AppCompatActivity {
    private Button btn_seccionExtras;
    private Button btn_seccionSeguridad;
    private Button btn_seccionGeneral;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_configuracion);
        inicializaComponentes();

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
                Intent i = new Intent(getApplicationContext(), PantallaTabsUsuario.class);
                startActivity(i);
                finish();

            }
        });//End toolbar listener

        btn_seccionGeneral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), PantallaConfInformacion.class));
            }
        });

        btn_seccionSeguridad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), PantallaConfPassword.class));
            }
        });

        btn_seccionExtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), PantallaAcercaDe.class));
            }
        });

    }

    private void inicializaComponentes(){
        btn_seccionGeneral = (Button) findViewById(R.id.conf_actualiza_informacion);
        btn_seccionSeguridad = (Button) findViewById(R.id.conf_cambia_contrasenia);
        btn_seccionExtras = (Button) findViewById(R.id.conf_acerca_nosotros);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, PantallaTabsUsuario.class));
        finish();
    }

}
