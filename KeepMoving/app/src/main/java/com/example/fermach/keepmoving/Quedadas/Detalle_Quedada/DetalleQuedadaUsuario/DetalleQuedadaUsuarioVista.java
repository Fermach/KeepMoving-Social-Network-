package com.example.fermach.keepmoving.Quedadas.Detalle_Quedada.DetalleQuedadaUsuario;

import android.Manifest;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fermach.keepmoving.Quedadas.Editar_Quedada.EditarQuedadaVista;
import com.example.fermach.keepmoving.MainActivity.ChangeToolbar;
import com.example.fermach.keepmoving.Modelos.Quedada.Quedada;
import com.example.fermach.keepmoving.Usuarios.Perfil_Usuario.PerfilPantallaVista;
import com.example.fermach.keepmoving.R;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

/**
 * Created by Fermach on 27/03/2018.
 */

public class DetalleQuedadaUsuarioVista extends Fragment implements DetalleQuedadaUsuarioContract.View {

    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final int LOCATION_PERMISIONS_REQUEST_CODE = 1234;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final LatLngBounds LAT_LNG_BOUNDS= new LatLngBounds(new LatLng(-40,-168),
            new LatLng(71,136));
    private boolean permisosConcedidos;
    private Quedada quedada;
    private Button btn_modificar;
    private Button btn_atras;
    private ImageView img_gps;
    private TextView tv_fecha;
    private TextView tv_hora;
    private TextView tv_autor;
    private TextView tv_lugar;
    private TextView tv_mas_info;
    private GoogleApiClient googleApiClient;
    private TextView tv_deporte;
    private TextView tv_plazas;
    private Fragment fragment;
    private GoogleMap mMap;
    private View myView;
    private DetalleQuedadaUsuarioContract.Presenter presenter;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private LatLng latLng;
    private Address localizacion;
    private final String QUEDADA_ID ="QUEDADA_ID";
    private List<Address> lista;
    private Geocoder geocoder;
    private MarkerOptions markerOptions;

    private FusedLocationProviderClient mFusedLocationProviderClient;

    //private double lat = 0, lng = 0;


    public DetalleQuedadaUsuarioVista() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.quedada_detalle_usuario, container, false);

        ((ChangeToolbar)getActivity()).setToolbarText("Detalle usuario");

        presenter = new DetalleQuedadaUsuarioPresenter(this);

        //pasar la quedada
        Bundle args = getArguments();
        quedada =(Quedada) args
                .getSerializable("QUEDADA");

        Log.i("DETALLE_QUEDADA_US: ",quedada.toString());

        inicializarVista();
        activarControladores();

        iniciarMaps();

        return myView;
    }



    public void inicializarVista() {
        btn_modificar = myView.findViewById(R.id.btn_editar_detalle_quedada_us);
        btn_atras = myView.findViewById(R.id.btn_cancelar_detalle_quedada_us);
        tv_fecha = myView.findViewById(R.id.text_fecha_detalle_quedada_us);
        tv_hora = myView.findViewById(R.id.text_hora_detalle_quedada_us);
        tv_lugar = myView.findViewById(R.id.lugar_detalle_quedada_us);
        tv_mas_info = myView.findViewById(R.id.text_masinfo_detalle_quedada_us);
        tv_deporte = myView.findViewById(R.id.text_deporte_detalle_quedada_us);
        tv_plazas = myView.findViewById(R.id.text_plazas_detalle_quedada_us);
        tv_autor= myView.findViewById(R.id.text_autor_detalle_quedada_us);


        tv_deporte.setText(quedada.getDeporte());
        tv_fecha.setText(quedada.getFecha());
        tv_hora.setText(quedada.getHora());
        tv_autor.setText(quedada.getAutor());
        tv_lugar.setText(quedada.getLugar());
        tv_mas_info.setText(quedada.getInfo());
        tv_plazas.setText(quedada.getPlazas());

    }

    public void activarControladores() {

        btn_modificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mostrar pantalla de editar quedada pasandole los datos
                if(isOnlineNet()) {

                Bundle args = new Bundle();
                args.putSerializable(QUEDADA_ID, quedada);
                Fragment toFragment = new EditarQuedadaVista();
                toFragment.setArguments(args);
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content_main, toFragment, QUEDADA_ID)
                        .addToBackStack(QUEDADA_ID).commit();

                }else{
                    Snackbar.make(myView,"No hay conexión a internet", Snackbar.LENGTH_SHORT).show();
                }
            }
        });

        btn_atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isOnlineNet()) {

                    fragment = new PerfilPantallaVista();
                getFragmentManager().beginTransaction().replace(R.id.content_main, fragment).commit();
                }else{
                    Snackbar.make(myView,"No hay conexión a internet", Snackbar.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void buscarLugar(){

            Log.i("UBICACION A BUSCAR", quedada.getLugar());
            this.latLng= new LatLng(Double.parseDouble(quedada.getLatitud()),Double.parseDouble(quedada.getLongitud()));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,15f));
            this.markerOptions= new MarkerOptions().position(latLng).title(quedada.getLugar());

            mMap.addMarker(markerOptions);



    }


    public void iniciarMaps(){

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                        .findFragmentById(R.id.map_detalle_us);

         mapFragment.getMapAsync(new OnMapReadyCallback() {
                    @Override
                    public void onMapReady(GoogleMap googleMap) {
                        mMap = googleMap;
                        mMap.getUiSettings().setAllGesturesEnabled(true);

                            buscarLugar();

                    }
                });

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
