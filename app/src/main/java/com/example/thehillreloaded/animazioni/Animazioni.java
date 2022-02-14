package com.example.thehillreloaded.animazioni;


import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.example.thehillreloaded.R;
import com.example.thehillreloaded.menu.MusicPlayer;

public class Animazioni extends AppCompatActivity {

    //Variabili
    Animation slideIn, slideOut, scaleUp, scaleDown, scaleUpText;
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

    //Setta le animazioni per le scritte della schermata di caricamento
    public void textAnimation1(TextView testo) {
        scaleUpText = AnimationUtils.loadAnimation(this, R.anim.scale_up_text);
        scaleUpText.setStartOffset(1000);
        testo.startAnimation(scaleUpText);
    }

    public void textAnimation2(TextView testo) {
        scaleUpText = AnimationUtils.loadAnimation(this, R.anim.scale_up_text);
        testo.setVisibility(View.VISIBLE);
        testo.startAnimation(scaleUpText);
    }
}