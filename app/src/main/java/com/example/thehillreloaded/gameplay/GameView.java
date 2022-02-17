package com.example.thehillreloaded.gameplay;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceView;

import androidx.core.content.res.ResourcesCompat;

import com.example.thehillreloaded.R;
import com.example.thehillreloaded.gameplay.imageclass.AluminumInfo;
import com.example.thehillreloaded.gameplay.imageclass.EWasteInfo;
import com.example.thehillreloaded.gameplay.imageclass.GameBar;
import com.example.thehillreloaded.gameplay.imageclass.GlassInfo;
import com.example.thehillreloaded.gameplay.imageclass.InfoImages;
import com.example.thehillreloaded.gameplay.imageclass.Missioni;
import com.example.thehillreloaded.gameplay.imageclass.PaperInfo;
import com.example.thehillreloaded.gameplay.imageclass.Pause;
import com.example.thehillreloaded.gameplay.imageclass.PlasticInfo;
import com.example.thehillreloaded.gameplay.imageclass.RecImages;
import com.example.thehillreloaded.gameplay.imageclass.SteelInfo;
import com.example.thehillreloaded.gameplay.imageclass.SunnyPoints;
import com.example.thehillreloaded.gameplay.imageclass.UnitInfo;
import com.example.thehillreloaded.gameplay.imageclass.UnitPoints;
import com.example.thehillreloaded.gameplay.imageclass.UnlockableUnit;
import com.example.thehillreloaded.gameplay.imageclass.Upgrade;
import com.example.thehillreloaded.gameplay.junk.Aluminum;
import com.example.thehillreloaded.gameplay.junk.EWaste;
import com.example.thehillreloaded.gameplay.junk.Glass;
import com.example.thehillreloaded.gameplay.junk.HazarWaste;
import com.example.thehillreloaded.gameplay.junk.Junk;
import com.example.thehillreloaded.gameplay.junk.Paper;
import com.example.thehillreloaded.gameplay.junk.Plastic;
import com.example.thehillreloaded.gameplay.junk.Steel;
import com.example.thehillreloaded.gameplay.recycle.AluminumUnit;
import com.example.thehillreloaded.gameplay.recycle.EWasteUnit;
import com.example.thehillreloaded.gameplay.recycle.GlassUnit;
import com.example.thehillreloaded.gameplay.recycle.Incinerator;
import com.example.thehillreloaded.gameplay.recycle.PaperUnit;
import com.example.thehillreloaded.gameplay.recycle.PlasticUnit;
import com.example.thehillreloaded.gameplay.recycle.RecUnit;
import com.example.thehillreloaded.gameplay.recycle.SteelUnit;
import com.example.thehillreloaded.menu.MusicPlayer;

import java.util.ArrayList;
import java.util.Random;

public class GameView extends SurfaceView implements Runnable {

    //Variabili
    private Thread thread;
    private boolean isPlaying;
    private static int nJunk;
    private double designX = 1080f, designY = 2072f, designDensity = 2.75;
    private int screenX, screenY, spawnBoundX, spawnY;
    public static double screenRatioX, screenRatioY, densityRatio;
    private Background background;
    private Paint paint, transparentPaint, rectPaint, strokePaint, textPaint;
    private ArrayList<Junk> junkList = new ArrayList<>();
    private ArrayList<Rect> rectList = new ArrayList<>();
    private ArrayList<RecUnit> recUnitList = new ArrayList<>();
    private ArrayList<RecImages> unitPointsRecImageList = new ArrayList<>();
    private ArrayList<RecImages> unlockableUnitList = new ArrayList<>();
    private ArrayList<RecImages> unitPointsInfoList = new ArrayList<>();
    private ArrayList<RecImages> sunnyPointsInfoList = new ArrayList<>();
    private ArrayList<InfoImages> infoImagesList = new ArrayList<>();
    private UnitInfo unitInfo;
    private Upgrade upgrade;
    private UnitPoints unitPoints;
    private RecImages sunnyPoints, missioni, pause;
    private GameBar gameBar;
    private double qualcosa;

    //Setta le view adattandole in base allo schermo
    public GameView(Context context, int screenX, int screenY, float density) {
        super(context);
        this.screenX = screenX;
        this.screenY = screenY;
        screenRatioX = screenX/designX;
        screenRatioY = screenY/designY;
        densityRatio = designDensity/density;
        spawnBoundX = (screenX - (int) (247f * screenRatioX * densityRatio));
        spawnY = screenY * 6/11;
        background = new Background(screenX, screenY, getResources());

        gameBar = new GameBar(screenX, screenY, getResources());
        gameBar.setMissioni();
        sunnyPoints = new SunnyPoints((int)(30*screenRatioX), (int)(10*screenRatioY), getResources());
        missioni = new Missioni((int)(30*screenRatioX), (int)(10*screenRatioY), getResources());
        pause = new Pause((int)(30*screenRatioX), (int)(10*screenRatioY), getResources());

        Random random = new Random();

        recUnitList.add(new GlassUnit((int) (-19 * screenRatioX), (int) (88 * screenRatioY), getResources()));
        recUnitList.add(new PaperUnit((int) (-21 * screenRatioX), (int) (490 * screenRatioY), getResources()));
        recUnitList.add(new AluminumUnit((int) (0 * screenRatioX), (int) (789 * screenRatioY), getResources()));
        recUnitList.add(new SteelUnit((int) (590 * screenRatioX), (int) (81 * screenRatioY), getResources()));
        recUnitList.add(new PlasticUnit((int) (639 * screenRatioX), (int) (452 * screenRatioY), getResources()));
        recUnitList.add(new EWasteUnit((int) (533 * screenRatioX), (int) (811 * screenRatioY), getResources()));
        recUnitList.add(new Incinerator((int) (381 * screenRatioX), (int) (388 * screenRatioY), getResources()));

        rectList.add(new Rect((int)(95*screenRatioX), (int)(427*screenRatioY), (int)(304*screenRatioX), (int)(444*screenRatioY)));
        rectList.add(new Rect((int)(95*screenRatioX), (int)(764*screenRatioY), (int)(304*screenRatioX), (int)(781*screenRatioY)));
        rectList.add(new Rect((int)(95*screenRatioX), (int)(1085*screenRatioY), (int)(304*screenRatioX), (int)(1102*screenRatioY)));
        rectList.add(new Rect((int)(799*screenRatioX), (int)(430*screenRatioY), (int)(1008*screenRatioX), (int)(447*screenRatioY)));
        rectList.add(new Rect((int)(799*screenRatioX), (int)(781*screenRatioY), (int)(1008*screenRatioX), (int)(798*screenRatioY)));
        rectList.add(new Rect((int)(799*screenRatioX), (int)(1077*screenRatioY), (int)(1008*screenRatioX), (int)(1094*screenRatioY)));
        rectList.add(new Rect((int)(451*screenRatioX), (int)(787*screenRatioY), (int)(660*screenRatioX), (int)(804*screenRatioY)));

        unitPointsRecImageList.add(new UnitPoints((int)(11.42*screenRatioX), (int)(123.48*screenRatioY), getResources()));
        unitPointsRecImageList.add(new UnitPoints((int)(11.42*screenRatioX), (int)(512*screenRatioY), getResources()));
        unitPointsRecImageList.add(new UnitPoints((int)(11.42*screenRatioX), (int)(829*screenRatioY), getResources()));
        unitPointsRecImageList.add(new UnitPoints((int)(693*screenRatioX), (int)(123.48*screenRatioY), getResources()));
        unitPointsRecImageList.add(new UnitPoints((int)(746*screenRatioX), (int)(529*screenRatioY), getResources()));
        unitPointsRecImageList.add(new UnitPoints((int)(609*screenRatioX), (int)(847*screenRatioY), getResources()));

        unlockableUnitList.add(new UnlockableUnit((int)(81*screenRatioX),(int)(670*screenRatioY), getResources()));
        unlockableUnitList.add(new UnlockableUnit((int)(78*screenRatioX),(int)(988*screenRatioY), getResources()));
        unlockableUnitList.add(new UnlockableUnit((int)(760*screenRatioX),(int)(317.52*screenRatioY), getResources()));
        unlockableUnitList.add(new UnlockableUnit((int)(760*screenRatioX),(int)(670*screenRatioY), getResources()));
        unlockableUnitList.add(new UnlockableUnit((int)(720*screenRatioX),(int)(988*screenRatioY), getResources()));

        unitInfo = new UnitInfo(0, (int) (1235 * screenRatioY), getResources());
        upgrade = new Upgrade(unitInfo.getX() + (int)(316*screenRatioX), unitInfo.getY() + (int)(388*screenRatioY), getResources());
        unitPoints = new UnitPoints(unitInfo.getX() + (int)(228.34*screenRatioX), unitInfo.getY() + (int)(423.36*screenRatioY), getResources());


        unitPointsInfoList.add(new UnitPoints(unitInfo.getX() + (int)(239.75*screenRatioX), unitInfo.getY() + (int)(699*screenRatioY), getResources()));
        unitPointsInfoList.add(new UnitPoints(unitInfo.getX() + (int)(475.7*screenRatioX), unitInfo.getY() + (int)(699*screenRatioY), getResources()));
        unitPointsInfoList.add(new UnitPoints(unitInfo.getX() + (int)(711.65*screenRatioX), unitInfo.getY() + (int)(699*screenRatioY), getResources()));

        sunnyPointsInfoList.add(new SunnyPoints(unitInfo.getX() + (int)(239.75*screenRatioX), unitInfo.getY() + (int)(760*screenRatioY), getResources()));
        sunnyPointsInfoList.add(new SunnyPoints(unitInfo.getX() + (int)(475.7*screenRatioX), unitInfo.getY() + (int)(760*screenRatioY), getResources()));
        sunnyPointsInfoList.add(new SunnyPoints(unitInfo.getX() + (int)(711.65*screenRatioX), unitInfo.getY() + (int)(760*screenRatioY), getResources()));

        infoImagesList.add(new GlassInfo(unitInfo.getX() + (int)(228.34*screenRatioX), unitInfo.getY() + (int)(170*screenRatioY), getResources()));
        infoImagesList.add(new PaperInfo(unitInfo.getX() + (int)(217*screenRatioX), unitInfo.getY() + (int)(199*screenRatioY), getResources()));
        infoImagesList.add(new AluminumInfo(unitInfo.getX() + (int)(232*screenRatioX), unitInfo.getY() + (int)(189*screenRatioY), getResources()));
        infoImagesList.add(new SteelInfo(unitInfo.getX() + (int)(217*screenRatioX), unitInfo.getY() + (int)(185*screenRatioY), getResources()));
        infoImagesList.add(new PlasticInfo(unitInfo.getX() + (int)(213*screenRatioX), unitInfo.getY() + (int)(184*screenRatioY), getResources()));
        infoImagesList.add(new EWasteInfo(unitInfo.getX() + (int)(209*screenRatioX), unitInfo.getY() + (int)(201*screenRatioY), getResources()));


        junkList.add(new Glass((random.nextInt(spawnBoundX) + (int) (15.22 * screenRatioX)), spawnY, getResources()));

        recUnitList.get(1).setIsUnlockedToTrue();
        recUnitList.get(2).setIsUnlockedToTrue();
        recUnitList.get(3).setIsUnlockedToTrue();
        recUnitList.get(4).setIsUnlockedToTrue();
        recUnitList.get(5).setIsUnlockedToTrue();

        qualcosa = Math.sqrt(Math.pow(screenRatioX + screenRatioY, 2));

        paint = new Paint();
        paint.setTextSize(64/ (float) densityRatio);
        paint.setTypeface(ResourcesCompat.getFont(context, R.font.bevan));
        transparentPaint = new Paint();
        transparentPaint.setAlpha(100);
        rectPaint = new Paint();
        rectPaint.setColor(Color.RED);
        strokePaint = new Paint();
        strokePaint.setStrokeWidth(10/(float)(densityRatio));
        strokePaint.setStyle(Paint.Style.STROKE);
        textPaint = new Paint();
        textPaint.setTextSize(32/(float) densityRatio);
        textPaint.setTypeface(ResourcesCompat.getFont(context, R.font.bevan));
    }


    @Override
    public void run() {

        while (isPlaying) {
            update();
            draw();
            sleep();
        }
    }

    //Aggiorna i blocchi ridisegnandoli in base al tasso di spawn
    public void update() {

        if (Junk.distanceIsEnough()) {
            Random random = new Random();
            double tassoTotale = Glass.getTasso() + Paper.getTasso() + Aluminum.getTasso() + Steel.getTasso() + Plastic.getTasso() + EWaste.getTasso() + HazarWaste.getTasso();

            double num = tassoTotale * random.nextDouble();

            if (num <= Glass.getTasso()) {
                junkList.add(new Glass((random.nextInt(spawnBoundX) + (int) (7.6 * screenRatioX)), spawnY, getResources()));

            } else if (num > Glass.getTasso() && num <= Glass.getTasso() + Paper.getTasso()) {
                junkList.add(new Paper((random.nextInt(spawnBoundX) + (int) (7.6 * screenRatioX)), spawnY, getResources()));

            } else if (num > Glass.getTasso() + Paper.getTasso() && num <= Glass.getTasso() + Paper.getTasso() + Aluminum.getTasso()) {
                junkList.add(new Aluminum((random.nextInt(spawnBoundX) + (int) (7.6 * screenRatioX)), spawnY, getResources()));

            } else if (num > Glass.getTasso() + Paper.getTasso() + Aluminum.getTasso() && num <= Glass.getTasso() + Paper.getTasso() + Aluminum.getTasso() + HazarWaste.getTasso()) {
                junkList.add(new HazarWaste((random.nextInt(spawnBoundX) + (int) (7.6 * screenRatioX)), spawnY, getResources()));

            } else if (num > Glass.getTasso() + Paper.getTasso() + Aluminum.getTasso() + HazarWaste.getTasso() && num <= tassoTotale - Plastic.getTasso() - HazarWaste.getTasso()) {
                junkList.add(new Steel((random.nextInt(spawnBoundX) + (int) (7.6 * screenRatioX)), spawnY, getResources()));

            } else if (num > tassoTotale - Plastic.getTasso() - HazarWaste.getTasso() && num <= tassoTotale - HazarWaste.getTasso()) {
                junkList.add(new Plastic((random.nextInt(spawnBoundX) + (int) (7.6 * screenRatioX)), spawnY, getResources()));

            } else {
                junkList.add(new EWaste((random.nextInt(spawnBoundX) + (int) (7.6 * screenRatioX)), spawnY, getResources()));
            }
        }

        for (int x = junkList.size() - 1; x >= 0; x--) {
            Junk junk = junkList.get(x);

            for (int y = x-1; y >= 0; y--) {
                Junk otherJunk = junkList.get(y);

                if (Rect.intersects(junk.getCollisionShape(), otherJunk.getCollisionShape())) {
                    junk.setIntersectionTrue();
                    junk.setY(otherJunk.getY() - junk.getHeight());
                }
            }

            if (!junk.getIntersection() && junk.getY() < screenY - (int) (24.7 * screenRatioY) - junk.getHeight()) {
                junk.setY(junk.getY() + (int) Junk.getSpeed());
            }

            if (junk.getY() >= screenY - (int) (24.7 * screenRatioY)) {
                junk.setY(screenY - (int) (24.7 * screenRatioY) - junk.getHeight());
            }
        }

        //Animazione unità riciclo
        for (int x = 0; x < recUnitList.size(); x++) {
            RecUnit recUnit = recUnitList.get(x);

            if (recUnit.getIsRecycling()) {
                if(!MusicPlayer.isPlayingEffect){
                    MusicPlayer.playEffetti(getContext(), R.raw.incinerator_sound);
                    MusicPlayer.loopEffetti();
                }
                recUnit.increaseState();

                if (recUnit.getState() == 9 && !(recUnit instanceof Incinerator) || recUnit.getState() == 12 && recUnit instanceof Incinerator) {
                    recUnit.resetState();
                }
                recUnit.increaseRecTotal();

                if(recUnit.recTotalIsEnough()) {
                    recUnit.unitPointsPlus();
                    recUnit.recycledUnitPlus();
                    recUnit.resetState();
                    recUnit.setIsRecycling(false);
                    if(MusicPlayer.isPlayingEffect){
                        MusicPlayer.stopEffetti();
                    }
                }

            }

        }

            Glass.rinnovaTasso();
            Paper.rinnovaTasso();
            Aluminum.rinnovaTasso();
            Steel.rinnovaTasso();
            Plastic.rinnovaTasso();
            EWaste.rinnovaTasso();
            HazarWaste.rinnovaTasso();

            Paper.tassoMassimoRaggiunto();
            Aluminum.tassoMassimoRaggiunto();
            Steel.tassoMassimoRaggiunto();
            Plastic.tassoMassimoRaggiunto();
            EWaste.tassoMassimoRaggiunto();
            HazarWaste.tassoMassimoRaggiunto();

            Junk.increaseDistance();
            Junk.increaseSpeed();

    }

        //Disegna le unità di riciclo
        public void draw() {

            if (getHolder().getSurface().isValid()) {
                int infoUnit = -1;
                Canvas canvas = getHolder().lockCanvas();
                canvas.drawBitmap(background.background, background.getX(), background.getY(), paint);
                canvas.drawBitmap(background.spawnZone, background.getX(), this.screenY * 6 / 11, paint);


                for (int x = 0; x < recUnitList.size(); x++) {
                    RecUnit recUnit = recUnitList.get(x);

                    if (!recUnit.getIsUnlocked()) {
                        canvas.drawBitmap(recUnit.getRecUnit(), recUnit.getX(), recUnit.getY(), transparentPaint);

                        if (x != 0 && x < recUnitList.size() - 1) {
                            RecImages upgradableUnit = unlockableUnitList.get(x - 1);
                            canvas.drawBitmap(upgradableUnit.getImageBitmap(), upgradableUnit.getX(), upgradableUnit.getY(), paint);
                            canvas.drawText(String.valueOf(recUnit.getUnitPrice()), upgradableUnit.getX() + upgradableUnit.getWidth() * 5 / 9, upgradableUnit.getY() + upgradableUnit.getHeight() * 4 / 5, paint);
                        }

                    } else {

                        Rect rect = rectList.get(x);
                        canvas.drawRect(rect, paint);

                        if (x != recUnitList.size() - 1) {
                            RecImages unitPoints = unitPointsRecImageList.get(x);
                            canvas.drawBitmap(unitPoints.getImageBitmap(), unitPoints.getX(), unitPoints.getY(), paint);
                            canvas.drawText(String.valueOf(recUnit.getUnitPoints()), unitPoints.getX() + unitPoints.getWidth() + (int) (13.32 * screenRatioX), unitPoints.getY() + unitPoints.getHeight() * 7 / 8, paint);
                        }

                        if (!recUnit.getIsUpgraded()) {

                            if (recUnit.getIsRecycling()) {
                                Rect progressRect = new Rect(rect.left, rect.top, rect.left + (int) ((rect.right - rect.left) * ((double) (recUnit.getRecTotal()) / (double) (recUnit.getMaxRecTotal()))), rect.bottom);
                                canvas.drawRect(progressRect, rectPaint);

                                if (recUnit.getState() == 0 || recUnit.getState() == 1 || recUnit.getState() == 2) {
                                    canvas.drawBitmap(recUnit.getRecUnit(), recUnit.getX(), recUnit.getY(), paint);

                                } else if (recUnit.getState() == 3 || recUnit.getState() == 4 || recUnit.getState() == 5) {
                                    canvas.drawBitmap(recUnit.getRecUnitState2(), recUnit.getX(), recUnit.getY(), paint);

                                } else if (recUnit.getState() == 6 || recUnit.getState() == 7 || recUnit.getState() == 8) {
                                    canvas.drawBitmap(recUnit.getRecUnitState3(), recUnit.getX(), recUnit.getY(), paint);

                                } else if (recUnit.getState() == 9 || recUnit.getState() == 10 || recUnit.getState() == 11) {
                                    canvas.drawBitmap(recUnit.getRecUnitState4(), recUnit.getX(), recUnit.getY(), paint);
                                }

                            } else if (!recUnit.getIsRecycling()) {
                                canvas.drawBitmap(recUnit.getRecUnit(), recUnit.getX(), recUnit.getY(), paint);
                            }

                        } else if (recUnit.getIsUpgraded()) {
                            Rect rectLvl2 = new Rect(rectList.get(x).left, rectList.get(x).top + (int) (17.64 * screenRatioY), rectList.get(x).right, rectList.get(x).bottom + (int) (17.64 * screenRatioY));
                            canvas.drawRect(rectLvl2, paint);

                            if (recUnit.getIsRecycling()) {

                                if (recUnit.getState() == 0 || recUnit.getState() == 1 || recUnit.getState() == 2) {
                                    canvas.drawBitmap(recUnit.getRecUnitLvl2(), recUnit.getX(), recUnit.getY(), paint);

                                } else if (recUnit.getState() == 3 || recUnit.getState() == 4 || recUnit.getState() == 5) {
                                    canvas.drawBitmap(recUnit.getRecUnitLvl2State2(), recUnit.getX(), recUnit.getY(), paint);

                                } else if (recUnit.getState() == 6 || recUnit.getState() == 7 || recUnit.getState() == 8) {
                                    canvas.drawBitmap(recUnit.getRecUnitLvl2State3(), recUnit.getX(), recUnit.getY(), paint);
                                }

                            } else if (!recUnit.getIsRecycling()) {
                                canvas.drawBitmap(recUnit.getRecUnitLvl2(), recUnit.getX(), recUnit.getY(), paint);
                            }
                        }
                    }
                }

                //Imposta l'ombra trasparente del blocco quando viene trascinato e lo riporta nella sua posizione al rilascio
                for (int x = 0; x < junkList.size(); x++) {
                    Junk junk = junkList.get(x);

                    if (junk.getBeingDragged()) {
                        canvas.drawBitmap(junk.getJunk(), junk.getX(), junk.getY(), transparentPaint);
                        canvas.drawBitmap(junk.getJunk(), junk.getDragX(), junk.getDragY(), paint);
                    } else {
                        canvas.drawBitmap(junk.getJunk(), junk.getX(), junk.getY(), paint);
                    }
                }

                for (int x = 0; x < recUnitList.size() - 1; x++) {
                    RecUnit recUnit = recUnitList.get(x);
                    InfoImages infoImages = infoImagesList.get(x);

                    if (!recUnit.getIsUnlocked()) {

                    } else {

                        if (recUnit.getIsCheckingInfo()) {
                            infoUnit = x;
                            canvas.drawBitmap(unitInfo.getImageBitmap(), unitInfo.getX(), unitInfo.getY(), paint);

                            canvas.drawText("Tipo unità: " + recUnit.getUnitType(), unitInfo.getX() + (int)(490*screenRatioX), unitInfo.getY() + (int)(290*screenRatioY), textPaint);
                            canvas.drawText("Livello usura: " + recUnit.getRecycledUnitUpgraded() + "/23", unitInfo.getX() + (int)(490*screenRatioX), unitInfo.getY() + (int)(370*screenRatioY), textPaint);
                            canvas.drawText("Totale riciclati: " + recUnit.getRecycledUnit() , unitInfo.getX() + (int)(490*screenRatioX), unitInfo.getY() + (int)(410*screenRatioY), textPaint);


                            //if(!recUnit.getIsUpgraded()) {
                                canvas.drawText("Livello: 1", unitInfo.getX() + (int)(490*screenRatioX), unitInfo.getY() + (int)(330*screenRatioY), textPaint);
                                canvas.drawBitmap(upgrade.getImageBitmap(), upgrade.getX(), upgrade.getY(), paint);
                                canvas.drawBitmap(unitPoints.getImageBitmap(), unitPoints.getX(), unitPoints.getY(), paint);
                                canvas.drawText(String.valueOf(RecUnit.getUpgradePrice()), unitPoints.getX() - (int)(49.47*screenRatioX), unitPoints.getY() + unitPoints.getHeight()*7/8, paint);
                            //}

                            canvas.drawRect(unitInfo.getX() + (int)(209.31*screenRatioX), unitInfo.getY() + (int)(529*screenRatioY), unitInfo.getX() + (int)(407*screenRatioX), unitInfo.getY() + (int)(691*screenRatioY), strokePaint);

                            //if(recUnit.getIsUpgraded()) {
                            //canvas.drawText("Livello: 2", unitInfo.getX() + (int)(490*screenRatioX), unitInfo.getY() + (int)(330*screenRatioY), textPaint);
                            canvas.drawRect(unitInfo.getX() + (int)(445*screenRatioX), unitInfo.getY() + (int)(529*screenRatioY), unitInfo.getX() + (int)(643*screenRatioX), unitInfo.getY() + (int)(691*screenRatioY), strokePaint);
                            canvas.drawRect(unitInfo.getX() + (int)(681*screenRatioX), unitInfo.getY() + (int)(529*screenRatioY), unitInfo.getX() + (int)(879*screenRatioX), unitInfo.getY() + (int)(691*screenRatioY), strokePaint);


                            //}

                            canvas.drawBitmap(infoImages.getImageBitmap(), infoImages.getX(), infoImages.getY(), paint);
                            canvas.drawBitmap(infoImages.getMaterial_lvl1(), unitInfo.getX() + (int)(216*screenRatioX), unitInfo.getY() + (int)(536*screenRatioY), paint);
                            canvas.drawBitmap(infoImages.getMaterial_lvl2(), unitInfo.getX() + (int)(452*screenRatioX), unitInfo.getY() + (int)(536*screenRatioY), paint);
                            canvas.drawBitmap(infoImages.getMaterial_lvl3(), unitInfo.getX() + (int)(688*screenRatioX), unitInfo.getY() + (int)(536*screenRatioY), paint);

                            for (int i = 0; i < unitPointsInfoList.size(); i++) {
                                RecImages unitPoints = unitPointsInfoList.get(i);
                                RecImages sunnyPoints = sunnyPointsInfoList.get(i);

                                canvas.drawBitmap(unitPoints.getImageBitmap(), unitPoints.getX(), unitPoints.getY(), paint);
                                canvas.drawBitmap(sunnyPoints.getImageBitmap(), sunnyPoints.getX(), sunnyPoints.getY(), paint);
                                canvas.drawText(String.valueOf(infoImages.getUnitPoints(i)), sunnyPoints.getX() + sunnyPoints.getWidth() + (int)(7.61*screenRatioX), unitPoints.getY() + unitPoints.getHeight() * 7/8, paint);
                                canvas.drawText(String.valueOf(infoImages.getSunnyPoints(i)), sunnyPoints.getX() + sunnyPoints.getWidth() + (int)(7.61*screenRatioX), sunnyPoints.getY() + sunnyPoints.getHeight() * 7/8, paint);
                            }

                        }
                    }
                }
                canvas.drawBitmap(background.greenRect, background.getX(), background.getY(), paint);
                canvas.drawBitmap(sunnyPoints.getImageBitmap(), sunnyPoints.getX(), sunnyPoints.getY(), paint);
                canvas.drawText(String.valueOf(sunnyPoints.getSunnyPoints()), sunnyPoints.getX() + sunnyPoints.getWidth()* 3/2, sunnyPoints.getY() + sunnyPoints.getHeight() , paint);
                canvas.drawBitmap(missioni.getImageBitmap(), missioni.getX() * 34/2, missioni.getY() - (float)(10 * screenRatioY) , paint);
                if(pause.isClicked()){

                    canvas.drawBitmap(pause.getImageBitmap2(), pause.getX() * 32 , pause.getY(), paint);
                    canvas.drawBitmap(gameBar.getElencoMissioni(), gameBar.getHeight() * 1/15, gameBar.getWidth() * 1/14, paint);
                    canvas.drawText("PAUSA",screenX/9 , screenY/5, paint);
                    canvas.drawBitmap(gameBar.getAudioIcon(), gameBar.getHeight() * 8, gameBar.getWidth() * 10, paint);
                    canvas.drawBitmap(gameBar.getMusicIcon(), gameBar.getHeight() * 16, gameBar.getWidth() * 10, paint);
                    //for(int x = 0; x < gameBar.getNumeroMissioni(); x++){
                    //canvas.drawText(gameBar.getMissioni(x),screenX/9 , screenY/5, paint);
                    //}
                }
                else {
                    canvas.drawBitmap(pause.getImageBitmap(), pause.getX() * 32 , pause.getY(), paint);
                }

                getHolder().unlockCanvasAndPost(canvas);

                if(infoUnit != -1) {
                    pause();
                }
            }
        }

        //Imposta periodo di pausa per il thread
        public void sleep() {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        //Riprende il gioco
        public void resume() {

            isPlaying = true;
            thread = new Thread(this);
            thread.start();
        }

        //Pausa il gioco
        public void pause() {

            try {
                isPlaying = false;
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        //Cattura i movimenti e le posizioni dei blocchi
        @Override
        public boolean onTouchEvent(MotionEvent event) {

            if (event.getAction() == MotionEvent.ACTION_DOWN){
                int touchX = (int)event.getX();
                int touchY = (int)event.getY();

                nJunk = -1;

                if(touchX >= pause.getX() * 32 && touchY >= pause.getY() && touchX < pause.getX() * 32 + pause.getWidth() && touchY < pause.getY() + pause.getHeight() && !(pause.isClicked())){

                    pause.setClicked(true);
                    sunnyPoints.setSunnyPoints(sunnyPoints.getSunnyPoints()+1);


                }
                else if(touchX >= pause.getX() * 32 && touchY >= pause.getY() && touchX < pause.getX() * 32 + pause.getWidth() && touchY < pause.getY() + pause.getHeight() && pause.isClicked()){
                    pause.setClicked(false);
                }

                if(touchX >= missioni.getX() * 34/2 && touchY >= missioni.getY()-10 && touchX < missioni.getX() * 34/2 + missioni.getWidth() && touchY < missioni.getY()-10 + missioni.getHeight() && !(missioni.isClicked())){
                    missioni.setClicked(true);
                }

                else if(touchX >= missioni.getX() * 34/2 && touchY >= missioni.getY()-10 && touchX < missioni.getX() * 34/2 + missioni.getWidth() && touchY < missioni.getY()-10 + missioni.getHeight() && missioni.isClicked()){
                    missioni.setClicked(false);
                }

                for (int i = 0; i < junkList.size(); i++) {
                    Junk junk = junkList.get(i);
                    boolean isTouching = (touchY >= junk.getY() && touchX < junk.getX() + junk.getWidth() && touchY < junk.getY() + junk.getHeight());
                    if (touchX >= junk.getX() && isTouching) {
                        nJunk = i;
                    }
                }

                for (int i = 0; i < recUnitList.size() - 1; i++) {
                    RecUnit recUnit = recUnitList.get(i);
                    boolean isTouching = (touchY >= recUnit.getY() && touchX < recUnit.getX() + recUnit.getWidth() && touchY < recUnit.getY() + recUnit.getHeight());

                    if (recUnit.getIsCheckingInfo() && touchY < unitInfo.getY() && !isTouching) {
                        recUnit.setIsCheckingInfo(false);
                        resume();
                    }

                    if (touchX >= recUnit.getX() && isTouching) {
                        if (isPlaying) {
                            recUnit.setIsCheckingInfo(true);
                        }
                    }
                }
            }

            if (event.getAction() == MotionEvent.ACTION_MOVE) {
                int x = (int)event.getX();
                int y = (int)event.getY();

                if ((nJunk != -1)) {
                    Junk junk = junkList.get(nJunk);
                    junk.setBeingDraggedTrue();
                    junk.setDragX(x);
                    junk.setDragY(y);
                }
            }

            if (event.getAction() == MotionEvent.ACTION_UP) {
                int x = (int)event.getX();
                int y = (int)event.getY();

                if(x >= pause.getX() * 32 && y >= pause.getY()/8 && x < pause.getX() * 32 + pause.getWidth() && y < pause.getY()/8 + pause.getHeight() && pause.isClicked()){
                    pause();
                }
                else if(x >= pause.getX() * 32 && y >= pause.getY()/8 && x < pause.getX() * 32 + pause.getWidth() && y < pause.getY()/8 + pause.getHeight() && !(pause.isClicked())){
                    resume();
                }

                if(x >= missioni.getX() * 34/2 && y >= missioni.getY()-20 && x < missioni.getX() * 34/2 + missioni.getWidth() && y < missioni.getY()-20 + missioni.getHeight() && missioni.isClicked()){
                    pause();
                }

                else if(x >= missioni.getX() * 34/2 && y >= missioni.getY()-20 && x < missioni.getX() * 34/2 + missioni.getWidth() && y < missioni.getY()-20 + missioni.getHeight() && !(missioni.isClicked())){
                    resume();
                }

                if ((nJunk != -1)) {
                    Junk junk = junkList.get(nJunk);
                    junk.setBeingDraggedFalse();

                    if (junk instanceof Glass && x >= recUnitList.get(0).getX() && y >= recUnitList.get(0).getY() &&
                            x < recUnitList.get(0).getX() + recUnitList.get(0).getWidth() && y <= recUnitList.get(0).getY()
                            + recUnitList.get(0).getHeight() && !recUnitList.get(0).getIsRecycling()) {
                        junkList.remove(nJunk);
                        recUnitList.get(0).setIsRecycling(true);

                    } else if (junk instanceof Paper && recUnitList.get(1).getIsUnlocked() && x >= recUnitList.get(1).getX()
                            && y >= recUnitList.get(1).getY() && x < recUnitList.get(1).getX() + recUnitList.get(1).getWidth()
                            && y <= recUnitList.get(1).getY() + recUnitList.get(1).getHeight() && !recUnitList.get(1).getIsRecycling()) {
                        junkList.remove(nJunk);
                        recUnitList.get(1).setIsRecycling(true);

                    } else if (junk instanceof Aluminum && recUnitList.get(2).getIsUnlocked() && x >= recUnitList.get(2).getX()
                            && y >= recUnitList.get(2).getY() && x < recUnitList.get(2).getX() + recUnitList.get(2).getWidth()
                            && y <= recUnitList.get(2).getY() + recUnitList.get(2).getHeight() && !recUnitList.get(2).getIsRecycling()) {
                        junkList.remove(nJunk);
                        recUnitList.get(2).setIsRecycling(true);

                    } else if (junk instanceof Steel && recUnitList.get(3).getIsUnlocked() && x >= recUnitList.get(3).getX()
                            && y >= recUnitList.get(3).getY() && x < recUnitList.get(3).getX() + recUnitList.get(3).getWidth()
                            && y <= recUnitList.get(3).getY() + recUnitList.get(3).getHeight() && !recUnitList.get(3).getIsRecycling()) {
                        junkList.remove(nJunk);
                        recUnitList.get(3).setIsRecycling(true);

                    } else if (junk instanceof Plastic && recUnitList.get(4).getIsUnlocked() && x >= recUnitList.get(4).getX()
                            && y >= recUnitList.get(4).getY() && x < recUnitList.get(4).getX() + recUnitList.get(4).getWidth()
                            && y <= recUnitList.get(4).getY() + recUnitList.get(4).getHeight() && !recUnitList.get(4).getIsRecycling()) {
                        junkList.remove(nJunk);
                        recUnitList.get(4).setIsRecycling(true);

                    } else if (junk instanceof EWaste && recUnitList.get(5).getIsUnlocked() && x >= recUnitList.get(5).getX()
                            && y >= recUnitList.get(5).getY() && x < recUnitList.get(5).getX() + recUnitList.get(5).getWidth()
                            && y <= recUnitList.get(5).getY() + recUnitList.get(5).getHeight() && !recUnitList.get(5).getIsRecycling()) {
                        junkList.remove(nJunk);
                        recUnitList.get(5).setIsRecycling(true);

                    } else if (x >= recUnitList.get(6).getX() && y >= recUnitList.get(6).getY()
                            && x < recUnitList.get(6).getX() + recUnitList.get(6).getWidth() && y < recUnitList.get(6).getY() +
                            recUnitList.get(6).getHeight() && !recUnitList.get(6).getIsRecycling()){
                        junkList.remove(nJunk);
                        recUnitList.get(6).setIsRecycling(true);
                    }

                }
            }
            return true;
        }
}
