package com.example.thehillreloaded.gameplay.imageclass;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class InfoImages extends RecImages {

    private Bitmap upgradedImageBitmap, material_lvl1, material_lvl2, material_lvl3;
    private int sunnyPointsLvl1 = 1, sunnyPointsLvl2 = 3, sunnyPointsLvl3 = 6;
    private int unitPointsLvl1 = 2, unitPointsLvl2 = 4, unitPointsLvl3 = 7;
    private boolean isCheckingMaterialLvl1Info = false, isCheckingMaterialLvl2Info = false,
            isCheckingMaterialLvl3Info = false;

    InfoImages(int x, int y) {
        super(x,y);

    }

    public Bitmap getMaterial_lvl1() {
        return material_lvl1;
    }

    public void setMaterial_lvl1(Bitmap bitmap) {
        material_lvl1 = bitmap;
    }

    public Bitmap getMaterial_lvl2() {
        return material_lvl2;
    }

    public void setMaterial_lvl2(Bitmap bitmap) {
        material_lvl2 = bitmap;
    }

    public Bitmap getMaterial_lvl3() {
        return material_lvl3;
    }

    public void setMaterial_lvl3(Bitmap bitmap) {
        material_lvl3 = bitmap;
    }

    public int getSunnyPoints(int i) {
        if(i == 0) {
            return sunnyPointsLvl1;

        } else if (i == 1) {
            return sunnyPointsLvl2;

        } else {
            return sunnyPointsLvl3;
        }
    }

    public int getUnitPoints(int i) {
        if(i == 0) {
            return unitPointsLvl1;

        } else if (i == 1) {
            return unitPointsLvl2;

        } else {
            return unitPointsLvl3;
        }
    }

    public void setIsCheckingMaterialLvl1Info(boolean isTrue) {
        isCheckingMaterialLvl1Info = isTrue;
    }

    public void setIsCheckingMaterialLvl2Info(boolean isTrue) {
        isCheckingMaterialLvl2Info = isTrue;
    }

    public void setIsCheckingMaterialLvl3Info(boolean isTrue) {
        isCheckingMaterialLvl3Info = isTrue;
    }

    public boolean getIsCheckingMaterialLvl1Info() {
        return isCheckingMaterialLvl1Info;
    }

    public boolean getIsCheckingMaterialLvl2Info() {
        return isCheckingMaterialLvl2Info;
    }

    public boolean getIsCheckingMaterialLvl3Info() {
        return isCheckingMaterialLvl3Info;
    }

    public void drawMaterialLvl1Text(int x, int y, Paint paint, Canvas canvas) {
    }

    public void drawMaterialLvl2Text(int x, int y, Paint paint, Canvas canvas) {
    }

    public void drawMaterialLvl3Text(int x, int y, Paint paint, Canvas canvas) {
    }



    public Bitmap getUpgradedImageBitmap() {
        return upgradedImageBitmap;
    }

    public void setUpgradedImageBitmap(Bitmap bitmap) {
        upgradedImageBitmap = bitmap;
    }
}
