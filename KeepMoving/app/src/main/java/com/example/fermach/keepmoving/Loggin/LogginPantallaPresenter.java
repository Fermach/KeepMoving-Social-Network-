package com.example.fermach.keepmoving.Loggin;

import android.util.Log;

import com.example.fermach.keepmoving.Modelos.Usuario.Usuario;
import com.example.fermach.keepmoving.Modelos.Usuario.UsuariosDataSource;
import com.example.fermach.keepmoving.Modelos.Usuario.UsuariosRepository;

/**
 * Created by Fermach on 27/03/2018.
 */

public class LogginPantallaPresenter implements  LogginPantallaContract.Presenter{
    private UsuariosRepository repository;
    private LogginPantallaContract.View logginView;



    public LogginPantallaPresenter(LogginPantallaContract.View view) {
        this.logginView = view;
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
    public void iniciarListenerFire() {
        repository.iniciarListener(new UsuariosDataSource.IniciarListenerCallback() {
            @Override
            public void onListenerIniciado() {
                Log.i("P LISTENER: ","INICIADO");
            }

            @Override
            public void onListenerIniciadoError() {
                Log.i("P LISTENER: ","NO INICIADO");
            }
        });
    }

    @Override
    public void detenerListenerFire() {
         repository.detenerListener(new UsuariosDataSource.DetenerListenerCallback() {
             @Override
             public void onListenerDetenido() {
                 Log.i("P LISTENER: ","DETENIDO");
             }

             @Override
             public void onListenerDetenidoError() {
                 Log.i("P LISTENER: ","NO DETENIDO");

             }
         });
    }
}
