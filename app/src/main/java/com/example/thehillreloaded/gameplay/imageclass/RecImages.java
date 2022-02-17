package com.example.thehillreloaded.gameplay.imageclass;

import android.graphics.Bitmap;

public class RecImages {

    private int x, y, width, height;
    private Bitmap imageBitmap;
    private Bitmap imageBitmap2;
    private Bitmap elencoMissioni;
    private boolean clicked = false;
    private int sunnyPoints = 0;

    public RecImages(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Bitmap getImageBitmap() {
        return imageBitmap;
    }

    public void setImageBitmap(Bitmap bitmap) {
        imageBitmap = bitmap;
    }

    public int getSunnyPoints() {
        return sunnyPoints;
    }

    public void setSunnyPoints(int sunny){
        this.sunnyPoints = sunny;
    }

    public Bitmap getElencoMissioni(){ return elencoMissioni;}

    public void setElencoMissioni(Bitmap elencoMissioni){ this.elencoMissioni = elencoMissioni;}

    public void setClicked(boolean clicked) {
        this.clicked = clicked;
    }

    public boolean isClicked() {
        return clicked;
    }

    public Bitmap getImageBitmap2() {
        return imageBitmap2;
    }

    public void setImageBitmap2(Bitmap imageBitmap2) {
        this.imageBitmap2 = imageBitmap2;
    }
}
