package com.example.thehillreloaded.gameplay.imageclass;

import static com.example.thehillreloaded.gameplay.GameView.densityRatio;
import static com.example.thehillreloaded.gameplay.GameView.screenRatioX;
import static com.example.thehillreloaded.gameplay.GameView.screenRatioY;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.thehillreloaded.R;

import java.util.ArrayList;

public class GameBar {

    private int x, y, width, height;
    private Bitmap elencoMissioni, elencoPause, saveIcon, exitIcon, audioIcon, musicIcon, audioIconRed, musicIconRed;
    private ArrayList<String> missioni;

    public GameBar(int screenX, int screenY, Resources res) {

        this.x = screenX;
        this.y = screenY;
        setElencoMissioni(BitmapFactory.decodeResource(res, R.drawable.pausarect));
        setAudioIcon(BitmapFactory.decodeResource(res, R.drawable.audio));
        setMusicIcon(BitmapFactory.decodeResource(res, R.drawable.music));
        //setAudioIconRed(BitmapFactory.decodeResource(res, R.drawable.audiored));
        //setAudioIconRed(BitmapFactory.decodeResource(res, R.drawable.musicred));

        this.setWidth((int)(getElencoMissioni().getWidth() * screenRatioX * densityRatio /30));
        this.setHeight((int)(getElencoMissioni().getHeight() * screenRatioY * densityRatio /30));
        this.setWidth((int)(getAudioIcon().getWidth() * screenRatioX * densityRatio /30));
        this.setHeight((int)(getAudioIcon().getHeight() * screenRatioY * densityRatio /30));
        this.setWidth((int)(getMusicIcon().getWidth() * screenRatioX * densityRatio /30));
        this.setHeight((int)(getMusicIcon().getHeight() * screenRatioY * densityRatio /30));
        //this.setWidth((int)(getAudioIconRed().getWidth() * screenRatioX * densityRatio /30));
        //this.setHeight((int)(getAudioIconRed().getHeight() * screenRatioY * densityRatio /30));
        //this.setWidth((int)(getMusicIconRed().getWidth() * screenRatioX * densityRatio /30));
        //this.setHeight((int)(getMusicIconRed().getHeight() * screenRatioY * densityRatio /30));
        setElencoMissioni(Bitmap.createScaledBitmap(getElencoMissioni(), screenX, screenY, true));
        setAudioIcon(Bitmap.createScaledBitmap(getAudioIcon(), getWidth() *3, getHeight()*3, true));
        setMusicIcon(Bitmap.createScaledBitmap(getMusicIcon(), getWidth() *3, getHeight()*3, true));
        //setAudioIconRed(Bitmap.createScaledBitmap(getAudioIconRed(), getWidth() *3, getHeight()*3, true));
        //setMusicIconRed(Bitmap.createScaledBitmap(getMusicIconRed(), getWidth() *3, getHeight()*3, true));
        missioni = new ArrayList<>();
    }
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Bitmap getElencoMissioni(){
        return elencoMissioni;
    }

    public void setElencoMissioni(Bitmap elencoMissioni){ this.elencoMissioni = elencoMissioni;}

    public void setMissioni(){
        missioni.add("Costruisci la stazione della carta");
    }

    public String getMissioni(int a){
        return missioni.get(a);
    }

    public int getNumeroMissioni(){
        return missioni.size();
    }

    public Bitmap getAudioIcon() {
        return audioIcon;
    }

    public void setAudioIcon(Bitmap audioIcon) {
        this.audioIcon = audioIcon;
    }

    public Bitmap getMusicIcon() {
        return musicIcon;
    }

    public void setMusicIcon(Bitmap musicIcon) {
        this.musicIcon = musicIcon;
    }

    public Bitmap getAudioIconRed() {
        return audioIconRed;
    }

    public void setAudioIconRed(Bitmap audioIconRed) {
        this.audioIconRed = audioIconRed;
    }

    public Bitmap getMusicIconRed() {
        return musicIconRed;
    }

    public void setMusicIconRed(Bitmap musicIconRed) {
        this.musicIconRed = musicIconRed;
    }
}
