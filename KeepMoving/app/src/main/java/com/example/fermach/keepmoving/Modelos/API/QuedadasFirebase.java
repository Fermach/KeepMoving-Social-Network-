package com.example.fermach.keepmoving.Modelos.API;

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
    private List<Quedada> quedadaList;
    private FirebaseAuth mAuth;
    private FirebaseUser user;

    public static QuedadasFirebase getInstance() {

        if (INSTANCIA_FIRE == null) {
            INSTANCIA_FIRE = new QuedadasFirebase();
        }
        return INSTANCIA_FIRE;
    }

    private QuedadasFirebase() {
        quedadaList= new ArrayList<>();
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
    public void eliminarQuedada(final String uid, final EliminarQuedadaCallback callback) {
        UsuariosRef.child(user.getUid()).child("Quedadas").orderByChild("child/id")
                .equalTo(uid).removeEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Log.i("ELIMINAR_QUEDADA","1" );
                QuedadasRef.orderByChild("child/id")
                        .equalTo(uid).removeEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {
                        Log.i("ELIMINAR_QUEDADA","2" );
                        Log.i("ELIMINAR_QUEDADA","TRUE" );
                        callback.onQuedadaEliminada();
                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.i("ELIMINAR_QUEDADA","ERROR" );
                        callback.onQuedadaEliminadaError();
                    }
                });
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.i("ELIMINAR_QUEDADA","ERROR" );
                 callback.onQuedadaEliminadaError();
            }
        });

    }

    @Override
    public void editarQuedada(String uid, Quedada quedada, EditarQuedadaCallback callback) {

    }

    @Override
    public void obtenerQuedadas(final ObtenerQuedadasCallback callback) {
       QuedadasRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                quedada= dataSnapshot.getValue(Quedada.class);
                Log.i("Quedada_OBTENIDA",quedada.toString());
                quedadaList.add(quedada);

                callback.onQuedadasObtenidas(quedadaList);
                quedadaList= new ArrayList<>();
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
                quedadaList.add(quedada);

                callback.onQuedadasObtenidas(quedadaList);
                quedadaList= new ArrayList<>();
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
}
