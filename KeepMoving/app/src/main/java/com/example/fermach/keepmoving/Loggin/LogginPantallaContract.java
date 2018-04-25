package com.example.fermach.keepmoving.Loggin;

import com.example.fermach.keepmoving.Modelos.Usuario.Usuario;

/**
 * Created by Fermach on 27/03/2018.
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
