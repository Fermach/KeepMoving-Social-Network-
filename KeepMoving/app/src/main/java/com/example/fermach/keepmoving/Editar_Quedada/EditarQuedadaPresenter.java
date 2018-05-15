package com.example.fermach.keepmoving.Editar_Quedada;

import com.example.fermach.keepmoving.Editar_Usuario.EditarUsuarioContract;
import com.example.fermach.keepmoving.Modelos.Quedada.Quedada;
import com.example.fermach.keepmoving.Modelos.Quedada.QuedadaDataSource;
import com.example.fermach.keepmoving.Modelos.Quedada.QuedadasRepository;
import com.example.fermach.keepmoving.Modelos.Usuario.Usuario;
import com.example.fermach.keepmoving.Modelos.Usuario.UsuariosRepository;
import com.google.android.gms.games.quest.QuestEntity;

/**
 * Created by Fermach on 27/03/2018.
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
