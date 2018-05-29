package com.example.fermach.keepmoving.Quedadas.Editar_Quedada;

import com.example.fermach.keepmoving.Modelos.Quedada.Quedada;

/**
 * Created by Fermach on 27/03/2018.
 */

public interface EditarQuedadaContract {
    interface View {
        void onQuedadaEditada();
        void onQuedadaEditadaError();


    }
    interface Presenter {
        void editarQuedada(Quedada quedada);
    }

}
