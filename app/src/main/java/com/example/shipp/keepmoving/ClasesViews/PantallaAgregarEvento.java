package com.example.shipp.keepmoving.ClasesViews;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
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
import android.os.AsyncTask;
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
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TimePicker;

import com.example.shipp.keepmoving.Clases.Evento;
import com.example.shipp.keepmoving.ClasesFirebase.FirebaseControl;
import com.example.shipp.keepmoving.ClasesValidaciones.ValidacionesNuevaAcademia;
import com.example.shipp.keepmoving.R;
import com.firebase.client.AuthData;
import com.firebase.client.Firebase;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class PantallaAgregarEvento extends AppCompatActivity {
    private TextInputLayout txtTitulo;
    private TextInputLayout txtDireccion;
    private TextInputLayout txtDescripcion;
    private TextInputLayout txtFechaEvento;
    private TextInputLayout txtTimeInicio;
    private TextInputLayout txtTimeFin;
    private ImageButton dateEvento;
    private ImageButton timeInicio;
    private ImageButton timeFin;
    private ImageButton obtenerDireccion;
    private ImageView imgEvento;

    private final static int SELECT_PHOTO = 12345;
    CoordinatorLayout coordinatorLayout;
    FirebaseControl firebaseControl;
    FloatingActionButton fab;

    String imagenBase64;
    int horaInicioHr;
    int horaInicioMin;
    int horaFinHr;
    int horaFinMin;
    int diaEvento;
    int mesEvento;
    int anioEvento;
    private double latitud;
    private double longitud;
    private String direccion;

    private String uId;
    private String email;

    private LocationManager locManager;
    private LocationListener locListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_agregar_evento);

        if (savedInstanceState != null){
            onRestoreInstanceState(savedInstanceState);
        }

        //Inicializacion de la toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.agregar_evento));
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
        Firebase.setAndroidContext(this);
        //txtDireccion.getEditText().setText("Dirección pendiente");
        imagenBase64 = "";

        txtDireccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtDireccion.getEditText().getText().equals("")){
                    txtDireccion.setError(getResources().getString(R.string.java_error_editText_direccion));
                }

            }
        });

        txtFechaEvento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtFechaEvento.getEditText().getText().equals("")){
                    txtFechaEvento.setError(getResources().getString(R.string.java_error_editText_fecha));
                }

            }
        });

        txtTimeInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtTimeInicio.getEditText().getText().equals("")) {
                    txtTimeInicio.setError(getResources().getString(R.string.java_error_editText_hInicio));
                }

            }
        });

        txtTimeFin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtTimeFin.getEditText().getText().equals("")) {
                    txtTimeFin.setError(getResources().getString(R.string.java_error_editText_hFin));
                }
            }
        });

        obtenerDireccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent i = new Intent(getApplicationContext(), MapsActivity.class);
                i.putExtra("activityAnterior", "activityEvento");
                startActivity(i);*/
                try {
                    comenzarLocalizacion();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                seleccionarFoto();
            }
        });

        dateEvento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateDialog();
            }
        });

        timeInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePickerInicio();
            }
        });

        timeFin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePickerFin();
            }
        });
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

            txtDireccion.getEditText().setText(direccion);
            latitud = Double.parseDouble(String.valueOf(loc.getLatitude()));
            longitud = Double.parseDouble(String.valueOf(loc.getLongitude()));
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
            mandarEvento();
            return true;
        }//end if

        return super.onOptionsItemSelected(item);
    }//End on options

    private void inicializaComponentes(){
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.pantalla_AgregarEvento_coordinator);
        txtTitulo = (TextInputLayout) findViewById(R.id.evento_et_1);

        txtDireccion = (TextInputLayout) findViewById(R.id.evento_et_2);
        txtDireccion.getEditText().setOnKeyListener(null); //El Edit text no se podra editar pero si copiar y pegar su contenido
        txtDireccion.getEditText().setKeyListener(null);

        txtDescripcion = (TextInputLayout) findViewById(R.id.evento_et_3);

        txtFechaEvento = (TextInputLayout) findViewById(R.id.evento_et_4);
        txtFechaEvento.getEditText().setOnKeyListener(null);
        txtFechaEvento.getEditText().setKeyListener(null);

        txtTimeInicio = (TextInputLayout) findViewById(R.id.evento_et_5);
        txtTimeInicio.getEditText().setOnKeyListener(null);
        txtTimeInicio.getEditText().setKeyListener(null);

        txtTimeFin = (TextInputLayout) findViewById(R.id.evento_et_6);
        txtTimeFin.getEditText().setOnKeyListener(null);
        txtTimeFin.getEditText().setKeyListener(null);

        imgEvento = (ImageView) findViewById(R.id.agregar_foto_evento);
        obtenerDireccion = (ImageButton) findViewById(R.id.btn_rastrear_direccion);
        timeFin = (ImageButton) findViewById(R.id.btn_tineEventoFin);
        timeInicio = (ImageButton) findViewById(R.id.btn_tineEventoIni);
        dateEvento = (ImageButton) findViewById(R.id.btn_dateEvento);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        firebaseControl = new FirebaseControl();

    }

    public void dateDialog() {
        Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dpd = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        anioEvento = year;
                        mesEvento = monthOfYear;
                        diaEvento = dayOfMonth;
                        txtFechaEvento.getEditText().setText(diaEvento + "/" + mesEvento + "/" + anioEvento);
                    }
                }, mYear, mMonth, mDay);
        dpd.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        dpd.show();
    }

    public void timePickerInicio(){
        Calendar c = Calendar.getInstance();
        int mHour = c.get(Calendar.HOUR_OF_DAY);
        int mMinute = c.get(Calendar.MINUTE);

        TimePickerDialog tpd = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {
                        horaInicioHr = hourOfDay;
                        horaInicioMin = minute;
                        txtTimeInicio.getEditText().setText(horaInicioHr + ":" + horaInicioMin);
                    }
                }, mHour, mMinute, false);
        tpd.show();
    }

    public void timePickerFin(){
        Calendar c = Calendar.getInstance();
        int mHour = c.get(Calendar.HOUR_OF_DAY);
        int mMinute = c.get(Calendar.MINUTE);

        TimePickerDialog tpd = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {
                        horaFinHr = hourOfDay;
                        horaFinMin = minute;
                        txtTimeFin.getEditText().setText(horaFinHr + ":" + horaFinMin);
                    }
                }, mHour, mMinute, false);
        tpd.show();
    }

    private void mandarEvento(){
        /*if (getDireccion() != null || getDireccion() != "") {
            txtDireccion.getEditText().setText(getDireccion());
        }*/

        final Firebase ref = new Firebase("https://keep-moving-data.firebaseio.com/");
        Evento ev = new Evento(txtTitulo.getEditText().getText().toString().trim(), //Instancia
                txtDireccion.getEditText().getText().toString().trim(),
                longitud,
                latitud,
                txtDescripcion.getEditText().getText().toString().trim(),
                horaInicioHr,
                horaInicioMin,
                horaFinHr,
                horaFinMin,
                diaEvento,
                mesEvento,
                anioEvento,
                imagenBase64);

        //Instancia para acceder a la validacion de descripcion y direccion
        ValidacionesNuevaAcademia valAcademia = new ValidacionesNuevaAcademia();

        if (valAcademia.validacionDescripcion(ev.getTitulo()) &&
                valAcademia.validacionDescripcion(ev.getDescripcion()) &&
                longitud != 0.0 && latitud != 0.0 && txtDescripcion.getEditText().getText().toString() != " " &&
                horaInicioHr != 0.0 && horaInicioMin != 0.0 && horaFinHr != 0.0 && horaFinMin != 0.0 &&
                diaEvento != 0 && mesEvento != 0 && anioEvento != 0 && imagenBase64 != null && imagenBase64 != "" &&
                txtFechaEvento.getEditText().getText().toString() != " " && txtTimeInicio.getEditText().getText().toString() != "" &&
                txtTimeFin.getEditText().getText().toString() != "") {

            ClaseAsyncTask asyncTask = new ClaseAsyncTask(getResources().getString(R.string.java_progress_titleCrear),
                    getResources().getString(R.string.java_progress_message),
                    ref,
                    ev);
            asyncTask.execute();

            /*
            Firebase evento = ref.child("evento").child("EventoN"  + mesEvento + diaEvento +
                    anioEvento + horaFinHr + horaFinMin + horaInicioHr + horaInicioMin  +
                    numAleatorio() + numAleatorio() + numAleatorio() +
                    numAleatorio() + numAleatorio());//Pooner un ID con hora fin e inicio y cinco minutos aleatorios

            evento.setValue(ev);
            Snackbar.make(coordinatorLayout, R.string.java_bien_snack,
                    Snackbar.LENGTH_SHORT).show();

            startActivity(new Intent(getApplicationContext(), PantallaMainAcademia.class));
            finish();
            */
        }
        else {
            //titulo mal
            if (valAcademia.validacionDescripcion(ev.getTitulo()) == false) {
                    txtTitulo.setError("Ingrese un titulo.");
            }

            //descripcion mal
            if (valAcademia.validacionDescripcion(ev.getDescripcion()) == false) {
                    txtDescripcion.setError("Ingrese una descripción del evento.");
            }

            if (txtDireccion.getEditText().getText().toString().equals(null)) {
                    txtDescripcion.setError("Ingrese una direccion del evento.");
            }

            if (txtFechaEvento.getEditText().getText().toString().equals(null)) {
                    txtDescripcion.setError("Ingrese la fecha de inicio del evento.");
            }

            if (txtTimeInicio.getEditText().getText().toString().equals(null)) {
                    txtDescripcion.setError("Ingrese la hora de inicio del evento.");
            }

            if (txtTimeFin.getEditText().getText().toString() .equals(null)) {
                    txtDescripcion.setError("Ingrese la hora de fin del evento.");
            }

            if (imagenBase64 == "") {
                    Snackbar.make(coordinatorLayout, "Ingrese un foto del evento.", Snackbar.LENGTH_SHORT).show();
            }
        }

    }

    public int numAleatorio(){
        Random rd = new Random();
        return  rd.nextInt() * 9 + 1;
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
                    imgEvento.setImageBitmap(BitmapFactory
                            .decodeFile(imgDecodableString));

                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                    Bitmap bitmap = BitmapFactory.decodeFile(imgDecodableString, options);
                    imgEvento.setImageBitmap(bitmap);

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

    class ClaseAsyncTask extends AsyncTask {
        ProgressDialog pDialog;
        private String progressTitle;
        private String progressMessage;
        private Firebase ref;
        private Evento evento;

        public ClaseAsyncTask(String progressTitle, String progressMessage, Firebase ref, Evento evento) {
            this.progressTitle = progressTitle;
            this.progressMessage = progressMessage;
            this.ref = ref;
            this.evento = evento;
        }

        @Override
        protected Object doInBackground(Object[] params) {
            ref.addAuthStateListener(new Firebase.AuthStateListener() {
                @Override
                public void onAuthStateChanged(AuthData authData) {
                    if (authData != null) {
                        uId = authData.getUid();
                        guardarEvento(uId);
                    } else {
                        Snackbar.make(coordinatorLayout, R.string.conf_in_usuario,
                                Snackbar.LENGTH_LONG).show();
                        Intent i = new Intent(getApplicationContext(), PantallaTabsAcademia.class);
                        startActivity(i);
                        finish();
                    }
                }
            });
/*
            Firebase evento = ref.child("evento").child("EventoN-"  + mesEvento + diaEvento +
                    anioEvento + "-" + horaFinHr + horaFinMin + "-" +  horaInicioHr + horaInicioMin  + "-" +
                    numAleatorio() + numAleatorio() + numAleatorio() +
                    numAleatorio() + numAleatorio());//Pooner un ID con hora fin e inicio y cinco minutos aleatorios

            evento.setValue(evento);
            Snackbar.make(coordinatorLayout, R.string.java_bien_snack,
                    Snackbar.LENGTH_SHORT).show();

            startActivity(new Intent(getApplicationContext(), PantallaTabsAcademia.class));
            finish();
*/
            return null;
        }

        private void guardarEvento(String uId){
            Firebase refEventos = new Firebase("https://keep-moving-data.firebaseio.com/eventos/" + uId
                    + "/" + "EventoN-"  + mesEvento + diaEvento +
                    anioEvento + "-" + horaFinHr + horaFinMin + "-" +  horaInicioHr + horaInicioMin  + "-" +
                    numAleatorio() + numAleatorio() + numAleatorio() +
                    numAleatorio() + numAleatorio());
            refEventos.setValue(evento);

            Firebase refEventosTotales = new Firebase("https://keep-moving-data.firebaseio.com/eventototales/"
                    + "/" + "EventoN-"  + mesEvento + diaEvento +
                    anioEvento + "-" + horaFinHr + horaFinMin + "-" +  horaInicioHr + horaInicioMin  + "-" +
                    numAleatorio() + numAleatorio() + numAleatorio() +
                    numAleatorio() + numAleatorio());
            refEventosTotales.setValue(evento);


            dialogoActualizacion();
            pDialog.dismiss();
        }

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            pDialog = new ProgressDialog(PantallaAgregarEvento.this);
            pDialog.setTitle(progressTitle);
            pDialog.setMessage(progressMessage);
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

    }

    @Override
    public void onBackPressed(){
        startActivity(new Intent(getApplicationContext(), PantallaTabsAcademia.class));
        finish();
    }

    private void dialogoActualizacion(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("Se registro el evento");
        builder.setTitle(getResources().getString(R.string.java_aviso));
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(getApplicationContext(), PantallaTabsAcademia.class));
                finish();
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
                Intent i = new Intent(getApplicationContext(), PantallaTabsAcademia.class);
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
