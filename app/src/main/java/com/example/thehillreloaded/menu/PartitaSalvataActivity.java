package com.example.thehillreloaded.menu;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.cardview.widget.CardView;

import com.example.thehillreloaded.R;
import com.example.thehillreloaded.animazioni.Animazioni;
import com.example.thehillreloaded.gameplay.GameActivity;
import com.example.thehillreloaded.gameplay.NormalGameActivity;
import com.example.thehillreloaded.gameplay.PowerUpGameActivity;
import com.example.thehillreloaded.gameplay.PowerUpGameView;

public class PartitaSalvataActivity extends Animazioni implements View.OnClickListener{

    //Variabili
    public CardView partitaSalvata, nuovaPartita;
    public ImageView indietro;

    //Chiama l'animazione all'avvio dell'activity
    @Override
    protected void onStart(){
        super.onStart();
        runAnimationSlideIn(partitaSalvata);
        runAnimationSlideIn(nuovaPartita);
    }

    //Chiama l'animazione alla pausa dell'activity
    @Override
    protected void onPause(){
        super.onPause();
        runAnimationSlideOut(partitaSalvata);
        runAnimationSlideOut(nuovaPartita);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partita_salvata);

        //Imposta l'orientamento portrait come obbligatorio
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //Trova le view tramite l'id e le assegna alle variabili
        partitaSalvata = findViewById(R.id.partitaSalvata);
        nuovaPartita = findViewById(R.id.nuovaPartita);
        indietro = findViewById(R.id.indietro);

        //Imposta metodo di callback quando la view viene cliccata
        partitaSalvata.setOnClickListener(this);
        nuovaPartita.setOnClickListener(this);
        indietro.setOnClickListener(this);

        //Animazione pulsanti
        clickButtonAnimation(partitaSalvata);
        clickButtonAnimation(nuovaPartita);
        clickButtonAnimation(indietro);
    }

    //Crea l'intent per passare all'activity successiva dopo la pressione di un pulsante
    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        Intent i;
        switch (view.getId()) {
            case R.id.partitaSalvata:
                i = new Intent(this, SchermataCaricamentoActivity.class);
                Bundle b = ActivityOptions.makeSceneTransitionAnimation(this).toBundle();
                startActivity(i, b);
                finish();
                break;
            case R.id.nuovaPartita:
                i = new Intent(this, DifficoltaActivity.class);
                Bundle b1 = ActivityOptions.makeSceneTransitionAnimation(this).toBundle();
                startActivity(i, b1);
                finish();
                break;
            case R.id.indietro:
                i = new Intent(this, GiocatoreSingoloActivity.class);
                Bundle b2 = ActivityOptions.makeSceneTransitionAnimation(this).toBundle();
                startActivity(i, b2);
                finish();
                break;
        }
    }
}