package com.example.fermach.keepmoving.Detalle_Quedada.DetalleQuedadaGeneral;

import com.example.fermach.keepmoving.Modelos.Quedada.Quedada;
import com.example.fermach.keepmoving.Modelos.Quedada.QuedadaDataSource;
import com.example.fermach.keepmoving.Modelos.Quedada.QuedadasRepository;
import com.example.fermach.keepmoving.Modelos.Usuario.Usuario;
import com.example.fermach.keepmoving.Modelos.Usuario.UsuariosDataSource;
import com.example.fermach.keepmoving.Modelos.Usuario.UsuariosRepository;

/**
 * Created by Fermach on 27/03/2018.
 */

public class DetalleQuedadaPresenter implements DetalleQuedadaContract.Presenter{
    private UsuariosRepository repository;
    private DetalleQuedadaContract.View view;


    public DetalleQuedadaPresenter(DetalleQuedadaContract.View view) {
        this.view = view;
        this.repository = UsuariosRepository.getInstance();
    }


    @Override
    public void obtenerUsuarioActual() {
        repository.obtenerUidUsuarioActual(new UsuariosDataSource.ObtenerUidUsuarioActualCallback() {
            @Override
            public void onUsuarioObtenido(String uid) {
                view.onUsuarioActualObtenido(uid);
            }

            @Override
            public void onUsuarioObtenidoError() {

            }
        });
    }
}
