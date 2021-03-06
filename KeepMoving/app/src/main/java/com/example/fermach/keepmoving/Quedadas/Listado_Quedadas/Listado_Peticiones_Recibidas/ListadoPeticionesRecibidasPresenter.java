package com.example.fermach.keepmoving.Quedadas.Listado_Quedadas.Listado_Peticiones_Recibidas;

import com.example.fermach.keepmoving.Modelos.Quedada.PeticionQuedada;
import com.example.fermach.keepmoving.Modelos.Quedada.QuedadaDataSource;
import com.example.fermach.keepmoving.Modelos.Quedada.QuedadasRepository;
import com.example.fermach.keepmoving.Modelos.Usuario.UsuariosDataSource;
import com.example.fermach.keepmoving.Modelos.Usuario.UsuariosRepository;

import java.util.List;

/**
 * Presentador de la pantalla del listado de peticiones recibidas
 * el cuál se comunica con el repositorio
 *
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
    public void obtenerFotoUsuario(String uid, final PeticionQuedada pQuedada) {
        usuariosRepository.obtenerFotoPerfilUsuario(uid, pQuedada,new UsuariosDataSource.ObtenerFotoPerfilUsuarioCallback() {
            @Override
            public void onFotoUsuarioPerfilObtenida(byte[] foto,  PeticionQuedada pQuedada) {
                view.onFotoObtenida(foto,  pQuedada);
            }

            @Override
            public void onFotoUsuarioPerfilObtenidaError(PeticionQuedada pQuedada) {
                view.onFotoObtenidaError(  pQuedada);
            }
        });
    }




}
