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
    public CardView unoControUnoClassico, unoControUnoPowerUp;
    public ImageView indietro;
    public static boolean unoVSunoClassico, unoVSunoPowerUp = false;
    public static String modalità;

    //Chiama l'animazione all'avvio dell'activity
    @Override
    protected void onStart(){
        super.onStart();
        runAnimationSlideIn(unoControUnoClassico);
        runAnimationSlideIn(unoControUnoPowerUp);
    }

    //Chiama l'animazione alla pausa dell'activity
    @Override
    protected void onPause(){
        super.onPause();
        runAnimationSlideOut(unoControUnoClassico);
        runAnimationSlideOut(unoControUnoPowerUp);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multigiocatore);

        //Imposta l'orientamento portrait come obbligatorio
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //Trova le view tramite l'id e le assegna alle variabili
        unoControUnoClassico = findViewById(R.id.uno_contro_uno_classico);
        unoControUnoPowerUp = findViewById(R.id.uno_contro_uno_powerUp);
        indietro = findViewById(R.id.indietro);

        //Imposta metodo di callback quando la view viene cliccata
        unoControUnoClassico.setOnClickListener(this);
        unoControUnoPowerUp.setOnClickListener(this);
        indietro.setOnClickListener(this);

        //Animazione pulsanti
        clickButtonAnimation(unoControUnoClassico);
        clickButtonAnimation(unoControUnoPowerUp);
        clickButtonAnimation(indietro);
    }

    //Crea l'intent per passare all'activity successiva dopo la pressione di un pulsante
    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        Intent i;
        switch (view.getId()){
            case R.id.uno_contro_uno_classico:
                unoVSunoClassico = true;
                unoVSunoPowerUp = false;
                modalità = "1 VS 1 Classico";
                i = new Intent(this, ConnessioneActivity.class);
                Bundle b = ActivityOptions.makeSceneTransitionAnimation(this).toBundle();
                startActivity(i, b);
                finish();
                break;
            case R.id.uno_contro_uno_powerUp:
                unoVSunoPowerUp = true;
                unoVSunoClassico = false;
                modalità = "1 VS 1 Power-up";
                i = new Intent(this, ConnessioneActivity.class);
                Bundle b1 = ActivityOptions.makeSceneTransitionAnimation(this).toBundle();
                startActivity(i, b1);
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