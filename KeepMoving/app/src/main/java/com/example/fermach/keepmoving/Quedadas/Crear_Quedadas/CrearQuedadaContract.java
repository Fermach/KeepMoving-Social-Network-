package com.example.fermach.keepmoving.Quedadas.Crear_Quedadas;

import com.example.fermach.keepmoving.Modelos.Quedada.Quedada;

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
