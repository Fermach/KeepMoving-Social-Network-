package com.example.fermach.keepmoving.Modelos.Usuario;

/**
 * Created by Fermach on 29/03/2018.
 */

public interface UsuariosDataSource {

    void loguearUsuario(Usuario usuario, LoguearUsuarioCallback callback);
    void registrarUsuario(Usuario usuario, RegistrarUsuarioCallback callback);
    void comprobarUsuarioRegistrado(ComprobarUsuarioRegistradoCallback callback);
    void cerrarSesion(CerrarSesionCallback callback);
    void iniciarListener(IniciarListenerCallback callback);
    void detenerListener(DetenerListenerCallback callback);

    interface LoguearUsuarioCallback{
        void onUsuarioLogueado();
        void onUsuarioLogueadoError();
    }
    interface RegistrarUsuarioCallback{
        void onUsuarioRegistrado();
        void onUsuarioRegistradoError();
    }

    interface ComprobarUsuarioRegistradoCallback{
        void onUsuarioRegistrado();
        void onUsuarioRegistradoError();
    }

    interface CerrarSesionCallback{
        void onSesionCerrada();
        void onSesionCerradaError();
    }

    interface IniciarListenerCallback{
        void onListenerIniciado();
        void onListenerIniciadoError();
    }

    interface DetenerListenerCallback{
        void onListenerDetenido();
        void onListenerDetenidoError();
    }
}
