package com.example.thehillreloaded.animazioni;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;

import com.example.thehillreloaded.R;
import com.example.thehillreloaded.accesso.ModalitaAccessoActivity;

import java.util.Locale;

public class SplashScreenActivity extends AppCompatActivity {

    Handler h = new Handler();

    public void impostaLingua(String lingua){
        Resources resources = getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        Configuration configuration = resources.getConfiguration();
        configuration.locale = new Locale(lingua);
        resources.updateConfiguration(configuration,metrics);
        onConfigurationChanged(configuration);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        //Imposta la lingua selezionata
        SharedPreferences preferenzeLingua = getSharedPreferences("salva3",MODE_PRIVATE);
        String lingua = preferenzeLingua.getString("lingua","it");
        impostaLingua(lingua);

        //Imposta l'orientamento portrait come obbligatorio
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        //Crea un intent per passare all'activity successiva dopo 3 secondi
        h.postDelayed(() -> {
            Intent i = new Intent(SplashScreenActivity.this, ModalitaAccessoActivity.class );
            startActivity(i);
            finish();
        },5000);
    }
}