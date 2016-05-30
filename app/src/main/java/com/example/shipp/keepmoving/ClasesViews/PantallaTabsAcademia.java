package com.example.shipp.keepmoving.ClasesViews;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.shipp.keepmoving.ClasesAdapters.TabAdapterMainAcademia;
import com.example.shipp.keepmoving.ClasesFirebase.FirebaseControl;
import com.example.shipp.keepmoving.R;
import com.firebase.client.Firebase;

public class PantallaTabsAcademia extends AppCompatActivity {

    private TabLayout tabLayout;
    private Toolbar toolbar;
    CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_tabs_academia);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Eventos");

        inicializaComponentes();

        Firebase.setAndroidContext(this);

        //Agregar nuevas tabs
        tabLayout.addTab(tabLayout.newTab().setIcon(R.mipmap.ic_book_white_24dp));//Eventos 0
        tabLayout.addTab(tabLayout.newTab().setIcon(R.mipmap.ic_home_black_24dp));//Academias 1
        tabLayout.addTab(tabLayout.newTab().setIcon(R.mipmap.ic_account_circle_black_24dp));//Perfil 1


        final ViewPager viewPager = (ViewPager) findViewById(R.id.pagerPantallaTabAcademias);

        TabAdapterMainAcademia tabAdapterMainAcademia = new TabAdapterMainAcademia(getSupportFragmentManager(),
                tabLayout.getTabCount());
        viewPager.setAdapter(tabAdapterMainAcademia);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                switch (tab.getPosition()){
                    case 0://Eventos
                        toolbar.setTitle(getResources().getString(R.string.eventos));
                        tab.setIcon(R.mipmap.ic_book_white_24dp);
                        break;
                    case 1: //Tips
                        toolbar.setTitle(getResources().getString(R.string.academias));
                        tab.setIcon(R.mipmap.ic_home_white_24dp);

                        break;
                    case 2: //Tips
                        toolbar.setTitle(getResources().getString(R.string.perfil_academia));
                        tab.setIcon(R.mipmap.ic_account_circle_white_24dp);
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
                    tab.setIcon(R.mipmap.ic_account_circle_black_24dp);
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void inicializaComponentes(){
        tabLayout = (TabLayout) findViewById(R.id.tabLayoutPantallaTabAcademias);
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
                startActivity(new Intent(getApplicationContext(), PantallaConfiguracionAcademia.class));
                break;
            case R.id.action_cerrar:
                cierreSesion();
                break;
        }
        return true;
    }

    public void cierreSesion(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage(getResources().getString(R.string.tabUser_cerrar_message));
        builder.setTitle(getResources().getString(R.string.tabUser_cerrar_title));
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Firebase ref = new Firebase("https://keep-moving-data.firebaseio.com/");
                ref.unauth();
                finish();
                startActivity(new Intent(getApplicationContext(), PantallaPrincipal.class));
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
