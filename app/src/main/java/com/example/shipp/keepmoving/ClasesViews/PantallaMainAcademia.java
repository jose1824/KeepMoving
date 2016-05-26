package com.example.shipp.keepmoving.ClasesViews;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.shipp.keepmoving.ClasesAdapters.TabAdapterMainAcademia;
import com.example.shipp.keepmoving.ClasesAdapters.TabAdapterMainUsuario;
import com.example.shipp.keepmoving.R;

public class PantallaMainAcademia extends AppCompatActivity {
    private TabLayout tablay;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_main_academia);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        inicializaCompoinentes();

        //Agregar nuevas tabs
        tablay.addTab(tablay.newTab().setIcon(R.mipmap.ic_book_black_24dp));//Eventos 0
        tablay.addTab(tablay.newTab().setIcon(R.mipmap.ic_account_circle_black_24dp));//Perfil 1

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pagerMain);
        final TabAdapterMainAcademia tabAdapterMainAcademia = new TabAdapterMainAcademia(getSupportFragmentManager(),
                tablay.getTabCount());

        //Poner adapter al ViewPager
        viewPager.setAdapter(tabAdapterMainAcademia);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tablay));
        tablay.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                switch (tab.getPosition()){
                    case 0://Eventos
                        toolbar.setTitle(getResources().getString(R.string.eventos));
                        tab.setIcon(R.mipmap.ic_book_white_24dp);
                        break;
                    case 1: //Tips
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
                    tab.setIcon(R.mipmap.ic_account_circle_black_24dp);
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }


    private void inicializaCompoinentes(){
        tablay = (TabLayout) findViewById(R.id.tab_layoutMain);
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
