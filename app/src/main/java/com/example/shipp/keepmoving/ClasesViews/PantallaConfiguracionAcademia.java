package com.example.shipp.keepmoving.ClasesViews;

import android.content.Intent;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.shipp.keepmoving.ClasesFirebase.FirebaseControl;
import com.example.shipp.keepmoving.R;
import com.firebase.client.Firebase;

public class PantallaConfiguracionAcademia extends AppCompatActivity {

    private Button btn_seccionExtras;
    private Button btn_seccionSeguridad;
    private Button btn_seccionGeneral;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_configuracion_academia);

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
                Intent i = new Intent(getApplicationContext(), PantallaTabsAcademia.class);
                startActivity(i);
                finish();
            }
        });//End toolbar listener

        btn_seccionGeneral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), PantallaConfInformacionAcademia.class));
            }
        });

        btn_seccionSeguridad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), PantallaConfPasswordAcademia.class));
            }
        });

        btn_seccionExtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), PantallaAcercaDe.class));
            }
        });

    }

    private void inicializaComponentes() {
        btn_seccionGeneral = (Button) findViewById(R.id.conf_actualiza_informacion_academia);
        btn_seccionSeguridad = (Button) findViewById(R.id.conf_cambia_contrasenia_academia);
        btn_seccionExtras = (Button) findViewById(R.id.conf_acerca_nosotros_academia);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, PantallaTabsAcademia.class));
        finish();
    }
}

