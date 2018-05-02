package com.example.fermach.keepmoving.Crear_Quedadas;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.fermach.keepmoving.Modelos.Usuario.Usuario;
import com.example.fermach.keepmoving.Perfil_Usuario.PerfilPantallaContract;
import com.example.fermach.keepmoving.Perfil_Usuario.PerfilPantallaPresenter;
import com.example.fermach.keepmoving.Perfil_Usuario.PerfilPantallaVista;
import com.example.fermach.keepmoving.R;


import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.michaelmuenzer.android.scrollablennumberpicker.ScrollableNumberPicker;
import com.michaelmuenzer.android.scrollablennumberpicker.ScrollableNumberPickerListener;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Fermach on 27/03/2018.
 */

public class CrearQuedadaVista extends Fragment implements CrearQuedadaContract.View{

    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final int LOCATION_PERMISIONS_REQUEST_CODE = 1234;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private boolean permisosConcedidos;
    private Button btn_publicar;
    private Button btn_cancelar;
    private ImageView img_gps;
    private TextView tv_fecha;
    private TextView tv_hora;
    private TextView tv_lugar;
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
    private FusedLocationProviderClient mFusedLocationProviderClient;

    //private double lat = 0, lng = 0;


    public CrearQuedadaVista() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.pantalla_crear_quedada, container, false);

        cDate = new Date();
        presenter = new CrearQuedadaPresenter(this);
        progressDialog = new ProgressDialog(myView.getContext());
        permisosConcedidos=false;

        inicializarVista();
        activarControladores();

        String[] permisos = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};


        if (ContextCompat.checkSelfPermission(getContext(), FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(getContext(), COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                permisosConcedidos = true;

                iniciarMaps();
            } else {
                ActivityCompat.requestPermissions(getActivity(), permisos, LOCATION_PERMISIONS_REQUEST_CODE);
            }
        } else {
            ActivityCompat.requestPermissions(getActivity(), permisos, LOCATION_PERMISIONS_REQUEST_CODE);
        }



        return myView;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permisosConcedidos = false;

        switch (requestCode) {
            case LOCATION_PERMISIONS_REQUEST_CODE: {
                if (grantResults.length > 0) {
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            permisosConcedidos = false;
                            Log.i("MAPS---", "PERMISOS FALSE");
                            Toast.makeText(getActivity(), "No se pudo acceder a la ubicación actual",Toast.LENGTH_SHORT).show();
                            break;

                        }
                    }
                    Log.i("MAPS---", "PERMISOS TRUE");
                    permisosConcedidos = true;

                    iniciarMaps();
                }
            }
        }
    }

    public void inicializarVista() {
        btn_cancelar = myView.findViewById(R.id.btn_cancelar_crear_quedada);
        btn_publicar = myView.findViewById(R.id.btn_aceptar_crear_quedada);
        tv_fecha = myView.findViewById(R.id.editText_fecha_crear_quedada);
        tv_hora = myView.findViewById(R.id.editText_hora_crear_quedada);
        tv_lugar = myView.findViewById(R.id.buscar_crear_quedada);
        tv_mas_info = myView.findViewById(R.id.descripcion_crear_quedada);
        spinner_deporte = myView.findViewById(R.id.spinner_deporte_crear_quedada);
        picker_plazas = myView.findViewById(R.id.plazas_crear_quedada);
        img_gps=myView.findViewById(R.id.img_ubicacion);

        img_gps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                obtenerUbicacion();
            }
        });

        String[] valores_deportes = {"Futbol", "Tenis", "Baloncesto",
                "Running", "Rugby", "Boxeo", "Artes Marciales", "Senderismo", "Otros"};
        spinner_deporte.setAdapter(new ArrayAdapter<String>
                (getContext(), R.layout.support_simple_spinner_dropdown_item, valores_deportes));


        fecha = new SimpleDateFormat("dd-M-YYYY").format(cDate);
        hora = new SimpleDateFormat("HH:mm").format(cDate);

        tv_fecha.setText("" + fecha);
        tv_hora.setText("" + hora);
    }

    public void activarControladores() {

        tv_lugar.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_SEARCH
                        || actionId== EditorInfo.IME_ACTION_DONE
                        || event.getAction()==KeyEvent.ACTION_DOWN
                        || event.getAction()==KeyEvent.KEYCODE_ENTER ){

                    String lugar= tv_lugar.getText().toString().trim();

                    Geocoder geocoder= new Geocoder(getActivity());
                    List<Address> lista = new ArrayList<>();

                    try {
                        lista =geocoder.getFromLocationName(lugar,1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    if(lista.size()>0){
                        Address address= lista.get(0);

                        LatLng latLng= new LatLng(address.getLatitude(),address.getLongitude());
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,15f));
                        MarkerOptions markerOptions= new MarkerOptions().position(latLng).title(address.getAddressLine(0));

                        mMap.addMarker(markerOptions);

                    }
                }

                return false;
            }
        });

        tv_fecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        final String fechaSeleccionada = dayOfMonth + "/" + (month + 1) + "/" + year;
                        tv_fecha.setText(fechaSeleccionada);
                    }

                });
                newFragment.show(getActivity().getFragmentManager(), "Seleccione Fecha");
            }
        });


        tv_hora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerFragment timerFragment = TimePickerFragment.newInstance(new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        final String horaSeleccionada = hourOfDay + ":" + minute;
                        tv_hora.setText(horaSeleccionada);
                    }
                });
                timerFragment.show(getActivity().getFragmentManager(), "Seleccione Hora");
            }
        });


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


    public void iniciarMaps(){

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                        .findFragmentById(R.id.map);

         mapFragment.getMapAsync(new OnMapReadyCallback() {
                    @Override
                    public void onMapReady(GoogleMap googleMap) {
                        mMap = googleMap;


                        if (permisosConcedidos) {
                            Log.i("MAPS", "PERMISOS UBICACION CONCEDIDOS");
                            obtenerUbicacion();
                            if (ActivityCompat.checkSelfPermission(getActivity(),
                                    Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                                    && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                                return;
                            }
                            mMap.setMyLocationEnabled(true);
                            mMap.getUiSettings().setMyLocationButtonEnabled(false);

                        }else {
                            Toast.makeText(getActivity(), "No se pudo acceder a la ubicación",Toast.LENGTH_SHORT).show();
                            Log.i("MAPS", "PERMISOS UBICACION NO CONCEDIDOS");
                            LatLng sydney = new LatLng(-34, 151);
                            mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
                            mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

                        }
                    }
                });

    }

    public void obtenerUbicacion(){
          mFusedLocationProviderClient=LocationServices.getFusedLocationProviderClient(getActivity());

          try{
              if (permisosConcedidos){
                  Log.i("MAPS---", "1 ");
                  Task ubicacion = mFusedLocationProviderClient.getLastLocation();
                  ubicacion.addOnCompleteListener(new OnCompleteListener() {
                      @Override
                      public void onComplete(@NonNull Task task) {
                          if (task.isSuccessful()){
                              Log.i("MAPS---", "1");
                              Location ubicacionActual=(Location)task.getResult();
                              mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(ubicacionActual.getLatitude(),ubicacionActual.getLongitude()),15f));
                          }else{
                              Toast.makeText(getActivity(), "No se pudo acceder a la ubicación actual",Toast.LENGTH_SHORT).show();
                              Log.i("MAPS---", "2");
                          }
                      }
                  });
              }
          }catch (  SecurityException e){
              Log.e("MAPS_ERROR","getDeviceLocation: "+e.getMessage());
          }
    }



}
