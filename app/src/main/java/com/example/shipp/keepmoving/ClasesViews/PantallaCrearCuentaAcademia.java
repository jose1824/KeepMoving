package com.example.shipp.keepmoving.ClasesViews;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
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
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.shipp.keepmoving.Clases.Academia;
import com.example.shipp.keepmoving.Clases.Usuario;
import com.example.shipp.keepmoving.ClasesFirebase.FirebaseControl;
import com.example.shipp.keepmoving.ClasesValidaciones.ValidacionesLogin;
import com.example.shipp.keepmoving.ClasesValidaciones.ValidacionesNuevaAcademia;
import com.example.shipp.keepmoving.ClasesValidaciones.ValidacionesNuevoUsuario;
import com.example.shipp.keepmoving.R;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.io.ByteArrayOutputStream;
import java.util.Map;

public class PantallaCrearCuentaAcademia extends AppCompatActivity {
    private TextInputLayout txtNombreAcademia;
    private TextInputLayout txtTelefonoAcademia;
    private TextInputLayout txtCorreoAcademia;
    private TextInputLayout txtDireccionAcademia;
    private TextInputLayout txtEncargadoAcademia;
    private TextInputLayout txtDescripcionAcademia;
    private TextInputLayout txtPaswwordAcademia;
    private TextInputLayout txtPasswordConfAcademia;
    private ImageButton btnObtenerDireccion;
    private ImageView imgAcademia;
    private final static int SELECT_PHOTO = 12345;
    CoordinatorLayout coordinatorLayout;
    FirebaseControl firebaseControl;
    FloatingActionButton fab;

    String imagenBase64;
    double latitudAcademia;
    double longitudAcademia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_crear_cuenta_academia);

        if (savedInstanceState != null){
            onRestoreInstanceState(savedInstanceState);
        }

        imagenBase64 = "";

        //Inicializacion de la toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.crear_cuenta_academia));
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
        inicializaComponentes();
        Firebase.setAndroidContext(this);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                seleccionarFoto();
            }
        });

        btnObtenerDireccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MapsActivity.class);
                i.putExtra("activityAnterior", "activityAcademia");
                startActivity(i);
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
            mandarAcademia();
            return true;
        }//end if

        return super.onOptionsItemSelected(item);
    }//End on options

    private void inicializaComponentes(){
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.pantalla_CuentaPersonal_coordinator);
        btnObtenerDireccion = (ImageButton) findViewById(R.id.btn_rastrear_direccion);
        txtNombreAcademia = (TextInputLayout) findViewById(R.id.academia_et_1);
        txtTelefonoAcademia = (TextInputLayout) findViewById(R.id.academia_et_2);
        txtCorreoAcademia = (TextInputLayout) findViewById(R.id.academia_et_3);

        txtDireccionAcademia = (TextInputLayout) findViewById(R.id.academia_et_4);
        MapsActivity map = new MapsActivity();
        if (map.getDireccion() != null || map.getDireccion() != "") {
            txtDireccionAcademia.getEditText().setText(map.getDireccion());
        }
        txtDireccionAcademia.getEditText().setOnKeyListener(null); //El Edit text no se podra editar pero si copiar y pegar su contenido
        txtDireccionAcademia.getEditText().setKeyListener(null);


        txtEncargadoAcademia = (TextInputLayout) findViewById(R.id.academia_et_5);
        txtDescripcionAcademia = (TextInputLayout) findViewById(R.id.academia_et_6);
        txtPaswwordAcademia = (TextInputLayout) findViewById(R.id.academia_et_7);
        txtPasswordConfAcademia = (TextInputLayout) findViewById(R.id.academia_et_8);
        imgAcademia = (ImageView) findViewById(R.id.academia_imagen_perfil);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        firebaseControl = new FirebaseControl();
    }//End inicializaComponentes

    private void mandarAcademia(){
        //Instancia para acceder a los atroibutos de la academia
        final Firebase ref = new Firebase("https://keep-moving-data.firebaseio.com/");
        Academia acad = new Academia(true,
                txtNombreAcademia.getEditText().getText().toString().trim(),
                txtCorreoAcademia.getEditText().getText().toString().trim(),
                txtTelefonoAcademia.getEditText().getText().toString().trim(),
                txtDireccionAcademia.getEditText().getText().toString().trim(),
                latitudAcademia,
                longitudAcademia,
                txtEncargadoAcademia.getEditText().getText().toString().trim(),
                txtDescripcionAcademia.getEditText().getText().toString().trim(),
                txtPaswwordAcademia.getEditText().getText().toString().trim(),
                imagenBase64);

        final String confPassword = txtPasswordConfAcademia.getEditText().getText().toString();

        //Instancia para acceder a las validaciones propias de los campos
        ValidacionesNuevaAcademia validacionesAcademia = new ValidacionesNuevaAcademia();

        //Esta instancia es usada porque las validaciones ya estan hechas en esta clase
        ValidacionesLogin valLogin = new ValidacionesLogin();

        //Instancia para acceder a las validaciones propias de los campos de nombre
        ValidacionesNuevoUsuario validacionesUsuario = new ValidacionesNuevoUsuario();

        if ( acad.getNombreAcademia().equals("")){
            txtNombreAcademia.setError("" + R.string.java_error_falta);
            Snackbar.make(coordinatorLayout, R.string.java_faltantes_snack,
                    Snackbar.LENGTH_SHORT).show();
        }
        if ( acad.getCorreoAcademia().equals("")){
            txtCorreoAcademia.setError("" + R.string.java_error_falta);
            Snackbar.make(coordinatorLayout, R.string.java_faltantes_snack,
                    Snackbar.LENGTH_SHORT).show();
        }
        if ( acad.getTelefonoAcademia().equals("")){
            txtNombreAcademia.setError("" + R.string.java_error_falta);
            Snackbar.make(coordinatorLayout, R.string.java_faltantes_snack,
                    Snackbar.LENGTH_SHORT).show();
        }
        if ( acad.getNombreAcademia().equals("")){
            txtNombreAcademia.setError("" + R.string.java_error_falta);
            Snackbar.make(coordinatorLayout, R.string.java_faltantes_snack,
                    Snackbar.LENGTH_SHORT).show();
        }


        if (validacionesUsuario.validacionNombreUsuario(acad.getNombreAcademia()) &&
                valLogin.validacionEmail(acad.getCorreoAcademia()) &&
                validacionesAcademia.validacionTelefono(acad.getTelefonoAcademia()) &&
                validacionesAcademia.validacionDireccion(acad.getDireccionAcademia()) &&
                validacionesUsuario.validacionNombreCompleto(acad.getEncargadoAcademia()) &&
                validacionesAcademia.validacionDescripcion(acad.getDescripcionAdademia()) &&
                valLogin.validacionContrasena(acad.getPasswordAcademia()) &&
                acad.getPasswordAcademia().equals(confPassword)){


            ClaseAsyncTask asyncTask = new ClaseAsyncTask(getResources().getString(R.string.java_progress_titleCrear),
                    getResources().getString(R.string.java_progress_message),
                    ref,
                    acad,
                    acad.getCorreoAcademia(),
                    acad.getPasswordAcademia());
            asyncTask.execute();

        }//End if principal
        else{

            if (validacionesUsuario.validacionNombreUsuario(acad.getNombreAcademia()) == false){

                limpiarNombreAcademia();
                txtNombreAcademia.setError("" + R.string.java_error_nombreacademia);
                Snackbar.make(coordinatorLayout, R.string.java_nombre_rechazado_snack,
                        Snackbar.LENGTH_SHORT).show();

            }//End if nombre mal

            if (valLogin.validacionEmail(acad.getCorreoAcademia()) == false){

                limpiarCorreoAcademia();
                txtCorreoAcademia.setError("" + R.string.java_error_email);
                Snackbar.make(coordinatorLayout, R.string.java_email_rechazado_snack,
                        Snackbar.LENGTH_SHORT).show();

            }//End if correo mal

            if (validacionesAcademia.validacionTelefono(acad.getTelefonoAcademia()) == false){

                limpiarTelefonoAcademia();
                txtTelefonoAcademia.setError("" + R.string.java_error_telefonoacademia);
                Snackbar.make(coordinatorLayout, R.string.java_telefono_snack,
                        Snackbar.LENGTH_SHORT).show();

            }//End if telefono mal

            if (validacionesAcademia.validacionDireccion(acad.getDireccionAcademia()) == false){

                limpiarDireccionAcademia();
                txtDireccionAcademia.setError(getResources().getString(R.string.java_error_direccionacademia));
                Snackbar.make(coordinatorLayout, R.string.java_direccion_snack,
                        Snackbar.LENGTH_SHORT).show();

            }//End if direccion mal

            if (validacionesUsuario.validacionNombreCompleto(acad.getEncargadoAcademia()) == false){

                limpiarDireccionAcademia();
                txtEncargadoAcademia.setError("" + R.string.java_error_encargadoacademia);
                Snackbar.make(coordinatorLayout, R.string.java_nombre_rechazado_snack,
                        Snackbar.LENGTH_SHORT).show();

            }//Nombre de encargado mal

            if (validacionesUsuario.validacionNombreCompleto(acad.getEncargadoAcademia()) == false){

                limpiarEncargadoAcademia();
                txtEncargadoAcademia.setError("" + R.string.java_error_encargadoacademia);
                Snackbar.make(coordinatorLayout, R.string.java_nombre_rechazado_snack,
                        Snackbar.LENGTH_SHORT).show();

            }//End if Nombre de encargado mal

            if (validacionesAcademia.validacionDescripcion(acad.getDescripcionAdademia()) == false){

                limpiarDescripcionAcademia();
                txtDescripcionAcademia.setError("" + R.string.java_error_descripcionacademia);
                Snackbar.make(coordinatorLayout, R.string.java_descripcion_snack,
                        Snackbar.LENGTH_SHORT).show();

            }//End if  descripcion mal

            if (valLogin.validacionContrasena(acad.getPasswordAcademia()) == false){

                limpiarPasswordAcademia();
                Snackbar.make(coordinatorLayout, R.string.java_contra_rechazada_snack,
                        Snackbar.LENGTH_SHORT).show();

            }//End if contraseña mal

            if (acad.getPasswordAcademia().equals(confPassword) == false){

                limpiarPasswordAcademia();
                Snackbar.make(coordinatorLayout, R.string.java_contra_diferente_snack,
                        Snackbar.LENGTH_SHORT).show();

            }//Contraseñas no coinciden

        }//End else principal

    }//End descripcion mal

    private void limpiarNombreAcademia(){
        txtNombreAcademia.getEditText().setText("");
    }

    private void limpiarCorreoAcademia(){
        txtCorreoAcademia.getEditText().setText("");
    }

    private void limpiarTelefonoAcademia(){
        txtTelefonoAcademia.getEditText().setText("");
    }

    private void limpiarDireccionAcademia(){
        txtDireccionAcademia.getEditText().setText("");
    }

    private void limpiarEncargadoAcademia(){
        txtEncargadoAcademia.getEditText().setText("");
    }

    private void limpiarDescripcionAcademia(){
        txtDescripcionAcademia.getEditText().setText("");
    }

    private void limpiarPasswordAcademia(){
        txtPaswwordAcademia.getEditText().setText("");
        txtPasswordConfAcademia.getEditText().setText("");
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
            imgAcademia.setImageBitmap(bitmap);

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream .toByteArray();

            imagenBase64 = Base64.encodeToString(byteArray, Base64.DEFAULT);

            // Do something with the bitmap


            // At the end remember to close the cursor or you will end with the RuntimeException!
            cursor.close();
        }
    }

    class ClaseAsyncTask extends AsyncTask {
        ProgressDialog pDialog;
        private String progressTitle;
        private String progressMessage;
        private Firebase ref;
        private Academia academia;
        private String correo_electronico;
        private String password;

        public ClaseAsyncTask(String progressTitle, String progressMessage, Firebase ref,
                              Academia academia, String correo_electronico, String password) {
            this.progressTitle = progressTitle;
            this.progressMessage = progressMessage;
            this.ref = ref;
            this.academia = academia;
            this.correo_electronico = correo_electronico;
            this.password = password;
        }

        @Override
        protected Object doInBackground(Object[] params) {
            ref.createUser(correo_electronico,
                    password, new Firebase.ValueResultHandler<Map<String, Object>>() {
                        @Override
                        public void onSuccess(Map<String, Object> result) {

                            Firebase usuario = ref.child("academias").child(result.get("uid") + "");
                            usuario.setValue(academia);
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
            pDialog = new ProgressDialog(PantallaCrearCuentaAcademia.this);
            pDialog.setTitle(progressTitle);
            pDialog.setMessage(progressMessage);
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

    }

}
