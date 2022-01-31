package com.example.thehillreloaded.animazioni;

import android.content.Context;
import android.content.Intent;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.thehillreloaded.NewActivity;


public class AnimazioneBarraCaricamento extends Animation{

    //Variabili
    private Context contesto;
    private ProgressBar barraCaricamento;
    private TextView percentuale;
    private float da;
    private float a;


    public AnimazioneBarraCaricamento(Context contesto, ProgressBar barraCaricamento, TextView percentuale, float da, float a){
        this.contesto = contesto;
        this.barraCaricamento = barraCaricamento;
        this.percentuale = percentuale;
        this.da = da;
        this.a = a;
    }

    //Attiva l'animazione della barra di caricamento
    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t){
        super.applyTransformation(interpolatedTime, t);
        float value = da + (a - da) * interpolatedTime;
        barraCaricamento.setProgress((int)value);
        percentuale.setText((int)value + " %");

        if(value == a){
            contesto.startActivity(new Intent(contesto, NewActivity.class));

        }

    }
}
