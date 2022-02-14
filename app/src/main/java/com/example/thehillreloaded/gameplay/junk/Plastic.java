package com.example.thehillreloaded.gameplay.junk;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.thehillreloaded.R;

import static com.example.thehillreloaded.gameplay.GameView.screenRatioX;
import static com.example.thehillreloaded.gameplay.GameView.screenRatioY;


public class Plastic extends Junk {

    private static double tasso = 0;
    private static boolean tassoMassimoRaggiunto = false;
    private Bitmap plastic;

    public Plastic(int x, int y, Resources res) {
        super(x, y);

        plastic = BitmapFactory.decodeResource(res, R.drawable.plastic);

        super.setWidth((int) (plastic.getWidth() * screenRatioX / 1.7));
        super.setHeight((int) (plastic.getHeight() * screenRatioY / 1.7));

        plastic = Bitmap.createScaledBitmap(plastic, getWidth(), getHeight(), true);
    }

    public static void rinnovaTasso() {
        if (tassoMassimoRaggiunto) {
            if (tasso > 0.15834) {
                tasso -= 0.00006;
            }
        } else if (Aluminum.isTassoMassimoRaggiunto()) {
            tasso += 0.00014;
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

    public static double getTasso() {
        return tasso;
    }

    @Override
    public Bitmap getJunk() {
        return plastic;
    }
}

