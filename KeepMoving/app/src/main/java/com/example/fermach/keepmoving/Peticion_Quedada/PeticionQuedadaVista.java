package com.example.fermach.keepmoving.Peticion_Quedada;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.fermach.keepmoving.Detalle_Quedada.DetalleQuedadaGeneral.DetalleQuedadaVista;
import com.example.fermach.keepmoving.Listado_Quedadas.Listado_Completo_Quedadas.ListadoQuedadasGeneralAdapter;
import com.example.fermach.keepmoving.Listado_Quedadas.Listado_Completo_Quedadas.ListadoQuedadasGeneralContract;
import com.example.fermach.keepmoving.Listado_Quedadas.Listado_Completo_Quedadas.ListadoQuedadasGeneralPresenter;
import com.example.fermach.keepmoving.Listado_Quedadas.Listado_Quedadas_Por_Usuario.ListadoQuedadasUsuarioVista;
import com.example.fermach.keepmoving.Listado_Quedadas.Listado_Solicitudes_Enviadas.ListadoSolicitudesEnviadasVista;
import com.example.fermach.keepmoving.Modelos.Quedada.PeticionQuedada;
import com.example.fermach.keepmoving.Modelos.Quedada.Quedada;
import com.example.fermach.keepmoving.Perfil_Usuario.PerfilPantallaVista;
import com.example.fermach.keepmoving.R;
import com.michaelmuenzer.android.scrollablennumberpicker.ScrollableNumberPicker;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fermach on 27/03/2018.
 */

public class PeticionQuedadaVista extends Fragment implements PeticionQuedadaContract.View {

    private Quedada quedada;
    private PeticionQuedada peticionQuedada;
    private Fragment fragment;
    private int plazas_seleccionadas;
    private int plazas_max;
    private View myView;
    private TextView tv_deporte;
    private TextView tv_plazas;
    private TextView tv_lugar;
    private TextView tv_fecha;
    private Button btn_enviar;
    private Button btn_cancelar;
    private TextView tv_hora;
    private TextView tv_autor;
    private ScrollableNumberPicker plazas_reserva;
    private PeticionQuedadaContract.Presenter presenter;
    private final String QUEDADA ="QUEDADA";
    private ProgressDialog progressDialog;

    public PeticionQuedadaVista() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.peticion_quedada, container, false);

        Bundle args = getArguments();
        quedada =(Quedada) args
                .getSerializable("QUEDADA");


        presenter = new PeticionQuedadaPresenter(this);

        inicializarVista();
        activarControladores();

        return myView;
    }



    public void inicializarVista() {
        tv_autor=myView.findViewById(R.id.autor_texto_peticion_quedada);
        tv_fecha=myView.findViewById(R.id.fecha_texto_peticion_quedada);
        tv_hora=myView.findViewById(R.id.hra_texto_peticion_quedada);
        tv_lugar=myView.findViewById(R.id.lugar_texto_peticion_quedada);
        tv_plazas=myView.findViewById(R.id.plazas_texto_peticion_quedada);
        tv_deporte=myView.findViewById(R.id.deporte_texto_peticion_quedada);
        plazas_reserva=myView.findViewById(R.id.plazas_peticion_quedada);
        btn_cancelar=myView.findViewById(R.id.btn_cancelar_peticion_quedada);
        btn_enviar=myView.findViewById(R.id.btn_enviar_peticion_quedada);

        tv_autor.setText(quedada.getAutor());
        tv_hora.setText(quedada.getHora());
        tv_fecha.setText(quedada.getFecha());
        tv_lugar.setText(quedada.getLugar());
        tv_plazas.setText(quedada.getPlazas());
        tv_deporte.setText(quedada.getDeporte());

    }

    public void activarControladores(){
       btn_enviar.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {


               AlertDialog.Builder myBuild = new AlertDialog.Builder(getContext());
               myBuild.setMessage("¿Estás seguro de que desea enviar esta solicitud? \n\n" +
                       "Una vez enviada la solicitud no se podrá deshacer!");
               myBuild.setTitle("Enviar Solicitud");

               myBuild.setNegativeButton("No", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {
                       dialog.cancel();
                   }
               });
               myBuild.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {
                       plazas_seleccionadas=Integer.parseInt(""+plazas_reserva.getValue());
                       plazas_max=Integer.parseInt(tv_plazas.getText().toString());

                       if(plazas_seleccionadas!=0 && plazas_seleccionadas<plazas_max){
                           peticionQuedada=new PeticionQuedada(quedada.getId(),quedada.getAutor(),quedada.getAutor_uid(),
                                   quedada.getLugar(),quedada.getFecha(),quedada.getHora(),quedada.getDeporte(),
                                   quedada.getInfo(),quedada.getPlazas(),quedada.getLongitud(),quedada.getLatitud(),
                                   ""+plazas_seleccionadas,"ENVIADA");

                           presenter.EnviarSolicitud(peticionQuedada);
                       }
                   }
               });

           }
       });

       btn_cancelar.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Bundle bundle = new Bundle();
               bundle.putSerializable(QUEDADA, quedada);
               Fragment toFragment = new DetalleQuedadaVista();
               toFragment.setArguments(bundle);
               getFragmentManager()
                       .beginTransaction()
                       .replace(R.id.content_main, toFragment, QUEDADA)
                       .addToBackStack(QUEDADA).commit();
           }
       });

    }

    @Override
    public void onSolicitudEnviada() {
        Snackbar.make(myView,"Peticion enviada correctamente", 4000).show();

        new Handler().postDelayed(new Runnable(){
            public void run(){

                fragment = new ListadoSolicitudesEnviadasVista();
                getFragmentManager().beginTransaction().replace(R.id.content_main, fragment ).commit();

            };
        }, 2000);
    }

    @Override
    public void onSolicitudEnviadaError() {
        Snackbar.make(myView,"No se pudo enviar la peticion", 4000).show();

    }
}
