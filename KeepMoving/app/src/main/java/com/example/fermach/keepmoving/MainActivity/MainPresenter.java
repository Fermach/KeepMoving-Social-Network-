package com.example.fermach.keepmoving.MainActivity;

import com.example.fermach.keepmoving.Modelos.Usuario.UsuariosDataSource;
import com.example.fermach.keepmoving.Modelos.Usuario.UsuariosRepository;

/**
 * Presentador de la actividad principal
 * el cuál se comunica con el repositorio
 *
 * Created by Fermach on 21/04/2018.
 */

public class MainPresenter implements MainContract.Presenter {

     private UsuariosRepository usuariosRepository;
     private MainContract.View view;

    public MainPresenter(MainContract.View view) {
        this.usuariosRepository= UsuariosRepository.getInstance();
        this.view= view;
    }

    /**
     * Se llama a la API para cerrar sesión con el usuario
     */
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

    /**
     * Settea el token en el repositrio para realizar una acción u otra
     *
     * @param TOKKEN
     */
    @Override
    public void setTOKEN(String TOKKEN) {
        usuariosRepository.setTOKEN(TOKKEN, new UsuariosDataSource.SeleccionarTOKENCallback() {
            @Override
            public void onTOKENseleccionado() {
                view.onTOKENseleccionado();
            }
        });
    }

    /**
     * Inicia el escuchador  para ver si un usuario está registrado o no
     */
    @Override
    public void iniciarListenerFire() {
        usuariosRepository.iniciarListener(new UsuariosDataSource.IniciarListenerCallback() {
            @Override
            public void onUsuarioRegistrado(String TOKEN) {
                view.onUsuarioRegistrado(TOKEN);
            }

            @Override
            public void onUsuarioNoRegistrado(String TOKEN) {
                view.onUsuarioNoRegistrado(TOKEN);
            }
        });
    }


}
