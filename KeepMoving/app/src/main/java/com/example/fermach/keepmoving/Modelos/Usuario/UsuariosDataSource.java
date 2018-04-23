package com.example.fermach.keepmoving.Modelos.Usuario;

/**
 * Created by Fermach on 29/03/2018.
 */

public interface UsuariosDataSource {

    void loguearUsuario(Usuario usuario, LoguearUsuarioCallback callback);
    void desloguearUsuario(DesloguearUsuarioCallback callback);
    void registrarUsuario(Usuario usuario, RegistrarUsuarioCallback callback);
    void registrarUsuarioAmpliadoConFoto(Usuario usuario,byte[] foto, RegistrarUsuarioConFotoCallback callback);
    void registrarUsuarioAmpliado(Usuario usuario, RegistrarUsuarioAmpliadoCallback callback);
    void comprobarUsuarioRegistrado(ComprobarUsuarioRegistradoCallback callback);
    void cancelarRegistroUsuario(CancelarRegistroUsuarioCallback callback);
    void iniciarListener(IniciarListenerCallback callback);
    void obtenerFotoPerfil(ObtenerFotoPerfilCallback callback);
    void obtenerUsuarioActual(ObtenerUsuarioActualCallback callback);



    interface ObtenerFotoPerfilCallback{
        void onFotoPerfilObtenida();
        void onFotoPerfilObtenidaError();
    }
    interface ObtenerUsuarioActualCallback{
        void onUsuarioObtenido();
        void onUsuarioObtenidoError();
    }
    interface LoguearUsuarioCallback{
        void onUsuarioLogueado();
        void onUsuarioLogueadoError();
    }
    interface DesloguearUsuarioCallback{
        void onUsuarioDeslogueado();
        void onUsuarioDeslogueadoError();
    }
    interface RegistrarUsuarioCallback{
        void onUsuarioRegistrado();
        void onUsuarioRegistradoError();
    }
    interface RegistrarUsuarioConFotoCallback{
        void onUsuarioRegistrado();
        void onUsuarioRegistradoError();
    }
    interface RegistrarUsuarioAmpliadoCallback{
        void onUsuarioRegistrado();
        void onUsuarioRegistradoError();
    }

    interface ComprobarUsuarioRegistradoCallback{
        void onUsuarioRegistrado();
        void onUsuarioRegistradoError();
    }

    interface CancelarRegistroUsuarioCallback{
        void onRegistroCancelado();
        void onRegistroCanceladoError();
    }

    interface IniciarListenerCallback{
        void onUsuarioRegistrado();
        void onUsuarioNoRegistrado();
    }

    interface DetenerListenerCallback{
        void onListenerDetenido();
        void onListenerDetenidoError();
    }
}
