package com.example.fermach.keepmoving.Usuarios.Editar_Usuario;

import com.example.fermach.keepmoving.Modelos.Usuario.Usuario;
import com.example.fermach.keepmoving.Modelos.Usuario.UsuariosDataSource;
import com.example.fermach.keepmoving.Modelos.Usuario.UsuariosRepository;

/**
 *
 * Presentador  que comunica la pantalla de editar usuario
 *  con el repositorio
 *
 *
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
