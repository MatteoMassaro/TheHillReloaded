package com.example.thehillreloaded.gameplay.recycle;

import static com.example.thehillreloaded.gameplay.GameView.screenRatioX;
import static com.example.thehillreloaded.gameplay.GameView.screenRatioY;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.ProgressBar;

import com.example.thehillreloaded.R;

public class GlassUnit extends RecUnit {

    private Bitmap glassUnit, glassUnitState2, glassUnitState3;

    public GlassUnit(int x, int y, Resources res) {
        super(x, y);
        setUnitType("Vetro");
        setIsUnlockedToTrue();

        glassUnit = BitmapFactory.decodeResource(res, R.drawable.glassunit);
        glassUnitState2 = BitmapFactory.decodeResource(res, R.drawable.glassunit_state2);
        glassUnitState3 = BitmapFactory.decodeResource(res, R.drawable.glassunit_state3);

        super.setWidth((int) (glassUnit.getWidth() * screenRatioX / 4.72));
        super.setHeight((int) (glassUnit.getHeight() * screenRatioY / 4.72));
        glassUnit = Bitmap.createScaledBitmap(glassUnit, getWidth(), getHeight(), true);
        glassUnitState2 = Bitmap.createScaledBitmap(glassUnitState2, getWidth(), getHeight(), true);
        glassUnitState3 = Bitmap.createScaledBitmap(glassUnitState3, getWidth(), getHeight(), true);
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
}