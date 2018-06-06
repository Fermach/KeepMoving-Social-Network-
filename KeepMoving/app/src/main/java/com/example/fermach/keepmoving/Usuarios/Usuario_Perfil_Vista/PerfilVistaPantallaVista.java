package com.example.fermach.keepmoving.Usuarios.Usuario_Perfil_Vista;

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
import android.widget.TextView;

import com.example.fermach.keepmoving.Quedadas.Listado_Quedadas.Listado_Completo_Quedadas.ListadoQuedadasGeneralVista;
import com.example.fermach.keepmoving.MainActivity.ChangeToolbar;
import com.example.fermach.keepmoving.Modelos.Usuario.Usuario;
import com.example.fermach.keepmoving.R;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Interfaz de la pantala de visualización del perfil del usuario
 */

public class PerfilVistaPantallaVista extends Fragment implements PerfilVistaPantallaContract.View  {


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
    private PerfilVistaPantallaContract.Presenter presenter;
    private final String USUARIO="USUARIO";
    private final String FOTO="FOTO";
    private String uid;

    public PerfilVistaPantallaVista() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.pantalla_perfil_usuario_vista, container, false);

        ((ChangeToolbar)getActivity()).setToolbarText("Perfil usuario");

        Bundle args = getArguments();
        uid =(String) args
                .getSerializable("UID_USUARIO");



        inicializarVista();
        activarControladores();

        presenter= new PerfilVistaPantallaPresenter(this);

        progressDialog= new ProgressDialog(myView.getContext());
        progressDialog.setMessage("Obteniendo datos");
        progressDialog.setCancelable(false);
        progressDialog.show();

        presenter.ObtenerUsuarioPorUid(uid);
        presenter.ObtenerFotoUsuario(uid);

        return myView;
    }

    public void inicializarVista() {
        tv_nombre=myView.findViewById(R.id.nombre_usuario_perfil_vista);
        tv_correo=myView.findViewById(R.id.correo_usuario_perfil_vista);
        circleImageView_foto=myView.findViewById(R.id.fab_usuarioImagen_perfil_vista);
        tv_bio=myView.findViewById(R.id.biografia_usuario_perfil_vista);
        tv_aficiones=myView.findViewById(R.id.aficiones_usuario_perfil_vista);
    }

    public void activarControladores() {


    }



    @Override
    public void onUsuarioObtenido(Usuario usuario) {
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
    public void onUsuarioObtenidoError() {
          Snackbar.make(myView,"No se pudieron obtener los datos de perfil", 4000).show();

          //lanzamos un hilo
          new Handler().postDelayed(new Runnable(){
            public void run(){

                progressDialog.dismiss();
                fragment = new ListadoQuedadasGeneralVista();
                getFragmentManager().beginTransaction().replace(R.id.content_main, fragment ).commit();

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
        Snackbar.make(myView,"No se pudo obtener la foto de perfil", 4000).show();


    }


    @Override
    public void onStart() {
        super.onStart();

    }

}
