package com.example.fermach.keepmoving.Quedadas.Crear_Quedadas;

import com.example.fermach.keepmoving.Modelos.Quedada.Quedada;
import com.example.fermach.keepmoving.Modelos.Quedada.QuedadaDataSource;
import com.example.fermach.keepmoving.Modelos.Quedada.QuedadasRepository;

/**
 * Created by Fermach on 27/03/2018.
 */

public class CrearQuedadaPresenter implements CrearQuedadaContract.Presenter{
    private QuedadasRepository repository;
    private CrearQuedadaContract.View view;



    public CrearQuedadaPresenter(CrearQuedadaContract.View view) {
        this.view = view;
        this.repository = QuedadasRepository.getInstance();
    }


    @Override
    public void CrearQuedada(Quedada quedada) {
          repository.crearQuedada(quedada, new QuedadaDataSource.CrearQuedadaCallback() {
              @Override
              public void onQuedadaCreada() {

                  view.onQuedadaCreada();
              }

              @Override
              public void onQuedadaCreadaError() {
                  view.onQuedadaCreadaError();
              }
          });
    }
}