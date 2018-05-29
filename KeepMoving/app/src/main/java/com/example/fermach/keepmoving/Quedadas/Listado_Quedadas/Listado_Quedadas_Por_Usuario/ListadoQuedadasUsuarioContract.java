package com.example.fermach.keepmoving.Quedadas.Listado_Quedadas.Listado_Quedadas_Por_Usuario;

import com.example.fermach.keepmoving.Modelos.Quedada.Quedada;

import java.util.List;

/**
 * Created by Fermach on 27/03/2018.
 */

public interface ListadoQuedadasUsuarioContract {
    interface View {
      void onQuedadasObtenidas(List<Quedada> quedadas);
      void onQuedadasObtenidasError();
      void onQuedadaEliminada();
      void onQuedadaEliminadaError();
      void mostrarQuedadasNumero(List<Quedada> quedadas);


    }
    interface Presenter {
      void obtenerQuedadas();
      void borrarQuedada(String id_quedada);
    }

}
