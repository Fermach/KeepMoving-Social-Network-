package com.example.fermach.keepmoving.MainActivity;

import com.example.fermach.keepmoving.Modelos.Usuario.UsuariosDataSource;
import com.example.fermach.keepmoving.Modelos.Usuario.UsuariosRepository;

/**
 * Created by Fermach on 21/04/2018.
 */

public class MainPresenter implements MainContract.Presenter {

     private UsuariosRepository usuariosRepository;
     private MainContract.View view;

    public MainPresenter(MainContract.View view) {
        this.usuariosRepository= UsuariosRepository.getInstance();
        this.view= view;
    }

    @Override
    public void cerrarSesion() {
      usuariosRepository.desloguearUsuario(new UsuariosDataSource.DesloguearUsuarioCallback() {
          @Override
          public void onUsuarioDeslogueado() {
              view.onSesionCerrada();

          }

          @Override
          public void onUsuarioDeslogueadoError() {
              view.onSesionCerradaError();
          }
      });
    }

    @Override
    public void iniciarListenerFire() {
        usuariosRepository.iniciarListener(new UsuariosDataSource.IniciarListenerCallback() {
            @Override
            public void onUsuarioRegistrado() {
                view.onUsuarioRegistrado();
            }

            @Override
            public void onUsuarioNoRegistrado() {
                view.onUsuarioNoRegistrado();
            }
        });
    }
}
