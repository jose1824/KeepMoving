package com.example.shipp.keepmoving.ClasesViews;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.example.shipp.keepmoving.R;

public class PantallaEleccionUsuario extends AppCompatActivity {
    private Button pantalla_eleccion_btn1;
    private Button pantalla_eleccion_btn2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_eleccion_usuario);

        //Inicializacion de la toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.crear_cuenta));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), PantallaPrincipal.class);
                startActivity(i);
                finish();
            }
        });

        incializaComponentes();

        pantalla_eleccion_btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), PantallaCrearCuentaPersonal.class));
            }
        });

        pantalla_eleccion_btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), PantallaCrearCuentaAcademia.class));
            }
        });

    }

    private void incializaComponentes(){
        pantalla_eleccion_btn1 = (Button) findViewById(R.id.pantalla_eleccion_btn1);
        pantalla_eleccion_btn2 = (Button) findViewById(R.id.pantalla_eleccion_btn2);
    }

}
