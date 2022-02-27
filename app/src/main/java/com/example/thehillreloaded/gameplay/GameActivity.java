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

public class GameActivity extends AppCompatActivity {

    //Variabili
    private GameView gameView;
    public int flagAudio;
    public int flagMusic;

    public void changeAudio(int audioFlag){
        SharedPreferences.Editor editor;
        switch (audioFlag){
            case 0:
                VolumeActivity.flagAudio = audioFlag;
                editor = getSharedPreferences("salva2",MODE_PRIVATE).edit();
                editor.putBoolean("effetti",false);
                editor.apply();
                break;
            case 1:
                VolumeActivity.flagAudio = audioFlag;
                editor = getSharedPreferences("salva2",MODE_PRIVATE).edit();
                editor.putBoolean("effetti",true);
                editor.apply();
                break;
        }
    }

    public void changeMusic(int musicFlag){
        SharedPreferences.Editor editor;
        switch (musicFlag){
            case 0:
                VolumeActivity.flagMusic = musicFlag;
                editor = getSharedPreferences("salva1",MODE_PRIVATE).edit();
                editor.putBoolean("musica",false);
                editor.apply();
                break;
            case 1:
                VolumeActivity.flagMusic = musicFlag;
                editor = getSharedPreferences("salva1",MODE_PRIVATE).edit();
                editor.putBoolean("musica",true);
                editor.apply();
                break;
        }
    }

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

        gameView = new GameView(this, point.x, point.y, density);
        setContentView(gameView);

        //Fa partire la musica
        SharedPreferences preferenze = getSharedPreferences("salva1",MODE_PRIVATE);
        boolean b = preferenze.getBoolean("musica",true);
        if(b){
            MusicPlayer.playMusic(this, R.raw.game_music);
        }
        VolumeActivity.flagMusic = 0;
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
}