package com.example.thehillreloaded.gameplay.recycle;

import static com.example.thehillreloaded.menu.MenuActivity.densityRatio;
import static com.example.thehillreloaded.menu.MenuActivity.screenRatioX;
import static com.example.thehillreloaded.menu.MenuActivity.screenRatioY;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.thehillreloaded.R;

public class AluminumUnit extends RecUnit {

    private Bitmap aluminumUnit, aluminumUnitState2, aluminumUnitState3;
    private Bitmap aluminumUnitUpgraded, aluminumUnitUpgradedState2, aluminumUnitUpgradedState3;
    private int maxRecTotal = 1300;

    public AluminumUnit(int x, int y, Resources res) {
        super(x, y);
        setUnitType("Alluminio");
        setRecycleScore(6);
        setUnitPrice(25);

        aluminumUnit = BitmapFactory.decodeResource(res, R.drawable.aluminumunit);
        aluminumUnitState2 = BitmapFactory.decodeResource(res, R.drawable.aluminumunit_state2);
        aluminumUnitState3 = BitmapFactory.decodeResource(res, R.drawable.aluminumunit_state3);
        aluminumUnitUpgraded = BitmapFactory.decodeResource(res, R.drawable.aluminumunit_upgraded);
        aluminumUnitUpgradedState2 = BitmapFactory.decodeResource(res, R.drawable.aluminumunit_upgraded_state2);
        aluminumUnitUpgradedState3 = BitmapFactory.decodeResource(res, R.drawable.aluminumunit_upgraded_state3);

        super.setWidth((int) (aluminumUnit.getWidth() * screenRatioX * densityRatio/ 5.28));
        super.setHeight((int) (aluminumUnit.getHeight() * screenRatioY * densityRatio/ 5.28));

        aluminumUnit = Bitmap.createScaledBitmap(aluminumUnit, getWidth(), getHeight(), true);
        aluminumUnitState2 = Bitmap.createScaledBitmap(aluminumUnitState2, getWidth(), getHeight(), true);
        aluminumUnitState3 = Bitmap.createScaledBitmap(aluminumUnitState3, getWidth(), getHeight(), true);
        aluminumUnitUpgraded = Bitmap.createScaledBitmap(aluminumUnitUpgraded, getWidth(), getHeight(), true);
        aluminumUnitUpgradedState2 = Bitmap.createScaledBitmap(aluminumUnitUpgradedState2, getWidth(), getHeight(), true);
        aluminumUnitUpgradedState3 = Bitmap.createScaledBitmap(aluminumUnitUpgradedState3, getWidth(), getHeight(), true);
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
    public Bitmap getRecUnitLvl2() {
        return aluminumUnitUpgraded;
    }

    @Override
    public Bitmap getRecUnitLvl2State2() {
        return aluminumUnitUpgradedState2;
    }

    @Override
    public Bitmap getRecUnitLvl2State3() {
        return aluminumUnitUpgradedState3;
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