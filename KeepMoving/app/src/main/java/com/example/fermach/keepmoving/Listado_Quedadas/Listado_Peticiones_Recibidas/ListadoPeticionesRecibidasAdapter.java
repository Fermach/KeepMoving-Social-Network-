package com.example.fermach.keepmoving.Listado_Quedadas.Listado_Peticiones_Recibidas;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.fermach.keepmoving.Listado_Quedadas.Listado_Solicitudes_Enviadas.ListadoSolicitudesEnviadasContract;
import com.example.fermach.keepmoving.Modelos.Quedada.PeticionQuedada;
import com.example.fermach.keepmoving.Perfil_Usuario.PerfilPantallaVista;
import com.example.fermach.keepmoving.R;
import com.michaelmuenzer.android.scrollablennumberpicker.ScrollableNumberPicker;

import java.util.List;

public class ListadoPeticionesRecibidasAdapter extends ArrayAdapter<PeticionQuedada> implements ListadoPeticionesRecibidasAdapterContract.View {
    private ListadoPeticionesRecibidasAdapterContract.Presenter presenter;
    private PeticionQuedada peticionQuedada;
    private Fragment fragment;
    private TextView deporte;
    private TextView plazas;
    private TextView lugar;
    private TextView fecha;
    private TextView hora;
    private TextView estado;
    private TextView plazas_usuario;
    private TextView nombre_us;
    private ScrollableNumberPicker imagen;
    private Button btn_confirmar;
    private Button btn_rechazar;


    public ListadoPeticionesRecibidasAdapter(@NonNull Context context, List<PeticionQuedada> peticionQuedadas) {
        super(context, 0,peticionQuedadas);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        peticionQuedada= getItem(position);
        presenter = new ListadoPeticionesRecibidasAdapterPresenter(this);

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.listado_peticiones_quedadas_recibidas_item, parent, false);
        }

        nombre_us=convertView.findViewById(R.id.nombre_autor_quedada_recibida_item);
        plazas_usuario = convertView.findViewById(R.id.plazas_solicitadas_quedada_recibida_item);
        deporte = convertView.findViewById(R.id.deporte_texto_peticion_quedada_recibida_item);
        plazas = convertView.findViewById(R.id.plazas_texto_peticion_quedada_recibida_item);
        lugar = convertView.findViewById(R.id.lugar_texto_peticion_quedada_recibida_item);
        fecha = convertView.findViewById(R.id.fecha_texto_peticion_quedada_recibida_item);
        hora = convertView.findViewById(R.id.hra_texto_peticion_quedada_recibida_item);
        estado = convertView.findViewById(R.id.estado_texto_peticion_quedada_recibida_item);
        btn_confirmar = convertView.findViewById(R.id.btn_aceptar_peticion_recibida_item);
        btn_rechazar = convertView.findViewById(R.id.btn_rechazar_peticion_recibida_item);

        plazas_usuario.setText(peticionQuedada.getNum_plazas_solicitadas());
        deporte.setText(peticionQuedada.getDeporte());
        plazas.setText(peticionQuedada.getPlazas());
        lugar.setText(peticionQuedada.getLugar());
        fecha.setText(peticionQuedada.getFecha());
        hora.setText(peticionQuedada.getHora());
        estado.setText(peticionQuedada.getEstado());

        switch (""+peticionQuedada.getEstado()){
            case "ENVIADA":
                estado.setBackgroundColor(Color.YELLOW);
                break;
            case "ACEPTADA":
                estado.setBackgroundColor(Color.GREEN);
                btn_confirmar.setEnabled(false);
                btn_confirmar.setBackgroundColor(Color.GREEN);
                btn_rechazar.setEnabled(false);
                break;
            case "RECHAZADA":
                estado.setBackgroundColor(Color.RED);
                btn_confirmar.setEnabled(false);
                btn_rechazar.setBackgroundColor(Color.RED);
                btn_rechazar.setEnabled(false);
                break;
        }


        btn_confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                peticionQuedada.setEstado("ACEPTADA");
                presenter.cambiarEstadoQuedada(peticionQuedada);
            }
        });

        btn_rechazar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                peticionQuedada.setEstado("RECHAZADA");
                presenter.cambiarEstadoQuedada(peticionQuedada);
            }
        });

        return convertView;
    }


    @Override
    public void onEstadoCambiado() {

        fragment= new ListadoPeticionesRecibidasVista();
        FragmentTransaction ft = ((AppCompatActivity) getContext()).getSupportFragmentManager()
                .beginTransaction().replace(R.id.content_main,fragment);
        ft.commit();

    }

    @Override
    public void onEstadoCambiadoError() {

    }
}
