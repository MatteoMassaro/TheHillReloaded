package com.example.thehillreloaded.gameplay.falling_objects;

import static com.example.thehillreloaded.menu.MenuActivity.densityRatio;
import static com.example.thehillreloaded.menu.MenuActivity.screenRatioX;
import static com.example.thehillreloaded.menu.MenuActivity.screenRatioY;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.thehillreloaded.R;
import com.example.thehillreloaded.gameplay.recycle.RecUnit;

public class SpeedUp extends PowerUp {

    private static int currentDuration = 0, duration = 1500, scaleDuration = 10;
    private static boolean isPoweredUp = false;
    private Bitmap speedUp;

    public SpeedUp(int x, int y, Resources res) {
        super(x,y);

        speedUp = BitmapFactory.decodeResource(res, R.drawable.speedup);

        super.setWidth((int) (speedUp.getWidth() * screenRatioX * densityRatio/ 7));
        super.setHeight((int) (speedUp.getHeight() * screenRatioY * densityRatio/ 7));

        speedUp = Bitmap.createScaledBitmap(speedUp, getWidth(), getHeight(), true);
    }

    @Override
    public void applyPowerUp() {
        RecUnit.setRecyclingSpeed(RecUnit.getRecyclingSpeed()*2);
        isPoweredUp = true;
    }

    public static void checkPowerIsUp() {
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
            resetValues();
        }
    }

    private static void resetValues() {
        currentDuration = 0;
        RecUnit.setRecyclingSpeed(RecUnit.getRecyclingSpeed()/2);
    }

    public static void resetDuration() {
        duration = 1500;
    }

    public static int getDuration() {
        return duration;
    }

    public static void setDuration(int newDuration) {
        duration = newDuration;
    }

    @Override
    public Bitmap getFallingObject() {
        return speedUp;
    }
}
