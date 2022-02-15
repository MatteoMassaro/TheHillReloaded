package com.example.thehillreloaded.gameplay.imageclass;

import android.content.res.Resources;
import android.graphics.Bitmap;

public class InfoImages extends RecImages {

    private Bitmap upgradedImageBitmap, material_lvl1, material_lvl2, material_lvl3;
    private int sunnyPointsLvl1 = 1, sunnyPointsLvl2 = 3, sunnyPointsLvl3 = 6;
    private int unitPointsLvl1 = 2, unitPointsLvl2 = 4, unitPointsLvl3 = 7;

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

    public Bitmap getUpgradedImageBitmap() {
        return upgradedImageBitmap;
    }

    public void setUpgradedImageBitmap(Bitmap bitmap) {
        upgradedImageBitmap = bitmap;
    }
}
