package com.example.thehillreloaded;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener{

    //Variabili
    public CardView giocatoreSingolo, multigiocatore;
    public ImageButton logout, opzioni;
    public Animation scaleUp, scaleDown, slideIn, slideOut;

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
        giocatoreSingolo.startAnimation(slideIn);
        multigiocatore.startAnimation(slideIn);
        logout.startAnimation(slideIn);
        opzioni.startAnimation(slideIn);
    }

    //Setta l'animazione finale delle view
    private void runAnimationSlideOut() {
        slideOut = AnimationUtils.loadAnimation(this,R.anim.slide_out);
        giocatoreSingolo.startAnimation(slideOut);
        multigiocatore.startAnimation(slideOut);
        logout.startAnimation(slideOut);
        opzioni.startAnimation(slideOut);
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
        opzioni = findViewById(R.id.opzioni);

        //Imposta metodo di callback quando la view viene cliccata
        multigiocatore.setOnClickListener(this);
        giocatoreSingolo.setOnClickListener(this);
        logout.setOnClickListener(this);
        opzioni.setOnClickListener(this);

        //Setta le animazioni per la pressione dei pulsanti
        scaleUp = AnimationUtils.loadAnimation(this,R.anim.scale_up);
        scaleDown = AnimationUtils.loadAnimation(this,R.anim.scale_down);

        //Animazione pulsante giocatoreSingolo
        giocatoreSingolo.setOnTouchListener((view, motionEvent) -> {
            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                giocatoreSingolo.startAnimation(scaleDown);
            } else if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                giocatoreSingolo.startAnimation(scaleUp);
            }
            return false;
        });

        //Animazione pulsante multigiocatore
        multigiocatore.setOnTouchListener((view, motionEvent) -> {
            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                multigiocatore.startAnimation(scaleDown);
            } else if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                multigiocatore.startAnimation(scaleUp);
            }
            return false;
        });

        //Animazione pulsante logout
        logout.setOnTouchListener((view, motionEvent) -> {
            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                logout.startAnimation(scaleDown);
            } else if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                logout.startAnimation(scaleUp);
            }
            return false;
        });

        //Animazione pulsante opzioni
        opzioni.setOnTouchListener((view, motionEvent) -> {
            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                opzioni.startAnimation(scaleDown);
            } else if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                opzioni.startAnimation(scaleUp);
            }
            return false;
        });

    }

    //Crea l'intent per passare all'activity successiva dopo la pressione di un bottone
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
                i = new Intent(this, LoginActivity.class);
                Bundle b2 = ActivityOptions.makeSceneTransitionAnimation(this).toBundle();
                startActivity(i,b2);
                finish();
                break;
            case R.id.opzioni:
                i = new Intent(this, OpzioniActivity.class);
                Bundle b3 = ActivityOptions.makeSceneTransitionAnimation(this).toBundle();
                startActivity(i,b3);
                break;

        }
    }
}