package com.example.fermach.keepmoving.Usuarios.Registro.Registro_Basico;

import com.example.fermach.keepmoving.Modelos.Usuario.Usuario;

/**
 * Interfaz para comunicar la vista con el presentador en la
 * pantalla de registro de usuario
 */
public interface RegistroPantallaContract {
    interface View {
        void onRegistroError(String error);
        void onRegistro();
        void onLogueo();
        void onTOKENselecionado();
        void onLogueoError();

    }
    interface Presenter {
        void registrarUsuario(Usuario usuario);
        void registrarUsuarioAmpliado(Usuario usuario);
        void loguearUsuario(Usuario usuario);
        void setTOKEN(String TOKKEN);

    }

}
