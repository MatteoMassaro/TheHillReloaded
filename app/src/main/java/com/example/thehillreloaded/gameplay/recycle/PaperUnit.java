package com.example.thehillreloaded.gameplay.recycle;

import static com.example.thehillreloaded.gameplay.GameView.screenRatioX;
import static com.example.thehillreloaded.gameplay.GameView.screenRatioY;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.thehillreloaded.R;

public class PaperUnit extends RecUnit{

    private Bitmap paperUnit, paperUnitState2, paperUnitState3;
    private int maxRecTotal = 1100;

    public PaperUnit(int x, int y, Resources res) {
        super(x,y);
        setUnitPrice(12);

        paperUnit = BitmapFactory.decodeResource(res, R.drawable.paperunit);
        paperUnitState2 = BitmapFactory.decodeResource(res, R.drawable.paperunit_state2);
        paperUnitState3 = BitmapFactory.decodeResource(res, R.drawable.paperunit_state3);

        super.setWidth((int) (paperUnit.getWidth() * screenRatioX / 2));
        super.setHeight((int) (paperUnit.getHeight() * screenRatioY / 2));

        paperUnit = Bitmap.createScaledBitmap(paperUnit, getWidth(), getHeight(), true);
        paperUnitState2 = Bitmap.createScaledBitmap(paperUnitState2, getWidth(), getHeight(), true);
        paperUnitState3 = Bitmap.createScaledBitmap(paperUnitState3, getWidth(), getHeight(), true);
    }

    @Override
    public Bitmap getRecUnit() {
        return paperUnit;
    }

    @Override
    public Bitmap getRecUnitState2() {
        return paperUnitState2;
    }

    @Override
    public Bitmap getRecUnitState3() {
        return paperUnitState3;
    }

    @Override
    public boolean recTotalIsEnough() {
        if (getRecTotal() > maxRecTotal) {
            resetRecTotal();
            return true;
        }

        return false;
    }

    @Override
    public int getMaxRecTotal() {
        return maxRecTotal;
    }
}