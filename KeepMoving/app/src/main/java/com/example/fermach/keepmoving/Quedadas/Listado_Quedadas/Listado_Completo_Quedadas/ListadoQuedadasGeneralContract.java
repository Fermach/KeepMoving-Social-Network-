package com.example.fermach.keepmoving.Quedadas.Listado_Quedadas.Listado_Completo_Quedadas;

import com.example.fermach.keepmoving.Modelos.Quedada.PeticionQuedada;
import com.example.fermach.keepmoving.Modelos.Quedada.Quedada;

import java.util.List;

/**
 * Created by Fermach on 27/03/2018.
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
