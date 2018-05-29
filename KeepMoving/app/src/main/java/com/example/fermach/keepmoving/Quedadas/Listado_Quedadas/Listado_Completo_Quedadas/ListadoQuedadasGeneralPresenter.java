package com.example.fermach.keepmoving.Quedadas.Listado_Quedadas.Listado_Completo_Quedadas;

import com.example.fermach.keepmoving.Modelos.Quedada.Quedada;
import com.example.fermach.keepmoving.Modelos.Quedada.QuedadaDataSource;
import com.example.fermach.keepmoving.Modelos.Quedada.QuedadasRepository;

import java.util.List;

/**
 * Created by Fermach on 27/03/2018.
 */

public class ListadoQuedadasGeneralPresenter implements ListadoQuedadasGeneralContract.Presenter{
    private QuedadasRepository repository;
    private ListadoQuedadasGeneralContract.View view;

    public ListadoQuedadasGeneralPresenter() {
        this.repository = QuedadasRepository.getInstance();
    }

    public ListadoQuedadasGeneralPresenter(ListadoQuedadasGeneralContract.View view) {
        this.view = view;
        this.repository = QuedadasRepository.getInstance();
    }


    @Override
    public void obtenerQuedadas() {
        repository.obtenerQuedadas(new QuedadaDataSource.ObtenerQuedadasCallback() {
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


}