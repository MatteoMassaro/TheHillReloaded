package com.example.thehillreloaded;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.thehillreloaded.menu.MusicPlayer;

public class NewActivity extends AppCompatActivity {

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences preferenze = getSharedPreferences("salva1",MODE_PRIVATE);
        boolean b = preferenze.getBoolean("musica",true);
        if(b) {
            MusicPlayer.stopMusic();
            MusicPlayer.playMusic(this, R.raw.menu_music);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);

        SharedPreferences preferenze = getSharedPreferences("salva1",MODE_PRIVATE);
        boolean b = preferenze.getBoolean("musica",true);
        if(b){
            MusicPlayer.playMusic(this,R.raw.game_music);
        }
    }
}