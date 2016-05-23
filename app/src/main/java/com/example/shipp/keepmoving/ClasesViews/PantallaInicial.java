package com.example.shipp.keepmoving.ClasesViews;

import android.content.Intent;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.example.shipp.keepmoving.R;

public class PantallaInicial extends AppCompatActivity {
    private Button inicial_btn_1;
    private GestureDetectorCompat gestureDetectorCompat;

    ImageView imageView1;//PROVISIONAL

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
                startActivity(new Intent(getApplicationContext(), PantallaMainUsuario.class));
                finish();
            }
        });

    }

    private void inicializaComponentes(){
        inicial_btn_1 = (Button) findViewById(R.id.inicial_btn_1);
        gestureDetectorCompat = new GestureDetectorCompat(this, new MyGestureListener());
        imageView1 = (ImageView) findViewById(R.id.imageView1);//PROVISIONAL
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.gestureDetectorCompat.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    class MyGestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onFling(MotionEvent event1, MotionEvent event2, float velocidadX,
                               float velocidadY) {

            if (event2.getX() < event1.getX()) {
                startActivity(new Intent(getApplicationContext(), PantallaPrincipal.class));
            }

            return true;
        }
    }

}
