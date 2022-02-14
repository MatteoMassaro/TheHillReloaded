package com.example.thehillreloaded.menu;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.thehillreloaded.R;
import com.example.thehillreloaded.animazioni.Animazioni;

import java.util.Locale;

public class LinguaActivity extends Animazioni {

    //Variabili
    public CardView italiano, inglese;
    public TextView testoItaliano, testoInglese, testoLingua;
    public ImageView indietro;


    //Chiama l'animazione all'avvio dell'activity
    @Override
    protected void onStart(){
        super.onStart();
        runAnimationSlideIn(italiano);
        runAnimationSlideIn(inglese);
    }

    //Chiama l'animazione alla pausa dell'activity
    @Override
    protected void onPause(){
        super.onPause();
        runAnimationSlideOut(italiano);
        runAnimationSlideOut(inglese);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lingua);

        //Imposta l'orientamento portrait come obbligatorio
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //Trova le view tramite l'id e le assegna alle variabili
        italiano = findViewById(R.id.italiano);
        inglese = findViewById(R.id.inglese);
        testoItaliano = findViewById(R.id.testoItaliano);
        testoInglese = findViewById(R.id.testoInglese);
        testoLingua = findViewById(R.id.testoLingua);
        indietro = findViewById(R.id.indietro);

        //Animazione pulsanti
        clickButtonAnimation(italiano);
        clickButtonAnimation(inglese);

        //Imposta la lingua italiana quando si preme il pulsante
        italiano.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String lingua = "it";
                impostaLingua(lingua);
                SharedPreferences.Editor editor = getSharedPreferences("salva3",MODE_PRIVATE).edit();
                editor.putString("lingua","it");
                editor.apply();
            }

        });

        //Imposta la lingua inglese quando si preme il pulsante
        inglese.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String lingua = "en";
                impostaLingua(lingua);
                SharedPreferences.Editor editor = getSharedPreferences("salva3",MODE_PRIVATE).edit();
                editor.putString("lingua","en");
                editor.apply();
            }

        });

        //Crea l'intent per passare all'activity successiva dopo la pressione del pulsante
        indietro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LinguaActivity.this, ImpostazioniActivity.class);
                startActivity(i);
            }
        });
    }

    public void impostaLingua(String lingua){
        Resources resources = getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        Configuration configuration = resources.getConfiguration();
        configuration.locale = new Locale(lingua);
        resources.updateConfiguration(configuration,metrics);
        onConfigurationChanged(configuration);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        testoItaliano.setText(R.string.italiano);
        testoInglese.setText(R.string.inglese);
        testoLingua.setText(R.string.lingua);

    }
}