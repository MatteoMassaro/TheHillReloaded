package com.example.thehillreloaded.menu;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import android.view.View;
import android.widget.ImageView;

import com.example.thehillreloaded.R;
import com.example.thehillreloaded.animazioni.Animazioni;

public class MultigiocatoreActivity extends Animazioni implements View.OnClickListener{

    //Variabili
    public CardView unoControUno, cooperativo;
    public ImageView indietro;

    //Chiama l'animazione all'avvio dell'activity
    @Override
    protected void onStart(){
        super.onStart();
        runAnimationSlideIn(unoControUno);
        runAnimationSlideIn(cooperativo);
    }

    //Chiama l'animazione alla pausa dell'activity
    @Override
    protected void onPause(){
        super.onPause();
        runAnimationSlideOut(unoControUno);
        runAnimationSlideOut(cooperativo);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multigiocatore);

        //Imposta l'orientamento portrait come obbligatorio
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //Trova le view tramite l'id e le assegna alle variabili
        unoControUno = findViewById(R.id.uno_contro_uno);
        cooperativo = findViewById(R.id.cooperativo);
        indietro = findViewById(R.id.indietro);

        //Imposta metodo di callback quando la view viene cliccata
        unoControUno.setOnClickListener(this);
        cooperativo.setOnClickListener(this);
        indietro.setOnClickListener(this);

        //Animazione pulsanti
        clickButtonAnimation(unoControUno);
        clickButtonAnimation(cooperativo);
        clickButtonAnimation(indietro);
    }

    //Crea l'intent per passare all'activity successiva dopo la pressione di un pulsante
    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        Intent i;
        switch (view.getId()){
            case R.id.uno_contro_uno:
            case R.id.cooperativo:
                i = new Intent(this, BluetoothActivity.class);
                Bundle b = ActivityOptions.makeSceneTransitionAnimation(this).toBundle();
                startActivity(i, b);
                finish();
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