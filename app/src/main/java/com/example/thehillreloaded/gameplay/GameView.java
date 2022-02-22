package com.example.thehillreloaded.gameplay;

import android.content.Context;
import android.graphics.Bitmap;
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
import com.example.thehillreloaded.gameplay.imageclass.MaterialInfo;
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
    private final double designX = 1080f, designY = 2072f, designDensity = 2.75;
    private int screenX, screenY, spawnBoundX, spawnY;
    public static double screenRatioX, screenRatioY, densityRatio;
    private Background background;
    private Paint paint, transparentPaint, redPaint, strokePaint, textPaint, missionPaint;
    private ArrayList<Junk> junkList = new ArrayList<>();
    private ArrayList<Rect> rectList = new ArrayList<>();
    private ArrayList<RecUnit> recUnitList = new ArrayList<>();
    private ArrayList<RecImages> unitPointsRecImageList = new ArrayList<>();
    private ArrayList<RecImages> unlockableUnitList = new ArrayList<>();
    private ArrayList<RecImages> unitPointsInfoList = new ArrayList<>();
    private ArrayList<RecImages> sunnyPointsInfoList = new ArrayList<>();
    private ArrayList<InfoImages> infoImagesList = new ArrayList<>();
    private ArrayList<Missioni> listaMissioni = new ArrayList<>();
    private UnitInfo unitInfo;
    private Upgrade upgrade;
    private UnitPoints unitPoints;
    private SunnyPoints sunnyPoints;
    private MaterialInfo materialInfo;
    private RecImages missioni, pause;
    private GameBar gameBar;
    private int GoalJunkk, GoalRecUpgr, GoalSunnyAcc, GoalUnitPointsUsed;
    private Missioni goals;

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
        sunnyPoints = new SunnyPoints((int)(30*screenRatioX), (int)(10*screenRatioY), getResources());
        missioni = new Missioni((int)(30*screenRatioX), (int)(10*screenRatioY), getResources());
        pause = new Pause((int)(30*screenRatioX), (int)(10*screenRatioY), getResources());
        goals = new Missioni(missioni.getX(), missioni.getY(), getResources());

        Random random = new Random();

        recUnitList.add(new GlassUnit((int) (10 * screenRatioX), (int) (88 * screenRatioY), getResources()));
        recUnitList.add(new PaperUnit((int) (7 * screenRatioX), (int) (490 * screenRatioY), getResources()));
        recUnitList.add(new AluminumUnit((int) (10 * screenRatioX), (int) (789 * screenRatioY), getResources()));
        recUnitList.add(new SteelUnit((int) (590 * screenRatioX), (int) (81 * screenRatioY), getResources()));
        recUnitList.add(new PlasticUnit((int) (700 * screenRatioX), (int) (467 * screenRatioY), getResources()));
        recUnitList.add(new EWasteUnit((int) (563 * screenRatioX), (int) (820 * screenRatioY), getResources()));
        recUnitList.add(new Incinerator((int) (381 * screenRatioX), (int) (388 * screenRatioY), getResources()));

        rectList.add(new Rect((int)(110*screenRatioX), (int)(431*screenRatioY), (int)(304*screenRatioX), (int)(448*screenRatioY)));
        rectList.add(new Rect((int)(110*screenRatioX), (int)(764*screenRatioY), (int)(304*screenRatioX), (int)(781*screenRatioY)));
        rectList.add(new Rect((int)(110*screenRatioX), (int)(1085*screenRatioY), (int)(304*screenRatioX), (int)(1102*screenRatioY)));
        rectList.add(new Rect((int)(799*screenRatioX), (int)(436*screenRatioY), (int)(1008*screenRatioX), (int)(453*screenRatioY)));
        rectList.add(new Rect((int)(799*screenRatioX), (int)(781*screenRatioY), (int)(1008*screenRatioX), (int)(798*screenRatioY)));
        rectList.add(new Rect((int)(799*screenRatioX), (int)(1077*screenRatioY), (int)(1008*screenRatioX), (int)(1094*screenRatioY)));
        rectList.add(new Rect((int)(451*screenRatioX), (int)(787*screenRatioY), (int)(660*screenRatioX), (int)(804*screenRatioY)));

        unitPointsRecImageList.add(new UnitPoints((int)(11.42*screenRatioX), (int)(125*screenRatioY), getResources()));
        unitPointsRecImageList.add(new UnitPoints((int)(11.42*screenRatioX), (int)(514*screenRatioY), getResources()));
        unitPointsRecImageList.add(new UnitPoints((int)(11.42*screenRatioX), (int)(830*screenRatioY), getResources()));
        unitPointsRecImageList.add(new UnitPoints((int)(688*screenRatioX), (int)(138*screenRatioY), getResources()));
        unitPointsRecImageList.add(new UnitPoints((int)(790*screenRatioX), (int)(510*screenRatioY), getResources()));
        unitPointsRecImageList.add(new UnitPoints((int)(613*screenRatioX), (int)(845*screenRatioY), getResources()));

        unlockableUnitList.add(new UnlockableUnit((int)(98*screenRatioX),(int)(670*screenRatioY), getResources()));
        unlockableUnitList.add(new UnlockableUnit((int)(78*screenRatioX),(int)(988*screenRatioY), getResources()));
        unlockableUnitList.add(new UnlockableUnit((int)(760*screenRatioX),(int)(317.52*screenRatioY), getResources()));
        unlockableUnitList.add(new UnlockableUnit((int)(800*screenRatioX),(int)(670*screenRatioY), getResources()));
        unlockableUnitList.add(new UnlockableUnit((int)(720*screenRatioX),(int)(988*screenRatioY), getResources()));

        unitInfo = new UnitInfo(0, (int) (1235 * screenRatioY), getResources());
        upgrade = new Upgrade(unitInfo.getX() + (int)(316*screenRatioX), unitInfo.getY() + (int)(388*screenRatioY), getResources());
        unitPoints = new UnitPoints(unitInfo.getX() + (int)(228.34*screenRatioX), unitInfo.getY() + (int)(423.36*screenRatioY), getResources());
        materialInfo = new MaterialInfo(0, (int)(230 * screenRatioY), getResources());


        unitPointsInfoList.add(new UnitPoints(unitInfo.getX() + (int)(239.75*screenRatioX), unitInfo.getY() + (int)(699*screenRatioY), getResources()));
        unitPointsInfoList.add(new UnitPoints(unitInfo.getX() + (int)(475.7*screenRatioX), unitInfo.getY() + (int)(699*screenRatioY), getResources()));
        unitPointsInfoList.add(new UnitPoints(unitInfo.getX() + (int)(711.65*screenRatioX), unitInfo.getY() + (int)(699*screenRatioY), getResources()));

        sunnyPointsInfoList.add(new SunnyPoints(unitInfo.getX() + (int)(239.75*screenRatioX), unitInfo.getY() + (int)(760*screenRatioY), getResources()));
        sunnyPointsInfoList.add(new SunnyPoints(unitInfo.getX() + (int)(475.7*screenRatioX), unitInfo.getY() + (int)(760*screenRatioY), getResources()));
        sunnyPointsInfoList.add(new SunnyPoints(unitInfo.getX() + (int)(711.65*screenRatioX), unitInfo.getY() + (int)(760*screenRatioY), getResources()));

        infoImagesList.add(new GlassInfo(unitInfo.getX() + (int)(222*screenRatioX), unitInfo.getY() + (int)(161*screenRatioY), getResources()));
        infoImagesList.add(new PaperInfo(unitInfo.getX() + (int)(203*screenRatioX), unitInfo.getY() + (int)(190*screenRatioY), getResources()));
        infoImagesList.add(new AluminumInfo(unitInfo.getX() + (int)(215*screenRatioX), unitInfo.getY() + (int)(182*screenRatioY), getResources()));
        infoImagesList.add(new SteelInfo(unitInfo.getX() + (int)(201*screenRatioX), unitInfo.getY() + (int)(180*screenRatioY), getResources()));
        infoImagesList.add(new PlasticInfo(unitInfo.getX() + (int)(208*screenRatioX), unitInfo.getY() + (int)(175*screenRatioY), getResources()));
        infoImagesList.add(new EWasteInfo(unitInfo.getX() + (int)(185*screenRatioX), unitInfo.getY() + (int)(201*screenRatioY), getResources()));


        GoalJunkk = goals.getGoalJunkRec();
        GoalRecUpgr = goals.getGoalRecUpgr();
        GoalSunnyAcc = goals.getGoalSunnyAccum();
        GoalUnitPointsUsed = goals.getGoalUnitPointsUsed();
        listaMissioni.add(new Missioni(missioni.getX(), missioni.getY(), 0, "Ricicla " +GoalJunkk + " rifiuti totali.", getResources()));
        listaMissioni.add(new Missioni(missioni.getX(), missioni.getY(), 1, "Effettua " +GoalRecUpgr + " upgrade per le\nstazioni.", getResources()));
        listaMissioni.add(new Missioni(missioni.getX(), missioni.getY(), 2, "Guadagna " +GoalSunnyAcc + " Sunny\npoints.", getResources()));
        listaMissioni.add(new Missioni(missioni.getX(), missioni.getY(), 3, "Usa " +GoalUnitPointsUsed + " Unit points.", getResources()));


        junkList.add(new Glass((random.nextInt(spawnBoundX) + (int) (15.22 * screenRatioX)), spawnY, getResources()));

        /*recUnitList.get(1).setIsUnlockedToTrue();
        recUnitList.get(2).setIsUnlockedToTrue();
        recUnitList.get(3).setIsUnlockedToTrue();
        recUnitList.get(4).setIsUnlockedToTrue();
        recUnitList.get(5).setIsUnlockedToTrue();
        recUnitList.get(0).setIsUpgraded(true);
        recUnitList.get(1).setIsUpgraded(true);
        recUnitList.get(2).setIsUpgraded(true);
        recUnitList.get(3).setIsUpgraded(true);
        recUnitList.get(4).setIsUpgraded(true);
        recUnitList.get(5).setIsUpgraded(true);
        recUnitList.get(0).unitPointsPlus();
        recUnitList.get(0).unitPointsPlus();
        recUnitList.get(0).unitPointsPlus();
        recUnitList.get(0).unitPointsPlus();
        recUnitList.get(0).unitPointsPlus();
        recUnitList.get(0).unitPointsPlus();
        recUnitList.get(0).unitPointsPlus();
        recUnitList.get(1).unitPointsPlus();
        recUnitList.get(1).unitPointsPlus();
        recUnitList.get(1).unitPointsPlus();
        recUnitList.get(1).unitPointsPlus();
        recUnitList.get(1).unitPointsPlus();
        recUnitList.get(1).unitPointsPlus();
        recUnitList.get(1).unitPointsPlus();
        recUnitList.get(2).unitPointsPlus();
        recUnitList.get(2).unitPointsPlus();
        recUnitList.get(2).unitPointsPlus();
        recUnitList.get(2).unitPointsPlus();
        recUnitList.get(2).unitPointsPlus();
        recUnitList.get(2).unitPointsPlus();
        recUnitList.get(2).unitPointsPlus();
        recUnitList.get(3).unitPointsPlus();
        recUnitList.get(3).unitPointsPlus();
        recUnitList.get(3).unitPointsPlus();
        recUnitList.get(3).unitPointsPlus();
        recUnitList.get(3).unitPointsPlus();
        recUnitList.get(3).unitPointsPlus();
        recUnitList.get(3).unitPointsPlus();
        recUnitList.get(4).unitPointsPlus();
        recUnitList.get(4).unitPointsPlus();
        recUnitList.get(4).unitPointsPlus();
        recUnitList.get(4).unitPointsPlus();
        recUnitList.get(4).unitPointsPlus();
        recUnitList.get(4).unitPointsPlus();
        recUnitList.get(4).unitPointsPlus();
        recUnitList.get(5).unitPointsPlus();
        recUnitList.get(5).unitPointsPlus();
        recUnitList.get(5).unitPointsPlus();
        recUnitList.get(5).unitPointsPlus();
        recUnitList.get(5).unitPointsPlus();
        recUnitList.get(5).unitPointsPlus();
        recUnitList.get(5).unitPointsPlus();*/

        paint = new Paint();
        paint.setTextSize(64 * (float)(screenRatioX * screenRatioY * densityRatio));
        paint.setTypeface(ResourcesCompat.getFont(context, R.font.bevan));
        transparentPaint = new Paint();
        transparentPaint.setAlpha(100);
        redPaint = new Paint();
        redPaint.setTextSize(64 * (float)(screenRatioX * screenRatioY * densityRatio));
        redPaint.setTypeface(ResourcesCompat.getFont(context, R.font.bevan));
        redPaint.setColor(Color.RED);
        strokePaint = new Paint();
        strokePaint.setStrokeWidth(10 * (float)(screenRatioX * screenRatioY * densityRatio));
        strokePaint.setStyle(Paint.Style.STROKE);
        textPaint = new Paint();
        textPaint.setTextSize(32 * (float)(screenRatioX * screenRatioY * densityRatio));
        textPaint.setTypeface(ResourcesCompat.getFont(context, R.font.bevan));
        missionPaint = new Paint();
        missionPaint.setTextSize(36 * (float)(screenRatioX * screenRatioY * densityRatio));
        missionPaint.setTypeface(ResourcesCompat.getFont(context, R.font.bevan));
    }


    @Override
    public void run() {

        while (isPlaying) {
            draw();
            update();
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

            for (int y = x; y >= 0; y--) {
                Junk otherJunk = junkList.get(y);

                if (Rect.intersects(junk.getCollisionShape(), otherJunk.getCollisionShape()) && x != y) {
                    junk.setIntersectionTrue();
                    junk.setY(otherJunk.getY() - junk.getHeight() + 1);
                } else {
                    junk.setIntersectionFalse();
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

                if(recUnit.getJunkBeingRecycled() == 1) {

                    if(recUnit.getRecTotal() >= recUnit.getRecTotalUpgraded()) {
                        recUnit.increaseRecTotal();

                    } else if (recUnit.getRecTotalUpgraded() > recUnit.getRecTotal()) {
                        recUnit.increaseRecTotalUpgraded();

                    }

                } else if (recUnit.getJunkBeingRecycled() == 2) {
                    recUnit.increaseRecTotal();
                    recUnit.increaseRecTotalUpgraded();
                }

                if (recUnit.recTotalIsEnough()) {
                    recUnit.unitPointsPlus();
                    recUnit.recycledUnitPlus();
                    recUnit.junkBeingRecycledMinus();

                    if(recUnit.getIsUpgraded()) {
                        recUnit.recycledUnitUpgradedPlus();
                    }

                    if (recUnit.getRecTotalUpgraded() == 0) {
                        recUnit.resetState();
                        recUnit.setIsRecycling(false);
                        if(MusicPlayer.isPlayingEffect){
                            MusicPlayer.stopEffetti();
                        }
                    }

                } else if (recUnit.recTotalUpgradedIsEnough()) {
                    recUnit.unitPointsPlus();
                    recUnit.recycledUnitPlus();
                    recUnit.junkBeingRecycledMinus();
                    recUnit.recycledUnitUpgradedPlus();

                    if (recUnit.getRecTotal() == 0) {
                        recUnit.resetState();
                        recUnit.setIsRecycling(false);
                        if(MusicPlayer.isPlayingEffect){
                            MusicPlayer.stopEffetti();
                        }
                    }
                }
            }

            if(recUnit.getIsUpgraded() && recUnit.getRecycledUnitUpgraded() == recUnit.getMaxRecycledUnitUpgraded()) {
                recUnit.setIsUpgraded(false);
                recUnit.recycledUnitUpgradedReset();
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
                                canvas.drawRect(progressRect, redPaint);

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
                            Rect rectLvl2 = new Rect(rectList.get(x).left, rectList.get(x).top + (int) (30 * screenRatioY), rectList.get(x).right, rectList.get(x).bottom + (int) (30 * screenRatioY));
                            canvas.drawRect(rectLvl2, paint);

                            if (recUnit.getIsRecycling()) {
                                Rect progressRect = new Rect(rect.left, rect.top, rect.left + (int) ((rect.right - rect.left) * ((double) (recUnit.getRecTotal()) / (double) (recUnit.getMaxRecTotal()))), rect.bottom);
                                Rect progressRectLvl2 = new Rect(rectLvl2.left, rectLvl2.top, rectLvl2.left + (int) ((rectLvl2.right - rectLvl2.left) * ((double) (recUnit.getRecTotalUpgraded()) / (double) (recUnit.getMaxRecTotal()))), rectLvl2.bottom);

                                if (recUnit.getJunkBeingRecycled() == 1) {

                                    if (recUnit.getRecTotal() >= recUnit.getRecTotalUpgraded()) {
                                        canvas.drawRect(progressRect, redPaint);

                                    } else if (recUnit.getRecTotalUpgraded() > recUnit.getRecTotal()) {
                                        canvas.drawRect(progressRectLvl2, redPaint);
                                    }

                                } else if (recUnit.getJunkBeingRecycled() == 2) {
                                    canvas.drawRect(progressRect, redPaint);
                                    canvas.drawRect(progressRectLvl2, redPaint);
                                }

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
                        //Imposta il menu unità quando viene toccata l'unità di riciclo
                        if (recUnit.getIsCheckingInfo()) {
                            if(MusicPlayer.isPlayingEffect){
                                MusicPlayer.stopEffetti();
                            }
                            infoUnit = x;
                            canvas.drawBitmap(unitInfo.getImageBitmap(), unitInfo.getX(), unitInfo.getY(), paint);

                            canvas.drawText("Tipo unità: " + recUnit.getUnitType(), unitInfo.getX() + (int)(490*screenRatioX), unitInfo.getY() + (int)(290*screenRatioY), textPaint);
                            canvas.drawText("Livello usura: " + recUnit.getRecycledUnitUpgraded() + "/" + recUnit.getMaxRecycledUnitUpgraded(), unitInfo.getX() + (int)(490*screenRatioX), unitInfo.getY() + (int)(370*screenRatioY), textPaint);
                            canvas.drawText("Totale riciclati: " + recUnit.getRecycledUnit() , unitInfo.getX() + (int)(490*screenRatioX), unitInfo.getY() + (int)(410*screenRatioY), textPaint);


                            if(!recUnit.getIsUpgraded()) {
                                canvas.drawBitmap(infoImages.getImageBitmap(), infoImages.getX(), infoImages.getY(), paint);
                                canvas.drawText("Livello: 1", unitInfo.getX() + (int)(490*screenRatioX), unitInfo.getY() + (int)(330*screenRatioY), textPaint);
                                canvas.drawBitmap(upgrade.getImageBitmap(), upgrade.getX(), upgrade.getY(), paint);
                                canvas.drawBitmap(unitPoints.getImageBitmap(), unitPoints.getX(), unitPoints.getY(), paint);
                                canvas.drawText(String.valueOf(RecUnit.getUpgradePrice()), unitPoints.getX() - (int)(49.47*screenRatioX), unitPoints.getY() + unitPoints.getHeight()*7/8, paint);

                            } else if (recUnit.getIsUpgraded()) {
                                canvas.drawBitmap(infoImages.getUpgradedImageBitmap(), infoImages.getX(), infoImages.getY(), paint);
                                canvas.drawText("Livello: 2", unitInfo.getX() + (int)(490*screenRatioX), unitInfo.getY() + (int)(330*screenRatioY), textPaint);
                                canvas.drawRect(unitInfo.getX() + (int)(445*screenRatioX), unitInfo.getY() + (int)(529*screenRatioY), unitInfo.getX() + (int)(643*screenRatioX), unitInfo.getY() + (int)(691*screenRatioY), strokePaint);
                                canvas.drawRect(unitInfo.getX() + (int)(681*screenRatioX), unitInfo.getY() + (int)(529*screenRatioY), unitInfo.getX() + (int)(879*screenRatioX), unitInfo.getY() + (int)(691*screenRatioY), strokePaint);
                                canvas.drawBitmap(infoImages.getMaterial_lvl2(), unitInfo.getX() + (int)(454*screenRatioX), unitInfo.getY() + (int)(536*screenRatioY), paint);
                                canvas.drawBitmap(infoImages.getMaterial_lvl3(), unitInfo.getX() + (int)(690*screenRatioX), unitInfo.getY() + (int)(536*screenRatioY), paint);
                            }

                            canvas.drawRect(unitInfo.getX() + (int)(209.31*screenRatioX), unitInfo.getY() + (int)(529*screenRatioY), unitInfo.getX() + (int)(407*screenRatioX), unitInfo.getY() + (int)(691*screenRatioY), strokePaint);

                            canvas.drawBitmap(infoImages.getMaterial_lvl1(), unitInfo.getX() + (int)(218*screenRatioX), unitInfo.getY() + (int)(536*screenRatioY), paint);

                            for (int i = 0; i < unitPointsInfoList.size(); i++) {
                                RecImages unitPoints = unitPointsInfoList.get(i);
                                RecImages sunnyPoints = sunnyPointsInfoList.get(i);

                                if (i < 1 || i > 0 && recUnit.getIsUpgraded()) {
                                    canvas.drawBitmap(unitPoints.getImageBitmap(), unitPoints.getX(), unitPoints.getY(), paint);
                                    canvas.drawBitmap(sunnyPoints.getImageBitmap(), sunnyPoints.getX(), sunnyPoints.getY(), paint);

                                    if (recUnit.getUnitPoints() < infoImages.getUnitPoints(i)) {
                                        canvas.drawText(String.valueOf(infoImages.getUnitPoints(i)), sunnyPoints.getX() + sunnyPoints.getWidth() + (int)(7.61*screenRatioX), unitPoints.getY() + unitPoints.getHeight() * 7/8, redPaint);
                                        canvas.drawText(String.valueOf(infoImages.getSunnyPoints(i)), sunnyPoints.getX() + sunnyPoints.getWidth() + (int)(7.61*screenRatioX), sunnyPoints.getY() + sunnyPoints.getHeight() * 7/8, redPaint);

                                    } else {
                                        canvas.drawText(String.valueOf(infoImages.getUnitPoints(i)), sunnyPoints.getX() + sunnyPoints.getWidth() + (int)(7.61*screenRatioX), unitPoints.getY() + unitPoints.getHeight() * 7/8, paint);
                                        canvas.drawText(String.valueOf(infoImages.getSunnyPoints(i)), sunnyPoints.getX() + sunnyPoints.getWidth() + (int)(7.61*screenRatioX), sunnyPoints.getY() + sunnyPoints.getHeight() * 7/8, paint);
                                    }
                                }
                            }

                        }
                        //Imposta il pop-up delle info sui materiali prodotti quando viene prodotto un materiale
                        else if (infoImages.getIsCheckingMaterialLvl1Info()) {
                            canvas.drawBitmap(materialInfo.getImageBitmap(), materialInfo.getX(), materialInfo.getY(), paint);
                            infoImages.drawMaterialLvl1Text(materialInfo.getX() + (int)(220*screenRatioX), materialInfo.getY() + (int)(420*screenRatioY), missionPaint, canvas);
                            infoUnit = x;

                        } else if (infoImages.getIsCheckingMaterialLvl2Info()) {
                            canvas.drawBitmap(materialInfo.getImageBitmap(), materialInfo.getX(), materialInfo.getY(), paint);
                            infoImages.drawMaterialLvl2Text(materialInfo.getX() + (int)(220*screenRatioX), materialInfo.getY() + (int)(420*screenRatioY), missionPaint, canvas);
                            infoUnit = x;

                        } else if (infoImages.getIsCheckingMaterialLvl3Info()) {
                            canvas.drawBitmap(materialInfo.getImageBitmap(), materialInfo.getX(), materialInfo.getY(), paint);
                            infoImages.drawMaterialLvl3Text(materialInfo.getX() + (int)(220*screenRatioX), materialInfo.getY() + (int)(420*screenRatioY), missionPaint, canvas);
                            infoUnit = x;
                        }
                    }
                }

                canvas.drawBitmap(background.greenRect, background.getX(), background.getY(), paint);
                canvas.drawBitmap(Bitmap.createScaledBitmap(sunnyPoints.getImageBitmap(), (int)(sunnyPoints.getWidth()*1.5), (int)(sunnyPoints.getHeight()*1.5), true), sunnyPoints.getX(), sunnyPoints.getY(), paint);
                canvas.drawText(String.valueOf(sunnyPoints.getSunnyPoints()), sunnyPoints.getX() + sunnyPoints.getWidth() * 2, sunnyPoints.getY() + sunnyPoints.getHeight() * 6/5, paint);
                canvas.drawBitmap(missioni.getImageBitmap(), missioni.getX() * 34/2, missioni.getY() - (float)(10 * screenRatioY) , paint);

                //Imposta il pannello delle missioni
                if(missioni.isClicked()){
                    canvas.drawBitmap(gameBar.getPausaRect(), 0, gameBar.getHeight() * 3, paint);
                    canvas.drawText("MISSIONI",missioni.getWidth()*8/3, missioni.getHeight()*7/2, paint);
                    for(int m = 0; m < listaMissioni.size(); m++) {
                        Missioni mission = listaMissioni.get(m);

                        if (mission.getMissionType() == 0){
                            canvas.drawText(mission.getDescrizione(), missioni.getWidth() * 2, missioni.getHeight() * 9/2, missionPaint);
                            if(mission.getTotJunkRec() < mission.getGoalJunkRec()) {
                                canvas.drawText("Obiettivo: " + mission.getTotJunkRec() + "/" + mission.getGoalJunkRec(), missioni.getHeight() * 2, missioni.getWidth() * 10/2, missionPaint);
                            }
                            else {
                                canvas.drawText("Completata!", missioni.getWidth() * 2, missioni.getHeight() * 10/2, missionPaint);
                            }
                        }
                        if(mission.getMissionType() == 1){
                            canvas.drawText(mission.getDescrizione(), missioni.getWidth() * 2, missioni.getHeight() * 12/2, missionPaint);
                            if(mission.getTotRecUpgr() < mission.getGoalRecUpgr()) {
                                canvas.drawText("Obiettivo: " +mission.getTotRecUpgr()+"/"+mission.getGoalRecUpgr(), missioni.getHeight() * 2, missioni.getWidth() * 13/2, missionPaint);
                            }
                            else {
                                canvas.drawText("Completata!", missioni.getWidth() * 2, missioni.getHeight() * 13/2, missionPaint);
                            }
                        }
                        if(mission.getMissionType() == 2){
                            canvas.drawText(mission.getDescrizione(), missioni.getWidth() * 2, missioni.getHeight() * 15/2, missionPaint);

                            if(mission.getTotSunnyAccum() < mission.getGoalSunnyAccum()) {
                                canvas.drawText("Obiettivo: " +mission.getTotSunnyAccum()+"/"+mission.getGoalSunnyAccum(), missioni.getHeight() * 2, missioni.getWidth() * 16/2, missionPaint);
                            }
                            else {
                                canvas.drawText("Completata!", missioni.getWidth() * 2, missioni.getHeight() * 16/2, missionPaint);
                            }
                        }
                        if(mission.getMissionType() == 3){
                            canvas.drawText(mission.getDescrizione(), missioni.getWidth() * 2, missioni.getHeight() * 18/2, missionPaint);

                            if(mission.getTotUnitPointsUsed() < mission.getGoalUnitPointsUsed()) {
                                canvas.drawText("Obiettivo: " +mission.getTotUnitPointsUsed()+"/"+mission.getGoalUnitPointsUsed(), missioni.getWidth() * 2, missioni.getHeight() * 19/2, missionPaint);
                            }
                            else {
                                canvas.drawText("Completata!", missioni.getWidth() * 2, missioni.getHeight() * 19/2, missionPaint);
                            }
                        }
                    }
                }

                //Imposta il menu di pausa
                if (pause.isClicked()){
                    canvas.drawBitmap(pause.getImageBitmap2(), pause.getX() * 31 , pause.getY(), paint);
                    canvas.drawBitmap(gameBar.getPausaRect(), gameBar.getWidth() * 2/9, gameBar.getHeight() * 8/5, paint);
                    canvas.drawText("PAUSA",missioni.getWidth()*3, missioni.getHeight()* 3, paint);
                    canvas.drawBitmap(gameBar.getMusicIcon(), gameBar.getWidth() * 8, gameBar.getHeight() * 10, paint);
                    canvas.drawText("MUSICA",missioni.getWidth()* 9/2, missioni.getHeight()* 9/2, missionPaint);
                    canvas.drawBitmap(gameBar.getAudioIcon(), gameBar.getWidth() * 8, gameBar.getHeight() * 15, paint);
                    canvas.drawText("EFFETTI",missioni.getWidth()* 9/2, missioni.getHeight()* 31/5, missionPaint);
                    canvas.drawBitmap(gameBar.getSaveIcon(), gameBar.getWidth() * 8, gameBar.getHeight() * 20, paint);
                    canvas.drawText("SALVA",missioni.getWidth()* 9/2, missioni.getHeight()* 8, missionPaint);
                    canvas.drawBitmap(gameBar.getExitIcon(), gameBar.getWidth() * 8, gameBar.getHeight() * 25, paint);
                    canvas.drawText("ESCI",missioni.getWidth()* 9/2, missioni.getHeight()* 49/5, missionPaint);
                }
                else {
                    canvas.drawBitmap(pause.getImageBitmap(), pause.getX() * 31 , pause.getY(), paint);
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

        //Cattura i movimenti e le posizioni dei blocchi e dei pulsanti
        @Override
        public boolean onTouchEvent(MotionEvent event) {

            if (event.getAction() == MotionEvent.ACTION_DOWN){
                int touchX = (int)event.getX();
                int touchY = (int)event.getY();

                nJunk = -1;

                if(touchX >= pause.getX() * 32 && touchY >= pause.getY() && touchX < pause.getX() * 32 + pause.getWidth() && touchY < pause.getY() + pause.getHeight() && !(pause.isClicked())){

                    pause.setClicked(true);
                    //sunnyPoints.setSunnyPoints(sunnyPoints.getSunnyPoints()+1);


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
                    boolean isTouching = (touchX >= junk.getX() && touchY >= junk.getY() && touchX < junk.getX() + junk.getWidth() && touchY < junk.getY() + junk.getHeight());
                    if (isTouching) {
                        nJunk = i;
                    }
                }

                for (int i = 0; i < recUnitList.size() - 1; i++) {
                    RecUnit recUnit = recUnitList.get(i);
                    InfoImages infoImages = infoImagesList.get(i);
                    boolean isTouchingRecUnit = (touchX >= recUnit.getX() && touchY >= recUnit.getY() && touchX < recUnit.getX() + recUnit.getWidth() && touchY < recUnit.getY() + recUnit.getHeight());
                    boolean isTouchingUpgrade = (touchX >= upgrade.getX() && touchY >= upgrade.getY() && touchX < upgrade.getX() + upgrade.getWidth() && touchY < upgrade.getY() + upgrade.getHeight());
                    boolean isTouchingLvl1Material = (touchX >= unitInfo.getX() + (int)(218*screenRatioX) && touchY >= unitInfo.getY() + (int)(536*screenRatioY) && touchX < unitInfo.getX() + (int)(218*screenRatioX) + infoImages.getWidth() && touchY < unitInfo.getY() + (int)(536*screenRatioY) + infoImages.getHeight());
                    boolean isTouchingLvl2Material = (touchX >= unitInfo.getX() + (int)(454*screenRatioX) && touchY >= unitInfo.getY() + (int)(536*screenRatioY) && touchX < unitInfo.getX() + (int)(454*screenRatioX) + infoImages.getWidth() && touchY < unitInfo.getY() + (int)(536*screenRatioY) + infoImages.getHeight());
                    boolean isTouchingLvl3Material = (touchX >= unitInfo.getX() + (int)(690*screenRatioX) && touchY >= unitInfo.getY() + (int)(536*screenRatioY) && touchX < unitInfo.getX() + (int)(690*screenRatioX) + infoImages.getWidth() && touchY < unitInfo.getY() + (int)(536*screenRatioY) + infoImages.getHeight());
                    boolean isTouchingMaterialInfo = (touchX >= materialInfo.getX() && touchY >= materialInfo.getY() && touchX < materialInfo.getX() + materialInfo.getWidth() && touchY < materialInfo.getY() + materialInfo.getHeight());

                    if (isTouchingRecUnit) {
                        if (recUnit.getIsUnlocked()) {
                            recUnit.setIsCheckingInfo(true);

                        } else if (!recUnit.getIsUnlocked() && sunnyPoints.getSunnyPoints() >= recUnit.getUnitPrice()) {
                            recUnit.setIsUnlockedToTrue();
                            sunnyPoints.setSunnyPoints(sunnyPoints.getSunnyPoints() - recUnit.getUnitPrice());
                        }

                    } else if (recUnit.getIsCheckingInfo() && touchY < unitInfo.getY() && !isTouchingRecUnit) {
                        recUnit.setIsCheckingInfo(false);
                        resume();
                    }

                    if (recUnit.getIsCheckingInfo() && isTouchingUpgrade && recUnit.getUnitPoints() >= RecUnit.getUpgradePrice() && !recUnit.getIsUpgraded()) {
                        recUnit.setIsUpgraded(true);
                        recUnit.setIsCheckingInfo(false);
                        recUnit.reduceUnitPoints(RecUnit.getUpgradePrice());

                        listaMissioni.get(1).setTotRecUpgr(1);

                        resume();
                    }

                    //Aggiorna i SunnyPoints, gli UnitPoints e gli obiettivi delle missioni in base ai vari materiali prodotti
                    if (recUnit.getIsCheckingInfo()) {

                        if (isTouchingLvl1Material && recUnit.getUnitPoints() >= infoImages.getUnitPoints(0)) {
                            recUnit.reduceUnitPoints(infoImages.getUnitPoints(0));
                            sunnyPoints.setSunnyPoints(sunnyPoints.getSunnyPoints() + infoImages.getSunnyPoints(0));
                            recUnit.setIsCheckingInfo(false);
                            infoImages.setIsCheckingMaterialLvl1Info(true);

                            listaMissioni.get(2).setTotSunnyAccum(infoImages.getSunnyPoints(0));
                            listaMissioni.get(3).setTotUnitPointsUsed(infoImages.getUnitPoints(0));

                            resume();

                        } else if (isTouchingLvl2Material && recUnit.getUnitPoints() >= infoImages.getUnitPoints(1) && recUnit.getIsUpgraded()) {
                            recUnit.reduceUnitPoints(infoImages.getUnitPoints(1));
                            sunnyPoints.setSunnyPoints(sunnyPoints.getSunnyPoints() + infoImages.getSunnyPoints(1));
                            recUnit.setIsCheckingInfo(false);
                            infoImages.setIsCheckingMaterialLvl2Info(true);

                            listaMissioni.get(2).setTotSunnyAccum(infoImages.getSunnyPoints(1));
                            listaMissioni.get(3).setTotUnitPointsUsed(infoImages.getUnitPoints(1));

                            resume();

                        } else if (isTouchingLvl3Material && recUnit.getUnitPoints() >= infoImages.getUnitPoints(2) && recUnit.getIsUpgraded()) {
                            recUnit.reduceUnitPoints(infoImages.getUnitPoints(2));
                            sunnyPoints.setSunnyPoints(sunnyPoints.getSunnyPoints() + infoImages.getSunnyPoints(2));
                            recUnit.setIsCheckingInfo(false);
                            infoImages.setIsCheckingMaterialLvl3Info(true);

                            listaMissioni.get(2).setTotSunnyAccum(infoImages.getSunnyPoints(2));
                            listaMissioni.get(3).setTotUnitPointsUsed(infoImages.getUnitPoints(2));

                            resume();
                        }

                    } else if (infoImages.getIsCheckingMaterialLvl1Info() || infoImages.getIsCheckingMaterialLvl2Info() || infoImages.getIsCheckingMaterialLvl3Info()) {

                        if (!isTouchingMaterialInfo) {
                            infoImages.setIsCheckingMaterialLvl1Info(false);
                            infoImages.setIsCheckingMaterialLvl2Info(false);
                            infoImages.setIsCheckingMaterialLvl3Info(false);
                            resume();
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

                //Mette in pausa o riprende il gioco in base alla pressione dell'utente sul pulsante
                if(x >= pause.getX() * 32 && y >= pause.getY()/8 && x < pause.getX() * 32 + pause.getWidth() && y < pause.getY()/8 + pause.getHeight() && pause.isClicked()){
                    pause();
                    if(MusicPlayer.isPlayingEffect){
                        MusicPlayer.stopEffetti();
                    }
                }
                else if(x >= pause.getX() * 32 && y >= pause.getY()/8 && x < pause.getX() * 32 + pause.getWidth() && y < pause.getY()/8 + pause.getHeight() && !(pause.isClicked())){
                    resume();
                }

                if(x >= missioni.getX() * 34/2 && y >= missioni.getY()-20 && x < missioni.getX() * 34/2 + missioni.getWidth() && y < missioni.getY()-20 + missioni.getHeight() && missioni.isClicked()){
                    pause();
                    if(MusicPlayer.isPlayingEffect){
                        MusicPlayer.stopEffetti();
                    }
                }

                else if(x >= missioni.getX() * 34/2 && y >= missioni.getY()-20 && x < missioni.getX() * 34/2 + missioni.getWidth() && y < missioni.getY()-20 + missioni.getHeight() && !(missioni.isClicked())){
                    resume();
                }

                if ((nJunk != -1)) {
                    Junk junk = junkList.get(nJunk);
                    junk.setBeingDraggedFalse();

                    if (junk instanceof Glass && x >= recUnitList.get(0).getX() && y >= recUnitList.get(0).getY() &&
                            x < recUnitList.get(0).getX() + recUnitList.get(0).getWidth() && y <= recUnitList.get(0).getY()
                            + recUnitList.get(0).getHeight()) {

                        if((recUnitList.get(0).getJunkBeingRecycled() < 2 && recUnitList.get(0).getIsUpgraded()) ||
                           (recUnitList.get(0).getJunkBeingRecycled() < 1 && !recUnitList.get(0).getIsUpgraded())) {
                            junkList.remove(nJunk);
                            recUnitList.get(0).setIsRecycling(true);
                            recUnitList.get(0).junkBeingRecycledPlus();

                            listaMissioni.get(0).setTotJunkRec(1);
                        }

                    } else if (junk instanceof Paper && recUnitList.get(1).getIsUnlocked() && x >= recUnitList.get(1).getX()
                            && y >= recUnitList.get(1).getY() && x < recUnitList.get(1).getX() + recUnitList.get(1).getWidth()
                            && y <= recUnitList.get(1).getY() + recUnitList.get(1).getHeight()) {
                        if((recUnitList.get(1).getJunkBeingRecycled() < 2 && recUnitList.get(1).getIsUpgraded()) ||
                           (recUnitList.get(1).getJunkBeingRecycled() < 1 && !recUnitList.get(1).getIsUpgraded())) {
                            junkList.remove(nJunk);
                            recUnitList.get(1).setIsRecycling(true);
                            recUnitList.get(1).junkBeingRecycledPlus();

                            listaMissioni.get(0).setTotJunkRec(1);
                        }

                    } else if (junk instanceof Aluminum && recUnitList.get(2).getIsUnlocked() && x >= recUnitList.get(2).getX()
                            && y >= recUnitList.get(2).getY() && x < recUnitList.get(2).getX() + recUnitList.get(2).getWidth()
                            && y <= recUnitList.get(2).getY() + recUnitList.get(2).getHeight()) {
                        if((recUnitList.get(2).getJunkBeingRecycled() < 2 && recUnitList.get(2).getIsUpgraded()) ||
                           (recUnitList.get(2).getJunkBeingRecycled() < 1 && !recUnitList.get(2).getIsUpgraded())) {
                            junkList.remove(nJunk);
                            recUnitList.get(2).setIsRecycling(true);
                            recUnitList.get(2).junkBeingRecycledPlus();

                            listaMissioni.get(0).setTotJunkRec(1);
                        }

                    } else if (junk instanceof Steel && recUnitList.get(3).getIsUnlocked() && x >= recUnitList.get(3).getX()
                            && y >= recUnitList.get(3).getY() && x < recUnitList.get(3).getX() + recUnitList.get(3).getWidth()
                            && y <= recUnitList.get(3).getY() + recUnitList.get(3).getHeight()) {
                        if((recUnitList.get(3).getJunkBeingRecycled() < 2 && recUnitList.get(3).getIsUpgraded()) ||
                           (recUnitList.get(3).getJunkBeingRecycled() < 1 && !recUnitList.get(3).getIsUpgraded())) {
                            junkList.remove(nJunk);
                            recUnitList.get(3).setIsRecycling(true);
                            recUnitList.get(3).junkBeingRecycledPlus();

                            listaMissioni.get(0).setTotJunkRec(1);
                        }

                    } else if (junk instanceof Plastic && recUnitList.get(4).getIsUnlocked() && x >= recUnitList.get(4).getX()
                            && y >= recUnitList.get(4).getY() && x < recUnitList.get(4).getX() + recUnitList.get(4).getWidth()
                            && y <= recUnitList.get(4).getY() + recUnitList.get(4).getHeight()) {
                        if((recUnitList.get(4).getJunkBeingRecycled() < 2 && recUnitList.get(4).getIsUpgraded()) ||
                           (recUnitList.get(4).getJunkBeingRecycled() < 1 && !recUnitList.get(4).getIsUpgraded())) {
                            junkList.remove(nJunk);
                            recUnitList.get(4).setIsRecycling(true);
                            recUnitList.get(4).junkBeingRecycledPlus();

                            listaMissioni.get(0).setTotJunkRec(1);
                        }

                    } else if (junk instanceof EWaste && recUnitList.get(5).getIsUnlocked() && x >= recUnitList.get(5).getX()
                            && y >= recUnitList.get(5).getY() && x < recUnitList.get(5).getX() + recUnitList.get(5).getWidth()
                            && y <= recUnitList.get(5).getY() + recUnitList.get(5).getHeight()) {
                        if ((recUnitList.get(5).getJunkBeingRecycled() < 2 && recUnitList.get(5).getIsUpgraded()) ||
                            (recUnitList.get(5).getJunkBeingRecycled() < 1 && !recUnitList.get(5).getIsUpgraded())) {
                            junkList.remove(nJunk);
                            recUnitList.get(5).setIsRecycling(true);
                            recUnitList.get(5).junkBeingRecycledPlus();

                            listaMissioni.get(0).setTotJunkRec(1);
                        }

                    } else if (x >= recUnitList.get(6).getX() && y >= recUnitList.get(6).getY()
                            && x < recUnitList.get(6).getX() + recUnitList.get(6).getWidth() && y < recUnitList.get(6).getY() +
                            recUnitList.get(6).getHeight() && !recUnitList.get(6).getIsRecycling()){

                        if(sunnyPoints.getSunnyPoints() >= 2) {
                            sunnyPoints.setSunnyPoints(sunnyPoints.getSunnyPoints() - 2);
                            junkList.remove(nJunk);
                            recUnitList.get(6).setIsRecycling(true);
                            recUnitList.get(6).junkBeingRecycledPlus();
                        }
                    }
                }
            }
            return true;
        }
}
