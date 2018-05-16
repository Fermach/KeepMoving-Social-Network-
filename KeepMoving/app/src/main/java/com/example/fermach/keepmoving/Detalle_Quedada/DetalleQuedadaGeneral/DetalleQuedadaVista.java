package com.example.fermach.keepmoving.Detalle_Quedada.DetalleQuedadaGeneral;

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
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
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
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.fermach.keepmoving.Crear_Quedadas.CrearQuedadaContract;
import com.example.fermach.keepmoving.Crear_Quedadas.CrearQuedadaPresenter;
import com.example.fermach.keepmoving.Crear_Quedadas.DatePickerFragment;
import com.example.fermach.keepmoving.Crear_Quedadas.TimePickerFragment;
import com.example.fermach.keepmoving.Listado_Quedadas.Listado_Completo_Quedadas.ListadoQuedadasGeneralVista;
import com.example.fermach.keepmoving.Listado_Quedadas.Listado_Quedadas_Por_Usuario.ListadoQuedadasUsuarioVista;
import com.example.fermach.keepmoving.Modelos.Quedada.Quedada;
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

public class DetalleQuedadaVista extends Fragment implements DetalleQuedadaContract.View {

    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final int LOCATION_PERMISIONS_REQUEST_CODE = 1234;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final LatLngBounds LAT_LNG_BOUNDS= new LatLngBounds(new LatLng(-40,-168),
            new LatLng(71,136));
    private boolean permisosConcedidos;
    private Quedada quedada;
    private Button btn_apuntarse;
    private Button btn_atras;
    private ImageView img_gps;
    private TextView tv_fecha;
    private TextView tv_hora;
    private TextView tv_lugar;
    private TextView tv_mas_info;
    private TextView tv_autor;
    private GoogleApiClient googleApiClient;
    private TextView tv_deporte;
    private TextView tv_plazas;
    private Fragment fragment;
    private GoogleMap mMap;
    private View myView;
    private DetalleQuedadaContract.Presenter presenter;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private LatLng latLng;
    private Address localizacion;
    private List<Address> lista;
    private ProgressDialog progressDialog;
    private Geocoder geocoder;
    private MarkerOptions markerOptions;
    private FusedLocationProviderClient mFusedLocationProviderClient;

    //private double lat = 0, lng = 0;


    public DetalleQuedadaVista() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.quedada_detalle_general, container, false);

        progressDialog= new ProgressDialog(myView.getContext());
        presenter = new DetalleQuedadaPresenter(this);

        Bundle args = getArguments();
        quedada =(Quedada) args
                .getSerializable("QUEDADA");

        Log.i("QUEDADA_GEN_DETALLE",    quedada.toString());

        inicializarVista();
        activarControladores();

        progressDialog.setMessage("Obteniendo Ubicación");
        progressDialog.setCancelable(false);
        progressDialog.show();

        iniciarMaps();

        return myView;
    }

    public void inicializarVista() {
        btn_apuntarse = myView.findViewById(R.id.btn_apuntarse_detalle_quedada_general);
        btn_atras = myView.findViewById(R.id.btn_cancelar_detalle_quedada_general);
        tv_fecha = myView.findViewById(R.id.text_fecha_detalle_quedada_general);
        tv_hora = myView.findViewById(R.id.text_hora_detalle_quedada_general);
        tv_lugar = myView.findViewById(R.id.lugar_detalle_quedada_general);
        tv_mas_info = myView.findViewById(R.id.text_masinfo_detalle_quedada_general);
        tv_deporte = myView.findViewById(R.id.text_deporte_detalle_quedada_general);
        tv_plazas = myView.findViewById(R.id.text_plazas_detalle_quedada_general);
        tv_autor= myView.findViewById(R.id.text_autor_detalle_quedada_general);

        tv_deporte.setText(quedada.getDeporte());
        tv_fecha.setText(quedada.getFecha());
        tv_autor.setText(quedada.getAutor());
        tv_hora.setText(quedada.getHora());
        tv_lugar.setText(quedada.getLugar());
        tv_mas_info.setText(quedada.getInfo());
        tv_plazas.setText(quedada.getPlazas());

    }

    public void activarControladores() {

        btn_apuntarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               //
            }
        });

        btn_atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new ListadoQuedadasGeneralVista();
                getFragmentManager().beginTransaction().replace(R.id.content_main, fragment).commit();

            }
        });
    }


    public void buscarLugar(){

            Log.i("UBICACION A BUSCAR", quedada.getLugar());
            this.latLng= new LatLng(Double.parseDouble(quedada.getLatitud()),Double.parseDouble(quedada.getLongitud()));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,15f));
            this.markerOptions= new MarkerOptions()
                    .position(latLng)
                    .title(quedada.getLugar());

            mMap.addMarker(markerOptions);

            progressDialog.dismiss();

    }


    public void iniciarMaps(){

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                        .findFragmentById(R.id.map_detalle_general);

         mapFragment.getMapAsync(new OnMapReadyCallback() {
                    @Override
                    public void onMapReady(GoogleMap googleMap) {
                        mMap = googleMap;
                        mMap.getUiSettings().setAllGesturesEnabled(true);


                            buscarLugar();



                    }
         });

    }



}
