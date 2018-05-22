package com.example.fermach.keepmoving.Listado_Quedadas.Listado_Peticiones_Recibidas;

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

import com.example.fermach.keepmoving.Listado_Quedadas.Listado_Solicitudes_Enviadas.ListadoSolicitudesEnviadasAdapter;
import com.example.fermach.keepmoving.Listado_Quedadas.Listado_Solicitudes_Enviadas.ListadoSolicitudesEnviadasContract;
import com.example.fermach.keepmoving.Listado_Quedadas.Listado_Solicitudes_Enviadas.ListadoSolicitudesEnviadasPresenter;
import com.example.fermach.keepmoving.Modelos.Quedada.PeticionQuedada;
import com.example.fermach.keepmoving.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fermach on 27/03/2018.
 */

public class ListadoPeticionesRecibidasVista extends Fragment implements ListadoPeticionesRecibidasContract.View {

    private PeticionQuedada peticionQuedada;
    private Fragment fragment;
    private List<PeticionQuedada> lista_peticiones;
    private View myView;
    private ListView listView;
    private TextView num_peticiones_recibidas;
    private ListadoPeticionesRecibidasAdapter listadoPeticionesRecibidasAdapter;
    private ListadoPeticionesRecibidasContract.Presenter presenter;
    private final String QUEDADA ="QUEDADA";
    private ProgressDialog progressDialog;

    public ListadoPeticionesRecibidasVista() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.listado_peticiones_quedadas_recibidas, container, false);

        lista_peticiones= new ArrayList<>();

        progressDialog= new ProgressDialog(myView.getContext());
        progressDialog.setMessage("Obteniendo datos");
        progressDialog.setCancelable(false);
      // progressDialog.show();

        presenter = new ListadoPeticionesRecibidasPresenter(this);
        presenter.obtenerPeticionesRecibidas();

        inicializarVista();
        activarControladores();

        return myView;
    }



    public void inicializarVista() {
        listView=myView.findViewById(R.id.list_peticiones_recibidas);
        num_peticiones_recibidas=myView.findViewById(R.id.num_peticiones_recibidas_lista);
    }

    public void activarControladores(){
        //Cuando clickemos en la lista de quedadas nos lleva al fragmento con el
        //detalle de la quedada pasándole el id de la quedada

    }

    @Override
    public void onPeticionesRecibidasObtenidas(List<PeticionQuedada> peticionesQuedadas) {
        this.lista_peticiones= peticionesQuedadas;

        listadoPeticionesRecibidasAdapter= new ListadoPeticionesRecibidasAdapter(myView.getContext(), peticionesQuedadas);
        listView.setAdapter(listadoPeticionesRecibidasAdapter);
    }

    @Override
    public void onPeticionesRecibidasObtenidasError() {

        Snackbar.make(myView,"No ha sido posible obtener la lista de peticiones pendientes", Snackbar.LENGTH_SHORT).show();
    }



    @Override
    public void mostrarPeticionesRecibidasNumero(List<PeticionQuedada> peticionesQuedadas) {
        num_peticiones_recibidas.setText("Numero de Peticiones Pendientes: "+ peticionesQuedadas.size());
    }
}
