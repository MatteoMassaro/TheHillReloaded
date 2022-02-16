package com.example.thehillreloaded.gameplay.recycle;

import static com.example.thehillreloaded.gameplay.GameView.densityRatio;
import static com.example.thehillreloaded.gameplay.GameView.screenRatioX;
import static com.example.thehillreloaded.gameplay.GameView.screenRatioY;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.thehillreloaded.R;

public class PlasticUnit extends RecUnit{

    private Bitmap plasticUnit, plasticUnitState2, plasticUnitState3;
    private int maxRecTotal = 1700;

    public PlasticUnit(int x, int y, Resources res) {
        super(x, y);
        setUnitType("Plastica");
        setUnitPrice(35);

        plasticUnit = BitmapFactory.decodeResource(res, R.drawable.plasticunit);
        plasticUnitState2 = BitmapFactory.decodeResource(res, R.drawable.plasticunit_state2);
        plasticUnitState3 = BitmapFactory.decodeResource(res, R.drawable.plasticunit_state3);

        super.setWidth((int) (plasticUnit.getWidth() * screenRatioX * densityRatio/4.72));
        super.setHeight((int) (plasticUnit.getHeight() * screenRatioY * densityRatio/4.72));

        plasticUnit = Bitmap.createScaledBitmap(plasticUnit, getWidth(), getHeight(), true);
        plasticUnitState2 = Bitmap.createScaledBitmap(plasticUnitState2, getWidth(), getHeight(), true);
        plasticUnitState3 = Bitmap.createScaledBitmap(plasticUnitState3, getWidth(), getHeight(), true);
    }

    @Override
    public Bitmap getRecUnit() {
        return plasticUnit;
    }

    @Override
    public Bitmap getRecUnitState2() {
        return plasticUnitState2;
    }

    @Override
    public Bitmap getRecUnitState3() {
        return plasticUnitState3;
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