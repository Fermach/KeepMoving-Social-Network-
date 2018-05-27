package com.example.fermach.keepmoving.Listado_Quedadas.Listado_Peticiones_Recibidas;

import com.example.fermach.keepmoving.Listado_Quedadas.Listado_Solicitudes_Enviadas.ListadoSolicitudesEnviadasContract;
import com.example.fermach.keepmoving.Modelos.Quedada.PeticionQuedada;
import com.example.fermach.keepmoving.Modelos.Quedada.QuedadaDataSource;
import com.example.fermach.keepmoving.Modelos.Quedada.QuedadasRepository;
import com.example.fermach.keepmoving.Modelos.Usuario.Usuario;
import com.example.fermach.keepmoving.Modelos.Usuario.UsuariosDataSource;
import com.example.fermach.keepmoving.Modelos.Usuario.UsuariosRepository;

import java.util.List;

/**
 * Created by Fermach on 27/03/2018.
 */

public class ListadoPeticionesRecibidasPresenter implements ListadoPeticionesRecibidasContract.Presenter{
    private QuedadasRepository quedadasRepository;
    private UsuariosRepository usuariosRepository;
    private ListadoPeticionesRecibidasContract.View view;

    public ListadoPeticionesRecibidasPresenter() {
        this.quedadasRepository = QuedadasRepository.getInstance();
        this.usuariosRepository = UsuariosRepository.getInstance();
    }

    public ListadoPeticionesRecibidasPresenter(ListadoPeticionesRecibidasContract.View view) {
        this.view = view;
        this.quedadasRepository = QuedadasRepository.getInstance();
        this.usuariosRepository = UsuariosRepository.getInstance();


    }


    @Override
    public void obtenerPeticionesRecibidas() {
        quedadasRepository.obtenerPeticionesRecibidasQuedadas(new QuedadaDataSource.ObtenerPeticionesRecibidasQuedadasCallback() {
            @Override
            public void onSolicitudesQuedadasObtenidas(List<PeticionQuedada> peticionesQuedadas) {


                if(peticionesQuedadas.isEmpty()){


                }else {
                    view.onPeticionesRecibidasObtenidas(peticionesQuedadas);
                    view.mostrarPeticionesRecibidasNumero(peticionesQuedadas);
                }
            }

            @Override
            public void onSolicitudesQuedadasObtenidasError() {

                view.onPeticionesRecibidasObtenidasError();
            }
        });
    }

    @Override
    public void cambiarEstadoQuedada(PeticionQuedada peticionQuedada) {
        quedadasRepository.cambiarEstadoQuedada(peticionQuedada, new QuedadaDataSource.CambiarEstadoCallback() {
            @Override
            public void onEstadoCambiado() {
                view.onEstadoCambiado();
            }

            @Override
            public void onEstadoCambiadoError() {

                view.onEstadoCambiadoError();
            }
        });
    }

    @Override
    public void obtenerFotoUsuario(String uid, PeticionQuedada pQuedada) {
        usuariosRepository.obtenerFotoPerfilUsuario(uid, pQuedada,new UsuariosDataSource.ObtenerFotoPerfilUsuarioCallback() {
            @Override
            public void onFotoUsuarioPerfilObtenida(byte[] foto,  PeticionQuedada pQuedada) {
                view.onFotoObtenida(foto,  pQuedada);
            }

            @Override
            public void onFotoUsuarioPerfilObtenidaError() {
                view.onFotoObtenidaError();
            }
        });
    }

    public Boolean isOnlineNet() {

        try {
            Process p = java.lang.Runtime.getRuntime().exec("ping -c 1 www.google.es");

            int val           = p.waitFor();
            boolean reachable = (val == 0);
            return reachable;

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }


}
