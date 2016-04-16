package com.example.shipp.keepmoving.ClasesViews;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import com.example.shipp.keepmoving.Clases.Evento;
import com.example.shipp.keepmoving.Clases.EventoAdapter;
import com.example.shipp.keepmoving.R;
import com.firebase.client.Firebase;

import java.util.ArrayList;
import java.util.List;

public class PantallaEvento extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    FloatingActionButton fab;
    RecyclerView recList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_evento);
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);;
        window.setStatusBarColor(getResources().getColor(R.color.colorTransparent));

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_evento);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.eventos));
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
        Firebase.setAndroidContext(this);

        //Inicializar floating button
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        //Inicializar navigation drawer
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Iniciar recycler view
        recList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);

        EventoAdapter ea = new EventoAdapter(createList(30));
        recList.setAdapter(ea);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_eventos) {
            // Handle the camera action
        } else if (id == R.id.nav_agenda) {

        } else if (id == R.id.nav_academias) {

        } else if (id == R.id.nav_tutoriales) {

        } else if (id == R.id.nav_tips) {

        } else if (id == R.id.nav_configuracion) {
            startActivity(new Intent(getApplicationContext(), PantallaConfiguracion.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void inicializaComponentes(){
        fab = (FloatingActionButton) findViewById(R.id.fab);
        recList = (RecyclerView) findViewById(R.id.recyclerView);
    }//End inicializaComponentes

    private List<Evento> createList(int size) {

        List<Evento> result = new ArrayList<Evento>();
        for (int i=1; i <= size; i++) {
            Evento ev = new Evento();
            ev.titulo = Evento.TITULO_PREFIX + i;
            ev.fechaHora = Evento.FECHA_PREFIX + i;
            ev.descripcion = Evento.DESCRIPCION_PREFIX + i;

            result.add(ev);

        }

        return result;
    }//end lisst

}
