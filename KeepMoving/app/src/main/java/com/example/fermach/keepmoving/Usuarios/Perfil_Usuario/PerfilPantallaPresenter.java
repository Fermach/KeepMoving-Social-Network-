package com.example.fermach.keepmoving.Usuarios.Perfil_Usuario;

import com.example.fermach.keepmoving.Modelos.Usuario.Usuario;
import com.example.fermach.keepmoving.Modelos.Usuario.UsuariosDataSource;
import com.example.fermach.keepmoving.Modelos.Usuario.UsuariosRepository;

/**
 * Created by Fermach on 27/03/2018.
 */

public class PerfilPantallaPresenter implements PerfilPantallaContract.Presenter{
    private UsuariosRepository repository;
    private PerfilPantallaContract.View view;



    public PerfilPantallaPresenter(PerfilPantallaContract.View view) {
        this.view = view;
        this.repository = UsuariosRepository.getInstance();
    }

    @Override
    public void ObtenerUsuarioActual() {
     repository.obtenerUsuarioActual(new UsuariosDataSource.ObtenerUsuarioActualCallback() {
      @Override
      public void onUsuarioObtenido(Usuario usuario) {
        view.onUsuarioActualObtenido(usuario);
      }

      @Override
      public void onUsuarioObtenidoError() {
        view.onUsuarioActualObtenidoError();
      }
     });
    }

    @Override
    public void CerrarSesion() {
        repository.desloguearUsuario(new UsuariosDataSource.DesloguearUsuarioCallback() {
            @Override
            public void onUsuarioDeslogueado() {
                view.onSesionCerrada();
            }

            @Override
            public void onUsuarioDeslogueadoError() {

            }
        });
    }

    @Override
    public void ObtenerFotoUsuarioActual() {
      repository.obtenerFotoPerfil(new UsuariosDataSource.ObtenerFotoPerfilCallback() {
       @Override
       public void onFotoPerfilObtenida(byte[] foto) {
         view.onFotoPerfilObtenida(foto);
       }

       @Override
       public void onFotoPerfilObtenidaError() {
          view.onFotoPerfilObtenidaError();
       }
      });
    }
}
