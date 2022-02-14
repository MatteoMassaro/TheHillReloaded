package com.example.thehillreloaded.gameplay.recycle;

import static com.example.thehillreloaded.gameplay.GameView.screenRatioX;
import static com.example.thehillreloaded.gameplay.GameView.screenRatioY;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.thehillreloaded.R;

public class AluminumUnit extends RecUnit {

    private Bitmap aluminumUnit, aluminumUnitState2, aluminumUnitState3;
    private int maxRecTotal = 1300;

    public AluminumUnit(int x, int y, Resources res) {
        super(x, y);
        setUnitPrice(25);

        aluminumUnit = BitmapFactory.decodeResource(res, R.drawable.aluminumunit);
        aluminumUnitState2 = BitmapFactory.decodeResource(res, R.drawable.aluminumunit_state2);
        aluminumUnitState3 = BitmapFactory.decodeResource(res, R.drawable.aluminumunit_state3);

        super.setWidth((int) (aluminumUnit.getWidth() * screenRatioX / 1.9));
        super.setHeight((int) (aluminumUnit.getHeight() * screenRatioY / 1.9));

        aluminumUnit = Bitmap.createScaledBitmap(aluminumUnit, getWidth(), getHeight(), true);
        aluminumUnitState2 = Bitmap.createScaledBitmap(aluminumUnitState2, getWidth(), getHeight(), true);
        aluminumUnitState3 = Bitmap.createScaledBitmap(aluminumUnitState3, getWidth(), getHeight(), true);
    }

    @Override
    public Bitmap getRecUnit() {
        return aluminumUnit;
    }

    @Override
    public Bitmap getRecUnitState2() {
        return aluminumUnitState2;
    }

    @Override
    public Bitmap getRecUnitState3() {
        return aluminumUnitState3;
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