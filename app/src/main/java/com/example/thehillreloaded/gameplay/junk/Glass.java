package com.example.thehillreloaded.gameplay.junk;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.thehillreloaded.R;

import static com.example.thehillreloaded.gameplay.GameView.densityRatio;
import static com.example.thehillreloaded.gameplay.GameView.screenRatioX;
import static com.example.thehillreloaded.gameplay.GameView.screenRatioY;
import static com.example.thehillreloaded.menu.DifficoltaActivity.tassoDifficolta;


public class Glass extends Junk {

    private static double tasso = 1;
    private Bitmap glass;

    public Glass(int x, int y, Resources res) {
        super(x, y);

        glass = BitmapFactory.decodeResource(res, R.drawable.glass);

        super.setWidth((int) (glass.getWidth() * screenRatioX * densityRatio/4.64));
        super.setHeight((int) (glass.getHeight() * screenRatioY * densityRatio/4.64));

        glass = Bitmap.createScaledBitmap(glass, getWidth(), getHeight(), true);
    }

    public static void rinnovaTasso() {
        if (tasso > 0.15834) {
            tasso -= 0.0001 * tassoDifficolta;
        }
    }

    public static double getTasso() {
        return tasso;
    }

    public static void resetValues() {
        tasso = 1;
    }

    @Override
    public Bitmap getJunk() {
        return glass;
    }
}