package com.example.fermach.keepmoving.Peticion_Quedada;

import com.example.fermach.keepmoving.Modelos.Quedada.PeticionQuedada;
import com.example.fermach.keepmoving.Modelos.Quedada.Quedada;
import com.example.fermach.keepmoving.Modelos.Quedada.QuedadaDataSource;
import com.example.fermach.keepmoving.Modelos.Quedada.QuedadasRepository;
import com.example.fermach.keepmoving.Modelos.Usuario.Usuario;
import com.example.fermach.keepmoving.Modelos.Usuario.UsuariosDataSource;
import com.example.fermach.keepmoving.Modelos.Usuario.UsuariosRepository;
import com.example.fermach.keepmoving.Perfil_Usuario.PerfilPantallaContract;

/**
 * Created by Fermach on 27/03/2018.
 */

public class PeticionQuedadaPresenter implements PeticionQuedadaContract.Presenter{
    private QuedadasRepository quedadasRepository;
    private UsuariosRepository usuarioRepository;
    private PeticionQuedadaContract.View view;



    public PeticionQuedadaPresenter(PeticionQuedadaContract.View view) {
        this.view = view;
        this.quedadasRepository = QuedadasRepository.getInstance();
        this.usuarioRepository= UsuariosRepository.getInstance();
    }


    @Override
    public void EnviarSolicitud(PeticionQuedada peticionQuedada) {
        quedadasRepository.enviarSolicitud(peticionQuedada, new QuedadaDataSource.EnviarSolicitudCallback() {
              @Override
              public void onSolicitudEnviada() {
                  view.onSolicitudEnviada();
              }

              @Override
              public void onSolicitudEnviadaError() {
                   view.onSolicitudEnviadaError();
              }
          });
    }

    @Override
    public void obtenerUsuarioActual() {
        usuarioRepository.obtenerUsuarioActual(new UsuariosDataSource.ObtenerUsuarioActualCallback() {
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
}
