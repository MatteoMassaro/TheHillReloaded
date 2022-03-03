package com.example.thehillreloaded.gameplay.falling_objects;

import static com.example.thehillreloaded.menu.MenuActivity.densityRatio;
import static com.example.thehillreloaded.menu.MenuActivity.screenRatioX;
import static com.example.thehillreloaded.menu.MenuActivity.screenRatioY;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.thehillreloaded.R;

public class SunnyPow extends PowerUp {

    private Bitmap sunnyPow;

    public SunnyPow(int x, int y, Resources res) {
        super(x,y);

        sunnyPow = BitmapFactory.decodeResource(res, R.drawable.sun);

        super.setWidth((int) (sunnyPow.getWidth() * screenRatioX * densityRatio/ 10));
        super.setHeight((int) (sunnyPow.getHeight() * screenRatioY * densityRatio/ 10));

        sunnyPow = Bitmap.createScaledBitmap(sunnyPow, getWidth(), getHeight(), true);
    }

    @Override
    public Bitmap getFallingObject() {
        return sunnyPow;
    }
}
