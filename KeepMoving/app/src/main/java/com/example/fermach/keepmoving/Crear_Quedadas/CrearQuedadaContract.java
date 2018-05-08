package com.example.fermach.keepmoving.Crear_Quedadas;

import com.example.fermach.keepmoving.Modelos.Quedada.Quedada;
import com.example.fermach.keepmoving.Modelos.Usuario.Usuario;

/**
 * Created by Fermach on 27/03/2018.
 */

public interface CrearQuedadaContract {
    interface View {
      void onQuedadaCreada();
      void onQuedadaCreadaError();

    }
    interface Presenter {
      void CrearQuedada(Quedada quedada);
    }

}
