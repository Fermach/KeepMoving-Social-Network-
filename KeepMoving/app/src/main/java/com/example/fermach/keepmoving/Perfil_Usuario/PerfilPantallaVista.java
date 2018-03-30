package com.example.fermach.keepmoving.Perfil_Usuario;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.fermach.keepmoving.R;

/**
 * Created by Fermach on 27/03/2018.
 */

public class PerfilPantallaVista extends Fragment implements PerfilPantallaContract.View  {

    private Button btn_mis_quedadas;
    private Button btn_editar_datos;
    private TextView tv_nombre;
    private TextView tv_aficiones;
    private TextView tv_bio;
    private View myView;
    private PerfilPantallaContract.Presenter presenter;


    public PerfilPantallaVista() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.pantalla_login, container, false);

        inicializarVista();
        activarControladores();

        presenter= new PerfilPantallaPresenter(this);
        return  myView;
    }

    public void inicializarVista() {
        btn_mis_quedadas= myView.findViewById(R.id.btn_mis_quedadas_perfil);
        btn_editar_datos=myView.findViewById(R.id.btn_modificar_perfil);
        tv_nombre=myView.findViewById(R.id.nombre_usuario_perfil);
        tv_bio=myView.findViewById(R.id.biografia_usuario_perfil);
        tv_aficiones=myView.findViewById(R.id.aficiones_usuario_perfil);
    }

    public void activarControladores() {

        btn_mis_quedadas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btn_editar_datos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

}
