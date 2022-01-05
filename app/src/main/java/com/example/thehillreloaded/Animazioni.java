package com.example.thehillreloaded;

import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

public class Animazioni extends AppCompatActivity {

    Animation slideIn, slideOut;

    public void clickButtonAnimation(View button) {

        //Definizione tipi di animazione
        Animation scaleUp = AnimationUtils.loadAnimation(this, R.anim.scale_up);
        Animation scaleDown = AnimationUtils.loadAnimation(this, R.anim.scale_down);

        //Setta le animazioni per la pressione dei pulsanti
        button.setOnTouchListener((view, motionEvent) -> {
            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                button.startAnimation(scaleDown);
            } else if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                button.startAnimation(scaleUp);
            }
            return false;
        });
    }

    public Animation getSlideInAnimation() {
        return slideIn;
    }

    public Animation getSlideOutAnimation() {
        return slideOut;
    }
}
