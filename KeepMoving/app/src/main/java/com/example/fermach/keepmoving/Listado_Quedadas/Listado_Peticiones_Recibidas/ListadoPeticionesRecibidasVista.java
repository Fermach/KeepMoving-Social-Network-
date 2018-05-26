package com.example.fermach.keepmoving.Listado_Quedadas.Listado_Peticiones_Recibidas;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.text.LocaleDisplayNames;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.fermach.keepmoving.Modelos.Quedada.PeticionQuedada;
import com.example.fermach.keepmoving.Modelos.Quedada.PeticionQuedadaRecibida;
import com.example.fermach.keepmoving.R;
import com.example.fermach.keepmoving.Usuario_Perfil_Vista.PerfilVistaPantallaVista;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Fermach on 27/03/2018.
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

        progressDialog= new ProgressDialog(myView.getContext());
        progressDialog.setMessage("Obteniendo peticiones");
        progressDialog.setCancelable(false);
        progressDialog.show();

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

    @Override
    public void onPeticionesRecibidasObtenidas(List<PeticionQuedada> peticionesQuedadas) {
        this.lista_peticiones= peticionesQuedadas;
        lista_peticionesRecibidas=new ArrayList<>();
        adaptadorSeteado=false;
        for(PeticionQuedada mpeticionQuedada: lista_peticiones){
            presenter.obtenerFotoUsuario(mpeticionQuedada.getAutor_peticion(),mpeticionQuedada);

        }
    }

    @Override
    public void onPeticionesRecibidasObtenidasError() {

        Snackbar.make(myView,"No ha sido posible obtener la lista de peticiones pendientes", Snackbar.LENGTH_SHORT).show();
        progressDialog.dismiss();
    }



    @Override
    public void mostrarPeticionesRecibidasNumero(List<PeticionQuedada> peticionesQuedadas) {
        num_peticiones_recibidas.setText("Numero de Peticiones Pendientes: "+ peticionesQuedadas.size());

    }

    @Override
    public void onEstadoCambiado() {
        fragment = new ListadoPeticionesRecibidasVista();
        getFragmentManager().beginTransaction().replace(R.id.content_main, fragment ).commit();

    }

    @Override
    public void onEstadoCambiadoError() {

    }

    @Override
    public void onFotoObtenida(byte[] foto,  PeticionQuedada pQuedada)
    {
        Log.i("ADAPTADOR", "+++++++++++++ ADAPTADOR SETEADO = "+adaptadorSeteado+" ++++++++++++++++++++++");
        if(adaptadorSeteado==true){
            return;
        }else {
            Log.i("ADAPTADOR", "+++++++++ PetCon FOTO" + pQuedada.toString() + " ++++++++");


            BitmapFactory.Options options = new BitmapFactory.Options();
            Bitmap bitmap = BitmapFactory.decodeByteArray(foto, 0, foto.length, options);

            peticionQuedadaRecibida = new PeticionQuedadaRecibida(pQuedada.getId(), pQuedada.getAutor_peticion_nombre(), pQuedada.getAutor(), pQuedada.getAutor_uid(), pQuedada.getLugar(), pQuedada.getFecha(), pQuedada.getHora(), pQuedada.getDeporte(), pQuedada.getInfo(), pQuedada.getPlazas(), pQuedada.getLongitud(),
                    pQuedada.getLatitud(), pQuedada.getNum_plazas_solicitadas(), pQuedada.getEstado(), pQuedada.getAutor_peticion());
            peticionQuedadaRecibida.setFoto(bitmap);

            if (lista_peticionesRecibidas.isEmpty()) {
                lista_peticionesRecibidas.add(peticionQuedadaRecibida);
            } else {
                for (int i = 0; i < lista_peticionesRecibidas.size(); i++) {
                    if (lista_peticionesRecibidas.get(i).getId().equals(peticionQuedadaRecibida.getId()) &&
                            lista_peticionesRecibidas.get(i).getAutor_peticion().equals(peticionQuedadaRecibida.getAutor_peticion())) {


                    } else {
                        lista_peticionesRecibidas.add(peticionQuedadaRecibida);

                    }
                }
            }

            if (lista_peticionesRecibidas.size() == lista_peticiones.size()) {

                Log.i("ADAPTADOR", "+++++++++ SETEANDO ADAPTADOR ++++++++\n" + lista_peticionesRecibidas.toString());
                adaptador = new PeticionesRecibidasAdapter(lista_peticionesRecibidas);
                adaptador.setCustomButtonListner(this);
                adaptador.setCustomImageButtonListener(this);
                listView.setAdapter(adaptador);
                adaptadorSeteado = true;

                LinearLayoutManager llm = new LinearLayoutManager(getContext());
                llm.setOrientation(LinearLayoutManager.HORIZONTAL);
                listView.setLayoutManager(llm);


                progressDialog.dismiss();
            }

        }
    }

    @Override
    public void onFotoObtenidaError() {
        lista_peticionesRecibidas.add(peticionQuedadaRecibida);
    }


    @Override
    public void onButtonClickListner(int position, PeticionQuedadaRecibida pQuedada, String estado) {
        pQuedada.setEstado(estado);
        peticionQuedada=new PeticionQuedada(pQuedada.getId(),pQuedada.getAutor_peticion_nombre(),pQuedada.getAutor(),pQuedada.getAutor_uid(),pQuedada.getLugar(),pQuedada.getFecha(),pQuedada.getHora(),pQuedada.getDeporte(),pQuedada.getInfo(),pQuedada.getPlazas(),pQuedada.getLongitud(),
                pQuedada.getLatitud(),pQuedada.getNum_plazas_solicitadas(),pQuedada.getEstado() );
        presenter.cambiarEstadoQuedada(peticionQuedada);
    }

    @Override
    public void onImageButtonListner(int position, PeticionQuedadaRecibida pQuedada) {

        Bundle bundle = new Bundle();
        bundle.putSerializable(UID_USUARIO, pQuedada.getAutor_peticion());
        Fragment toFragment = new PerfilVistaPantallaVista();
        toFragment.setArguments(bundle);
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.content_main, toFragment, UID_USUARIO)
                .addToBackStack(UID_USUARIO).commit();
    }


}
