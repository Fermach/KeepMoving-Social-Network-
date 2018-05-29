package com.example.fermach.keepmoving.Quedadas.Listado_Quedadas.Listado_Completo_Quedadas;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.fermach.keepmoving.Modelos.Quedada.Quedada;
import com.example.fermach.keepmoving.R;

import java.util.List;

public class ListadoQuedadasGeneralAdapter extends ArrayAdapter<Quedada> {

    public ListadoQuedadasGeneralAdapter(@NonNull Context context, List<Quedada> quedadas) {

        super(context, 0,quedadas);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Quedada quedada= getItem(position);


        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.listado_quedadas_general_item, parent, false);
        }

        TextView deporte = convertView.findViewById(R.id.deporte_texto_quedada_general_item);
        TextView plazas = convertView.findViewById(R.id.plazas_texto_quedada_general_item);
        TextView lugar = convertView.findViewById(R.id.lugar_texto_quedada_general_item);
        TextView fecha = convertView.findViewById(R.id.fecha_texto_quedada_general_item);
        TextView hora = convertView.findViewById(R.id.hra_texto_quedada_general_item);

        deporte.setText(quedada.getDeporte());
        plazas.setText(quedada.getPlazas());
        lugar.setText(quedada.getLugar());
        fecha.setText(quedada.getFecha());
        hora.setText(quedada.getHora());


        return convertView;
    }
}