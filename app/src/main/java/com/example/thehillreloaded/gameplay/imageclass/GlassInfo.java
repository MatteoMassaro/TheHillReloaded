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

public class GlassInfo extends InfoImages {

    private String materialLvl1Text = "Il vetro può essere riutilizzato\ntantissime volte: una bottiglia\npuò avere fino a 50 vite.\n\nFonte:\nhttps://ecomalu.it/il-riciclo\n-del-vetro/",
            materialLvl2Text = "Ogni bicchiere di vetro\nriciclato può essere composto\nfino al 90% di rottame di\nvetro.\n\nFonte:\nhttps://coreve.it/il-ciclo-del\n-riciclo/",
            materialLvl3Text = "Grazie al riciclo del vetro, le\nemissioni di CO2 sono diminuite\ndel 70% negli ultimi 25 anni.\n\nFonte:\nhttps://www.vetropack.it/it/\nil-vetro/riciclo/";

    public GlassInfo(int x, int y, Resources res) {
        super(x,y);

        setImageBitmap(BitmapFactory.decodeResource(res, R.drawable.glassunit));
        setUpgradedImageBitmap(BitmapFactory.decodeResource(res, R.drawable.glassunit_upgraded));
        setMaterial_lvl1(BitmapFactory.decodeResource(res, R.drawable.glass_bottle));
        setMaterial_lvl2(BitmapFactory.decodeResource(res, R.drawable.glass_cup));
        setMaterial_lvl3(BitmapFactory.decodeResource(res, R.drawable.jar));

        super.setWidth((int)(getImageBitmap().getWidth() * screenRatioX * densityRatio/7.66));
        super.setHeight((int)(getImageBitmap().getHeight() * screenRatioY * densityRatio/7.66));
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
