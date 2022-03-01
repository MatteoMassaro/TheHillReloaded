package com.example.thehillreloaded.gameplay.imageclass;

import static com.example.thehillreloaded.menu.MenuActivity.densityRatio;
import static com.example.thehillreloaded.menu.MenuActivity.screenRatioX;
import static com.example.thehillreloaded.menu.MenuActivity.screenRatioY;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.thehillreloaded.R;

public class AluminumInfo extends InfoImages {

    private String materialLvl1Text = "L'alluminio è un materiale\nmolto presente in  natura e\nriciclabile all'infinito.\n\nFonte:\nhttps://wisesociety.it/\nambiente-e-scienza/riciclo\n-alluminio-cial-economia\n-circolare/",
            materialLvl2Text = "Riciclando l'alluminio si\nrisparmia il 95% dell'energia\nnecessaria per produrre\nalluminio nuovo.\n\nFonte:\nhttps://www.slowfood.it/\nlalluminio-riciclabile-al\n-100-una-ricchezza-infinita/",
            materialLvl3Text = "Le bombolette spray possono\nessere riciclate al 100% e\nall'infinito.\n\nFonte:\nhttps://www.eso.it/rifiuti\n-industriali/smaltimento\n-bombolette-spray";

    public AluminumInfo(int x, int y, Resources res) {
        super(x,y);

        setImageBitmap(BitmapFactory.decodeResource(res, R.drawable.aluminumunit));
        setUpgradedImageBitmap(BitmapFactory.decodeResource(res, R.drawable.aluminumunit_upgraded));
        setMaterial_lvl1(BitmapFactory.decodeResource(res, R.drawable.cola_can));
        setMaterial_lvl2(BitmapFactory.decodeResource(res, R.drawable.soup_can));
        setMaterial_lvl3(BitmapFactory.decodeResource(res, R.drawable.spray));

        super.setWidth((int)(getImageBitmap().getWidth() * screenRatioX * densityRatio/8.19));
        super.setHeight((int)(getImageBitmap().getHeight() * screenRatioY * densityRatio/8.19));
        setImageBitmap(Bitmap.createScaledBitmap(getImageBitmap(), getWidth(), getHeight(), true));
        setUpgradedImageBitmap(Bitmap.createScaledBitmap(getUpgradedImageBitmap(), getWidth(), getHeight(), true));

        super.setWidth((int)(getMaterial_lvl1().getWidth() * screenRatioX * densityRatio/ 2.75));
        super.setHeight((int)(getMaterial_lvl1().getHeight() * screenRatioY * densityRatio/ 2.75));
        setMaterial_lvl1(Bitmap.createScaledBitmap(getMaterial_lvl1(), (getWidth()), (getHeight()), true));
        setMaterial_lvl2(Bitmap.createScaledBitmap(getMaterial_lvl2(), (getWidth()), (getHeight()), true));
        setMaterial_lvl3(Bitmap.createScaledBitmap(getMaterial_lvl3(), (getWidth()), (getHeight()), true));
    }

    @Override
    public void drawMaterialLvl1Text(int x, int y, Paint paint, Canvas canvas) {
        drawMultiLineText(materialLvl1Text, x, y, paint, canvas);
    }

    @Override
    public void drawMaterialLvl2Text(int x, int y, Paint paint, Canvas canvas) {
        drawMultiLineText(materialLvl2Text, x, y, paint, canvas);
    }

    @Override
    public void drawMaterialLvl3Text(int x, int y, Paint paint, Canvas canvas) {
        drawMultiLineText(materialLvl3Text, x, y, paint, canvas);
    }


}