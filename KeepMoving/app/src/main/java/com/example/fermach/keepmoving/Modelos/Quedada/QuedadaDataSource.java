package com.example.fermach.keepmoving.Modelos.Quedada;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fermach on 29/03/2018.
 */

public interface QuedadaDataSource {

    void crearQuedada(Quedada quedada, CrearQuedadaCallback callback);
    void eliminarQuedada(String uid, EliminarQuedadaCallback callback);
    void editarQuedada(String uid, Quedada quedada, EditarQuedadaCallback callback);
    void obtenerQuedadas( ObtenerQuedadasCallback callback);
    void obtenerQuedadasUsuario(ObtenerQuedadasCallback callback);


    interface CrearQuedadaCallback{
        void onQuedadaCreada();
        void onQuedadaCreadaError();
    }
    interface EliminarQuedadaCallback{
        void onQuedadaEliminada();
        void onQuedadaEliminadaError();
    }
    interface EditarQuedadaCallback{
        void onQuedadaEditada();
        void onQuedadaEditadaError();
    }
    interface ObtenerQuedadasCallback{
        void onQuedadasObtenidas(List<Quedada> quedadas);
        void onQuedadasObtenidasError();
    }

}
