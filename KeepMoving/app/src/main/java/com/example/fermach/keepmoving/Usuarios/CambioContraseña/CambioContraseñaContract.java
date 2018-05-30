package com.example.fermach.keepmoving.Usuarios.CambioContraseña;

import com.example.fermach.keepmoving.Modelos.Usuario.Usuario;

/**
 * Created by Fermach on 27/03/2018.
 */

public interface CambioContraseñaContract {
    interface View {
        void onContraseñaCambiada();
        void onContraseñaCambiadaError();


    }
    interface Presenter {
        void cambiarContraseña(String email);

    }

}
