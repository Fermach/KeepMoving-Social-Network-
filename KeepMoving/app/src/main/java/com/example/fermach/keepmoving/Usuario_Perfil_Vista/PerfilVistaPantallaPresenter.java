package com.example.fermach.keepmoving.Usuario_Perfil_Vista;

import com.example.fermach.keepmoving.Modelos.Quedada.PeticionQuedada;
import com.example.fermach.keepmoving.Modelos.Usuario.Usuario;
import com.example.fermach.keepmoving.Modelos.Usuario.UsuariosDataSource;
import com.example.fermach.keepmoving.Modelos.Usuario.UsuariosRepository;
import com.example.fermach.keepmoving.Perfil_Usuario.PerfilPantallaContract;

/**
 * Created by Fermach on 27/03/2018.
 */

public class PerfilVistaPantallaPresenter implements PerfilVistaPantallaContract.Presenter {
    private UsuariosRepository repository;
    private PerfilVistaPantallaContract.View view;


    public PerfilVistaPantallaPresenter(PerfilVistaPantallaContract.View view) {
        this.view = view;
        this.repository = UsuariosRepository.getInstance();
    }


    @Override
    public void ObtenerUsuarioPorUid(String uid) {
        repository.obtenerUsuarioPorUID(uid, new UsuariosDataSource.ObtenerUsuarioPorUIDCallback() {
            @Override
            public void onUsuarioObtenido(Usuario usuario) {
                view.onUsuarioObtenido(usuario);
            }

            @Override
            public void onUsuarioObtenidoError() {
                view.onUsuarioObtenidoError();
            }
        });
    }

    @Override
    public void ObtenerFotoUsuario(String uid) {
        PeticionQuedada peticionQuedada= new PeticionQuedada();
        repository.obtenerFotoPerfilUsuario(uid, peticionQuedada, new UsuariosDataSource.ObtenerFotoPerfilUsuarioCallback() {
            @Override
            public void onFotoUsuarioPerfilObtenida(byte[] foto, PeticionQuedada peticionQuedada1) {
                view.onFotoPerfilObtenida(foto);
            }

            @Override
            public void onFotoUsuarioPerfilObtenidaError() {

                view.onFotoPerfilObtenidaError();
            }
        });
    }

}