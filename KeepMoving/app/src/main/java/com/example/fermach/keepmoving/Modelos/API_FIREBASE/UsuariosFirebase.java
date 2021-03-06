package com.example.fermach.keepmoving.Modelos.API_FIREBASE;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.fermach.keepmoving.Modelos.Quedada.PeticionQuedada;
import com.example.fermach.keepmoving.Modelos.Usuario.Usuario;
import com.example.fermach.keepmoving.Modelos.Usuario.UsuariosDataSource;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import java.util.List;

/**
 * Created by Fermach on 29/03/2018.
 */

public class UsuariosFirebase implements UsuariosDataSource {

    private FirebaseUser user;
    private FirebaseAuth mAuth;
    private String UID_actual;
    private Usuario usuarioActual;
    private String TOKEN;
    private FirebaseDatabase database;
    private DatabaseReference UsuariosRef;
   // private QuedadasFirebase quedadasFirebase;
    private StorageReference myfileStoragePath;
    private StorageReference myStorageRef;
    private static UsuariosFirebase INSTANCIA_FIRE =null;
    private final long ONE_MEGABYTE = 1024 * 1024;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    /**
     * SINGLETONE
     * @return
     */
    public static UsuariosFirebase getInstance() {
        if (INSTANCIA_FIRE == null) {
            INSTANCIA_FIRE = new UsuariosFirebase();
        }
        return INSTANCIA_FIRE;
    }


    private UsuariosFirebase() {
        myStorageRef= FirebaseStorage.getInstance().getReference();
        //quedadasFirebase=QuedadasFirebase.getInstance();
        database = FirebaseDatabase.getInstance();
        UsuariosRef= database.getReference("Usuarios");
        mAuth= FirebaseAuth.getInstance();
        TOKEN= "LOGGIN";

        user=mAuth.getCurrentUser();
        //FirebaseUser currentUser = mAuth.getCurrentUser();
        //user=mAuth.getCurrentUser();
    }

    /**
     * Inicia sesión con un usuario
     * @param usuario
     * @param callback
     */
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

    /**
     * Cierra sesión con el usuario actual
     * @param callback
     */
    @Override
    public void desloguearUsuario(DesloguearUsuarioCallback callback) {
        Log.i("SIGNOUT","------------");
        mAuth.signOut();

        callback.onUsuarioDeslogueado();
    }

    /**
     * Crea un nuevo usuario en la autenticación de firebase
     * con email y contraseña
     * @param usuario
     * @param callback
     */
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

                            Log.i("REGISTRO_FIRE","ERROR " +task.getException().getMessage() );
                            callback.onUsuarioRegistradoError(""+task.getException().getMessage());
                        }
                    }
                });
    }

    /**
     * Registra un usuario en la BBDD con una foto
     * en el Storage de Firebase asociada al usuario
     * @param usuario
     * @param foto
     * @param callback
     */
    @Override
    public void registrarUsuarioAmpliadoConFoto(final Usuario usuario, final byte[] foto, final RegistrarUsuarioConFotoCallback callback) {


        myfileStoragePath=myStorageRef.child("FotosPerfil/").child(user.getUid());

        Log.i("USUARIO_PUSH",usuario.toString());
        UsuariosRef.child(user.getUid()).setValue(usuario).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    myfileStoragePath.putBytes(foto).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            if(task.isSuccessful()){
                                callback.onUsuarioRegistrado();
                                Log.i("REGISTRO_FIRE_DB","SUCCESFUL");
                            }else{
                                //borrar usuario de la BBDDs
                                /*
                                if(exito){

                                    Log.i("REGISTRO_FIRE_DB","ERROR");
                                   callback.onUsuarioRegistrado();
                                   }
                                 */
                                Log.i("REGISTRO_FIRE_DB","ERROR");
                                callback.onUsuarioRegistradoError();
                            }
                        }
                    });

                }else{
                    Log.i("REGISTRO_FIRE_DB","ERROR");
                    callback.onUsuarioRegistradoError();
                }
            }
        });
    }


    /**
     * Registra un usuario en la BBDD
     * @param usuario
     * @param callback
     */
    @Override
    public void registrarUsuarioAmpliado(Usuario usuario,  final RegistrarUsuarioAmpliadoCallback callback) {

          Log.i("USUARIO_PUSH",usuario.toString());
          UsuariosRef.child(user.getUid()).setValue(usuario).addOnCompleteListener(new OnCompleteListener<Void>() {
              @Override
              public void onComplete(@NonNull Task<Void> task) {
                  if (task.isSuccessful()){
                      callback.onUsuarioRegistrado();
                      Log.i("REGISTRO_FIRE_DB","SUCCESFUL");
                  }else{
                      Log.i("REGISTRO_FIRE_DB","ERROR");
                      callback.onUsuarioRegistradoError();
                  }
              }
          });
    }

    /**
     * Comprueba si un usuario está registrado
     * @param callback
     */
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

    /**
     * Cancela el registro del usuario eliminando es usuario creado
     * @param callback
     */
    @Override
    public void cancelarRegistroUsuario(final CancelarRegistroUsuarioCallback callback) {
            user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Log.i("REGISTRO CANCELADO", "EXITO");
                        callback.onRegistroCancelado();
                    } else {
                        Log.i("REGISTRO CANCELADO", "ERROR");
                        callback.onRegistroCanceladoError();
                    }
                }
            });


    }


    /**
     * Inicia el escuchador de control de usuario registrado
     * @param callback
     */
    @Override
    public void iniciarListener( final IniciarListenerCallback callback) {
        Log.i("LISTENER","USUARIOS FIRE 1" );

        mAuth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth fireAuth) {
                user= fireAuth.getCurrentUser();
                Log.i("LISTENER","USUARIOS FIRE 2" );
                if(user!=null){
                    Log.i("LISTENER","USUARIO REGISTRADO" );
                    callback.onUsuarioRegistrado(TOKEN);
                }else{
                    Log.i("LISTENER","USUARIO NO REGISTRADO" );
                    callback.onUsuarioNoRegistrado(TOKEN);
                }
            }
        });

    }


    /**
     * Obtiene la foto de perfil del usuario actual
     * @param callback
     */
    @Override
    public void obtenerFotoPerfil(final ObtenerFotoPerfilCallback callback) {
        myfileStoragePath=myStorageRef.child("FotosPerfil/").child(user.getUid());

        myfileStoragePath.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Log.i("OBTENER FOTO FIRE","SUCCESFUL" );
                callback.onFotoPerfilObtenida(bytes);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i("OBTENER FOTO FIRE","ERROR" );
                callback.onFotoPerfilObtenidaError();
            }
        });
    }

    /**
     * Obtiene la foto de perfil de un usuario
     * @param uid
     * @param pQuedada
     * @param callback
     */
    @Override
    public void obtenerFotoPerfilUsuario(String uid, final  PeticionQuedada pQuedada, final ObtenerFotoPerfilUsuarioCallback callback) {
        myfileStoragePath=myStorageRef.child("FotosPerfil/").child(uid);

        myfileStoragePath.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Log.i("OBTENER FOTO US FIRE","SUCCESFUL" );
                callback.onFotoUsuarioPerfilObtenida(bytes,  pQuedada);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i("OBTENER FOTO US FIRE","ERROR" );
                callback.onFotoUsuarioPerfilObtenidaError(pQuedada);
            }
        });
    }

    /**
     * Obtiene los datos del usuario actuak de la BBDD
     * @param callback
     */
    @Override
    public void obtenerUsuarioActual(final ObtenerUsuarioActualCallback callback) {
        UsuariosRef.child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String nombre= dataSnapshot.child("nombre").getValue(String.class);
                String apellidos= dataSnapshot.child("apellidos").getValue(String.class);
                String correo= dataSnapshot.child("correo").getValue(String.class);
                String biografia= dataSnapshot.child("biografia").getValue(String.class);
                String aficiones= dataSnapshot.child("aficiones").getValue(String.class);
                usuarioActual= new Usuario(nombre,apellidos,correo,biografia,aficiones);

                Log.i("OBTENER USUARIO FIRE","SUCCESFUL -- "+usuarioActual );
                callback.onUsuarioObtenido(usuarioActual);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onUsuarioObtenidoError();
                Log.i("OBTENER USUARIO FIRE","ERROR -- "+usuarioActual );
            }
        });

    }

    /**
     * Envia un email al correo de usuario actual para
     * cambiar la contraseña
     * @param email
     * @param callback
     */
    @Override
    public void cambiarContraseña(String email, final CambiarContraseñaCallback callback) {
        user=mAuth.getCurrentUser();
        if(user!=null) {

            mAuth.sendPasswordResetEmail(""+user.getEmail()).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Log.i("CAMBIAR CONTRASEÑA FIRE", "SUCCESFUL -- ");
                        callback.onContraseñaCambiada();
                    } else {
                        Log.i("CAMBIAR CONTRASEÑA FIRE", "ERROR -- ");
                        callback.onContraseñaCambiadaError();
                    }
                }
            });
        }else{

            mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Log.i("CAMBIAR CONTRASEÑA FIRE", "SUCCESFUL -- ");
                        callback.onContraseñaCambiada();
                    } else {
                        Log.i("CAMBIAR CONTRASEÑA FIRE", "ERROR -- ");
                        callback.onContraseñaCambiadaError();
                    }
                }
            });


        }
    }

    /**
     * Obtiene los datos de un usuario
     * @param Uid
     * @param callback
     */
    @Override
    public void obtenerUsuarioPorUID(String Uid, final ObtenerUsuarioPorUIDCallback callback) {
        UsuariosRef.child(Uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String nombre= dataSnapshot.child("nombre").getValue(String.class);
                String apellidos= dataSnapshot.child("apellidos").getValue(String.class);
                String correo= dataSnapshot.child("correo").getValue(String.class);
                String biografia= dataSnapshot.child("biografia").getValue(String.class);
                String aficiones= dataSnapshot.child("aficiones").getValue(String.class);
                usuarioActual= new Usuario(nombre,apellidos,correo,biografia,aficiones);

                Log.i("OBTENER USUARIO UID F","SUCCESFUL -- "+usuarioActual );
                callback.onUsuarioObtenido(usuarioActual);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onUsuarioObtenidoError();
                Log.i("OBTENER USUARIO UID F","ERROR -- "+usuarioActual );
            }
        });
    }

    /**
     * Obtiene el UID del usuario actual
     * @param callback
     */
    @Override
    public void obtenerUidUsuarioActual(ObtenerUidUsuarioActualCallback callback) {
        callback.onUsuarioObtenido(user.getUid());
    }

    /**
     * Obtiene el email del usuario actual
     * @param callback
     */
    @Override
    public void obtenerCorreoUsuarioActual(ObtenerCorreoUsuarioActualCallback callback) {
        if(user!=null){
            Log.i("EMAIL FIRE",""+user.getEmail());
            callback.onCorreoUsuarioObtenido(""+user.getEmail());
        }else{
            callback.onCorreoUsuarioObtenidoError();
        }
    }


    @Override
    public void setTOKEN(String TOKEN, SeleccionarTOKENCallback callback) {
        this.TOKEN = TOKEN;
        callback.onTOKENseleccionado();
    }

    /**
     * Edita los datos de un usuario
     * @param usuario
     * @param foto
     * @param callback
     */
    @Override
    public void editarUsuario(final Usuario usuario, final byte[] foto, final EditarUsuarioCallback callback) {
        Query query =  UsuariosRef.child(user.getUid());

       query.addListenerForSingleValueEvent(new ValueEventListener() {
           @Override
           public void onDataChange(DataSnapshot dataSnapshot) {
               dataSnapshot.getRef().child("aficiones").setValue(usuario.getAficiones());
               dataSnapshot.getRef().child("apellidos").setValue(usuario.getApellidos());
               dataSnapshot.getRef().child("nombre").setValue(usuario.getNombre());
               dataSnapshot.getRef().child("biografia").setValue(usuario.getBiografia());

               myfileStoragePath=myStorageRef.child("FotosPerfil/").child(user.getUid());
               myfileStoragePath.putBytes(foto).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                   @Override
                   public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                       if(task.isSuccessful()){
                           callback.onUsuarioEditado();
                           Log.i("EDITAR_FIRE_DB","SUCCESFUL");
                       }else{
                           //borrar usuario de la BBDDs
                                /*
                                if(exito){

                                    Log.i("REGISTRO_FIRE_DB","ERROR");
                                   callback.onUsuarioRegistrado();
                                   }
                                 */
                           Log.i("EDITAR_FIRE_DB","ERROR");
                           callback.onUsuarioEditadoError();
                       }
                   }
               });
           }

           @Override
           public void onCancelled(DatabaseError databaseError) {

           }
       });

    }


}
