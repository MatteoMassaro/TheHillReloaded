package com.example.thehillreloaded.animazioni;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.thehillreloaded.gameplay.GameActivity;
import com.example.thehillreloaded.menu.GiocatoreSingoloActivity;


public class AnimazioneBarraCaricamento extends Animation{

    //Variabili
    private final Context contesto;
    private final ProgressBar barraCaricamento;
    private final TextView percentuale;
    private final float da;
    private final float a;


    public AnimazioneBarraCaricamento(Context contesto, ProgressBar barraCaricamento, TextView percentuale, float da, float a){
        this.contesto = contesto;
        this.barraCaricamento = barraCaricamento;
        this.percentuale = percentuale;
        this.da = da;
        this.a = a;
    }

    //Attiva l'animazione della barra di caricamento
    @SuppressLint("SetTextI18n")
    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t){
        super.applyTransformation(interpolatedTime, t);
        float value = da + (a - da) * interpolatedTime;
        barraCaricamento.setProgress((int)value);
        percentuale.setText((int)value + " %");

        if(value == a && GiocatoreSingoloActivity.classica){
            contesto.startActivity(new Intent(contesto, GameActivity.class));

        }else if(value == a && GiocatoreSingoloActivity.powerUp){
            //game activity per modalità power-up
            contesto.startActivity(new Intent(contesto, GameActivity.class));
        }

    }
}
