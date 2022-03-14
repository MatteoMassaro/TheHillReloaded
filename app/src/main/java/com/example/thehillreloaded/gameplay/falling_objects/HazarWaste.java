package com.example.thehillreloaded.gameplay.falling_objects;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.thehillreloaded.R;

import static com.example.thehillreloaded.menu.DifficoltaActivity.tassoDifficolta;
import static com.example.thehillreloaded.menu.MenuActivity.densityRatio;
import static com.example.thehillreloaded.menu.MenuActivity.screenRatioX;
import static com.example.thehillreloaded.menu.MenuActivity.screenRatioY;

public class HazarWaste extends Junk {

    private static double tasso = 0;
    private static boolean tassoMassimoRaggiunto = false;
    private Bitmap hazarWaste;

    public HazarWaste(int x, int y, Resources res) {
        super(x, y);
        setJunkType("hazar_waste");

        hazarWaste = BitmapFactory.decodeResource(res, R.drawable.radioactive);

        super.setWidth((int) (hazarWaste.getWidth() * screenRatioX * densityRatio/ 9.01));
        super.setHeight((int) (hazarWaste.getHeight() * screenRatioY * densityRatio/ 9.01));

        hazarWaste = Bitmap.createScaledBitmap(hazarWaste, getWidth(), getHeight(), true);
    }


    public static void rinnovaTasso() {
        if (Paper.isTassoMassimoRaggiunto() && !isTassoMassimoRaggiunto()) {
            tasso += 0.000015 * tassoDifficolta;
        }
    }

    public static boolean isTassoMassimoRaggiunto() {
        return tassoMassimoRaggiunto;
    }

    public static void setTassoMassimoRaggiunto(boolean new_tassoMassimoRaggiunto) {
        tassoMassimoRaggiunto = new_tassoMassimoRaggiunto;
    }

    public static void tassoMassimoRaggiunto() {
        if (tasso > 0.05) {
            tassoMassimoRaggiunto = true;
        }
    }

    public static double getTasso() {
        return tasso;
    }

    public static void setTasso(double new_tasso) {
        tasso = new_tasso;
    }

    public static void resetValues() {
        tasso = 0;
        tassoMassimoRaggiunto = false;
    }

    @Override
    public Bitmap getFallingObject() {
        return hazarWaste;
    }
}