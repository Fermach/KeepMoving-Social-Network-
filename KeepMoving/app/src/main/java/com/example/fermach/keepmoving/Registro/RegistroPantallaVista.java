package com.example.fermach.keepmoving.Registro;

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
import com.example.fermach.keepmoving.Loggin.LogginPantallaVista;
import com.example.fermach.keepmoving.Modelos.Usuario.Usuario;
import com.example.fermach.keepmoving.Perfil_Usuario.PerfilPantallaVista;
import com.example.fermach.keepmoving.R;

/**
 * Created by Fermach on 27/03/2018.
 */

public class RegistroPantallaVista extends Fragment implements RegistroPantallaContract.View  {

    private Button btn_cancelar;
    private Button btn_siguiente;
    private EditText et_correo;
    private EditText et_contraseña;
    private EditText et_contraseña2;
    private View myView;
    private RegistroPantallaContract.Presenter presenter;
    private String correo;
    private String contraseña;
    private String contraseña2;
    private ProgressDialog progressDialog;
    private Usuario usuario;
    private Fragment fragment;
    private final String USUARIO="USUARIO";

    public RegistroPantallaVista() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.pantalla_registro1, container, false);

        inicializarVista();
        activarControladores();

        presenter= new RegistroPantallaPresenter(this);
        progressDialog=new ProgressDialog(myView.getContext());

        return  myView;
    }

    public void inicializarVista() {

        btn_siguiente= myView.findViewById(R.id.btn_siguiente1_registro);
        btn_cancelar=myView.findViewById(R.id.btn_cancelar_registro);
        et_correo=myView.findViewById(R.id.editText_correoUsuario_registro);
        et_contraseña=myView.findViewById(R.id.editText_passwordUsuario_registro1);
        et_contraseña2=myView.findViewById(R.id.editText_passwordUsuario_registro2);
    }

    public void activarControladores() {

        btn_siguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                correo= et_correo.getText().toString().trim();
                contraseña= et_contraseña.getText().toString().trim();
                contraseña2= et_contraseña2.getText().toString().trim();

                if(!correo.isEmpty() && !contraseña.isEmpty()  && !contraseña2.isEmpty())  {

                    if(contraseña2.equals(contraseña)) {

                        progressDialog.setMessage("Se están validando los datos");
                        progressDialog.show();

                        usuario = new Usuario(correo, contraseña);
                        presenter.registrarUsuario(usuario);

                    }else{
                        Snackbar.make(myView,"Deben coincidir las contraseñas", Snackbar.LENGTH_SHORT).show();

                    }
                }else{
                    Snackbar.make(myView,"Debe rellenar todos los campos", Snackbar.LENGTH_SHORT).show();

                }
            }
        });

        btn_cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fragment = new LogginPantallaVista();
                getFragmentManager().beginTransaction().replace(R.id.content_main, fragment ).commit();


            }
        });
    }

    @Override
    public void onRegistroError() {
        Snackbar.make(myView,"No se pudo iniciar sesión", Snackbar.LENGTH_SHORT).show();
        progressDialog.dismiss();
    }

    @Override
    public void onRegistro() {
        fragment = new RegistroPantallaVista2();
        progressDialog.dismiss();

        Bundle args = new Bundle();
        args.putSerializable(USUARIO, usuario);
        fragment.setArguments(args);
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.content_main, fragment, USUARIO)
                .addToBackStack(USUARIO).commit();

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
