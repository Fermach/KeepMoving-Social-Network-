package com.example.fermach.keepmoving.Usuarios.Loggin;

import com.example.fermach.keepmoving.Modelos.Usuario.Usuario;
import com.example.fermach.keepmoving.Modelos.Usuario.UsuariosDataSource;
import com.example.fermach.keepmoving.Modelos.Usuario.UsuariosRepository;

/**
 * Created by Fermach on 27/03/2018.
 */

public class LogginPantallaPresenter implements  LogginPantallaContract.Presenter {
    private UsuariosRepository repository;
    private LogginPantallaContract.View logginView;


    public LogginPantallaPresenter(LogginPantallaContract.View view) {
        this.logginView = view;
        repository = UsuariosRepository.getInstance();
        // this.repository = UsuariosRepository.getInstance();
    }


    @Override
    public void loggearUsuario(Usuario usuario) {
        repository.loguearUsuario(usuario, new UsuariosDataSource.LoguearUsuarioCallback() {
            @Override
            public void onUsuarioLogueado() {
                logginView.onSesionIniciada();
            }

            @Override
            public void onUsuarioLogueadoError() {
                logginView.onSesionIniciadaError();
            }
        });
    }

    @Override
    public void comprobarRegistroDeUsuario() {
        repository.comprobarUsuarioRegistrado(new UsuariosDataSource.ComprobarUsuarioRegistradoCallback() {
            @Override
            public void onUsuarioRegistrado() {
                logginView.onSesionIniciada();
            }

            @Override
            public void onUsuarioRegistradoError() {
                logginView.onSesionIniciadaError();
            }
        });
    }

    @Override
    public void setTOKEN(String TOKEN) {
        repository.setTOKEN(TOKEN, new UsuariosDataSource.SeleccionarTOKENCallback() {
            @Override
            public void onTOKENseleccionado() {
                logginView.onTOKENselecionado();
            }
        });
    }

}
