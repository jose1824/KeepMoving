package com.example.shipp.keepmoving.ClasesViews;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;

import com.example.shipp.keepmoving.Clases.Usuario;
import com.example.shipp.keepmoving.ClasesFirebase.FirebaseControl;
import com.example.shipp.keepmoving.ClasesValidaciones.ValidacionesLogin;
import com.example.shipp.keepmoving.ClasesValidaciones.ValidacionesNuevoUsuario;
import com.example.shipp.keepmoving.R;
import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;
import java.util.Map;

public class PantallaCrearCuentaPersonal extends AppCompatActivity {
    private TextInputLayout txtNombreCompleto;
    private TextInputLayout txtNombreUsuario;
    private TextInputLayout txtEmail;
    private TextInputLayout txtPassword;
    private TextInputLayout txtConfPassword;
    private ImageView imgUsuario;
    private DatePicker dateFechaNac;
    private final static int SELECT_PHOTO = 12345;
    CoordinatorLayout coordinatorLayout;
    FirebaseControl firebaseControl;
    FloatingActionButton fab;

    String imagenBase64;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_crear_cuenta_personal);
        incializaComponentes();
        Firebase.setAndroidContext(this);

        //Inicializacion de la toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.crear_cuenta_personal));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), PantallaEleccionUsuario.class);
                startActivity(i);
                finish();
            }
        });//End toolbar listener

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                seleccionarFoto();
            }
        });
        //Poner fecha de hoy
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        dateFechaNac.init(year, month, day, null);
        //La fecha esta comprendida entre 5 años menos de la actual a 100 años mas
        c.add(Calendar.DATE, -1825);
        dateFechaNac.setMinDate(c.getTimeInMillis());
        c.add(Calendar.DATE, 36500);
        dateFechaNac.setMaxDate(c.getTimeInMillis());
    }//Ennd on create

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
            mandarUsuario();
            return true;
        }//end if

        return super.onOptionsItemSelected(item);
    }//End on options

    private void incializaComponentes(){
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.pantalla_CuentaPersonal_coordinator);
        txtNombreCompleto = (TextInputLayout) findViewById(R.id.personal_et_1);
        txtNombreUsuario = (TextInputLayout) findViewById(R.id.personal_et_2);
        txtEmail = (TextInputLayout) findViewById(R.id.personal_et_3);
        txtPassword = (TextInputLayout) findViewById(R.id.personal_et_4);
        txtConfPassword = (TextInputLayout) findViewById(R.id.personal_et_5);
        imgUsuario = (ImageView) findViewById(R.id.personal_imagen_perfil);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        dateFechaNac = (DatePicker) findViewById(R.id.personal_date);
        firebaseControl = new FirebaseControl();
    }//End inicializa componentes

    public void mandarUsuario(){
        //Instancia para acceder a los atroibutos del usuario
         final Usuario user = new Usuario(false,
                txtNombreCompleto.getEditText().getText().toString().trim(),
                txtNombreUsuario.getEditText().getText().toString().trim(),
                txtEmail.getEditText().getText().toString().trim(),
                txtPassword.getEditText().getText().toString(),
                 imagenBase64);

        final String confPassword = txtConfPassword.getEditText().getText().toString();
        final Firebase ref = new Firebase("https://keep-moving-data.firebaseio.com/");

        //Instancia para acceder a las validaciones propias de los campos
        ValidacionesNuevoUsuario validacionesUsuario = new ValidacionesNuevoUsuario();

        //Esta instancia es usada porque las validaciones ya estan hechas en esta clase
        ValidacionesLogin valLogin = new ValidacionesLogin();

        //El nombre completo solo acepta letras mayusculas y minusculas con acentos
        //En un rango de 4 a 100 caracteres
        if (validacionesUsuario.validacionNombreCompleto(user.getNombreUsuario()) &&
                validacionesUsuario.validacionNombreUsuario(user.getAliasUsuario()) &&
                valLogin.validacionEmail(user.getEmailUsuario()) &&
                valLogin.validacionContrasena(user.getPasswordUsuario()) &&
                user.getPasswordUsuario().equals(confPassword)){


            ClaseAsyncTask asyncTask = new ClaseAsyncTask(getResources().getString(R.string.java_progress_titleCrear),
                    getResources().getString(R.string.java_progress_message),
                    ref,
                    user,
                    user.getEmailUsuario(),
                    user.getPasswordUsuario());
            asyncTask.execute();

            /*
            ref.createUser(user.getEmailUsuario(),
                    user.getPasswordUsuario(), new Firebase.ValueResultHandler<Map<String, Object>>() {
                        @Override
                        public void onSuccess(Map<String, Object> result) {

                            Firebase usuario = ref.child("usuarios").child(result.get("uid") + "");
                            usuario.setValue(user);
                            Snackbar.make(coordinatorLayout, R.string.java_bien_snack,
                                    Snackbar.LENGTH_SHORT).show();

                            startActivity(new Intent(getApplicationContext(), PantallaTabsUsuario.class));
                        }

                        @Override
                        public void onError(FirebaseError firebaseError) {
                            switch(firebaseError.getCode()){
                                case FirebaseError.UNKNOWN_ERROR:
                                    Snackbar.make(coordinatorLayout, R.string.error_unknown,
                                            Snackbar.LENGTH_SHORT).show();
                                    break;
                                case FirebaseError.NETWORK_ERROR:
                                    Snackbar.make(coordinatorLayout, R.string.error_network,
                                            Snackbar.LENGTH_SHORT).show();
                                    break;
                                case FirebaseError.USER_CODE_EXCEPTION:
                                    Snackbar.make(coordinatorLayout, R.string.error_user,
                                            Snackbar.LENGTH_SHORT).show();
                                    break;
                                case FirebaseError.DISCONNECTED:
                                    Snackbar.make(coordinatorLayout, R.string.error_disconnected,
                                            Snackbar.LENGTH_SHORT).show();
                                    break;
                            }

                        }
                    });
                */
        }//End if Principal
        else{

            if (validacionesUsuario.validacionNombreCompleto(user.getNombreUsuario()) == false){

                limpiaNombreCompleto();

                txtNombreCompleto.setError("" + R.string.java_error_nombre);
                Snackbar.make(coordinatorLayout, R.string.java_nombre_rechazado_snack,
                        Snackbar.LENGTH_SHORT).show();
            }//End if nombre completo mal

            if(validacionesUsuario.validacionNombreUsuario(user.getAliasUsuario()) == false){

                limpiaNombreUsuario();

                txtNombreUsuario.setError("" + R.string.java_error_nombre);
                Snackbar.make(coordinatorLayout, R.string.java_nombre_usuario_rechazado_snack,
                        Snackbar.LENGTH_SHORT).show();
            }//End if nombre usuario mal

            if(valLogin.validacionEmail(user.getEmailUsuario()) == false){

                limpiaEmail();

                txtEmail.setError("" + R.string.java_error_email);
                Snackbar.make(coordinatorLayout, R.string.java_email_rechazado_snack,
                        Snackbar.LENGTH_SHORT).show();
            }//end if correo mal

            if(valLogin.validacionContrasena(user.getPasswordUsuario()) == false ||
                    valLogin.validacionContrasena(confPassword) == false){

                limpiaPassword();

                Snackbar.make(coordinatorLayout, R.string.java_contra_rechazada_snack,
                        Snackbar.LENGTH_SHORT).show();
            }//End if contraseña mal

            if(user.getPasswordUsuario().equals(confPassword) == false){

                limpiaPassword();

                Snackbar.make(coordinatorLayout, R.string.java_contra_diferente_snack,
                        Snackbar.LENGTH_SHORT).show();
            }//End if contraseñas no coinciden

        }//Emd else principal
    }//End mandarUsuario

    private void limpiaNombreCompleto(){
        txtNombreCompleto.getEditText().setText("");
    }

    private void limpiaNombreUsuario(){
        txtNombreUsuario.getEditText().setText("");
    }

    private void limpiaEmail(){
        txtEmail.getEditText().setText("");
    }

    private void limpiaPassword(){
        txtPassword.getEditText().setText("");
        txtConfPassword.getEditText().setText("");
    }

    private void seleccionarFoto(){
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, SELECT_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Here we need to check if the activity that was triggers was the Image Gallery.
        // If it is the requestCode will match the LOAD_IMAGE_RESULTS value.
        // If the resultCode is RESULT_OK and there is some data we know that an image was picked.
        if (requestCode == SELECT_PHOTO && resultCode == RESULT_OK && data != null) {
            // Let's read picked image data - its URI
            Uri pickedImage = data.getData();
            // Let's read picked image path using content resolver
            String[] filePath = { MediaStore.Images.Media.DATA };
            Cursor cursor = getContentResolver().query(pickedImage, filePath, null, null, null);
            cursor.moveToFirst();
            String imagePath = cursor.getString(cursor.getColumnIndex(filePath[0]));

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath, options);
            imgUsuario.setImageBitmap(bitmap);

            // Do something with the bitmap
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream .toByteArray();

            imagenBase64 = Base64.encodeToString(byteArray, Base64.DEFAULT);

            // At the end remember to close the cursor or you will end with the RuntimeException!
            cursor.close();
        }
    }

    class ClaseAsyncTask extends AsyncTask {
        ProgressDialog pDialog;
        private String progressTitle;
        private String progressMessage;
        private Firebase ref;
        private Usuario user;
        private String correo_electronico;
        private String password;

        public ClaseAsyncTask(String progressTitle, String progressMessage, Firebase ref,
                              Usuario user, String correo_electronico, String password) {
            this.progressTitle = progressTitle;
            this.progressMessage = progressMessage;
            this.ref = ref;
            this.user = user;
            this.correo_electronico = correo_electronico;
            this.password = password;
        }

        @Override
        protected Object doInBackground(Object[] params) {
            ref.createUser(correo_electronico,
                    password, new Firebase.ValueResultHandler<Map<String, Object>>() {
                        @Override
                        public void onSuccess(Map<String, Object> result) {

                            Firebase usuario = ref.child("usuarios").child(result.get("uid") + "");
                            usuario.setValue(user);
                            Snackbar.make(coordinatorLayout, R.string.java_bien_snack,
                                    Snackbar.LENGTH_SHORT).show();

                            startActivity(new Intent(getApplicationContext(), PantallaTabsUsuario.class));
                            finish();
                        }

                        @Override
                        public void onError(FirebaseError firebaseError) {
                            switch(firebaseError.getCode()){
                                case FirebaseError.UNKNOWN_ERROR:
                                    Snackbar.make(coordinatorLayout, R.string.error_unknown,
                                            Snackbar.LENGTH_SHORT).show();
                                    break;
                                case FirebaseError.NETWORK_ERROR:
                                    Snackbar.make(coordinatorLayout, R.string.error_network,
                                            Snackbar.LENGTH_SHORT).show();
                                    break;
                                case FirebaseError.USER_CODE_EXCEPTION:
                                    Snackbar.make(coordinatorLayout, R.string.error_user,
                                            Snackbar.LENGTH_SHORT).show();
                                    break;
                                case FirebaseError.DISCONNECTED:
                                    Snackbar.make(coordinatorLayout, R.string.error_disconnected,
                                            Snackbar.LENGTH_SHORT).show();
                                    break;
                            }

                        }
                    });

            return null;
        }

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            pDialog = new ProgressDialog(PantallaCrearCuentaPersonal.this);
            pDialog.setTitle(progressTitle);
            pDialog.setMessage(progressMessage);
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

    }


}
