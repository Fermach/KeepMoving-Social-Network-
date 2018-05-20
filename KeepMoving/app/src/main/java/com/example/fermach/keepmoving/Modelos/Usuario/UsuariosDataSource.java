package com.example.fermach.keepmoving.Modelos.Usuario;

/**
 * Created by Fermach on 29/03/2018.
 */

public interface UsuariosDataSource {

    void loguearUsuario(Usuario usuario, LoguearUsuarioCallback callback);
    void editarUsuario(Usuario usuario,byte[] foto, EditarUsuarioCallback callback);
    void desloguearUsuario(DesloguearUsuarioCallback callback);
    void registrarUsuario(Usuario usuario, RegistrarUsuarioCallback callback);
    void registrarUsuarioAmpliadoConFoto(Usuario usuario,byte[] foto, RegistrarUsuarioConFotoCallback callback);
    void registrarUsuarioAmpliado(Usuario usuario, RegistrarUsuarioAmpliadoCallback callback);
    void comprobarUsuarioRegistrado(ComprobarUsuarioRegistradoCallback callback);
    void cancelarRegistroUsuario(CancelarRegistroUsuarioCallback callback);
    void iniciarListener( IniciarListenerCallback callback);
    void obtenerFotoPerfil(ObtenerFotoPerfilCallback callback);
    void obtenerUsuarioActual(ObtenerUsuarioActualCallback callback);
    void obtenerUidUsuarioActual(ObtenerUidUsuarioActualCallback callback);
    void obtenerCorreoUsuarioActual(ObtenerCorreoUsuarioActualCallback callback);
    void setTOKEN(String TOKEN, SeleccionarTOKENCallback callback);


    interface SeleccionarTOKENCallback{
        void onTOKENseleccionado();

    }

    interface EditarUsuarioCallback{
        void onUsuarioEditado();
        void onUsuarioEditadoError();
    }
    interface ObtenerFotoPerfilCallback{
        void onFotoPerfilObtenida(byte[] foto);
        void onFotoPerfilObtenidaError();
    }
    interface ObtenerUsuarioActualCallback{
        void onUsuarioObtenido(Usuario usuario);
        void onUsuarioObtenidoError();
    }
    interface ObtenerUidUsuarioActualCallback{
        void onUsuarioObtenido(String uid);
        void onUsuarioObtenidoError();
    }
    interface ObtenerCorreoUsuarioActualCallback{
        void onCorreoUsuarioObtenido(String correoUsuario);
        void onCorreoUsuarioObtenidoError();
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
        void onUsuarioRegistrado(String TOKEN);
        void onUsuarioNoRegistrado(String TOKEN);
    }

    interface DetenerListenerCallback{
        void onListenerDetenido();
        void onListenerDetenidoError();
    }
}
