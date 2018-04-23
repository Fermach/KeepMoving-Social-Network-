package com.example.fermach.keepmoving.MainActivity;

import com.example.fermach.keepmoving.Modelos.Usuario.Usuario;

/**
 * Created by Fermach on 27/03/2018.
 */

public interface MainContract {
    interface View {
        void onSesionCerrada();
        void onSesionCerradaError();
        void onUsuarioNoRegistrado();
        void onUsuarioRegistrado();
    }
    interface Presenter {
        void cerrarSesion();
        void iniciarListenerFire();

    }

}
