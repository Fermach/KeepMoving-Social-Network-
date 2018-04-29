package com.example.fermach.keepmoving.Crear_Quedadas;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.fermach.keepmoving.Modelos.Usuario.Usuario;
import com.example.fermach.keepmoving.Perfil_Usuario.PerfilPantallaContract;
import com.example.fermach.keepmoving.Perfil_Usuario.PerfilPantallaPresenter;
import com.example.fermach.keepmoving.Perfil_Usuario.PerfilPantallaVista;
import com.example.fermach.keepmoving.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.michaelmuenzer.android.scrollablennumberpicker.ScrollableNumberPicker;
import com.michaelmuenzer.android.scrollablennumberpicker.ScrollableNumberPickerListener;

import java.text.SimpleDateFormat;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Fermach on 27/03/2018.
 */

public class CrearQuedadaVista extends Fragment implements CrearQuedadaContract.View, OnMapReadyCallback {

    private static final int FINE_LOCATION_PERMISSION_REQUEST = 1;
    private static final int CONNECTION_RESOLUTION_REQUEST = 2;
    private Button btn_publicar;
    private Button btn_cancelar;
    private TextView tv_fecha;
    private TextView tv_hora;
    private TextView tv_mas_info;
    private Spinner spinner_deporte;
    private ScrollableNumberPicker picker_plazas;
    private Fragment fragment;
    private ProgressDialog progressDialog;
    private GoogleMap mMap;
    private View myView;
    private CrearQuedadaContract.Presenter presenter;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private Date cDate;
    private String fecha;
    private String hora;
    private double lat = 0, lng = 0;


    public CrearQuedadaVista() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.pantalla_crear_quedada, container, false);

        cDate= new Date();
        presenter = new CrearQuedadaPresenter(this);
        progressDialog = new ProgressDialog(myView.getContext());

        inicializarVista();
        activarControladores();

        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        return myView;
    }


    public void inicializarVista() {
        btn_cancelar = myView.findViewById(R.id.btn_cancelar_crear_quedada);
        btn_publicar = myView.findViewById(R.id.btn_aceptar_crear_quedada);
        tv_fecha = myView.findViewById(R.id.editText_fecha_crear_quedada);
        tv_hora = myView.findViewById(R.id.editText_hora_crear_quedada);
        tv_mas_info = myView.findViewById(R.id.descripcion_crear_quedada);
        spinner_deporte = myView.findViewById(R.id.spinner_deporte_crear_quedada);
        picker_plazas = myView.findViewById(R.id.plazas_crear_quedada);


        String[] valores_deportes={"Futbol","Tenis","Baloncesto",
                "Running","Rugby","Boxeo","Artes Marciales","Senderismo","Otros"};
        spinner_deporte.setAdapter(new ArrayAdapter<String>
                (getContext(),R.layout.support_simple_spinner_dropdown_item,valores_deportes));


        fecha = new SimpleDateFormat("yyyy-mm-dd").format(cDate);
        hora = new SimpleDateFormat("HH:mm:ss").format(cDate);

        tv_fecha.setText(""+fecha);
        tv_hora.setText(""+hora);
    }

    public void activarControladores() {

        btn_publicar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btn_cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new PerfilPantallaVista();
                getFragmentManager().beginTransaction().replace(R.id.content_main, fragment).commit();

            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();


    }

    @Override
    public void onQuedadaCreada() {

    }

    @Override
    public void onQuedadaCreadaError() {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

    }





}
