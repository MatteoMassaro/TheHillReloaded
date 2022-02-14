package com.example.thehillreloaded.gameplay.imageclass;

import static com.example.thehillreloaded.gameplay.GameView.screenRatioX;
import static com.example.thehillreloaded.gameplay.GameView.screenRatioY;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.thehillreloaded.R;

public class UnitPoints extends RecImages {

    public UnitPoints(int x, int y, Resources res) {
        super(x,y);

        setImageBitmap(BitmapFactory.decodeResource(res, R.drawable.unitpoints));
        super.setWidth((int)(getImageBitmap().getHeight() * screenRatioX/8));
        super.setHeight((int)(getImageBitmap().getWidth() * screenRatioY/8));
        setImageBitmap(Bitmap.createScaledBitmap(getImageBitmap(), getWidth(), getHeight(), true));
    }
}