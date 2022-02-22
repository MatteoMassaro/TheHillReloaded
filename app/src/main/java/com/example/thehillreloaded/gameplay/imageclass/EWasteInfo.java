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

public class EWasteInfo extends InfoImages {

    private String materialLvl1Text = "Dal riciclo dei rifiuti elettronici\nsi possono recuperare materie\nprime come metallo, plastica,\nschede elettroniche o motori.\n\nFonte:\nhttps://www.ansa.it/canale\n_ambiente/notizie/focus\n_energia/2018/09/12/dove-lo\n-riciclo-raee-una-guida-in\n-10-punti-per-orientare-i\n-cittadini_dc930ac2-a577-482b\n-bd20-74de73830127.html",
            materialLvl2Text = "Da un frigorifero può essere\nestratto 1kg di rame.\n\nFonte:\nhttps://www.ecoserviz\nindustriali.it/rifiuti-raee\n-cosa-sono-perche-e-importante\n-riciclarli/",
            materialLvl3Text = "Attualmente ricicliamo\nsolamente il 20% dei rifiuti\nelettrici ed elettronici.\n\nFonte:\nhttps://www.pandasecurity.\ncom/it/mediacenter/mobile\n-news/riciclare-rifiuti\n-elettronici/";

    public EWasteInfo(int x, int y, Resources res) {
        super(x,y);

        setImageBitmap(BitmapFactory.decodeResource(res, R.drawable.ewasteunit));
        setUpgradedImageBitmap(BitmapFactory.decodeResource(res, R.drawable.ewasteunit_upgraded));
        setMaterial_lvl1(BitmapFactory.decodeResource(res, R.drawable.chip));
        setMaterial_lvl2(BitmapFactory.decodeResource(res, R.drawable.copper));
        setMaterial_lvl3(BitmapFactory.decodeResource(res, R.drawable.tv));

        super.setWidth((int)(getImageBitmap().getWidth() * screenRatioX * densityRatio/8.21));
        super.setHeight((int)(getImageBitmap().getHeight() * screenRatioY * densityRatio/8.21));
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