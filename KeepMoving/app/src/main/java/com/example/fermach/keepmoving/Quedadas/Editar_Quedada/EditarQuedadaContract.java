package com.example.fermach.keepmoving.Quedadas.Editar_Quedada;

import com.example.fermach.keepmoving.Modelos.Quedada.Quedada;

/**
 *
 * Interfaz que comunica la interfaz de editar quedada con el repositorio
 *
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
