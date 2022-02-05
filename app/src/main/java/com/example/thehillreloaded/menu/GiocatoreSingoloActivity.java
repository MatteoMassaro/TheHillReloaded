package com.example.thehillreloaded.menu;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;

import com.example.thehillreloaded.R;
import com.example.thehillreloaded.animazioni.SchermataCaricamentoActivity;
import com.example.thehillreloaded.animazioni.AnimazioniView;

public class GiocatoreSingoloActivity extends AnimazioniView implements View.OnClickListener{

    //Variabili
    public CardView modalitaClassica, modalitaPowerUp;
    public ImageView indietro;

    //Chiama l'animazione all'avvio dell'activity
    @Override
    protected void onStart(){
        super.onStart();
        runAnimationSlideIn(modalitaClassica);
        runAnimationSlideIn(modalitaPowerUp);
    }

    //Chiama l'animazione alla pausa dell'activity
    @Override
    protected void onPause(){
        super.onPause();
        runAnimationSlideOut(modalitaClassica);
        runAnimationSlideOut(modalitaPowerUp);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giocatoresingolo);

        //Imposta l'orientamento portrait come obbligatorio
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //Trova le view tramite l'id e le assegna alle variabili
        modalitaClassica = findViewById(R.id.modalitaClassica);
        modalitaPowerUp = findViewById(R.id.modalitaPowerUp);
        indietro = findViewById(R.id.indietro);

        //Imposta metodo di callback quando la view viene cliccata
        modalitaClassica.setOnClickListener(this);
        modalitaPowerUp.setOnClickListener(this);
        indietro.setOnClickListener(this);

        //Animazione pulsanti
        clickButtonAnimation(modalitaClassica);
        clickButtonAnimation(modalitaPowerUp);
        clickButtonAnimation(indietro);
    }

    //Crea l'intent per passare all'activity successiva dopo la pressione di un pulsante
    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        Intent i;
        switch (view.getId()){
            case R.id.modalitaClassica:
            case R.id.modalitaPowerUp:
                i = new Intent(this, SchermataCaricamentoActivity.class);
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