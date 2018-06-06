package com.example.fermach.keepmoving.Usuarios.CambioContraseña;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.fermach.keepmoving.MainActivity.ChangeToolbar;
import com.example.fermach.keepmoving.MainActivity.DrawerLocker;
import com.example.fermach.keepmoving.R;
import com.example.fermach.keepmoving.Usuarios.Loggin.LogginPantallaVista;

/**
 * Interfaz de cambio de contraseña
 */

public class CambioContraseñaVista extends Fragment implements CambioContraseñaContract.View  {


    private EditText et_email;
    private Button btn_cambioContraseña;
    private Button btn_cancelar;
    private View myView;
    private ProgressDialog progressDialog;
    private CambioContraseñaContract.Presenter presenter;
    private String correo;
    private Fragment fragment;

    public CambioContraseñaVista() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.pantalla_cambio_pass, container, false);

        inicializarVista();
        activarControladores();

        ((DrawerLocker)getActivity()).setDrawerLocked(true);
        ((ChangeToolbar)getActivity()).hideToolbar(true);

        presenter= new CambioContraseñaPresenter(this);
        progressDialog=new ProgressDialog(myView.getContext());


        return  myView;
    }

    public void inicializarVista() {
        btn_cambioContraseña= myView.findViewById(R.id.btn_cambioContraseña);
        btn_cancelar=myView.findViewById(R.id.btn_cancelar_canbioContraseña);
        et_email=myView.findViewById(R.id.editText_cambioContraseña);

    }

    public void activarControladores() {


        btn_cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isOnlineNet()) {

                    fragment = new LogginPantallaVista();
                    getFragmentManager().beginTransaction().replace(R.id.content_main, fragment).commit();
                }else{

                    Snackbar.make(myView,"No hay conexión a internet", Snackbar.LENGTH_SHORT).show();

                }
            }
        });


        btn_cambioContraseña.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isOnlineNet()) {

                    correo = et_email.getText().toString().trim();


                    if (!correo.isEmpty()) {

                        AlertDialog.Builder myBuild = new AlertDialog.Builder(getContext());
                        myBuild.setMessage("¿Está seguro de que desea enviar un email para cambiar su contraseña?");
                        myBuild.setTitle("Cambio de Contraseña");

                        myBuild.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        myBuild.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                presenter.cambiarContraseña(correo);

                            }
                        });
                        myBuild.show();



                    } else {
                        Snackbar.make(myView, "Debe rellenar su email", Snackbar.LENGTH_SHORT).show();

                    }
                }else{

                    Snackbar.make(myView, "No hay conexión a internet", Snackbar.LENGTH_SHORT).show();

                }


            }
        });
    }

    /**
     * Se comprueba el estado de la conexion con internet
     * @return
     */
    public Boolean isOnlineNet() {

        try {
            Process p = Runtime.getRuntime().exec("ping -c 1 www.google.es");

            int val           = p.waitFor();
            boolean reachable = (val == 0);
            return reachable;

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void onContraseñaCambiada() {
        Snackbar.make(myView,"Se ha enviado un email a su correo para completar el cambio", 4000).show();
        new Handler().postDelayed(new Runnable(){
            public void run(){
                fragment = new LogginPantallaVista();
                getFragmentManager().beginTransaction().replace(R.id.content_main, fragment ).commit();

            };
        }, 2000);
    }

    @Override
    public void onContraseñaCambiadaError() {
        Snackbar.make(myView,"El email introducido no es válido", 4000).show();

    }
}
