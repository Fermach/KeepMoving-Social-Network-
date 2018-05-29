package com.example.fermach.keepmoving.Quedadas.Listado_Quedadas.Listado_Quedadas_Por_Usuario;

import com.example.fermach.keepmoving.Modelos.Quedada.Quedada;
import com.example.fermach.keepmoving.Modelos.Quedada.QuedadaDataSource;
import com.example.fermach.keepmoving.Modelos.Quedada.QuedadasRepository;

import java.util.List;

/**
 * Created by Fermach on 27/03/2018.
 */

public class ListadoQuedadasUsuarioPresenter implements ListadoQuedadasUsuarioContract.Presenter{
    private QuedadasRepository repository;
    private ListadoQuedadasUsuarioContract.View view;

    public ListadoQuedadasUsuarioPresenter() {
        this.repository = QuedadasRepository.getInstance();
    }

    public ListadoQuedadasUsuarioPresenter(ListadoQuedadasUsuarioContract.View view) {
        this.view = view;
        this.repository = QuedadasRepository.getInstance();
    }


    @Override
    public void obtenerQuedadas() {
        repository.obtenerQuedadasUsuario(new QuedadaDataSource.ObtenerQuedadasCallback() {
            @Override
            public void onQuedadasObtenidas(List<Quedada> quedadas) {
                view.onQuedadasObtenidas(quedadas);
                view.mostrarQuedadasNumero(quedadas);
            }

            @Override
            public void onQuedadasObtenidasError() {
                 view.onQuedadasObtenidasError();
            }
        });
    }

    @Override
    public void borrarQuedada(String id_quedada) {
      repository.eliminarQuedada(id_quedada, new QuedadaDataSource.EliminarQuedadaCallback() {
          @Override
          public void onQuedadaEliminada() {
              view.onQuedadaEliminada();
          }

          @Override
          public void onQuedadaEliminadaError() {
              view.onQuedadaEliminadaError();
          }
      });
    }
}
