package com.example.thehillreloaded.gameplay.recycle;

import static com.example.thehillreloaded.gameplay.GameView.densityRatio;
import static com.example.thehillreloaded.gameplay.GameView.screenRatioX;
import static com.example.thehillreloaded.gameplay.GameView.screenRatioY;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.thehillreloaded.R;

public class PaperUnit extends RecUnit{

    private Bitmap paperUnit, paperUnitState2, paperUnitState3;
    private Bitmap paperUnitUpgraded, paperUnitUpgradedState2, paperUnitUpgradedState3;
    private int maxRecTotal = 1100;

    public PaperUnit(int x, int y, Resources res) {
        super(x,y);
        setUnitType("Carta");
        setUnitPrice(12);

        paperUnit = BitmapFactory.decodeResource(res, R.drawable.paperunit);
        paperUnitState2 = BitmapFactory.decodeResource(res, R.drawable.paperunit_state2);
        paperUnitState3 = BitmapFactory.decodeResource(res, R.drawable.paperunit_state3);
        paperUnitUpgraded = BitmapFactory.decodeResource(res, R.drawable.paperunit_upgraded);
        paperUnitUpgradedState2 = BitmapFactory.decodeResource(res, R.drawable.paperunit_upgraded_state2);
        paperUnitUpgradedState3 = BitmapFactory.decodeResource(res, R.drawable.paperunit_upgraded_state3);

        super.setWidth((int) (paperUnit.getWidth() * screenRatioX * densityRatio/ 5.53));
        super.setHeight((int) (paperUnit.getHeight() * screenRatioY * densityRatio/ 5.53));

        paperUnit = Bitmap.createScaledBitmap(paperUnit, getWidth(), getHeight(), true);
        paperUnitState2 = Bitmap.createScaledBitmap(paperUnitState2, getWidth(), getHeight(), true);
        paperUnitState3 = Bitmap.createScaledBitmap(paperUnitState3, getWidth(), getHeight(), true);
        paperUnitUpgraded = Bitmap.createScaledBitmap(paperUnitUpgraded, getWidth(), getHeight(), true);
        paperUnitUpgradedState2 = Bitmap.createScaledBitmap(paperUnitUpgradedState2, getWidth(), getHeight(), true);
        paperUnitUpgradedState3 = Bitmap.createScaledBitmap(paperUnitUpgradedState3, getWidth(), getHeight(), true);
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
    public Bitmap getRecUnitLvl2() {
        return paperUnitUpgraded;
    }

    @Override
    public Bitmap getRecUnitLvl2State2() {
        return paperUnitUpgradedState2;
    }

    @Override
    public Bitmap getRecUnitLvl2State3() {
        return paperUnitUpgradedState3;
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