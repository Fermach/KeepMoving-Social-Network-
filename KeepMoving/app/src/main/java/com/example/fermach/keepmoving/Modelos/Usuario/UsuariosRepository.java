package com.example.fermach.keepmoving.Modelos.Usuario;

import android.util.Log;

import com.example.fermach.keepmoving.Modelos.API.UsuariosFirebase;

import java.util.List;

/**
 * Created by Fermach on 29/03/2018.
 */

public class UsuariosRepository implements UsuariosDataSource{

    private static UsuariosRepository INSTANCIA = null;
    //private List<Usuario> listaEjerciciosPorRutina = null;

    //Singletone del repositorio, para que solo pueda existir una instancia

    public static UsuariosRepository getInstance() {
        if (INSTANCIA == null) {
            INSTANCIA = new UsuariosRepository();
        }
        return INSTANCIA;
    }

    @Override
    public void loguearUsuario(Usuario usuario, final LoguearUsuarioCallback callback) {
        UsuariosFirebase usuariosFirebase= new UsuariosFirebase();
        usuariosFirebase.loguearUsuario(usuario, new LoguearUsuarioCallback() {
            @Override
            public void onUsuarioLogueado() {

                callback.onUsuarioLogueado();
            }

            @Override
            public void onUsuarioLogueadoError() {
                callback.onUsuarioLogueadoError();

            }
        });
    }

    @Override
    public void registrarUsuario(Usuario usuario, final RegistrarUsuarioCallback callback) {
        UsuariosFirebase usuariosFirebase=new UsuariosFirebase();
        usuariosFirebase.registrarUsuario(usuario, new RegistrarUsuarioCallback() {
            @Override
            public void onUsuarioRegistrado() {
                Log.i("REGISTRO", "SUCCESFUL");
                callback.onUsuarioRegistrado();
            }

            @Override
            public void onUsuarioRegistradoError() {
                Log.i("REGISTRO", "ERROR");
                callback.onUsuarioRegistradoError();
            }
        });
    }

    @Override
    public void comprobarUsuarioRegistrado(final ComprobarUsuarioRegistradoCallback callback) {
        UsuariosFirebase usuariosFirebase= new UsuariosFirebase();
        usuariosFirebase.comprobarUsuarioRegistrado(new ComprobarUsuarioRegistradoCallback() {
            @Override
            public void onUsuarioRegistrado() {

                callback.onUsuarioRegistrado();
            }

            @Override
            public void onUsuarioRegistradoError() {

                callback.onUsuarioRegistradoError();
            }
        });
    }

    @Override
    public void cerrarSesion(CerrarSesionCallback callback) {

    }

    @Override
    public void iniciarListener(final IniciarListenerCallback callback) {
        UsuariosFirebase usuariosFirebase= new UsuariosFirebase();
        usuariosFirebase.iniciarListener(new IniciarListenerCallback() {
            @Override
            public void onListenerIniciado() {
                Log.i("LISTENER: ", "INICIADO");
                callback.onListenerIniciado();
            }

            @Override
            public void onListenerIniciadoError() {

            }
        });

    }

    @Override
    public void detenerListener(final DetenerListenerCallback callback) {
        UsuariosFirebase usuariosFirebase= new UsuariosFirebase();
        usuariosFirebase.detenerListener(new DetenerListenerCallback() {
            @Override
            public void onListenerDetenido() {
                Log.i("LISTENER: ", "DETENIDO");
                callback.onListenerDetenido();
            }

            @Override
            public void onListenerDetenidoError() {

            }
        });

    }
}
