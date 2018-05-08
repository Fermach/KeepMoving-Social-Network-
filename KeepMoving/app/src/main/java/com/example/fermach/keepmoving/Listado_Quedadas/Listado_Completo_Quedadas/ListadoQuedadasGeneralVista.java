package com.example.fermach.keepmoving.Listado_Quedadas.Listado_Completo_Quedadas;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.fermach.keepmoving.Crear_Quedadas.CrearQuedadaVista;
import com.example.fermach.keepmoving.Listado_Quedadas.Listado_Quedadas_Por_Usuario.ListadoQuedadasUsuarioAdapter;
import com.example.fermach.keepmoving.Listado_Quedadas.Listado_Quedadas_Por_Usuario.ListadoQuedadasUsuarioContract;
import com.example.fermach.keepmoving.Listado_Quedadas.Listado_Quedadas_Por_Usuario.ListadoQuedadasUsuarioMenuLClick;
import com.example.fermach.keepmoving.Listado_Quedadas.Listado_Quedadas_Por_Usuario.ListadoQuedadasUsuarioPresenter;
import com.example.fermach.keepmoving.Modelos.Quedada.Quedada;
import com.example.fermach.keepmoving.R;

import java.util.List;

/**
 * Created by Fermach on 27/03/2018.
 */

public class ListadoQuedadasGeneralVista extends Fragment implements ListadoQuedadasGeneralContract.View {

    private Quedada quedada;
    private Fragment fragment;
    private View myView;
    private ListView listView;
    private TextView num_quedadas;
    private ListadoQuedadasGeneralAdapter listadoQuedadasGeneralAdapter;
    private ListadoQuedadasGeneralContract.Presenter presenter;
    private final String QUEDADA ="QUEDADA";
    private ProgressDialog progressDialog;

    public ListadoQuedadasGeneralVista() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.listado_quedadas_general, container, false);


        inicializarVista();
        activarControladores();

        progressDialog= new ProgressDialog(myView.getContext());
        progressDialog.setMessage("Obteniendo datos");
        progressDialog.setCancelable(false);
        progressDialog.show();

        presenter = new ListadoQuedadasGeneralPresenter(this);
        presenter.obtenerQuedadas();

        return myView;
    }



    public void inicializarVista() {
        listView=myView.findViewById(R.id.list_quedadas_general);
        num_quedadas=myView.findViewById(R.id.num_quedadas_lista_general);
    }

    public void activarControladores() {

    }

    @Override
    public void onStart() {
        super.onStart();

    }


    @Override
    public void onQuedadasObtenidas(List<Quedada> quedadas) {

        listadoQuedadasGeneralAdapter= new ListadoQuedadasGeneralAdapter(myView.getContext(), quedadas);
        listView.setAdapter(listadoQuedadasGeneralAdapter);
        progressDialog.dismiss();
    }

    @Override
    public void onQuedadasObtenidasError() {
        Snackbar.make(myView,"No ha sido posible obtener la lista de quedadas", Snackbar.LENGTH_SHORT).show();
        progressDialog.dismiss();
    }

    @Override
    public void mostrarQuedadasNumero(List<Quedada> quedadas) {
        num_quedadas.setText("Numero de quedadas: "+ quedadas.size());
    }

    @Override
    public void activarListaClickable(final List<Quedada> quedadas) {

        //Cuando clickemos en la lista de quedadas nos lleva al fragmento con el
        //detalle de la quedada pasándole el id de la quedada
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                quedada= quedadas.get(position);
                /*
                Bundle args = new Bundle();
                args.putSerializable(QUEDADA, quedada.getId());
                Fragment toFragment = new DetalleQuedadaUsuarioVista();
                toFragment.setArguments(args);
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content_main, toFragment, QUEDADA)
                        .addToBackStack(QUEDADA).commit();
                        */
            }
        });


    }
}
