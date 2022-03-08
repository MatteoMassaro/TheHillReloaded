package com.example.thehillreloaded.menu;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.thehillreloaded.R;
import com.example.thehillreloaded.animazioni.Animazioni;

public class PunteggiActivity extends Animazioni {

    //Variabili
    ImageView indietro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_punteggi);

        //Trova le view tramite l'id e le assegna alle variabili
        indietro =  findViewById(R.id.indietro);

        //Imposta l'orientamento portrait come obbligatorio
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //Animazione pulsanti
        clickButtonAnimation(indietro);

        //Crea l'intent per passare all'activity successiva dopo la pressione del pulsante
        indietro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PunteggiActivity.this, MenuActivity.class);
                Bundle b = ActivityOptions.makeSceneTransitionAnimation(PunteggiActivity.this).toBundle();
                startActivity(i, b);
                finish();
            }
        });
    }
}