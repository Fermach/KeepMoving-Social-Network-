package com.example.fermach.keepmoving.Detalle_Quedada.DetalleQuedadaGeneral;

import com.example.fermach.keepmoving.Modelos.Quedada.Quedada;
import com.example.fermach.keepmoving.Modelos.Quedada.QuedadaDataSource;
import com.example.fermach.keepmoving.Modelos.Quedada.QuedadasRepository;

/**
 * Created by Fermach on 27/03/2018.
 */

public class DetalleQuedadaPresenter implements DetalleQuedadaContract.Presenter{
    private QuedadasRepository repository;
    private DetalleQuedadaContract.View view;



    public DetalleQuedadaPresenter(DetalleQuedadaContract.View view) {
        this.view = view;
        this.repository = QuedadasRepository.getInstance();
    }


}
