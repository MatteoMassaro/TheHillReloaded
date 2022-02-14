package com.example.thehillreloaded.gameplay.recycle;

import static com.example.thehillreloaded.gameplay.GameView.screenRatioX;
import static com.example.thehillreloaded.gameplay.GameView.screenRatioY;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.thehillreloaded.R;

public class SteelUnit extends RecUnit{

    private Bitmap steelUnit, steelUnitState2, steelUnitState3;
    private int maxRecTotal = 1500;

    public SteelUnit(int x, int y, Resources res) {
        super(x, y);
        setUnitPrice(30);

        steelUnit = BitmapFactory.decodeResource(res, R.drawable.steelunit);
        steelUnitState2 = BitmapFactory.decodeResource(res, R.drawable.steelunit_state2);
        steelUnitState3 = BitmapFactory.decodeResource(res, R.drawable.steelunit_state2);

        super.setWidth((int) (steelUnit.getWidth() * screenRatioX / 1.7));
        super.setHeight((int) (steelUnit.getHeight() * screenRatioY / 1.7));

        steelUnit = Bitmap.createScaledBitmap(steelUnit, getWidth(), getHeight(), true);
        steelUnitState2 = Bitmap.createScaledBitmap(steelUnitState2, getWidth(), getHeight(), true);
        steelUnitState3 = Bitmap.createScaledBitmap(steelUnitState3, getWidth(), getHeight(), true);
    }

    @Override
    public Bitmap getRecUnit() {
        return steelUnit;
    }

    @Override
    public Bitmap getRecUnitState2() {
        return steelUnitState2;
    }

    @Override
    public Bitmap getRecUnitState3() {
        return steelUnitState3;
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