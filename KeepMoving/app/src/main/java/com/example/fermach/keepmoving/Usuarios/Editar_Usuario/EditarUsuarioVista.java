package com.example.fermach.keepmoving.Usuarios.Editar_Usuario;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.example.fermach.keepmoving.Usuarios.Perfil_Usuario.PerfilPantallaVista;
import com.example.fermach.keepmoving.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by Fermach on 27/03/2018.
 */

public class EditarUsuarioVista extends Fragment implements EditarUsuarioContract.View  {

    private Button btn_atras;
    private Button btn_guardar;
    private EditText et_nombre;
    private EditText et_apellidos;
    private EditText et_biografia;
    private Bitmap fotoPerfil;
    ProgressDialog progressDialog;
    private MultiAutoCompleteTextView multi_aficiones;
    private CircleImageView foto_editar_usuario;
    private View myView;
    private EditarUsuarioContract.Presenter presenter;
    private String correo;
    private String nombre;
    private String apellidos;
    private String biografia;
    private String aficiones;
    private Uri foto;
    private String uid_actual;
    private Usuario usuario_ref;
    private Usuario usuario;
    private Fragment fragment;
    private  byte[] foto_bytes;


    public EditarUsuarioVista() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.pantalla_editar_usuario, container, false);

        progressDialog= new ProgressDialog(myView.getContext());
        ((ChangeToolbar)getActivity()).setToolbarText("Editar usuario");

        Bundle args = getArguments();

        //se comprueba si se estan recibiendo argumentos de otro fragmento
        //para saber si el menu de borrado nos esta pasando una llave para que borremos una
        //quedada de la lista
        if(args!=null) {


            //recoger foro de perfil
            foto_bytes=(byte[]) args.getSerializable("FOTO");
            //recoger usuario
            usuario_ref = (Usuario) args.getSerializable("USUARIO");
            Log.i("Argumentos", "RECOGIDOS =" + usuario_ref.toString());

        }else{
            Log.i("Argumentos", "NULOS" );

        }

        inicializarVista();
        activarControladores();

        presenter= new EditarUsuarioPresenter(this);


        return  myView;
    }

    public void inicializarVista() {
        btn_guardar= myView.findViewById(R.id.btn_guardar_editar_usuario);
        btn_atras=myView.findViewById(R.id.btn_atras_editar_usuario);
        et_apellidos=myView.findViewById(R.id.editText_apellidosUsuario_editar_usuario);
        et_nombre=myView.findViewById(R.id.editText_nombreUsuario_editar_usuario);
        et_biografia=myView.findViewById(R.id.editText_biografiaUsuario_editar_usuario);
        multi_aficiones=myView.findViewById(R.id.multiAutoComplete_aficiones_editar_usuario);
        foto_editar_usuario=myView.findViewById(R.id.fab_editar_usuarioImagen);


        String[] valores_aficiones= {"Futbol","Tenis","Baloncesto",
                "Running","Rugby","Boxeo","Artes Marciales","Senderismo","Otros"};

        multi_aficiones.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
        multi_aficiones.setAdapter(new ArrayAdapter<String>
                (getContext(),R.layout.support_simple_spinner_dropdown_item,valores_aficiones));

        //setear datos
        et_apellidos.setText(usuario_ref.getApellidos());
        et_biografia.setText(usuario_ref.getBiografia());
        et_nombre.setText(usuario_ref.getNombre());
        multi_aficiones.setText(usuario_ref.getAficiones());

        BitmapFactory.Options options = new BitmapFactory.Options();
        if(foto_bytes!=null) {
            fotoPerfil = BitmapFactory.decodeByteArray(foto_bytes, 0, foto_bytes.length, options);
            foto_editar_usuario.setImageBitmap(fotoPerfil);
        }

    }

    public void activarControladores() {

        btn_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

           if (isOnlineNet()) {

                nombre=""+ et_nombre.getText().toString().trim();
                apellidos=""+et_apellidos.getText().toString().trim();
                aficiones=""+ multi_aficiones.getText().toString().trim();
                biografia=""+ et_biografia.getText().toString().trim();


                if(!nombre.isEmpty() && !apellidos.isEmpty() )  {
                        progressDialog.setMessage("Se están validando los datos");
                        progressDialog.setCancelable(false);
                        progressDialog.show();

                        //crear usuario con los campos
                        usuario = new Usuario(nombre, apellidos,usuario_ref.getCorreo(),biografia,aficiones);

                        //actualizar usuario
                        presenter.editarUsuario(usuario,foto_bytes);


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

                    fragment = new PerfilPantallaVista();
                    getFragmentManager().beginTransaction().replace(R.id.content_main, fragment).commit();
                }else {
                    Snackbar.make(myView, "No hay conexión a internet", Snackbar.LENGTH_SHORT).show();
                }
            }
        });



        foto_editar_usuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarImagenGaleria();
            }
        });
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
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 25, bytes);
                foto_bytes = bytes.toByteArray();
            } catch (IOException e) {
                e.printStackTrace();
            }
            foto_editar_usuario.setImageBitmap(bitmap);


        }
    }


    @Override
    public void onUsuarioEditado() {
        progressDialog.dismiss();
        fragment = new PerfilPantallaVista();
        Snackbar.make(myView,"Usuario modificado correctamente!", 5000).show();
        new Handler().postDelayed(new Runnable(){
            public void run(){

                getFragmentManager().beginTransaction().replace(R.id.content_main, fragment).commit();

            };
        }, 3000);
    }

    @Override
    public void onUsuarioEditadoError() {
        progressDialog.dismiss();

        Snackbar.make(myView,"No fue posible modificar el usuario!", 5000).show();


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
