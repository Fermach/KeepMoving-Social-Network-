package com.example.fermach.keepmoving.Loggin;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.fermach.keepmoving.App;
import com.example.fermach.keepmoving.Modelos.Usuario.Usuario;
import com.example.fermach.keepmoving.Perfil_Usuario.PerfilPantallaVista;
import com.example.fermach.keepmoving.R;
import com.example.fermach.keepmoving.Registro.RegistroPantallaVista;

/**
 * Created by Fermach on 27/03/2018.
 */

public class LogginPantallaVista extends Fragment implements LogginPantallaContract.View  {

    private Button btn_registrar;
    private Button btn_iniciar_sesion;
    private EditText et_correo;
    private EditText et_contraseña;
    private View myView;
    private ProgressDialog progressDialog;
    private LogginPantallaContract.Presenter presenter;
    private String correo;
    private String contraseña;
    private Usuario usuario;
    private Fragment fragment;

    public LogginPantallaVista() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.pantalla_login, container, false);

        inicializarVista();
        activarControladores();

        presenter= new LogginPantallaPresenter(this);
        progressDialog=new ProgressDialog(myView.getContext());


        return  myView;
    }

    public void inicializarVista() {
        btn_registrar= myView.findViewById(R.id.btn_registro);
        btn_iniciar_sesion=myView.findViewById(R.id.btn_iniciar_sesion);
        et_correo=myView.findViewById(R.id.editText_correoUsuario);
        et_contraseña=myView.findViewById(R.id.editText_passwordUsuario);
    }

    public void activarControladores() {

        btn_registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new RegistroPantallaVista();
                getFragmentManager().beginTransaction().replace(R.id.content_main, fragment ).commit();

            }
        });

        btn_iniciar_sesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                correo= et_correo.getText().toString().trim();
                contraseña= et_contraseña.getText().toString().trim();

                if(!correo.isEmpty() && !contraseña.isEmpty()) {

                    progressDialog.setMessage("Logueando usuario");
                    progressDialog.show();

                    usuario = new Usuario(correo, contraseña);
                    presenter.loggearUsuario(usuario);
                }else{
                    Snackbar.make(myView,"Debe rellenar todos los campos", Snackbar.LENGTH_SHORT).show();

                }
            }
        });
    }

    @Override
    public void onSesionIniciadaError() {
        progressDialog.dismiss();
        Snackbar.make(myView,"No se pudo iniciar sesión", Snackbar.LENGTH_SHORT).show();

    }

    @Override
    public void onSesionIniciada() {
        progressDialog.dismiss();
        fragment = new PerfilPantallaVista();
        getFragmentManager().beginTransaction().replace(R.id.content_main, fragment ).commit();

    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.iniciarListenerFire();
    }

    @Override
    public void onStop() {
        super.onStop();
       // presenter.detenerListenerFire();
    }
}
