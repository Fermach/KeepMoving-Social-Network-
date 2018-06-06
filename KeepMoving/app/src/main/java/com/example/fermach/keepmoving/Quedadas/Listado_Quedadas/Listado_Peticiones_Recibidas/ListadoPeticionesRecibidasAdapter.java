package com.example.fermach.keepmoving.Quedadas.Listado_Quedadas.Listado_Peticiones_Recibidas;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.fermach.keepmoving.Modelos.Quedada.PeticionQuedadaRecibida;
import com.example.fermach.keepmoving.R;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Adaptador para cada uno de los items de la lista basado en un arrayAdapter
 */
public class ListadoPeticionesRecibidasAdapter extends ArrayAdapter<PeticionQuedadaRecibida>   {
    private PeticionQuedadaRecibida peticionQuedada;
    private Fragment fragment;
    ViewHolder viewHolder;
    private final String UID_USUARIO= "UID_USUARIO";
    private Context context;
    private List<PeticionQuedadaRecibida> listaPeticionesQuedadas =new ArrayList<>();
    CustomButtonListener customButtonListener;
    CustomImageButtonListener customImageButtonListener;
    CustomImageChangeListener customImageChangeListener;



    public interface CustomButtonListener {
        public void onButtonClickListner(int position, PeticionQuedadaRecibida pQuedada, String estado);

    }
    public interface CustomImageButtonListener {
        public void onImageButtonListner(int position, PeticionQuedadaRecibida pQuedada);

    }
    public interface CustomImageChangeListener {
        public void onImageChangeListener(int position, PeticionQuedadaRecibida pQuedada);

    }

    public void setCustomButtonListner(CustomButtonListener listener) {
        this.customButtonListener = listener;
    }
    public void setCustomImageButtonListener( CustomImageButtonListener listener) {
        this.customImageButtonListener = listener;
    }
    public void setCustomImageChangeListener( CustomImageChangeListener listener) {
        this.customImageChangeListener = listener;
    }

    public ViewHolder getViewHolder() {
        return viewHolder;
    }
    public void setmImageBitmat(byte[] foto ){
        BitmapFactory.Options options = new BitmapFactory.Options();
        viewHolder.img_verPerfil.setImageBitmap( BitmapFactory.decodeByteArray(foto, 0, foto.length, options));

    }

    public class ViewHolder {
        Button btn_confirmar;
        Button btn_rechazar;
        TextView tv_estado;
        CircleImageView img_verPerfil;
    }

    public ListadoPeticionesRecibidasAdapter(@NonNull Context context, List<PeticionQuedadaRecibida> peticionesQuedadas) {
        super(context, 0,peticionesQuedadas);
        this.context=context;
        this.listaPeticionesQuedadas=peticionesQuedadas;

    }



    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final PeticionQuedadaRecibida peticionQuedada= getItem(position);



        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.listado_peticiones_quedadas_recibidas_item, parent, false);
            viewHolder= new ViewHolder();
            viewHolder.img_verPerfil=(CircleImageView) convertView.findViewById(R.id.fab_usuarioImagen_perfil_quedada_recibida_item);
            viewHolder.btn_confirmar= (Button)convertView.findViewById(R.id.btn_aceptar_peticion_recibida_item);
            viewHolder.btn_rechazar= (Button)convertView.findViewById(R.id.btn_rechazar_peticion_recibida_item);
            viewHolder.tv_estado= (TextView)convertView.findViewById(R.id.estado_texto_peticion_quedada_recibida_item);
            convertView.setTag(viewHolder);
        }else{
            viewHolder=(ViewHolder) convertView.getTag();
        }

        viewHolder.btn_confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(customButtonListener!=null){
                    customButtonListener.onButtonClickListner(position,peticionQuedada,"ACEPTADA" );
                }
            }
        });

        viewHolder.btn_rechazar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(customButtonListener!=null){
                    customButtonListener.onButtonClickListner(position,peticionQuedada,"RECHAZADA" );
                }
            }
        });

        viewHolder.img_verPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(customImageButtonListener!=null){
                    customImageButtonListener.onImageButtonListner(position,peticionQuedada  );
                }
            }
        });
      //  Log.i("HOLLAAAAAAAAAA","HOLAA");
        if(customImageChangeListener!=null) {
            customImageChangeListener.onImageChangeListener(position, peticionQuedada);
        }

        viewHolder.tv_estado.setText(peticionQuedada.getEstado());

        TextView nombre_us=convertView.findViewById(R.id.nombre_autor_peticion_quedada_recibida_item);
        TextView plazas_usuario = convertView.findViewById(R.id.plazas_solicitadas_quedada_recibida_item);
        TextView deporte = convertView.findViewById(R.id.deporte_texto_peticion_quedada_recibida_item);
        TextView plazas = convertView.findViewById(R.id.plazas_texto_peticion_quedada_recibida_item);
        TextView lugar = convertView.findViewById(R.id.lugar_texto_peticion_quedada_recibida_item);
        TextView fecha = convertView.findViewById(R.id.fecha_texto_peticion_quedada_recibida_item);
        TextView hora = convertView.findViewById(R.id.hra_texto_peticion_quedada_recibida_item);

        switch (""+peticionQuedada.getEstado()) {
            case "ENVIADA":
                viewHolder.tv_estado.setBackgroundColor(Color.YELLOW);
                viewHolder.btn_confirmar.setEnabled(true);
                viewHolder.btn_rechazar.setEnabled(true);
                viewHolder.btn_confirmar.setBackgroundColor(convertView.getResources().getColor(android.R.color.darker_gray));
                viewHolder.btn_rechazar.setBackgroundColor(convertView.getResources().getColor(android.R.color.darker_gray));
                break;
            case "ACEPTADA":
                viewHolder.tv_estado.setBackgroundColor(Color.GREEN);
                viewHolder.btn_confirmar.setEnabled(false);
                viewHolder.btn_confirmar.setBackgroundColor(Color.GREEN);
                viewHolder.btn_rechazar.setBackgroundColor(android.R.drawable.btn_default);
                viewHolder.btn_rechazar.setEnabled(false);
                break;
            case "RECHAZADA":
                viewHolder.tv_estado.setBackgroundColor(Color.RED);
                viewHolder.btn_confirmar.setEnabled(false);
                viewHolder.btn_rechazar.setBackgroundColor(Color.RED);
                viewHolder.btn_rechazar.setEnabled(false);
                viewHolder.btn_confirmar.setBackgroundColor(android.R.drawable.btn_default);

                break;


        }

        nombre_us.setText(peticionQuedada.getAutor_peticion_nombre());
        plazas_usuario.setText(peticionQuedada.getNum_plazas_solicitadas());
        deporte.setText(peticionQuedada.getDeporte());
        plazas.setText(peticionQuedada.getPlazas());
        lugar.setText(peticionQuedada.getLugar());
        fecha.setText(peticionQuedada.getFecha());
        hora.setText(peticionQuedada.getHora());
        viewHolder.img_verPerfil.setImageBitmap( peticionQuedada.getFoto());


/*

        btn_verPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle args = new Bundle();
                args.putSerializable(UID_USUARIO, peticionQuedada.getAutor_peticion());


                fragment= new PerfilVistaPantallaVista();
                fragment.setArguments(args);
                FragmentTransaction ft = ((AppCompatActivity) getContext()).getSupportFragmentManager()
                        .beginTransaction().replace(R.id.content_main,fragment,UID_USUARIO).addToBackStack(UID_USUARIO);
                ft.commit();
            }
        });
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

*/

        return convertView;
    }






}
