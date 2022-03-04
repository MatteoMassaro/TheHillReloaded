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

public class PaperInfo extends InfoImages {

    private String materialLvl1Text = "La percentuale di carta\nriciclata usata per produrre\ngiornali varia tra l'85% e il\n100%.\n\nFonte:\nhttps://www.perlen.ch/wp\n-content/uploads/files/\nRessourcen_Recycling-Prozess\n_i.pdf",
            materialLvl2Text = "Il cartone può essere riciclato\npiù di 25 volte, senza perdere\nla sua integrità.\n\nFonte:\nhttps://www.askanews.it/\neconomia/2022/01/17/pro\n-carton-packaging-in-carta\n-e-cartone-riciclabili-pi%c3%b9\n-di-25-volte-pn_20220117_00128/",
            materialLvl3Text = "In provincia di Bari viene\nprodotta una linea di borse\nin carta riciclata. Sono le\nKimoshi, dalla caratteristica\nforma versatile che permette al\ncliente di avere più soluzioni in\nun’unica borsa. La Kimoshi\ninfatti può essere zaino, pochette\n o borsa.\n\nFonte:\nhttps://www.comunieco\ncampioni.org/la-puglia\n-mette-in-mostra-lindustria\n-del-riciclo-di-carta-e-cartone/";


    public PaperInfo(int x, int y, Resources res) {
        super(x,y);

        setImageBitmap(BitmapFactory.decodeResource(res, R.drawable.paperunit));
        setUpgradedImageBitmap(BitmapFactory.decodeResource(res, R.drawable.paperunit_upgraded));
        setMaterial_lvl1(BitmapFactory.decodeResource(res, R.drawable.newspaper));
        setMaterial_lvl2(BitmapFactory.decodeResource(res, R.drawable.box));
        setMaterial_lvl3(BitmapFactory.decodeResource(res, R.drawable.paper_bag));

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