package com.example.fermach.keepmoving.Modelos.API;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.fermach.keepmoving.Modelos.Usuario.Usuario;
import com.example.fermach.keepmoving.Modelos.Usuario.UsuariosDataSource;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by Fermach on 29/03/2018.
 */

public class UsuariosFirebase implements UsuariosDataSource {

    private FirebaseUser user;
    private FirebaseAuth mAuth;
   // private FirebaseAuth.AuthStateListener mAuthStateListener;

    public UsuariosFirebase() {
        mAuth=FirebaseAuth.getInstance();
        //FirebaseUser currentUser = mAuth.getCurrentUser();
        //user=mAuth.getCurrentUser();
    }

    @Override
    public void loguearUsuario(Usuario usuario, final LoguearUsuarioCallback callback) {
        //IMPLEMENTAR AUTENTIFICACIÓN AQUI

        String email= usuario.getCorreo();
        String password=usuario.getContraseña();

        Log.i("LOGGIN","EMAIL: "+email +", CONTRASEÑA: "+password);


        mAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Log.i("LOGGIN","SUCCESFUL");
                            user = mAuth.getCurrentUser();
                            callback.onUsuarioLogueado();
                        }else{
                            Log.i("LOGGIN","ERROR");

                            callback.onUsuarioLogueadoError();
                        }
                    }
                });
    }

    @Override
    public void registrarUsuario(Usuario usuario, final RegistrarUsuarioCallback callback) {
        //____________________
        String email= usuario.getCorreo();
        String password=usuario.getContraseña();

        Log.i("REGISTRO","EMAIL: "+email +", CONTRASEÑA: "+password);
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            Log.i("REGISTRO_FIRE","SUCCESFUL");
                            callback.onUsuarioRegistrado();
                        }else{

                            Log.i("REGISTRO_FIRE","ERROR");
                            callback.onUsuarioRegistradoError();
                        }
                    }
                });
    }

    @Override
    public void comprobarUsuarioRegistrado(final ComprobarUsuarioRegistradoCallback callback) {
       FirebaseUser userCurrent = mAuth.getCurrentUser();

           if(userCurrent==null){
                  //NO ESTA LOGUEADO
                    Log.i("LOGGIN","NO LOGUEADO");
                    callback.onUsuarioRegistradoError();

                }else{
                  //ESTÁ LOGUEADO
                    Log.i("LOGGIN","LOGUEADO");
                    callback.onUsuarioRegistrado();
                }

    }



    @Override
    public void cerrarSesion(CerrarSesionCallback callback) {
           mAuth.signOut();
    }

    @Override
    public void iniciarListener(IniciarListenerCallback callback) {
           //mAuth.addAuthStateListener(mAuthStateListener);
        user = mAuth.getCurrentUser();
        callback.onListenerIniciado();
    }

    @Override
    public void detenerListener(DetenerListenerCallback callback) {

       /* if(mAuthStateListener!=null){
           mAuth.removeAuthStateListener(mAuthStateListener);
           callback.onListenerDetenido();
        }*/

    }


}
