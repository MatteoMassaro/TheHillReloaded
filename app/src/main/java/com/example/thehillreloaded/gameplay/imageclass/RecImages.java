package com.example.thehillreloaded.gameplay.imageclass;

import android.graphics.Bitmap;

public class RecImages {

    private int x, y, width, height;
    private Bitmap imageBitmap;

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
}
