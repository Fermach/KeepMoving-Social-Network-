package com.example.fermach.keepmoving.Registro.Registro_Ampliado;

import android.util.Log;

import com.example.fermach.keepmoving.Modelos.Usuario.Usuario;
import com.example.fermach.keepmoving.Modelos.Usuario.UsuariosDataSource;
import com.example.fermach.keepmoving.Modelos.Usuario.UsuariosRepository;
import com.example.fermach.keepmoving.Registro.Registro_Basico.RegistroPantallaContract;

/**
 * Created by Fermach on 27/03/2018.
 */

public class RegistroAmpliadoPantallaPresenter implements  RegistroAmpliadoPantallaContract.Presenter{
    private UsuariosRepository repository;
    private RegistroAmpliadoPantallaContract.View registroView;



    public RegistroAmpliadoPantallaPresenter(RegistroAmpliadoPantallaContract.View view) {
        this.registroView = view;
        repository = UsuariosRepository.getInstance();
       //this.repository = UsuariosRepository.getInstance();
    }




    @Override
    public void registrarUsuario(Usuario usuario) {
        repository.registrarUsuarioAmpliado(usuario, new UsuariosDataSource.RegistrarUsuarioAmpliadoCallback() {
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
    public void registrarUsuarioConFoto(Usuario usuario) {
        repository.registrarUsuarioAmpliadoConFoto(usuario, new UsuariosDataSource.RegistrarUsuarioConFotoCallback() {
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
    public void desloguearUsuario() {
        repository.desloguearUsuario(new UsuariosDataSource.DesloguearUsuarioCallback() {
            @Override
            public void onUsuarioDeslogueado() {
                Log.i("DESLOGUEO","-------------");
                registroView.onDeslogueo();
            }

            @Override
            public void onUsuarioDeslogueadoError() {

            }
        });
    }


}
