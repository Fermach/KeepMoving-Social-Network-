package com.example.fermach.keepmoving.Quedadas.Listado_Quedadas.Listado_Peticiones_Recibidas;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.fermach.keepmoving.Modelos.Quedada.PeticionQuedadaRecibida;
import com.example.fermach.keepmoving.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Adaptador para cada uno de los items de la lista basado en un RecycledView
 */
public class PeticionesRecibidasAdapter extends RecyclerView.Adapter<PeticionesRecibidasAdapter.MyViewHolder>{

    private List<PeticionQuedadaRecibida> peticionesList;
    CustomButtonListener customButtonListener;
    CustomImageButtonListener customImageButtonListener;
    Button btn_confirmar;
    Button btn_rechazar;
    TextView tv_estado;
    TextView nombre_us;
    TextView plazas_usuario;
    TextView deporte ;
    TextView plazas ;
    TextView lugar ;
    TextView fecha ;
    TextView hora ;
    CircleImageView img_verPerfil;

    //interfazes para saber que item de la iista se esta clickando
    public interface CustomButtonListener {
        public void onButtonClickListner(int position, PeticionQuedadaRecibida pQuedada, String estado);

    }
    public interface CustomImageButtonListener {
        public void onImageButtonListner(int position, PeticionQuedadaRecibida pQuedada);

    }



    public void setCustomButtonListner(CustomButtonListener listener) {
        this.customButtonListener = listener;
    }
    public void setCustomImageButtonListener( CustomImageButtonListener listener) {
        this.customImageButtonListener = listener;
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {
      View view;

      public MyViewHolder(View view) {
        super(view);
        this.view=view;
         nombre_us=view.findViewById(R.id.nombre_autor_peticion_quedada_recibida_item);
         plazas_usuario = view.findViewById(R.id.plazas_solicitadas_quedada_recibida_item);
         deporte = view.findViewById(R.id.deporte_texto_peticion_quedada_recibida_item);
         plazas = view.findViewById(R.id.plazas_texto_peticion_quedada_recibida_item);
         lugar = view.findViewById(R.id.lugar_texto_peticion_quedada_recibida_item);
         fecha = view.findViewById(R.id.fecha_texto_peticion_quedada_recibida_item);
         hora = view.findViewById(R.id.hra_texto_peticion_quedada_recibida_item);
         img_verPerfil=view.findViewById(R.id.fab_usuarioImagen_perfil_quedada_recibida_item);
         btn_confirmar= view.findViewById(R.id.btn_aceptar_peticion_recibida_item);
         btn_rechazar= view.findViewById(R.id.btn_rechazar_peticion_recibida_item);
         tv_estado= view.findViewById(R.id.estado_texto_peticion_quedada_recibida_item);

      }
   }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listado_peticiones_quedadas_recibidas_item , parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public int getItemCount() {
        return peticionesList.size();
    }

    public PeticionesRecibidasAdapter(List<PeticionQuedadaRecibida> peticionesList) {
        this.peticionesList = peticionesList;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final PeticionQuedadaRecibida p = peticionesList.get(position);
        Log.i("HOLA",p.toString());
        nombre_us.setText(p.getAutor_peticion_nombre());
        plazas_usuario.setText(p.getNum_plazas_solicitadas());
        deporte.setText(p.getDeporte());
        plazas.setText(p.getPlazas());
        lugar.setText(p.getLugar());
        fecha.setText(p.getFecha());
        hora.setText(p.getHora());
        tv_estado.setText(p.getEstado());

        if(p.getFoto()!=null) {
            img_verPerfil.setImageBitmap(p.getFoto());
        }

        btn_confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (customButtonListener != null) {
                    customButtonListener.onButtonClickListner(position, p, "ACEPTADA");
                }
            }
        });

        btn_rechazar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(customButtonListener!=null){
                    customButtonListener.onButtonClickListner(position,p,"RECHAZADA" );
                }
            }
        });

        img_verPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(customImageButtonListener!=null){
                    customImageButtonListener.onImageButtonListner(position,p  );
                }
            }
        });
        //  Log.i("HOLLAAAAAAAAAA","HOLAA");

        //dpendiendo del estado setea unos colores  y un texto diferentes en el item de la peticion
        switch (""+p.getEstado()) {
            case "ENVIADA":
                tv_estado.setBackgroundColor(Color.YELLOW);
                btn_confirmar.setEnabled(true);
                btn_rechazar.setEnabled(true);
                btn_confirmar.setBackgroundColor(holder.view.getResources().getColor(android.R.color.darker_gray));
                btn_rechazar.setBackgroundColor(holder.view.getResources().getColor(android.R.color.darker_gray));
                break;
            case "ACEPTADA":
                tv_estado.setBackgroundColor(Color.GREEN);
                btn_confirmar.setEnabled(false);
                btn_confirmar.setBackgroundColor(Color.GREEN);
                btn_rechazar.setBackgroundColor(android.R.drawable.btn_default);
                btn_rechazar.setEnabled(false);
                break;
            case "RECHAZADA":
                tv_estado.setBackgroundColor(Color.RED);
                btn_confirmar.setEnabled(false);
                btn_rechazar.setBackgroundColor(Color.RED);
                btn_rechazar.setEnabled(false);
                btn_confirmar.setBackgroundColor(android.R.drawable.btn_default);

                break;
        }

    }







}

