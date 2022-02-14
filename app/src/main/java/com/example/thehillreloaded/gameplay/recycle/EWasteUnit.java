package com.example.thehillreloaded.gameplay.recycle;

import static com.example.thehillreloaded.gameplay.GameView.screenRatioX;
import static com.example.thehillreloaded.gameplay.GameView.screenRatioY;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.thehillreloaded.R;

public class EWasteUnit extends RecUnit{

    private Bitmap eWasteUnit, eWasteUnitState2, eWasteUnitState3;
    private int maxRecTotal = 2000;

    public EWasteUnit(int x, int y, Resources res) {
        super(x, y);
        setUnitPrice(40);

        eWasteUnit = BitmapFactory.decodeResource(res, R.drawable.ewasteunit);
        eWasteUnitState2 = BitmapFactory.decodeResource(res, R.drawable.ewasteunit_state2);
        eWasteUnitState3 = BitmapFactory.decodeResource(res, R.drawable.ewasteunit_state3);

        super.setWidth((int) (eWasteUnit.getWidth() * screenRatioX / 1.7));
        super.setHeight((int) (eWasteUnit.getHeight() * screenRatioY / 1.7));

        eWasteUnit = Bitmap.createScaledBitmap(eWasteUnit, getWidth(), getHeight(), true);
        eWasteUnitState2 = Bitmap.createScaledBitmap(eWasteUnitState2, getWidth(), getHeight(), true);
        eWasteUnitState3 = Bitmap.createScaledBitmap(eWasteUnitState3, getWidth(), getHeight(), true);
    }

    @Override
    public Bitmap getRecUnit() {
        return eWasteUnit;
    }

    @Override
    public Bitmap getRecUnitState2() {
        return eWasteUnitState2;
    }

    @Override
    public Bitmap getRecUnitState3() {
        return eWasteUnitState3;
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
