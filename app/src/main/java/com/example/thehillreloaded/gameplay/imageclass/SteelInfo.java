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

public class SteelInfo extends InfoImages {

    private String materialLvl1Text = "Con 200 tappi di acciaio\nriciclati è possibile ottenere\nuna chiave inglese.\n\nFonte:\nhttp://alessandriaricicla.it/\nraccolta-differenziata-dell\n-acciaio/",
            materialLvl2Text = "L'acciaio è il materiale più\nriciclato al mondo ed è\nriciclabile infinite volte.\n\nFonte:\nhttps://www.promozione\nacciaio.it/cms/it6637\n-economia-circolare-acciaio\n-e-riciclo.asp",
            materialLvl3Text = "Con 1500 scatolette di tonno\nriciclate è possibile ottenere\nil telaio di una bicicletta.\n\nFonte:\nhttp://alessandriaricicla.it/\nraccolta-differenziata-dell-\nacciaio/";

    public SteelInfo(int x, int y, Resources res) {
        super(x,y);

        setImageBitmap(BitmapFactory.decodeResource(res, R.drawable.steelunit));
        setUpgradedImageBitmap(BitmapFactory.decodeResource(res, R.drawable.steelunit_upgraded));
        setMaterial_lvl1(BitmapFactory.decodeResource(res, R.drawable.wrench));
        setMaterial_lvl2(BitmapFactory.decodeResource(res, R.drawable.steel_pot));
        setMaterial_lvl3(BitmapFactory.decodeResource(res, R.drawable.bike));

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
