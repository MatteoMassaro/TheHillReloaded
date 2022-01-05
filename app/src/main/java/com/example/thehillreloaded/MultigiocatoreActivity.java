package com.example.thehillreloaded;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

public class MultigiocatoreActivity extends Animazioni implements View.OnClickListener{

    //Variabili
    public CardView unoControUno, cooperativo;
    public Animation slideIn, slideOut;

    //Chiama l'animazione all'avvio dell'activity
    @Override
    protected void onStart(){
        super.onStart();
        runAnimationSlideIn();
    }

    //Chiama l'animazione alla pausa dell'activity
    @Override
    protected void onPause(){
        super.onPause();
        runAnimationSlideOut();
    }

    //Setta l'animazione iniziale delle view
    private void runAnimationSlideIn() {
        slideIn = AnimationUtils.loadAnimation(this,R.anim.slide_in);
        unoControUno.startAnimation(slideIn);
        cooperativo.startAnimation(slideIn);
    }

    //Setta l'animazione finale delle view
    private void runAnimationSlideOut() {
        slideOut = AnimationUtils.loadAnimation(this,R.anim.slide_out);
        unoControUno.startAnimation(slideOut);
        cooperativo.startAnimation(slideOut);
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

        //Imposta metodo di callback quando la view viene cliccata
        unoControUno.setOnClickListener(this);
        cooperativo.setOnClickListener(this);

        //Animazione pulsanti
        clickButtonAnimation(unoControUno);
        clickButtonAnimation(cooperativo);
    }

    //Crea l'intent per passare all'activity successiva dopo la pressione di un bottone
    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        Intent i;
        switch (view.getId()){
            case R.id.uno_contro_uno:
                i = new Intent(this, SchermataCaricamentoActivity.class);
                Bundle b = ActivityOptions.makeSceneTransitionAnimation(this).toBundle();
                startActivity(i, b);
                break;
            case R.id.cooperativo:
                i = new Intent(this, SchermataCaricamentoActivity.class);
                Bundle b1 = ActivityOptions.makeSceneTransitionAnimation(this).toBundle();
                startActivity(i,b1);
                break;

        }
    }
}