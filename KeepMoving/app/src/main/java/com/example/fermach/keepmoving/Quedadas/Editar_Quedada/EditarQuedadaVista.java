package com.example.fermach.keepmoving.Quedadas.Editar_Quedada;

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

import com.example.fermach.keepmoving.Quedadas.Crear_Quedadas.DatePickerFragment;
import com.example.fermach.keepmoving.Quedadas.Crear_Quedadas.TimePickerFragment;
import com.example.fermach.keepmoving.MainActivity.ChangeToolbar;
import com.example.fermach.keepmoving.Modelos.Quedada.Quedada;
import com.example.fermach.keepmoving.Usuarios.Perfil_Usuario.PerfilPantallaVista;
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

public class EditarQuedadaVista extends Fragment implements EditarQuedadaContract.View {

    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final int LOCATION_PERMISIONS_REQUEST_CODE = 1234;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final LatLngBounds LAT_LNG_BOUNDS= new LatLngBounds(new LatLng(-40,-168),
            new LatLng(71,136));
    private boolean permisosConcedidos;
    private Quedada quedada;
    private Button btn_guardar;
    private Button btn_cancelar;
    private ImageView img_gps;
    private TextView tv_fecha;
    private TextView tv_hora;
    private EditText tv_lugar;
    private TextView tv_mas_info;
    private GoogleApiClient googleApiClient;
    private AdapterView spinner_deporte;
    private ScrollableNumberPicker picker_plazas;
    private Fragment fragment;
    private GoogleMap mMap;
    private boolean ubicacionEncontrada;
    private View myView;
    private EditarQuedadaContract.Presenter presenter;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private LatLng latLng;
    private Date cDate;
    private Address localizacion;
    private List<Address> lista;
    private Geocoder geocoder;
    private MarkerOptions markerOptions;
    private ProgressDialog progressDialog;
    private String fecha;
    private String hora;
    private String longitud;
    private String latitud;
    private String lugar;
    private String id;
    private String deporte;
    private String mas_info;
    private String plazas;
    private FusedLocationProviderClient mFusedLocationProviderClient;

    //private double lat = 0, lng = 0;


    public EditarQuedadaVista() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.pantalla_editar_quedada, container, false);

        ((ChangeToolbar)getActivity()).setToolbarText("Editar quedada");

        cDate = new Date();
        progressDialog= new ProgressDialog(myView.getContext());
        presenter = new EditarQuedadaPresenter(this);
        permisosConcedidos=false;
        ubicacionEncontrada=false;


        Bundle args = getArguments();

        //se comprueba si se estan recibiendo argumentos de otro fragmento
        //para saber si el menu de borrado nos esta pasando una llave para que borremos una
        //quedada de la lista
        if(args!=null) {

            //recoger usuario
            quedada = (Quedada) args.getSerializable("QUEDADA_ID");
            Log.i("Argumentos", "RECOGIDOS =" + quedada.toString());

        }else{
            Log.i("Argumentos", "NULOS" );

        }

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
        btn_cancelar = myView.findViewById(R.id.btn_cancelar_editar_quedada);
        btn_guardar = myView.findViewById(R.id.btn_guardar_editar_quedada);
        tv_fecha = myView.findViewById(R.id.editText_fecha_editar_quedada);
        tv_hora = myView.findViewById(R.id.editText_hora_editar_quedada);
        tv_lugar = myView.findViewById(R.id.buscar_editar_quedada);
        tv_mas_info = myView.findViewById(R.id.descripcion_editar_quedada);
        spinner_deporte = myView.findViewById(R.id.spinner_deporte_editar_quedada);
        picker_plazas = myView.findViewById(R.id.plazas_editar_quedada);
        img_gps=myView.findViewById(R.id.img_ubicacion_editar_quedada);


        String[] valores_deportes = {quedada.getDeporte(),"Futbol", "Tenis", "Baloncesto",
                "Running", "Rugby", "Boxeo", "Artes Marciales", "Senderismo", "Otros"};
        ArrayAdapter<String> adapter= new ArrayAdapter<String>
                (getContext(), R.layout.support_simple_spinner_dropdown_item, valores_deportes);

        spinner_deporte.setAdapter(adapter);

        fecha = new SimpleDateFormat("dd-M-YYYY").format(cDate);
        hora = new SimpleDateFormat("HH:mm").format(cDate);

        tv_fecha.setText(quedada.getFecha());
        tv_hora.setText(quedada.getHora());
        tv_lugar.setText(quedada.getLugar());
        tv_mas_info.setText(quedada.getInfo());
        picker_plazas.setValue(Integer.parseInt(quedada.getPlazas()));



    }

    public void activarControladores() {

        img_gps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                obtenerUbicacion();
            }
        });


        tv_lugar.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_SEARCH
                        || actionId== EditorInfo.IME_ACTION_DONE
                        || event.getAction()==KeyEvent.ACTION_DOWN
                        || event.getAction()==KeyEvent.KEYCODE_ENTER ){

                   buscarLugar();
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


        btn_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isOnlineNet()) {
                    lugar = "" + tv_lugar.getText().toString().trim();
                    hora = "" + tv_hora.getText().toString().trim();
                    fecha = "" + tv_fecha.getText().toString().trim();
                    mas_info = "" + tv_mas_info.getText().toString().trim();
                    plazas = "" + picker_plazas.getValue();
                    deporte = "" + spinner_deporte.getSelectedItem().toString().trim();


                    if (!lugar.isEmpty() && !hora.isEmpty() &&
                            !fecha.isEmpty() && !deporte.isEmpty() && !plazas.isEmpty()) {
                        //subir quedada

                        buscarLugar();
                        btn_guardar.setEnabled(false);
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }


                        if (ubicacionEncontrada == true) {


                            progressDialog.setMessage("Se están actualizando los datos de la quedada");
                            progressDialog.setCancelable(false);
                            progressDialog.show();

                            longitud = "" + localizacion.getLongitude();
                            latitud = "" + localizacion.getLatitude();
                            //  id=""+ (localizacion.getLongitude()+localizacion.getLatitude()) + ""+fecha+hora;

                            quedada = new Quedada(quedada.getId(), quedada.getAutor(), lugar, fecha, hora, deporte, mas_info, plazas,
                                    longitud, latitud);

                            presenter.editarQuedada(quedada);
                        } else {
                            btn_guardar.setEnabled(true);
                            Snackbar.make(myView, "No se pudo encontrar la ubicación seleccionada!", 4000).show();

                        }

                    } else {
                        Snackbar.make(myView, "Debe rellenar todos los campos obligatorios", Snackbar.LENGTH_SHORT).show();

                    }

            }else {
                Snackbar.make(myView, "No hay conexión a internet", Snackbar.LENGTH_SHORT).show();
            }
         }
        });

        btn_cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isOnlineNet()) {

                    fragment = new PerfilPantallaVista();
                getFragmentManager().beginTransaction().replace(R.id.content_main, fragment).commit();
                }else {
                    Snackbar.make(myView, "No hay conexión a internet", Snackbar.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();

    }



    public void buscarLugar(){
        String lugar= tv_lugar.getText().toString().trim();
        ubicacionEncontrada=false;
        this.geocoder= new Geocoder(getActivity());
        this.lista = new ArrayList<>();


        try {
            lista =geocoder.getFromLocationName(lugar,1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(lista.size()>0){
            this.localizacion= lista.get(0);
            ubicacionEncontrada=true;
            Log.i("UBICACION A BUSCAR", localizacion.toString());
            this.latLng= new LatLng(localizacion.getLatitude(),localizacion.getLongitude());
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,15f));
            this.markerOptions= new MarkerOptions().position(latLng).title(localizacion.getAddressLine(0));

            mMap.addMarker(markerOptions);

        }

    }


    public void iniciarMaps(){

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                        .findFragmentById(R.id.map_editar_quedada);

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


    @Override
    public void onQuedadaEditada() {
        Snackbar.make(myView,"Quedada modificada correctamente", 4000).show();
        btn_guardar.setEnabled(true);
        new Handler().postDelayed(new Runnable(){
            public void run(){
                progressDialog.dismiss();
                fragment = new PerfilPantallaVista();
                getFragmentManager().beginTransaction().replace(R.id.content_main, fragment ).commit();

            };
        }, 2000);
    }

    @Override
    public void onQuedadaEditadaError() {
        btn_guardar.setEnabled(true);
        progressDialog.dismiss();
        Snackbar.make(myView,"No fue posible modificar la quedada ", 4000).show();


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
