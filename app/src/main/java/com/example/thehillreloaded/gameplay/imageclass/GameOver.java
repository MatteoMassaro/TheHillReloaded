package com.example.thehillreloaded.gameplay.imageclass;

import static com.example.thehillreloaded.menu.MenuActivity.densityRatio;
import static com.example.thehillreloaded.menu.MenuActivity.screenRatioX;
import static com.example.thehillreloaded.menu.MenuActivity.screenRatioY;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.thehillreloaded.R;

public class GameOver extends RecImages {

    private boolean gameOver = false;

    public GameOver(int x, int y, Resources res){
        super(x, y);
        setImageBitmap(BitmapFactory.decodeResource(res, R.drawable.game_over));
        setImageBitmap2(BitmapFactory.decodeResource(res, R.drawable.redo));
        setImageBitmap3(BitmapFactory.decodeResource(res, R.drawable.exit));
        setImageBitmap4(BitmapFactory.decodeResource(res, R.drawable.trophy));
        super.setWidth((int)(getImageBitmap().getWidth() * screenRatioX * densityRatio/2));
        super.setHeight((int)(getImageBitmap().getHeight() * screenRatioY * densityRatio/2));
        setImageBitmap(Bitmap.createScaledBitmap(getImageBitmap(), getWidth(), getHeight(), true));

        super.setWidth((int)(getImageBitmap2().getWidth() * screenRatioX * densityRatio/13));
        super.setHeight((int)(getImageBitmap2().getHeight() * screenRatioX * densityRatio/13));
        setImageBitmap2(Bitmap.createScaledBitmap(getImageBitmap2(), getWidth(), getHeight(), true));
        setImageBitmap3(Bitmap.createScaledBitmap(getImageBitmap3(), getWidth(), getHeight(), true));
        setImageBitmap4(Bitmap.createScaledBitmap(getImageBitmap4(), getWidth(), getHeight(), true));

    }
}
