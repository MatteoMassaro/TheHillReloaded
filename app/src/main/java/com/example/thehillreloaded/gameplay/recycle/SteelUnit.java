package com.example.thehillreloaded.gameplay.recycle;

import static com.example.thehillreloaded.gameplay.GameView.densityRatio;
import static com.example.thehillreloaded.gameplay.GameView.screenRatioX;
import static com.example.thehillreloaded.gameplay.GameView.screenRatioY;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.thehillreloaded.R;

public class SteelUnit extends RecUnit{

    private Bitmap steelUnit, steelUnitState2, steelUnitState3;
    private Bitmap steelUnitUpgraded, steelUnitUpgradedState2, steelUnitUpgradedState3;
    private int maxRecTotal = 1500;

    public SteelUnit(int x, int y, Resources res) {
        super(x, y);
        setUnitType("Acciaio");
        setUnitPrice(30);

        steelUnit = BitmapFactory.decodeResource(res, R.drawable.steelunit);
        steelUnitState2 = BitmapFactory.decodeResource(res, R.drawable.steelunit_state2);
        steelUnitState3 = BitmapFactory.decodeResource(res, R.drawable.steelunit_state2);
        steelUnitUpgraded = BitmapFactory.decodeResource(res, R.drawable.steelunit_upgraded);
        steelUnitUpgradedState2 = BitmapFactory.decodeResource(res, R.drawable.steelunit_upgraded_state2);
        steelUnitUpgradedState3 = BitmapFactory.decodeResource(res, R.drawable.steelunit_upgraded_state3);

        super.setWidth((int) (steelUnit.getWidth() * screenRatioX * densityRatio/ 4.71));
        super.setHeight((int) (steelUnit.getHeight() * screenRatioY * densityRatio/ 4.71));

        steelUnit = Bitmap.createScaledBitmap(steelUnit, getWidth(), getHeight(), true);
        steelUnitState2 = Bitmap.createScaledBitmap(steelUnitState2, getWidth(), getHeight(), true);
        steelUnitState3 = Bitmap.createScaledBitmap(steelUnitState3, getWidth(), getHeight(), true);
        steelUnitUpgraded = Bitmap.createScaledBitmap(steelUnitUpgraded, getWidth(), getHeight(), true);
        steelUnitUpgradedState2 = Bitmap.createScaledBitmap(steelUnitUpgradedState2, getWidth(), getHeight(), true);
        steelUnitUpgradedState3 = Bitmap.createScaledBitmap(steelUnitUpgradedState3, getWidth(), getHeight(), true);
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
    public Bitmap getRecUnitLvl2() {
        return steelUnitUpgraded;
    }

    @Override
    public Bitmap getRecUnitLvl2State2() {
        return steelUnitUpgradedState2;
    }

    @Override
    public Bitmap getRecUnitLvl2State3() {
        return steelUnitUpgradedState3;
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
    public boolean recTotalUpgradedIsEnough() {
        if (getRecTotalUpgraded() > maxRecTotal) {
            resetRecTotalUpgraded();
            return true;
        }

        return false;
    }

    @Override
    public int getMaxRecTotal() {
        return maxRecTotal;
    }
}