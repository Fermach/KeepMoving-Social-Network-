package com.example.fermach.keepmoving.Modelos.API_FIREBASE;

import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.util.Log;

import com.example.fermach.keepmoving.Modelos.Quedada.PeticionQuedada;
import com.example.fermach.keepmoving.Modelos.Quedada.Quedada;
import com.example.fermach.keepmoving.Modelos.Quedada.QuedadaDataSource;
import com.example.fermach.keepmoving.Modelos.Usuario.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
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

import java.io.Console;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fermach on 03/05/2018.
 */

public class QuedadasFirebase implements QuedadaDataSource {

    private static QuedadasFirebase INSTANCIA_FIRE = null;
    private FirebaseDatabase database;
    private DatabaseReference UsuariosRef;
    private DatabaseReference QuedadasRef;
    private Usuario usuarioActual;
    private Quedada quedada;
    private PeticionQuedada peticionQuedada;
    private static List<PeticionQuedada> listaPeticionesQuedadasUsuario;
    private static List<Quedada> listaQuedadasUsuario;
    private static List<Quedada> listaQuedadasGeneral;
    private FirebaseAuth mAuth;
    private FirebaseUser user;

    public static QuedadasFirebase getInstance() {

        if (INSTANCIA_FIRE == null) {
            INSTANCIA_FIRE = new QuedadasFirebase();
        }

        listaQuedadasUsuario = new ArrayList<>();
        listaQuedadasGeneral = new ArrayList<>();
        listaPeticionesQuedadasUsuario= new ArrayList<>();

        return INSTANCIA_FIRE;
    }

    private QuedadasFirebase() {

        database = FirebaseDatabase.getInstance();
        UsuariosRef = database.getReference("Usuarios");
        QuedadasRef = database.getReference("Quedadas");
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

    }

    @Override
    public void crearQuedada(final Quedada quedada, final CrearQuedadaCallback callback) {


        //optenemos el usuario actual para cargar el nombre al crear la quedada
        UsuariosRef.child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String nombre = dataSnapshot.child("nombre").getValue(String.class);
                String apellidos = dataSnapshot.child("apellidos").getValue(String.class);
                String correo = dataSnapshot.child("correo").getValue(String.class);
                String biografia = dataSnapshot.child("biografia").getValue(String.class);
                String aficiones = dataSnapshot.child("aficiones").getValue(String.class);
                usuarioActual = new Usuario(nombre, apellidos, correo, biografia, aficiones);

                Log.i("OBTENER USUARIO FIRE", "SUCCESFUL -- " + usuarioActual);
                quedada.setAutor("" + usuarioActual.getNombre() + ", " + usuarioActual.getApellidos());
                quedada.setAutor_uid(""+ user.getUid());

                //se sube la quedada la quedada
                UsuariosRef.child(user.getUid()).child("Quedadas").push().setValue(quedada).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {

                            Log.i("CREAR_QUEDADA_FIRE1", "EXITO");
                            QuedadasRef.push().setValue(quedada).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {

                                        Log.i("CREAR_QUEDADA_FIRE2", "EXITO");
                                        callback.onQuedadaCreada();
                                    } else {
                                        Log.i("CREAR_QUEDADA_FIRE", "ERROR");
                                        callback.onQuedadaCreadaError();
                                    }
                                }
                            });

                        } else {
                            Log.i("CREAR_QUEDADA_FIRE", "ERROR");
                            callback.onQuedadaCreadaError();
                        }
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onQuedadaCreadaError();
                Log.i("OBTENER USUARIO FIRE", "ERROR -- " + usuarioActual);
            }
        });

    }


    @Override
    public void obtenerQuedadas(final ObtenerQuedadasCallback callback) {

        QuedadasRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                quedada = dataSnapshot.getValue(Quedada.class);
                Log.i("Quedada_OBTENIDA", quedada.toString());
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
    public void obtenerSolicitudesQuedadas(final ObtenerSolicitudesQuedadasCallback callback) {
        UsuariosRef.child(user.getUid()).child("Peticiones enviadas").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                peticionQuedada = dataSnapshot.getValue(PeticionQuedada.class);
                    Log.i("PETICIONES_OBTENIDA", peticionQuedada.toString());
                listaPeticionesQuedadasUsuario.add(peticionQuedada);

                callback.onSolicitudesQuedadasObtenidas(listaPeticionesQuedadasUsuario);

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

                callback.onSolicitudesQuedadasObtenidasError();
            }
        }) ;
    }

    @Override
    public void obtenerQuedadasUsuario(final ObtenerQuedadasCallback callback) {
        UsuariosRef.child(user.getUid()).child("Quedadas").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                quedada = dataSnapshot.getValue(Quedada.class);
                Log.i("Quedada_OBTENIDA", quedada.toString());
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
    public void editarQuedada(final Quedada quedada, final EditarQuedadaCallback callback) {
        Query query =  UsuariosRef.child(user.getUid()).child("Quedadas").orderByChild("id")
                .equalTo(quedada.getId());
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                dataSnapshot.getRef().child("deporte").setValue(quedada.getDeporte());
                dataSnapshot.getRef().child("fecha").setValue(quedada.getFecha());
                dataSnapshot.getRef().child("hora").setValue(quedada.getHora());
                dataSnapshot.getRef().child("info").setValue(quedada.getInfo());
                dataSnapshot.getRef().child("latitud").setValue(quedada.getLatitud());
                dataSnapshot.getRef().child("longitud").setValue(quedada.getLongitud());
                dataSnapshot.getRef().child("plazas").setValue(quedada.getPlazas());
                dataSnapshot.getRef().child("lugar").setValue(quedada.getLugar());

                Query query2 = QuedadasRef.orderByChild("id")
                        .equalTo(quedada.getId());
                query2.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        dataSnapshot.getRef().child("deporte").setValue(quedada.getDeporte());
                        dataSnapshot.getRef().child("fecha").setValue(quedada.getFecha());
                        dataSnapshot.getRef().child("hora").setValue(quedada.getHora());
                        dataSnapshot.getRef().child("info").setValue(quedada.getInfo());
                        dataSnapshot.getRef().child("latitud").setValue(quedada.getLatitud());
                        dataSnapshot.getRef().child("longitud").setValue(quedada.getLongitud());
                        dataSnapshot.getRef().child("plazas").setValue(quedada.getPlazas());
                        dataSnapshot.getRef().child("lugar").setValue(quedada.getLugar());

                        callback.onQuedadaEditada();
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
                      callback.onQuedadaEditadaError();
                    }
                });
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

                callback.onQuedadaEditadaError();
            }
        });
    }



    @Override
    public void eliminarQuedada(final String uid, final EliminarQuedadaCallback callback) {

        Query query =  UsuariosRef.child(user.getUid()).child("Quedadas").orderByChild("id")
                .equalTo(uid);

        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(final DataSnapshot dataSnapshot, String s) {
                String key= dataSnapshot.getKey().toString();
                Log.i("KEY_ELIMINAR_QUEDADA", key);

                dataSnapshot.getRef().removeValue(new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {

                      if(databaseError==null) {
                          Query query2 = QuedadasRef.orderByChild("id")
                                  .equalTo(uid);
                          query2.addChildEventListener(new ChildEventListener() {
                              @Override
                              public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                  String key2 = dataSnapshot.getKey().toString();
                                  Log.i("KEY2_ELIMINAR_QUEDADA", key2);
                                  dataSnapshot.getRef().removeValue(new DatabaseReference.CompletionListener() {
                                      @Override
                                      public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                          if (databaseError == null) {
                                              callback.onQuedadaEliminada();
                                          } else {
                                              callback.onQuedadaEliminadaError();
                                          }
                                      }
                                  });
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

                              }
                          });
                      }else{

                          callback.onQuedadaEliminadaError();
                      }
                    }
                });
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

            }
        });


    }

    @Override
    public void enviarSolicitud(final PeticionQuedada peticionQuedada, final EnviarSolicitudCallback callback) {
        peticionQuedada.setAutor_peticion(user.getUid());

        UsuariosRef.child(user.getUid()).child("Peticiones enviadas").push().setValue(peticionQuedada).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful()) {
                    UsuariosRef.child(peticionQuedada.getAutor_uid()).child("Peticiones recibidas").push().setValue(peticionQuedada).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()) {
                                Log.i("ENVIAR_SOLICITUD_FIRE", "SUCCESFUL");
                                callback.onSolicitudEnviada();
                            }else{
                                Log.i("ENVIAR_SOLICITUD_FIRE", "ERROR_2");
                                callback.onSolicitudEnviadaError();
                            }
                        }
                    });

                } else {
                    Log.i("ENVIAR_SOLICITUD_FIRE", "ERROR_1");
                    callback.onSolicitudEnviadaError();
                }
            }
        });
    }
}
