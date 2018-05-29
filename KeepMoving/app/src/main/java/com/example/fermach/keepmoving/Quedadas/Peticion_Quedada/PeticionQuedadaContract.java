package com.example.fermach.keepmoving.Quedadas.Peticion_Quedada;

import com.example.fermach.keepmoving.Modelos.Quedada.PeticionQuedada;
import com.example.fermach.keepmoving.Modelos.Usuario.Usuario;

/**
 * Created by Fermach on 27/03/2018.
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
