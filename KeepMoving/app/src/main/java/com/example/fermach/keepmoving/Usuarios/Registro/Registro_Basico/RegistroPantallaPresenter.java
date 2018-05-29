package com.example.fermach.keepmoving.Usuarios.Registro.Registro_Basico;

import com.example.fermach.keepmoving.Modelos.Usuario.Usuario;
import com.example.fermach.keepmoving.Modelos.Usuario.UsuariosDataSource;
import com.example.fermach.keepmoving.Modelos.Usuario.UsuariosRepository;

/**
 * Created by Fermach on 27/03/2018.
 */

public class RegistroPantallaPresenter implements  RegistroPantallaContract.Presenter{
    private UsuariosRepository repository;
    private RegistroPantallaContract.View registroView;



    public RegistroPantallaPresenter(RegistroPantallaContract.View view) {
        this.registroView = view;
        repository = UsuariosRepository.getInstance();
       //this.repository = UsuariosRepository.getInstance();
    }


    @Override
    public void registrarUsuario(Usuario usuario) {
       repository.registrarUsuario(usuario, new UsuariosDataSource.RegistrarUsuarioCallback() {
          @Override
          public void onUsuarioRegistrado() {
             registroView.onRegistro();
          }

          @Override
          public void onUsuarioRegistradoError() {
             registroView.onRegistroError();
          }
       });
    }

    @Override
    public void registrarUsuarioAmpliado(Usuario usuario) {
        repository.registrarUsuarioAmpliado(usuario, new UsuariosDataSource.RegistrarUsuarioAmpliadoCallback() {
            @Override
            public void onUsuarioRegistrado() {

            }

            @Override
            public void onUsuarioRegistradoError() {

            }
        });
    }

    @Override
    public void loguearUsuario(Usuario usuario) {
        repository.loguearUsuario(usuario, new UsuariosDataSource.LoguearUsuarioCallback() {
            @Override
            public void onUsuarioLogueado() {
                registroView.onLogueo();
            }

            @Override
            public void onUsuarioLogueadoError() {
                registroView.onLogueoError();
            }
        });
    }

    @Override
    public void setTOKEN(String TOKKEN) {
        repository.setTOKEN(TOKKEN, new UsuariosDataSource.SeleccionarTOKENCallback() {
            @Override
            public void onTOKENseleccionado() {
                registroView.onTOKENselecionado();
            }
        });
    }
}
