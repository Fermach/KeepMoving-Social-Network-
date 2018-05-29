package com.example.fermach.keepmoving.Quedadas.Listado_Quedadas.Listado_Solicitudes_Enviadas;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.fermach.keepmoving.MainActivity.ChangeToolbar;
import com.example.fermach.keepmoving.Modelos.Quedada.PeticionQuedada;
import com.example.fermach.keepmoving.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fermach on 27/03/2018.
 */

public class ListadoSolicitudesEnviadasVista extends Fragment implements ListadoSolicitudesEnviadasContract.View {

    private PeticionQuedada peticionQuedada;
    private Fragment fragment;
    private List<PeticionQuedada> lista_peticiones;
    private View myView;
    private ListView listView;
    private TextView num_peticiones;
    private ListadoSolicitudesEnviadasAdapter listadoSolicitudesEnviadasAdapter;
    private ListadoSolicitudesEnviadasContract.Presenter presenter;
    private final String QUEDADA ="QUEDADA";
    private ProgressDialog progressDialog;

    public ListadoSolicitudesEnviadasVista() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.listado_peticiones_quedadas_enviadas, container, false);

        lista_peticiones= new ArrayList<>();
        ((ChangeToolbar)getActivity()).setToolbarText("Peticiones enviadas");

        presenter = new ListadoSolicitudesEnviadasPresenter(this);
        presenter.obtenerSolicitudes();

        inicializarVista();
        activarControladores();

        return myView;
    }



    public void inicializarVista() {
        listView=myView.findViewById(R.id.list_peticiones_enviadas);
        num_peticiones=myView.findViewById(R.id.num_peticiones_enviadas_lista);
    }

    public void activarControladores(){
        //Cuando clickemos en la lista de quedadas nos lleva al fragmento con el
        //detalle de la quedada pasándole el id de la quedada

    }

    @Override
    public void onSolicitudesQuedadasObtenidas(List<PeticionQuedada> peticionesQuedadas) {
        this.lista_peticiones= peticionesQuedadas;

        listadoSolicitudesEnviadasAdapter= new ListadoSolicitudesEnviadasAdapter(myView.getContext(), peticionesQuedadas);
        listView.setAdapter(listadoSolicitudesEnviadasAdapter);
    }

    @Override
    public void onSolicitudesQuedadasObtenidasError() {

        Snackbar.make(myView,"No ha sido posible obtener la lista de solicitudes", Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void mostrarSolicitudesQuedadasNumero(List<PeticionQuedada> peticionesQuedadas) {
        num_peticiones.setText("Numero de solicitudes enviadas: "+ peticionesQuedadas.size());
    }
}
