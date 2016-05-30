package com.example.shipp.keepmoving.ClasesViews;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.shipp.keepmoving.R;
import com.firebase.client.AuthData;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, android.location.LocationListener {
    protected LocationManager locationManager;
    private Button aceptarDireccion;

    private double lattitud;
    private double longitud;

    private GoogleMap mMap;

    private String direccion;

    private boolean confAcademia;

    String activityAnterior;
    String nombre;

    public String getDireccion() {
        return direccion;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getIntent().getExtras();
        lattitud = bundle.getDouble("latitud");
        longitud = bundle.getDouble("longitud");
        nombre = bundle.getString("nombre");


        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);



        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);

        //aceptarDireccion = (Button) findViewById(R.id.btn_aceptar_direccion);

        //Obtener el dato que se paso del intent
        //activityAnterior = bundle.getString("activityAnterior");


        /*aceptarDireccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (activityAnterior.equals("activityEvento")) {
                    PantallaAgregarEvento pantallaAgregarEvento = new PantallaAgregarEvento();
                    pantallaAgregarEvento.setDireccion(direccion);
                    pantallaAgregarEvento.setLatitud(lattitud);
                    pantallaAgregarEvento.setLongitud(longitud);
                    startActivity(new Intent(getApplicationContext(), PantallaAgregarEvento.class));
                }
                else if (activityAnterior.equals("activityAcademia")) {
                    PantallaCrearCuentaAcademia pantallaCrearCuentaAcademia = new PantallaCrearCuentaAcademia();
                    pantallaCrearCuentaAcademia.setDireccion(direccion);
                    pantallaCrearCuentaAcademia.setLatitudAcademia(lattitud);
                    pantallaCrearCuentaAcademia.setLongitudAcademia(longitud);
                    startActivity(new Intent(getApplicationContext(), PantallaCrearCuentaAcademia.class));
                }
                else {
                    System.out.println(activityAnterior);;
                }

            }
        });*/

    }
    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng posicionActual = new LatLng(lattitud, longitud);
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(posicionActual, 15);

        mMap.moveCamera(cameraUpdate);
        mMap.animateCamera(CameraUpdateFactory.zoomIn());
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.addMarker(new MarkerOptions().position(posicionActual).title(nombre));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);


        mMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {
                lattitud = location.getLatitude();
                longitud = location.getLongitude();
                LatLng posicionActual = new LatLng(lattitud, longitud);
                mMap.moveCamera(CameraUpdateFactory.newLatLng(posicionActual));
            }
        });

        /*mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                mMap.clear();
                Marker marcador = mMap.addMarker(new MarkerOptions()
                                    .position(latLng)
                                    .title(obtenerDireccion(latLng))
                                    .icon(BitmapDescriptorFactory
                                            .defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
                                    .draggable(true));

                direccion = marcador.getTitle();
                lattitud = marcador.getPosition().latitude;
                longitud = marcador.getPosition().longitude;
            }
        });*/

    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    /*private String obtenerDireccion(LatLng latLng){
        Geocoder geocoder = new Geocoder(this);

        String address = "";
        try {
            address = geocoder
                    .getFromLocation( latLng.latitude, latLng.longitude, 1 )
                    .get( 0 ).getAddressLine( 0 );
        } catch (IOException e ) {
        }

        return address;
    }*/

    @Override
    public void onBackPressed() {
       /* if(activityAnterior.equals("activityPerfilAcademia")) {
            finish();
        }*/
        Firebase ref = new Firebase("https://keep-moving-data.firebaseio.com/");
        ref.addAuthStateListener(new Firebase.AuthStateListener() {
            @Override
            public void onAuthStateChanged(AuthData authData) {
                if (authData != null) {
                    String uId = authData.getUid();
                    comprobacionTipoUsuario(uId);
                }
            }
        });


        /*else {*/


        //}

    }
    private void comprobacionTipoUsuario(String uId){
        Firebase ref = new Firebase("https://keep-moving-data.firebaseio.com/usuarios/" + uId);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                confAcademia = (boolean) dataSnapshot.child("confAcademia").getValue();
                String mail = (String) dataSnapshot.child("correoAcademia").getValue();
                System.out.println(mail);
                System.out.println(confAcademia);
                if (confAcademia){
                    startActivity(new Intent(getApplicationContext(), PantallaTabsAcademia.class));
                    finish();
                }
                else{
                    startActivity(new Intent(getApplicationContext(), PantallaTabsUsuario.class));
                    finish();
                }
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }
}
