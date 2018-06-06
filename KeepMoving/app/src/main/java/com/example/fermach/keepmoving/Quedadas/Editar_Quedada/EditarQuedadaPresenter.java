package com.example.fermach.keepmoving.Quedadas.Editar_Quedada;

import com.example.fermach.keepmoving.Modelos.Quedada.Quedada;
import com.example.fermach.keepmoving.Modelos.Quedada.QuedadaDataSource;
import com.example.fermach.keepmoving.Modelos.Quedada.QuedadasRepository;

/**
 * Presentador de la pantalla de editar quedadas
 * el cuál se comunica con el repositorio
 *
 *
 */

public class EditarQuedadaPresenter implements  EditarQuedadaContract.Presenter{
    private QuedadasRepository repository;
    private EditarQuedadaContract.View view;



    public EditarQuedadaPresenter(EditarQuedadaContract.View view) {
        this.view = view;
        repository = QuedadasRepository.getInstance();
       //this.repository = UsuariosRepository.getInstance();
    }


    @Override
    public void editarQuedada(Quedada quedada) {
     repository.editarQuedada(quedada, new QuedadaDataSource.EditarQuedadaCallback() {
         @Override
         public void onQuedadaEditada() {
             view.onQuedadaEditada();
         }

         @Override
         public void onQuedadaEditadaError() {

             view.onQuedadaEditadaError();
         }
     });
    }
}
