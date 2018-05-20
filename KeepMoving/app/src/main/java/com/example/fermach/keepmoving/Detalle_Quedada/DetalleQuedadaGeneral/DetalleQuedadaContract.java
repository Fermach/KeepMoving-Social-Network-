package com.example.fermach.keepmoving.Detalle_Quedada.DetalleQuedadaGeneral;

import com.example.fermach.keepmoving.Modelos.Quedada.Quedada;

/**
 * Created by Fermach on 27/03/2018.
 */

public interface DetalleQuedadaContract {
    interface View {
       void onUsuarioActualObtenido(String uid);


    }
    interface Presenter {
       void obtenerUsuarioActual();
    }

}
