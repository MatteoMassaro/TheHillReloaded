package com.example.thehillreloaded;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;

public class MusicPlayer {
    //Variabili
    public static MediaPlayer mediaPlayer;
    public static boolean isPlayingAudio=false;

    //Riproduce la musica in background
    public static void playAudio(Context c, int id){
        mediaPlayer = MediaPlayer.create(c,id);
        if(!mediaPlayer.isPlaying())
        {
            isPlayingAudio=true;
            mediaPlayer.start();
            mediaPlayer.setLooping(true);
        }
    }

    //Ferma la musica
    public static void stopAudio(){
        isPlayingAudio=false;
        mediaPlayer.stop();
    }
}
