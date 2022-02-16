package com.example.thehillreloaded.gameplay.recycle;

import static com.example.thehillreloaded.gameplay.GameView.densityRatio;
import static com.example.thehillreloaded.gameplay.GameView.screenRatioX;
import static com.example.thehillreloaded.gameplay.GameView.screenRatioY;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.thehillreloaded.R;

public class Incinerator extends RecUnit {

    private Bitmap incinerator, incineratorState2, incineratorState3, incineratorState4;

    public Incinerator(int x, int y, Resources res) {
        super(x, y);
        setIsUnlockedToTrue();

        incinerator = BitmapFactory.decodeResource(res, R.drawable.incinerator);
        incineratorState2 = BitmapFactory.decodeResource(res, R.drawable.incinerator_state2);
        incineratorState3 = BitmapFactory.decodeResource(res, R.drawable.incinerator_state3);
        incineratorState4 = BitmapFactory.decodeResource(res, R.drawable.incinerator_state4);

        super.setWidth((int) (incinerator.getWidth() * screenRatioX * densityRatio/ 2.77));
        super.setHeight((int) (incinerator.getHeight() * screenRatioY * densityRatio/ 2.77));

        incinerator = Bitmap.createScaledBitmap(incinerator, getWidth(), getHeight(), true);
        incineratorState2 = Bitmap.createScaledBitmap(incineratorState2, getWidth(), getHeight(), true);
        incineratorState3 = Bitmap.createScaledBitmap(incineratorState3, getWidth(), getHeight(), true);
        incineratorState4 = Bitmap.createScaledBitmap(incineratorState4, getWidth(), getHeight(), true);
    }

    @Override
    public Bitmap getRecUnit() {
        return incinerator;
    }

    @Override
    public Bitmap getRecUnitState2() {
        return incineratorState2;
    }

    @Override
    public Bitmap getRecUnitState3() {
        return incineratorState3;
    }

    @Override
    public Bitmap getRecUnitState4() {
        return incineratorState4;
    }

}