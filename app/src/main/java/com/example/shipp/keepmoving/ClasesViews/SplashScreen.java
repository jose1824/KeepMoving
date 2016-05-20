package com.example.shipp.keepmoving.ClasesViews;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.shipp.keepmoving.R;

public class SplashScreen extends AppCompatActivity {
    boolean mostartInternetDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        mostartInternetDialog = false;

        if (savedInstanceState != null){
            onRestoreInstanceState(savedInstanceState);
        }
        else{
            mostartInternetDialog= true;
        }

        validarInternet();

    }

    public void validarInternet(){

        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

            startActivity(new Intent(getApplicationContext(), PantallaInicial.class));
            finish();

        }//End if esta conectado
        else{

            if (mostartInternetDialog){
                AlertDialog.Builder builder = new AlertDialog.Builder(this);

                builder.setMessage(getResources().getString(R.string.splash_noInternet));
                builder.setTitle(getResources().getString(R.string.java_mal_snack));
                builder.setPositiveButton(getResources().getString(R.string.splash_retry), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        validarInternet();
                    }
                });
                builder.setNegativeButton(getResources().getString(R.string.splash_salir), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();
            }


        }//End else no esta conectado
    }//end validar internet


}
