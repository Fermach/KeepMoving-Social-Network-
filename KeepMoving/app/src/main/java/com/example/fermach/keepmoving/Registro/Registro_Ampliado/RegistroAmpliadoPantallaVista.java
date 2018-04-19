package com.example.fermach.keepmoving.Registro.Registro_Ampliado;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
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
import com.example.fermach.keepmoving.Loggin.LogginPantallaVista;
import com.example.fermach.keepmoving.Modelos.Usuario.Usuario;
import com.example.fermach.keepmoving.R;
import com.example.fermach.keepmoving.Registro.Registro_Basico.RegistroPantallaVista;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by Fermach on 27/03/2018.
 */

public class RegistroAmpliadoPantallaVista extends Fragment implements RegistroAmpliadoPantallaContract.View  {

    private Button btn_atras;
    private Button btn_registrarse;
    private EditText et_nombre;
    private EditText et_apellidos;
    private EditText et_biografia;
    ProgressDialog progressDialog;
    private MultiAutoCompleteTextView multi_aficiones;
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
    private Usuario usuario=null;
    private Fragment fragment;
    private  byte[] foto_bytes;


    public RegistroAmpliadoPantallaVista() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.pantalla_registro_ampliado, container, false);


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
        progressDialog= new ProgressDialog(myView.getContext());
        presenter= new RegistroAmpliadoPantallaPresenter(this);

        return  myView;
    }

    public void inicializarVista() {
        btn_registrarse= myView.findViewById(R.id.btn_registrar_registro);
        btn_atras=myView.findViewById(R.id.btn_atras_registro);
        et_apellidos=myView.findViewById(R.id.editText_apellidosUsuario_registro);
        et_nombre=myView.findViewById(R.id.editText_nombreUsuario_registro);
        et_biografia=myView.findViewById(R.id.editText_biografiaUsuario_registro1);
        multi_aficiones=myView.findViewById(R.id.multiAutoComplete_aficiones_registro);
        foto_registro=myView.findViewById(R.id.fab_usuarioImagen_registro);
    }

    public void activarControladores() {

        btn_registrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                correo=usuario.getCorreo();
                nombre= et_nombre.getText().toString().trim();
                apellidos=et_apellidos.getText().toString().trim();
                aficiones= multi_aficiones.getText().toString().trim();
                biografia= et_biografia.getText().toString().trim();


                if(!nombre.isEmpty() && !apellidos.isEmpty() )  {


                        progressDialog.setMessage("Se están validando los datos");
                        progressDialog.setCancelable(false);
                        progressDialog.show();

                        if(foto !=null) {
                            usuario = new Usuario(nombre, apellidos,correo,biografia,aficiones);
                            presenter.registrarUsuarioConFoto(usuario,foto_bytes);

                        }else{
                            usuario = new Usuario(nombre, apellidos,correo,biografia,aficiones );
                            presenter.registrarUsuario(usuario);
                        }

                }else{
                        Snackbar.make(myView,"Debe introducir el nombre y los apellidos", Snackbar.LENGTH_SHORT).show();
                 }

            }
        });

        btn_atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new RegistroPantallaVista();
                getFragmentManager().beginTransaction().replace(R.id.content_main, fragment ).commit();

            }
        });

        foto_registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarImagenGaleria();
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

    public void cargarImagenGaleria(){
        Intent intent= new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/");
        startActivityForResult(intent.createChooser(intent,"Seleccione la Aplicación"),10);

    }

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

    @Override
    public void onRegistro() {
        presenter.desloguearUsuario();

    }

    @Override
    public void onDeslogueo() {
        progressDialog.dismiss();
        fragment = new LogginPantallaVista();
        getFragmentManager().beginTransaction().replace(R.id.content_main, fragment ).commit();

    }

    @Override
    public void onDeslogueoError() {

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
