package com.example.thehillreloaded.gameplay.imageclass;

import static com.example.thehillreloaded.gameplay.GameView.densityRatio;
import static com.example.thehillreloaded.gameplay.GameView.screenRatioX;
import static com.example.thehillreloaded.gameplay.GameView.screenRatioY;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.thehillreloaded.R;

public class Pause extends RecImages{

    public Pause(int x ,int y, Resources res){

        super(x, y);
        setImageBitmap(BitmapFactory.decodeResource(res, R.drawable.pausa));
        setImageBitmap2(BitmapFactory.decodeResource(res,R.drawable.play));
        super.setWidth((int)(getImageBitmap2().getHeight() * screenRatioX * densityRatio/13));
        super.setHeight((int)(getImageBitmap2().getWidth() * screenRatioY * densityRatio/13));
        setImageBitmap(Bitmap.createScaledBitmap(getImageBitmap(), getWidth(), getHeight(), true));
        setImageBitmap2(Bitmap.createScaledBitmap(getImageBitmap2(), getWidth(), getHeight(), true));
    }
}
