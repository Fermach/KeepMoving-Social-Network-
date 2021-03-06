package com.example.fermach.keepmoving.Quedadas.Listado_Quedadas.Listado_Solicitudes_Enviadas;

import com.example.fermach.keepmoving.Modelos.Quedada.PeticionQuedada;
import com.example.fermach.keepmoving.Modelos.Quedada.QuedadaDataSource;
import com.example.fermach.keepmoving.Modelos.Quedada.QuedadasRepository;

import java.util.List;

/**
 * Presentador de la pantalla del listado de solicitudes enviadas
 * el cuál se comunica con el repositorio
 *
 */

public class ListadoSolicitudesEnviadasPresenter implements ListadoSolicitudesEnviadasContract.Presenter{
    private QuedadasRepository repository;
    private ListadoSolicitudesEnviadasContract.View view;

    public ListadoSolicitudesEnviadasPresenter() {
        this.repository = QuedadasRepository.getInstance();
    }

    public ListadoSolicitudesEnviadasPresenter(ListadoSolicitudesEnviadasContract.View view) {
        this.view = view;
        this.repository = QuedadasRepository.getInstance();
    }


    @Override
    public void obtenerSolicitudes() {
         repository.obtenerSolicitudesQuedadas(new QuedadaDataSource.ObtenerSolicitudesQuedadasCallback() {
             @Override
             public void onSolicitudesQuedadasObtenidas(List<PeticionQuedada> solicitudesQuedadas) {
                 view.onSolicitudesQuedadasObtenidas(solicitudesQuedadas);
                 view.mostrarSolicitudesQuedadasNumero(solicitudesQuedadas);
             }

             @Override
             public void onSolicitudesQuedadasObtenidasError() {
                 view.onSolicitudesQuedadasObtenidasError();
             }
         });
    }
}
