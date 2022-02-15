package com.example.thehillreloaded.gameplay.imageclass;

import static com.example.thehillreloaded.gameplay.GameView.screenRatioX;
import static com.example.thehillreloaded.gameplay.GameView.screenRatioY;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.thehillreloaded.R;

public class UnlockableUnit extends RecImages {

    public UnlockableUnit(int x, int y, Resources res) {
        super(x,y);

        setImageBitmap(BitmapFactory.decodeResource(res, R.drawable.upgradable));
        super.setWidth((int)(getImageBitmap().getWidth() * screenRatioX/5.5));
        super.setHeight((int)(getImageBitmap().getHeight() * screenRatioY/5.5));
        setImageBitmap(Bitmap.createScaledBitmap(getImageBitmap(), getWidth(), getHeight(), true));
    }
}
