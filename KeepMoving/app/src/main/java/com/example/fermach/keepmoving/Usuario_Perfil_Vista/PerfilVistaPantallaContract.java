package com.example.fermach.keepmoving.Usuario_Perfil_Vista;

import com.example.fermach.keepmoving.Modelos.Usuario.Usuario;

/**
 * Created by Fermach on 27/03/2018.
 */

public interface PerfilVistaPantallaContract {
    interface View {
      void onUsuarioObtenido(Usuario usuario);
      void onUsuarioObtenidoError();
      void onFotoPerfilObtenida(byte[] foto);
      void onFotoPerfilObtenidaError();

    }
    interface Presenter {
      void ObtenerUsuarioPorUid(String uid);
      void ObtenerFotoUsuario(String uid);
    }

}
