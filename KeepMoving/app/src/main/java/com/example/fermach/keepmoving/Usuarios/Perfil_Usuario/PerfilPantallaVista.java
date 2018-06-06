package com.example.fermach.keepmoving.Usuarios.Perfil_Usuario;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.fermach.keepmoving.Usuarios.Editar_Usuario.EditarUsuarioVista;
import com.example.fermach.keepmoving.Quedadas.Listado_Quedadas.Listado_Quedadas_Por_Usuario.ListadoQuedadasUsuarioVista;
import com.example.fermach.keepmoving.MainActivity.ChangeToolbar;
import com.example.fermach.keepmoving.Modelos.Usuario.Usuario;
import com.example.fermach.keepmoving.R;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Interfaz de la pantalla de perfil del usuario
 */

public class PerfilPantallaVista extends Fragment implements PerfilPantallaContract.View  {

    private Button btn_mis_quedadas;
    private Button btn_editar_datos;
    private TextView tv_nombre;
    private TextView tv_aficiones;
    private TextView tv_bio;
    private TextView tv_correo;
    private CircleImageView circleImageView_foto;
    private Fragment fragment;
    private ProgressDialog progressDialog;
    private View myView;
    private Usuario usuarioPerfil =null;
    private byte[] fotoObtenida = null;
    private Bitmap fotoPerfil;
    private PerfilPantallaContract.Presenter presenter;
    private final String USUARIO="USUARIO";
    private final String FOTO="FOTO";

    public PerfilPantallaVista() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.pantalla_perfil_usuario, container, false);
        ((ChangeToolbar)getActivity()).setToolbarText("Mi perfil");

        inicializarVista();
        activarControladores();

        presenter= new PerfilPantallaPresenter(this);

        progressDialog= new ProgressDialog(myView.getContext());
        progressDialog.setMessage("Obteniendo datos");
        progressDialog.setCancelable(false);
        progressDialog.show();

        //se obtienen los daos del usuario actual
        presenter.ObtenerUsuarioActual();
        presenter.ObtenerFotoUsuarioActual();

        return myView;
    }

    public void inicializarVista() {
        btn_mis_quedadas= myView.findViewById(R.id.btn_mis_quedadas_perfil);
        btn_editar_datos=myView.findViewById(R.id.btn_modificar_perfil);
        tv_nombre=myView.findViewById(R.id.nombre_usuario_perfil);
        tv_correo=myView.findViewById(R.id.correo_usuario_perfil);
        circleImageView_foto=myView.findViewById(R.id.fab_usuarioImagen_perfil);
        tv_bio=myView.findViewById(R.id.biografia_usuario_perfil);
        tv_aficiones=myView.findViewById(R.id.aficiones_usuario_perfil);
    }

    public void activarControladores() {

        btn_mis_quedadas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isOnlineNet()) {

                    fragment = new ListadoQuedadasUsuarioVista();
                    getFragmentManager().beginTransaction().replace(R.id.content_main, fragment ).commit();
                }else {
                    Snackbar.make(myView, "No hay conexión a internet", Snackbar.LENGTH_SHORT).show();
                }
            }
        });

        btn_editar_datos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isOnlineNet()) {

               //mostramos la pantalla de edicion panadole el usuario y la foto
                Bundle args = new Bundle();
                args.putSerializable(USUARIO, usuarioPerfil);
                args.putSerializable(FOTO, fotoObtenida);
                Fragment toFragment = new EditarUsuarioVista();
                toFragment.setArguments(args);
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content_main, toFragment)
                        .addToBackStack(USUARIO).addToBackStack(FOTO).commit();

                }else {
                    Snackbar.make(myView, "No hay conexión a internet", Snackbar.LENGTH_SHORT).show();
                }
            }
        });
    }



    @Override
    public void onUsuarioActualObtenido(Usuario usuario) {
          usuarioPerfil=usuario;
          /*while(usuarioPerfil ==null || fotoObtenida==null){

           //esperamos a que se reciba la foto y el usuario
          }*/
          progressDialog.dismiss();

          tv_nombre.setText(usuario.getNombre()+", "+usuario.getApellidos());
          tv_aficiones.setText(usuario.getAficiones());
          tv_bio.setText(usuario.getBiografia());
          tv_correo.setText(usuario.getCorreo());

    }


    @Override
    public void onUsuarioActualObtenidoError() {
          Snackbar.make(myView,"No se pudieron obtener los datos de perfil", 4000).show();

          //lanzamos un hilo
          new Handler().postDelayed(new Runnable(){
            public void run(){
                progressDialog.dismiss();
                presenter.CerrarSesion();
            };
          },2000);
    }

    @Override
    public void onFotoPerfilObtenida(byte[] foto) {
          fotoObtenida=foto;
          /*while(usuarioPerfil ==null || fotoObtenida==null){

            //esperamos a que se reciba la foto y el usuario
          }*/
          progressDialog.dismiss();

          BitmapFactory.Options options = new BitmapFactory.Options();
          fotoPerfil = BitmapFactory.decodeByteArray(foto, 0, foto.length, options);
          circleImageView_foto.setImageBitmap(fotoPerfil);
    }

    @Override
    public void onFotoPerfilObtenidaError() {
        /* Snackbar.make(myView,"No se pudieron obtener los datos de perfil", 4000).show();

         //lanzamos un hilo
         new Handler().postDelayed(new Runnable(){
            public void run(){
                progressDialog.dismiss();
                presenter.CerrarSesion();
            };
        }, 2000);
        */
    }

    @Override
    public void onSesionCerrada() {

    }

    @Override
    public void onStart() {
        super.onStart();

    }

    /**
     * Se comprueba el estado de la conexión a internet
     * @return
     */
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
