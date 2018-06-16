package com.example.fermach.keepmoving.Quedadas.Listado_Quedadas.Listado_Peticiones_Recibidas;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.fermach.keepmoving.MainActivity.ChangeToolbar;
import com.example.fermach.keepmoving.Modelos.Quedada.PeticionQuedada;
import com.example.fermach.keepmoving.Modelos.Quedada.PeticionQuedadaRecibida;
import com.example.fermach.keepmoving.R;
import com.example.fermach.keepmoving.Usuarios.Usuario_Perfil_Vista.PerfilVistaPantallaVista;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Interfaz del listado de peticiones recibidas
 */

public class ListadoPeticionesRecibidasVista extends Fragment implements ListadoPeticionesRecibidasContract.View, PeticionesRecibidasAdapter.CustomButtonListener,PeticionesRecibidasAdapter.CustomImageButtonListener {

    private PeticionQuedada peticionQuedada;
    private PeticionQuedadaRecibida peticionQuedadaRecibida;
    private Fragment fragment;
    private List<PeticionQuedada> lista_peticiones;
    private List<PeticionQuedadaRecibida> lista_peticionesRecibidas;
    private String obteniendoFoto;
    private View myView;
    private RecyclerView listView;
    private PeticionesRecibidasAdapter adaptador;
    private TextView num_peticiones_recibidas;
    private ListadoPeticionesRecibidasContract.Presenter presenter;
    private final String QUEDADA ="QUEDADA";
    private final String UID_USUARIO ="UID_USUARIO";
    private ProgressDialog progressDialog;
    private boolean adaptadorSeteado=false;

    public ListadoPeticionesRecibidasVista() {

    }

    public ProgressDialog getProgressDialog() {
        return progressDialog;
    }

    public void setProgressDialog(ProgressDialog progressDialog) {
        this.progressDialog = progressDialog;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.listado_peticiones_quedadas_recibidas, container, false);

        lista_peticiones= new ArrayList<>();
        lista_peticionesRecibidas= new ArrayList<>();

        ((ChangeToolbar)getActivity()).setToolbarText("Peticiones recibidas");

        //si las rutinas no se han obtenido a los 30 sec se muestra el error
        new Handler().postDelayed(new Runnable(){
            public void run(){

              if(progressDialog.isShowing()){
                  Snackbar.make(myView,"No ha sido posible obtener la lista de peticiones pendientes", Snackbar.LENGTH_SHORT).show();
                  progressDialog.dismiss();

              }

            };
        }, 30000);

        progressDialog= new ProgressDialog(myView.getContext());
        progressDialog.setMessage("Obteniendo peticiones");
        progressDialog.setCancelable(false);


        presenter = new ListadoPeticionesRecibidasPresenter(this);
        presenter.obtenerPeticionesRecibidas();

        inicializarVista();
        activarControladores();

        return myView;
    }



    public void inicializarVista() {
        listView=myView.findViewById(R.id.list_peticiones_recibidas);
        num_peticiones_recibidas=myView.findViewById(R.id.num_peticiones_recibidas_lista);
    }

    public void activarControladores(){
        //Cuando clickemos en la lista de quedadas nos lleva al fragmento con el
        //detalle de la quedada pasándole el id de la quedada

    }

    /**
     * Cuando se obtienen las peticiones
     * @param peticionesQuedadas
     */
    @Override
    public void onPeticionesRecibidasObtenidas(List<PeticionQuedada> peticionesQuedadas) {
        this.lista_peticiones= peticionesQuedadas;
        lista_peticionesRecibidas=new ArrayList<>();


        progressDialog.show();
        Log.i("ADAPTADOR", "+++++++++ LISTA PETICIONES RECIBIDAS ++++++++\n" + peticionesQuedadas.toString());

        PeticionQuedada mPeticionQuedada= peticionesQuedadas.get(peticionesQuedadas.size()-1);

        Log.i("ADAPTADOR", "+++++++++ OBTENIENDO FOTO  ++++++++\n" + mPeticionQuedada.toString());

        presenter.obtenerFotoUsuario(mPeticionQuedada.getAutor_peticion(),mPeticionQuedada);


    }

    /**
     * Cuando no se han podido obtener las peticiones
     */
    @Override
    public void onPeticionesRecibidasObtenidasError() {

        Snackbar.make(myView,"No ha sido posible obtener la lista de peticiones pendientes", Snackbar.LENGTH_SHORT).show();
        progressDialog.dismiss();
    }


    /**
     * Se muestra el numero de peticiones recibidas
     * @param peticionesQuedadas
     */
    @Override
    public void mostrarPeticionesRecibidasNumero(List<PeticionQuedada> peticionesQuedadas) {
        num_peticiones_recibidas.setText("Numero de Peticiones: "+ peticionesQuedadas.size());

    }

    /**
     * cuando se actualiza una peticion se recarga la lista
     */
    @Override
    public void onEstadoCambiado() {
        fragment = new ListadoPeticionesRecibidasVista();
        getFragmentManager().beginTransaction().replace(R.id.content_main, fragment ).commit();

    }

    @Override
    public void onEstadoCambiadoError() {

    }

    /**
     * Cuando se obtiene la foto del autor de la peticion se carga en el correspondiente item
     * @param foto
     * @param pQuedada
     */
    @Override
    public void onFotoObtenida(byte[] foto,  PeticionQuedada pQuedada)
    {

            Log.i("ADAPTADOR", "+++++++++ PetCon FOTO" + pQuedada.toString() + " ++++++++");


            BitmapFactory.Options options = new BitmapFactory.Options();
            Bitmap bitmap = BitmapFactory.decodeByteArray(foto, 0, foto.length, options);
            //se crea una peticion de quedada recibida con la peticion obteneda + la foto y se añade a la lista
            peticionQuedadaRecibida = new PeticionQuedadaRecibida(pQuedada.getId(), pQuedada.getAutor_peticion_nombre(), pQuedada.getAutor(), pQuedada.getAutor_uid(), pQuedada.getLugar(), pQuedada.getFecha(), pQuedada.getHora(), pQuedada.getDeporte(), pQuedada.getInfo(), pQuedada.getPlazas(), pQuedada.getLongitud(),
                    pQuedada.getLatitud(), pQuedada.getNum_plazas_solicitadas(), pQuedada.getEstado(), pQuedada.getAutor_peticion());
            peticionQuedadaRecibida.setFoto(bitmap);
            peticionQuedadaRecibida.setId_peticion(pQuedada.getId_peticion());

            lista_peticionesRecibidas.add(peticionQuedadaRecibida);
            Log.i("ADAPTADOR", "+++++++++ QUEDADA AÑADIDA A LISTA ++++++++\n" + peticionQuedadaRecibida.toString());
             //cuando se añaden todas las peticionesse setea el adaptador
            if (lista_peticionesRecibidas.size() == lista_peticiones.size()) {
                 //se activan los controladores del item correspondiente
                Log.i("ADAPTADOR", "+++++++++ SETEANDO ADAPTADOR ++++++++\n" + lista_peticionesRecibidas.toString());
                adaptador = new PeticionesRecibidasAdapter(lista_peticionesRecibidas);
                adaptador.setCustomButtonListner(this);
                adaptador.setCustomImageButtonListener(this);
                listView.setAdapter(adaptador);

                LinearLayoutManager llm = new LinearLayoutManager(getContext());
                llm.setOrientation(LinearLayoutManager.HORIZONTAL);
                listView.setLayoutManager(llm);

                progressDialog.dismiss();
            }


    }

    /**
     * Si no se ha podido obtener la foto
     */
    @Override
    public void onFotoObtenidaError( PeticionQuedada pQuedada) {

        //se crea una peticion de quedada recibida con la peticion obteneda sin la foto y se añade a la lista
       // lista_peticionesRecibidas.add(peticionQuedadaRecibida);
        peticionQuedadaRecibida = new PeticionQuedadaRecibida(pQuedada.getId(), pQuedada.getAutor_peticion_nombre(), pQuedada.getAutor(), pQuedada.getAutor_uid(), pQuedada.getLugar(), pQuedada.getFecha(), pQuedada.getHora(), pQuedada.getDeporte(), pQuedada.getInfo(), pQuedada.getPlazas(), pQuedada.getLongitud(),
                pQuedada.getLatitud(), pQuedada.getNum_plazas_solicitadas(), pQuedada.getEstado(), pQuedada.getAutor_peticion());
        peticionQuedadaRecibida.setId_peticion(pQuedada.getId_peticion());

        boolean añadir=true;
        for (PeticionQuedadaRecibida  mmpeticionQuedadaRecibida:lista_peticionesRecibidas
             ) {
            if(mmpeticionQuedadaRecibida.getId_peticion().toString().equals(peticionQuedadaRecibida.getId_peticion().toString())){
                añadir=false;
            }

        }

        if(añadir) {
            lista_peticionesRecibidas.add(peticionQuedadaRecibida);
        }

        //cuando se añaden todas las peticionesse setea el adaptador
        if (lista_peticionesRecibidas.size() == lista_peticiones.size()) {
            //se activan los controladores del item correspondiente
            Log.i("ADAPTADOR", "+++++++++ SETEANDO ADAPTADOR ++++++++\n" + lista_peticionesRecibidas.toString());
            adaptador = new PeticionesRecibidasAdapter(lista_peticionesRecibidas);
            adaptador.setCustomButtonListner(this);
            adaptador.setCustomImageButtonListener(this);
            listView.setAdapter(adaptador);

            LinearLayoutManager llm = new LinearLayoutManager(getContext());
            llm.setOrientation(LinearLayoutManager.HORIZONTAL);
            listView.setLayoutManager(llm);

            progressDialog.dismiss();
        }
    }


    /**
     * Cuando se clickea en un boton de un item se realiza la accion
     * correspondiente en el item correspondiente de a lista
     * @param position
     * @param pQuedada
     * @param estado
     */
    @Override
    public void onButtonClickListner(int position, PeticionQuedadaRecibida pQuedada, String estado) {
        if(isOnlineNet()) {

        peticionQuedada=new PeticionQuedada(pQuedada.getId(),pQuedada.getAutor_peticion_nombre(),pQuedada.getAutor(),pQuedada.getAutor_uid(),pQuedada.getLugar(),pQuedada.getFecha(),pQuedada.getHora(),pQuedada.getDeporte(),pQuedada.getInfo(),pQuedada.getPlazas(),pQuedada.getLongitud(),
                pQuedada.getLatitud(),pQuedada.getNum_plazas_solicitadas(),estado );
        peticionQuedada.setAutor_peticion(pQuedada.getAutor_peticion());
        peticionQuedada.setId_peticion(pQuedada.getId_peticion());

        Log.i("CAMBIANDO ESTADO ", "NUEVA PETICION CON CAMBIO DE ESTADO: " + peticionQuedada.toString());

            String fecha_obtenida= ""+peticionQuedada.getFecha()+ " "+peticionQuedada.getHora();
            if(compararFechaActualCon(fecha_obtenida)) {
                presenter.cambiarEstadoQuedada(peticionQuedada);

            }else{
                AlertDialog.Builder myBuild = new AlertDialog.Builder(getContext());
                myBuild.setMessage("La fecha propuesta de la quedada ya ha expirado.\n\n No es posible confirmar o rechazar esta quedada!" );
                myBuild.setTitle("Alerta");

                myBuild.setNegativeButton("Cerrar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                myBuild.show();
            }

        }else{
            Snackbar.make(myView,"No hay conexión con internet", Snackbar.LENGTH_SHORT).show();

        }
    }

    /**
     * Cuand se clickea en el autor de la peticion se muestra el perfil del usuario
     * en el item correspondiente
     * @param position
     * @param pQuedada
     */
    @Override
    public void onImageButtonListner(int position, PeticionQuedadaRecibida pQuedada) {
        if(isOnlineNet()) {

        Bundle bundle = new Bundle();
        bundle.putSerializable(UID_USUARIO, pQuedada.getAutor_peticion());
        Fragment toFragment = new PerfilVistaPantallaVista();
        toFragment.setArguments(bundle);
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.content_main, toFragment, UID_USUARIO)
                .addToBackStack(UID_USUARIO).commit();
        }else{
            Snackbar.make(myView,"No hay conexión con internet", Snackbar.LENGTH_SHORT).show();

        }

    }

    /**
     * Comprueba el estado de la conexion a internet
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

    /**
     * Compara una fecha con la fecha actual del sistema
     * @param fecha_obtenida
     * @return
     */
    private boolean compararFechaActualCon(String fecha_obtenida) {
        boolean fecha_valida = false;

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date date = new Date();
        String fecha_actual = dateFormat.format(date);

        try {
            Date date2 = dateFormat.parse(fecha_obtenida);
            Date date1 = dateFormat.parse(fecha_actual);

            Log.i("COMPARANDO FECHAS", "F_ACTUAL: " + date1 + ", F_OBTENIDA: " + date2);

            if (date2.after(date1) ) {
                fecha_valida = true;
                Log.i("COMPARANDO FECHAS", "F_VALIDA: TRUE");
            } else {
                fecha_valida = false;
                Log.i("COMPARANDO FECHAS", "F_VALIDA: FALSE");

            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return fecha_valida;
    }

}
