package com.example.fermach.keepmoving.Editar_Usuario;

import android.util.Log;

import com.example.fermach.keepmoving.Modelos.Usuario.Usuario;
import com.example.fermach.keepmoving.Modelos.Usuario.UsuariosDataSource;
import com.example.fermach.keepmoving.Modelos.Usuario.UsuariosRepository;
import com.example.fermach.keepmoving.Registro.Registro_Ampliado.RegistroAmpliadoPantallaContract;

/**
 * Created by Fermach on 27/03/2018.
 */

public class EditarUsuarioPresenter implements  EditarUsuarioContract.Presenter{
    private UsuariosRepository repository;
    private EditarUsuarioContract.View view;



    public EditarUsuarioPresenter(EditarUsuarioContract.View view) {
        this.view = view;
        repository = UsuariosRepository.getInstance();
       //this.repository = UsuariosRepository.getInstance();
    }



    @Override
    public void editarUsuario(Usuario usuario,byte[] foto) {
          repository.editarUsuario(usuario, foto, new UsuariosDataSource.EditarUsuarioCallback() {
              @Override
              public void onUsuarioEditado() {
                  view.onUsuarioEditado();
              }

              @Override
              public void onUsuarioEditadoError() {
                 view.onUsuarioEditadoError();
              }
          });
    }
}
