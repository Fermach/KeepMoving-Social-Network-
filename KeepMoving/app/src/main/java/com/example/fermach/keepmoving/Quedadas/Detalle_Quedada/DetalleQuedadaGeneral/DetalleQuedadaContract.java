package com.example.fermach.keepmoving.Quedadas.Detalle_Quedada.DetalleQuedadaGeneral;

import com.example.fermach.keepmoving.Modelos.Quedada.Quedada;

/**
 * Created by Fermach on 27/03/2018.
 */

public interface DetalleQuedadaContract {
    interface View {
       void onUsuarioActualObtenido(String uid);
       void onPeticionLibre();
       void onPeticionOcupada();
       void onUsuarioFotoObtenida(byte[] foto);
       void onUsuarioFotoObtenidaError();

    }
    interface Presenter {
       void obtenerUsuarioActual();
       void obtenerFotoUsuario(String uid);
       void verificarPeticionQuedada(Quedada quedada);
    }

}
