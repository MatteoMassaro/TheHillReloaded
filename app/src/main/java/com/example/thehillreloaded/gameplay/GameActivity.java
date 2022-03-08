package com.example.thehillreloaded.gameplay;

import static com.example.thehillreloaded.menu.GiocatoreSingoloActivity.b;
import static com.example.thehillreloaded.menu.GiocatoreSingoloActivity.b1;

import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.example.thehillreloaded.R;
import com.example.thehillreloaded.menu.MusicPlayer;
import com.example.thehillreloaded.menu.VolumeActivity;

public class GameActivity extends AppCompatActivity {

    public void changeAudio(int audioFlag){
        SharedPreferences.Editor editor;
        switch (audioFlag){
            case 0:
                VolumeActivity.flagAudio = audioFlag;
                editor = getSharedPreferences("salva2",MODE_PRIVATE).edit();
                editor.putBoolean("effetti",true);
                editor.apply();
                break;
            case 1:
                VolumeActivity.flagAudio = audioFlag;
                editor = getSharedPreferences("salva2",MODE_PRIVATE).edit();
                editor.putBoolean("effetti",false);
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
                editor.putBoolean("musica",true);
                editor.apply();
                break;
            case 1:
                VolumeActivity.flagMusic = musicFlag;
                editor = getSharedPreferences("salva1",MODE_PRIVATE).edit();
                editor.putBoolean("musica",false);
                editor.apply();
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Imposta l'orientamento portrait come obbligatorio
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        SharedPreferences preferenze = getSharedPreferences("salva1", MODE_PRIVATE);
        b = preferenze.getBoolean("musica", true);
        if (b) {
            MusicPlayer.playMusic(this, R.raw.game_music);
        }
        VolumeActivity.flagMusic = 0;
        SharedPreferences preferenze1 = getSharedPreferences("salva2", MODE_PRIVATE);
        b1 = preferenze1.getBoolean("effetti", true);
        if (b1){
            changeAudio(0);
        }else {
            changeAudio(1);
        }
        preferences();
    }

    //Fa partire la musica
    public void preferences() {
        SharedPreferences preferenze = getSharedPreferences("salva1", MODE_PRIVATE);
        b = preferenze.getBoolean("musica", true);
        SharedPreferences preferenze1 = getSharedPreferences("salva2", MODE_PRIVATE);
        b1 = preferenze1.getBoolean("effetti", true);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
