package com.example.fermach.keepmoving.Listado_Quedadas.Listado_Peticiones_Recibidas;

import com.example.fermach.keepmoving.Modelos.Quedada.PeticionQuedada;

import java.util.List;

/**
 * Created by Fermach on 27/03/2018.
 */

public interface ListadoPeticionesRecibidasContract {
    interface View {
      void onPeticionesRecibidasObtenidas(List<PeticionQuedada> peticionesQuedadas);
      void onPeticionesRecibidasObtenidasError();
      void mostrarPeticionesRecibidasNumero(List<PeticionQuedada> peticionesQuedadas);

    }
    interface Presenter {
      void obtenerPeticionesRecibidas();

    }

}
