package com.example.thehillreloaded.gameplay.recycle;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.widget.ProgressBar;

import com.example.thehillreloaded.R;

public class RecUnit {

    private static double recyclingSpeed = 10;
    private int recTotal = 0, maxRecTotal = 900;
    private int unitPoints = 0, unitPrice;
    private static int upgradePrice = 5;
    private String unitType;
    private boolean isUnlocked = false;
    private boolean isUpgraded = false;
    private boolean isRecycling = false;
    private boolean isCheckingInfo = false;
    private int state = 0;
    private int recycledUnit = 0, recycledUnitUpgraded = 0;
    private int x, y, width, height;
    private Resources res;

    public RecUnit(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static void setSpeed(int newSpeed) {
        recyclingSpeed = newSpeed;
    }

    public static double getRecyclingSpeed() {
        return recyclingSpeed;
    }

    public void increaseRecTotal() {
        recTotal += (int) recyclingSpeed;
    }

    public int getRecTotal() {
        return recTotal;
    }

    public void resetRecTotal() {
        recTotal = 0;
    }

    public boolean recTotalIsEnough() {
        if (recTotal > 900) {
            recTotal = 0;
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

    public void setUnitType(String unitType) {
        this.unitType = unitType;
    }

    public String getUnitType() {
        return unitType;
    }

    public boolean getIsUnlocked() {
        return isUnlocked;
    }

    public void setIsUnlockedToTrue() {
        isUnlocked = true;
    }

    public int getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(int price) {
        this.unitPrice = price;
    }

    public static int getUpgradePrice() {
        return upgradePrice;
    }

    public void recycledUnitPlus() {
        recycledUnitUpgraded++;
    }

    public int getRecycledUnit() {
        return recycledUnitUpgraded;
    }

    public void recycledUnitUpgradedPlus() {
        recycledUnitUpgraded++;
    }

    public int getRecycledUnitUpgraded() {
        return recycledUnitUpgraded;
    }


    public boolean getIsUpgraded() {
        return isUpgraded;
    }

    public void setIsUpgraded(boolean isTrue) {
        isUpgraded = isTrue;
    }

    public boolean getIsRecycling() {
        return isRecycling;
    }

    public void setIsRecycling(boolean isTrue) {
        isRecycling = isTrue;
    }

    public boolean getIsCheckingInfo() {
        return isCheckingInfo;
    }

    public void setIsCheckingInfo(boolean isTrue) {
        isCheckingInfo = isTrue;
    }

    public int getUnitPoints() {
        return unitPoints;
    }

    public void unitPointsPlus() {
        unitPoints++;
    }

    public void reduceUnitPoints(int reduction) {
        unitPoints -= reduction;
    }

    public int getState() {
        return state;
    }

    public void increaseState() {
        state++;
    }

    public void resetState() {
        state = 0;
    }

    public Bitmap getRecUnit() {
        return BitmapFactory.decodeResource(res, R.drawable.rubbish);
    }

    public Bitmap getRecUnitState2() {
        return BitmapFactory.decodeResource(res, R.drawable.rubbish);
    }

    public Bitmap getRecUnitState3() {
        return BitmapFactory.decodeResource(res, R.drawable.rubbish);
    }

    public Bitmap getRecUnitState4() {
        return BitmapFactory.decodeResource(res, R.drawable.rubbish);
    }

    public Bitmap getRecUnitLvl2() {
        return BitmapFactory.decodeResource(res, R.drawable.rubbish);
    }

    public Bitmap getRecUnitLvl2State2() {
        return BitmapFactory.decodeResource(res, R.drawable.rubbish);
    }

    public Bitmap getRecUnitLvl2State3() {
        return BitmapFactory.decodeResource(res, R.drawable.rubbish);
    }

    public void rinnovaTasso() {

    }

    public int getMaxRecTotal() {
        return maxRecTotal;
    }

    public Bitmap getUnitPointsBitmap() {
        return BitmapFactory.decodeResource(res, R.drawable.unitpoints);
    }
}
