package com.example.fermach.keepmoving.Usuarios.Loggin;

import com.example.fermach.keepmoving.Modelos.Usuario.Usuario;

/**
 * Interfaz para comunicar la vista con el presentador en la
 * pantalla de loggin
 */

public interface LogginPantallaContract {
    interface View {
        void onSesionIniciadaError();
        void onSesionIniciada();
        void onTOKENselecionado();

    }
    interface Presenter {
        void loggearUsuario(Usuario usuario);
        void comprobarRegistroDeUsuario();
        void setTOKEN(String TOKKEN);

    }

}
