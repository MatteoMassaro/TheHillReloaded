package com.example.thehillreloaded.gameplay.imageclass;

import static com.example.thehillreloaded.gameplay.GameView.densityRatio;
import static com.example.thehillreloaded.gameplay.GameView.screenRatioX;
import static com.example.thehillreloaded.gameplay.GameView.screenRatioY;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.thehillreloaded.R;

public class ConfirmBuilding extends RecImages {

    private String confirmString = "Sei sicuro di voler\ncostruire questa unità?";

    public ConfirmBuilding (int x, int y, Resources res) {
        super(x,y);

        setImageBitmap(BitmapFactory.decodeResource(res, R.drawable.pop_up));
        setImageBitmap2(BitmapFactory.decodeResource(res, R.drawable.yes_button));
        setImageBitmap3(BitmapFactory.decodeResource(res, R.drawable.no_button));

        super.setWidth((int)(getImageBitmap().getWidth() * screenRatioX * densityRatio/3));
        super.setHeight((int)(getImageBitmap().getHeight() * screenRatioY * densityRatio/1.7));
        setImageBitmap(Bitmap.createScaledBitmap(getImageBitmap(), getWidth(), getHeight(), true));

        super.setWidth((int)(getImageBitmap2().getWidth() * screenRatioX * densityRatio/ 3.5));
        super.setHeight((int)(getImageBitmap2().getHeight() * screenRatioY * densityRatio/ 3.5));
        setImageBitmap2(Bitmap.createScaledBitmap(getImageBitmap2(), (getWidth()), (getHeight()), true));
        setImageBitmap3(Bitmap.createScaledBitmap(getImageBitmap3(), (getWidth()), (getHeight()), true));
    }

    public void drawConfirmBuildingText(int x, int y, Paint paint, Canvas canvas) {
        drawMultiLineText(confirmString, x, y, paint, canvas);
    }
}
