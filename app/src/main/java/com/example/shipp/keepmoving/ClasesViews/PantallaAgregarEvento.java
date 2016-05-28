package com.example.shipp.keepmoving.ClasesViews;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TimePicker;

import com.example.shipp.keepmoving.Clases.Evento;
import com.example.shipp.keepmoving.Clases.Usuario;
import com.example.shipp.keepmoving.ClasesFirebase.FirebaseControl;
import com.example.shipp.keepmoving.ClasesValidaciones.ValidacionesEvento;
import com.example.shipp.keepmoving.ClasesValidaciones.ValidacionesNuevaAcademia;
import com.example.shipp.keepmoving.R;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.io.ByteArrayOutputStream;
import java.sql.Time;
import java.util.Calendar;
import java.util.Map;
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
                Intent i = new Intent(getApplicationContext(), PantallaTabsAcademia.class);
                startActivity(i);
                finish();
            }
        });//End toolbar listener
        inicializaComponentes();
        Firebase.setAndroidContext(this);
        txtDireccion.getEditText().setText("Dirección pendiente");

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
                Intent i = new Intent(getApplicationContext(), MapsActivity.class);
                i.putExtra("activityAnterior", "activityEvento");
                startActivity(i);
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

        txtTimeInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePickerInicio();
            }
        });

        txtTimeFin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePickerFin();
            }
        });
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
        txtDireccion.getEditText().setText("wsedaiofhcouysdf");

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

        ValidacionesEvento valEvento = new ValidacionesEvento();
        //Instancia para acceder a la validacion de descripcion y direccion
        ValidacionesNuevaAcademia valAcademia = new ValidacionesNuevaAcademia();

        if (valEvento.validarTitulo(ev.getTitulo()) &&
                valAcademia.validacionDireccion(ev.getDireccionEvento()) &&
                valAcademia.validacionDescripcion(ev.getDescripcion()) &&
                longitud != 0.0 &&
                latitud != 0.0 ) {

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

            Firebase evento = ref.child("evento").child("EventoN"  + mesEvento + diaEvento +
                    anioEvento + horaFinHr + horaFinMin + horaInicioHr + horaInicioMin  +
                    numAleatorio() + numAleatorio() + numAleatorio() +
                    numAleatorio() + numAleatorio());//Pooner un ID con hora fin e inicio y cinco minutos aleatorios

            evento.setValue(evento);
            Snackbar.make(coordinatorLayout, R.string.java_bien_snack,
                    Snackbar.LENGTH_SHORT).show();

            startActivity(new Intent(getApplicationContext(), PantallaTabsAcademia.class));
            finish();

            return null;
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
        recreate();
    }

}
