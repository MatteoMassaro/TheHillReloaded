package com.example.thehillreloaded.gameplay;

import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Point;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.example.thehillreloaded.R;
import com.example.thehillreloaded.menu.MusicPlayer;
import com.example.thehillreloaded.menu.VolumeActivity;

public class PowerUpGameActivity extends AppCompatActivity {

    //Variabili
    private PowerUpGameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Point point = new Point();
        getWindowManager().getDefaultDisplay().getSize(point);
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        float density = displayMetrics.density;

        //Imposta l'orientamento portrait come obbligatorio
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        gameView = new PowerUpGameView(this, point.x, point.y, density);
        setContentView(gameView);

        //Fa partire la musica
        SharedPreferences preferenze = getSharedPreferences("salva1",MODE_PRIVATE);
        boolean b = preferenze.getBoolean("musica",true);
        if(b){
            MusicPlayer.playMusic(this, R.raw.game_music);
        }
        VolumeActivity.flag = 0;
    }

    @Override
    protected void onPause() {
        super.onPause();
        gameView.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        gameView.resume();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        gameView.restart();
    }
}