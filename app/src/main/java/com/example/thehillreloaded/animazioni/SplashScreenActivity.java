package com.example.thehillreloaded.animazioni;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.thehillreloaded.R;
import com.example.thehillreloaded.accesso.ModalitaAccessoActivity;

public class SplashScreenActivity extends AppCompatActivity {
    Handler h = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

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