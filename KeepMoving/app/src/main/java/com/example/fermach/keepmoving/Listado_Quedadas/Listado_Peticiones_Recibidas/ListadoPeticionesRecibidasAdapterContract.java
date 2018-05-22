package com.example.fermach.keepmoving.Listado_Quedadas.Listado_Peticiones_Recibidas;

import com.example.fermach.keepmoving.Modelos.Quedada.PeticionQuedada;

import java.util.List;

/**
 * Created by Fermach on 27/03/2018.
 */

public interface ListadoPeticionesRecibidasAdapterContract {
    interface View {
      void onEstadoCambiado();
      void onEstadoCambiadoError();

    }
    interface Presenter {
      void cambiarEstadoQuedada(PeticionQuedada peticionQuedada);

    }

}
