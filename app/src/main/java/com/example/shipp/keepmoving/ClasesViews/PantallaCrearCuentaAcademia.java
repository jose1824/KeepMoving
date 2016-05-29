package com.example.shipp.keepmoving.ClasesViews;

import android.Manifest;
import android.app.ProgressDialog;
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
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
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
import com.example.shipp.keepmoving.ClasesFragments.FragmentTips;
import com.example.shipp.keepmoving.ClasesValidaciones.ValidacionesLogin;
import com.example.shipp.keepmoving.ClasesValidaciones.ValidacionesNuevaAcademia;
import com.example.shipp.keepmoving.ClasesValidaciones.ValidacionesNuevoUsuario;
import com.example.shipp.keepmoving.R;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
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
    private FloatingActionButton fab;

    private LocationManager locManager;
    private LocationListener locListener;

    private String imagenBase64;
    private double latitudAcademia;
    private double longitudAcademia;
    private String direccion;

    /*public double getLatitudAcademia() {
        return latitudAcademia;
    }

    public void setLatitudAcademia(double latitudAcademia) {
        this.latitudAcademia = latitudAcademia;
    }

    public double getLongitudAcademia() {
        return longitudAcademia;
    }

    public void setLongitudAcademia(double longitudAcademia) {
        this.longitudAcademia = longitudAcademia;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_crear_cuenta_academia);

        if (savedInstanceState != null) {
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
        //validarInternet();
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

    }//End on create

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

    private void inicializaComponentes(){
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.pantalla_CuentaAcademia_coordinator);
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
            txtNombreAcademia.setError(getResources().getString(R.string.java_error_falta));
            Snackbar.make(coordinatorLayout, getResources().getString(R.string.java_faltantes_snack),
                    Snackbar.LENGTH_SHORT).show();
        }
        if ( acad.getCorreoAcademia().equals("")){
            txtCorreoAcademia.setError(getResources().getString(R.string.java_error_falta));
            Snackbar.make(coordinatorLayout, getResources().getString(R.string.java_faltantes_snack),
                    Snackbar.LENGTH_SHORT).show();
        }
        if ( acad.getTelefonoAcademia().equals("")){
            txtNombreAcademia.setError(getResources().getString(R.string.java_error_falta));
            Snackbar.make(coordinatorLayout, getResources().getString(R.string.java_faltantes_snack),
                    Snackbar.LENGTH_SHORT).show();
        }
        if ( acad.getNombreAcademia().equals("")){
            txtNombreAcademia.setError(getResources().getString(R.string.java_error_falta));
            Snackbar.make(coordinatorLayout, getResources().getString(R.string.java_faltantes_snack),
                    Snackbar.LENGTH_SHORT).show();
        }


        if (validacionesUsuario.validacionNombreUsuario(acad.getNombreAcademia()) &&
                valLogin.validacionEmail(acad.getCorreoAcademia()) &&
                validacionesAcademia.validacionTelefono(acad.getTelefonoAcademia()) &&
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
                txtNombreAcademia.setError(getResources().getString(R.string.java_error_nombreacademia));
                Snackbar.make(coordinatorLayout, getResources().getString(R.string.java_nombre_rechazado_snack),
                        Snackbar.LENGTH_SHORT).show();

            }//End if nombre mal

            if (valLogin.validacionEmail(acad.getCorreoAcademia()) == false){

                limpiarCorreoAcademia();
                txtCorreoAcademia.setError(getResources().getString(R.string.java_error_email));
                Snackbar.make(coordinatorLayout, getResources().getString(R.string.java_email_rechazado_snack),
                        Snackbar.LENGTH_SHORT).show();

            }//End if correo mal

            if (validacionesAcademia.validacionTelefono(acad.getTelefonoAcademia()) == false){

                limpiarTelefonoAcademia();
                txtTelefonoAcademia.setError(getResources().getString(R.string.java_error_telefonoacademia));
                Snackbar.make(coordinatorLayout, getResources().getString(R.string.java_telefono_snack),
                        Snackbar.LENGTH_SHORT).show();

            }//End if telefono mal

            if (validacionesUsuario.validacionNombreCompleto(acad.getEncargadoAcademia()) == false){

                limpiarDireccionAcademia();
                txtEncargadoAcademia.setError(getResources().getString(R.string.java_error_encargadoacademia));
                Snackbar.make(coordinatorLayout, getResources().getString(R.string.java_nombre_rechazado_snack),
                        Snackbar.LENGTH_SHORT).show();

            }//Nombre de encargado mal

            if (validacionesUsuario.validacionNombreCompleto(acad.getEncargadoAcademia()) == false){

                limpiarEncargadoAcademia();
                txtEncargadoAcademia.setError(getResources().getString(R.string.java_error_encargadoacademia));
                Snackbar.make(coordinatorLayout, getResources().getString(R.string.java_nombre_rechazado_snack),
                        Snackbar.LENGTH_SHORT).show();

            }//End if Nombre de encargado mal

            if (validacionesAcademia.validacionDescripcion(acad.getDescripcionAdademia()) == false){

                limpiarDescripcionAcademia();
                txtDescripcionAcademia.setError(getResources().getString(R.string.java_error_descripcionacademia));
                Snackbar.make(coordinatorLayout, getResources().getString(R.string.java_descripcion_snack),
                        Snackbar.LENGTH_SHORT).show();

            }//End if  descripcion mal

            if (valLogin.validacionContrasena(acad.getPasswordAcademia()) == false){

                limpiarPasswordAcademia();
                Snackbar.make(coordinatorLayout, getResources().getString(R.string.java_contra_rechazada_snack),
                        Snackbar.LENGTH_SHORT).show();

            }//End if contraseña mal

            if (acad.getPasswordAcademia().equals(confPassword) == false){

                limpiarPasswordAcademia();
                Snackbar.make(coordinatorLayout, getResources().getString(R.string.java_contra_diferente_snack),
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

    }

    public void validarInternet(){

        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

            startActivity(new Intent(getApplicationContext(), PantallaPrincipal.class));
            finish();

        }//End if esta conectado
        else{
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


        }//End else no esta conectado
    }//end validar internet

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
            ref.createUser(correo_electronico, password, new Firebase.ValueResultHandler<Map<String, Object>>() {
                        @Override
                        public void onSuccess(Map<String, Object> result) {

                            Firebase academiaBranch = ref.child("academias").child(result.get("uid") + "");
                            academiaBranch.setValue(academia);
                            Firebase usuario = ref.child("usuarios").child(result.get("uid") + "");
                            usuario.setValue(academia);
                            //Firebase evento = ref.child("eventos").child(result.get("uid") + "");
                            //evento.child("claveEvento").setValue("Evento123");

                            startActivity(new Intent(getApplicationContext(), PantallaTabsAcademia.class));
                            pDialog.dismiss();
                            finish();
                        }

                        @Override
                        public void onError(FirebaseError firebaseError) {
                            switch(firebaseError.getCode()){
                                case FirebaseError.UNKNOWN_ERROR:
                                    Snackbar.make(coordinatorLayout, R.string.error_unknown,
                                            Snackbar.LENGTH_SHORT).show();
                                    pDialog.dismiss();
                                    break;
                                case FirebaseError.NETWORK_ERROR:
                                    Snackbar.make(coordinatorLayout, R.string.error_network,
                                            Snackbar.LENGTH_SHORT).show();
                                    pDialog.dismiss();
                                    break;
                                case FirebaseError.USER_CODE_EXCEPTION:
                                    Snackbar.make(coordinatorLayout, R.string.error_user,
                                            Snackbar.LENGTH_SHORT).show();
                                    pDialog.dismiss();
                                    break;
                                case FirebaseError.DISCONNECTED:
                                    Snackbar.make(coordinatorLayout, R.string.error_disconnected,
                                            Snackbar.LENGTH_SHORT).show();
                                    pDialog.dismiss();
                                    break;
                                default:
                                    pDialog.dismiss();
                                    Snackbar.make(coordinatorLayout, "Error en el servidor",
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
