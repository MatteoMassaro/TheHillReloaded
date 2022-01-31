package com.example.thehillreloaded.animazioni;

import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.thehillreloaded.R;

public class SchermataCaricamentoActivity extends AnimazioniView {

    ProgressBar barraCaricamento;
    TextView percentuale, infoCaricamento1, infoCaricamento2;

    @Override
    public void onBackPressed() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schermata_caricamento);

        Handler h = new Handler();

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Trova le view tramite l'id e le assegna alle variabili
        barraCaricamento = findViewById(R.id.barra_caricamento);
        percentuale = findViewById(R.id.percentuale);
        infoCaricamento1 = findViewById(R.id.info_caricamento1);
        infoCaricamento2 = findViewById(R.id.info_caricamento2);

        barraCaricamento.setMax(100);
        barraCaricamento.setScaleY(3f);

        textAnimation1(infoCaricamento1);
        h.postDelayed(() -> {
            textAnimation2(infoCaricamento2);
        },5000);
        animazioneCaricamento();
    }

    public void animazioneCaricamento(){
        AnimazioneBarraCaricamento anim = new AnimazioneBarraCaricamento(this, barraCaricamento, percentuale, 0, 100);
        anim.setDuration(8000);
        barraCaricamento.setAnimation(anim);
    }
}