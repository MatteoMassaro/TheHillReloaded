package com.example.thehillreloaded.gameplay.recycle;

import static com.example.thehillreloaded.menu.MenuActivity.densityRatio;
import static com.example.thehillreloaded.menu.MenuActivity.screenRatioX;
import static com.example.thehillreloaded.menu.MenuActivity.screenRatioY;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.ProgressBar;

import com.example.thehillreloaded.R;

public class GlassUnit extends RecUnit {

    private Bitmap glassUnit, glassUnitState2, glassUnitState3;
    private Bitmap glassUnitUpgraded, glassUnitUpgradedState2, glassUnitUpgradedState3;

    public GlassUnit(int x, int y, Resources res) {
        super(x, y);
        setUnitType("glass_unit");
        setRecycleScore(3);
        setIsUnlockedToTrue();

        glassUnit = BitmapFactory.decodeResource(res, R.drawable.glassunit);
        glassUnitState2 = BitmapFactory.decodeResource(res, R.drawable.glassunit_state2);
        glassUnitState3 = BitmapFactory.decodeResource(res, R.drawable.glassunit_state3);
        glassUnitUpgraded = BitmapFactory.decodeResource(res, R.drawable.glassunit_upgraded);
        glassUnitUpgradedState2 = BitmapFactory.decodeResource(res, R.drawable.glassunit_upgraded_state2);
        glassUnitUpgradedState3 = BitmapFactory.decodeResource(res, R.drawable.glassunit_upgraded_state3);

        super.setWidth((int) (glassUnit.getWidth() * screenRatioX * densityRatio/ 4.72));
        super.setHeight((int) (glassUnit.getHeight() * screenRatioY * densityRatio/ 4.72));
        glassUnit = Bitmap.createScaledBitmap(glassUnit, getWidth(), getHeight(), true);
        glassUnitState2 = Bitmap.createScaledBitmap(glassUnitState2, getWidth(), getHeight(), true);
        glassUnitState3 = Bitmap.createScaledBitmap(glassUnitState3, getWidth(), getHeight(), true);
        glassUnitUpgraded = Bitmap.createScaledBitmap(glassUnitUpgraded, getWidth(), getHeight(), true);
        glassUnitUpgradedState2 = Bitmap.createScaledBitmap(glassUnitUpgradedState2, getWidth(), getHeight(), true);
        glassUnitUpgradedState3 = Bitmap.createScaledBitmap(glassUnitUpgradedState3, getWidth(), getHeight(), true);
    }

    @Override
    public Bitmap getRecUnit() {
        return glassUnit;
    }

    @Override
    public Bitmap getRecUnitState2() {
        return glassUnitState2;
    }

    @Override
    public Bitmap getRecUnitState3() {
        return glassUnitState3;
    }

    @Override
    public Bitmap getRecUnitLvl2() {
        return glassUnitUpgraded;
    }

    @Override
    public Bitmap getRecUnitLvl2State2() {
        return glassUnitUpgradedState2;
    }

    @Override
    public Bitmap getRecUnitLvl2State3() {
        return glassUnitUpgradedState3;
    }
}