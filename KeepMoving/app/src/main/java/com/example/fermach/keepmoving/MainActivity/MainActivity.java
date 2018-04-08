package com.example.fermach.keepmoving.MainActivity;

import android.os.PersistableBundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.example.fermach.keepmoving.Loggin.LogginPantallaVista;
import com.example.fermach.keepmoving.R;

public class MainActivity extends AppCompatActivity {
    public final static String MAIN_FRAGMENT="MAIN_FRAGMENT";
    private Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //se instancia el toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fragment= new LogginPantallaVista();

        //Se instancia el Navigation Drawer
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        //se inicia el fragmeno con la lista de rutinas

        getSupportFragmentManager().beginTransaction().replace(R.id.content_main,fragment).commit();

        //se activa el controlador de nuestro Navigation Drawer
        NavigationView navigationView = findViewById(R.id.nav_view);
        //navigationView.setNavigationItemSelectedListener(this);
        Log.i("Activity Main", "MAIN");


    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }

    /**
     * Controlamos las el boton tactil de ir hacia atrás
     */
    @Override
    public void onBackPressed() {
        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            Log.i("Activity Main", "1");

            drawer.closeDrawer(GravityCompat.START);
        } else {
            Log.i("Activity Main", "2");

            //si no queda ningún fragment sale de la aplcación
            if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
                Log.i("Activity Main", "3");

                finishAffinity();

            } else {
                Log.i("Activity Main", "4");

                //si no manda al fragment anterior.
                getSupportFragmentManager().popBackStackImmediate();
            }
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    public boolean onNavigationItemSelected(MenuItem item) {
        //detecta el item de nuestro NavigationBar clickeado y nos permite navegar por los fragmentos
        int id = item.getItemId();
        boolean itemSeleccionado= false;

        if (id == R.id.nav_nueva_rutina) {
            //fragment= new CrearRutinaVista();
            itemSeleccionado=true;
        }
        else if (id == R.id.nav_lista_rutinas) {
            //fragment= new ListaRutinasVista();
            itemSeleccionado=true;
        }else if (id == R.id.nav_info) {
            //fragment= new Pantalla_info();
            itemSeleccionado=true;
        }

        if(itemSeleccionado==true){
         //   getSupportFragmentManager().beginTransaction().replace(R.id.content_main,fragment).commit();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
