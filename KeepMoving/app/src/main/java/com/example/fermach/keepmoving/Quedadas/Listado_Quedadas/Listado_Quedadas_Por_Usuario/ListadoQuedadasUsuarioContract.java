package com.example.fermach.keepmoving.Quedadas.Listado_Quedadas.Listado_Quedadas_Por_Usuario;

import com.example.fermach.keepmoving.Modelos.Quedada.Quedada;

import java.util.List;

/**
 *
 * Interfaz que comunica la interfaz del listado de quedadas con el repositorio
 *
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
