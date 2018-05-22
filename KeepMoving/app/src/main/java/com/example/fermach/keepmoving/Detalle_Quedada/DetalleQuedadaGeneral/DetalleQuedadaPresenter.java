package com.example.fermach.keepmoving.Detalle_Quedada.DetalleQuedadaGeneral;

import android.util.Log;

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
    private UsuariosRepository usuariosRepository;
    private QuedadasRepository quedadasRepository;
    private DetalleQuedadaContract.View view;


    public DetalleQuedadaPresenter(DetalleQuedadaContract.View view) {
        this.view = view;
        this.usuariosRepository = UsuariosRepository.getInstance();
        this.quedadasRepository = QuedadasRepository.getInstance();
    }


    @Override
    public void obtenerUsuarioActual() {
        usuariosRepository.obtenerUidUsuarioActual(new UsuariosDataSource.ObtenerUidUsuarioActualCallback() {
            @Override
            public void onUsuarioObtenido(String uid) {
                view.onUsuarioActualObtenido(uid);
            }

            @Override
            public void onUsuarioObtenidoError() {

            }
        });
    }

    @Override
    public void verificarPeticionQuedada(Quedada quedada) {
        quedadasRepository.verificarPeticionQuedada(quedada, new QuedadaDataSource.VerificarPeticionQuedadaCallback() {
            @Override
            public void onQuedadaLibre() {

                Log.i("VERIFICACION_Q", "LIBREE");
                view.onPeticionLibre();
            }

            @Override
            public void onQuedadaOcupada() {

                Log.i("VERIFICACION_Q", "OCUPADA");

                view.onPeticionOcupada();
            }
        });
    }
}
