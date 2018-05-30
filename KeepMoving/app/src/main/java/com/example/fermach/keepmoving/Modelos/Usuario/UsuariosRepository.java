package com.example.fermach.keepmoving.Modelos.Usuario;

import android.util.Log;

import com.example.fermach.keepmoving.Modelos.API_FIREBASE.UsuariosFirebase;
import com.example.fermach.keepmoving.Modelos.Quedada.PeticionQuedada;

/**
 * Created by Fermach on 29/03/2018.
 */

public class UsuariosRepository implements UsuariosDataSource{

    private static UsuariosRepository INSTANCIA = null;
    //private List<Usuario> listaEjerciciosPorRutina = null;

    //Singletone del repositorio, para que solo pueda existir una instancia

    public static UsuariosRepository getInstance() {
        if (INSTANCIA == null) {
            INSTANCIA = new UsuariosRepository();
        }
        return INSTANCIA;
    }

    @Override
    public void loguearUsuario(Usuario usuario, final LoguearUsuarioCallback callback) {
        UsuariosFirebase usuariosFirebase= UsuariosFirebase.getInstance();
        usuariosFirebase.loguearUsuario(usuario, new LoguearUsuarioCallback() {
            @Override
            public void onUsuarioLogueado() {

                callback.onUsuarioLogueado();
            }

            @Override
            public void onUsuarioLogueadoError() {
                callback.onUsuarioLogueadoError();

            }
        });
    }

    @Override
    public void editarUsuario(Usuario usuario,byte[] foto, final EditarUsuarioCallback callback) {
        UsuariosFirebase usuariosFirebase= UsuariosFirebase.getInstance();
        usuariosFirebase.editarUsuario(usuario,foto, new EditarUsuarioCallback() {
            @Override
            public void onUsuarioEditado() {
                callback.onUsuarioEditado();
            }

            @Override
            public void onUsuarioEditadoError() {
               callback.onUsuarioEditadoError();
            }
        });
    }

    @Override
    public void desloguearUsuario(final DesloguearUsuarioCallback callback) {
        UsuariosFirebase usuariosFirebase= UsuariosFirebase.getInstance();
        usuariosFirebase.desloguearUsuario(new DesloguearUsuarioCallback() {
            @Override
            public void onUsuarioDeslogueado() {
                callback.onUsuarioDeslogueado();
            }

            @Override
            public void onUsuarioDeslogueadoError() {

            }
        });

    }

    @Override
    public void registrarUsuario(Usuario usuario, final RegistrarUsuarioCallback callback) {
        UsuariosFirebase usuariosFirebase=UsuariosFirebase.getInstance();
        usuariosFirebase.registrarUsuario(usuario, new RegistrarUsuarioCallback() {
            @Override
            public void onUsuarioRegistrado() {
                Log.i("REGISTRO", "SUCCESFUL");
                callback.onUsuarioRegistrado();
            }

            @Override
            public void onUsuarioRegistradoError(String error) {
                Log.i("REGISTRO", "ERROR");
                callback.onUsuarioRegistradoError(error);
            }
        });
    }

    @Override
    public void registrarUsuarioAmpliadoConFoto(Usuario usuario,byte[] foto, final RegistrarUsuarioConFotoCallback callback) {
        UsuariosFirebase usuariosFirebase=UsuariosFirebase.getInstance();
        usuariosFirebase.registrarUsuarioAmpliadoConFoto(usuario,foto, new RegistrarUsuarioConFotoCallback() {
            @Override
            public void onUsuarioRegistrado() {
                Log.i("REGISTRO AMPLIADO", "SUCCESFUL");
                callback.onUsuarioRegistrado();
            }

            @Override
            public void onUsuarioRegistradoError() {
                Log.i("REGISTRO AMPLIADO", "ERROR");
                callback.onUsuarioRegistradoError();
            }
        });
    }

    @Override
    public void registrarUsuarioAmpliado(Usuario usuario, final RegistrarUsuarioAmpliadoCallback callback) {
        UsuariosFirebase usuariosFirebase=UsuariosFirebase.getInstance();
        usuariosFirebase.registrarUsuarioAmpliado(usuario, new RegistrarUsuarioAmpliadoCallback() {
            @Override
            public void onUsuarioRegistrado() {
                Log.i("REGISTRO AMPLIADO", "SUCCESFUL");
                callback.onUsuarioRegistrado();
            }

            @Override
            public void onUsuarioRegistradoError() {
                Log.i("REGISTRO AMPLIADO", "ERROR");
                callback.onUsuarioRegistradoError();
            }
        });
    }

    @Override
    public void comprobarUsuarioRegistrado(final ComprobarUsuarioRegistradoCallback callback) {
        UsuariosFirebase usuariosFirebase=UsuariosFirebase.getInstance();
        usuariosFirebase.comprobarUsuarioRegistrado(new ComprobarUsuarioRegistradoCallback() {
            @Override
            public void onUsuarioRegistrado() {

                callback.onUsuarioRegistrado();
            }

            @Override
            public void onUsuarioRegistradoError() {

                callback.onUsuarioRegistradoError();
            }
        });
    }

    @Override
    public void cancelarRegistroUsuario(final CancelarRegistroUsuarioCallback callback) {
        UsuariosFirebase usuariosFirebase= UsuariosFirebase.getInstance();
        usuariosFirebase.cancelarRegistroUsuario(new CancelarRegistroUsuarioCallback() {
            @Override
            public void onRegistroCancelado() {
                callback.onRegistroCancelado();
            }

            @Override
            public void onRegistroCanceladoError() {
                callback.onRegistroCanceladoError();
            }
        });
    }


    @Override
    public void iniciarListener(final IniciarListenerCallback callback) {
        UsuariosFirebase usuariosFirebase= UsuariosFirebase.getInstance();
        Log.i("LISTENER","REPOSITORY");
        usuariosFirebase.iniciarListener(new IniciarListenerCallback() {


            @Override
            public void onUsuarioRegistrado(String TOKEN) {
                Log.i("LISTENER: ", "INICIADO");
                callback.onUsuarioRegistrado(TOKEN);

            }

            @Override
            public void onUsuarioNoRegistrado(String TOKEN) {
                Log.i("LISTENER: ", "NO INICIADO");
                callback.onUsuarioNoRegistrado(TOKEN);
            }
        });

    }

    @Override
    public void obtenerFotoPerfil(final ObtenerFotoPerfilCallback callback) {
        UsuariosFirebase usuariosFirebase= UsuariosFirebase.getInstance();
        usuariosFirebase.obtenerFotoPerfil(new ObtenerFotoPerfilCallback() {
            @Override
            public void onFotoPerfilObtenida(byte[] foto) {
                callback.onFotoPerfilObtenida(foto);
            }

            @Override
            public void onFotoPerfilObtenidaError() {
                callback.onFotoPerfilObtenidaError();
            }
        });
    }

    @Override
    public void obtenerFotoPerfilUsuario(String uid, final PeticionQuedada pQuedada, final ObtenerFotoPerfilUsuarioCallback callback) {
        UsuariosFirebase usuariosFirebase= UsuariosFirebase.getInstance();
        usuariosFirebase.obtenerFotoPerfilUsuario(uid,  pQuedada, new ObtenerFotoPerfilUsuarioCallback() {
            @Override
            public void onFotoUsuarioPerfilObtenida(byte[] foto, PeticionQuedada pQuedada) {
                callback.onFotoUsuarioPerfilObtenida(foto,  pQuedada);
            }

            @Override
            public void onFotoUsuarioPerfilObtenidaError() {
                 callback.onFotoUsuarioPerfilObtenidaError();
            }
        });
    }

    @Override
    public void obtenerUsuarioActual(final ObtenerUsuarioActualCallback callback) {
        UsuariosFirebase usuariosFirebase= UsuariosFirebase.getInstance();
        usuariosFirebase.obtenerUsuarioActual(new ObtenerUsuarioActualCallback() {
            @Override
            public void onUsuarioObtenido(Usuario usuario) {
                callback.onUsuarioObtenido(usuario);
            }

            @Override
            public void onUsuarioObtenidoError() {
                callback.onUsuarioObtenidoError();
            }
        });
    }

    @Override
    public void cambiarContraseña(String email, final CambiarContraseñaCallback callback) {
        UsuariosFirebase usuariosFirebase= UsuariosFirebase.getInstance();
        usuariosFirebase.cambiarContraseña(email, new CambiarContraseñaCallback() {
             @Override
             public void onContraseñaCambiada() {
                  callback.onContraseñaCambiada();
             }

             @Override
             public void onContraseñaCambiadaError() {
                  callback.onContraseñaCambiadaError();
             }
        });
    }

    @Override
    public void obtenerUsuarioPorUID(String Uid, final ObtenerUsuarioPorUIDCallback callback) {
        UsuariosFirebase usuariosFirebase= UsuariosFirebase.getInstance();
        usuariosFirebase.obtenerUsuarioPorUID(Uid, new ObtenerUsuarioPorUIDCallback() {
            @Override
            public void onUsuarioObtenido(Usuario usuario) {
                callback.onUsuarioObtenido(usuario);
            }

            @Override
            public void onUsuarioObtenidoError() {

                callback.onUsuarioObtenidoError();
            }
        });
    }

    @Override
    public void obtenerUidUsuarioActual(final ObtenerUidUsuarioActualCallback callback) {
        UsuariosFirebase usuariosFirebase= UsuariosFirebase.getInstance();
        usuariosFirebase.obtenerUidUsuarioActual(new ObtenerUidUsuarioActualCallback() {
            @Override
            public void onUsuarioObtenido(String uid) {
                callback.onUsuarioObtenido(uid);
            }

            @Override
            public void onUsuarioObtenidoError() {

                callback.onUsuarioObtenidoError();
            }
        });
    }

    @Override
    public void obtenerCorreoUsuarioActual(final ObtenerCorreoUsuarioActualCallback callback) {
        UsuariosFirebase usuariosFirebase= UsuariosFirebase.getInstance();
        usuariosFirebase.obtenerCorreoUsuarioActual(new ObtenerCorreoUsuarioActualCallback() {
            @Override
            public void onCorreoUsuarioObtenido(String correoUsuario) {
                Log.i("CORREO REPO",""+correoUsuario);
                callback.onCorreoUsuarioObtenido(correoUsuario);
            }

            @Override
            public void onCorreoUsuarioObtenidoError() {
                callback.onCorreoUsuarioObtenidoError();
            }
        });
    }

    @Override
    public void setTOKEN(String TOKEN, final SeleccionarTOKENCallback callback) {
        UsuariosFirebase usuariosFirebase= UsuariosFirebase.getInstance();
        usuariosFirebase.setTOKEN(TOKEN, new SeleccionarTOKENCallback() {
            @Override
            public void onTOKENseleccionado() {
                callback.onTOKENseleccionado();
            }
        });
    }


}
