package com.example.fermach.keepmoving.Usuarios.CambioContraseña;

import com.example.fermach.keepmoving.Modelos.Usuario.Usuario;
import com.example.fermach.keepmoving.Modelos.Usuario.UsuariosDataSource;
import com.example.fermach.keepmoving.Modelos.Usuario.UsuariosRepository;
import com.example.fermach.keepmoving.Usuarios.Loggin.LogginPantallaContract;

/**
 *
 * Presentador  que comunica la pantalla de cambio de contraseña del usuario
 *  con el repositorio
 *
 *
 */
public class CambioContraseñaPresenter implements  CambioContraseñaContract.Presenter {
    private UsuariosRepository repository;
    private CambioContraseñaContract.View view;


    public CambioContraseñaPresenter(CambioContraseñaContract.View view) {
        this.view = view;
        repository = UsuariosRepository.getInstance();
        // this.repository = UsuariosRepository.getInstance();
    }


    @Override
    public void cambiarContraseña(String email) {
        repository.cambiarContraseña(email, new UsuariosDataSource.CambiarContraseñaCallback() {
            @Override
            public void onContraseñaCambiada() {
                view.onContraseñaCambiada();
            }

            @Override
            public void onContraseñaCambiadaError() {
                view.onContraseñaCambiadaError();
            }
        });
    }
}
