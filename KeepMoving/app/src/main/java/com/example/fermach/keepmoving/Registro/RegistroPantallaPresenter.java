package com.example.fermach.keepmoving.Registro;

import android.util.Log;

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
       // this.repository = UsuariosRepository.getInstance();
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
}
