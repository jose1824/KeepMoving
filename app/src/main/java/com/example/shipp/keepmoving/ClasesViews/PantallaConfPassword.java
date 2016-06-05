package com.example.shipp.keepmoving.ClasesViews;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shipp.keepmoving.ClasesFirebase.FirebaseControl;
import com.example.shipp.keepmoving.ClasesValidaciones.ValidacionesLogin;
import com.example.shipp.keepmoving.R;
import com.firebase.client.AuthData;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class PantallaConfPassword extends AppCompatActivity {
    private TextInputLayout txtPasswordActual;
    private TextInputLayout txtPasswordNueva;
    private TextInputLayout txtPasswordConf;
    private ImageView imgViewConfPass;
    private TextView txtViewConfPass;
    CoordinatorLayout coordinatorLayout;
    FirebaseControl firebaseControl;

    private String uId;
    private String email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_conf_password);

        Firebase.setAndroidContext(this);
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
                dialogoRegresar();
            }
        });//End toolbar listener
        inicializaComponentes();

        Firebase ref = new Firebase("https://keep-moving-data.firebaseio.com/");
        ref.addAuthStateListener(new Firebase.AuthStateListener() {
            @Override
            public void onAuthStateChanged(AuthData authData) {
                if (authData != null) {
                    uId = authData.getUid();
                    email = String.valueOf(authData.getProviderData().get("email"));
                    cargaInformacion(uId);
                } else {
                    Snackbar.make(coordinatorLayout, R.string.conf_in_usuario,
                            Snackbar.LENGTH_LONG).show();
                    Intent i = new Intent(getApplicationContext(), PantallaConfiguracion.class);
                    startActivity(i);
                    finish();
                }
            }
        });

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
            System.out.println("" + email + "" + uId);
            cambiarPassword(uId, email);
            return true;
        }//end if

        return super.onOptionsItemSelected(item);
    }//End on options

    private void inicializaComponentes(){
        txtPasswordActual = (TextInputLayout) findViewById(R.id.confPassword_et_1);
        txtPasswordNueva = (TextInputLayout) findViewById(R.id.confPassword_et_2);
        txtPasswordConf = (TextInputLayout) findViewById(R.id.confPassword_et_3);
        imgViewConfPass = (ImageView) findViewById(R.id.conImgPass);
        txtViewConfPass = (TextView) findViewById(R.id.confUsuarioNombrePass);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.pantalla_confPassword_coordinator);
    }//End inicializa componentes

    private void cambiarPassword(final String uId, String email){
        final String passActual = txtPasswordActual.getEditText().getText().toString();
        final String passNueva = txtPasswordNueva.getEditText().getText().toString();
        final String passConf = txtPasswordConf.getEditText().getText().toString();

        //Instancia para acceder a las validaciones propias de los campos
        ValidacionesLogin validacionesLogin = new ValidacionesLogin();

        if (validacionesLogin.validacionContrasena(passActual) &&
                validacionesLogin.validacionContrasena(passNueva) &&
                passNueva.equals(passConf)){

            Firebase ref = new Firebase("https://keep-moving-data.firebaseio.com/");
            ref.changePassword(email, passActual, passNueva, new Firebase.ResultHandler() {
                @Override
                public void onSuccess() {
                    dialogoActualizacion();
                    actualizaBranch(uId, passNueva);
                    limpiarCampos();
                }

                @Override
                public void onError(FirebaseError firebaseError) {
                    Snackbar.make(coordinatorLayout, R.string.java_error_firebase_snack,
                            Snackbar.LENGTH_LONG).show();
                }
            });
        }
        else{

            if (validacionesLogin.validacionContrasena(passActual) == false){
                limpiarCampos();
                txtPasswordActual.setError("La contrseña no es válida.");
                Snackbar.make(coordinatorLayout, R.string.java_contra_rechazada_snack,
                        Snackbar.LENGTH_LONG).show();
            }
            if (validacionesLogin.validacionContrasena(passNueva) == false){

                limpiarCampos();
                txtPasswordNueva.setError("La contrseña no es válida.");
                Snackbar.make(coordinatorLayout, R.string.java_contra_rechazada_snack,
                        Snackbar.LENGTH_LONG).show();
            }
            if (passNueva.equals(passConf) == false){

                limpiarCampos();
                Snackbar.make(coordinatorLayout, "Las contrseñas no coinciden.",
                        Snackbar.LENGTH_LONG).show();
            }
        }

    }//End cambia password

    private void limpiarCampos(){
        txtPasswordConf.getEditText().setText("");
        txtPasswordNueva.getEditText().setText("");
        txtPasswordActual.getEditText().setText("");
    }

    private void actualizaBranch(String uId, String pass){
        Firebase refUsuario = new Firebase("https://keep-moving-data.firebaseio.com/usuarios/" + uId);
        Map<String, Object> usuario = new HashMap<String, Object>();
        usuario.put("passwordUsuario", pass);
        refUsuario.updateChildren(usuario);
    }

    private void cargaInformacion(String uId){
        Firebase refUsuario = new Firebase("https://keep-moving-data.firebaseio.com/usuarios/" + uId);
        refUsuario.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String aliasUsuario = (String) dataSnapshot.child("aliasUsuario").getValue();
                String fotoUsuario = (String) dataSnapshot.child("imagenUsuario64").getValue();

                byte[] decodedString  = Base64.decode(fotoUsuario, Base64.DEFAULT);
                Bitmap decodedImage = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

                imgViewConfPass.setImageBitmap(decodedImage);
                txtViewConfPass.setText(aliasUsuario);
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    private void dialogoActualizacion(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage(getResources().getString(R.string.java_datos_bien_snack));
        builder.setTitle(getResources().getString(R.string.java_aviso));
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                recreate();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }

    private void dialogoRegresar(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage(getResources().getString(R.string.java_datos_confirma_salir_act));
        builder.setTitle(getResources().getString(R.string.java_aviso));
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent i = new Intent(getApplicationContext(), PantallaConfiguracion.class);
                startActivity(i);
                finish();
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, PantallaConfiguracion.class));
        finish();
    }

}
