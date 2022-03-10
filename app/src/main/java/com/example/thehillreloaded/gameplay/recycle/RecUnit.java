package com.example.thehillreloaded.gameplay.recycle;

import static com.example.thehillreloaded.menu.DifficoltaActivity.tassoDifficolta;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.widget.ProgressBar;

import com.example.thehillreloaded.R;

public class RecUnit {

    private static double recyclingSpeed = 10;
    private int recTotal = 0, recTotalUpgraded = 0, maxRecTotal = 900;
    private int unitPoints = 0, unitPrice;
    private int upgradePrice = 5;
    private int recycleScore = 0;
    private int junkBeingRecycled = 0;
    private String unitType;
    private boolean isUnlocked = false, isUnlocking = false;
    private boolean isUpgraded = false;
    private boolean isRecycling = false;
    private boolean isCheckingInfo = false;
    private boolean isPoweredUp = false;
    private int state = 0;
    private int recycledUnit = 0, recycledUnitUpgraded = 0, maxRecycledUnitUpgraded = 23;
    private int x, y, width, height;
    private Resources res;

    public RecUnit(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static void setRecyclingSpeed(double newSpeed) {
        recyclingSpeed = newSpeed;
    }

    public static double getRecyclingSpeed() {
        return recyclingSpeed;
    }

    public static void resetRecyclingSpeed() {
        recyclingSpeed = 10;
    }

    public void increaseRecTotal() {
        recTotal += (int) recyclingSpeed;
    }

    public void increaseRecTotalUpgraded() {
        recTotalUpgraded += (int) recyclingSpeed;
    }

    public int getRecTotal() {
        return recTotal;
    }

    public int getRecTotalUpgraded() {
        return recTotalUpgraded;
    }

    public void setRecTotal(int recTot) {
        this.recTotal = recTot;
    }

    public void setRecTotalUpgraded(int recTotUpgraded) {
        this.recTotalUpgraded = recTotUpgraded;
    }

    public void resetRecTotal() {
        recTotal = 0;
    }

    public void resetRecTotalUpgraded() {
        recTotalUpgraded = 0;
    }

    public boolean recTotalIsEnough() {
        if (recTotal > maxRecTotal) {
            recTotal = 0;
            return true;
        }

        return false;
    }

    public boolean recTotalUpgradedIsEnough() {
        if (recTotalUpgraded > maxRecTotal) {
            recTotalUpgraded = 0;
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

    public boolean getIsUnlocking() {
        return isUnlocking;
    }

    public void setIsUnlocking(boolean isTrue) {
        isUnlocking = isTrue;
    }

    public int getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(int price) {
        this.unitPrice = price;
    }

    public int getUpgradePrice() {
        return upgradePrice;
    }

    public void setRecycleScore(int recScore) {
        recycleScore = recScore;
    }

    public int getRecycleScore() {
        return recycleScore;
    }

    public void recycledUnitPlus() {
        recycledUnit++;
    }

    public int getRecycledUnit() {
        return recycledUnit;
    }

    public void setRecycledUnit(int recycledUnit) {
        this.recycledUnit = recycledUnit;
    }

    public void recycledUnitUpgradedPlus() {
        recycledUnitUpgraded++;
    }

    public void recycledUnitUpgradedReset() {
        recycledUnitUpgraded = 0;
    }

    public int getRecycledUnitUpgraded() {
        return recycledUnitUpgraded;
    }

    public void setRecycledUnitUpgraded(int recycledUnitUpgraded) {
        this.recycledUnitUpgraded = recycledUnitUpgraded;
    }

    public int getMaxRecycledUnitUpgraded() {
        return maxRecycledUnitUpgraded;
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

    public int getJunkBeingRecycled() {
        return junkBeingRecycled;
    }

    public void junkBeingRecycledPlus() {
        junkBeingRecycled++;
    }

    public void junkBeingRecycledMinus() {
        junkBeingRecycled--;
    }

    public void setJunkBeingRecycled(int junkBeingRecycled) {
        this.junkBeingRecycled = junkBeingRecycled;
    }

    public boolean getIsCheckingInfo() {
        return isCheckingInfo;
    }

    public void setIsCheckingInfo(boolean isTrue) {
        isCheckingInfo = isTrue;
    }

    public boolean getIsPoweredUp() {
        return isPoweredUp;
    }

    public void setIsPoweredUp(boolean isTrue) {
        isPoweredUp = isTrue;
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

    public void setUnitPoints(int unitPoints) {
        this.unitPoints = unitPoints;
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

    public void setState(int state) {
        this.state = state;
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
}
