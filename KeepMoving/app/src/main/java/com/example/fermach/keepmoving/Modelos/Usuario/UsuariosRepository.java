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
        UsuariosFirebase usuariosFirebase= UsuariosFirebase.getInstance();
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
    public void desloguearUsuario(final DesloguearUsuarioCallback callback) {
        UsuariosFirebase usuariosFirebase= UsuariosFirebase.getInstance();
        usuariosFirebase.desloguearUsuario(new DesloguearUsuarioCallback() {
            @Override
            public void onUsuarioDeslogueado() {
                callback.onUsuarioDeslogueado();
            }

            @Override
            public void onUsuarioDeslogueadoError() {

            }
        });

    }

    @Override
    public void registrarUsuario(Usuario usuario, final RegistrarUsuarioCallback callback) {
        UsuariosFirebase usuariosFirebase=UsuariosFirebase.getInstance();
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
    public void registrarUsuarioAmpliadoConFoto(Usuario usuario,byte[] foto, final RegistrarUsuarioConFotoCallback callback) {
        UsuariosFirebase usuariosFirebase=UsuariosFirebase.getInstance();
        usuariosFirebase.registrarUsuarioAmpliadoConFoto(usuario,foto, new RegistrarUsuarioConFotoCallback() {
            @Override
            public void onUsuarioRegistrado() {
                Log.i("REGISTRO AMPLIADO", "SUCCESFUL");
                callback.onUsuarioRegistrado();
            }

            @Override
            public void onUsuarioRegistradoError() {
                Log.i("REGISTRO AMPLIADO", "ERROR");
                callback.onUsuarioRegistradoError();
            }
        });
    }

    @Override
    public void registrarUsuarioAmpliado(Usuario usuario, final RegistrarUsuarioAmpliadoCallback callback) {
        UsuariosFirebase usuariosFirebase=UsuariosFirebase.getInstance();
        usuariosFirebase.registrarUsuarioAmpliado(usuario, new RegistrarUsuarioAmpliadoCallback() {
            @Override
            public void onUsuarioRegistrado() {
                Log.i("REGISTRO AMPLIADO", "SUCCESFUL");
                callback.onUsuarioRegistrado();
            }

            @Override
            public void onUsuarioRegistradoError() {
                Log.i("REGISTRO AMPLIADO", "ERROR");
                callback.onUsuarioRegistradoError();
            }
        });
    }

    @Override
    public void comprobarUsuarioRegistrado(final ComprobarUsuarioRegistradoCallback callback) {
        UsuariosFirebase usuariosFirebase=UsuariosFirebase.getInstance();
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
    public void cancelarRegistroUsuario(final CancelarRegistroUsuarioCallback callback) {
        UsuariosFirebase usuariosFirebase= UsuariosFirebase.getInstance();
        usuariosFirebase.cancelarRegistroUsuario(new CancelarRegistroUsuarioCallback() {
            @Override
            public void onRegistroCancelado() {
                callback.onRegistroCancelado();
            }

            @Override
            public void onRegistroCanceladoError() {
                callback.onRegistroCanceladoError();
            }
        });
    }


    @Override
    public void iniciarListener(final IniciarListenerCallback callback) {
        UsuariosFirebase usuariosFirebase= UsuariosFirebase.getInstance();
        Log.i("LISTENER","REPOSITORY");
        usuariosFirebase.iniciarListener(new IniciarListenerCallback() {
            @Override
            public void onUsuarioRegistrado() {
                Log.i("LISTENER: ", "INICIADO");
                callback.onUsuarioRegistrado();
            }

            @Override
            public void onUsuarioNoRegistrado() {
                Log.i("LISTENER: ", "NO INICIADO");
                callback.onUsuarioNoRegistrado();
            }
        });

    }

    @Override
    public void obtenerFotoPerfil(ObtenerFotoPerfilCallback callback) {

    }

    @Override
    public void obtenerUsuarioActual(ObtenerUsuarioActualCallback callback) {

    }


}
