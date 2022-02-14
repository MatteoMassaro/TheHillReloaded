package com.example.thehillreloaded.accesso;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.thehillreloaded.menu.MusicPlayer;
import com.example.thehillreloaded.menu.MenuActivity;
import com.example.thehillreloaded.R;
import com.example.thehillreloaded.animazioni.Animazioni;
import com.example.thehillreloaded.menu.VolumeActivity;

public class ModalitaAccessoActivity extends Animazioni implements View.OnClickListener{

    //Variabili
    public CardView account, ospite;
    public Animation slideIn, slideOut;
    public static boolean isPlayingAudio = false;

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
        slideIn = AnimationUtils.loadAnimation(this, R.anim.slide_in);
        account.startAnimation(slideIn);
        ospite.startAnimation(slideIn);
    }

    //Setta l'animazione finale delle view
    private void runAnimationSlideOut() {
        slideOut = AnimationUtils.loadAnimation(this,R.anim.slide_out);
        account.startAnimation(slideOut);
        ospite.startAnimation(slideOut);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modalita_accesso);

        SharedPreferences preferenze = getSharedPreferences("salva1",MODE_PRIVATE);
        boolean b = preferenze.getBoolean("musica",true);
        if(b) {
            if (VolumeActivity.flag == 0) {
                MusicPlayer.playMusic(this, R.raw.menu_music);
                isPlayingAudio = true;
                VolumeActivity.flag = 1;
            }
        }

        //Imposta l'orientamento portrait come obbligatorio
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //Trova le view tramite l'id e le assegna alle variabili
        account = findViewById(R.id.account);
        ospite = findViewById(R.id.ospite);

        //Imposta metodo di callback quando la view viene cliccata
        account.setOnClickListener(this);
        ospite.setOnClickListener(this);

        //Animazione pulsanti
        clickButtonAnimation(account);
        clickButtonAnimation(ospite);
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