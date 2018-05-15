package com.example.fermach.keepmoving.Modelos.API_FIREBASE;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.fermach.keepmoving.Modelos.Quedada.Quedada;
import com.example.fermach.keepmoving.Modelos.Quedada.QuedadaDataSource;
import com.example.fermach.keepmoving.Modelos.Usuario.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fermach on 03/05/2018.
 */

public class QuedadasFirebase implements QuedadaDataSource {

    private static QuedadasFirebase INSTANCIA_FIRE =null;
    private FirebaseDatabase database;
    private DatabaseReference UsuariosRef;
    private DatabaseReference QuedadasRef;
    private Usuario usuarioActual;
    private Quedada quedada;
    private static List<Quedada> listaQuedadasUsuario;
    private static List<Quedada> listaQuedadasGeneral;
    private FirebaseAuth mAuth;
    private FirebaseUser user;

    public static QuedadasFirebase getInstance() {

        if (INSTANCIA_FIRE == null) {
            INSTANCIA_FIRE = new QuedadasFirebase();
        }

        listaQuedadasUsuario= new ArrayList<>();
        listaQuedadasGeneral= new ArrayList<>();
        return INSTANCIA_FIRE;
    }

    private QuedadasFirebase() {

        database = FirebaseDatabase.getInstance();
        UsuariosRef= database.getReference("Usuarios");
        QuedadasRef= database.getReference("Quedadas");
        mAuth= FirebaseAuth.getInstance();
        user=mAuth.getCurrentUser();

    }

    @Override
    public void crearQuedada(final Quedada quedada, final CrearQuedadaCallback callback) {


        //optenemos el usuario actual para cargar el nombre al crear la quedada
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
                quedada.setAutor(""+usuarioActual.getNombre()+", "+usuarioActual.getApellidos());

                //se sube la quedada la quedada
                UsuariosRef.child(user.getUid()).child("Quedadas").push().setValue(quedada).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Log.i("CREAR_QUEDADA_FIRE1","EXITO");
                            QuedadasRef.push().setValue(quedada).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()) {

                                        Log.i("CREAR_QUEDADA_FIRE2","EXITO");
                                        callback.onQuedadaCreada();
                                    }else{
                                        Log.i("CREAR_QUEDADA_FIRE","ERROR");
                                        callback.onQuedadaCreadaError();
                                    }
                                }
                            });

                        }else{
                            Log.i("CREAR_QUEDADA_FIRE","ERROR");
                            callback.onQuedadaCreadaError();
                        }
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onQuedadaCreadaError();
                Log.i("OBTENER USUARIO FIRE","ERROR -- "+usuarioActual );
            }
        });

    }


    @Override
    public void obtenerQuedadas(final ObtenerQuedadasCallback callback) {

       QuedadasRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                quedada= dataSnapshot.getValue(Quedada.class);
                Log.i("Quedada_OBTENIDA",quedada.toString());
                listaQuedadasGeneral.add(quedada);

                callback.onQuedadasObtenidas(listaQuedadasGeneral);
                //listaQuedadasGeneral= new ArrayList<>();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onQuedadasObtenidasError();
            }
        });
    }

    @Override
    public void obtenerQuedadasUsuario(final ObtenerQuedadasCallback callback) {
        UsuariosRef.child(user.getUid()).child("Quedadas").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                quedada= dataSnapshot.getValue(Quedada.class);
                Log.i("Quedada_OBTENIDA",quedada.toString());
                listaQuedadasUsuario.add(quedada);

                callback.onQuedadasObtenidas(listaQuedadasUsuario);
               // listaQuedadasUsuario= new ArrayList<>();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onQuedadasObtenidasError();
            }
        });
    }

    

    @Override
    public void editarQuedada( Quedada quedada,  EditarQuedadaCallback callback) {

    }


    //NO FUNCIONA
    @Override
    public void eliminarQuedada(final String uid, final EliminarQuedadaCallback callback) {

        UsuariosRef.child(user.getUid()).child("Quedadas").orderByChild("id")
                .equalTo(uid).getRef().removeValue(new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if(databaseError==null){

                    Log.i("Quedada_1_Eliminada", "TRUE");
                    QuedadasRef.orderByChild("child/id")
                            .equalTo(uid).getRef().removeValue(new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                            if(databaseError==null) {
                                Log.i("Quedada_2_Eliminada", "TRUE");
                                callback.onQuedadaEliminada();
                            }
                            else{
                                Log.i("Quedada_2_Eliminada", "FALSE");
                                callback.onQuedadaEliminadaError();
                            }
                        }
                    });

                }else{
                    Log.i("Quedada_1_Eliminada", "FALSE");
                    callback.onQuedadaEliminadaError();
                }
            }


        });


    }
}
