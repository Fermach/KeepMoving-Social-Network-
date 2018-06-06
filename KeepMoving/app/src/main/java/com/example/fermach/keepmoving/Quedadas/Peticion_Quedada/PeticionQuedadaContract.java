package com.example.fermach.keepmoving.Quedadas.Peticion_Quedada;

import com.example.fermach.keepmoving.Modelos.Quedada.PeticionQuedada;
import com.example.fermach.keepmoving.Modelos.Usuario.Usuario;

/**
 *
 * Interfaz que comunica la interfaz del listado de peticion a una
 * quedada con el repositorio
 *
 */
public interface PeticionQuedadaContract {
    interface View {
      void onSolicitudEnviada();
      void onSolicitudEnviadaError();
      void onUsuarioActualObtenido(Usuario usuario);
      void onUsuarioActualObtenidoError();

    }
    interface Presenter {
      void EnviarSolicitud(PeticionQuedada peticionQuedada);
      void obtenerUsuarioActual();
    }

}
