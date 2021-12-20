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

public class GiocatoreSingoloActivity extends AppCompatActivity implements View.OnClickListener{

    //Variabili
    public CardView modalitaClassica, modalitaPowerUp;
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
        modalitaClassica.startAnimation(slideIn);
        modalitaPowerUp.startAnimation(slideIn);
        logout.startAnimation(slideIn);
        opzioni.startAnimation(slideIn);
    }

    //Setta l'animazione finale delle view
    private void runAnimationSlideOut() {
        slideOut = AnimationUtils.loadAnimation(this,R.anim.slide_out);
        modalitaClassica.startAnimation(slideOut);
        modalitaPowerUp.startAnimation(slideOut);
        logout.startAnimation(slideOut);
        opzioni.startAnimation(slideOut);
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
        logout = findViewById(R.id.logout);
        opzioni = findViewById(R.id.opzioni);

        //Imposta metodo di callback quando la view viene cliccata
        modalitaClassica.setOnClickListener(this);
        modalitaPowerUp.setOnClickListener(this);
        logout.setOnClickListener(this);
        opzioni.setOnClickListener(this);

        //Setta le animazioni per la pressione dei pulsanti
        scaleUp = AnimationUtils.loadAnimation(this,R.anim.scale_up);
        scaleDown = AnimationUtils.loadAnimation(this,R.anim.scale_down);

        //Animazione pulsante modalità classica
        modalitaClassica.setOnTouchListener((view, motionEvent) -> {
            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                modalitaClassica.startAnimation(scaleDown);
            } else if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                modalitaClassica.startAnimation(scaleUp);
            }
            return false;
        });

        //Animazione pulsante modalità power-up
        modalitaPowerUp.setOnTouchListener((view, motionEvent) -> {
            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                modalitaPowerUp.startAnimation(scaleDown);
            } else if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                modalitaPowerUp.startAnimation(scaleUp);
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
            case R.id.modalitaClassica:
                i = new Intent(this, NewActivity.class);
                Bundle b = ActivityOptions.makeSceneTransitionAnimation(this).toBundle();
                startActivity(i, b);
                break;
            case R.id.modalitaPowerUp:
                i = new Intent(this, NewActivity.class);
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