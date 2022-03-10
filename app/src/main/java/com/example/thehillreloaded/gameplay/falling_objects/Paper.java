package com.example.thehillreloaded.gameplay.falling_objects;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.thehillreloaded.R;

import static com.example.thehillreloaded.menu.DifficoltaActivity.tassoDifficolta;
import static com.example.thehillreloaded.menu.MenuActivity.densityRatio;
import static com.example.thehillreloaded.menu.MenuActivity.screenRatioX;
import static com.example.thehillreloaded.menu.MenuActivity.screenRatioY;


public class Paper extends Junk {

    private static double tasso = 0;
    private static boolean tassoMassimoRaggiunto = false;
    private Bitmap paper;

    public Paper(int x, int y, Resources res) {
        super(x, y);
        setJunkType("paper");

        paper = BitmapFactory.decodeResource(res, R.drawable.paper);

        super.setWidth((int) (paper.getWidth() * screenRatioX * densityRatio/ 5.19));
        super.setHeight((int) (paper.getHeight() * screenRatioY * densityRatio/ 5.19));

        paper = Bitmap.createScaledBitmap(paper, getWidth(), getHeight(), true);
    }

    public static void rinnovaTasso() {
        if (tassoMassimoRaggiunto) {
            if (tasso > 0.15834) {
                tasso -= 0.0001 * tassoDifficolta;
            }
        } else {
            tasso += 0.00006 * tassoDifficolta;
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
        return paper;
    }
}