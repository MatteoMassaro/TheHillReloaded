package com.example.thehillreloaded.gameplay;

import static com.example.thehillreloaded.menu.MusicPlayer.mediaPlayer;
import static com.example.thehillreloaded.menu.MusicPlayer.stopMusic;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceView;

import androidx.core.content.res.ResourcesCompat;

import com.example.thehillreloaded.R;
import com.example.thehillreloaded.gameplay.imageclass.AboutToExpire;
import com.example.thehillreloaded.gameplay.imageclass.AluminumInfo;
import com.example.thehillreloaded.gameplay.imageclass.ConfirmBuilding;
import com.example.thehillreloaded.gameplay.imageclass.EWasteInfo;
import com.example.thehillreloaded.gameplay.imageclass.GameBar;
import com.example.thehillreloaded.gameplay.imageclass.GameOver;
import com.example.thehillreloaded.gameplay.imageclass.GlassInfo;
import com.example.thehillreloaded.gameplay.imageclass.IncineratorInfo;
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
import com.example.thehillreloaded.menu.DifficoltaActivity;
import com.example.thehillreloaded.menu.GiocatoreSingoloActivity;
import com.example.thehillreloaded.menu.MenuActivity;
import com.example.thehillreloaded.menu.MusicPlayer;
import com.example.thehillreloaded.menu.SchermataCaricamentoActivity;
import com.example.thehillreloaded.menu.VolumeActivity;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;

public class GameView extends SurfaceView implements Runnable {

    //Variabili, liste e oggetti
    private Thread thread;
    private boolean isPlaying;
    private static int nJunk;
    private final double designX = 1080f, designY = 2072f, designDensity = 2.75;
    private int screenX, screenY, spawnBoundX, spawnY;
    public static double screenRatioX, screenRatioY, densityRatio;
    private Background background;
    private Paint paint, transparentPaint, redPaint, strokePaint, textInfoPaint, otherTextInfoPaint;
    private ArrayList<Junk> junkList = new ArrayList<>();
    private ArrayList<Rect> rectList = new ArrayList<>();
    private ArrayList<RecUnit> recUnitList = new ArrayList<>();
    private ArrayList<RecImages> unitPointsRecImageList = new ArrayList<>();
    private ArrayList<RecImages> upgradeAboutToExpireList = new ArrayList<>();
    private ArrayList<RecImages> unlockableUnitList = new ArrayList<>();
    private ArrayList<RecImages> unitPointsInfoList = new ArrayList<>();
    private ArrayList<RecImages> sunnyPointsInfoList = new ArrayList<>();
    private ArrayList<InfoImages> infoImagesList = new ArrayList<>();
    private ArrayList<Missioni> listaMissioni = new ArrayList<>();
    private ConfirmBuilding confirmBuilding;
    private GameOver gameOver;
    private boolean isGameOver;
    private UnitInfo unitInfo;
    private Upgrade upgrade;
    private UnitPoints unitPoints;
    private SunnyPoints sunnyPoints;
    private MaterialInfo materialInfo;
    private RecImages missioni, pause;
    private GameBar gameBar;
    private int GoalJunkk, GoalRecUpgr, GoalSunnyAcc, GoalUnitPointsUsed;
    private Missioni goals;
    private GameActivity gameActivity;
    private VolumeActivity volumeActivity;

    //Setta le view adattandole in base allo schermo
    public GameView(Context context, int screenX, int screenY, float density) {
        super(context);
        gameActivity = (GameActivity) context;
        this.screenX = screenX;
        this.screenY = screenY;
        screenRatioX = screenX/designX;
        screenRatioY = screenY/designY;
        densityRatio = designDensity/density;
        spawnBoundX = screenX * 65/68;
        spawnY = screenY * 6/11;
        isGameOver = false;

        //definisci oggetti per la visualizzazione e la manipolazione di dati inerenti il background, il rettangolo di spawn, la barra di sopra, ecc.
        background = new Background(screenX, screenY, getResources());
        gameBar = new GameBar(screenX, screenY, getResources());
        gameOver = new GameOver((int)(60*screenRatioX), (int)(650 * screenRatioY), getResources());
        sunnyPoints = new SunnyPoints((int)(30*screenRatioX), (int)(10*screenRatioY), getResources());
        missioni = new Missioni((int)(30*screenRatioX), (int)(10*screenRatioY), getResources());
        pause = new Pause((int)(30*screenRatioX), (int)(10*screenRatioY), getResources());
        goals = new Missioni(missioni.getX(), missioni.getY(), getResources());

        //aggiungi alla lista recUnitList oggetti necessari alla visualizzazione e alla manipolazione dei dati delle unità di riciclo
        recUnitList.add(new GlassUnit((int) (10 * screenRatioX), (int) (88 * screenRatioY), getResources()));
        recUnitList.add(new PaperUnit((int) (7 * screenRatioX), (int) (490 * screenRatioY), getResources()));
        recUnitList.add(new AluminumUnit((int) (10 * screenRatioX), (int) (789 * screenRatioY), getResources()));
        recUnitList.add(new SteelUnit((int) (590 * screenRatioX), (int) (81 * screenRatioY), getResources()));
        recUnitList.add(new PlasticUnit((int) (700 * screenRatioX), (int) (467 * screenRatioY), getResources()));
        recUnitList.add(new EWasteUnit((int) (563 * screenRatioX), (int) (820 * screenRatioY), getResources()));
        recUnitList.add(new Incinerator((int) (381 * screenRatioX), (int) (388 * screenRatioY), getResources()));

        //aggiungi alla lista rectList oggetti necessari alla visualizzazione delle barre presenti al di sotto delle unità di riciclo
        rectList.add(new Rect((int)(110*screenRatioX), (int)(431*screenRatioY), (int)(304*screenRatioX), (int)(448*screenRatioY)));
        rectList.add(new Rect((int)(110*screenRatioX), (int)(764*screenRatioY), (int)(304*screenRatioX), (int)(781*screenRatioY)));
        rectList.add(new Rect((int)(110*screenRatioX), (int)(1085*screenRatioY), (int)(304*screenRatioX), (int)(1102*screenRatioY)));
        rectList.add(new Rect((int)(799*screenRatioX), (int)(436*screenRatioY), (int)(1008*screenRatioX), (int)(453*screenRatioY)));
        rectList.add(new Rect((int)(799*screenRatioX), (int)(781*screenRatioY), (int)(1008*screenRatioX), (int)(798*screenRatioY)));
        rectList.add(new Rect((int)(799*screenRatioX), (int)(1077*screenRatioY), (int)(1008*screenRatioX), (int)(1094*screenRatioY)));
        rectList.add(new Rect((int)(451*screenRatioX), (int)(787*screenRatioY), (int)(660*screenRatioX), (int)(804*screenRatioY)));

        //aggiungi alle liste gli oggetti necessari alla visualizzazione delle icone degli unitPoints
        //e dei warnings (riguardanti l'usura dell'unità aggiornata)
        unitPointsRecImageList.add(new UnitPoints((int)(11.42*screenRatioX), (int)(125*screenRatioY), getResources()));
        unitPointsRecImageList.add(new UnitPoints((int)(11.42*screenRatioX), (int)(514*screenRatioY), getResources()));
        unitPointsRecImageList.add(new UnitPoints((int)(11.42*screenRatioX), (int)(830*screenRatioY), getResources()));
        unitPointsRecImageList.add(new UnitPoints((int)(688*screenRatioX), (int)(138*screenRatioY), getResources()));
        unitPointsRecImageList.add(new UnitPoints((int)(790*screenRatioX), (int)(510*screenRatioY), getResources()));
        unitPointsRecImageList.add(new UnitPoints((int)(613*screenRatioX), (int)(845*screenRatioY), getResources()));
        upgradeAboutToExpireList.add(new AboutToExpire((int)(320*screenRatioX), (int)(155*screenRatioY), getResources()));
        upgradeAboutToExpireList.add(new AboutToExpire((int)(300*screenRatioX), (int)(514*screenRatioY), getResources()));
        upgradeAboutToExpireList.add(new AboutToExpire((int)(350*screenRatioX), (int)(860*screenRatioY), getResources()));
        upgradeAboutToExpireList.add(new AboutToExpire((int)(920*screenRatioX), (int)(160*screenRatioY), getResources()));
        upgradeAboutToExpireList.add(new AboutToExpire((int)(910*screenRatioX), (int)(490*screenRatioY), getResources()));
        upgradeAboutToExpireList.add(new AboutToExpire((int)(850*screenRatioX), (int)(845*screenRatioY), getResources()));

        //aggiungi alla lista unlockableUnitList oggetti necessari alla visualizzazione del cartello del prezzo delle unità non ancora sbloccate
        unlockableUnitList.add(new UnlockableUnit((int)(98*screenRatioX),(int)(670*screenRatioY), getResources()));
        unlockableUnitList.add(new UnlockableUnit((int)(78*screenRatioX),(int)(988*screenRatioY), getResources()));
        unlockableUnitList.add(new UnlockableUnit((int)(760*screenRatioX),(int)(317.52*screenRatioY), getResources()));
        unlockableUnitList.add(new UnlockableUnit((int)(800*screenRatioX),(int)(670*screenRatioY), getResources()));
        unlockableUnitList.add(new UnlockableUnit((int)(720*screenRatioX),(int)(988*screenRatioY), getResources()));

        //definisci oggetti che servono alla visualizzazione delle immagini inerenti le informazioni delle unità di riciclo
        unitInfo = new UnitInfo(0, (int) (1235 * screenRatioY), getResources());
        upgrade = new Upgrade(unitInfo.getX() + (int)(316*screenRatioX), unitInfo.getY() + (int)(388*screenRatioY), getResources());
        unitPoints = new UnitPoints(unitInfo.getX() + (int)(228.34*screenRatioX), unitInfo.getY() + (int)(423.36*screenRatioY), getResources());

        //aggiungi alle liste unitPointsInfoList e sunnyPointsInfoList gli oggetti che servono per la visualizzazione
        //delle icone degli unitPoints e dei sunnyPoints una volta cliccato su di un'unità di riciclo
        unitPointsInfoList.add(new UnitPoints(unitInfo.getX() + (int)(239.75*screenRatioX), unitInfo.getY() + (int)(699*screenRatioY), getResources()));
        unitPointsInfoList.add(new UnitPoints(unitInfo.getX() + (int)(475.7*screenRatioX), unitInfo.getY() + (int)(699*screenRatioY), getResources()));
        unitPointsInfoList.add(new UnitPoints(unitInfo.getX() + (int)(711.65*screenRatioX), unitInfo.getY() + (int)(699*screenRatioY), getResources()));
        sunnyPointsInfoList.add(new SunnyPoints(unitInfo.getX() + (int)(239.75*screenRatioX), unitInfo.getY() + (int)(760*screenRatioY), getResources()));
        sunnyPointsInfoList.add(new SunnyPoints(unitInfo.getX() + (int)(475.7*screenRatioX), unitInfo.getY() + (int)(760*screenRatioY), getResources()));
        sunnyPointsInfoList.add(new SunnyPoints(unitInfo.getX() + (int)(711.65*screenRatioX), unitInfo.getY() + (int)(760*screenRatioY), getResources()));

        //aggiungi alla lista infoImagesList gli oggetti che servono per visualizzare le informazioni delle unità di riciclo
        infoImagesList.add(new GlassInfo(unitInfo.getX() + (int)(222*screenRatioX), unitInfo.getY() + (int)(161*screenRatioY), getResources()));
        infoImagesList.add(new PaperInfo(unitInfo.getX() + (int)(203*screenRatioX), unitInfo.getY() + (int)(190*screenRatioY), getResources()));
        infoImagesList.add(new AluminumInfo(unitInfo.getX() + (int)(215*screenRatioX), unitInfo.getY() + (int)(182*screenRatioY), getResources()));
        infoImagesList.add(new SteelInfo(unitInfo.getX() + (int)(201*screenRatioX), unitInfo.getY() + (int)(180*screenRatioY), getResources()));
        infoImagesList.add(new PlasticInfo(unitInfo.getX() + (int)(208*screenRatioX), unitInfo.getY() + (int)(175*screenRatioY), getResources()));
        infoImagesList.add(new EWasteInfo(unitInfo.getX() + (int)(185*screenRatioX), unitInfo.getY() + (int)(201*screenRatioY), getResources()));
        infoImagesList.add(new IncineratorInfo(unitInfo.getX() + (int)(200*screenRatioX), unitInfo.getY() + (int)(180*screenRatioY), getResources()));


        GoalJunkk = goals.getGoalJunkRec();
        GoalRecUpgr = goals.getGoalRecUpgr();
        GoalSunnyAcc = goals.getGoalSunnyAccum();
        GoalUnitPointsUsed = goals.getGoalUnitPointsUsed();
        listaMissioni.add(new Missioni(missioni.getX(), missioni.getY(), 0, "Ricicla " +GoalJunkk + " rifiuti totali.", getResources()));
        listaMissioni.add(new Missioni(missioni.getX(), missioni.getY(), 1, "Effettua " +GoalRecUpgr + " upgrade per le\nstazioni.", getResources()));
        listaMissioni.add(new Missioni(missioni.getX(), missioni.getY(), 2, "Guadagna " +GoalSunnyAcc + " Sunny\npoints.", getResources()));
        listaMissioni.add(new Missioni(missioni.getX(), missioni.getY(), 3, "Usa " +GoalUnitPointsUsed + " Unit points.", getResources()));

        //aggiungi il primo rifiuto alla lista dei rifiuti
        Random random = new Random();
        Glass glass = new Glass(0, 0, getResources());
        junkList.add(new Glass(random.nextInt(spawnBoundX - glass.getWidth()) + (int) (25 * screenRatioX), spawnY, getResources()));

        //definisci l'oggetto per la visualizzazione delle info riguardanti l'oggetto ottenuto utilizzando gli unitPoints
        materialInfo = new MaterialInfo(0, (int)(230 * screenRatioY), getResources());

        //definisci l'oggetto per la visualizzazione del pop-up di conferma della costruzione di un'unità di riciclo
        confirmBuilding = new ConfirmBuilding((int)(90*screenRatioX), (int)(750 * screenRatioY), getResources());


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
        recUnitList.get(5).unitPointsPlus();
        sunnyPoints.setSunnyPoints(sunnyPoints.getSunnyPoints()+50);*/


        //definisci tutti i paint
        paint = new Paint(); //uno generico
        paint.setTextSize(64 * (float)(screenRatioX * screenRatioY * densityRatio));
        paint.setTypeface(ResourcesCompat.getFont(context, R.font.bevan));
        transparentPaint = new Paint(); //uno per disegnare oggetti semi-trasparenti
        transparentPaint.setAlpha(100);
        redPaint = new Paint(); //uno per disegnare sfondi di colore rosso
        redPaint.setTextSize(64 * (float)(screenRatioX * screenRatioY * densityRatio));
        redPaint.setTypeface(ResourcesCompat.getFont(context, R.font.bevan));
        redPaint.setColor(Color.RED);
        strokePaint = new Paint(); //uno per disegnare i rettangoli in cui verranno disegnati gli oggetti che si possono creare con i sunnyPoints
        strokePaint.setStrokeWidth(10 * (float)(screenRatioX * screenRatioY * densityRatio));
        strokePaint.setStyle(Paint.Style.STROKE);
        textInfoPaint = new Paint(); //uno per il testo riguardante le info delle unità di riciclo
        textInfoPaint.setTextSize(32 * (float)(screenRatioX * screenRatioY * densityRatio));
        textInfoPaint.setTypeface(ResourcesCompat.getFont(context, R.font.bevan));
        otherTextInfoPaint = new Paint(); //uno per il testo di ciò che riguarda le missioni e le informazioni degli oggetti ottenuti con gli unitPoints
        otherTextInfoPaint.setTextSize(36 * (float)(screenRatioX * screenRatioY * densityRatio));
        otherTextInfoPaint.setTypeface(ResourcesCompat.getFont(context, R.font.bevan));
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

        //se la distanza percorsa dagli oggetti Junk è sufficiente
        if (Junk.distanceIsEnough()) {
            Random random = new Random();
            double tassoTotale = Glass.getTasso() + Paper.getTasso() + Aluminum.getTasso() + Steel.getTasso() + Plastic.getTasso() + EWaste.getTasso() + HazarWaste.getTasso();
            double num = tassoTotale * random.nextDouble();

            //esegui controlli sul numero ottenuto, in base al tasso di spawn dei rifiuti, e aggiungi un rifiuto alla lista junkList
            if (num <= Glass.getTasso()) {
                //aggiungi un rifiuto di vetro
                Glass glass = new Glass(0,0, getResources());
                junkList.add(new Glass((random.nextInt(spawnBoundX - glass.getWidth()) + (int) (25 * screenRatioX)), spawnY, getResources()));

            } else if (num > Glass.getTasso() && num <= Glass.getTasso() + Paper.getTasso()) {
                //aggiungi un rifiuto di carta
                Paper paper = new Paper(0,0, getResources());
                junkList.add(new Paper((random.nextInt(spawnBoundX - paper.getWidth()) + (int) (25 * screenRatioX)), spawnY, getResources()));

            } else if (num > Glass.getTasso() + Paper.getTasso() && num <= Glass.getTasso() + Paper.getTasso() + Aluminum.getTasso()) {
                //aggiungi un rifiuto di alluminio
                Aluminum aluminum = new Aluminum(0,0, getResources());
                junkList.add(new Aluminum((random.nextInt(spawnBoundX - aluminum.getWidth()) + (int) (25 * screenRatioX)), spawnY, getResources()));

            } else if (num > Glass.getTasso() + Paper.getTasso() + Aluminum.getTasso() && num <= Glass.getTasso() + Paper.getTasso() + Aluminum.getTasso() + HazarWaste.getTasso()) {
                //aggiungi un rifiuto pericoloso
                HazarWaste hazarWaste = new HazarWaste(0,0, getResources());
                junkList.add(new HazarWaste((random.nextInt(spawnBoundX - hazarWaste.getWidth()) + (int) (25 * screenRatioX)), spawnY, getResources()));

            } else if (num > Glass.getTasso() + Paper.getTasso() + Aluminum.getTasso() + HazarWaste.getTasso() && num <= tassoTotale - Plastic.getTasso() - HazarWaste.getTasso()) {
                //aggiungi un rifiuto di acciaio
                Steel steel = new Steel(0,0, getResources());
                junkList.add(new Steel((random.nextInt(spawnBoundX - steel.getWidth()) + (int) (25 * screenRatioX)), spawnY, getResources()));

            } else if (num > tassoTotale - Plastic.getTasso() - HazarWaste.getTasso() && num <= tassoTotale - HazarWaste.getTasso()) {
                //aggiungi un rifiuto di plastica
                Plastic plastic = new Plastic(0,0, getResources());
                junkList.add(new Plastic((random.nextInt(spawnBoundX - plastic.getWidth()) + (int) (25 * screenRatioX)), spawnY, getResources()));

            } else {
                //aggiungi un rifiuto tecnologico
                EWaste eWaste = new EWaste(0,0, getResources());
                junkList.add(new EWaste((random.nextInt(spawnBoundX - eWaste.getWidth()) + (int) (25 * screenRatioX)), spawnY, getResources()));
            }
        }

        //ciclo for per controllare il posizionamento dei rifiuti
        for (int x = junkList.size() - 1; x >= 0; x--) {
            Junk junk = junkList.get(x);

            //ciclo for per controllare l'intersezione tra due rifiuti
            for (int y = x; y >= 0; y--) {
                Junk otherJunk = junkList.get(y);

                //se due rifiuti diversi si intersecano
                if (Rect.intersects(junk.getCollisionShape(), otherJunk.getCollisionShape()) && x != y) {
                    junk.setIntersection(true); //modifica l'attributo che indica se il rifiuto ne interseca un altro
                    junk.setY(otherJunk.getY() - junk.getHeight() + 1); //posiziona il rifiuto aggiunto successivamente al di sopra del rifiuto aggiunto precedentemente
                    break;
                } else {
                    junk.setIntersection(false);
                }
            }

            //se i rifiuti non intersecano nessun altro rifiuto e non oltrepassano la linea di base
            if (!junk.getIntersection() && junk.getY() < screenY - (int) (24.7 * screenRatioY) - junk.getHeight()) {
                junk.setY(junk.getY() + (int) (Junk.getSpeed() * screenRatioY * densityRatio)); //fai scendere progressivamente il rifiuto, in base all'attributo speed della classe Junk

            } else if (junk.getY() >= screenY - (int) (24.7 * screenRatioY) - junk.getHeight()) { //se i rifiuti oltrepassano la linea di base
                junk.setY(screenY - (int) (24.7 * screenRatioY) - junk.getHeight()); //posiziona i rifiuti sulla linea di base
            }
        }

        //Animazione unità riciclo
        for (int x = 0; x < recUnitList.size(); x++) {
            RecUnit recUnit = recUnitList.get(x);

            //se l'unità di riciclo sta riciclando
            if (recUnit.getIsRecycling()) {

                if (!MusicPlayer.isPlayingEffect && VolumeActivity.flagAudio != 0) {
                    MusicPlayer.playEffetti(getContext(), R.raw.incinerator_sound);
                    MusicPlayer.loopEffetti();
                }

                recUnit.increaseState(); //incrementa lo stato dell'unità di riciclo (serve per le animazioni di riciclo)

                //se lo stato è uguale a 9 per le unità di riciclo o uguale a 12 per l'inceneritore
                if (recUnit.getState() == 9 && !(recUnit instanceof Incinerator) || recUnit.getState() == 12 && recUnit instanceof Incinerator) {
                    recUnit.resetState(); //ritorna allo stato 0
                }

                //se l'unità di riciclo sta riciclando un rifiuto
                if (recUnit.getJunkBeingRecycled() == 1) {

                    //se viene utilizzato il primo processo di smaltimento
                    if (recUnit.getRecTotal() >= recUnit.getRecTotalUpgraded()) {
                        recUnit.increaseRecTotal(); //incrementa l'andamento del primo processo

                    } else if (recUnit.getRecTotalUpgraded() > recUnit.getRecTotal()) { //altrimenti
                        recUnit.increaseRecTotalUpgraded(); //incrementa l'andamento del secondo

                    }

                } else if (recUnit.getJunkBeingRecycled() == 2) { //se invece vengono riciclati due rifiuti
                    recUnit.increaseRecTotal(); //incrementa l'andamento del primo
                    recUnit.increaseRecTotalUpgraded(); //e del secondo processo
                }

                //se il primo processo è completo
                if (recUnit.recTotalIsEnough()) {
                    recUnit.unitPointsPlus(); //aumenta gli unitPoints dell'unità di riciclo di una unità
                    recUnit.recycledUnitPlus(); //aumenta il numero di rifiuti totali riciclati di uno
                    recUnit.junkBeingRecycledMinus(); //diminuisci il numero dei rifiuti che sono attualmente riciclati dall'unità
                    gameBar.increaseScore(recUnit.getRecycleScore()); //aumenta il punteggio di un valore basato sul tipo di unità che ha riciclato il rifiuto

                    //se l'unità di riciclo è aggiornata
                    if(recUnit.getIsUpgraded()) {
                        recUnit.recycledUnitUpgradedPlus(); //aumenta il numero di rifiuti riciclati mentre è aggiornata
                    }

                    //se anche il secondo processo non è attivo
                    if (recUnit.getRecTotalUpgraded() == 0) {
                        recUnit.resetState(); //riporta l'unità di riciclo allo stato iniziale
                        recUnit.setIsRecycling(false); //l'unità di riciclo non sta più riciclando
                        if(MusicPlayer.isPlayingEffect){
                            MusicPlayer.stopEffetti();
                        }
                    }

                } else if (recUnit.recTotalUpgradedIsEnough()) {//se il secondo processo è completo
                    recUnit.unitPointsPlus(); //compi le stesse azioni di prima
                    recUnit.recycledUnitPlus();
                    recUnit.junkBeingRecycledMinus();
                    recUnit.recycledUnitUpgradedPlus(); //e in più aumenta di uno il numero dei rifiuti riciclati mentre l'unità è aggiornata
                    gameBar.increaseScore(recUnit.getRecycleScore());

                    //se anche il processo primario è inattivo
                    if (recUnit.getRecTotal() == 0) {
                        recUnit.resetState(); //riporta l'unità di riciclo allo stato iniziale
                        recUnit.setIsRecycling(false); //l'unità di riciclo non sta più riciclando
                        if(MusicPlayer.isPlayingEffect){
                            MusicPlayer.stopEffetti();
                        }
                    }
                }
            }

            //se l'unità di riciclo è aggiornata e il numero di rifiuti riciclati mentre l'unità è aggiornata è uguale al numero massimo
            if (recUnit.getIsUpgraded() && recUnit.getRecycledUnitUpgraded() == recUnit.getMaxRecycledUnitUpgraded()) {
                recUnit.setIsUpgraded(false); //riporta l'unità al primo livello
                recUnit.recycledUnitUpgradedReset(); //riporta il numero di rifiuti riciclati da aggiornata a 0
            }

            //se l'unità in questione è l'inceneritore e si è premuto il pulsante "power up"
            if (x == recUnitList.size() - 1 && recUnit.getIsPoweredUp()) {
                recUnit.setIsPoweredUp(false); //impedisci a questo corpo di essere rieseguito
                recUnit.junkBeingRecycledPlus(); //aumenta di uno il numero di unità che è attualmente riciclato (l'unità sta ora riciclando)

                //ciclo che esegue un controllo su tutti i rifiuti
                for (int i = junkList.size() - 1; i >= 0; i--) {
                    Junk junk = junkList.get(i);

                    //se i rifiuti sono presenti sulla linea di base
                    if (junk.getY() > screenY - (int) (24.7 * screenRatioY) - junk.getHeight() - 1) {
                        junkList.remove(i); //rimuovi i rifiuti
                        recUnit.recycledUnitPlus();
                    }
                }
            }
        }

            //rinnova i tassi di apparizione degli oggetti
            Glass.rinnovaTasso();
            Paper.rinnovaTasso();
            Aluminum.rinnovaTasso();
            Steel.rinnovaTasso();
            Plastic.rinnovaTasso();
            EWaste.rinnovaTasso();
            HazarWaste.rinnovaTasso();

            //controlla se i tassi massimi dei rifiuti sono stati raggiunti
            Paper.tassoMassimoRaggiunto();
            Aluminum.tassoMassimoRaggiunto();
            Steel.tassoMassimoRaggiunto();
            Plastic.tassoMassimoRaggiunto();
            EWaste.tassoMassimoRaggiunto();
            HazarWaste.tassoMassimoRaggiunto();

            //aumenta la distanza percorsa dai rifiuti
            Junk.increaseDistance();

            //aumenta la velocità di caduta (e di apparizione) dei rifiuti
            Junk.increaseSpeed();
    }

        //Disegna le unità di riciclo
        public void draw() {

            if (getHolder().getSurface().isValid()) {
                boolean pauseFlag = false; //flag utilizzato per mettere in pausa il gioco
                Canvas canvas = getHolder().lockCanvas(); //canvas su cui verranno disegnate le bitmap, i testi, ecc.
                canvas.drawBitmap(background.background, background.getX(), background.getY(), paint); //disegna il background
                canvas.drawBitmap(background.spawnZone, background.getX(), this.screenY * 6 / 11, paint); //disegna la zona di spawn (rettangolo celeste)

                //per ogni unità di riciclo
                for (int x = 0; x < recUnitList.size(); x++) {
                    RecUnit recUnit = recUnitList.get(x);

                    //se l'unità di riciclo non è sbloccata
                    if (!recUnit.getIsUnlocked()) {
                        canvas.drawBitmap(recUnit.getRecUnit(), recUnit.getX(), recUnit.getY(), transparentPaint); //disegnala in maniera parzialmente trasparente
                        RecImages upgradableUnit = unlockableUnitList.get(x - 1); //rappresenta i cartelli da disegnare per ogni unità di riciclo non sbloccata
                        canvas.drawBitmap(upgradableUnit.getImageBitmap(), upgradableUnit.getX(), upgradableUnit.getY(), paint); //disegna i cartelli per ciascuna unità non sbloccata
                        canvas.drawText(String.valueOf(recUnit.getUnitPrice()), upgradableUnit.getX() + upgradableUnit.getWidth() * 5 / 9, upgradableUnit.getY() + upgradableUnit.getHeight() * 4 / 5, paint); //disegna il costo dell'unità di riciclo sul cartello

                    } else { //se l'unità di riciclo è sbloccata

                        Rect rect = rectList.get(x);
                        canvas.drawRect(rect, paint); //disegna ciascun rettangolo che rappresenta il progresso del processo di riciclo

                        //se non si tratta dell'inceneritore
                        if (x != recUnitList.size() - 1) {
                            RecImages unitPoints = unitPointsRecImageList.get(x);
                            canvas.drawBitmap(unitPoints.getImageBitmap(), unitPoints.getX(), unitPoints.getY(), paint); //disegna l'icona degli unitPoints per ciascuna unità di riciclo
                            canvas.drawText(String.valueOf(recUnit.getUnitPoints()), unitPoints.getX() + unitPoints.getWidth() + (int) (13.32 * screenRatioX), unitPoints.getY() + unitPoints.getHeight() * 7 / 8, paint); //disegna il numero di unitPoints dell'unità di riciclo
                        }

                        //se l'unità non è aggiornata
                        if (!recUnit.getIsUpgraded()) {

                            //se l'unità sta riciclando
                            if (recUnit.getIsRecycling()) {
                                Rect progressRect = new Rect(rect.left, rect.top, rect.left + (int) ((rect.right - rect.left) * ((double) (recUnit.getRecTotal()) / (double) (recUnit.getMaxRecTotal()))), rect.bottom); //definisci un nuovo rettangolo che andrà ad indicare l'andamento del processo di riciclo
                                canvas.drawRect(progressRect, redPaint); //disegna il rettangolo di colore rosso

                                if (recUnit.getState() == 0 || recUnit.getState() == 1 || recUnit.getState() == 2) {//se lo stato dell'unità è uguale a 0, 1 o 2
                                    canvas.drawBitmap(recUnit.getRecUnit(), recUnit.getX(), recUnit.getY(), paint); //disegna l'unità di riciclo di base

                                } else if (recUnit.getState() == 3 || recUnit.getState() == 4 || recUnit.getState() == 5) {//se lo stato dell'unità è uguale a 3, 4 o 5
                                    canvas.drawBitmap(recUnit.getRecUnitState2(), recUnit.getX(), recUnit.getY(), paint); //disegna il secondo stato dell'unità di riciclo

                                } else if (recUnit.getState() == 6 || recUnit.getState() == 7 || recUnit.getState() == 8) {//se lo stato dell'unità è uguale a 6, 7 o 8
                                    canvas.drawBitmap(recUnit.getRecUnitState3(), recUnit.getX(), recUnit.getY(), paint); //disegna il terzo stato dell'unità di riciclo

                                } else if (recUnit.getState() == 9 || recUnit.getState() == 10 || recUnit.getState() == 11) {//se lo stato dell'unità è uguale a 9, 10 o 11
                                    canvas.drawBitmap(recUnit.getRecUnitState4(), recUnit.getX(), recUnit.getY(), paint); //disegna il quarto stato dell'unità di riciclo (valido solo per l'inceneritore)
                                }

                            } else if (!recUnit.getIsRecycling()) { //se l'unità non sta riciclando
                                canvas.drawBitmap(recUnit.getRecUnit(), recUnit.getX(), recUnit.getY(), paint); //disegna l'unità di riciclo di base
                            }

                        } else if (recUnit.getIsUpgraded()) { //se l'unità di riciclo è aggiornata
                            Rect rectLvl2 = new Rect(rectList.get(x).left, rectList.get(x).top + (int) (30 * screenRatioY), rectList.get(x).right, rectList.get(x).bottom + (int) (30 * screenRatioY)); //definisci un secondo rettangolo, che sta a rappresentare il secondo processo di riciclo
                            canvas.drawRect(rectLvl2, paint); //disegna il secondo rettangolo

                            //se l'aggiornamento dell'unità di riciclo sta per scadere
                            if (recUnit.getRecycledUnitUpgraded() >= recUnit.getMaxRecycledUnitUpgraded() - 3) {
                                RecImages aboutToExpire = upgradeAboutToExpireList.get(x); //definisci l'icona di warning
                                canvas.drawBitmap(aboutToExpire.getImageBitmap(), aboutToExpire.getX(), aboutToExpire.getY(), paint); //disegnala a schermo
                            }

                            //se l'unità sta riciclando
                            if (recUnit.getIsRecycling()) {
                                //definisci due rettangoli che vanno a rappresentare l'andamento del processo di riciclo
                                Rect progressRect = new Rect(rect.left, rect.top, rect.left + (int) ((rect.right - rect.left) * ((double) (recUnit.getRecTotal()) / (double) (recUnit.getMaxRecTotal()))), rect.bottom);
                                Rect progressRectLvl2 = new Rect(rectLvl2.left, rectLvl2.top, rectLvl2.left + (int) ((rectLvl2.right - rectLvl2.left) * ((double) (recUnit.getRecTotalUpgraded()) / (double) (recUnit.getMaxRecTotal()))), rectLvl2.bottom);

                                //se l'unità di riciclo sta riciclando un solo rifiuto
                                if (recUnit.getJunkBeingRecycled() == 1) {

                                    if (recUnit.getRecTotal() >= recUnit.getRecTotalUpgraded()) { //se l'andamento del primo processo è maggiore dell'andamento del secondo
                                        canvas.drawRect(progressRect, redPaint); //disegna il primo rettangolo di colore rosso

                                    } else if (recUnit.getRecTotalUpgraded() > recUnit.getRecTotal()) { //altrimenti
                                        canvas.drawRect(progressRectLvl2, redPaint); //disegna il secondo rettangolo di colore rosso
                                    }

                                } else if (recUnit.getJunkBeingRecycled() == 2) { //se i rifiuti che vengono riciclati sono due
                                    canvas.drawRect(progressRect, redPaint); //disegna il primo
                                    canvas.drawRect(progressRectLvl2, redPaint); //e il secondo rettangolo di colore rosso
                                }

                                if (recUnit.getState() == 0 || recUnit.getState() == 1 || recUnit.getState() == 2) { //se lo stato dell'unità è uguale a 0, 1 o 2
                                    canvas.drawBitmap(recUnit.getRecUnitLvl2(), recUnit.getX(), recUnit.getY(), paint); //disegna l'unità di riciclo di base aggiornata

                                } else if (recUnit.getState() == 3 || recUnit.getState() == 4 || recUnit.getState() == 5) { //se lo stato dell'unità è uguale a 3, 4 o 5
                                    canvas.drawBitmap(recUnit.getRecUnitLvl2State2(), recUnit.getX(), recUnit.getY(), paint); //disegna il secondo stato dell'unità di riciclo aggiornata

                                } else if (recUnit.getState() == 6 || recUnit.getState() == 7 || recUnit.getState() == 8) { //se lo stato dell'unità è uguale a 6, 7 o 8
                                    canvas.drawBitmap(recUnit.getRecUnitLvl2State3(), recUnit.getX(), recUnit.getY(), paint); //disegna il terzo stato dell'unità di riciclo aggiornata
                                }

                            } else if (!recUnit.getIsRecycling()) { //se l'unità non sta riciclando
                                canvas.drawBitmap(recUnit.getRecUnitLvl2(), recUnit.getX(), recUnit.getY(), paint); //disegna l'unità di riciclo di base aggiornata
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

                    if (junk.getY() < spawnY) {
                        if(!isGameOver) {
                            isPlaying = false;
                        }
                        isGameOver = true;
                        canvas.drawBitmap(gameOver.getImageBitmap(), gameOver.getX(), gameOver.getY(), paint);
                        canvas.drawBitmap(gameOver.getImageBitmap2(), gameOver.getX() + (int)(170*screenRatioX), gameOver.getY() + (int)(350*screenRatioY), paint);
                        canvas.drawBitmap(gameOver.getImageBitmap3(), gameOver.getX() + (int)(170*screenRatioX), gameOver.getY() + (int)(500*screenRatioY), paint);
                        canvas.drawText("RICOMINCIA", gameOver.getX() + gameOver.getWidth() + (int)(200*screenRatioX), gameOver.getY() + (int)(450*screenRatioY), paint);
                        canvas.drawText("ESCI", gameOver.getX() + gameOver.getWidth() + (int)(200*screenRatioX), gameOver.getY() + (int)(600*screenRatioY), paint);
                    }
                }

                for (int x = 0; x <= recUnitList.size() - 1; x++) {
                    RecUnit recUnit = recUnitList.get(x);
                    InfoImages infoImages = infoImagesList.get(x);

                    if (!recUnit.getIsUnlocked()) {

                        if (recUnit.getIsUnlocking()) {
                            canvas.drawBitmap(confirmBuilding.getImageBitmap(), confirmBuilding.getX(), confirmBuilding.getY(), paint);
                            confirmBuilding.drawConfirmBuildingText(confirmBuilding.getX() + (int)(200*screenRatioX), confirmBuilding.getY() + (int)(250*screenRatioY), otherTextInfoPaint, canvas);
                            canvas.drawBitmap(confirmBuilding.getImageBitmap2(), confirmBuilding.getX() + (int)(180*screenRatioX), confirmBuilding.getY() + (int)(350*screenRatioY), paint);
                            canvas.drawBitmap(confirmBuilding.getImageBitmap3(), confirmBuilding.getX() + (int)(500*screenRatioX), confirmBuilding.getY() + (int)(350*screenRatioY), paint);
                            pauseFlag = true;
                        }

                    } else {
                        //Imposta il menu unità quando viene toccata l'unità di riciclo
                        if (recUnit.getIsCheckingInfo() && x != recUnitList.size() - 1) {
                            pauseFlag = true;
                            canvas.drawBitmap(unitInfo.getImageBitmap(), unitInfo.getX(), unitInfo.getY(), paint);

                            canvas.drawText("Tipo unità: " + recUnit.getUnitType(), unitInfo.getX() + (int)(490*screenRatioX), unitInfo.getY() + (int)(290*screenRatioY), textInfoPaint);
                            canvas.drawText("Livello usura: " + recUnit.getRecycledUnitUpgraded() + "/" + recUnit.getMaxRecycledUnitUpgraded(), unitInfo.getX() + (int)(490*screenRatioX), unitInfo.getY() + (int)(370*screenRatioY), textInfoPaint);
                            canvas.drawText("Totale riciclati: " + recUnit.getRecycledUnit() , unitInfo.getX() + (int)(490*screenRatioX), unitInfo.getY() + (int)(410*screenRatioY), textInfoPaint);

                            if (!recUnit.getIsUpgraded()) {
                                canvas.drawBitmap(infoImages.getImageBitmap(), infoImages.getX(), infoImages.getY(), paint);
                                canvas.drawText("Livello: 1", unitInfo.getX() + (int)(490*screenRatioX), unitInfo.getY() + (int)(330*screenRatioY), textInfoPaint);
                                canvas.drawBitmap(upgrade.getImageBitmap(), upgrade.getX(), upgrade.getY(), paint);
                                canvas.drawBitmap(unitPoints.getImageBitmap(), unitPoints.getX(), unitPoints.getY(), paint);
                                canvas.drawText(String.valueOf(recUnit.getUpgradePrice()), unitPoints.getX() - (int)(49.47*screenRatioX), unitPoints.getY() + unitPoints.getHeight()*7/8, paint);

                            } else if (recUnit.getIsUpgraded()) {
                                canvas.drawBitmap(infoImages.getUpgradedImageBitmap(), infoImages.getX(), infoImages.getY(), paint);
                                canvas.drawText("Livello: 2", unitInfo.getX() + (int)(490*screenRatioX), unitInfo.getY() + (int)(330*screenRatioY), textInfoPaint);
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
                        } else if (recUnit.getIsCheckingInfo() && x == recUnitList.size() - 1) {
                            pauseFlag = true;

                            canvas.drawBitmap(unitInfo.getImageBitmap(), unitInfo.getX(), unitInfo.getY(), paint);
                            canvas.drawBitmap(infoImages.getImageBitmap(), infoImages.getX(), infoImages.getY(), paint);
                            canvas.drawText("Tipo unità: " + recUnit.getUnitType(), unitInfo.getX() + (int)(490*screenRatioX), unitInfo.getY() + (int)(320*screenRatioY), textInfoPaint);
                            canvas.drawText("Totale riciclati: " + recUnit.getRecycledUnit() , unitInfo.getX() + (int)(490*screenRatioX), unitInfo.getY() + (int)(360*screenRatioY), textInfoPaint);
                            canvas.drawText("Costo di utilizzo: 2", unitInfo.getX() + (int)(490*screenRatioX), unitInfo.getY() + (int)(400*screenRatioY), textInfoPaint);
                            canvas.drawText("4", unitInfo.getX() + (int)(280*screenRatioX), unitInfo.getY() + (int)(590*screenRatioY), paint);
                            canvas.drawBitmap(sunnyPoints.getImageBitmap(), unitInfo.getX() + (int)(200*screenRatioX), unitInfo.getY() + (int)(530*screenRatioY), paint);
                            canvas.drawBitmap(infoImages.getImageBitmap2(), unitInfo.getX() + (int)(360*screenRatioX), unitInfo.getY() + (int)(490*screenRatioY), paint);
                        }

                        //Imposta il pop-up delle info sui materiali prodotti quando viene prodotto un materiale
                        else if (infoImages.getIsCheckingMaterialLvl1Info()) {
                            canvas.drawBitmap(materialInfo.getImageBitmap(), materialInfo.getX(), materialInfo.getY(), paint);
                            infoImages.drawMaterialLvl1Text(materialInfo.getX() + (int)(220*screenRatioX), materialInfo.getY() + (int)(420*screenRatioY), otherTextInfoPaint, canvas);
                            pauseFlag = true;

                        } else if (infoImages.getIsCheckingMaterialLvl2Info()) {
                            canvas.drawBitmap(materialInfo.getImageBitmap(), materialInfo.getX(), materialInfo.getY(), paint);
                            infoImages.drawMaterialLvl2Text(materialInfo.getX() + (int)(220*screenRatioX), materialInfo.getY() + (int)(420*screenRatioY), otherTextInfoPaint, canvas);
                            pauseFlag = true;

                        } else if (infoImages.getIsCheckingMaterialLvl3Info()) {
                            canvas.drawBitmap(materialInfo.getImageBitmap(), materialInfo.getX(), materialInfo.getY(), paint);
                            infoImages.drawMaterialLvl3Text(materialInfo.getX() + (int)(220*screenRatioX), materialInfo.getY() + (int)(420*screenRatioY), otherTextInfoPaint, canvas);
                            pauseFlag = true;
                        }
                    }
                }

                //Imposta il pannello generale
                canvas.drawBitmap(background.greenRect, background.getX(), background.getY(), paint);
                canvas.drawBitmap(Bitmap.createScaledBitmap(sunnyPoints.getImageBitmap(), (int)(sunnyPoints.getWidth()*1.5), (int)(sunnyPoints.getHeight()*1.5), true), sunnyPoints.getX(), sunnyPoints.getY(), paint);
                canvas.drawText(String.valueOf(sunnyPoints.getSunnyPoints()), sunnyPoints.getX() + sunnyPoints.getWidth() * 2, sunnyPoints.getY() + sunnyPoints.getHeight() * 6/5, paint);
                canvas.drawText("Punti: " + String.valueOf(gameBar.getScore()), sunnyPoints.getX() + (int)(sunnyPoints.getWidth() * 10), sunnyPoints.getY() + sunnyPoints.getHeight() * 6/5, otherTextInfoPaint);
                canvas.drawBitmap(missioni.getImageBitmap(), missioni.getX() * 33/2, missioni.getY() - (float)(10 * screenRatioY) , paint);
                canvas.drawText("Difficoltà:", sunnyPoints.getX() + (int)(sunnyPoints.getWidth() * 3.5), (float) (sunnyPoints.getY() + sunnyPoints.getHeight() * 0.8), textInfoPaint);
                canvas.drawText(DifficoltaActivity.difficoltà, sunnyPoints.getX() + (int)(sunnyPoints.getWidth() * 3.5), (float) (sunnyPoints.getY() + sunnyPoints.getHeight() * 1.4), textInfoPaint);

                //Imposta il pannello delle missioni
                if (missioni.isClicked()){
                    pauseFlag = true;
                    canvas.drawBitmap(gameBar.getMissioniRect(), 0, gameBar.getHeight() * 3, paint);
                    canvas.drawText("MISSIONI",missioni.getWidth()*8/3, missioni.getHeight()*7/2, paint);
                    for(int m = 0; m < listaMissioni.size(); m++) {
                        Missioni mission = listaMissioni.get(m);

                        if (mission.getMissionType() == 0){
                            canvas.drawText(mission.getDescrizione(), missioni.getWidth() * 2, missioni.getHeight() * 9/2, otherTextInfoPaint);
                            if(mission.getTotJunkRec() < mission.getGoalJunkRec()) {
                                canvas.drawText("Obiettivo: " + mission.getTotJunkRec() + "/" + mission.getGoalJunkRec(), missioni.getWidth() * 2, missioni.getHeight() * 10/2, otherTextInfoPaint);
                            }
                            else {
                                canvas.drawText("Completata!", missioni.getWidth() * 2, missioni.getHeight() * 10/2, otherTextInfoPaint);
                            }
                        }

                        if (mission.getMissionType() == 1){
                            canvas.drawText(mission.getDescrizione(), missioni.getWidth() * 2, missioni.getHeight() * 12/2, otherTextInfoPaint);
                            if(mission.getTotRecUpgr() < mission.getGoalRecUpgr()) {
                                canvas.drawText("Obiettivo: " +mission.getTotRecUpgr()+"/"+mission.getGoalRecUpgr(), missioni.getWidth() * 2, missioni.getHeight() * 13/2, otherTextInfoPaint);
                            }
                            else {
                                canvas.drawText("Completata!", missioni.getWidth() * 2, missioni.getHeight() * 13/2, otherTextInfoPaint);
                            }
                        }

                        if (mission.getMissionType() == 2){
                            canvas.drawText(mission.getDescrizione(), missioni.getWidth() * 2, missioni.getHeight() * 15/2, otherTextInfoPaint);

                            if(mission.getTotSunnyAccum() < mission.getGoalSunnyAccum()) {
                                canvas.drawText("Obiettivo: " +mission.getTotSunnyAccum()+"/"+mission.getGoalSunnyAccum(), missioni.getWidth() * 2, missioni.getHeight() * 16/2, otherTextInfoPaint);
                            }
                            else {
                                canvas.drawText("Completata!", missioni.getWidth() * 2, missioni.getHeight() * 16/2, otherTextInfoPaint);
                            }
                        }

                        if (mission.getMissionType() == 3){
                            canvas.drawText(mission.getDescrizione(), missioni.getWidth() * 2, missioni.getHeight() * 18/2, otherTextInfoPaint);

                            if (mission.getTotUnitPointsUsed() < mission.getGoalUnitPointsUsed()) {
                                canvas.drawText("Obiettivo: " +mission.getTotUnitPointsUsed()+"/"+mission.getGoalUnitPointsUsed(), missioni.getWidth() * 2, missioni.getHeight() * 19/2, otherTextInfoPaint);
                            }
                            else {
                                canvas.drawText("Completata!", missioni.getWidth() * 2, missioni.getHeight() * 19/2, otherTextInfoPaint);
                            }
                        }
                    }
                }

                //Imposta il menu di pausa
                if (pause.isClicked()){
                    pauseFlag = true;
                    gameActivity.preferences();
                    canvas.drawBitmap(pause.getImageBitmap2(), pause.getX() * 31 , pause.getY(), paint);
                    canvas.drawBitmap(gameBar.getPausaRect(), gameBar.getWidth() * 2/9, gameBar.getHeight() * 1/4, paint);
                    canvas.drawText("PAUSA",missioni.getWidth()*3, missioni.getHeight() * 11/2, paint);
                    if(gameBar.isMusicClicked() && GameActivity.b == true){
                        canvas.drawBitmap(gameBar.getMusicIcon(), gameBar.getWidth() * 8, gameBar.getHeight() * 16, paint);
                    }
                    else {
                        canvas.drawBitmap(gameBar.getMusicIconRed(), gameBar.getWidth() * 8, gameBar.getHeight() * 16, paint);
                        gameBar.setMusicClicked(false);
                    }
                    canvas.drawText("MUSICA",missioni.getWidth()* 9/2, missioni.getHeight() * 13/2, otherTextInfoPaint);
                    if(gameBar.isAudioClicked() && GameActivity.b1 == true){
                        canvas.drawBitmap(gameBar.getAudioIcon(), gameBar.getWidth() * 8, gameBar.getHeight() * 20, paint);
                    }
                    else{
                        canvas.drawBitmap(gameBar.getAudioIconRed(), gameBar.getWidth() * 8, gameBar.getHeight() * 20, paint);
                        gameBar.setAudioClicked(false);
                    }
                    canvas.drawText("EFFETTI",missioni.getWidth()* 9/2, missioni.getHeight() * 16/2, otherTextInfoPaint);
                    canvas.drawBitmap(gameOver.getImageBitmap2(), gameBar.getWidth() * 8, gameBar.getHeight() * 24, paint);
                    canvas.drawText("RICOMINCIA",missioni.getWidth()* 9/2, missioni.getHeight() * 19/2, otherTextInfoPaint);
                    canvas.drawBitmap(gameBar.getExitIcon(), gameBar.getWidth() * 8, gameBar.getHeight() * 28, paint);
                    canvas.drawText("ESCI",missioni.getWidth()* 9/2, missioni.getHeight() * 11, otherTextInfoPaint);
                }
                else {
                    canvas.drawBitmap(pause.getImageBitmap(), pause.getX() * 31 , pause.getY(), paint);
                }

                getHolder().unlockCanvasAndPost(canvas);

                if (pauseFlag) {
                    if(MusicPlayer.isPlayingEffect){
                        MusicPlayer.stopEffetti();
                    }
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

        //Mette in pausa il gioco
        public void pause() {

            try {
                isPlaying = false;
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        //Ricomincia il gioco
        public void restart() {

            if (MusicPlayer.isPlayingEffect){
                MusicPlayer.stopEffetti();
            }

            RecUnit.resetRecyclingSpeed();
            Junk.resetValues();
            Glass.resetValues();
            Paper.resetValues();
            Aluminum.resetValues();
            HazarWaste.resetValues();
            Steel.resetValues();
            Plastic.resetValues();
            HazarWaste.resetValues();

            gameActivity.finish();
            gameActivity.startActivity(new Intent(gameActivity, SchermataCaricamentoActivity.class));
        }

        //Esce dal gioco
        public void exit() {

            if(MusicPlayer.isPlayingEffect){
                MusicPlayer.stopEffetti();
            }

            RecUnit.resetRecyclingSpeed();
            Junk.resetValues();
            Glass.resetValues();
            Paper.resetValues();
            Aluminum.resetValues();
            HazarWaste.resetValues();
            Steel.resetValues();
            Plastic.resetValues();
            HazarWaste.resetValues();

            gameActivity.finish();
            gameActivity.startActivity(new Intent(gameActivity, MenuActivity.class));
            if(MusicPlayer.isPlayingMusic){
                MusicPlayer.stopMusic();
            }
        }

        //Cattura i movimenti e le posizioni dei blocchi e dei pulsanti
        @Override
        public boolean onTouchEvent(MotionEvent event) {

            if (event.getAction() == MotionEvent.ACTION_DOWN){
                int touchX = (int)event.getX();
                int touchY = (int)event.getY();
                boolean isTouchingPause = touchX >= pause.getX() * 32 && touchY >= pause.getY() && touchX < pause.getX() * 32 + pause.getWidth() && touchY < pause.getY() + pause.getHeight();
                boolean isTouchingMission = touchX >= missioni.getX() * 34/2 && touchY >= missioni.getY()-10 && touchX < missioni.getX() * 34/2 + missioni.getWidth() && touchY < missioni.getY()-10 + missioni.getHeight();
                boolean isTouchingRedoPause = touchX >= gameBar.getWidth() * 8 && touchY >= gameBar.getHeight() * 24 && touchX < gameBar.getWidth() * 8 + gameOver.getWidth() && touchY < gameBar.getHeight() * 24 + gameOver.getHeight();
                boolean isTouchingExitPause = touchX >= gameBar.getWidth() * 8 && touchY >= gameBar.getHeight() * 28 && touchX < gameBar.getWidth() * 8 + gameBar.getWidth()*3 && touchY < gameBar.getHeight() * 28 + gameBar.getHeight()*3;
                nJunk = -1;

                //Mette in pausa o riprende il gioco in base alla pressione dell'utente sul pulsante
                if (isTouchingPause && !(missioni.isClicked())){

                    if (!pause.isClicked() && isPlaying) {
                        pause.setClicked(true);

                    } else if (pause.isClicked() && !isPlaying) {
                        pause.setClicked(false);
                        resume();
                    }

                } else if (isTouchingMission && !(pause.isClicked())) {

                    if (!missioni.isClicked() && isPlaying) {
                        missioni.setClicked(true);

                    } else if (missioni.isClicked() && !isPlaying) {
                        missioni.setClicked(false);
                        resume();
                    }
                }
                if(touchX >= gameBar.getWidth() * 8 && touchY >= gameBar.getHeight() * 16 && touchX < gameBar.getWidth() * 8 + gameBar.getWidth()*3 && touchY < gameBar.getHeight() * 16 + gameBar.getHeight()*3 && pause.isClicked()){
                    if(gameBar.isMusicClicked() && GameActivity.b == false) {
                        gameBar.setMusicClicked(false);
                    }
                    else {
                        gameBar.setMusicClicked(true);
                    }
                }

                if(touchX >= gameBar.getWidth() * 8 && touchY >= gameBar.getHeight() * 20 && touchX < gameBar.getWidth() * 8 + gameBar.getWidth()*3 && touchY < gameBar.getHeight() * 20 + gameBar.getHeight()*3 && pause.isClicked()) {
                    if(gameBar.isAudioClicked() && GameActivity.b1 == false){
                        gameBar.setAudioClicked(false);
                    }
                    else{
                        gameBar.setAudioClicked(true);
                    }
                }

                if(isTouchingExitPause || isTouchingRedoPause){
                    pause.setClicked(false);
                    resume();
                    isPlaying = false;
                }

                for (int i = 0; i < junkList.size(); i++) {
                    Junk junk = junkList.get(i);
                    boolean isTouching = (touchX >= junk.getX() && touchY >= junk.getY() && touchX < junk.getX() + junk.getWidth() && touchY < junk.getY() + junk.getHeight());
                    if (isTouching) {
                        nJunk = i;
                    }
                }

                for (int i = 0; i <= recUnitList.size() - 1; i++) {
                    RecUnit recUnit = recUnitList.get(i);
                    InfoImages infoImages = infoImagesList.get(i);
                    boolean isTouchingRecUnit = (touchX >= recUnit.getX() && touchY >= recUnit.getY() && touchX < recUnit.getX() + recUnit.getWidth() && touchY < recUnit.getY() + recUnit.getHeight());
                    boolean isTouchingGameBar = touchY < screenY * 0.06;

                    if (isTouchingRecUnit && !isTouchingGameBar && !isGameOver) {
                        if (recUnit.getIsUnlocked()) {
                            recUnit.setIsCheckingInfo(true);

                        } else if (!recUnit.getIsUnlocked() && sunnyPoints.getSunnyPoints() >= recUnit.getUnitPrice() && !missioni.isClicked() && !pause.isClicked()) {
                            recUnit.setIsUnlocking(true);
                        }

                    } else if (recUnit.getIsCheckingInfo() && touchY < unitInfo.getY() && !isTouchingRecUnit) {
                        recUnit.setIsCheckingInfo(false);
                        resume();

                    } else if (recUnit.getIsUnlocking() && !isTouchingGameBar && !isGameOver) {
                        boolean isTouchingYesButton = (touchX >= confirmBuilding.getX() + (int)(180*screenRatioX) && touchY >= confirmBuilding.getY() + (int)(350*screenRatioY) && touchX < confirmBuilding.getX() + (int)(180*screenRatioX) + confirmBuilding.getWidth() && touchY < confirmBuilding.getY() + (int)(350*screenRatioY) + confirmBuilding.getHeight());
                        boolean isTouchingNoButton = (touchX >= confirmBuilding.getX() + (int)(500*screenRatioX) && touchY >= confirmBuilding.getY() + (int)(350*screenRatioY) && touchX < confirmBuilding.getX() + (int)(500*screenRatioX) + confirmBuilding.getWidth() && touchY < confirmBuilding.getY() + (int)(350*screenRatioY) + confirmBuilding.getHeight());

                        if (isTouchingYesButton) {
                            recUnit.setIsUnlockedToTrue();
                            sunnyPoints.setSunnyPoints(sunnyPoints.getSunnyPoints() - recUnit.getUnitPrice());
                            recUnit.setIsUnlocking(false);
                            resume();

                        } else if (isTouchingNoButton) {
                            recUnit.setIsUnlocking(false);
                            resume();
                        }

                    } else if (isGameOver) {
                        boolean isTouchingRedo = (touchX >= gameOver.getX() + (int)(170*screenRatioX) && touchY >= gameOver.getY() + (int)(350*screenRatioY) && touchX < gameOver.getX() + (int)(170*screenRatioX) + gameOver.getWidth() && touchY < gameOver.getY() + (int)(350*screenRatioY) + gameOver.getHeight());
                        boolean isTouchingExit = (touchX >= gameOver.getX() + (int)(170*screenRatioX) && touchY >= gameOver.getY() + (int)(500*screenRatioY) && touchX < gameOver.getX() + (int)(170*screenRatioX) + gameOver.getWidth() && touchY < gameOver.getY() + (int)(500*screenRatioY) + gameOver.getHeight());

                        if (isTouchingRedo) {
                            restart();

                        } else if (isTouchingExit) {
                            exit();
                        }
                    }

                    //Aggiorna i SunnyPoints, gli UnitPoints e gli obiettivi delle missioni in base ai vari materiali prodotti
                    if (recUnit.getIsCheckingInfo() && i != recUnitList.size() - 1) {
                        boolean isTouchingUpgrade = (touchX >= upgrade.getX() && touchY >= upgrade.getY() && touchX < upgrade.getX() + upgrade.getWidth() && touchY < upgrade.getY() + upgrade.getHeight());
                        boolean isTouchingLvl1Material = (touchX >= unitInfo.getX() + (int)(218*screenRatioX) && touchY >= unitInfo.getY() + (int)(536*screenRatioY) && touchX < unitInfo.getX() + (int)(218*screenRatioX) + infoImages.getWidth() && touchY < unitInfo.getY() + (int)(536*screenRatioY) + infoImages.getHeight());
                        boolean isTouchingLvl2Material = (touchX >= unitInfo.getX() + (int)(454*screenRatioX) && touchY >= unitInfo.getY() + (int)(536*screenRatioY) && touchX < unitInfo.getX() + (int)(454*screenRatioX) + infoImages.getWidth() && touchY < unitInfo.getY() + (int)(536*screenRatioY) + infoImages.getHeight());
                        boolean isTouchingLvl3Material = (touchX >= unitInfo.getX() + (int)(690*screenRatioX) && touchY >= unitInfo.getY() + (int)(536*screenRatioY) && touchX < unitInfo.getX() + (int)(690*screenRatioX) + infoImages.getWidth() && touchY < unitInfo.getY() + (int)(536*screenRatioY) + infoImages.getHeight());

                        if (isTouchingUpgrade && recUnit.getUnitPoints() >= recUnit.getUpgradePrice() && !recUnit.getIsUpgraded()) {
                            recUnit.setIsUpgraded(true);
                            recUnit.setIsCheckingInfo(false);
                            recUnit.reduceUnitPoints(recUnit.getUpgradePrice());

                            listaMissioni.get(1).setTotRecUpgr(1);

                            resume();

                        } else if (isTouchingLvl1Material && recUnit.getUnitPoints() >= infoImages.getUnitPoints(0)) {
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

                    } else if ((infoImages.getIsCheckingMaterialLvl1Info() || infoImages.getIsCheckingMaterialLvl2Info() || infoImages.getIsCheckingMaterialLvl3Info()) && i != recUnitList.size() - 1) {
                        boolean isTouchingMaterialInfo = (touchX >= materialInfo.getX() && touchY >= materialInfo.getY() && touchX < materialInfo.getX() + materialInfo.getWidth() && touchY < materialInfo.getY() + materialInfo.getHeight());

                        if (!isTouchingMaterialInfo) {
                            infoImages.setIsCheckingMaterialLvl1Info(false);
                            infoImages.setIsCheckingMaterialLvl2Info(false);
                            infoImages.setIsCheckingMaterialLvl3Info(false);
                            resume();
                        }

                    } else if (recUnit.getIsCheckingInfo() && i == recUnitList.size() - 1) {
                        boolean isTouchingPowerUp = (touchX >= unitInfo.getX() + (int)(360*screenRatioX) && touchY >= unitInfo.getY() + (int)(500*screenRatioY) && touchX < unitInfo.getX() + (int)(360*screenRatioX) + infoImages.getWidth() && touchY < unitInfo.getY() + (int)(500*screenRatioY) + infoImages.getHeight());

                        if (isTouchingPowerUp && sunnyPoints.getSunnyPoints() >= 4 && !recUnit.getIsRecycling()) {
                            recUnit.setIsPoweredUp(true);
                            recUnit.setIsCheckingInfo(false);
                            recUnit.setIsRecycling(true);
                            sunnyPoints.setSunnyPoints(sunnyPoints.getSunnyPoints() - 4);
                            nJunk = -1;
                            resume();
                        }
                    }
                }
            }

            if (event.getAction() == MotionEvent.ACTION_MOVE) {
                int x = (int)event.getX();
                int y = (int)event.getY();

                if ((nJunk != -1  && isPlaying)) {
                    Junk junk = junkList.get(nJunk);
                    junk.setBeingDraggedTrue();
                    junk.setDragX(x);
                    junk.setDragY(y);
                }
            }

            if (event.getAction() == MotionEvent.ACTION_UP) {
                int x = (int)event.getX();
                int y = (int)event.getY();
                boolean isTouchingRedoPause = x >= gameBar.getWidth() * 8 && y >= gameBar.getHeight() * 24 && x < gameBar.getWidth() * 8 + gameOver.getWidth() && y < gameBar.getHeight() * 24 + gameOver.getHeight();
                boolean isTouchingExitPause = x >= gameBar.getWidth() * 8 && y >= gameBar.getHeight() * 28 && x < gameBar.getWidth() * 8 + gameBar.getWidth()*3 && y < gameBar.getHeight() * 28 + gameBar.getHeight()*3;

                if(x >= gameBar.getWidth() * 8 && y >= gameBar.getHeight() * 16 && x < gameBar.getWidth() * 8 + gameBar.getWidth()*3 && y < gameBar.getHeight() * 16 + gameBar.getHeight()*3 && pause.isClicked()){
                    if(gameBar.isMusicClicked() && GameActivity.b == false) {
                        MusicPlayer.playMusic(this.gameActivity, R.raw.game_music);
                        this.gameActivity.changeMusic(1);
                    }
                    else {
                        stopMusic();
                        this.gameActivity.changeMusic(0);
                    }
                }

                if(x >= gameBar.getWidth() * 8 && y >= gameBar.getHeight() * 20 && x < gameBar.getWidth() * 8 + gameBar.getWidth()*3 && y < gameBar.getHeight() * 20 + gameBar.getHeight()*3 && pause.isClicked()){
                    if(gameBar.isAudioClicked() && GameActivity.b1 == false) {
                        this.gameActivity.changeAudio(1);
                    }
                    else{
                        this.gameActivity.changeAudio(0);
                    }
                }

                if(isTouchingRedoPause){
                    restart();
                }

                if(isTouchingExitPause){
                    exit();
                }

                if ((nJunk != -1 && isPlaying)) {
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
