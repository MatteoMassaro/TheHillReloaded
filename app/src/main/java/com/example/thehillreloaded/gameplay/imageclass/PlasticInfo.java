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

public class PlasticInfo extends InfoImages {

    private String materialLvl1Text = "A seconda del materiale di\ncui sono fatti, i sacchetti di\nplastica possono essere\nriciclati più o meno facilmente.\n\nFonte:\nhttps://it.wikipedia.org/\nwiki/Sacchetto_di_plastica",
            materialLvl2Text = "Ogni minuto vengono vendute\n1 milione di bottiglie di plastica\nnel mondo e solamente il\n10% di queste viene riclato.\n\nFonte:\nhttps://ecocentrica.it/5-cose\n-da-sapere-sulle-bottiglie\n-di-plastica/",
            materialLvl3Text = "Ogni bottiglia può essere\nriciclata infinite volte per\ndiventare una bottiglia nuova\ne identica all'originale.\n\nFonte:\nhttps://www.inabottle.it/it/\nambiente/sai-come-funziona\n-il-riciclo-delle-bottiglie-di\n-plastica";

    public PlasticInfo(int x, int y, Resources res) {
        super(x,y);

        setImageBitmap(BitmapFactory.decodeResource(res, R.drawable.plasticunit));
        setUpgradedImageBitmap(BitmapFactory.decodeResource(res, R.drawable.plasticunit_upgraded));
        setMaterial_lvl1(BitmapFactory.decodeResource(res, R.drawable.plastic_bag));
        setMaterial_lvl2(BitmapFactory.decodeResource(res, R.drawable.plastic_bottle_small));
        setMaterial_lvl3(BitmapFactory.decodeResource(res, R.drawable.plastic_bottle_big));

        super.setWidth((int)(getImageBitmap().getWidth() * screenRatioX * densityRatio/7.64));
        super.setHeight((int)(getImageBitmap().getHeight() * screenRatioY * densityRatio/7.64));
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
