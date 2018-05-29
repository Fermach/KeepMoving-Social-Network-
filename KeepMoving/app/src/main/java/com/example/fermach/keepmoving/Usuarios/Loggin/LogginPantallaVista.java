package com.example.fermach.keepmoving.Usuarios.Loggin;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.fermach.keepmoving.MainActivity.ChangeToolbar;
import com.example.fermach.keepmoving.MainActivity.DrawerLocker;
import com.example.fermach.keepmoving.Modelos.Usuario.Usuario;
import com.example.fermach.keepmoving.R;
import com.example.fermach.keepmoving.Usuarios.Registro.Registro_Basico.RegistroPantallaVista;

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

        ((DrawerLocker)getActivity()).setDrawerLocked(true);
        ((ChangeToolbar)getActivity()).hideToolbar(true);
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
                if(isOnlineNet()) {

                    fragment = new RegistroPantallaVista();
                    getFragmentManager().beginTransaction().replace(R.id.content_main, fragment).commit();
                }else{

                    Snackbar.make(myView,"No hay conexión a internet", Snackbar.LENGTH_SHORT).show();

                }
            }
        });

        btn_iniciar_sesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isOnlineNet()) {

                    correo = et_correo.getText().toString().trim();
                    contraseña = et_contraseña.getText().toString().trim();

                    if (!correo.isEmpty() && !contraseña.isEmpty()) {

                        progressDialog.setMessage("Logueando usuario");
                        progressDialog.setCancelable(false);
                        progressDialog.show();

                        usuario = new Usuario(correo, contraseña);
                        presenter.setTOKEN("LOGGIN");
                    } else {
                        Snackbar.make(myView, "Debe rellenar todos los campos", Snackbar.LENGTH_SHORT).show();

                    }
                }else{


                }


            }
        });
    }

    @Override
    public void onSesionIniciadaError() {
       progressDialog.dismiss();
        Snackbar.make(myView,"No se pudo iniciar sesión en este momento, vuelva a intentarlo más tarde.", Snackbar.LENGTH_SHORT).show();

    }

    @Override
    public void onSesionIniciada() {
        progressDialog.dismiss();


    }

    @Override
    public void onTOKENselecionado() {
        presenter.loggearUsuario(usuario);
    }


    @Override
    public void onStart() {
        super.onStart();
        Log.i("Start","onStart");
        //presenter.iniciarListenerFire();
    }

    @Override
    public void onStop() {
        super.onStop();
       // presenter.detenerListenerFire();
    }

    public Boolean isOnlineNet() {

        try {
            Process p = java.lang.Runtime.getRuntime().exec("ping -c 1 www.google.es");

            int val           = p.waitFor();
            boolean reachable = (val == 0);
            return reachable;

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }

}
