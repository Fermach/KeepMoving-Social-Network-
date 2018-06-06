package com.example.fermach.keepmoving.Usuarios.Registro.Registro_Ampliado;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
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

import com.example.fermach.keepmoving.App;
import com.example.fermach.keepmoving.MainActivity.ChangeToolbar;
import com.example.fermach.keepmoving.Modelos.Usuario.Usuario;
import com.example.fermach.keepmoving.R;
import com.example.fermach.keepmoving.Usuarios.Registro.Registro_Basico.RegistroPantallaVista;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Interfaz de la pantalla de registro ampliado
 */

public class RegistroAmpliadoPantallaVista extends Fragment implements RegistroAmpliadoPantallaContract.View  {

    private Button btn_atras;
    private Button btn_registrarse;
    private EditText et_nombre;
    private EditText et_apellidos;
    private EditText et_biografia;
    ProgressDialog progressDialog;
    private EditText et_aficiones;
    private CircleImageView foto_registro;
    private View myView;
    private RegistroAmpliadoPantallaContract.Presenter presenter;
    private String correo;
    private String nombre;
    private String apellidos;
    private String biografia;
    private String aficiones;
    private Uri foto;
    private String uid_actual;
    private Usuario usuario;
    private Fragment fragment;
    private  byte[] foto_bytes;


    public RegistroAmpliadoPantallaVista() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.pantalla_registro_ampliado, container, false);
        ((ChangeToolbar)getActivity()).setToolbarText("Registro usuario");

        progressDialog= new ProgressDialog(myView.getContext());
        presenter= new RegistroAmpliadoPantallaPresenter(this);
        //se obtiene el correo del usuario actual
        presenter.obtenerCorreoUsuarioActual();

        return  myView;
    }

    public void inicializarVista() {
        btn_registrarse= myView.findViewById(R.id.btn_registrar_registro);
        btn_atras=myView.findViewById(R.id.btn_atras_registro);
        et_apellidos=myView.findViewById(R.id.editText_apellidosUsuario_registro);
        et_nombre=myView.findViewById(R.id.editText_nombreUsuario_registro);
        et_biografia=myView.findViewById(R.id.editText_biografiaUsuario_registro1);
        et_aficiones=myView.findViewById(R.id.editText_aficiones_registro);
        foto_registro=myView.findViewById(R.id.fab_usuarioImagen_registro);
    }

    public void activarControladores() {

        btn_registrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isOnlineNet()) {

                    //correo=usuario.getCorreo();
                nombre=""+ et_nombre.getText().toString().trim();
                apellidos=""+et_apellidos.getText().toString().trim();
                aficiones=""+ et_aficiones.getText().toString().trim();
                biografia=""+ et_biografia.getText().toString().trim();

               // se comprueba los datos introducidos
                if(!nombre.isEmpty() && !apellidos.isEmpty() )  {


                        progressDialog.setMessage("Se están validando los datos");
                        progressDialog.setCancelable(false);
                        progressDialog.show();

                        //se controla si se ha introducido foto o no
                        if(foto !=null) {

                            usuario = new Usuario(nombre, apellidos,""+correo,biografia,aficiones);
                            presenter.registrarUsuarioConFoto(usuario,foto_bytes);

                        }else{
                            usuario = new Usuario(nombre, apellidos,""+correo,biografia,aficiones );
                            presenter.registrarUsuario(usuario);
                        }

                }else{
                        Snackbar.make(myView,"Debe introducir el nombre y los apellidos", Snackbar.LENGTH_SHORT).show();
                 }
                }else {
                    Snackbar.make(myView, "No hay conexión a internet", Snackbar.LENGTH_SHORT).show();
                }

            }
        });

        btn_atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isOnlineNet()) {

                    presenter.setTOKKEN("REGISTRO");
                }else {
                    Snackbar.make(myView, "No hay conexión a internet", Snackbar.LENGTH_SHORT).show();
                }
            }
        });

        //al clickar sobre el icono de foto de perfil nos abre la galeria para seleccionar una foto
        foto_registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isOnlineNet()) {
                    cargarImagenGaleria();

                }else {
                    Snackbar.make(myView, "No hay conexión a internet", Snackbar.LENGTH_SHORT).show();
                }
            }
        });
    }

    //se carga una imagen de perfil de la galeria
    public void cargarImagenGaleria(){
        Intent intent= new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/");
        startActivityForResult(intent.createChooser(intent,"Seleccione la Aplicación"),10);

    }


    /**
     *  cuando se ha seleccionado una foto se setea en el icono
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==-1){
            foto=data.getData();

            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(App.getAppContext().getContentResolver(),foto);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 25, baos);
                foto_bytes = baos.toByteArray();
            } catch (IOException e) {
                e.printStackTrace();
            }
            foto_registro.setImageBitmap(bitmap);


        }
    }



    @Override
    public void onRegistroError() {
        progressDialog.dismiss();
        Snackbar.make(myView,"En este momento, no se pudo completar el registro", Snackbar.LENGTH_LONG).show();

    }

    /**
     * Se setea e token para decirle a la app desde que pantalla se ha enviado el usuario
     */
    @Override
    public void onRegistro() {
        presenter.setTOKKEN_2("MENU");


    }

    @Override
    public void onDeslogueo() {
        progressDialog.dismiss();

    }

    @Override
    public void onTOKENselecionado() {
        presenter.cancelarRegistro();
    }

    @Override
    public void onTOKEN2selecionado() {
        Snackbar.make(myView,"Usuario registrado correctamente.\n Vuelva a introducir sus datos para iniciar sesión.", 5000).show();

        presenter.desloguearUsuario();
           new Handler().postDelayed(new Runnable(){
            public void run(){

            };
        }, 2000);

    }

    @Override
    public void onRegistroCancelado() {
       Log.i("VISTA", "REGISTRO CANCELADO");
    }

    @Override
    public void onRegistroCanceladoError() {
        Snackbar.make(myView,"Se ha producido un error al cancelar el registro, vuelva a intentarlo mas tarde", Snackbar.LENGTH_LONG).show();

    }

    @Override
    public void onDeslogueoError() {
        Snackbar.make(myView,"Se produjo un fallo en el registro ", 4000).show();

    }

    /**
     * Cuando se obtiene el correo se inicializan la vista y los controladores
     * @param correoUsuario
     */
    @Override
    public void onCorreoUsuarioActualObtenido(String correoUsuario) {
        this.correo=correoUsuario;
        Log.i("CORREO ACTUAL", ""+correo);
        inicializarVista();
        activarControladores();
    }

    @Override
    public void onCorreoUsuarioActualObtenidoError() {
        Snackbar.make(myView,"En este momento, no se pudo acceder a la base de datos", 4000).show();
        new Handler().postDelayed(new Runnable(){
            public void run(){
                fragment = new RegistroPantallaVista();
                getFragmentManager().beginTransaction().replace(R.id.content_main, fragment ).commit();

            };
        }, 2000);
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        presenter.desloguearUsuario();
    }

    /**
     * Se comprueba el estado de la conexion a internet
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
