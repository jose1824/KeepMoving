package com.example.shipp.keepmoving.ClasesViews;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.example.shipp.keepmoving.ClasesFirebase.FirebaseControl;
import com.example.shipp.keepmoving.ClasesValidaciones.ValidacionesLogin;
import com.example.shipp.keepmoving.ClasesValidaciones.ValidacionesNuevoUsuario;
import com.example.shipp.keepmoving.R;

public class PantallaConfInformacion extends AppCompatActivity {
    private TextInputLayout txtNombreCompleto;
    private TextInputLayout txtNombreUsuario;
    private TextInputLayout txtEmail;
    private ImageView imgUsuario;
    private final static int SELECT_PHOTO = 12345;
    CoordinatorLayout coordinatorLayout;
    FirebaseControl firebaseControl;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_conf_informacion);

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
                Intent i = new Intent(getApplicationContext(), PantallaConfiguracion.class);
                startActivity(i);
                finish();
            }
        });//End toolbar listener

        inicializaComponentes();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                seleccionarFoto();
            }
        });

    }//end on create

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
            cambiarInformacion();
            return true;
        }//end if

        return super.onOptionsItemSelected(item);
    }//End on options

    private void inicializaComponentes(){
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.pantalla_configuracion_coordinator);
        txtNombreCompleto = (TextInputLayout) findViewById(R.id.conf_info_et_1);
        txtNombreUsuario = (TextInputLayout) findViewById(R.id.conf_info_et_2);
        txtEmail = (TextInputLayout) findViewById(R.id.conf_info_et_3);
        imgUsuario = (ImageView) findViewById(R.id.personal_imagen_perfil);
        fab = (FloatingActionButton) findViewById(R.id.fab);
    }

    private void cambiarInformacion(){
        final String nombreCompleto = txtNombreCompleto.getEditText().getText().toString().trim();
        final String nombreUsuario = txtNombreUsuario.getEditText().getText().toString().trim();
        final String email = txtEmail.getEditText().getText().toString().trim();

        //Instancia para acceder a las validaciones propias de los campos
        ValidacionesNuevoUsuario validacionesUsuario = new ValidacionesNuevoUsuario();

        //Esta instancia es usada porque las validaciones ya estan hechas en esta clase
        ValidacionesLogin valLogin = new ValidacionesLogin();

        if (validacionesUsuario.validacionNombreCompleto(nombreCompleto) &&
                validacionesUsuario.validacionNombreUsuario(nombreUsuario) &&
                valLogin.validacionEmail(email)){

            Snackbar.make(coordinatorLayout, R.string.java_datos_bien_snack,
                    Snackbar.LENGTH_LONG).show();

        }//End if principal
        else{
            if (validacionesUsuario.validacionNombreCompleto(nombreCompleto) == false){
                Snackbar.make(coordinatorLayout, R.string.java_nombre_rechazado_snack,
                        Snackbar.LENGTH_LONG).show();
            }//End if nombre completo mal
            if(validacionesUsuario.validacionNombreUsuario(nombreUsuario) == false){
                Snackbar.make(coordinatorLayout, R.string.java_nombre_usuario_rechazado_snack,
                        Snackbar.LENGTH_LONG).show();
            }//End if nombre usuario mal
            if (valLogin.validacionEmail(email) == false){
                Snackbar.make(coordinatorLayout, R.string.java_email_rechazado_snack,
                        Snackbar.LENGTH_LONG).show();
            }//end if email mal
        }//End else principal

    }//End cambiar informacion

    private void seleccionarFoto(){
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, SELECT_PHOTO);
    }//End seleccionar foto

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

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

            cursor.close();
        }//end if
    }//End if onActivityResult



}
