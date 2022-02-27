package com.example.thehillreloaded.menu;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Switch;

import com.example.thehillreloaded.R;


public class VolumeActivity extends AppCompatActivity {
    //Variabili
    public CardView musica, effetti;
    public Switch switchMusica, switchEffetti;
    public ImageView indietro;
    Animation slideIn, slideOut, scaleUp, scaleDown;
    public static int flagAudio = 0;
    public static int flagMusic = 0;
    Handler h = new Handler();

    //Setta l'animazione iniziale delle view
    protected void runAnimationSlideIn(CardView button) {
        button.startAnimation(slideIn);
        button.startAnimation(slideIn);
    }

    //Setta l'animazione finale delle view
    protected void runAnimationSlideOut(CardView button) {
        button.startAnimation(slideOut);
        button.startAnimation(slideOut);
    }

    @SuppressLint("ClickableViewAccessibility")
    public void clickButtonAnimation(View button) {


        //Assegnazione tipi di animazione
        scaleUp = AnimationUtils.loadAnimation(this, R.anim.scale_up);
        scaleDown = AnimationUtils.loadAnimation(this, R.anim.scale_down);
        slideIn = AnimationUtils.loadAnimation(this, R.anim.slide_in);
        slideOut = AnimationUtils.loadAnimation(this, R.anim.slide_out);

        //Setta le animazioni per la pressione dei pulsanti
        button.setOnTouchListener((view, motionEvent) -> {
            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                button.startAnimation(scaleDown);
                SharedPreferences preferenze = getSharedPreferences("salva2",MODE_PRIVATE);
                boolean b = preferenze.getBoolean("effetti",true);
                if(b) {
                    MusicPlayer.playEffetti(this,R.raw.tap_sound);
                    h.postDelayed(() -> {
                        MusicPlayer.stopEffetti();
                    },200);
                }
            } else if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                button.startAnimation(scaleUp);
            }
            return false;
        });
    }

    //Chiama l'animazione all'avvio dell'activity
    @Override
    protected void onStart(){
        super.onStart();
        runAnimationSlideIn(musica);
        runAnimationSlideIn(effetti);
    }

    //Chiama l'animazione alla pausa dell'activity
    @Override
    protected void onPause(){
        super.onPause();
        runAnimationSlideOut(musica);
        runAnimationSlideOut(effetti);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volume);

        //Trova le view tramite l'id e le assegna alle variabili
        switchMusica = findViewById(R.id.switchMusica);
        switchEffetti = findViewById(R.id.switchEffetti);
        musica = findViewById(R.id.musica);
        effetti = findViewById(R.id.effetti);
        indietro = findViewById(R.id.indietro);

        //Animazione pulsante
        clickButtonAnimation(indietro);

        //Salva lo stato degli switch
        SharedPreferences preferenze1 = getSharedPreferences("salva1",MODE_PRIVATE);
        SharedPreferences preferenze2 = getSharedPreferences("salva2",MODE_PRIVATE);
        switchMusica.setChecked(preferenze1.getBoolean("musica",true));
        switchEffetti.setChecked(preferenze2.getBoolean("effetti",true));

        //Riproduce e ferma la musica in base allo stato dello switch
        switchMusica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(switchMusica.isChecked()){
                    SharedPreferences.Editor editor = getSharedPreferences("salva1",MODE_PRIVATE).edit();
                    editor.putBoolean("musica",true);
                    editor.apply();
                    switchMusica.setChecked(true);
                    flagMusic = 1;
                    MusicPlayer.playMusic(VolumeActivity.this,R.raw.menu_music);
                }else {
                    SharedPreferences.Editor editor = getSharedPreferences("salva1",MODE_PRIVATE).edit();
                    editor.putBoolean("musica",false);
                    editor.apply();
                    switchMusica.setChecked(false);
                    flagMusic = 0;
                    MusicPlayer.stopMusic();
                }
            }
        });

        //Riproduce e ferma l'audio degli effetti in base allo stato dello switch (da implementare)
        switchEffetti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(switchEffetti.isChecked()){
                    SharedPreferences.Editor editor = getSharedPreferences("salva2",MODE_PRIVATE).edit();
                    editor.putBoolean("effetti",true);
                    editor.apply();
                }else {
                    SharedPreferences.Editor editor = getSharedPreferences("salva2",MODE_PRIVATE).edit();
                    editor.putBoolean("effetti",false);
                    editor.apply();
                }
            }
        });

        //Crea l'intent per passare all'activity successiva dopo la pressione del pulsante
        indietro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(VolumeActivity.this, ImpostazioniActivity.class);
                startActivity(i);
            }
        });
    }
}