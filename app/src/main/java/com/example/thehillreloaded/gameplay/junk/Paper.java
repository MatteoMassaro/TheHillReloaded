package com.example.thehillreloaded.gameplay.junk;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.thehillreloaded.R;

import static com.example.thehillreloaded.gameplay.GameView.densityRatio;
import static com.example.thehillreloaded.gameplay.GameView.screenRatioX;
import static com.example.thehillreloaded.gameplay.GameView.screenRatioY;


public class Paper extends Junk {

    private static double tasso = 0;
    private static boolean tassoMassimoRaggiunto = false;
    private Bitmap paper;

    public Paper(int x, int y, Resources res) {
        super(x, y);

        paper = BitmapFactory.decodeResource(res, R.drawable.paper);

        super.setWidth((int) (paper.getWidth() * screenRatioX * densityRatio/ 5.19));
        super.setHeight((int) (paper.getHeight() * screenRatioY * densityRatio/ 5.19));

        paper = Bitmap.createScaledBitmap(paper, getWidth(), getHeight(), true);
    }

    public static void rinnovaTasso() {
        if (tassoMassimoRaggiunto) {
            if (tasso > 0.15834) {
                tasso -= 0.00013;
            }
        } else {
            tasso += 0.00008;
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
        return paper;
    }
}