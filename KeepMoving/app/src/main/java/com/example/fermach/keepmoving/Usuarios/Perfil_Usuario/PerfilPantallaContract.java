package com.example.fermach.keepmoving.Usuarios.Perfil_Usuario;

import com.example.fermach.keepmoving.Modelos.Usuario.Usuario;

/**
 *  Interfaz para comunicar la vista con el presentador en la
 * pantalla de perfil del usuario
 */

public interface PerfilPantallaContract {
    interface View {
      void onUsuarioActualObtenido(Usuario usuario);
      void onUsuarioActualObtenidoError();
      void onFotoPerfilObtenida(byte[] foto);
      void onFotoPerfilObtenidaError();
      void onSesionCerrada();
    }
    interface Presenter {
      void ObtenerUsuarioActual();
      void CerrarSesion();
      void ObtenerFotoUsuarioActual();
    }

}
