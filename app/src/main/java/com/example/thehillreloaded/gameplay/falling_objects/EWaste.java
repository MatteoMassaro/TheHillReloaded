package com.example.thehillreloaded.gameplay.falling_objects;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.thehillreloaded.R;

import static com.example.thehillreloaded.menu.DifficoltaActivity.tassoDifficolta;
import static com.example.thehillreloaded.menu.MenuActivity.densityRatio;
import static com.example.thehillreloaded.menu.MenuActivity.screenRatioX;
import static com.example.thehillreloaded.menu.MenuActivity.screenRatioY;


public class EWaste extends Junk {

    private static double tasso = 0;
    private static boolean tassoMassimoRaggiunto = false;
    private Bitmap eWaste;

    public EWaste(int x, int y, Resources res) {
        super(x, y);
        setJunkType("ewaste");

        eWaste = BitmapFactory.decodeResource(res, R.drawable.ewaste);

        super.setWidth((int) (eWaste.getWidth() * screenRatioX * densityRatio/ 6.28));
        super.setHeight((int) (eWaste.getHeight() * screenRatioY * densityRatio/ 6.28));

        eWaste = Bitmap.createScaledBitmap(eWaste, getWidth(), getHeight(), true);
    }

    public static void rinnovaTasso() {
        if (tassoMassimoRaggiunto) {
            if (tasso > 0.15834) {
                tasso -= 0.00006 * tassoDifficolta;
            }
        } else if (Aluminum.isTassoMassimoRaggiunto()) {
            tasso += 0.00001 * tassoDifficolta;
        }
    }

    public static void tassoMassimoRaggiunto() {
        if (tasso > 0.16) {
            tassoMassimoRaggiunto = true;
        }
    }

    public static boolean isTassoMassimoRaggiunto() {
        return tassoMassimoRaggiunto;
    }

    public static void setTassoMassimoRaggiunto(boolean new_tassoMassimoRaggiunto) {
        tassoMassimoRaggiunto = new_tassoMassimoRaggiunto;
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
        return eWaste;
    }
}