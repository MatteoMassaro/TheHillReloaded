package com.example.thehillreloaded.gameplay.junk;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.thehillreloaded.R;

import static com.example.thehillreloaded.gameplay.GameView.densityRatio;
import static com.example.thehillreloaded.gameplay.GameView.screenRatioX;
import static com.example.thehillreloaded.gameplay.GameView.screenRatioY;
import static com.example.thehillreloaded.menu.DifficoltaActivity.tassoDifficolta;


public class Steel extends Junk {

    private static double tasso = 0;
    private static boolean tassoMassimoRaggiunto = false;
    private Bitmap steel;

    public Steel(int x, int y, Resources res) {
        super(x, y);

        steel = BitmapFactory.decodeResource(res, R.drawable.steel);

        super.setWidth((int) (steel.getWidth() * screenRatioX * densityRatio/ 13.65));
        super.setHeight((int) (steel.getHeight() * screenRatioY * densityRatio/ 13.65));

        steel = Bitmap.createScaledBitmap(steel, getWidth(), getHeight(), true);
    }

    public static void rinnovaTasso() {
        if (tassoMassimoRaggiunto) {
            if (tasso > 0.15834) {
                tasso -= 0.00018 * tassoDifficolta;
            }
        } else if (Aluminum.isTassoMassimoRaggiunto()){
            tasso += 0.00007 * tassoDifficolta;
        }
    }

    public static void tassoMassimoRaggiunto() {
        if (tasso > 0.19) {
            tassoMassimoRaggiunto = true;
        }
    }

    public static double getTasso() {
        return tasso;
    }

    public static void resetValues() {
        tasso = 0;
        tassoMassimoRaggiunto = false;
    }

    @Override
    public Bitmap getJunk() {
        return steel;
    }
}