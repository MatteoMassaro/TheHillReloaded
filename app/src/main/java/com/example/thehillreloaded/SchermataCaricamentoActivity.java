package com.example.thehillreloaded;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

public class SchermataCaricamentoActivity extends AppCompatActivity {

    ProgressBar barraCaricamento;
    TextView percentuale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schermata_caricamento);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        barraCaricamento = findViewById(R.id.barra_caricamento);
        percentuale = findViewById(R.id.percentuale);

        barraCaricamento.setMax(100);
        barraCaricamento.setScaleY(3f);

        animazioneCaricamento();
    }

    public void animazioneCaricamento(){
        AnimazioneBarraCaricamento anim = new AnimazioneBarraCaricamento(this, barraCaricamento, percentuale, 0, 100);
        anim.setDuration(8000);
        barraCaricamento.setAnimation(anim);
    }
}