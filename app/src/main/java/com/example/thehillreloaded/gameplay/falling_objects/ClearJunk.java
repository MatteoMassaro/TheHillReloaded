package com.example.thehillreloaded.gameplay.falling_objects;

import static com.example.thehillreloaded.menu.MenuActivity.densityRatio;
import static com.example.thehillreloaded.menu.MenuActivity.screenRatioX;
import static com.example.thehillreloaded.menu.MenuActivity.screenRatioY;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.thehillreloaded.R;

public class ClearJunk extends PowerUp {

    private Bitmap clearJunk;

    public ClearJunk(int x, int y, Resources res) {
        super(x,y);
        setJunkType("clear_junk");

        clearJunk = BitmapFactory.decodeResource(res, R.drawable.clear_junk);

        super.setWidth((int) (clearJunk.getWidth() * screenRatioX * densityRatio/ 8.7));
        super.setHeight((int) (clearJunk.getHeight() * screenRatioY * densityRatio/ 8.7));

        clearJunk = Bitmap.createScaledBitmap(clearJunk, getWidth(), getHeight(), true);
    }

    @Override
    public Bitmap getFallingObject() {
        return clearJunk;
    }
}
