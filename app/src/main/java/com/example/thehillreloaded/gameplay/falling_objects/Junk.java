package com.example.thehillreloaded.gameplay.falling_objects;

import static com.example.thehillreloaded.menu.DifficoltaActivity.tassoDifficolta;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import com.example.thehillreloaded.R;

public class Junk {

    private static double speed = 7, speedIncrease = 0.0004;
    private static int distance = 0, distanceRequired = 1000;
    private int x, y, width, height;
    private int dragX, dragY;
    private boolean doesIntersect = false;
    private boolean isBeingDragged = false;
    private String junkType;
    private Resources res;

    public Junk(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static void setSpeed(double newSpeed) {
        speed = newSpeed;
    }

    public static double getSpeed() {
        return speed;
    }

    public static void increaseSpeed() {
        speed = speed + speedIncrease;
    }

    public static double getSpeedIncrease() {
        return speedIncrease;
    }

    public static void setSpeedIncrease(double speedIncreaseValue) {
        speedIncrease = speedIncreaseValue;
    }

    public static void increaseDistance() {
        distance += (int) speed;
    }

    public static boolean distanceIsEnough() {
        if (distance > distanceRequired) {
            distance = 0;
            return true;
        }

        return false;
    }

    public static void resetJunkValues() {
        distance = 0;
        speed = 7;
        speedIncrease = 0.0004;
    }

    public static int getDistance() {
        return distance;
    }

    public static void setDistance(int new_distance) {
        distance = new_distance;
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

    public void setIntersection(boolean isTrue) {
        doesIntersect = isTrue;
    }

    public boolean getIntersection() {
        return doesIntersect;
    }

    public void setBeingDragged(boolean isTrue) {
        isBeingDragged = isTrue;
    }

    public boolean getBeingDragged() {
        return isBeingDragged;
    }

    public String getJunkType() {
        return junkType;
    }

    public void setJunkType(String type) {
        junkType = type;
    }

    public Bitmap getFallingObject() {
        return BitmapFactory.decodeResource(res, R.drawable.rubbish);
    }

}