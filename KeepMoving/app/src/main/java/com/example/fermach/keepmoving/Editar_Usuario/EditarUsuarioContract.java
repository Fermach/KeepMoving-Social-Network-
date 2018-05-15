package com.example.fermach.keepmoving.Editar_Usuario;

import com.example.fermach.keepmoving.Modelos.Usuario.Usuario;

/**
 * Created by Fermach on 27/03/2018.
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
