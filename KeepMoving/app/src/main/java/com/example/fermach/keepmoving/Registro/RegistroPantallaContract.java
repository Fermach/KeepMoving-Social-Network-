package com.example.fermach.keepmoving.Registro;

import com.example.fermach.keepmoving.Modelos.Usuario.Usuario;

/**
 * Created by Fermach on 27/03/2018.
 */

public interface RegistroPantallaContract {
    interface View {
        void onRegistroError();
        void onRegistro();
    }
    interface Presenter {
        void registrarUsuario(Usuario usuario);

    }

}
