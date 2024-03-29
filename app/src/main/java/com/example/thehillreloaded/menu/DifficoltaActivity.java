package com.example.thehillreloaded.menu;

import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.thehillreloaded.R;
import com.example.thehillreloaded.animazioni.Animazioni;

public class DifficoltaActivity extends Animazioni implements View.OnClickListener{

    //Variabili
    public CardView facile, media, difficile;
    public ImageView indietro;
    public static double tassoDifficolta;
    public static String difficoltà;

    //Chiama l'animazione all'avvio dell'activity
    @Override
    protected void onStart() {
        super.onStart();
        runAnimationSlideIn(facile);
        runAnimationSlideIn(media);
        runAnimationSlideIn(difficile);
    }

    //Chiama l'animazione alla pausa dell'activity
    @Override
    protected void onPause() {
        super.onPause();
        runAnimationSlideOut(facile);
        runAnimationSlideOut(media);
        runAnimationSlideOut(difficile);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_difficolta);

        //Imposta l'orientamento portrait come obbligatorio
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //Trova le view tramite l'id e le assegna alle variabili
        facile = findViewById(R.id.facile);
        media = findViewById(R.id.media);
        difficile = findViewById(R.id.difficile);
        indietro = findViewById(R.id.indietro);

        //Imposta metodo di callback quando la view viene cliccata
        facile.setOnClickListener(this);
        media.setOnClickListener(this);
        difficile.setOnClickListener(this);
        indietro.setOnClickListener(this);

        //Animazione pulsanti
        clickButtonAnimation(facile);
        clickButtonAnimation(media);
        clickButtonAnimation(difficile);
        clickButtonAnimation(indietro);
    }

        //Crea l'intent per passare all'activity successiva dopo la pressione di un pulsante
        @SuppressLint("NonConstantResourceId")
        @Override
        public void onClick(View view) {
            Intent i;
            Bundle b;
            switch (view.getId()){
                case R.id.facile:
                    tassoDifficolta = 0.8;
                    difficoltà = "Facile";
                    GiocatoreSingoloActivity.partitaSalvata = false;
                    i = new Intent(this, SchermataCaricamentoActivity.class);
                    b = ActivityOptions.makeSceneTransitionAnimation(this).toBundle();
                    startActivity(i, b);
                    finish();
                    break;
                case R.id.media:
                    tassoDifficolta = 1;
                    difficoltà = "Media";
                    GiocatoreSingoloActivity.partitaSalvata = false;
                    i = new Intent(this, SchermataCaricamentoActivity.class);
                    b = ActivityOptions.makeSceneTransitionAnimation(this).toBundle();
                    startActivity(i, b);
                    finish();
                    break;
                case R.id.difficile:
                    tassoDifficolta = 1.25;
                    difficoltà = "Difficile";
                    GiocatoreSingoloActivity.partitaSalvata = false;
                    i = new Intent(this, SchermataCaricamentoActivity.class);
                    b = ActivityOptions.makeSceneTransitionAnimation(this).toBundle();
                    startActivity(i, b);
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
