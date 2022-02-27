package com.example.thehillreloaded.gameplay.imageclass;

import static com.example.thehillreloaded.gameplay.GameView.densityRatio;
import static com.example.thehillreloaded.gameplay.GameView.screenRatioX;
import static com.example.thehillreloaded.gameplay.GameView.screenRatioY;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.thehillreloaded.R;

public class GameBar {

    private int x, y, width, height;
    private Bitmap pausaRect, missioniRect, elencoPause, saveIcon, exitIcon, audioIcon, musicIcon, audioIconRed, musicIconRed;
    private int score;
    private boolean musicClicked = true;
    private boolean audioClicked = true;

    public GameBar(int screenX, int screenY, Resources res) {

        this.x = screenX;
        this.y = screenY;
        setPausaRect(BitmapFactory.decodeResource(res, R.drawable.pausa_rect));
        setMissioniRect(BitmapFactory.decodeResource(res, R.drawable.missioni_rect));
        setAudioIcon(BitmapFactory.decodeResource(res, R.drawable.audio));
        setMusicIcon(BitmapFactory.decodeResource(res, R.drawable.music));
        setSaveIcon(BitmapFactory.decodeResource(res, R.drawable.save));
        setExitIcon(BitmapFactory.decodeResource(res, R.drawable.exit));
        setAudioIconRed(BitmapFactory.decodeResource(res, R.drawable.audiored));
        setMusicIconRed(BitmapFactory.decodeResource(res, R.drawable.musicred));

        this.setWidth((int)(getPausaRect().getWidth() * screenRatioX * densityRatio /30));
        this.setHeight((int)(getPausaRect().getHeight() * screenRatioY * densityRatio /30));
        this.setWidth((int)(getAudioIcon().getWidth() * screenRatioX * densityRatio /30));
        this.setHeight((int)(getAudioIcon().getHeight() * screenRatioY * densityRatio /30));
        this.setWidth((int)(getMusicIcon().getWidth() * screenRatioX * densityRatio /30));
        this.setHeight((int)(getMusicIcon().getHeight() * screenRatioY * densityRatio /30));
        this.setWidth((int)(getSaveIcon().getWidth() * screenRatioX * densityRatio /30));
        this.setHeight((int)(getSaveIcon().getHeight() * screenRatioY * densityRatio /30));
        this.setWidth((int)(getExitIcon().getWidth() * screenRatioX * densityRatio /30));
        this.setHeight((int)(getExitIcon().getHeight() * screenRatioY * densityRatio /30));
        this.setWidth((int)(getAudioIconRed().getWidth() * screenRatioX * densityRatio /30));
        this.setHeight((int)(getAudioIconRed().getHeight() * screenRatioY * densityRatio /30));
        this.setWidth((int)(getMusicIconRed().getWidth() * screenRatioX * densityRatio /30));
        this.setHeight((int)(getMusicIconRed().getHeight() * screenRatioY * densityRatio /30));
        setPausaRect(Bitmap.createScaledBitmap(getPausaRect(), screenX, screenY*4/5, true));
        setMissioniRect(Bitmap.createScaledBitmap(getMissioniRect(), screenX, screenY*4/5, true));
        setAudioIcon(Bitmap.createScaledBitmap(getAudioIcon(), getWidth() *3, getHeight()*3, true));
        setMusicIcon(Bitmap.createScaledBitmap(getMusicIcon(), getWidth() *3, getHeight()*3, true));
        setSaveIcon(Bitmap.createScaledBitmap(getSaveIcon(), getWidth() *3, getHeight()*3, true));
        setExitIcon(Bitmap.createScaledBitmap(getExitIcon(), getWidth() *3, getHeight()*3, true));
        setAudioIconRed(Bitmap.createScaledBitmap(getAudioIconRed(), getWidth() *3, getHeight()*3, true));
        setMusicIconRed(Bitmap.createScaledBitmap(getMusicIconRed(), getWidth() *3, getHeight()*3, true));
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

    public Bitmap getMissioniRect(){
        return missioniRect;
    }

    public void setMissioniRect(Bitmap missioniRect){ this.missioniRect= missioniRect;}

    public Bitmap getPausaRect(){
        return pausaRect;
    }

    public void setPausaRect(Bitmap pausaRec){ this.pausaRect= pausaRec;}

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

    public Bitmap getSaveIcon() {
        return saveIcon;
    }

    public void setSaveIcon(Bitmap saveIcon) {
        this.saveIcon = saveIcon;
    }

    public Bitmap getExitIcon() {
        return exitIcon;
    }

    public void setExitIcon(Bitmap exitIcon) {
        this.exitIcon = exitIcon;
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

    public void increaseScore(int score) {
        this.score += score;
    }

    public int getScore() {
        return score;
    }

    public boolean isMusicClicked() {
        return musicClicked;
    }

    public void setMusicClicked(boolean musicClicked) {
        this.musicClicked = musicClicked;
    }

    public boolean isAudioClicked() {
        return audioClicked;
    }

    public void setAudioClicked(boolean audioClicked) {
        this.audioClicked = audioClicked;
    }
}
