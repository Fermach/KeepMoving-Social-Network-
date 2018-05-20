package com.example.fermach.keepmoving.Modelos.Quedada;

import com.example.fermach.keepmoving.Modelos.API_FIREBASE.QuedadasFirebase;

import java.util.List;

/**
 * Created by Fermach on 29/03/2018.
 */

public class QuedadasRepository implements QuedadaDataSource {

    private static QuedadasRepository INSTANCIA = null;

    //Singletone del repositorio, para que solo pueda existir una instancia

    public static QuedadasRepository getInstance() {
        if (INSTANCIA == null) {
            INSTANCIA = new QuedadasRepository();
        }
        return INSTANCIA;
    }


    @Override
    public void crearQuedada(Quedada quedada, final CrearQuedadaCallback callback) {
       QuedadasFirebase quedadasFirebase= QuedadasFirebase.getInstance();
        quedadasFirebase.crearQuedada(quedada, new CrearQuedadaCallback() {
           @Override
           public void onQuedadaCreada() {
              callback.onQuedadaCreada();
           }

           @Override
           public void onQuedadaCreadaError() {
              callback.onQuedadaCreadaError();
           }
       });
    }

    @Override
    public void eliminarQuedada(String uid, final EliminarQuedadaCallback callback) {
        QuedadasFirebase quedadasFirebase= QuedadasFirebase.getInstance();
        quedadasFirebase.eliminarQuedada(uid, new EliminarQuedadaCallback() {
            @Override
            public void onQuedadaEliminada() {
                callback.onQuedadaEliminada();
            }

            @Override
            public void onQuedadaEliminadaError() {
                callback.onQuedadaEliminadaError();
            }
        });
    }

    @Override
    public void enviarSolicitud(PeticionQuedada peticionQuedada, final EnviarSolicitudCallback callback) {
        QuedadasFirebase quedadasFirebase= QuedadasFirebase.getInstance();
        quedadasFirebase.enviarSolicitud(peticionQuedada, new EnviarSolicitudCallback() {
            @Override
            public void onSolicitudEnviada() {
                callback.onSolicitudEnviada();
            }

            @Override
            public void onSolicitudEnviadaError() {

                callback.onSolicitudEnviadaError();
            }
        });
    }

    @Override
    public void editarQuedada(Quedada quedada, final EditarQuedadaCallback callback) {
        QuedadasFirebase quedadasFirebase= QuedadasFirebase.getInstance();
        quedadasFirebase.editarQuedada( quedada, new EditarQuedadaCallback() {
            @Override
            public void onQuedadaEditada() {
                callback.onQuedadaEditada();
            }

            @Override
            public void onQuedadaEditadaError() {
                callback.onQuedadaEditadaError();
            }
        });
    }

    @Override
    public void obtenerQuedadas(final ObtenerQuedadasCallback callback) {
        QuedadasFirebase quedadasFirebase= QuedadasFirebase.getInstance();
        quedadasFirebase.obtenerQuedadas(new ObtenerQuedadasCallback() {
            @Override
            public void onQuedadasObtenidas(List<Quedada> quedadas) {
               callback.onQuedadasObtenidas(quedadas);
            }

            @Override
            public void onQuedadasObtenidasError() {
              callback.onQuedadasObtenidasError();
            }
        });
    }

    @Override
    public void obtenerSolicitudesQuedadas(final ObtenerSolicitudesQuedadasCallback callback) {
        QuedadasFirebase quedadasFirebase= QuedadasFirebase.getInstance();
        quedadasFirebase.obtenerSolicitudesQuedadas(new ObtenerSolicitudesQuedadasCallback() {
            @Override
            public void onSolicitudesQuedadasObtenidas(List<PeticionQuedada> solicitudesQuedadas) {
                callback.onSolicitudesQuedadasObtenidas(solicitudesQuedadas);
            }

            @Override
            public void onSolicitudesQuedadasObtenidasError() {

                callback.onSolicitudesQuedadasObtenidasError();
            }
        });
    }


    @Override
    public void obtenerQuedadasUsuario( final ObtenerQuedadasCallback callback) {
        QuedadasFirebase quedadasFirebase= QuedadasFirebase.getInstance();
        quedadasFirebase.obtenerQuedadasUsuario(new ObtenerQuedadasCallback() {
            @Override
            public void onQuedadasObtenidas(List<Quedada> quedadas) {
                callback.onQuedadasObtenidas(quedadas);
            }

            @Override
            public void onQuedadasObtenidasError() {
                callback.onQuedadasObtenidasError();
            }
        });
    }
}
