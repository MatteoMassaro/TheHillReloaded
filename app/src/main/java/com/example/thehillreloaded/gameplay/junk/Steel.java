package com.example.thehillreloaded.gameplay.junk;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.thehillreloaded.R;

import static com.example.thehillreloaded.gameplay.GameView.screenRatioX;
import static com.example.thehillreloaded.gameplay.GameView.screenRatioY;


public class Steel extends Junk {

    private static double tasso = 0;
    private static boolean tassoMassimoRaggiunto = false;
    private Bitmap steel;

    public Steel(int x, int y, Resources res) {
        super(x, y);

        steel = BitmapFactory.decodeResource(res, R.drawable.steel);

        super.setWidth((int) (steel.getWidth() * screenRatioX / 5));
        super.setHeight((int) (steel.getHeight() * screenRatioY / 5));

        steel = Bitmap.createScaledBitmap(steel, getWidth(), getHeight(), true);
    }

    public static void rinnovaTasso() {
        if (tassoMassimoRaggiunto) {
            if (tasso > 0.15834) {
                tasso -= 0.00018;
            }
        } else if (Aluminum.isTassoMassimoRaggiunto()){
            tasso += 0.00025;
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

    @Override
    public Bitmap getJunk() {
        return steel;
    }
}