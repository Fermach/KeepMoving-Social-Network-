package com.example.fermach.keepmoving.Usuarios.Usuario_Perfil_Vista;

import com.example.fermach.keepmoving.Modelos.Usuario.Usuario;

/**
 *  Interfaz para comunicar la vista con el presentador en la
 * pantalla de visualización del perfil del usuario
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
