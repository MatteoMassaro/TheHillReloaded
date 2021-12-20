package com.example.thehillreloaded;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

public class ModalitaAccessoActivity extends AppCompatActivity implements View.OnClickListener{

    //Variabili
    public CardView account, ospite;
    public Animation scaleUp, scaleDown;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modalita_accesso);

        //Imposta l'orientamento portrait come obbligatorio
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //Trova le view tramite l'id e le assegna alle variabili
        account = findViewById(R.id.account);
        ospite = findViewById(R.id.ospite);

        //Imposta metodo di callback quando la view viene cliccata
        account.setOnClickListener(this);
        ospite.setOnClickListener(this);

        //Setta le animazioni per la pressione dei pulsanti
        scaleUp = AnimationUtils.loadAnimation(this,R.anim.scale_up);
        scaleDown = AnimationUtils.loadAnimation(this,R.anim.scale_down);

        //Animazione pulsante account
        account.setOnTouchListener((view, motionEvent) -> {
            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                account.startAnimation(scaleDown);
            } else if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                account.startAnimation(scaleUp);
            }
            return false;
        });

        //Animazione pulsante ospite
        ospite.setOnTouchListener((view, motionEvent) -> {
            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                ospite.startAnimation(scaleDown);
            } else if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                ospite.startAnimation(scaleUp);
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
            case R.id.account:
                i = new Intent(this, RegistrazioneActivity.class);
                startActivity(i);
                break;
            case R.id.ospite:
                i = new Intent(this, MenuActivity.class);
                startActivity(i);
                break;

        }
    }
}