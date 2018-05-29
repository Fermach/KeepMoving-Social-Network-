package com.example.fermach.keepmoving.Quedadas.Listado_Quedadas.Listado_Quedadas_Por_Usuario;

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

import com.example.fermach.keepmoving.Quedadas.Crear_Quedadas.CrearQuedadaVista;
import com.example.fermach.keepmoving.Quedadas.Detalle_Quedada.DetalleQuedadaUsuario.DetalleQuedadaUsuarioVista;
import com.example.fermach.keepmoving.MainActivity.ChangeToolbar;
import com.example.fermach.keepmoving.Modelos.Quedada.Quedada;
import com.example.fermach.keepmoving.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fermach on 27/03/2018.
 */

public class ListadoQuedadasUsuarioVista extends Fragment implements ListadoQuedadasUsuarioContract.View {

    private Quedada quedada;
    private Fragment fragment;
    private List<Quedada> lista_quedadas;
    private View myView;
    private ListView listView;
    private TextView num_quedadas;
    private FloatingActionButton fab_quedadas;
    private ListadoQuedadasUsuarioAdapter listadoQuedadasUsuarioAdapter;
    private ListadoQuedadasUsuarioContract.Presenter presenter;
    private boolean quedadaActualizada;
    private boolean eliminar_quedada;
    private String id_quedada_eliminar;
    private final String QUEDADA ="QUEDADA";
    private ProgressDialog progressDialog;

    public ListadoQuedadasUsuarioVista() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.listado_quedadas_usuario, container, false);

        lista_quedadas= new ArrayList<>();
        Bundle args = getArguments();
        ((ChangeToolbar)getActivity()).setToolbarText("Mis quedadas publicadas");

        presenter = new ListadoQuedadasUsuarioPresenter(this);


        progressDialog= new ProgressDialog(myView.getContext());

        quedadaActualizada=false;
        eliminar_quedada=false;

        //se comprueba si se estan recibiendo argumentos de otro fragmento
        //para saber si el menu de borrado nos esta pasando una llave para que borremos una
        //quedada de la lista
        if(args!=null) {

            if(((Boolean) args.getSerializable("ELIMINAR_QUEDADA"))!=null) {
                id_quedada_eliminar = (String) args.getSerializable("QUEDADA_A_ELIMINAR_ID");
                Log.i("Argumentos", "RECOGIDOS =" + eliminar_quedada);

                presenter.borrarQuedada(id_quedada_eliminar);
            }
        }else{
            Log.i("Argumentos", "NULOS" );

        }
        presenter.obtenerQuedadas();

        inicializarVista();
        activarControladores();

        return myView;
    }



    public void inicializarVista() {
        listView=myView.findViewById(R.id.list_quedadas);
        num_quedadas=myView.findViewById(R.id.num_quedadas_lista);
        fab_quedadas=myView.findViewById(R.id.fab_quedadas_us_lista);

    }

    public void activarControladores(){

        //Cuando clickemos en la lista de quedadas nos lleva al fragmento con el
        //detalle de la quedada pasándole el id de la quedada
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                if(isOnlineNet()) {
                quedada= lista_quedadas.get(position);

                Bundle args = new Bundle();
                args.putSerializable(QUEDADA, quedada);
                Fragment toFragment = new DetalleQuedadaUsuarioVista();
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

        //Cuando hagamos long click en la lista de quedadas nos lleva al fragmento con
        //el menu de borrar o editar pasandole la quedada como instancia
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
                if (isOnlineNet()) {

                    quedada = lista_quedadas.get(position);
                    FragmentManager fragmentManager = getFragmentManager();

                    DialogFragment dialogFragment = ListadoQuedadasUsuarioMenuLClick.newInstance(quedada);
                    dialogFragment.show(fragmentManager, "menu_quedadas");

                    return true;
                } else {
                    Snackbar.make(myView, "No hay conexión con internet", Snackbar.LENGTH_SHORT).show();
                    return false;

                }
            }
        });

        fab_quedadas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(isOnlineNet()) {

                    fragment = new CrearQuedadaVista();
                    getFragmentManager().beginTransaction().replace(R.id.content_main, fragment ).commit();
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

        this.lista_quedadas= quedadas;
        listadoQuedadasUsuarioAdapter= new ListadoQuedadasUsuarioAdapter(myView.getContext(), quedadas);
        listView.setAdapter(listadoQuedadasUsuarioAdapter);
    }

    @Override
    public void onQuedadasObtenidasError() {
        Snackbar.make(myView,"No ha sido posible obtener la lista de quedadas", Snackbar.LENGTH_SHORT).show();

    }

    @Override
    public void onQuedadaEliminada() {
        presenter.obtenerQuedadas();
        Snackbar.make(myView,"La quedada se eliminó correctamente!", Snackbar.LENGTH_SHORT).show();

    }

    @Override
    public void onQuedadaEliminadaError() {
        Snackbar.make(myView,"No ha sido posible eliminar la quedada", Snackbar.LENGTH_SHORT).show();

    }

    @Override
    public void mostrarQuedadasNumero(List<Quedada> quedadas) {
        num_quedadas.setText("Numero de quedadas: " + quedadas.size());
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
