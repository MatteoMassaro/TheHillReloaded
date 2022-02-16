package com.example.thehillreloaded.gameplay.imageclass;

import static com.example.thehillreloaded.gameplay.GameView.screenRatioX;
import static com.example.thehillreloaded.gameplay.GameView.screenRatioY;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.thehillreloaded.R;

public class AluminumInfo extends InfoImages {

    public AluminumInfo(int x, int y, Resources res) {
        super(x,y);

        setImageBitmap(BitmapFactory.decodeResource(res, R.drawable.aluminumunit));
        setUpgradedImageBitmap(BitmapFactory.decodeResource(res, R.drawable.aluminumunit_upgraded));
        setMaterial_lvl1(BitmapFactory.decodeResource(res, R.drawable.cola_can));
        setMaterial_lvl2(BitmapFactory.decodeResource(res, R.drawable.soup_can));
        setMaterial_lvl3(BitmapFactory.decodeResource(res, R.drawable.spray));

        super.setWidth((int)(getImageBitmap().getWidth() * screenRatioX/8.19));
        super.setHeight((int)(getImageBitmap().getHeight() * screenRatioY/8.19));
        setImageBitmap(Bitmap.createScaledBitmap(getImageBitmap(), getWidth(), getHeight(), true));
        setUpgradedImageBitmap(Bitmap.createScaledBitmap(getUpgradedImageBitmap(), getWidth(), getHeight(), true));

        super.setWidth((int)(getMaterial_lvl1().getWidth() * screenRatioX / 2.75));
        super.setHeight((int)(getMaterial_lvl1().getHeight() * screenRatioY / 2.75));
        setMaterial_lvl1(Bitmap.createScaledBitmap(getMaterial_lvl1(), (getWidth()), (getHeight()), true));
        setMaterial_lvl2(Bitmap.createScaledBitmap(getMaterial_lvl2(), (getWidth()), (getHeight()), true));
        setMaterial_lvl3(Bitmap.createScaledBitmap(getMaterial_lvl3(), (getWidth()), (getHeight()), true));
    }

}