package com.example.fermach.keepmoving.Perfil_Usuario;

/**
 * Created by Fermach on 27/03/2018.
 */

public class PerfilPantallaPresenter implements  PerfilPantallaContract.Presenter{
   // private UsuariosRepository repository;
    private PerfilPantallaContract.View logginView;



    public PerfilPantallaPresenter(PerfilPantallaContract.View view) {
        this.logginView = view;
       // this.repository = UsuariosRepository.getInstance();
    }

}
