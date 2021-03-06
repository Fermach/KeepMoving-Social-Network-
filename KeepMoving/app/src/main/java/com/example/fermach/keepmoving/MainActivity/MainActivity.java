package com.example.fermach.keepmoving.MainActivity;


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
import android.view.animation.DecelerateInterpolator;

import com.example.fermach.keepmoving.Quedadas.Crear_Quedadas.CrearQuedadaVista;
import com.example.fermach.keepmoving.Quedadas.Listado_Quedadas.Listado_Completo_Quedadas.ListadoQuedadasGeneralVista;
import com.example.fermach.keepmoving.Quedadas.Listado_Quedadas.Listado_Peticiones_Recibidas.ListadoPeticionesRecibidasVista;
import com.example.fermach.keepmoving.Quedadas.Listado_Quedadas.Listado_Solicitudes_Enviadas.ListadoSolicitudesEnviadasVista;
import com.example.fermach.keepmoving.Usuarios.Loggin.LogginPantallaVista;
import com.example.fermach.keepmoving.Usuarios.Perfil_Usuario.PerfilPantallaVista;
import com.example.fermach.keepmoving.R;
import com.example.fermach.keepmoving.Usuarios.Registro.Registro_Ampliado.RegistroAmpliadoPantallaVista;
import com.example.fermach.keepmoving.Usuarios.Registro.Registro_Basico.RegistroPantallaVista;

/**
 * @author Fermach
 * @version 1.0.
 * Actividad principal donde se van alternando las demás
 * pantallas (Fragmentos)
 */
public class MainActivity extends AppCompatActivity implements MainContract.View, DrawerLocker,ChangeToolbar{
    private final static String MAIN_FRAGMENT="MAIN_FRAGMENT";
    private Fragment fragment;
    private String TOKEN;
    private View view;
    private Toolbar toolbar;
    private MainContract.Presenter presenter;
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter= new MainPresenter(this);
        view = this.findViewById(R.id.content_main);

        //se instancia el toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //se inicia el fragmento con la pantalla de login
        fragment= new LogginPantallaVista();
        getSupportFragmentManager().beginTransaction().replace(R.id.content_main,fragment).commitAllowingStateLoss();
        presenter.iniciarListenerFire();

        //se instancia el Navigation Drawer
        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        //se activa el controlador de nuestro Navigation Drawer
        NavigationView navigationView = findViewById(R.id.nav_view);
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
                else if (id == R.id.nav_mis_peticiones_recibidas) {
                    fragment= new ListadoPeticionesRecibidasVista();
                    itemSeleccionado = true;
                }
                else if (id == R.id.nav_mis_solicitudes_enviadas) {
                    fragment= new ListadoSolicitudesEnviadasVista();
                    itemSeleccionado = true;
                }
                else if (id == R.id.nav_signout) {
                    if(isOnlineNet()) {
                        //Log.i("PULSADO","PULSADO");
                        itemSeleccionado = false;
                        presenter.setTOKEN("MENU");
                    }else {
                            Snackbar.make(view, "No hay conexión a internet", Snackbar.LENGTH_SHORT).show();
                        }

                }
                else if (id == R.id.nav_perfil) {
                    fragment= new PerfilPantallaVista();
                    itemSeleccionado=true;
                }

                if(itemSeleccionado==true){
                    if(isOnlineNet()) {
                      getSupportFragmentManager().beginTransaction().replace(R.id.content_main,fragment).commitAllowingStateLoss();
                    }else {
                        Snackbar.make(view, "No hay conexión a internet", Snackbar.LENGTH_SHORT).show();
                    }

                }

                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        Log.i("Activity Main", "MAIN");

    }

    /**
     * Controlamos el boton de ir hacia atrás
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

    /**
     * Si el usuario no está registrado muestra una pantalla
     * u otra dependiendo del token el cuál dice la pantalla desde donde viene
     *
     * @param TOKEN
     */
    @Override
    public void onUsuarioNoRegistrado(String TOKEN) {
        this.TOKEN=TOKEN;
        if(TOKEN=="REGISTRO") {
            Log.i("TOKEEN MAIN", TOKEN);

            fragment = new RegistroPantallaVista();
            getSupportFragmentManager().beginTransaction().replace(R.id.content_main, fragment).commitAllowingStateLoss();
        }else{
            Log.i("TOKEEN MAIN", TOKEN);

            fragment = new LogginPantallaVista();
            getSupportFragmentManager().beginTransaction().replace(R.id.content_main, fragment).commitAllowingStateLoss();
        }
    }

    /**
     * Si el usuario está registrado muestra una pantalla
     * u otra dependiendo del token el cuál dice la pantalla desde donde viene
     *
     * @param TOKEN
     */
    @Override
    public void onUsuarioRegistrado(String TOKEN) {
        this.TOKEN=TOKEN;

        if(isOnlineNet()) {
            if (TOKEN == "LOGGIN") {
                Log.i("TOKEEN MAIN", TOKEN);

                fragment = new ListadoQuedadasGeneralVista();
                getSupportFragmentManager().beginTransaction().replace(R.id.content_main, fragment).commitAllowingStateLoss();
            } else {
                Log.i("TOKEEN MAIN", TOKEN);

                fragment = new RegistroAmpliadoPantallaVista();
                getSupportFragmentManager().beginTransaction().replace(R.id.content_main, fragment).commitAllowingStateLoss();

            }
        }else{


            fragment = new LogginPantallaVista();
            getSupportFragmentManager().beginTransaction().replace(R.id.content_main, fragment).commitAllowingStateLoss();
            Snackbar.make(view,"No hay conexión a internet", Snackbar.LENGTH_SHORT).show();

        }
    }



    @Override
    public void onTOKENseleccionado() {
        presenter.cerrarSesion();
    }

    /**
     * Comprueba si hay conexión a internet
     * @return
     */
    public Boolean isOnlineNet() {

        try {
            Process p = java.lang.Runtime.getRuntime().exec("ping -c 1 www.google.es");

            int val           = p.waitFor();
            boolean reachable = (val == 0);
            return reachable;

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Bloquea el Nav Drawer
     * @param enabled
     */
    @Override
    public void setDrawerLocked(boolean enabled) {
        if(enabled){
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        }else{
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        }
    }

    /**
     * Settea el texto del Toolbar
     * @param title
     */
    @Override
    public void setToolbarText(String title) {
            toolbar.setTitle(title);
    }

    /**
     * Esconde el Toolbar o lo muestra
     * @param hide
     */
    @Override
    public void hideToolbar(boolean hide) {
        if (hide) {
            getSupportActionBar().hide();
        }else{
            getSupportActionBar().show();
            toolbar.animate().translationY(0).setInterpolator(new DecelerateInterpolator()).start();
        }
    }
}
