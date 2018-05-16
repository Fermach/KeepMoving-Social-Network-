package com.example.fermach.keepmoving.Listado_Quedadas.Listado_Quedadas_Por_Usuario;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.fermach.keepmoving.Editar_Quedada.EditarQuedadaVista;
import com.example.fermach.keepmoving.Modelos.Quedada.Quedada;
import com.example.fermach.keepmoving.R;



public class ListadoQuedadasUsuarioMenuLClick extends DialogFragment {

    private Quedada mQuedada;
    private ListadoQuedadasUsuarioContract.Presenter presenter;
    private final String QUEDADA_ID ="QUEDADA_ID";
    private final String ELIMINAR_QUEDADA ="ELIMINAR_QUEDADA";
    private final String QUEDADA_A_ELIMINAR_ID ="QUEDADA_A_ELIMINAR_ID";


    public static ListadoQuedadasUsuarioMenuLClick newInstance(Quedada quedada) {

        Bundle args = new Bundle();

        //mandamos la quedada clickeada en nuestra lista
        args.putSerializable("QUEDADA", quedada);
        ListadoQuedadasUsuarioMenuLClick fragment = new ListadoQuedadasUsuarioMenuLClick();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter= new ListadoQuedadasUsuarioPresenter();

        //cuando se inicia el menu se recoge la quedada clickeada de nuestra lista
        mQuedada =(Quedada) getArguments()
                .getSerializable("QUEDADA");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
      final View v = inflater.inflate(R.layout.menu_edicion_borrado_quedada, container, false);


        Button borrar_rutina= v.findViewById(R.id.btn_borrar_quedada_menu);
        Button editar_rutina= v.findViewById(R.id.btn_editar_quedada_menu);


        borrar_rutina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Al pulsar en el boton de borrar rutina se crea un dialogo que pregunte
                //si estamos seguros de su borrado
                AlertDialog.Builder myBuild = new AlertDialog.Builder(view.getContext());
                myBuild.setMessage("¿Estás seguro de que deseas eliminar esta quedada?");
                myBuild.setTitle("Eliminar Quedada");
                myBuild.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //si elegimos Si se cierra el dialogo y el fragmento
                        //y realizamos un transaccion al fragmento de la lista de quedada pasandole
                        //el id de la quedada a eliminar y una llave para saber que queremos eliminar una quedada

                        dialogInterface.cancel();
                        getDialog().dismiss();
                        Bundle args_eli = new Bundle();
                        args_eli.putSerializable(QUEDADA_A_ELIMINAR_ID, mQuedada.getId());
                        args_eli.putSerializable(ELIMINAR_QUEDADA, true);
                        Fragment toFragment = new ListadoQuedadasUsuarioVista();
                        toFragment.setArguments(args_eli);
                        getFragmentManager()
                                .beginTransaction()
                                .replace(R.id.content_main, toFragment)
                                .addToBackStack(ELIMINAR_QUEDADA).addToBackStack(QUEDADA_A_ELIMINAR_ID).commit();
                    }
                });
                myBuild.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        //si elegimos no se cierra el dialogo, y se mantiene el menu abierto
                        dialogInterface.cancel();
                    }
                });
                myBuild.show();

             }
        });

        editar_rutina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Si seleccionamos "Editar quedada" nos lleva al fragmento de editar quedada pasandole el id de la quedada a editar
                getDialog().dismiss();
                Bundle bundle = new Bundle();
                bundle.putSerializable(QUEDADA_ID, mQuedada);
                Fragment toFragment = new EditarQuedadaVista();
                toFragment.setArguments(bundle);
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content_main, toFragment, QUEDADA_ID)
                        .addToBackStack(QUEDADA_ID).commit();
            }
        });

      return v;
    }
}
