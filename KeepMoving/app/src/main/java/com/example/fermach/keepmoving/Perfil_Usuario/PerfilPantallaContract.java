package com.example.fermach.keepmoving.Perfil_Usuario;

import com.example.fermach.keepmoving.Modelos.Usuario.Usuario;

/**
 * Created by Fermach on 27/03/2018.
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
