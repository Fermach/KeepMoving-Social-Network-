package com.example.fermach.keepmoving.Listado_Quedadas.Listado_Completo_Quedadas;

import com.example.fermach.keepmoving.Modelos.Quedada.Quedada;

import java.util.List;

/**
 * Created by Fermach on 27/03/2018.
 */

public interface ListadoQuedadasGeneralContract {
    interface View {
      void onQuedadasObtenidas(List<Quedada> quedadas);
      void onQuedadasObtenidasError();
      void mostrarQuedadasNumero(List<Quedada> quedadas);


    }
    interface Presenter {
      void obtenerQuedadas();

    }

}
