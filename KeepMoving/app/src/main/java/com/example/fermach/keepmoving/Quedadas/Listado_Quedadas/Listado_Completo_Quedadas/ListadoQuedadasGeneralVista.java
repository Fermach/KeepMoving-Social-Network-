package com.example.fermach.keepmoving.Quedadas.Listado_Quedadas.Listado_Completo_Quedadas;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.fermach.keepmoving.Modelos.Quedada.PeticionQuedada;
import com.example.fermach.keepmoving.Quedadas.Detalle_Quedada.DetalleQuedadaGeneral.DetalleQuedadaVista;
import com.example.fermach.keepmoving.MainActivity.ChangeToolbar;
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
        ((ChangeToolbar)getActivity()).hideToolbar(false);
        ((ChangeToolbar)getActivity()).setToolbarText("Listado de quedadas");

        progressDialog= new ProgressDialog(myView.getContext());
        progressDialog.setMessage("Obteniendo datos");
        progressDialog.setCancelable(false);
      // progressDialog.show();

        presenter = new ListadoQuedadasGeneralPresenter(this);
        presenter.obtenerQuedadas();
        presenter.obtenerPeticionesRecibidas();

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
    public void onPeticionesRecibidasObtenidas(List<PeticionQuedada> peticionesQuedadas) {
       boolean nuevasPeticiones=false;
        for (PeticionQuedada peticionQuedada: peticionesQuedadas) {
            if(peticionQuedada.getEstado().equals("ENVIADA")){
                nuevasPeticiones=true;
            }
        }

        Log.i("NUEVAS PETICIONES:", "----------------------"+nuevasPeticiones);
        if(nuevasPeticiones){
            AlertDialog.Builder myBuild = new AlertDialog.Builder(getContext());
            myBuild.setMessage("Tiene nuevas peticiones pendientes de confirmar.\n\nRevise su lista de peticiones.");
            myBuild.setTitle("Alerta");

            myBuild.setNegativeButton("Cerrar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            myBuild.show();
        }
    }

    @Override
    public void onPeticionesRecibidasObtenidasError() {

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
