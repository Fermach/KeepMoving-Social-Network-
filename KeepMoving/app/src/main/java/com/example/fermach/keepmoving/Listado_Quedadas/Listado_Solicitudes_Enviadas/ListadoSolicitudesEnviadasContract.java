package com.example.fermach.keepmoving.Listado_Quedadas.Listado_Solicitudes_Enviadas;

import com.example.fermach.keepmoving.Modelos.Quedada.PeticionQuedada;
import com.example.fermach.keepmoving.Modelos.Quedada.Quedada;

import java.util.List;

/**
 * Created by Fermach on 27/03/2018.
 */

public interface ListadoSolicitudesEnviadasContract {
    interface View {
      void onSolicitudesQuedadasObtenidas(List<PeticionQuedada> solicitudesQuedadas);
      void onSolicitudesQuedadasObtenidasError();
      void mostrarSolicitudesQuedadasNumero(List<PeticionQuedada> solicitudesQuedadas);

    }
    interface Presenter {
      void obtenerSolicitudes();

    }

}
