package com.example.fermach.keepmoving.Usuarios.Registro.Registro_Ampliado;

import com.example.fermach.keepmoving.Modelos.Usuario.Usuario;
/**
 *  Interfaz para comunicar la vista con el presentador en la
 * pantalla de registro ampliado de usuario
 */
public interface RegistroAmpliadoPantallaContract {
    interface View {
        void onRegistroError();
        void onRegistro();
        void onDeslogueo();
        void onTOKENselecionado();
        void onTOKEN2selecionado();
        void onRegistroCancelado();
        void onRegistroCanceladoError();
        void onDeslogueoError();
        void onCorreoUsuarioActualObtenido(String correoUsuario);
        void onCorreoUsuarioActualObtenidoError();

    }
    interface Presenter {
        void cancelarRegistro();
        void registrarUsuario(Usuario usuario);
        void registrarUsuarioConFoto(Usuario usuario, byte[] foto);
        void desloguearUsuario();
        void obtenerCorreoUsuarioActual();
        void setTOKKEN(String TOKEN);
        void setTOKKEN_2(String menu);
    }

}
