package com.example.shipp.keepmoving.ClasesViews;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.shipp.keepmoving.Clases.Usuario;
import com.example.shipp.keepmoving.ClasesAdapters.TabAdapterMainUsuario;
import com.example.shipp.keepmoving.ClasesFirebase.FirebaseControl;
import com.example.shipp.keepmoving.R;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
//Activity contenedora de los tabs

public class PantallaMainUsuario extends AppCompatActivity {

    private TabLayout tablay;
    private Toolbar toolbar;
    FirebaseControl firebaseControl;
    CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_main_usuario);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        inicializaCompoinentes();
        Firebase.setAndroidContext(this);

        //PRUEBA
        obtenerFirebaseDatos();

        //Agregar nuevas tabs
        tablay.addTab(tablay.newTab().setIcon(R.mipmap.ic_book_black_24dp));//Agenda 0
        tablay.addTab(tablay.newTab().setIcon(R.mipmap.ic_home_black_24dp));//Academias 1
        tablay.addTab(tablay.newTab().setIcon(R.mipmap.ic_add_black_24dp));//Tips 2 //CAMBIAR
        tablay.addTab(tablay.newTab().setIcon(R.mipmap.ic_date_range_black_24dp));//Eventos 3

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pagerMain);
        final TabAdapterMainUsuario tabAdapterMainUsuario = new TabAdapterMainUsuario(getSupportFragmentManager(),
                tablay.getTabCount());

        //Poner adapter al ViewPager
        viewPager.setAdapter(tabAdapterMainUsuario);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tablay));
        tablay.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                switch (tab.getPosition()){
                    case 0: //Agenda
                        toolbar.setTitle(getResources().getString(R.string.agenda));
                        tab.setIcon(R.mipmap.ic_book_white_24dp);
                        break;
                    case 1://Academias
                        toolbar.setTitle(getResources().getString(R.string.academias));
                        tab.setIcon(R.mipmap.ic_home_white_24dp);
                        break;
                    case 2: //Tips
                        toolbar.setTitle(getResources().getString(R.string.tips));
                        tab.setIcon(R.mipmap.ic_add_white_24dp);
                        break;
                    case 3://Eventos
                        toolbar.setTitle(getResources().getString(R.string.eventos));
                        tab.setIcon(R.mipmap.ic_date_range_white_24dp);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0){
                    tab.setIcon(R.mipmap.ic_book_black_24dp);
                }
                if (tab.getPosition() == 1){
                    tab.setIcon(R.mipmap.ic_home_black_24dp);
                }
                if (tab.getPosition() == 2){
                    tab.setIcon(R.mipmap.ic_add_black_24dp);
                }
                if (tab.getPosition() == 3){
                    tab.setIcon(R.mipmap.ic_date_range_black_24dp);
                }

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    private void obtenerFirebaseDatos(){
        final Firebase ref = new Firebase("https://keep-moving-data.firebaseio.com/");
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Usuario user = dataSnapshot.getValue(Usuario.class);
                Snackbar.make(coordinatorLayout, dataSnapshot.getKey() + " = " +
                        user.getAliasUsuario(),
                        Snackbar.LENGTH_SHORT).show();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    private void inicializaCompoinentes(){
        tablay = (TabLayout) findViewById(R.id.tab_layoutMain);
        firebaseControl = new FirebaseControl();
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.pantalla_Main_coordinator);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_configuraciones, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.action_config:
                startActivity(new Intent(getApplicationContext(), PantallaConfiguracion.class));
                break;
            case R.id.action_cerrar:
                cerrarSesion();
                break;
        }
        return true;
    }

    private void cerrarSesion(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage(getResources().getString(R.string.tabUser_cerrar_message));
        builder.setTitle(getResources().getString(R.string.tabUser_cerrar_title));
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(getApplicationContext(), PantallaInicial.class));
                finish();
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                obtenerFirebaseDatos();//PRUEBA
            }
        });

        AlertDialog alert = builder.create();
        alert.show();

    }

}
