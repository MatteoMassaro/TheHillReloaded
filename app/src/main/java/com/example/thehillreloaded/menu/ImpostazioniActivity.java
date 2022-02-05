package com.example.thehillreloaded.menu;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;

import com.example.thehillreloaded.LinguaActivity;
import com.example.thehillreloaded.R;
import com.example.thehillreloaded.animazioni.AnimazioniView;

public class ImpostazioniActivity extends AnimazioniView implements View.OnClickListener {

    //Variabili
    public CardView lingua, volumeEffetti;
    public ImageView indietro;


    //Chiama l'animazione all'avvio dell'activity
    @Override
    protected void onStart(){
        super.onStart();
        runAnimationSlideIn(lingua);
        runAnimationSlideIn(volumeEffetti);
    }

    //Chiama l'animazione alla pausa dell'activity
    @Override
    protected void onPause(){
        super.onPause();
        runAnimationSlideOut(lingua);
        runAnimationSlideOut(volumeEffetti);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_impostazioni);

        //Imposta l'orientamento portrait come obbligatorio
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //Trova le view tramite l'id e le assegna alle variabili
        lingua = findViewById(R.id.lingua);
        volumeEffetti = findViewById(R.id.volume_effetti);
        indietro = findViewById(R.id.indietro);

        //Imposta metodo di callback quando la view viene cliccata
        lingua.setOnClickListener(this);
        volumeEffetti.setOnClickListener(this);
        indietro.setOnClickListener(this);

        //Animazione pulsanti
        clickButtonAnimation(lingua);
        clickButtonAnimation(volumeEffetti);
        clickButtonAnimation(indietro);
    }

    //Crea l'intent per passare all'activity successiva dopo la pressione di un pulsante
    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        Intent i;
        switch (view.getId()){
            case R.id.lingua:
                i = new Intent(this, LinguaActivity.class);
                Bundle b = ActivityOptions.makeSceneTransitionAnimation(this).toBundle();
                startActivity(i, b);
                break;
            case R.id.volume_effetti:
                i = new Intent(this, VolumeActivity.class);
                Bundle b1 = ActivityOptions.makeSceneTransitionAnimation(this).toBundle();
                startActivity(i, b1);
                break;
            case R.id.indietro:
                i = new Intent(this, MenuActivity.class);
                Bundle b2 = ActivityOptions.makeSceneTransitionAnimation(this).toBundle();
                startActivity(i, b2);
                finish();
                break;


        }
    }
}