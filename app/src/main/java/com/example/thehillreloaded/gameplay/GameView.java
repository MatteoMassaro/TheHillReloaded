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
import com.example.thehillreloaded.gameplay.imageclass.RecImages;
import com.example.thehillreloaded.gameplay.imageclass.UnitPoints;
import com.example.thehillreloaded.gameplay.imageclass.UpgradableUnit;
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
    private double designX = 411f, designY = 731f;
    private int screenX, screenY, spawnBoundX, spawnY;
    public static double screenRatioX, screenRatioY;
    private Background background;
    private Paint paint, transparentPaint, rectPaint;
    private ArrayList<Junk> junkList = new ArrayList<>();
    private ArrayList<Rect> rectList = new ArrayList<>();
    private ArrayList<RecUnit> recUnitList = new ArrayList<>();
    private ArrayList<RecImages> unitPointsRecImageList = new ArrayList<>();
    private ArrayList<RecImages> upgradableUnitList = new ArrayList<>();

    //Setta le view adattandole in base allo schermo
    public GameView(Context context, int screenX, int screenY) {
        super(context);
        this.screenX = screenX;
        this.screenY = screenY;
        screenRatioX = designX/screenX;
        screenRatioY = designY/screenY;
        spawnBoundX = (screenX - (int) (650f * screenRatioX));
        spawnY = screenY * 6/11;
        background = new Background(screenX, screenY, getResources());
        Random random = new Random();
        recUnitList.add(new GlassUnit((int) (-50 * screenRatioX), (int) (250 * screenRatioY), getResources()));
        recUnitList.add(new PaperUnit((int) (-55 * screenRatioX), (int) (1400 * screenRatioY), getResources()));
        recUnitList.add(new AluminumUnit((int) (0 * screenRatioX), (int) (2250 * screenRatioY), getResources()));
        recUnitList.add(new SteelUnit((int) (1550 * screenRatioX), (int) (230 * screenRatioY), getResources()));
        recUnitList.add(new PlasticUnit((int) (1680 * screenRatioX), (int) (1280 * screenRatioY), getResources()));
        recUnitList.add(new EWasteUnit((int) (1400 * screenRatioX), (int) (2300 * screenRatioY), getResources()));
        recUnitList.add(new Incinerator((int) (1000 * screenRatioX), (int) (1100 * screenRatioY), getResources()));
        rectList.add(new Rect((int)(250*screenRatioX), (int)(1200*screenRatioY), (int)(800*screenRatioX), (int)(1250*screenRatioY)));
        rectList.add(new Rect((int)(250*screenRatioX), (int)(2180*screenRatioY), (int)(800*screenRatioX), (int)(2230*screenRatioY)));
        rectList.add(new Rect((int)(250*screenRatioX), (int)(3080*screenRatioY), (int)(800*screenRatioX), (int)(3130*screenRatioY)));
        rectList.add(new Rect((int)(2100*screenRatioX), (int)(1220*screenRatioY), (int)(2650*screenRatioX), (int)(1270*screenRatioY)));
        rectList.add(new Rect((int)(2100*screenRatioX), (int)(2200*screenRatioY), (int)(2650*screenRatioX), (int)(2250*screenRatioY)));
        rectList.add(new Rect((int)(2100*screenRatioX), (int)(3060*screenRatioY), (int)(2650*screenRatioX), (int)(3110*screenRatioY)));
        rectList.add(new Rect((int)(1190*screenRatioX), (int)(2230*screenRatioY), (int)(1740*screenRatioX), (int)(2280*screenRatioY)));
        unitPointsRecImageList.add(new UnitPoints((int)(30*screenRatioX), (int)(350*screenRatioY), getResources()));
        unitPointsRecImageList.add(new UnitPoints((int)(30*screenRatioX), (int)(1450*screenRatioY), getResources()));
        unitPointsRecImageList.add(new UnitPoints((int)(30*screenRatioX), (int)(2350*screenRatioY), getResources()));
        unitPointsRecImageList.add(new UnitPoints((int)(1820*screenRatioX), (int)(350*screenRatioY), getResources()));
        unitPointsRecImageList.add(new UnitPoints((int)(1960*screenRatioX), (int)(1500*screenRatioY), getResources()));
        unitPointsRecImageList.add(new UnitPoints((int)(1600*screenRatioX), (int)(2400*screenRatioY), getResources()));
        upgradableUnitList.add(new UpgradableUnit((int)(220*screenRatioX),(int)(1900*screenRatioY), getResources()));
        upgradableUnitList.add(new UpgradableUnit((int)(220*screenRatioX),(int)(2800*screenRatioY), getResources()));
        upgradableUnitList.add(new UpgradableUnit((int)(2000*screenRatioX),(int)(900*screenRatioY), getResources()));
        upgradableUnitList.add(new UpgradableUnit((int)(2000*screenRatioX),(int)(1900*screenRatioY), getResources()));
        upgradableUnitList.add(new UpgradableUnit((int)(1900*screenRatioX),(int)(2800*screenRatioY), getResources()));
        junkList.add(new Glass((random.nextInt(spawnBoundX) + (int) (40 * screenRatioX)), spawnY, getResources()));

        /*recUnitList.get(1).setIsUnlockedToTrue();
        recUnitList.get(2).setIsUnlockedToTrue();
        recUnitList.get(3).setIsUnlockedToTrue();
        recUnitList.get(4).setIsUnlockedToTrue();
        recUnitList.get(5).setIsUnlockedToTrue();*/

        paint = new Paint();
        paint.setTextSize(64);
        paint.setTypeface(ResourcesCompat.getFont(context, R.font.bevan));
        transparentPaint = new Paint();
        transparentPaint.setAlpha(100);
        rectPaint = new Paint();
        rectPaint.setColor(Color.RED);
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
                junkList.add(new Glass((random.nextInt(spawnBoundX) + (int) (20 * screenRatioX)), spawnY, getResources()));

            } else if (num > Glass.getTasso() && num <= Glass.getTasso() + Paper.getTasso()) {
                junkList.add(new Paper((random.nextInt(spawnBoundX) + (int) (20 * screenRatioX)), spawnY, getResources()));

            } else if (num > Glass.getTasso() + Paper.getTasso() && num <= Glass.getTasso() + Paper.getTasso() + Aluminum.getTasso()) {
                junkList.add(new Aluminum((random.nextInt(spawnBoundX) + (int) (20 * screenRatioX)), spawnY, getResources()));

            } else if (num > Glass.getTasso() + Paper.getTasso() + Aluminum.getTasso() && num <= Glass.getTasso() + Paper.getTasso() + Aluminum.getTasso() + HazarWaste.getTasso()) {
                junkList.add(new HazarWaste((random.nextInt(spawnBoundX) + (int) (20 * screenRatioX)), spawnY, getResources()));

            } else if (num > Glass.getTasso() + Paper.getTasso() + Aluminum.getTasso() + HazarWaste.getTasso() && num <= tassoTotale - Plastic.getTasso() - HazarWaste.getTasso()) {
                junkList.add(new Steel((random.nextInt(spawnBoundX) + (int) (20 * screenRatioX)), spawnY, getResources()));

            } else if (num > tassoTotale - Plastic.getTasso() - HazarWaste.getTasso() && num <= tassoTotale - HazarWaste.getTasso()) {
                junkList.add(new Plastic((random.nextInt(spawnBoundX) + (int) (20 * screenRatioX)), spawnY, getResources()));

            } else {
                junkList.add(new EWaste((random.nextInt(spawnBoundX) + (int) (20 * screenRatioX)), spawnY, getResources()));
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

            if (!junk.getIntersection() && junk.getY() < screenY - (int) (70 * screenRatioY) - junk.getHeight()) {
                junk.setY(junk.getY() + (int) Junk.getSpeed());
            }

            if (junk.getY() >= screenY - (int) (70 * screenRatioY)) {
                junk.setY(screenY - (int) (70 * screenRatioY) - junk.getHeight());
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
                    recUnit.resetState();
                    recUnit.setIsRecyclingToFalse();
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
                Canvas canvas = getHolder().lockCanvas();
                canvas.drawBitmap(background.background, background.getX(), background.getY(), paint);
                canvas.drawBitmap(background.spawnZone, background.getX(), this.screenY*6/11, paint);


                for(int x = 0; x < recUnitList.size(); x++) {
                    RecUnit recUnit = recUnitList.get(x);

                    if(!recUnit.getIsUnlocked()) {
                        canvas.drawBitmap(recUnit.getRecUnit(), recUnit.getX(), recUnit.getY(), transparentPaint);

                        if(x != 0 && x < recUnitList.size()-1) {
                            RecImages upgradableUnit = upgradableUnitList.get(x-1);
                            canvas.drawBitmap(upgradableUnit.getImageBitmap(), upgradableUnit.getX(), upgradableUnit.getY(), paint);
                            canvas.drawText(String.valueOf(recUnit.getUnitPrice()), upgradableUnit.getX() + upgradableUnit.getWidth()*5/9, upgradableUnit.getY() + upgradableUnit.getHeight()*4/5, paint);
                        }

                    } else {
                        Rect rect = rectList.get(x);
                        canvas.drawRect(rect, paint);

                        if(x != recUnitList.size() - 1) {
                            RecImages unitPoints = unitPointsRecImageList.get(x);
                            canvas.drawBitmap(unitPoints.getImageBitmap(), unitPoints.getX(), unitPoints.getY(), paint);
                            canvas.drawText(String.valueOf(recUnit.getUnitPoints()), unitPoints.getX() + unitPoints.getWidth() + (int)(35*screenRatioX), unitPoints.getY() + unitPoints.getHeight()*7/8,paint);
                        }

                        if (!recUnit.getIsUpgraded()) {

                            if (recUnit.getIsRecycling()) {
                                Rect progressRect = new Rect(rect.left, rect.top, rect.left + (int)((rect.right - rect.left) * ((double)(recUnit.getRecTotal())/(double)(recUnit.getMaxRecTotal()))), rect.bottom);
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
                            Rect rectLvl2 = new Rect(rectList.get(x).left, rectList.get(x).top + (int)(50*screenRatioY), rectList.get(x).right, rectList.get(x).bottom +  (int)(50*screenRatioY));
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
                for (Junk junk: junkList) {

                    if (junk.getBeingDragged()) {
                        canvas.drawBitmap(junk.getJunk(), junk.getX(), junk.getY(), transparentPaint);
                        canvas.drawBitmap(junk.getJunk(), junk.getDragX(), junk.getDragY(), paint);
                    } else {
                        canvas.drawBitmap(junk.getJunk(), junk.getX(), junk.getY(), paint);
                    }
                }
                getHolder().unlockCanvasAndPost(canvas);
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

                for (int i = 0; i < junkList.size(); i++) {
                    Junk junk = junkList.get(i);
                    if (touchX >= junk.getX() && touchY >= junk.getY() && touchX < junk.getX() + junk.getWidth() && touchY < junk.getY() + junk.getHeight()) {
                        nJunk = i;
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

                if ((nJunk != -1)) {
                    Junk junk = junkList.get(nJunk);
                    junk.setBeingDraggedFalse();

                    if (junk instanceof Glass && x >= recUnitList.get(0).getX() && y >= recUnitList.get(0).getY() &&
                            x < recUnitList.get(0).getX() + recUnitList.get(0).getWidth() && y <= recUnitList.get(0).getY()
                            + recUnitList.get(0).getHeight() && !recUnitList.get(0).getIsRecycling()) {
                        junkList.remove(nJunk);
                        recUnitList.get(0).setIsRecyclingToTrue();

                    } else if (junk instanceof Paper && recUnitList.get(1).getIsUnlocked() && x >= recUnitList.get(1).getX()
                            && y >= recUnitList.get(1).getY() && x < recUnitList.get(1).getX() + recUnitList.get(1).getWidth()
                            && y <= recUnitList.get(1).getY() + recUnitList.get(1).getHeight() && !recUnitList.get(1).getIsRecycling()) {
                        junkList.remove(nJunk);
                        recUnitList.get(1).setIsRecyclingToTrue();

                    } else if (junk instanceof Aluminum && recUnitList.get(2).getIsUnlocked() && x >= recUnitList.get(2).getX()
                            && y >= recUnitList.get(2).getY() && x < recUnitList.get(2).getX() + recUnitList.get(2).getWidth()
                            && y <= recUnitList.get(2).getY() + recUnitList.get(2).getHeight() && !recUnitList.get(2).getIsRecycling()) {
                        junkList.remove(nJunk);
                        recUnitList.get(2).setIsRecyclingToTrue();

                    } else if (junk instanceof Steel && recUnitList.get(3).getIsUnlocked() && x >= recUnitList.get(3).getX()
                            && y >= recUnitList.get(3).getY() && x < recUnitList.get(3).getX() + recUnitList.get(3).getWidth()
                            && y <= recUnitList.get(3).getY() + recUnitList.get(3).getHeight() && !recUnitList.get(3).getIsRecycling()) {
                        junkList.remove(nJunk);
                        recUnitList.get(3).setIsRecyclingToTrue();

                    } else if (junk instanceof Plastic && recUnitList.get(4).getIsUnlocked() && x >= recUnitList.get(4).getX()
                            && y >= recUnitList.get(4).getY() && x < recUnitList.get(4).getX() + recUnitList.get(4).getWidth()
                            && y <= recUnitList.get(4).getY() + recUnitList.get(4).getHeight() && !recUnitList.get(4).getIsRecycling()) {
                        junkList.remove(nJunk);
                        recUnitList.get(4).setIsRecyclingToTrue();

                    } else if (junk instanceof EWaste && recUnitList.get(5).getIsUnlocked() && x >= recUnitList.get(5).getX()
                            && y >= recUnitList.get(5).getY() && x < recUnitList.get(5).getX() + recUnitList.get(5).getWidth()
                            && y <= recUnitList.get(5).getY() + recUnitList.get(5).getHeight() && !recUnitList.get(5).getIsRecycling()) {
                        junkList.remove(nJunk);
                        recUnitList.get(5).setIsRecyclingToTrue();

                    } else if (x >= recUnitList.get(6).getX() && y >= recUnitList.get(6).getY()
                            && x < recUnitList.get(6).getX() + recUnitList.get(6).getWidth() && y < recUnitList.get(6).getY() +
                            recUnitList.get(6).getHeight() && !recUnitList.get(6).getIsRecycling()){
                        junkList.remove(nJunk);
                        recUnitList.get(6).setIsRecyclingToTrue();
                    }

                }
            }
            return true;
        }
}
