package com.example.thehillreloaded.gameplay.falling_objects;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.thehillreloaded.R;

import static com.example.thehillreloaded.menu.DifficoltaActivity.tassoDifficolta;
import static com.example.thehillreloaded.menu.MenuActivity.densityRatio;
import static com.example.thehillreloaded.menu.MenuActivity.screenRatioX;
import static com.example.thehillreloaded.menu.MenuActivity.screenRatioY;


public class Aluminum extends Junk {

    private static double tasso = 0;
    private static boolean tassoMassimoRaggiunto = false;
    private Bitmap aluminum;

    public Aluminum(int x, int y, Resources res) {
        super(x, y);

        aluminum = BitmapFactory.decodeResource(res, R.drawable.aluminum);

        super.setWidth((int) (aluminum.getWidth() * screenRatioX * densityRatio/ 4.91));
        super.setHeight((int) (aluminum.getHeight() * screenRatioY * densityRatio/ 4.91));

        aluminum = Bitmap.createScaledBitmap(aluminum, getWidth(), getHeight(), true);
    }

    public static void rinnovaTasso() {
        if (tassoMassimoRaggiunto) {
            if (tasso > 0.15834) {
                tasso -= 0.00008 * tassoDifficolta;
            }
        } else if (Paper.isTassoMassimoRaggiunto()){
            tasso += 0.00008 * tassoDifficolta;
        } else {
            tasso += 0.00002 * tassoDifficolta;
        }
    }

    public static void tassoMassimoRaggiunto() {
        if (tasso > 0.5) {
            tassoMassimoRaggiunto = true;
        }
    }

    public static boolean isTassoMassimoRaggiunto() {
        return tassoMassimoRaggiunto;
    }

    public static double getTasso() {
        return tasso;
    }

    public static void resetValues() {
        tasso = 0;
        tassoMassimoRaggiunto = false;
    }

    @Override
    public Bitmap getFallingObject() {
        return aluminum;
    }
}