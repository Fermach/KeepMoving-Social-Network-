package com.example.fermach.keepmoving.Listado_Quedadas.Listado_Peticiones_Recibidas;

import com.example.fermach.keepmoving.Modelos.Quedada.PeticionQuedada;
import com.example.fermach.keepmoving.Modelos.Quedada.QuedadaDataSource;
import com.example.fermach.keepmoving.Modelos.Quedada.QuedadasRepository;

import java.util.List;

/**
 * Created by Fermach on 27/03/2018.
 */

public class ListadoPeticionesRecibidasAdapterPresenter implements ListadoPeticionesRecibidasAdapterContract.Presenter{
    private QuedadasRepository repository;
    private ListadoPeticionesRecibidasAdapterContract.View view;

    public ListadoPeticionesRecibidasAdapterPresenter() {
        this.repository = QuedadasRepository.getInstance();
    }

    public ListadoPeticionesRecibidasAdapterPresenter(ListadoPeticionesRecibidasAdapterContract.View view) {
        this.view = view;
        this.repository = QuedadasRepository.getInstance();
    }

    @Override
    public void cambiarEstadoQuedada(PeticionQuedada peticionQuedada) {
        repository.cambiarEstadoQuedada(peticionQuedada, new QuedadaDataSource.CambiarEstadoCallback() {
            @Override
            public void onEstadoCambiado() {
                view.onEstadoCambiado();
            }

            @Override
            public void onEstadoCambiadoError() {

                view.onEstadoCambiadoError();
            }
        });
    }


}
