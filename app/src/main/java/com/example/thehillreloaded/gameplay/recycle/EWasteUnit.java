package com.example.thehillreloaded.gameplay.recycle;

import static com.example.thehillreloaded.gameplay.GameView.densityRatio;
import static com.example.thehillreloaded.gameplay.GameView.screenRatioX;
import static com.example.thehillreloaded.gameplay.GameView.screenRatioY;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.thehillreloaded.R;

public class EWasteUnit extends RecUnit{

    private Bitmap eWasteUnit, eWasteUnitState2, eWasteUnitState3;
    private Bitmap eWasteUnitUpgraded, eWasteUnitUpgradedState2, eWasteUnitUpgradedState3;
    private int maxRecTotal = 2000;

    public EWasteUnit(int x, int y, Resources res) {
        super(x, y);
        setUnitType("Rifiuti tecnologici");
        setUnitPrice(40);

        eWasteUnit = BitmapFactory.decodeResource(res, R.drawable.ewasteunit);
        eWasteUnitState2 = BitmapFactory.decodeResource(res, R.drawable.ewasteunit_state2);
        eWasteUnitState3 = BitmapFactory.decodeResource(res, R.drawable.ewasteunit_state3);
        eWasteUnitUpgraded = BitmapFactory.decodeResource(res, R.drawable.ewasteunit_upgraded);
        eWasteUnitUpgradedState2 = BitmapFactory.decodeResource(res, R.drawable.ewasteunit_upgraded_state2);
        eWasteUnitUpgradedState3 = BitmapFactory.decodeResource(res, R.drawable.ewasteunit_upgraded_state3);

        super.setWidth((int) (eWasteUnit.getWidth() * screenRatioX * densityRatio/ 4.86));
        super.setHeight((int) (eWasteUnit.getHeight() * screenRatioY * densityRatio/ 4.86));

        eWasteUnit = Bitmap.createScaledBitmap(eWasteUnit, getWidth(), getHeight(), true);
        eWasteUnitState2 = Bitmap.createScaledBitmap(eWasteUnitState2, getWidth(), getHeight(), true);
        eWasteUnitState3 = Bitmap.createScaledBitmap(eWasteUnitState3, getWidth(), getHeight(), true);
        eWasteUnitUpgraded = Bitmap.createScaledBitmap(eWasteUnitUpgraded, getWidth(), getHeight(), true);
        eWasteUnitUpgradedState2 = Bitmap.createScaledBitmap(eWasteUnitUpgradedState2, getWidth(), getHeight(), true);
        eWasteUnitUpgradedState3 = Bitmap.createScaledBitmap(eWasteUnitUpgradedState3, getWidth(), getHeight(), true);
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
    public Bitmap getRecUnitLvl2() {
        return eWasteUnitUpgraded;
    }

    @Override
    public Bitmap getRecUnitLvl2State2() {
        return eWasteUnitUpgradedState2;
    }

    @Override
    public Bitmap getRecUnitLvl2State3() {
        return eWasteUnitUpgradedState3;
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
