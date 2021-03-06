package com.example.fermach.keepmoving.MainActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import com.example.fermach.keepmoving.App;
import com.example.fermach.keepmoving.R;


/**
 * Pantalla con el logo de la aplicación
 * @author Fermach
 * @version 1.0.
 *
 */

public class Pantalla_temporal extends FragmentActivity {
    private View myView;



    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantalla_temporal);


        Log.i("PantallaT", "En Pantalla temporal");

        //lanzamos un hilo
        new Handler().postDelayed(new Runnable(){
            public void run(){
                // Cuando pasen los 3 segundos, pasamos a la actividad principal de la aplicación
                Intent intent = new Intent(App.getAppContext(), MainActivity.class);
                startActivity(intent);

            };
        }, 4000);
    }
}
