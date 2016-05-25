package com.example.shipp.keepmoving.ClasesViews;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.shipp.keepmoving.R;

public class PantallaAcercaDe extends AppCompatActivity {
    int contadorSergio;
    int contadorJesus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_acerca_de);

        //Inicializacion de la toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Acerca de Nosotros");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), PantallaConfiguracion.class);//Cambiar
                startActivity(i);
                finish();
            }
        });//End toolbar listener

        //ESTA PROGRAMADO A LO WEY :3
        TextView mensajeSergio = (TextView) findViewById(R.id.textViewClic);
        TextView mensajeJesus = (TextView) findViewById(R.id.textViewClicJ);

        contadorSergio = 1;
        contadorJesus = 1;

        //5 clics y sale algo bonito
        mensajeSergio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (contadorSergio >= 5) {
                    dialog(getResources().getString(R.string.info_ana));
                    contadorSergio = 1;
                }
                else{
                    contadorSergio++;
                }
            }
        });

        mensajeJesus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (contadorJesus >= 7) {
                    dialog(getResources().getString(R.string.mensajeChucho));
                    contadorJesus = 1;
                }
                else{
                    contadorJesus++;
                }
            }
        });

    }

    public void dialog(String titulo) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage(titulo);
        builder.setPositiveButton("CERRAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
