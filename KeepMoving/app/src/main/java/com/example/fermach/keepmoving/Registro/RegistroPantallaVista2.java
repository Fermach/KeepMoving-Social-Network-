package com.example.fermach.keepmoving.Registro;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;

import com.example.fermach.keepmoving.Loggin.LogginPantallaVista;
import com.example.fermach.keepmoving.Modelos.Usuario.Usuario;
import com.example.fermach.keepmoving.R;

/**
 * Created by Fermach on 27/03/2018.
 */

public class RegistroPantallaVista2 extends Fragment implements RegistroPantallaContract.View  {

    private Button btn_atras;
    private Button btn_registrarse;
    private EditText et_nombre;
    private EditText et_biografia;
    private MultiAutoCompleteTextView multi_aficiones;
    private FloatingActionButton fab_foto_registro;
    private View myView;
    private RegistroPantallaContract.Presenter presenter;
    private String nombre;
    private String biografia;
    private String aficiones;
    private Usuario usuario=null;
    private Fragment fragment;

    public RegistroPantallaVista2() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.pantalla_registro2, container, false);

        Bundle args = getArguments();
        usuario=(Usuario) args
                .getSerializable("USUARIO");

        if(usuario==null){
            Log.i("BUNDLE","Error al recibir el usuario");

        }else{
            Log.i("BUNDLE",usuario.toString());
        }

        inicializarVista();
        activarControladores();
        iniciarAdeptadores();

        presenter= new RegistroPantallaPresenter(this);

        return  myView;
    }

    public void inicializarVista() {
        btn_registrarse= myView.findViewById(R.id.btn_registrar_registro);
        btn_atras=myView.findViewById(R.id.btn_atras_registro);
        et_nombre=myView.findViewById(R.id.editText_nombreUsuario_registro);
        et_biografia=myView.findViewById(R.id.editText_biografiaUsuario_registro1);
        multi_aficiones=myView.findViewById(R.id.multiAutoComplete_aficiones_registro);
        fab_foto_registro=myView.findViewById(R.id.fab_usuarioImagen_registro);
    }

    public void activarControladores() {

        btn_registrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btn_atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
    }

    public void iniciarAdeptadores(){


        String[] valores_aficiones= {"Futbol","Tenis","Baloncesto",
                "Running","Rugby","Boxeo","Artes Marciales","Senderismo","Otros"};

        multi_aficiones.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
        multi_aficiones.setAdapter(new ArrayAdapter<String>
                (getContext(),R.layout.support_simple_spinner_dropdown_item,valores_aficiones));


    }

    @Override
    public void onRegistroError() {

    }

    @Override
    public void onRegistro() {

    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onStop() {
        super.onStop();
    }
}
