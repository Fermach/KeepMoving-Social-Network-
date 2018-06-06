package com.example.fermach.keepmoving.Quedadas.Listado_Quedadas.Listado_Solicitudes_Enviadas;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.fermach.keepmoving.Modelos.Quedada.PeticionQuedada;
import com.example.fermach.keepmoving.R;

import java.util.List;

/**
 * Adaptador para cada uno de los items de la lista
 */
public class ListadoSolicitudesEnviadasAdapter extends ArrayAdapter<PeticionQuedada> {

    public ListadoSolicitudesEnviadasAdapter(@NonNull Context context, List<PeticionQuedada> peticionQuedadas) {

        super(context, 0,peticionQuedadas);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        PeticionQuedada peticionQuedada= getItem(position);


        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.listado_peticiones_quedadas_enviadas_item, parent, false);
        }

        TextView deporte = convertView.findViewById(R.id.deporte_texto_peticion_quedada_item);
        TextView plazas = convertView.findViewById(R.id.plazas_texto_peticion_quedada_item);
        TextView lugar = convertView.findViewById(R.id.lugar_texto_peticion_quedada_item);
        TextView fecha = convertView.findViewById(R.id.fecha_texto_peticion_quedada_item);
        TextView hora = convertView.findViewById(R.id.hra_texto_peticion_quedada_item);
        TextView estado = convertView.findViewById(R.id.estado_texto_peticion_quedada_item);
        LinearLayout layoutEstado=convertView.findViewById(R.id.layoutEstado);

        deporte.setText(peticionQuedada.getDeporte());
        plazas.setText(peticionQuedada.getPlazas());
        lugar.setText(peticionQuedada.getLugar());
        fecha.setText(peticionQuedada.getFecha());
        hora.setText(peticionQuedada.getHora());
        estado.setText(peticionQuedada.getEstado());

        //se modifica el texto y color del layout estado dependiendo del estado de la petcion'
        switch (""+peticionQuedada.getEstado()){
            case "ENVIADA":
                layoutEstado.setBackgroundColor(Color.YELLOW);
                break;
            case "ACEPTADA":
                layoutEstado.setBackgroundColor(Color.GREEN);
                break;
            case "RECHAZADA":
                layoutEstado.setBackgroundColor(Color.RED);
                break;
        }

        return convertView;
    }
}
