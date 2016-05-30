package com.example.shipp.keepmoving.ClasesFragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shipp.keepmoving.ClasesFirebase.FirebaseControl;
import com.example.shipp.keepmoving.ClasesViews.PantallaConfiguracionAcademia;
import com.example.shipp.keepmoving.R;
import com.firebase.client.AuthData;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

/**
 * Created by xubudesktop1 on 19/05/16.
 */
public class FragmentPerfilAcademia extends android.support.v4.app.Fragment {
    private TextView txtCorreo;
    private TextView txtEncargado;
    private TextView txtTelefono;
    private TextView txtDescripcion;
    private TextView txtNombreAcademiaPerfil;
    private ImageView imagenAcademiaPerfil;
    private Button btnDireccionAcademia;

    private LocationManager locManager;
    private LocationListener locListener;
    FirebaseControl firebaseControl;

    private String imagenBase64;
    private double latitudAcademia;
    private double longitudAcademia;
    private String direccion;

    private String uId;

    CoordinatorLayout cLayout;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        cLayout = (CoordinatorLayout)
                inflater.inflate(R.layout.fragment_tab_academia_perfil, container, false);

        cLayout.findViewById(R.id.academia_perfil_coordinator);

        inicializaComponentes();
        Firebase.setAndroidContext(getActivity().getApplicationContext());

        Firebase ref = new Firebase("https://keep-moving-data.firebaseio.com/");
        ref.addAuthStateListener(new Firebase.AuthStateListener() {
            @Override
            public void onAuthStateChanged(AuthData authData) {
                if (authData != null) {
                    uId = authData.getUid();
                    cargaInformacion(uId);
                } else {
                    Snackbar.make(cLayout, R.string.conf_in_usuario,
                            Snackbar.LENGTH_LONG).show();

                }
            }
        });
        //Abrir maps activity con la direccion de la academia
        btnDireccionAcademia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.putExtra("latitud", latitudAcademia);
                i.putExtra("longitud", longitudAcademia);
                i.putExtra("activityAnterior", "activityPerfilAcademia");
                i.putExtra("nombre", btnDireccionAcademia.getText().toString());
                startActivity(i);
            }
        });

        return cLayout;
    }

    private void inicializaComponentes(){
        txtCorreo = (TextView) cLayout.findViewById(R.id.txt_perfil_1);
        txtEncargado = (TextView) cLayout.findViewById(R.id.txt_perfil_2);
        txtTelefono = (TextView) cLayout.findViewById(R.id.txt_perfil_3);
        txtDescripcion = (TextView) cLayout.findViewById(R.id.txt_perfil_4);
        txtNombreAcademiaPerfil = (TextView) cLayout.findViewById(R.id.nombreAcademia_perfil);
        imagenAcademiaPerfil = (ImageView) cLayout.findViewById(R.id.imagenAcademia_perfil);
        btnDireccionAcademia = (Button) cLayout.findViewById(R.id.btn_direccionAcademia_perfil);
        firebaseControl = new FirebaseControl();
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

                imagenAcademiaPerfil.setImageBitmap(decodedImage);

                latitudAcademia = latitudAcademiaRec;
                longitudAcademia = longitudAcademiaRec;

                txtNombreAcademiaPerfil.setText(nombreAcademia);
                txtTelefono.setText(telefonoAcademia);
                txtCorreo.setText(correoAcademia);
                btnDireccionAcademia.setText(direccionAcademia);
                txtEncargado.setText(encargadoAcademia);
                txtDescripcion.setText(descripcionAcademia);

                imagenBase64 = imagenAcademia64;
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

}