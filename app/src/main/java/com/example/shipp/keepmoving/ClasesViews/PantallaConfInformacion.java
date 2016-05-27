package com.example.shipp.keepmoving.ClasesViews;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.Settings;
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
import android.widget.ImageView;
import android.widget.Toast;

import com.example.shipp.keepmoving.ClasesFirebase.FirebaseControl;
import com.example.shipp.keepmoving.ClasesValidaciones.ValidacionesLogin;
import com.example.shipp.keepmoving.ClasesValidaciones.ValidacionesNuevoUsuario;
import com.example.shipp.keepmoving.R;
import com.firebase.client.AuthData;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class PantallaConfInformacion extends AppCompatActivity {
    private TextInputLayout txtNombreCompleto;
    private TextInputLayout txtNombreUsuario;
    private ImageView imgUsuario;
    private final static int SELECT_PHOTO = 12345;
    CoordinatorLayout coordinatorLayout;
    FirebaseControl firebaseControl;
    FloatingActionButton fab;
    String uId;
    String imagenBase64;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_conf_informacion);

        Firebase.setAndroidContext(this);

        //Inicializacion de la toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.conf_cambia));
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
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.pantalla_confInfo_coordinator);
        txtNombreCompleto = (TextInputLayout) findViewById(R.id.conf_info_et_1);
        txtNombreUsuario = (TextInputLayout) findViewById(R.id.conf_info_et_2);
        imgUsuario = (ImageView) findViewById(R.id.confUsuarioImageView);
        fab = (FloatingActionButton) findViewById(R.id.fab);
    }

    private void cargaInformacion(String uId){
        Firebase refUsuario = new Firebase("https://keep-moving-data.firebaseio.com/usuarios/" + uId);
        refUsuario.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String nombreUsuario = (String) dataSnapshot.child("nombreUsuario").getValue();
                String aliasUsuario = (String) dataSnapshot.child("aliasUsuario").getValue();
                String fotoUsuario = (String) dataSnapshot.child("imagenUsuario64").getValue();

                byte[] decodedString  = Base64.decode(fotoUsuario, Base64.DEFAULT);
                Bitmap decodedImage = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

                imgUsuario.setImageBitmap(decodedImage);

                txtNombreCompleto.getEditText().setText(nombreUsuario);
                txtNombreUsuario.getEditText().setText(aliasUsuario);
                imagenBase64 = fotoUsuario;
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    private void cambiarInformacion(){
        final String nombreCompleto = txtNombreCompleto.getEditText().getText().toString().trim();
        final String nombreUsuario = txtNombreUsuario.getEditText().getText().toString().trim();

        //Instancia para acceder a las validaciones propias de los campos
        ValidacionesNuevoUsuario validacionesUsuario = new ValidacionesNuevoUsuario();

        //Esta instancia es usada porque las validaciones ya estan hechas en esta clase
        ValidacionesLogin valLogin = new ValidacionesLogin();

        if (validacionesUsuario.validacionNombreCompleto(nombreCompleto) &&
                validacionesUsuario.validacionNombreUsuario(nombreUsuario) && imagenBase64 != null){

            Firebase refUsuario = new Firebase("https://keep-moving-data.firebaseio.com/usuarios/" + uId);
            Map<String, Object> usuario = new HashMap<String, Object>();
            usuario.put("nombreUsuario", nombreCompleto);
            usuario.put("aliasUsuario", nombreUsuario);
            usuario.put("imagenUsuario64", imagenBase64);
            refUsuario.updateChildren(usuario);
            dialogoActualizacion();

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
            if(imagenBase64 == null){
                Snackbar.make(coordinatorLayout, R.string.java_no_eligio_imagen,
                        Snackbar.LENGTH_LONG).show();
            }//End if nombre usuario mal
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
        try {
            // When an Image is picked
            if (requestCode == SELECT_PHOTO && resultCode == RESULT_OK
                    && null != data) {
                // Get the Image from data

                Uri selectedImage = data.getData();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };

                // Get the cursor
                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                // Move to first row
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String imgDecodableString = cursor.getString(columnIndex);
                cursor.close();
                // Set the Image in ImageView after decoding the String
                Bitmap bitmapBandera = BitmapFactory.decodeFile(imgDecodableString);
                int largoImagen = bitmapBandera.getHeight(), anchoImagen = bitmapBandera.getWidth();

                if (largoImagen > 1280 && anchoImagen > 960){
                    Snackbar.make(coordinatorLayout, getResources().getString(R.string.java_error_tamanio_imagen),
                            Snackbar.LENGTH_SHORT).show();
                }else {
                    imgUsuario.setImageBitmap(BitmapFactory
                            .decodeFile(imgDecodableString));

                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                    Bitmap bitmap = BitmapFactory.decodeFile(imgDecodableString, options);
                    imgUsuario.setImageBitmap(bitmap);

                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                    byte[] byteArray = byteArrayOutputStream.toByteArray();

                    imagenBase64 = Base64.encodeToString(byteArray, Base64.DEFAULT);
                }
            } else {
                Snackbar.make(coordinatorLayout,getResources().getString(R.string.java_error_imagen),
                        Snackbar.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Snackbar.make(coordinatorLayout, getResources().getString(R.string.java_no_eligio_imagen),
                    Snackbar.LENGTH_SHORT).show();
        }
    }//End if onActivityResult

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

}
