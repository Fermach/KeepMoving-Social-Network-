package com.example.fermach.keepmoving.Quedadas.Detalle_Quedada.DetalleQuedadaGeneral;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.BitmapFactory;
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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.fermach.keepmoving.Quedadas.Listado_Quedadas.Listado_Completo_Quedadas.ListadoQuedadasGeneralVista;
import com.example.fermach.keepmoving.MainActivity.ChangeToolbar;
import com.example.fermach.keepmoving.Modelos.Quedada.Quedada;
import com.example.fermach.keepmoving.Usuarios.Perfil_Usuario.PerfilPantallaVista;
import com.example.fermach.keepmoving.Quedadas.Peticion_Quedada.PeticionQuedadaVista;
import com.example.fermach.keepmoving.R;
import com.example.fermach.keepmoving.Usuarios.Usuario_Perfil_Vista.PerfilVistaPantallaVista;
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

import de.hdodenhof.circleimageview.CircleImageView;

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
    private final String QUEDADA="QUEDADA";
    private final String QUEDADA_ID="QUEDADA_ID";
    private final String UID_USUARIO="UID_USUARIO";
    private Button btn_apuntarse;
    private Button btn_atras;
    private ImageView img_gps;
    private LinearLayout layout_usuario_autor;
    private TextView tv_fecha;
    private TextView tv_hora;
    private TextView tv_lugar;
    private TextView tv_mas_info;
    private TextView tv_autor;
    private CircleImageView imagen_autor;
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
        ((ChangeToolbar)getActivity()).setToolbarText("Detalle quedada");

        progressDialog= new ProgressDialog(myView.getContext());
        presenter = new DetalleQuedadaPresenter(this);

        Bundle args = getArguments();
        quedada =(Quedada) args
                .getSerializable("QUEDADA");

        Log.i("QUEDADA_GEN_DETALLE",    quedada.toString());

        inicializarVista();

        progressDialog.setMessage("Obteniendo Ubicación");
        progressDialog.setCancelable(false);
        progressDialog.show();

        presenter.obtenerFotoUsuario(quedada.getAutor_uid());
        presenter.obtenerUsuarioActual();

        iniciarMaps();

        return myView;
    }

    public void inicializarVista() {
        imagen_autor= myView.findViewById(R.id.fab_usuario_imagen_quedada_general);
        btn_apuntarse = myView.findViewById(R.id.btn_apuntarse_detalle_quedada_general);
        btn_atras = myView.findViewById(R.id.btn_cancelar_detalle_quedada_general);
        tv_fecha = myView.findViewById(R.id.text_fecha_detalle_quedada_general);
        tv_hora = myView.findViewById(R.id.text_hora_detalle_quedada_general);
        layout_usuario_autor=myView.findViewById(R.id.layout_autor_detalle_quedada_general);
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

    public void activarControladoresGenerales() {


        layout_usuario_autor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isOnlineNet()) {
                    //    Toast.makeText(getContext(),"PULSADO",Toast.LENGTH_SHORT).show();

                    Bundle bundle = new Bundle();
                    bundle.putSerializable(UID_USUARIO, quedada.getAutor_uid());
                    Fragment toFragment = new PerfilVistaPantallaVista();
                    toFragment.setArguments(bundle);
                    getFragmentManager()
                            .beginTransaction()
                            .replace(R.id.content_main, toFragment, UID_USUARIO)
                            .addToBackStack(UID_USUARIO).commit();
                }else{
                    Snackbar.make(myView,"No hay conexión a internet", Snackbar.LENGTH_SHORT).show();

                }
            }
        });



        btn_apuntarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isOnlineNet()) {
                    presenter.verificarPeticionQuedada(quedada);
                }else{

                    Snackbar.make(myView,"No hay conexión a internet", Snackbar.LENGTH_SHORT).show();

                }
            }
        });

        btn_atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isOnlineNet()) {
                    fragment = new ListadoQuedadasGeneralVista();
                    getFragmentManager().beginTransaction().replace(R.id.content_main, fragment).commit();
                }
                else{
                    Snackbar.make(myView,"No hay conexión a internet", Snackbar.LENGTH_SHORT).show();
                }

            }
        });
    }

    public void activarControladoresMiQuedada() {

        layout_usuario_autor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isOnlineNet()) {
                    //  Toast.makeText(getContext(),"PULSADO",Toast.LENGTH_SHORT).show();
                    fragment = new PerfilPantallaVista();
                    getFragmentManager().beginTransaction().replace(R.id.content_main, fragment).commit();
                }else{
                        Snackbar.make(myView,"No hay conexión a internet", Snackbar.LENGTH_SHORT).show();
                    }
            }
        });

        btn_apuntarse.setText("Editar");
        btn_apuntarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isOnlineNet()) {
                Bundle bundle = new Bundle();
                bundle.putSerializable(QUEDADA, quedada);
                Fragment toFragment = new PeticionQuedadaVista();
                toFragment.setArguments(bundle);
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content_main, toFragment, QUEDADA)
                        .addToBackStack(QUEDADA).commit();
                }else{
                    Snackbar.make(myView,"No hay conexión a internet", Snackbar.LENGTH_SHORT).show();
                }
            }
        });

        btn_atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isOnlineNet()) {
                fragment = new ListadoQuedadasGeneralVista();
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


    @Override
    public void onUsuarioActualObtenido(String uid) {

        Log.i("USUARIO_ACTUAL_OBTENIDO","Usuario QUEDADA: "+quedada.getAutor_uid()+
                        "Usuario ACTUAL: "+uid);

        if(uid.equals(quedada.getAutor_uid())){

            Log.i("USUARIO_ACTUAL_OBTENIDO"," 1");
            activarControladoresMiQuedada();
        }else{

            Log.i("USUARIO_ACTUAL_OBTENIDO"," 2");
            activarControladoresGenerales();

        }
    }

    @Override
    public void onPeticionLibre() {

        Bundle bundle = new Bundle();
        bundle.putSerializable(QUEDADA, quedada);
        Fragment toFragment = new PeticionQuedadaVista();
        toFragment.setArguments(bundle);
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.content_main, toFragment, QUEDADA)
                .addToBackStack(QUEDADA).commit();
    }

    @Override
    public void onPeticionOcupada() {

        AlertDialog.Builder myBuild = new AlertDialog.Builder(getContext());
        myBuild.setMessage("Ya existe una petición suya para participar en esta quedada, usted no puede apuntarse dos veces" +
                        " en la misma quedada!" );
        myBuild.setTitle("Alerta");

        myBuild.setNegativeButton("Cerrar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        myBuild.show();
    }

    @Override
    public void onUsuarioFotoObtenida(byte[]  foto) {

        BitmapFactory.Options options = new BitmapFactory.Options();
        imagen_autor.setImageBitmap( BitmapFactory.decodeByteArray(foto, 0, foto.length, options));
    }

    @Override
    public void onUsuarioFotoObtenidaError() {
        Snackbar.make(myView,"No se pudo obtener la foto del autor de la quedada", 4000).show();
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
