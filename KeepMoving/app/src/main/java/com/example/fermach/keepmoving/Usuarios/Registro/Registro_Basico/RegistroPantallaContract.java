package com.example.fermach.keepmoving.Usuarios.Registro.Registro_Basico;

import com.example.fermach.keepmoving.Modelos.Usuario.Usuario;

/**
 * Created by Fermach on 27/03/2018.
 */

public interface RegistroPantallaContract {
    interface View {
        void onRegistroError();
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
