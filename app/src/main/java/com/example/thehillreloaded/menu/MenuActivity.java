package com.example.thehillreloaded.menu;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;

import com.example.thehillreloaded.R;
import com.example.thehillreloaded.accesso.LoginActivity;
import com.example.thehillreloaded.accesso.ModalitaAccessoActivity;
import com.example.thehillreloaded.animazioni.AnimazioniView;

public class MenuActivity extends AnimazioniView implements View.OnClickListener{

    //Variabili
    public CardView giocatoreSingolo, multigiocatore;
    public ImageView logout, impostazioni;

    //Chiama l'animazione all'avvio dell'activity
    @Override
    protected void onStart(){
        super.onStart();
        runAnimationSlideIn(giocatoreSingolo);
        runAnimationSlideIn(multigiocatore);
    }

    //Chiama l'animazione alla pausa dell'activity
    @Override
    protected void onPause(){
        super.onPause();
        runAnimationSlideOut(giocatoreSingolo);
        runAnimationSlideOut(multigiocatore);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        //Imposta l'orientamento portrait come obbligatorio
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //Trova le view tramite l'id e le assegna alle variabili
        giocatoreSingolo = findViewById(R.id.giocatoreSingolo);
        multigiocatore = findViewById(R.id.multigiocatore);
        logout = findViewById(R.id.logout);
        impostazioni = findViewById(R.id.impostazioni);

        //Imposta metodo di callback quando la view viene cliccata
        multigiocatore.setOnClickListener(this);
        giocatoreSingolo.setOnClickListener(this);
        logout.setOnClickListener(this);
        impostazioni.setOnClickListener(this);

        //Animazione pulsanti
        clickButtonAnimation(giocatoreSingolo);
        clickButtonAnimation(multigiocatore);
        clickButtonAnimation(logout);
        clickButtonAnimation(impostazioni);
    }

    //Crea l'intent per passare all'activity successiva dopo la pressione di un pulsante
    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        Intent i;
        switch (view.getId()){
            case R.id.giocatoreSingolo:
                i = new Intent(this, GiocatoreSingoloActivity.class);
                Bundle b = ActivityOptions.makeSceneTransitionAnimation(this).toBundle();
                startActivity(i, b);
                break;
            case R.id.multigiocatore:
                i = new Intent(this, MultigiocatoreActivity.class);
                Bundle b1 = ActivityOptions.makeSceneTransitionAnimation(this).toBundle();
                startActivity(i,b1);
                break;
            case R.id.logout:
                i = new Intent(this, ModalitaAccessoActivity.class);
                Bundle b2 = ActivityOptions.makeSceneTransitionAnimation(this).toBundle();
                startActivity(i,b2);
                finish();
                break;
            case R.id.impostazioni:
                i = new Intent(this, ImpostazioniActivity.class);
                Bundle b3 = ActivityOptions.makeSceneTransitionAnimation(this).toBundle();
                startActivity(i,b3);
                break;

        }
    }
}