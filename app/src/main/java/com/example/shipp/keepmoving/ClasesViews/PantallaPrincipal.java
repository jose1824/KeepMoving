package com.example.shipp.keepmoving.ClasesViews;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.example.shipp.keepmoving.ClasesFirebase.FirebaseControl;
import com.example.shipp.keepmoving.ClasesValidaciones.ValidacionesLogin;
import com.example.shipp.keepmoving.R;
import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;


public class PantallaPrincipal extends AppCompatActivity {
    private TextInputLayout txtMail;
    private TextInputLayout txtPassword;
    private Button btnLogin;
    private Button btnRegistro;
    private Button btnReset;
    CoordinatorLayout coordinatorLayout;
    FirebaseControl firebaseControl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_principal);
        inicializaComponentes();
        Firebase.setAndroidContext(this);

        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), PantallaEleccionUsuario.class);
                startActivity(i);
                finish();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String correo_electronico = txtMail.getEditText().getText().toString().trim();
                final String password = txtPassword.getEditText().getText().toString();
                final Firebase ref = new Firebase(firebaseControl.obtieneUrlFirebase());

                //Instancia para acceder a las validaciones propias de los campos
                ValidacionesLogin validacionesLogin = new ValidacionesLogin();

                if (validacionesLogin.validacionEmail(correo_electronico) &&
                        validacionesLogin.validacionContrasena(password)){

                    ref.authWithPassword(correo_electronico, password, new Firebase.AuthResultHandler() {
                        @Override
                        public void onAuthenticated(AuthData authData) {
                            //System.out.println("User ID: " + authData.getUid() + ", Provider: " + authData.getProvider());
                            txtMail.setError(null);
                            txtPassword.setError(null);
                            startActivity(new Intent(getApplicationContext(), PantallaEvento.class));
                        }

                        @Override
                        public void onAuthenticationError(FirebaseError firebaseError) {
                            // there was an error
                            switch (firebaseError.getCode()) {
                                case FirebaseError.USER_DOES_NOT_EXIST:
                                    txtPassword.setError(null);
                                    txtMail.setError("El correo ingresado no está registrado.");
                                    limpiaCorreo();
                                    break;
                                case FirebaseError.INVALID_PASSWORD:
                                    txtMail.setError(null);
                                    txtPassword.setError("La contraseña es incorrecta");
                                    break;
                                default:
                                    limpiaCampos();
                                    break;
                            }
                        }
                    });

                }else{
                    if (validacionesLogin.validacionEmail(correo_electronico) == false
                            && validacionesLogin.validacionContrasena(password) == false){

                        limpiaCampos();

                        txtMail.setError("El correo ingresado no es válido.");
                        txtPassword.setError("La contraseña no es válida.");

                        Snackbar.make(coordinatorLayout, R.string.java_contra_rechazada_snack,
                                Snackbar.LENGTH_LONG).show();

                    }

                    if (validacionesLogin.validacionEmail(correo_electronico) == false
                            && validacionesLogin.validacionContrasena(password)){
                        limpiaCorreo();

                        txtMail.setError("El correo ingresado no es válido.");

                        txtPassword.setError(null);
                    }

                    if (validacionesLogin.validacionEmail(correo_electronico)
                            && validacionesLogin.validacionContrasena(password) == false){
                        limpiaPassword();

                        txtMail.setError(null);

                        txtPassword.setError("La contraseña no es válida.");

                        Snackbar.make(coordinatorLayout, R.string.java_contra_rechazada_snack,
                                Snackbar.LENGTH_LONG).show();
                    }
                }
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Firebase ref = new Firebase(firebaseControl.obtieneUrlFirebase());
                final String correoElectronico = txtMail.getEditText().getText().toString().trim();
                if (correoElectronico.equals(null) || correoElectronico.equals("")){
                    Snackbar.make(coordinatorLayout, R.string.java_reset_password_snack,
                            Snackbar.LENGTH_SHORT).show();
                    txtMail.setError("Ingresa tu Email.");
                    txtPassword.setError(null);
                }else{
                    ref.resetPassword(correoElectronico, new Firebase.ResultHandler() {
                        @Override
                        public void onSuccess() {
                            dialog(correoElectronico);
                        }

                        @Override
                        public void onError(FirebaseError firebaseError) {
                            switch (firebaseError.getCode()){
                                case FirebaseError.USER_DOES_NOT_EXIST:
                                    Snackbar.make(coordinatorLayout, R.string.java_correo_inexistente_snack,
                                            Snackbar.LENGTH_LONG).show();
                                    txtMail.setError("Ingrese una cuenta de correo registrada.");
                                    txtPassword.setError(null);
                                    break;
                                default:
                                    Snackbar.make(coordinatorLayout, R.string.java_error_firebase_snack,
                                            Snackbar.LENGTH_LONG).show();
                                    break;
                            }
                        }
                    });
                }
            }
        });
    }

    private void inicializaComponentes(){
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.principal_coordinator);
        txtMail = (TextInputLayout) findViewById(R.id.principal_et_1);
        txtPassword = (TextInputLayout) findViewById(R.id.principal_et_2);
        btnLogin = (Button) findViewById(R.id.principal_btn_1);
        btnRegistro = (Button) findViewById(R.id.principal_btn_2);
        btnReset = (Button) findViewById(R.id.principal_btn_3);
        firebaseControl = new FirebaseControl();
    }

    private void limpiaCampos(){
        txtMail.getEditText().setText("");
        txtPassword.getEditText().setText("");
    }

    private void limpiaCorreo(){
        txtMail.getEditText().setText("");
    }

    private void limpiaPassword(){
        txtPassword.getEditText().setText("");
    }

    private void dialog(String email){
        AlertDialog.Builder builder =
                new AlertDialog.Builder(this, R.style.AppCompatAlertDialogStyle);
        builder.setTitle(R.string.java_alert_title);
        builder.setMessage(R.string.java_alert_mensaje + ": "+ email+"");
        builder.setPositiveButton(R.string.java_alert_positivebutton, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        /*builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });*/
        builder.show();
    }

}
