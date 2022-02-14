package com.example.thehillreloaded.menu;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;

public class MusicPlayer {
    //Variabili
    public static MediaPlayer mediaPlayer, mediaPlayer1;
    public static boolean isPlayingMusic=false;
    public static boolean isPlayingEffect=false;

    //Riproduce la musica in background
    public static void playMusic(Context c, int id){
        mediaPlayer = MediaPlayer.create(c,id);
        if(!mediaPlayer.isPlaying())
        {
            isPlayingMusic=true;
            mediaPlayer.start();
            mediaPlayer.setLooping(true);
        }
    }

    //Ferma la musica
    public static void stopMusic(){
        isPlayingMusic = false;
        mediaPlayer.stop();
    }

    //Riproduce gli effetti
    public static void playEffetti(Context c, int id){
        mediaPlayer1 = MediaPlayer.create(c,id);
        if(!mediaPlayer1.isPlaying())
        {
            isPlayingEffect=true;
            mediaPlayer1.start();
        }
    }

    //Ferma l'effetto
    public static void stopEffetti(){
        isPlayingEffect = false;
        mediaPlayer1.stop();
        mediaPlayer1.release();
    }

    public static void loopEffetti(){
        mediaPlayer1.setLooping(true);
    }
}
