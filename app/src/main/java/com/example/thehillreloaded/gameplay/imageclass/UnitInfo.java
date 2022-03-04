package com.example.thehillreloaded.gameplay.imageclass;

import static com.example.thehillreloaded.menu.MenuActivity.densityRatio;
import static com.example.thehillreloaded.menu.MenuActivity.screenRatioX;
import static com.example.thehillreloaded.menu.MenuActivity.screenRatioY;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.thehillreloaded.R;

public class UnitInfo extends RecImages {

    public UnitInfo(int x, int y, Resources res) {
        super(x,y);

        setImageBitmap(BitmapFactory.decodeResource(res, R.drawable.info_rectangle));
        super.setWidth((int)(getImageBitmap().getWidth() * screenRatioX  * densityRatio/1.297));
        super.setHeight((int)(getImageBitmap().getHeight() * screenRatioY * densityRatio *1.47));
        setImageBitmap(Bitmap.createScaledBitmap(getImageBitmap(), getWidth(), getHeight(), true));
    }
}
