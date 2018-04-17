package com.example.fermach.keepmoving.Registro.Registro_Ampliado;

import com.example.fermach.keepmoving.Modelos.Usuario.Usuario;

/**
 * Created by Fermach on 27/03/2018.
 */

public interface RegistroAmpliadoPantallaContract {
    interface View {
        void onRegistroError();
        void onRegistro();
        void onDeslogueo();
        void onDeslogueoError();

    }
    interface Presenter {
        void registrarUsuario(Usuario usuario);
        void registrarUsuarioConFoto(Usuario usuario);
        void desloguearUsuario();
    }

}
