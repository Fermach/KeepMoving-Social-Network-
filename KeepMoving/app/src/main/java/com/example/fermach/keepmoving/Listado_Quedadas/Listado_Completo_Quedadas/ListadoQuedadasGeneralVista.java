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
import com.example.fermach.keepmoving.Detalle_Quedada.DetalleQuedadaGeneral.DetalleQuedadaVista;
import com.example.fermach.keepmoving.Detalle_Quedada.DetalleQuedadaUsuario.DetalleQuedadaUsuarioVista;
import com.example.fermach.keepmoving.Listado_Quedadas.Listado_Quedadas_Por_Usuario.ListadoQuedadasUsuarioAdapter;
import com.example.fermach.keepmoving.Listado_Quedadas.Listado_Quedadas_Por_Usuario.ListadoQuedadasUsuarioContract;
import com.example.fermach.keepmoving.Listado_Quedadas.Listado_Quedadas_Por_Usuario.ListadoQuedadasUsuarioMenuLClick;
import com.example.fermach.keepmoving.Listado_Quedadas.Listado_Quedadas_Por_Usuario.ListadoQuedadasUsuarioPresenter;
import com.example.fermach.keepmoving.MainActivity.DrawerLocker;
import com.example.fermach.keepmoving.Modelos.Quedada.Quedada;
import com.example.fermach.keepmoving.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fermach on 27/03/2018.
 */

public class ListadoQuedadasGeneralVista extends Fragment implements ListadoQuedadasGeneralContract.View {

    private Quedada quedada;
    private Fragment fragment;
    private List<Quedada> lista_quedadas;
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

        lista_quedadas= new ArrayList<>();


        ((DrawerLocker)getActivity()).setDrawerLocked(false);
        progressDialog= new ProgressDialog(myView.getContext());
        progressDialog.setMessage("Obteniendo datos");
        progressDialog.setCancelable(false);
      // progressDialog.show();

        presenter = new ListadoQuedadasGeneralPresenter(this);
        presenter.obtenerQuedadas();

        inicializarVista();
        activarControladores();

        return myView;
    }



    public void inicializarVista() {
        listView=myView.findViewById(R.id.list_quedadas_general);
        num_quedadas=myView.findViewById(R.id.num_quedadas_lista_general);
    }

    public void activarControladores(){
        //Cuando clickemos en la lista de quedadas nos lleva al fragmento con el
        //detalle de la quedada pasándole el id de la quedada
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                quedada= lista_quedadas.get(position);

                if(isOnlineNet()) {
                    Bundle args = new Bundle();
                    args.putSerializable(QUEDADA, quedada);
                    Fragment toFragment = new DetalleQuedadaVista();
                    toFragment.setArguments(args);
                    getFragmentManager()
                            .beginTransaction()
                            .replace(R.id.content_main, toFragment, QUEDADA)
                            .addToBackStack(QUEDADA).commit();
                }else{
                    Snackbar.make(myView,"No hay conexión con internet", Snackbar.LENGTH_SHORT).show();

                }
            }
        });


    }


    @Override
    public void onStart() {
        super.onStart();

    }


    @Override
    public void onQuedadasObtenidas(List<Quedada> quedadas) {
       // progressDialog.dismiss();
        this.lista_quedadas= quedadas;

        listadoQuedadasGeneralAdapter= new ListadoQuedadasGeneralAdapter(myView.getContext(), quedadas);
        listView.setAdapter(listadoQuedadasGeneralAdapter);

    }

    @Override
    public void onQuedadasObtenidasError() {
       // progressDialog.dismiss();

        Snackbar.make(myView,"No ha sido posible obtener la lista de quedadas", Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void mostrarQuedadasNumero(List<Quedada> quedadas) {
       // progressDialog.dismiss();
        num_quedadas.setText("Numero de quedadas: "+ quedadas.size());
    }

    public Boolean isOnlineNet() {

        try {
            Process p = java.lang.Runtime.getRuntime().exec("ping -c 1 www.google.es");

            int val           = p.waitFor();
            boolean reachable = (val == 0);
            return reachable;

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }
}
