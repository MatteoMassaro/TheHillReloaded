package com.example.thehillreloaded.gameplay.imageclass;

import static com.example.thehillreloaded.gameplay.GameView.densityRatio;
import static com.example.thehillreloaded.gameplay.GameView.screenRatioX;
import static com.example.thehillreloaded.gameplay.GameView.screenRatioY;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.thehillreloaded.R;

public class Missioni extends  RecImages{

    private Bitmap elencoMissioni;


    public Missioni(int x ,int y, Resources res){

        super(x, y);
        setImageBitmap(BitmapFactory.decodeResource(res, R.drawable.goal));
        setElencoMissioni(BitmapFactory.decodeResource(res, R.drawable.info_rectangle));
        super.setWidth((int)(getImageBitmap().getHeight() * screenRatioX * densityRatio/11));
        super.setHeight((int)(getImageBitmap().getWidth() * screenRatioY * densityRatio/11));
        setImageBitmap(Bitmap.createScaledBitmap(getImageBitmap(), getWidth(), getHeight(), true));
        setElencoMissioni(Bitmap.createScaledBitmap(getElencoMissioni(), getWidth(), getHeight(), true));

    }

    public void setElencoMissioni(Bitmap elencoMissioni) {
        this.elencoMissioni = elencoMissioni;
    }

    public Bitmap getElencoMissioni() {
        return elencoMissioni;
    }
}
