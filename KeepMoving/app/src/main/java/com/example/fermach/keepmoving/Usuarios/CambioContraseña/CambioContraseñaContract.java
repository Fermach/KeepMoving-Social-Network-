package com.example.fermach.keepmoving.Usuarios.CambioContraseña;

import com.example.fermach.keepmoving.Modelos.Usuario.Usuario;

/**
 * Interfaz para comunicar la vista con el presentador en la
 * pantalla de cambio de contraseña
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
