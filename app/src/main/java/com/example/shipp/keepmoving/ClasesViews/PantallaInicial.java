package com.example.shipp.keepmoving.ClasesViews;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.example.shipp.keepmoving.R;

public class PantallaInicial extends AppCompatActivity {
    private Button inicial_btn_1;

    ImageView imageView1; //PROVISONAL

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_inicial);

        //Ocultar status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Incializa los componentes de la activity junto con el listener
        inicializaComponentes();

        inicial_btn_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), PantallaEleccionUsuario.class));
                finish();
            }
        });
        //PROVISIONAL
        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), PantallaPrincipal.class));
                finish();
            }
        });

    }

    private void inicializaComponentes(){
        inicial_btn_1 = (Button) findViewById(R.id.inicial_btn_1);

        imageView1 = (ImageView) findViewById(R.id.imageView1); //PROVISIONAL
    }

}
