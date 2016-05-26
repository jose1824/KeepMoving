package com.example.shipp.keepmoving.ClasesViews;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.pdf.PdfDocument;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.shipp.keepmoving.ClasesAdapters.TabAdapterMainUsuario;
import com.example.shipp.keepmoving.ClasesFirebase.FirebaseControl;
import com.example.shipp.keepmoving.R;
import com.firebase.client.Firebase;

public class PantallaTabsUsuario extends AppCompatActivity {

    private TabLayout tabLayout;
    private Toolbar toolbar;
    FirebaseControl firebaseControl;
    CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_tabs_usuario);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Agenda");

        inicializaComponentes();

        Firebase.setAndroidContext(this);

        //Agregar nuevas tabs
        tabLayout.addTab(tabLayout.newTab().setIcon(R.mipmap.ic_book_white_24dp));//Agenda 0
        tabLayout.addTab(tabLayout.newTab().setIcon(R.mipmap.ic_home_black_24dp));//Academias 1
        tabLayout.addTab(tabLayout.newTab().setIcon(R.mipmap.ic_add_black_24dp));//Tips 2 //CAMBIAR
        tabLayout.addTab(tabLayout.newTab().setIcon(R.mipmap.ic_date_range_black_24dp));//Eventos 3

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pagerPantallaTabUsuarios);

        TabAdapterMainUsuario tabAdapterMainUsuario = new TabAdapterMainUsuario(getSupportFragmentManager(),
                tabLayout.getTabCount());
        viewPager.setAdapter(tabAdapterMainUsuario);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

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

    private void inicializaComponentes() {
        tabLayout = (TabLayout) findViewById(R.id.tabLayoutPantallaTabUsuarios);
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
                startActivity(new Intent(getApplicationContext(), PantallaPrincipal.class));
                Firebase ref = new Firebase(firebaseControl.obtieneUrlFirebase());
                ref.unauth();
                finish();
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                //obtenerFirebaseDatos();//PRUEBA
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void onBackPressed() {
        recreate();
    }
}

