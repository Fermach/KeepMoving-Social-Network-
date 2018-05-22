package com.example.fermach.keepmoving.Listado_Quedadas.Listado_Peticiones_Recibidas;

import com.example.fermach.keepmoving.Listado_Quedadas.Listado_Solicitudes_Enviadas.ListadoSolicitudesEnviadasContract;
import com.example.fermach.keepmoving.Modelos.Quedada.PeticionQuedada;
import com.example.fermach.keepmoving.Modelos.Quedada.QuedadaDataSource;
import com.example.fermach.keepmoving.Modelos.Quedada.QuedadasRepository;

import java.util.List;

/**
 * Created by Fermach on 27/03/2018.
 */

public class ListadoPeticionesRecibidasPresenter implements ListadoPeticionesRecibidasContract.Presenter{
    private QuedadasRepository repository;
    private ListadoPeticionesRecibidasContract.View view;

    public ListadoPeticionesRecibidasPresenter() {
        this.repository = QuedadasRepository.getInstance();
    }

    public ListadoPeticionesRecibidasPresenter(ListadoPeticionesRecibidasContract.View view) {
        this.view = view;
        this.repository = QuedadasRepository.getInstance();
    }


    @Override
    public void obtenerPeticionesRecibidas() {
        repository.obtenerPeticionesRecibidasQuedadas(new QuedadaDataSource.ObtenerPeticionesRecibidasQuedadasCallback() {
            @Override
            public void onSolicitudesQuedadasObtenidas(List<PeticionQuedada> peticionesQuedadas) {
                view.onPeticionesRecibidasObtenidas(peticionesQuedadas);
                view.mostrarPeticionesRecibidasNumero(peticionesQuedadas);
            }

            @Override
            public void onSolicitudesQuedadasObtenidasError() {

                view.onPeticionesRecibidasObtenidasError();
            }
        });
    }


}
