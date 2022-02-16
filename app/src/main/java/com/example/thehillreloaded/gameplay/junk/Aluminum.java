package com.example.thehillreloaded.gameplay.junk;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.thehillreloaded.R;

import static com.example.thehillreloaded.gameplay.GameView.screenRatioX;
import static com.example.thehillreloaded.gameplay.GameView.screenRatioY;


public class Aluminum extends Junk {

    private static double tasso = 0;
    private static boolean tassoMassimoRaggiunto = false;
    private Bitmap aluminum;

    public Aluminum(int x, int y, Resources res) {
        super(x, y);

        aluminum = BitmapFactory.decodeResource(res, R.drawable.aluminum);

        super.setWidth((int) (aluminum.getWidth() * screenRatioX / 4.91));
        super.setHeight((int) (aluminum.getHeight() * screenRatioY / 4.91));

        aluminum = Bitmap.createScaledBitmap(aluminum, getWidth(), getHeight(), true);
    }

    public static void rinnovaTasso() {
        if (tassoMassimoRaggiunto) {
            if (tasso > 0.15834) {
                tasso -= 0.00025;
            }
        } else if (Paper.isTassoMassimoRaggiunto()){
            tasso += 0.0002;
        } else {
            tasso += 0.00005;
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

    @Override
    public Bitmap getJunk() {
        return aluminum;
    }
}