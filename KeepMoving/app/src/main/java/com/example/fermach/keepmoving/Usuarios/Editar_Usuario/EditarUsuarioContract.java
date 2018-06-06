package com.example.fermach.keepmoving.Usuarios.Editar_Usuario;

import com.example.fermach.keepmoving.Modelos.Usuario.Usuario;


/**
 * Interfaz para comunicar la vista con el presentador en la
 * pantalla de editar usuario
 */

public interface EditarUsuarioContract {
    interface View {
        void onUsuarioEditado();
        void onUsuarioEditadoError();

    }
    interface Presenter {
        void editarUsuario(Usuario usuario, byte[] foto);

    }

}
