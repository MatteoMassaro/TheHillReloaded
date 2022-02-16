package com.example.thehillreloaded.gameplay.junk;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.thehillreloaded.R;

import static com.example.thehillreloaded.gameplay.GameView.densityRatio;
import static com.example.thehillreloaded.gameplay.GameView.screenRatioX;
import static com.example.thehillreloaded.gameplay.GameView.screenRatioY;


public class EWaste extends Junk {

    private static double tasso = 0;
    private static boolean tassoMassimoRaggiunto = false;
    private Bitmap eWaste;

    public EWaste(int x, int y, Resources res) {
        super(x, y);

        eWaste = BitmapFactory.decodeResource(res, R.drawable.ewaste);

        super.setWidth((int) (eWaste.getWidth() * screenRatioX * densityRatio/ 6.28));
        super.setHeight((int) (eWaste.getHeight() * screenRatioY * densityRatio/ 6.28));

        eWaste = Bitmap.createScaledBitmap(eWaste, getWidth(), getHeight(), true);
    }

    public static void rinnovaTasso() {
        if (tassoMassimoRaggiunto) {
            if (tasso > 0.15834) {
                tasso -= 0.00006;
            }
        } else if (Aluminum.isTassoMassimoRaggiunto()) {
            tasso += 0.00007;
        }
    }

    public static void tassoMassimoRaggiunto() {
        if (tasso > 0.16) {
            tassoMassimoRaggiunto = true;
        }
    }

    public static double getTasso() {
        return tasso;
    }

    @Override
    public Bitmap getJunk() {
        return eWaste;
    }
}