package com.example.shipp.keepmoving.ClasesViews;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.example.shipp.keepmoving.R;

public class PantallaConfiguracion extends AppCompatActivity {
    private LinearLayout seccionExtras;
    private LinearLayout seccionSeguridad;
    private LinearLayout seccionGeneral;

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
                Intent i = new Intent(getApplicationContext(), PantallaMainUsuario.class);
                startActivity(i);
                finish();
            }
        });//End toolbar listener

        seccionGeneral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), PantallaConfInformacion.class));
            }
        });

        seccionSeguridad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), PantallaConfPassword.class));
            }
        });

        seccionExtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), PantallaAcercaDe.class));
            }
        });

    }

    private void inicializaComponentes(){
        seccionGeneral = (LinearLayout) findViewById(R.id.conf_seccion1);
        seccionSeguridad = (LinearLayout) findViewById(R.id.conf_seccion2);
        seccionExtras = (LinearLayout) findViewById(R.id.conf_seccion3);
    }
}
