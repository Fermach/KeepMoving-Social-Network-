package com.example.fermach.keepmoving.Modelos.Quedada;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fermach on 29/03/2018.
 */

public interface QuedadaDataSource {

    void crearQuedada(Quedada quedada, CrearQuedadaCallback callback);
    void verificarPeticionQuedada(Quedada quedada, VerificarPeticionQuedadaCallback callback);
    void eliminarQuedada(String uid, EliminarQuedadaCallback callback);
    void enviarSolicitud(PeticionQuedada peticionQuedada, EnviarSolicitudCallback callback);
    void editarQuedada( Quedada quedada, EditarQuedadaCallback callback);
    void obtenerQuedadas( ObtenerQuedadasCallback callback);
    void cambiarEstadoQuedada( PeticionQuedada peticionQuedada, CambiarEstadoCallback callback);
    void obtenerSolicitudesQuedadas( ObtenerSolicitudesQuedadasCallback callback);
    void obtenerPeticionesRecibidasQuedadas( ObtenerPeticionesRecibidasQuedadasCallback callback);
    void obtenerQuedadasUsuario(ObtenerQuedadasCallback callback);

    interface VerificarPeticionQuedadaCallback{
        void onQuedadaLibre();
        void onQuedadaOcupada();
    }
    interface CrearQuedadaCallback{
        void onQuedadaCreada();
        void onQuedadaCreadaError();
    }
    interface EliminarQuedadaCallback{
        void onQuedadaEliminada();
        void onQuedadaEliminadaError();
    }
    interface EnviarSolicitudCallback{
        void onSolicitudEnviada();
        void onSolicitudEnviadaError();
    }
    interface EditarQuedadaCallback{
        void onQuedadaEditada();
        void onQuedadaEditadaError();
    }
    interface ObtenerQuedadasCallback{
        void onQuedadasObtenidas(List<Quedada> quedadas);
        void onQuedadasObtenidasError();
    }

    interface ObtenerSolicitudesQuedadasCallback{
        void onSolicitudesQuedadasObtenidas(List<PeticionQuedada> peticionesQuedadas);
        void onSolicitudesQuedadasObtenidasError();
    }
    interface ObtenerPeticionesRecibidasQuedadasCallback{
        void onSolicitudesQuedadasObtenidas(List<PeticionQuedada> peticionesQuedadas);
        void onSolicitudesQuedadasObtenidasError();
    }
    interface CambiarEstadoCallback{
        void onEstadoCambiado();
        void onEstadoCambiadoError();
    }

}
