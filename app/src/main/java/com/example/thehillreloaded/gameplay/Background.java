package com.example.thehillreloaded.gameplay;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.thehillreloaded.R;

public class Background {

    private int x = 0, y = 0;
    Bitmap background;
    Bitmap spawnZone;
    Bitmap greenRect;

    //Crea il background di gioco e la zona di spawn
    public Background(int screenX, int screenY, Resources res) {
        background = BitmapFactory.decodeResource(res, R.drawable.background_grass);
        background = Bitmap.createScaledBitmap(background, screenX, screenY, false);
        spawnZone = BitmapFactory.decodeResource(res, R.drawable.imagespawn);
        spawnZone = Bitmap.createScaledBitmap(spawnZone, screenX, screenY * 5/11, false);
        greenRect = BitmapFactory.decodeResource(res, R.drawable.greenrect);
        greenRect = Bitmap.createScaledBitmap(greenRect, screenX, screenY * 1/15, false);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
