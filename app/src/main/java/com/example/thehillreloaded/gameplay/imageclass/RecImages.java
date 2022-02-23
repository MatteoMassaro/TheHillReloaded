package com.example.thehillreloaded.gameplay.imageclass;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class RecImages {

    private int x, y, width, height;
    private Bitmap imageBitmap, imageBitmap2, imageBitmap3;
    private boolean clicked = false;

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

    public Bitmap getImageBitmap3() {
        return imageBitmap3;
    }

    public void setImageBitmap3(Bitmap imageBitmap3) {
        this.imageBitmap3 = imageBitmap3;
    }

    public void drawMultiLineText(String string, int x, int y, Paint paint, Canvas canvas) {
        for (String line: string.split("\n")) {
            canvas.drawText(line, x, y, paint);
            y += paint.descent() - paint.ascent();
        }
    }
}
