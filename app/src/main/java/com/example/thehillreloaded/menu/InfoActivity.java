package com.example.thehillreloaded.menu;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.thehillreloaded.R;
import com.example.thehillreloaded.animazioni.Animazioni;

public class InfoActivity extends Animazioni {

    //Variabili
    public ImageView indietro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        //Imposta l'orientamento portrait come obbligatorio
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //Trova le view tramite l'id e le assegna alle variabili
        indietro = findViewById(R.id.indietro);

        //Animazione pulsanti
        clickButtonAnimation(indietro);

        //Imposta metodo di callback quando la view viene cliccata
        indietro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(InfoActivity.this, GiocatoreSingoloActivity.class);
                startActivity(i);
            }
        });
    }
}