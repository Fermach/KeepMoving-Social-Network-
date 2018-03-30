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

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    public UsuariosFirebase() {
        mAuth=FirebaseAuth.getInstance();

    }

    @Override
    public void loguearUsuario(Usuario usuario, final LoguearUsuarioCallback callback) {
        //IMPLEMENTAR AUTENTIFICACIÓN AQUI
        mAuth.signInWithEmailAndPassword(usuario.getCorreo(),usuario.getContraseña())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            callback.onUsuarioLogueado();
                        }else{
                            callback.onUsuarioLogueadoError();
                        }
                    }
                });
    }

    @Override
    public void comprobarUsuarioRegistrado(final ComprobarUsuarioRegistradoCallback callback) {
        mAuthStateListener= new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser userCurrent = mAuth.getCurrentUser();
                if(userCurrent==null){
                  //NO ESTA LOGUEADO
                    callback.onUsuarioRegistradoError();
                }else{
                  //ESTÁ LOGUEADO
                    callback.onUsuarioRegistrado();
                }
            }
        };
    }

    @Override
    public void cerrarSesion(CerrarSesionCallback callback) {
           mAuth.signOut();
    }

    @Override
    public void iniciarListener(IniciarListenerCallback callback) {
           mAuth.addAuthStateListener(mAuthStateListener);
           callback.onListenerIniciado();
    }

    @Override
    public void detenerListener(DetenerListenerCallback callback) {

        if(mAuthStateListener!=null){
           mAuth.removeAuthStateListener(mAuthStateListener);
           callback.onListenerDetenido();
        }

    }


}
