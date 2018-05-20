package com.example.fermach.keepmoving.MainActivity;

import android.app.ProgressDialog;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.example.fermach.keepmoving.App;
import com.example.fermach.keepmoving.Crear_Quedadas.CrearQuedadaVista;
import com.example.fermach.keepmoving.Listado_Quedadas.Listado_Completo_Quedadas.ListadoQuedadasGeneralVista;
import com.example.fermach.keepmoving.Listado_Quedadas.Listado_Solicitudes_Enviadas.ListadoSolicitudesEnviadasVista;
import com.example.fermach.keepmoving.Loggin.LogginPantallaVista;
import com.example.fermach.keepmoving.Perfil_Usuario.PerfilPantallaVista;
import com.example.fermach.keepmoving.R;
import com.example.fermach.keepmoving.Registro.Registro_Ampliado.RegistroAmpliadoPantallaVista;
import com.example.fermach.keepmoving.Registro.Registro_Basico.RegistroPantallaVista;

public class MainActivity extends AppCompatActivity implements MainContract.View{
    private final static String MAIN_FRAGMENT="MAIN_FRAGMENT";
    private Fragment fragment;
    private String TOKEN;
    private View view;
    private MainContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter= new MainPresenter(this);
        view = this.findViewById(R.id.content_main);

        //se instancia el toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fragment= new LogginPantallaVista();
        //presenter.cerrarSesion();

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

        presenter.iniciarListenerFire();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                boolean itemSeleccionado= false;

                if (id == R.id.nav_crear_quedada) {
                    fragment= new CrearQuedadaVista();
                    itemSeleccionado=true;
                }
                else if (id == R.id.nav_lista_quedadas) {
                    fragment= new ListadoQuedadasGeneralVista();
                    itemSeleccionado = true;
                }
                else if (id == R.id.nav_mis_solicitudes_enviadas) {
                    fragment= new ListadoSolicitudesEnviadasVista();
                    itemSeleccionado = true;
                }
                else if (id == R.id.nav_signout) {
                    //Log.i("PULSADO","PULSADO");
                    itemSeleccionado=true;
                    presenter.setTOKEN("MENU");
                }
                else if (id == R.id.nav_perfil) {
                    fragment= new PerfilPantallaVista();
                    itemSeleccionado=true;
                }

                if(itemSeleccionado==true){
                      getSupportFragmentManager().beginTransaction().replace(R.id.content_main,fragment).commit();
                }

                DrawerLayout drawer = findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });


        Log.i("Activity Main", "MAIN");


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
            if(TOKEN=="REGISTRO"){

                presenter.cerrarSesion();
            }
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


    @Override
    public void onSesionCerrada() {
/*
        fragment= new LogginPantallaVista();
        getSupportFragmentManager().beginTransaction().replace(R.id.content_main,fragment).commit();
*/
    }

    @Override
    public void onSesionCerradaError() {
        Snackbar.make(view,"No se pudo cerrar sesión en esta momento, vuelva a intentarlo más tarde", Snackbar.LENGTH_SHORT).show();

    }

    @Override
    public void onUsuarioNoRegistrado(String TOKEN) {
        this.TOKEN=TOKEN;
        if(TOKEN=="REGISTRO") {
            Log.i("TOKEEN MAIN", TOKEN);

            fragment = new RegistroPantallaVista();
            getSupportFragmentManager().beginTransaction().replace(R.id.content_main, fragment).commit();
        }else{
            Log.i("TOKEEN MAIN", TOKEN);

            fragment = new LogginPantallaVista();
            getSupportFragmentManager().beginTransaction().replace(R.id.content_main, fragment).commit();
        }
    }


    @Override
    public void onUsuarioRegistrado(String TOKEN) {
        this.TOKEN=TOKEN;
        if(TOKEN=="LOGGIN") {
            Log.i("TOKEEN MAIN", TOKEN);

            fragment = new ListadoQuedadasGeneralVista();
            getSupportFragmentManager().beginTransaction().replace(R.id.content_main, fragment).commit();
        }else{
            Log.i("TOKEEN MAIN", TOKEN);

            fragment = new RegistroAmpliadoPantallaVista();
            getSupportFragmentManager().beginTransaction().replace(R.id.content_main, fragment).commit();

        }
    }

    @Override
    public void onTOKENseleccionado() {
        presenter.cerrarSesion();
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {

    }
}
