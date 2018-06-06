package com.example.fermach.keepmoving.Quedadas.Listado_Quedadas.Listado_Completo_Quedadas;

import com.example.fermach.keepmoving.Modelos.Quedada.PeticionQuedada;
import com.example.fermach.keepmoving.Modelos.Quedada.Quedada;

import java.util.List;

/**
 *
 * Interfaz que comunica la interfaz del listado de quedadas con el repositorio
 *
 */
public interface ListadoQuedadasGeneralContract {
    interface View {
      void onQuedadasObtenidas(List<Quedada> quedadas);
      void onQuedadasObtenidasError();
      void onPeticionesRecibidasObtenidas(List<PeticionQuedada> peticionesQuedadas);
      void onPeticionesRecibidasObtenidasError();
      void mostrarQuedadasNumero(List<Quedada> quedadas);


    }
    interface Presenter {
      void obtenerQuedadas();
      void obtenerPeticionesRecibidas();
    }

}
