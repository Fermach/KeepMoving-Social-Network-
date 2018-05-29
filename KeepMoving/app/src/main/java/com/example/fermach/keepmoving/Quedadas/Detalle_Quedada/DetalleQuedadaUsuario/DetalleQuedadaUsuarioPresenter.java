package com.example.fermach.keepmoving.Quedadas.Detalle_Quedada.DetalleQuedadaUsuario;

import com.example.fermach.keepmoving.Modelos.Quedada.QuedadasRepository;

/**
 * Created by Fermach on 27/03/2018.
 */

public class DetalleQuedadaUsuarioPresenter implements DetalleQuedadaUsuarioContract.Presenter{
    private QuedadasRepository repository;
    private DetalleQuedadaUsuarioContract.View view;



    public DetalleQuedadaUsuarioPresenter(DetalleQuedadaUsuarioContract.View view) {
        this.view = view;
        this.repository = QuedadasRepository.getInstance();
    }



}
