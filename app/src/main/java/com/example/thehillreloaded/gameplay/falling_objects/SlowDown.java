package com.example.thehillreloaded.gameplay.falling_objects;

import static com.example.thehillreloaded.menu.MenuActivity.densityRatio;
import static com.example.thehillreloaded.menu.MenuActivity.screenRatioX;
import static com.example.thehillreloaded.menu.MenuActivity.screenRatioY;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.thehillreloaded.R;

public class SlowDown extends PowerUp {

    private static int currentDuration = 0, duration = 1500, scaleDuration = 10;
    private static boolean isPoweredUp = false;
    private Bitmap slowDown;

    public SlowDown(int x, int y, Resources res) {
        super(x,y);
        setJunkType("slow_down");

        slowDown = BitmapFactory.decodeResource(res, R.drawable.slowdown);

        super.setWidth((int) (slowDown.getWidth() * screenRatioX * densityRatio/9));
        super.setHeight((int) (slowDown.getHeight() * screenRatioY * densityRatio/9));

        slowDown = Bitmap.createScaledBitmap(slowDown, getWidth(), getHeight(), true);
    }

    @Override
    public void applyPowerUp() {
        Junk.setSpeed(Junk.getSpeed()/2);
        Junk.setSpeedIncrease(Junk.getSpeedIncrease()/2);
        isPoweredUp = true;
    }

    public static void checkIfPowerIsUp() {
        increaseCurrentDuration();
        powerUpHasEnded();
    }

    private static void increaseCurrentDuration() {
        if (isPoweredUp) {
            currentDuration += scaleDuration;
        }
    }

    private static void powerUpHasEnded() {
        if (currentDuration >= duration) {
            isPoweredUp = false;
            adjustValues();
        }
    }

    private static void adjustValues() {
        currentDuration = 0;
        Junk.setSpeed(Junk.getSpeed()*2);
        Junk.setSpeedIncrease(Junk.getSpeedIncrease()*2);
    }

    public static void resetValues() {
        duration = 1500;
        currentDuration = 0;
        isPoweredUp = false;
    }

    public static int getCurrentDuration() {
        return currentDuration;
    }

    public static void setCurrentDuration(int new_currentDuration) {
        currentDuration = new_currentDuration;
    }

    public static boolean getIsPoweredUp() {
        return isPoweredUp;
    }

    public static void setIsPoweredUp(boolean new_isPoweredUp) {
        isPoweredUp = new_isPoweredUp;
    }

    public static int getDuration() {
        return duration;
    }

    public static void setDuration(int newDuration) {
        duration = newDuration;
    }

    @Override
    public Bitmap getFallingObject() {
        return slowDown;
    }
}
