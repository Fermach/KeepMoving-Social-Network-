package com.example.fermach.keepmoving.Listado_Quedadas.Listado_Quedadas_Por_Usuario;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.fermach.keepmoving.Crear_Quedadas.CrearQuedadaContract;
import com.example.fermach.keepmoving.Crear_Quedadas.CrearQuedadaPresenter;
import com.example.fermach.keepmoving.Crear_Quedadas.CrearQuedadaVista;
import com.example.fermach.keepmoving.Crear_Quedadas.DatePickerFragment;
import com.example.fermach.keepmoving.Crear_Quedadas.TimePickerFragment;
import com.example.fermach.keepmoving.Detalle_Quedada.DetalleQuedadaUsuario.DetalleQuedadaUsuarioVista;
import com.example.fermach.keepmoving.Modelos.Quedada.Quedada;
import com.example.fermach.keepmoving.Perfil_Usuario.PerfilPantallaVista;
import com.example.fermach.keepmoving.R;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.michaelmuenzer.android.scrollablennumberpicker.ScrollableNumberPicker;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

                quedada= lista_quedadas.get(position);

                Bundle args = new Bundle();
                args.putSerializable(QUEDADA, quedada);
                Fragment toFragment = new DetalleQuedadaUsuarioVista();
                toFragment.setArguments(args);
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content_main, toFragment, QUEDADA)
                        .addToBackStack(QUEDADA).commit();
            }
        });

        //Cuando hagamos long click en la lista de quedadas nos lleva al fragmento con
        //el menu de borrar o editar pasandole la quedada como instancia
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
                quedada= lista_quedadas.get(position);
                FragmentManager fragmentManager=getFragmentManager();

                DialogFragment dialogFragment= ListadoQuedadasUsuarioMenuLClick.newInstance(quedada);
                dialogFragment.show(fragmentManager, "menu_quedadas");

                return true;
            }
        });

        fab_quedadas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment = new CrearQuedadaVista();
                getFragmentManager().beginTransaction().replace(R.id.content_main, fragment ).commit();

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
}
