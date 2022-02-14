package com.example.thehillreloaded.gameplay.junk;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;

import com.example.thehillreloaded.R;

public class Junk {

    private static double speed = 9, speedIncrease = 0.0018;
    private static int distance = 0;
    private int x, y, width, height;
    private int dragX, dragY;
    private boolean doesIntersect = false;
    private boolean isBeingDragged = false;
    private Resources res;

    public Junk(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static void setSpeed(int newSpeed) {
        speed = newSpeed;
    }

    public static double getSpeed() {
        return speed;
    }

    public static void increaseSpeed() {
        speed = speed + speedIncrease;
    }

    public static void setSpeedIncrease(double speedIncreaseValue) {
        speedIncrease = speedIncreaseValue;
    }

    public static void increaseDistance() {
        distance += (int) speed;
    }

    public static boolean distanceIsEnough() {
        if (distance > 1600) {
            distance = 0;
            return true;
        }

        return false;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getDragX() {
        return dragX;
    }

    public void setDragX(int x) {
        this.dragX = x;
    }

    public int getDragY() {
        return dragY;
    }

    public void setDragY(int y) {
        this.dragY = y;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getWidth() {
        return width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getHeight() {
        return height;
    }

    public Rect getCollisionShape () {
        return new Rect(getX(), getY(), getX() + getWidth(), getY() + getHeight());
    }

    public void setIntersectionTrue() {
        doesIntersect = true;
    }

    public void setIntersectionFalse() {
        doesIntersect = false;
    }

    public boolean getIntersection() {
        return doesIntersect;
    }

    public void setBeingDraggedTrue() {
        isBeingDragged = true;
    }

    public void setBeingDraggedFalse() {
        isBeingDragged = false;
    }

    public boolean getBeingDragged() {
        return isBeingDragged;
    }


    public Bitmap getJunk() {
        return BitmapFactory.decodeResource(res, R.drawable.rubbish);
    }
}