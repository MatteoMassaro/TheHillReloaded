package com.example.thehillreloaded.gameplay.imageclass;

import static com.example.thehillreloaded.gameplay.GameView.densityRatio;
import static com.example.thehillreloaded.gameplay.GameView.screenRatioX;
import static com.example.thehillreloaded.gameplay.GameView.screenRatioY;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.thehillreloaded.R;

public class SunnyPoints extends RecImages {

    private int sunnyPoints = 0;

    public SunnyPoints(int x, int y, Resources res) {
        super(x,y);

        setImageBitmap(BitmapFactory.decodeResource(res, R.drawable.sun));
        super.setWidth((int)(getImageBitmap().getWidth() * screenRatioX * densityRatio/21.84));
        super.setHeight((int)(getImageBitmap().getHeight() * screenRatioY * densityRatio/21.84));
        setImageBitmap(Bitmap.createScaledBitmap(getImageBitmap(), getWidth(), getHeight(), true));
    }

    public int getSunnyPoints() {
        return sunnyPoints;
    }

    public void setSunnyPoints(int sunny){
        this.sunnyPoints = sunny;
    }
}