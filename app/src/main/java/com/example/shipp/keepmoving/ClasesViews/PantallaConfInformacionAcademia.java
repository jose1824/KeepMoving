package com.example.shipp.keepmoving.ClasesViews;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
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
import com.example.shipp.keepmoving.ClasesFirebase.FirebaseControl;
import com.example.shipp.keepmoving.ClasesValidaciones.ValidacionesLogin;
import com.example.shipp.keepmoving.ClasesValidaciones.ValidacionesNuevaAcademia;
import com.example.shipp.keepmoving.ClasesValidaciones.ValidacionesNuevoUsuario;
import com.example.shipp.keepmoving.ClasesViews.PantallaEleccionUsuario;
import com.example.shipp.keepmoving.R;
import com.firebase.client.AuthData;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class PantallaConfInformacionAcademia extends AppCompatActivity {

    private TextInputLayout txtNombreAcademia;
    private TextInputLayout txtTelefonoAcademia;
    private TextInputLayout txtCorreoAcademia;
    private TextInputLayout txtDireccionAcademia;
    private TextInputLayout txtEncargadoAcademia;
    private TextInputLayout txtDescripcionAcademia;
    private ImageButton btnObtenerDireccion;
    private ImageView imgAcademia;
    private final static int SELECT_PHOTO = 12345;
    CoordinatorLayout coordinatorLayout;
    FirebaseControl firebaseControl;
    private FloatingActionButton fab;

    private LocationManager locManager;
    private LocationListener locListener;

    private String imagenBase64;
    private double latitudAcademia;
    private double longitudAcademia;
    private String direccion;

    private String uId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_conf_informacion_academia);

        if (savedInstanceState != null) {
            onRestoreInstanceState(savedInstanceState);
        }

        imagenBase64 = "";

        //Inicializacion de la toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Actualizar datos");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogoRegresar();
            }
        });//End toolbar listener
        Firebase.setAndroidContext(this);

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
                    Intent i = new Intent(getApplicationContext(), PantallaConfiguracionAcademia.class);
                    startActivity(i);
                    finish();
                }
            }
        });
        inicializaComponentes();
        //validarInternet();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                seleccionarFoto();
            }
        });

        btnObtenerDireccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent i = new Intent(getApplicationContext(), MapsActivity.class);
                i.putExtra("activityAnterior", "activityAcademia");
                startActivity(i);*/
                try {
                    comenzarLocalizacion();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void cargaInformacion(String uId){
        Firebase refUsuario = new Firebase("https://keep-moving-data.firebaseio.com/usuarios/" + uId);
        refUsuario.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String imagenAcademia64 = (String) dataSnapshot.child("imagenAcademia64").getValue();
                String nombreAcademia = (String) dataSnapshot.child("nombreAcademia").getValue();
                String telefonoAcademia = (String) dataSnapshot.child("telefonoAcademia").getValue();
                String correoAcademia = (String) dataSnapshot.child("correoAcademia").getValue();
                String direccionAcademia = (String) dataSnapshot.child("direccionAcademia").getValue();
                String encargadoAcademia = (String) dataSnapshot.child("encargadoAcademia").getValue();
                String descripcionAcademia = (String) dataSnapshot.child("descripcionAdademia").getValue();
                Double latitudAcademiaRec = (Double) dataSnapshot.child("latitudAcademia").getValue();
                Double longitudAcademiaRec = (Double) dataSnapshot.child("longitudAcademia").getValue();

                byte[] decodedString  = Base64.decode(imagenAcademia64, Base64.DEFAULT);
                Bitmap decodedImage = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

                imgAcademia.setImageBitmap(decodedImage);

                latitudAcademia = latitudAcademiaRec;
                longitudAcademia = longitudAcademiaRec;

                txtNombreAcademia.getEditText().setText(nombreAcademia);
                txtTelefonoAcademia.getEditText().setText(telefonoAcademia);
                txtCorreoAcademia.getEditText().setText(correoAcademia);
                txtDireccionAcademia.getEditText().setText(direccionAcademia);
                txtEncargadoAcademia.getEditText().setText(encargadoAcademia);
                txtDescripcionAcademia.getEditText().setText(descripcionAcademia);

                imagenBase64 = imagenAcademia64;
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    private void inicializaComponentes(){
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.pantalla_conf_CuentaAcademia_coordinator);
        btnObtenerDireccion = (ImageButton) findViewById(R.id.conf_btn_rastrear_direccion);
        txtNombreAcademia = (TextInputLayout) findViewById(R.id.conf_academia_et_1);
        txtTelefonoAcademia = (TextInputLayout) findViewById(R.id.conf_academia_et_2);
        txtCorreoAcademia = (TextInputLayout) findViewById(R.id.conf_academia_et_3);

        txtDireccionAcademia = (TextInputLayout) findViewById(R.id.conf_academia_et_4);
        txtDireccionAcademia.getEditText().setOnKeyListener(null); //El Edit text no se podra editar pero si copiar y pegar su contenido
        txtDireccionAcademia.getEditText().setKeyListener(null);


        txtEncargadoAcademia = (TextInputLayout) findViewById(R.id.conf_academia_et_5);
        txtDescripcionAcademia = (TextInputLayout) findViewById(R.id.conf_academia_et_6);
        imgAcademia = (ImageView) findViewById(R.id.conf_academia_imagen_perfil);
        fab = (FloatingActionButton) findViewById(R.id.conf_academia_fab);
        firebaseControl = new FirebaseControl();
    }

    private void comenzarLocalizacion() throws IOException {
        //Obtenemos una referencia al LocationManager
        locManager =
                (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        //Obtenemos la última posición conocida
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location loc =
                locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        //Mostramos la última posición conocida
        mostrarPosicion(loc);

        //Nos registramos para recibir actualizaciones de la posición
        locListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                try {
                    mostrarPosicion(location);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            public void onProviderDisabled(String provider){
                Snackbar.make(coordinatorLayout, "GSP desactivado.", Snackbar.LENGTH_SHORT).show();
            }
            public void onProviderEnabled(String provider){
                Snackbar.make(coordinatorLayout, "Se ha activado el GPS", Snackbar.LENGTH_SHORT).show();
            }
            public void onStatusChanged(String provider, int status, Bundle extras){

            }
        };

        locManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER, 30000, 0, locListener);
    }

    private void mostrarPosicion(Location loc) throws IOException {
        if(loc != null)
        {
            System.out.println("Latitud: " + String.valueOf(loc.getLatitude()));
            System.out.println("Longitud: " + String.valueOf(loc.getLongitude()));
            System.out.println("Precision: " + String.valueOf(loc.getAccuracy()));

            Geocoder geocoder;
            List<Address> addresses;
            geocoder = new Geocoder(this, Locale.getDefault());

            addresses = geocoder.getFromLocation(Double.parseDouble(String.valueOf(loc.getLatitude())),
                    Double.parseDouble(String.valueOf(loc.getLongitude())), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5

            String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            String city = addresses.get(0).getLocality();
            String state = addresses.get(0).getAdminArea();
            String country = addresses.get(0).getCountryName();
            String postalCode = addresses.get(0).getPostalCode();
            String knownName = addresses.get(0).getFeatureName(); // Only i

            direccion = address + ", " + state + ", " + country;

            txtDireccionAcademia.getEditText().setText(direccion);
            latitudAcademia = Double.parseDouble(String.valueOf(loc.getLatitude()));
            longitudAcademia = Double.parseDouble(String.valueOf(loc.getLongitude()));
        }
        else
        {
            System.out.println("Latitud: (sin_datos)");
            System.out.println("Longitud: (sin_datos)");
            System.out.println("Precision: (sin_datos)");
        }
    }

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

    private void mandarAcademia(){

        final String nombreAcademia = txtNombreAcademia.getEditText().getText().toString().trim();
        final String telefonoAcademia = txtTelefonoAcademia.getEditText().getText().toString().trim();
        final String correoAcademia = txtCorreoAcademia.getEditText().getText().toString().trim();
        final String direccionAcademia = txtDireccionAcademia.getEditText().getText().toString().trim();
        final String encargadoAcademia = txtEncargadoAcademia.getEditText().getText().toString().trim();
        final String descripcionAcademia = txtDescripcionAcademia.getEditText().getText().toString().trim();

        //Instancia para acceder a las validaciones propias de los campos
        ValidacionesNuevaAcademia validacionesAcademia = new ValidacionesNuevaAcademia();

        //Esta instancia es usada porque las validaciones ya estan hechas en esta clase
        ValidacionesLogin valLogin = new ValidacionesLogin();

        //Instancia para acceder a las validaciones propias de los campos de nombre
        ValidacionesNuevoUsuario validacionesUsuario = new ValidacionesNuevoUsuario();


        if (validacionesUsuario.validacionNombreUsuario(nombreAcademia) &&
                valLogin.validacionEmail(correoAcademia) &&
                validacionesAcademia.validacionTelefono(telefonoAcademia) &&
                validacionesUsuario.validacionNombreCompleto(encargadoAcademia) &&
                validacionesAcademia.validacionDescripcion(descripcionAcademia)){


            Firebase refUsuario = new Firebase("https://keep-moving-data.firebaseio.com/usuarios/" + uId);
            Map<String, Object> academia = new HashMap<String, Object>();
            academia.put("nombreAcademia", nombreAcademia);
            academia.put("telefonoAcademia", telefonoAcademia);
            academia.put("correoAcademia", correoAcademia);
            academia.put("direccionAcademia", direccionAcademia);
            academia.put("encargadoAcademia", encargadoAcademia);
            academia.put("descripcionAdademia", descripcionAcademia);
            academia.put("latitudAcademia", latitudAcademia);
            academia.put("longitudAcademia", longitudAcademia);
            academia.put("imagenAcademia64", imagenBase64);
            refUsuario.updateChildren(academia);

            Firebase refAcademia = new Firebase("https://keep-moving-data.firebaseio.com/academias/" + uId);
            Map<String, Object> academiaAct = new HashMap<String, Object>();
            academiaAct.put("nombreAcademia", nombreAcademia);
            academiaAct.put("telefonoAcademia", telefonoAcademia);
            academiaAct.put("correoAcademia", correoAcademia);
            academiaAct.put("direccionAcademia", direccionAcademia);
            academiaAct.put("encargadoAcademia", encargadoAcademia);
            academiaAct.put("descripcionAdademia", descripcionAcademia);
            academiaAct.put("latitudAcademia", latitudAcademia);
            academiaAct.put("longitudAcademia", longitudAcademia);
            academiaAct.put("imagenAcademia64", imagenBase64);
            refAcademia.updateChildren(academia);

            dialogoActualizacion();

        }//End if principal
        else{

            if (validacionesUsuario.validacionNombreUsuario(nombreAcademia) == false){

                limpiarNombreAcademia();
                txtNombreAcademia.setError(getResources().getString(R.string.java_error_nombreacademia));
                Snackbar.make(coordinatorLayout, getResources().getString(R.string.java_nombre_rechazado_snack),
                        Snackbar.LENGTH_SHORT).show();

            }//End if nombre mal

            if (valLogin.validacionEmail(correoAcademia) == false){

                limpiarCorreoAcademia();
                txtCorreoAcademia.setError(getResources().getString(R.string.java_error_email));
                Snackbar.make(coordinatorLayout, getResources().getString(R.string.java_email_rechazado_snack),
                        Snackbar.LENGTH_SHORT).show();

            }//End if correo mal

            if (validacionesAcademia.validacionTelefono(telefonoAcademia) == false){

                limpiarTelefonoAcademia();
                txtTelefonoAcademia.setError(getResources().getString(R.string.java_error_telefonoacademia));
                Snackbar.make(coordinatorLayout, getResources().getString(R.string.java_telefono_snack),
                        Snackbar.LENGTH_SHORT).show();

            }//End if telefono mal

            if (validacionesUsuario.validacionNombreCompleto(encargadoAcademia) == false){

                limpiarDireccionAcademia();
                txtEncargadoAcademia.setError(getResources().getString(R.string.java_error_encargadoacademia));
                Snackbar.make(coordinatorLayout, getResources().getString(R.string.java_nombre_rechazado_snack),
                        Snackbar.LENGTH_SHORT).show();

            }//Nombre de encargado mal


            if (validacionesAcademia.validacionDescripcion(descripcionAcademia) == false){

                limpiarDescripcionAcademia();
                txtDescripcionAcademia.setError(getResources().getString(R.string.java_error_descripcionacademia));
                Snackbar.make(coordinatorLayout, getResources().getString(R.string.java_descripcion_snack),
                        Snackbar.LENGTH_SHORT).show();

            }//End if  descripcion mal

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
                    imgAcademia.setImageBitmap(BitmapFactory
                            .decodeFile(imgDecodableString));

                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                    Bitmap bitmap = BitmapFactory.decodeFile(imgDecodableString, options);
                    imgAcademia.setImageBitmap(bitmap);

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
                Intent i = new Intent(getApplicationContext(), PantallaConfiguracionAcademia.class);
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
