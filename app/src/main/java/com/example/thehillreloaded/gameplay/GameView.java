package com.example.thehillreloaded.gameplay;

import static com.example.thehillreloaded.menu.DifficoltaActivity.tassoDifficolta;
import static com.example.thehillreloaded.menu.MenuActivity.densityRatio;
import static com.example.thehillreloaded.menu.MenuActivity.screenRatioX;
import static com.example.thehillreloaded.menu.MenuActivity.screenRatioY;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceView;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;

import com.example.thehillreloaded.R;
import com.example.thehillreloaded.accesso.LoginActivity;
import com.example.thehillreloaded.gameplay.falling_objects.Aluminum;
import com.example.thehillreloaded.gameplay.falling_objects.EWaste;
import com.example.thehillreloaded.gameplay.falling_objects.Glass;
import com.example.thehillreloaded.gameplay.falling_objects.HazarWaste;
import com.example.thehillreloaded.gameplay.falling_objects.Junk;
import com.example.thehillreloaded.gameplay.falling_objects.Paper;
import com.example.thehillreloaded.gameplay.falling_objects.Plastic;
import com.example.thehillreloaded.gameplay.falling_objects.PowerUp;
import com.example.thehillreloaded.gameplay.falling_objects.Steel;
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
import com.example.thehillreloaded.gameplay.recycle.AluminumUnit;
import com.example.thehillreloaded.gameplay.recycle.EWasteUnit;
import com.example.thehillreloaded.gameplay.recycle.GlassUnit;
import com.example.thehillreloaded.gameplay.recycle.Incinerator;
import com.example.thehillreloaded.gameplay.recycle.PaperUnit;
import com.example.thehillreloaded.gameplay.recycle.PlasticUnit;
import com.example.thehillreloaded.gameplay.recycle.RecUnit;
import com.example.thehillreloaded.gameplay.recycle.SteelUnit;
import com.example.thehillreloaded.menu.ConnessioneActivity;
import com.example.thehillreloaded.menu.DifficoltaActivity;
import com.example.thehillreloaded.menu.GiocatoreSingoloActivity;
import com.example.thehillreloaded.menu.MenuActivity;
import com.example.thehillreloaded.menu.MultigiocatoreActivity;
import com.example.thehillreloaded.menu.MusicPlayer;
import com.example.thehillreloaded.menu.VolumeActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.net.ConnectException;
import java.util.ArrayList;
import java.util.Random;

public class GameView extends SurfaceView implements Runnable {

    //Variabili, liste e oggetti

    FirebaseDatabase database;
    DatabaseReference mainRef, ref, ref2, ref3, myRef;

    private Thread thread;
    private boolean isPlaying = false;
    protected static int nJunk;
    private final double designX = 1080f, designY = 2072f, designDensity = 2.75;
    private int screenX, screenY;
    protected int spawnBoundX, spawnY;
    private Background background;
    private Paint paint, transparentPaint, redPaint, strokePaint, textInfoPaint, otherTextInfoPaint;
    protected ArrayList<Junk> junkList = new ArrayList<>();
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
    protected SunnyPoints sunnyPoints;
    private MaterialInfo materialInfo;
    private RecImages missioni;
    protected RecImages pause;
    protected GameBar gameBar;
    private int GoalJunkk, GoalRecUpgr, GoalSunnyAcc, GoalUnitPointsUsed;
    private Missioni goals;
    private VolumeActivity volumeActivity;
    private boolean pauseFlag;
    public static int playerExit;
    private GameActivity gameActivity;
    private int num_junk, index;
    private String avversario;

    public GameView(Context context, int screenX, int screenY, float density) {
        super(context);

        this.screenX = screenX;
        this.screenY = screenY;
        screenRatioX = screenX / designX;
        screenRatioY = screenY / designY;
        densityRatio = designDensity / density;
        spawnBoundX = screenX * 65 / 68;
        spawnY = screenY * 6 / 11;
        isGameOver = false;
        pauseFlag = false;

        database = FirebaseDatabase.getInstance();
        if (LoginActivity.currentUser != null)
            mainRef = database.getReference(LoginActivity.currentUser);

        //definisci oggetti per la visualizzazione e la manipolazione di dati inerenti al background, al rettangolo di spawn, alla barra di sopra, ecc.
        background = new Background(screenX, screenY, getResources());
        gameBar = new GameBar(screenX, screenY, getResources());
        gameOver = new GameOver((int) (60 * screenRatioX), (int) (650 * screenRatioY), getResources());
        sunnyPoints = new SunnyPoints((int) (30 * screenRatioX), (int) (10 * screenRatioY), getResources());
        missioni = new Missioni((int) (30 * screenRatioX), (int) (10 * screenRatioY), getResources());
        pause = new Pause((int) (30 * screenRatioX), (int) (10 * screenRatioY), getResources());
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
        rectList.add(new Rect((int) (110 * screenRatioX), (int) (431 * screenRatioY), (int) (304 * screenRatioX), (int) (448 * screenRatioY)));
        rectList.add(new Rect((int) (110 * screenRatioX), (int) (764 * screenRatioY), (int) (304 * screenRatioX), (int) (781 * screenRatioY)));
        rectList.add(new Rect((int) (110 * screenRatioX), (int) (1085 * screenRatioY), (int) (304 * screenRatioX), (int) (1102 * screenRatioY)));
        rectList.add(new Rect((int) (799 * screenRatioX), (int) (436 * screenRatioY), (int) (1008 * screenRatioX), (int) (453 * screenRatioY)));
        rectList.add(new Rect((int) (799 * screenRatioX), (int) (781 * screenRatioY), (int) (1008 * screenRatioX), (int) (798 * screenRatioY)));
        rectList.add(new Rect((int) (799 * screenRatioX), (int) (1077 * screenRatioY), (int) (1008 * screenRatioX), (int) (1094 * screenRatioY)));
        rectList.add(new Rect((int) (451 * screenRatioX), (int) (787 * screenRatioY), (int) (660 * screenRatioX), (int) (804 * screenRatioY)));

        //aggiungi alle liste gli oggetti necessari alla visualizzazione delle icone degli unitPoints
        //e dei warnings (riguardanti l'usura dell'unità aggiornata)
        unitPointsRecImageList.add(new UnitPoints((int) (11.42 * screenRatioX), (int) (125 * screenRatioY), getResources()));
        unitPointsRecImageList.add(new UnitPoints((int) (11.42 * screenRatioX), (int) (514 * screenRatioY), getResources()));
        unitPointsRecImageList.add(new UnitPoints((int) (11.42 * screenRatioX), (int) (830 * screenRatioY), getResources()));
        unitPointsRecImageList.add(new UnitPoints((int) (688 * screenRatioX), (int) (138 * screenRatioY), getResources()));
        unitPointsRecImageList.add(new UnitPoints((int) (790 * screenRatioX), (int) (510 * screenRatioY), getResources()));
        unitPointsRecImageList.add(new UnitPoints((int) (613 * screenRatioX), (int) (845 * screenRatioY), getResources()));
        upgradeAboutToExpireList.add(new AboutToExpire((int) (320 * screenRatioX), (int) (155 * screenRatioY), getResources()));
        upgradeAboutToExpireList.add(new AboutToExpire((int) (300 * screenRatioX), (int) (514 * screenRatioY), getResources()));
        upgradeAboutToExpireList.add(new AboutToExpire((int) (350 * screenRatioX), (int) (860 * screenRatioY), getResources()));
        upgradeAboutToExpireList.add(new AboutToExpire((int) (920 * screenRatioX), (int) (160 * screenRatioY), getResources()));
        upgradeAboutToExpireList.add(new AboutToExpire((int) (910 * screenRatioX), (int) (490 * screenRatioY), getResources()));
        upgradeAboutToExpireList.add(new AboutToExpire((int) (850 * screenRatioX), (int) (845 * screenRatioY), getResources()));

        //aggiungi alla lista unlockableUnitList oggetti necessari alla visualizzazione del cartello del prezzo delle unità non ancora sbloccate
        unlockableUnitList.add(new UnlockableUnit((int) (98 * screenRatioX), (int) (670 * screenRatioY), getResources()));
        unlockableUnitList.add(new UnlockableUnit((int) (78 * screenRatioX), (int) (988 * screenRatioY), getResources()));
        unlockableUnitList.add(new UnlockableUnit((int) (760 * screenRatioX), (int) (317.52 * screenRatioY), getResources()));
        unlockableUnitList.add(new UnlockableUnit((int) (800 * screenRatioX), (int) (670 * screenRatioY), getResources()));
        unlockableUnitList.add(new UnlockableUnit((int) (720 * screenRatioX), (int) (988 * screenRatioY), getResources()));

        //definisci oggetti che servono alla visualizzazione delle immagini inerenti alle informazioni delle unità di riciclo
        unitInfo = new UnitInfo(0, (int) (1235 * screenRatioY), getResources());
        upgrade = new Upgrade(unitInfo.getX() + (int) (316 * screenRatioX), unitInfo.getY() + (int) (388 * screenRatioY), getResources());
        unitPoints = new UnitPoints(unitInfo.getX() + (int) (228.34 * screenRatioX), unitInfo.getY() + (int) (423.36 * screenRatioY), getResources());

        //aggiungi alle liste unitPointsInfoList e sunnyPointsInfoList gli oggetti che servono per la visualizzazione
        //delle icone degli unitPoints e dei sunnyPoints una volta cliccato su di un'unità di riciclo
        unitPointsInfoList.add(new UnitPoints(unitInfo.getX() + (int) (239.75 * screenRatioX), unitInfo.getY() + (int) (699 * screenRatioY), getResources()));
        unitPointsInfoList.add(new UnitPoints(unitInfo.getX() + (int) (475.7 * screenRatioX), unitInfo.getY() + (int) (699 * screenRatioY), getResources()));
        unitPointsInfoList.add(new UnitPoints(unitInfo.getX() + (int) (711.65 * screenRatioX), unitInfo.getY() + (int) (699 * screenRatioY), getResources()));
        sunnyPointsInfoList.add(new SunnyPoints(unitInfo.getX() + (int) (239.75 * screenRatioX), unitInfo.getY() + (int) (760 * screenRatioY), getResources()));
        sunnyPointsInfoList.add(new SunnyPoints(unitInfo.getX() + (int) (475.7 * screenRatioX), unitInfo.getY() + (int) (760 * screenRatioY), getResources()));
        sunnyPointsInfoList.add(new SunnyPoints(unitInfo.getX() + (int) (711.65 * screenRatioX), unitInfo.getY() + (int) (760 * screenRatioY), getResources()));

        //aggiungi alla lista infoImagesList gli oggetti che servono per visualizzare le informazioni delle unità di riciclo
        infoImagesList.add(new GlassInfo(unitInfo.getX() + (int) (222 * screenRatioX), unitInfo.getY() + (int) (161 * screenRatioY), getResources()));
        infoImagesList.add(new PaperInfo(unitInfo.getX() + (int) (203 * screenRatioX), unitInfo.getY() + (int) (190 * screenRatioY), getResources()));
        infoImagesList.add(new AluminumInfo(unitInfo.getX() + (int) (215 * screenRatioX), unitInfo.getY() + (int) (182 * screenRatioY), getResources()));
        infoImagesList.add(new SteelInfo(unitInfo.getX() + (int) (201 * screenRatioX), unitInfo.getY() + (int) (180 * screenRatioY), getResources()));
        infoImagesList.add(new PlasticInfo(unitInfo.getX() + (int) (208 * screenRatioX), unitInfo.getY() + (int) (175 * screenRatioY), getResources()));
        infoImagesList.add(new EWasteInfo(unitInfo.getX() + (int) (185 * screenRatioX), unitInfo.getY() + (int) (201 * screenRatioY), getResources()));
        infoImagesList.add(new IncineratorInfo(unitInfo.getX() + (int) (200 * screenRatioX), unitInfo.getY() + (int) (180 * screenRatioY), getResources()));


        GoalJunkk = goals.getGoalJunkRec();
        GoalRecUpgr = goals.getGoalRecUpgr();
        GoalSunnyAcc = goals.getGoalSunnyAccum();
        GoalUnitPointsUsed = goals.getGoalUnitPointsUsed();
        listaMissioni.add(new Missioni(missioni.getX(), missioni.getY(), 0, "Ricicla " + GoalJunkk + " rifiuti totali.", getResources()));
        listaMissioni.add(new Missioni(missioni.getX(), missioni.getY(), 1, "Effettua " + GoalRecUpgr + " upgrade per le\nstazioni.", getResources()));
        listaMissioni.add(new Missioni(missioni.getX(), missioni.getY(), 2, "Guadagna " + GoalSunnyAcc + " Sunny\npoints.", getResources()));
        listaMissioni.add(new Missioni(missioni.getX(), missioni.getY(), 3, "Usa " + GoalUnitPointsUsed + " Unit points.", getResources()));

        //definisci l'oggetto per la visualizzazione delle info riguardanti l'oggetto ottenuto utilizzando gli unitPoints
        materialInfo = new MaterialInfo(0, (int) (230 * screenRatioY), getResources());

        //definisci l'oggetto per la visualizzazione del pop-up di conferma della costruzione di un'unità di riciclo
        confirmBuilding = new ConfirmBuilding((int) (90 * screenRatioX), (int) (750 * screenRatioY), getResources());

        //se si sta cominciando una nuova partita
        if(!GiocatoreSingoloActivity.partitaSalvata) {
            //aggiungi il primo rifiuto alla lista dei rifiuti
            Random random = new Random();
            Glass glass = new Glass(0, 0, getResources());
            junkList.add(new Glass(random.nextInt(spawnBoundX - glass.getWidth()) + (int) (25 * screenRatioX), spawnY, getResources()));

            if(MultigiocatoreActivity.unoVSunoClassico || MultigiocatoreActivity.unoVSunoPowerUp){
                tassoDifficolta = 0.8;
            }
            //modifica la velocità dei rifiuti e la velocità di riciclo in base alla difficoltà scelta
            Junk.setSpeed(Junk.getSpeed() * tassoDifficolta);
            Junk.setSpeedIncrease(Junk.getSpeedIncrease() * tassoDifficolta);
            RecUnit.setRecyclingSpeed(RecUnit.getRecyclingSpeed() / tassoDifficolta);

            //se l'utente ha precedentemente effettuato l'accesso
            if(LoginActivity.currentUser != null) {
                ref = mainRef.child("general_data");
                ref.child("is_saved").setValue(false); //ora non è più presente una partita salvata (se prima c'era)
            }

            //se è iniziata una partita multiplayer classica
            if(MultigiocatoreActivity.unoVSunoClassico) {
                ref3 = database.getReference("rooms_uno_contro_uno_classico").child(ConnessioneActivity.roomName);
                ref3.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            if (ConnessioneActivity.player1) {
                                avversario = snapshot.child("player2").getValue(String.class);
                            } else {
                                avversario = snapshot.child("player1").getValue(String.class);
                            }
                        }
                        ref.removeEventListener(this);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
             //se è iniziata una partita multiplayer power-up
            }else if(MultigiocatoreActivity.unoVSunoPowerUp) {
                ref3 = database.getReference("rooms_uno_contro_uno_powerUp").child(ConnessioneActivity.roomName);
                ref3.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            if (ConnessioneActivity.player1) {
                                avversario = snapshot.child("player2").getValue(String.class);
                            } else {
                                avversario = snapshot.child("player1").getValue(String.class);
                            }
                        }
                        ref.removeEventListener(this);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        }

        retrieveData(); //imposta i dati di una partita precedentemente salvata se si sta riprendendo una partita salvata

        //definisci tutti i paint
        paint = new Paint(); //uno generico
        paint.setTextSize(64 * (float) (screenRatioX * screenRatioY * densityRatio));
        paint.setTypeface(ResourcesCompat.getFont(context, R.font.bevan));
        transparentPaint = new Paint(); //uno per disegnare oggetti semi-trasparenti
        transparentPaint.setAlpha(100);
        redPaint = new Paint(); //uno per disegnare sfondi di colore rosso
        redPaint.setTextSize(64 * (float) (screenRatioX * screenRatioY * densityRatio));
        redPaint.setTypeface(ResourcesCompat.getFont(context, R.font.bevan));
        redPaint.setColor(Color.RED);
        strokePaint = new Paint(); //uno per disegnare i rettangoli in cui verranno disegnati gli oggetti che si possono creare con i sunnyPoints
        strokePaint.setStrokeWidth(10 * (float) (screenRatioX * screenRatioY * densityRatio));
        strokePaint.setStyle(Paint.Style.STROKE);
        textInfoPaint = new Paint(); //uno per il testo riguardante le info delle unità di riciclo
        textInfoPaint.setTextSize(32 * (float) (screenRatioX * screenRatioY * densityRatio));
        textInfoPaint.setTypeface(ResourcesCompat.getFont(context, R.font.bevan));
        otherTextInfoPaint = new Paint(); //uno per il testo di ciò che riguarda le missioni e le informazioni degli oggetti ottenuti con gli unitPoints
        otherTextInfoPaint.setTextSize(36 * (float) (screenRatioX * screenRatioY * densityRatio));
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

    //metodo per ricominciare la partita
    public void restart() {

        stopEffects();

        //ripristina tutti gli attributi statici ai valori originali
        RecUnit.resetRecyclingSpeed();
        Junk.resetJunkValues();
        Glass.resetValues();
        Paper.resetValues();
        Aluminum.resetValues();
        HazarWaste.resetValues();
        Steel.resetValues();
        Plastic.resetValues();
        EWaste.resetValues();
    }

    //metodo per salvare la partita
    public void save() {
        saveGeneralData(); //salva i dati generici della partita
        saveRecUnitData(); //salva i dati inerenti alle unità di riciclo
        saveJunkData(); //salva i dati inerenti ai rifiuti

        exit(); //esci dalla partita
    }


    //metodo per uscire dal gioco e tornare nel menù
    public void exit() {

        stopEffects();
        stopMusic();

        //ripristina tutti gli attributi statici ai valori originali
        RecUnit.resetRecyclingSpeed();
        Junk.resetJunkValues();
        Glass.resetValues();
        Paper.resetValues();
        Aluminum.resetValues();
        HazarWaste.resetValues();
        Steel.resetValues();
        Plastic.resetValues();
        EWaste.resetValues();
    }

    //metodo per uscire dal gioco e vedere i risultati
    public void showResult() {

        stopEffects();
        stopMusic();

        //ripristina tutti gli attributi statici ai valori originali
        RecUnit.resetRecyclingSpeed();
        Junk.resetJunkValues();
        Glass.resetValues();
        Paper.resetValues();
        Aluminum.resetValues();
        HazarWaste.resetValues();
        Steel.resetValues();
        Plastic.resetValues();
        EWaste.resetValues();
    }

    //Aggiorna i valori numerici inerenti alle unità di riciclo e ai rifiuti
    public void update() {

        spawnJunk(junkList); //metodo per far apparire a schermo i rifiuti
        positionJunk(junkList); //metodo per gestire la caduta dei rifiuti
        updateRecUnit(recUnitList); //metodo per gestire i dati inerenti alle unità di riciclo
        updateJunkValues(); //metodo per aggiornare i tassi di spawn dei rifiuti
    }

    //metodo per disegnare a schermo i rifiuti, le unità di riciclo, i pop-up, ecc.
    public void draw() {

        if (getHolder().getSurface().isValid()) {
            pauseFlag = false; //flag utilizzato per mettere in pausa il gioco
            Canvas canvas = getHolder().lockCanvas(); //canvas su cui verranno disegnate le bitmap, i testi, ecc.

            drawBackground(canvas); //disegna a schermo il background e il rettangolo in cui spawneranno gli oggetti
            drawRecUnit(recUnitList, canvas); //disegna a schermo le unità di riciclo e le loro animazioni
            drawJunk(junkList, canvas); //disegna a schermo i rifiuti
            drawRecUnitPopups(recUnitList, infoImagesList, canvas); //disegna a schermo i pop-up e le immagini che fanno riferimento alle unità di riciclo
            drawGameBar(canvas); //disegna a schermo la barra di sopra
            drawMissions(listaMissioni, canvas); //disegna a schermo il pop-up delle missioni
            drawPause(canvas); //disegna a schermo il menù di pausa

            getHolder().unlockCanvasAndPost(canvas); //mostra tutti i contenuti del canvas a schermo

            //se è stata eseguita un'azione di pausa
            if (pauseFlag) {
                stopEffects();
                pause(); //metti il gioco in pausa
            }

            if(MultigiocatoreActivity.unoVSunoClassico) {
                ref = database.getReference("rooms_uno_contro_uno_classico").child(ConnessioneActivity.roomName).child("playerExit");
            }else if (MultigiocatoreActivity.unoVSunoPowerUp){
                ref = database.getReference("rooms_uno_contro_uno_powerUp").child(ConnessioneActivity.roomName).child("playerExit");
            }
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists()) {
                        playerExit = snapshot.getValue(Integer.class);
                        if (playerExit != 0) {
                            if(MultigiocatoreActivity.unoVSunoClassico) {
                                ref2 = database.getReference("rooms_uno_contro_uno_classico").child(ConnessioneActivity.roomName);
                            }else if (MultigiocatoreActivity.unoVSunoPowerUp){
                                ref2 = database.getReference("rooms_uno_contro_uno_powerUp").child(ConnessioneActivity.roomName);
                            }
                            ref2.child("player1_isPlaying").setValue(false);
                            ref2.child("player2_isPlaying").setValue(false);
                            ref2.child("numero_giocatori").setValue(0);
                            showResult();
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

    //Cattura i movimenti e le posizioni dei blocchi e dei pulsanti
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        actionDownEvent(event); //effettua controlli nel caso in cui l'utente tocca lo schermo
        actionMoveEvent(event); //effettua controlli nel caso in cui l'utente muove il dito (mentre tocca lo schermo)
        actionUpEvent(event); //effettua controlli nel caso in cui l'utente solleva il dito dallo schermo

        return true;
    }

    private void playEffects() {
        //se non ci sono gli effetti e questi non sono disattivati nell'app
        if (!MusicPlayer.isPlayingEffect && VolumeActivity.flagAudio != 1) {
            MusicPlayer.playEffetti(getContext(), R.raw.incinerator_sound); //esegui gli effetti
            MusicPlayer.loopEffetti(); //continua ad eseguire gli effetti una volta che questi sono terminati
        }
    }

    private void stopMusic() {
        //se la musica è in esecuzione
        if (MusicPlayer.isPlayingMusic) {
            MusicPlayer.stopMusic(); //ferma la musica
        }
    }

    private void stopEffects() {
        if (MusicPlayer.isPlayingEffect) { //se ci sono gli effetti delle unità di riciclo
            MusicPlayer.stopEffetti(); //ferma gli effetti
        }
    }


    //metodi da incapsulare in update()

    protected void spawnJunk(ArrayList<Junk> junkArrayList) {

        //se la distanza percorsa dagli oggetti Junk è sufficiente
        if (Junk.distanceIsEnough()) {
            Random random = new Random();
            double tassoTotale = Glass.getTasso() + Paper.getTasso() + Aluminum.getTasso() + Steel.getTasso() + Plastic.getTasso() + EWaste.getTasso() + HazarWaste.getTasso();
            double num = tassoTotale * random.nextDouble();

            //esegui controlli sul numero ottenuto, in base al tasso di spawn dei rifiuti, e aggiungi un rifiuto alla lista junkList
            if (num <= Glass.getTasso()) {
                //aggiungi un rifiuto di vetro
                Glass glass = new Glass(0, 0, getResources());
                junkArrayList.add(new Glass((random.nextInt(spawnBoundX - glass.getWidth()) + (int) (25 * screenRatioX)), spawnY, getResources()));

            } else if (num > Glass.getTasso() && num <= Glass.getTasso() + Paper.getTasso()) {
                //aggiungi un rifiuto di carta
                Paper paper = new Paper(0, 0, getResources());
                junkArrayList.add(new Paper((random.nextInt(spawnBoundX - paper.getWidth()) + (int) (25 * screenRatioX)), spawnY, getResources()));

            } else if (num > Glass.getTasso() + Paper.getTasso() && num <= Glass.getTasso() + Paper.getTasso() + Aluminum.getTasso()) {
                //aggiungi un rifiuto di alluminio
                Aluminum aluminum = new Aluminum(0, 0, getResources());
                junkArrayList.add(new Aluminum((random.nextInt(spawnBoundX - aluminum.getWidth()) + (int) (25 * screenRatioX)), spawnY, getResources()));

            } else if (num > Glass.getTasso() + Paper.getTasso() + Aluminum.getTasso() && num <= Glass.getTasso() + Paper.getTasso() + Aluminum.getTasso() + HazarWaste.getTasso()) {
                //aggiungi un rifiuto pericoloso
                HazarWaste hazarWaste = new HazarWaste(0, 0, getResources());
                junkArrayList.add(new HazarWaste((random.nextInt(spawnBoundX - hazarWaste.getWidth()) + (int) (25 * screenRatioX)), spawnY, getResources()));

            } else if (num > Glass.getTasso() + Paper.getTasso() + Aluminum.getTasso() + HazarWaste.getTasso() && num <= tassoTotale - Plastic.getTasso() - HazarWaste.getTasso()) {
                //aggiungi un rifiuto di acciaio
                Steel steel = new Steel(0, 0, getResources());
                junkArrayList.add(new Steel((random.nextInt(spawnBoundX - steel.getWidth()) + (int) (25 * screenRatioX)), spawnY, getResources()));

            } else if (num > tassoTotale - Plastic.getTasso() - HazarWaste.getTasso() && num <= tassoTotale - HazarWaste.getTasso()) {
                //aggiungi un rifiuto di plastica
                Plastic plastic = new Plastic(0, 0, getResources());
                junkArrayList.add(new Plastic((random.nextInt(spawnBoundX - plastic.getWidth()) + (int) (25 * screenRatioX)), spawnY, getResources()));

            } else {
                //aggiungi un rifiuto tecnologico
                EWaste eWaste = new EWaste(0, 0, getResources());
                junkArrayList.add(new EWaste((random.nextInt(spawnBoundX - eWaste.getWidth()) + (int) (25 * screenRatioX)), spawnY, getResources()));
            }
        }
    }

    private void positionJunk(ArrayList<Junk> junkArrayList) {

        //ciclo for per controllare il posizionamento dei rifiuti
        for (int x = junkArrayList.size() - 1; x >= 0; x--) {
            Junk junk = junkArrayList.get(x);

            //ciclo for per controllare l'intersezione tra due rifiuti
            for (int y = x; y >= 0; y--) {
                Junk otherJunk = junkArrayList.get(y);

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
    }

    private void updateRecUnit(ArrayList<RecUnit> recUnitArrayList) {

        //per ogni unità di riciclo
        for (int x = 0; x < recUnitArrayList.size(); x++) {
            RecUnit recUnit = recUnitArrayList.get(x);

            //se l'unità di riciclo sta riciclando
            if (recUnit.getIsRecycling() && isPlaying) {

                playEffects();
                updateState(recUnit); //aggiorna lo stato dell'unità di riciclo
                updateRecyclingProcess(recUnit); //aggiorna lo stato del processo (o dei processi) di riciclo
                completeRecyclingProcess(recUnit); //termina il processo di riciclo se questo è completo
            }

            checkIfUpgradeIsExpired(recUnit); //controlla se l'aggiornamento è scaduto
            checkIfPowerUpIsActivated(recUnit); //controlla se è stato attivato il power up dell'inceneritore
        }
    }

    private void updateState(RecUnit recUnit) {

        recUnit.increaseState(); //incrementa lo stato dell'unità di riciclo (serve per le animazioni di riciclo)

        //se lo stato è uguale a 9 per le unità di riciclo o uguale a 12 per l'inceneritore
        if (recUnit.getState() == 9 && !(recUnit instanceof Incinerator) || recUnit.getState() == 12 && recUnit instanceof Incinerator) {
            recUnit.resetState(); //ritorna allo stato 0
        }
    }

    private void updateRecyclingProcess(RecUnit recUnit) {

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
    }

    private void completeRecyclingProcess(RecUnit recUnit) {

        //se il primo processo è completo
        if (recUnit.recTotalIsEnough()) {
            recUnit.unitPointsPlus(); //aumenta gli unitPoints dell'unità di riciclo di una unità
            recUnit.recycledUnitPlus(); //aumenta il numero di rifiuti totali riciclati di uno
            recUnit.junkBeingRecycledMinus(); //diminuisci il numero dei rifiuti che sono attualmente riciclati dall'unità
            gameBar.increaseScore(recUnit.getRecycleScore()); //aumenta il punteggio di un valore basato sul tipo di unità che ha riciclato il rifiuto

            //se l'unità di riciclo è aggiornata
            if (recUnit.getIsUpgraded()) {
                recUnit.recycledUnitUpgradedPlus(); //aumenta il numero di rifiuti riciclati mentre è aggiornata
            }

            //se anche il secondo processo non è attivo
            if (recUnit.getRecTotalUpgraded() == 0) {
                recUnit.resetState(); //riporta l'unità di riciclo allo stato iniziale
                recUnit.setIsRecycling(false); //l'unità di riciclo non sta più riciclando
                stopEffects();
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
                stopEffects();
            }
        }
    }

    private void checkIfUpgradeIsExpired(RecUnit recUnit) {

        //se l'unità di riciclo è aggiornata e il numero di rifiuti riciclati mentre l'unità è aggiornata è uguale al numero massimo
        if (recUnit.getIsUpgraded() && recUnit.getRecycledUnitUpgraded() == recUnit.getMaxRecycledUnitUpgraded()) {
            recUnit.setIsUpgraded(false); //riporta l'unità al primo livello
            recUnit.recycledUnitUpgradedReset(); //riporta il numero di rifiuti riciclati da aggiornata a 0
        }
    }

    private void checkIfPowerUpIsActivated(RecUnit recUnit) {

        //se si è premuto il pulsante "power up" (vale solo per l'inceneritore)
        if (recUnit.getIsPoweredUp()) {

            recUnit.setIsPoweredUp(false); //impedisci a questo corpo di essere rieseguito
            recUnit.junkBeingRecycledPlus(); //aumenta di uno il numero di unità che è attualmente riciclato (l'unità sta ora riciclando)

            //ciclo che esegue un controllo su tutti i rifiuti
            for (int i = junkList.size() - 1; i >= 0; i--) {
                Junk junk = junkList.get(i);

                //se i rifiuti sono presenti sulla linea di base
                if (junk.getY() > screenY - (int) (24.7 * screenRatioY) - junk.getHeight() - 1) {
                    junkList.remove(i); //rimuovi i rifiuti
                    recUnit.recycledUnitPlus(); //aumenta di uno il numero di rifiuti riciclati dall'unità
                }
            }
        }
    }

    protected void updateJunkValues() {

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


    //metodi da incapsulare in draw()

    private void drawBackground(Canvas canvas) {
        canvas.drawBitmap(background.background, background.getX(), background.getY(), paint); //disegna il background
        canvas.drawBitmap(background.spawnZone, background.getX(), this.screenY * 6 / 11, paint); //disegna la zona di spawn (rettangolo celeste)

    }

    private void drawRecUnit(ArrayList<RecUnit> recUnitArrayList, Canvas canvas) {

        //per ogni unità di riciclo
        for (int x = 0; x < recUnitArrayList.size(); x++) {
            RecUnit recUnit = recUnitArrayList.get(x);

            drawLockedRecUnit(recUnit, x, canvas); //disegna a schermo l'unità di riciclo non sbloccata
            drawUnlockedRecUnit(recUnit, x, canvas); //disegna a schermo l'unità di riciclo sbloccata, con tanto di animazioni
        }
    }

    private void drawLockedRecUnit(RecUnit recUnit, int recUnitIndex, Canvas canvas) {

        //se l'unità di riciclo non è sbloccata
        if (!recUnit.getIsUnlocked()) {
            canvas.drawBitmap(recUnit.getRecUnit(), recUnit.getX(), recUnit.getY(), transparentPaint); //disegnala in maniera parzialmente trasparente
            RecImages upgradableUnit = unlockableUnitList.get(recUnitIndex - 1); //rappresenta i cartelli da disegnare per ogni unità di riciclo non sbloccata
            canvas.drawBitmap(upgradableUnit.getImageBitmap(), upgradableUnit.getX(), upgradableUnit.getY(), paint); //disegna i cartelli per ciascuna unità non sbloccata
            canvas.drawText(String.valueOf(recUnit.getUnitPrice()), upgradableUnit.getX() + upgradableUnit.getWidth() * 5 / 9, upgradableUnit.getY() + upgradableUnit.getHeight() * 4 / 5, paint); //disegna il costo dell'unità di riciclo sul cartello
        }
    }

    private void drawUnlockedRecUnit(RecUnit recUnit, int recUnitIndex, Canvas canvas) {

        //se l'unità di riciclo è sbloccata
        if (recUnit.getIsUnlocked()) {
            drawRect(recUnitIndex, canvas); //disegna il rettangolo che rappresenta il processo di riciclo dei rifiuti
            drawUnitPoints(recUnit, recUnitIndex, canvas); //disegna l'icona degli unitPoints
            drawNormalRecUnit(recUnit, recUnitIndex, canvas); //disegna l'unità di riciclo di livello 1, con tanto di animazioni
            drawUpgradedRecUnit(recUnit, recUnitIndex, canvas); //disegna l'unità di riciclo di livello 2, assieme alle animazioni
        }
    }

    private void drawRect(int recUnitIndex, Canvas canvas) {
        Rect rect = rectList.get(recUnitIndex);
        canvas.drawRect(rect, paint); //disegna ciascun rettangolo che rappresenta il progresso del processo di riciclo
    }

    private void drawUnitPoints(RecUnit recUnit, int recUnitIndex, Canvas canvas) {
        //se non si tratta dell'inceneritore
        if (recUnitIndex != recUnitList.size() - 1) {
            RecImages unitPoints = unitPointsRecImageList.get(recUnitIndex);
            canvas.drawBitmap(unitPoints.getImageBitmap(), unitPoints.getX(), unitPoints.getY(), paint); //disegna l'icona degli unitPoints per ciascuna unità di riciclo
            canvas.drawText(String.valueOf(recUnit.getUnitPoints()), unitPoints.getX() + unitPoints.getWidth() + (int) (13.32 * screenRatioX), unitPoints.getY() + unitPoints.getHeight() * 7 / 8, paint); //disegna il numero di unitPoints dell'unità di riciclo
        }
    }

    private void drawNormalRecUnit(RecUnit recUnit, int recUnitIndex, Canvas canvas) {
        //se l'unità non è aggiornata
        if (!recUnit.getIsUpgraded()) {
            drawNormalRecyclingRecUnit(recUnit, recUnitIndex, canvas); //mostra a schermo l'animazione dell'unità di riciclo e la barra rappresentante il progresso del processo di riciclo
            drawNonRecyclingRecUnit(recUnit, canvas); //mostra a schermo l'immagine di base dell'unità di riciclo
        }
    }

    private void drawNormalRecyclingRecUnit(RecUnit recUnit, int recUnitIndex, Canvas canvas) {

        //se l'unità sta riciclando
        if (recUnit.getIsRecycling()) {
            drawProgressRect(recUnit, recUnitIndex, canvas); //mostra a schermo l'andamento del processo di riciclo
            drawRecUnitStates(recUnit, canvas); //mostra a schermo l'animazione dell'unità di riciclo
        }
    }

    private void drawProgressRect(RecUnit recUnit, int recUnitIndex, Canvas canvas) {
        Rect rect = rectList.get(recUnitIndex);
        Rect progressRect = new Rect(rect.left, rect.top, rect.left + (int) ((rect.right - rect.left) * ((double) (recUnit.getRecTotal()) / (double) (recUnit.getMaxRecTotal()))), rect.bottom); //definisci un nuovo rettangolo che andrà ad indicare l'andamento del processo di riciclo
        canvas.drawRect(progressRect, redPaint); //disegna il rettangolo di colore rosso
    }

    private void drawRecUnitStates(RecUnit recUnit, Canvas canvas) {
        if (recUnit.getState() == 0 || recUnit.getState() == 1 || recUnit.getState() == 2) {//se lo stato dell'unità è uguale a 0, 1 o 2
            canvas.drawBitmap(recUnit.getRecUnit(), recUnit.getX(), recUnit.getY(), paint); //disegna l'unità di riciclo di base

        } else if (recUnit.getState() == 3 || recUnit.getState() == 4 || recUnit.getState() == 5) {//se lo stato dell'unità è uguale a 3, 4 o 5
            canvas.drawBitmap(recUnit.getRecUnitState2(), recUnit.getX(), recUnit.getY(), paint); //disegna il secondo stato dell'unità di riciclo

        } else if (recUnit.getState() == 6 || recUnit.getState() == 7 || recUnit.getState() == 8) {//se lo stato dell'unità è uguale a 6, 7 o 8
            canvas.drawBitmap(recUnit.getRecUnitState3(), recUnit.getX(), recUnit.getY(), paint); //disegna il terzo stato dell'unità di riciclo

        } else if (recUnit.getState() == 9 || recUnit.getState() == 10 || recUnit.getState() == 11) {//se lo stato dell'unità è uguale a 9, 10 o 11
            canvas.drawBitmap(recUnit.getRecUnitState4(), recUnit.getX(), recUnit.getY(), paint); //disegna il quarto stato dell'unità di riciclo (valido solo per l'inceneritore)
        }
    }

    private void drawNonRecyclingRecUnit(RecUnit recUnit, Canvas canvas) {

        //se l'unità non sta riciclando
        if (!recUnit.getIsRecycling()) {
            canvas.drawBitmap(recUnit.getRecUnit(), recUnit.getX(), recUnit.getY(), paint); //disegna l'unità di riciclo di base
        }
    }

    private void drawUpgradedRecUnit(RecUnit recUnit, int recUnitIndex, Canvas canvas) {

        //se l'unità è aggiornata
        if (recUnit.getIsUpgraded()) {
            drawUpgradedRect(recUnitIndex, canvas); //mostra a schermo la barra extra delle unità aggiornate
            drawWarning(recUnit, recUnitIndex, canvas); //mostra a schermo l'icona di warning
            drawUpgradedRecyclingRecUnit(recUnit, recUnitIndex, canvas); //mostra a schermo l'animazione dell'unità di riciclo aggiornata, assieme al progresso della barra extra
            drawUpgradedNonRecyclingRecUnit(recUnit, canvas); //mostra a schermo l'immagine di base dell'unità di riciclo aggiornata
        }
    }

    public void drawUpgradedRect(int recUnitIndex, Canvas canvas) {
        Rect rectLvl2 = new Rect(rectList.get(recUnitIndex).left, rectList.get(recUnitIndex).top + (int) (30 * screenRatioY), rectList.get(recUnitIndex).right, rectList.get(recUnitIndex).bottom + (int) (30 * screenRatioY)); //definisci un secondo rettangolo, che sta a rappresentare il secondo processo di riciclo
        canvas.drawRect(rectLvl2, paint); //disegna il secondo rettangolo
    }

    public void drawWarning(RecUnit recUnit, int recUnitIndex, Canvas canvas) {
        //se l'aggiornamento dell'unità di riciclo sta per scadere
        if (recUnit.getRecycledUnitUpgraded() >= recUnit.getMaxRecycledUnitUpgraded() - 3) {
            RecImages aboutToExpire = upgradeAboutToExpireList.get(recUnitIndex); //definisci l'icona di warning
            canvas.drawBitmap(aboutToExpire.getImageBitmap(), aboutToExpire.getX(), aboutToExpire.getY(), paint); //disegnala a schermo
        }
    }

    public void drawUpgradedRecyclingRecUnit(RecUnit recUnit, int recUnitIndex, Canvas canvas) {

        //se l'unità sta riciclando
        if (recUnit.getIsRecycling()) {
            drawProgressRect(recUnit, recUnitIndex, canvas); //disegna a schermo il rettangolo rosso che rappresenta il progresso del primo processo di riciclo
            drawUpgradedProgressRect(recUnit, recUnitIndex, canvas); //disegna a schermo il rettangolo rosso che rappresenta il progresso del secondo processo di riciclo
            drawUpgradedRecUnitStates(recUnit, canvas); //disegna a schermo l'animazione dell'unità di riciclo aggiornata
        }
    }

    public void drawUpgradedProgressRect(RecUnit recUnit, int recUnitIndex, Canvas canvas) {
        Rect rectLvl2 = new Rect(rectList.get(recUnitIndex).left, rectList.get(recUnitIndex).top + (int) (30 * screenRatioY), rectList.get(recUnitIndex).right, rectList.get(recUnitIndex).bottom + (int) (30 * screenRatioY)); //definisci un secondo rettangolo, che sta a rappresentare il secondo processo di riciclo
        Rect progressRectLvl2 = new Rect(rectLvl2.left, rectLvl2.top, rectLvl2.left + (int) ((rectLvl2.right - rectLvl2.left) * ((double) (recUnit.getRecTotalUpgraded()) / (double) (recUnit.getMaxRecTotal()))), rectLvl2.bottom); //definisci un secondo rettangolo, che sta a rappresentare il progresso del secondo processo di riciclo
        canvas.drawRect(progressRectLvl2, redPaint); //disegna il rettangolo di colore rosso
    }

    public void drawUpgradedRecUnitStates(RecUnit recUnit, Canvas canvas) {
        if (recUnit.getState() == 0 || recUnit.getState() == 1 || recUnit.getState() == 2) { //se lo stato dell'unità è uguale a 0, 1 o 2
            canvas.drawBitmap(recUnit.getRecUnitLvl2(), recUnit.getX(), recUnit.getY(), paint); //disegna l'unità di riciclo di base aggiornata

        } else if (recUnit.getState() == 3 || recUnit.getState() == 4 || recUnit.getState() == 5) { //se lo stato dell'unità è uguale a 3, 4 o 5
            canvas.drawBitmap(recUnit.getRecUnitLvl2State2(), recUnit.getX(), recUnit.getY(), paint); //disegna il secondo stato dell'unità di riciclo aggiornata

        } else if (recUnit.getState() == 6 || recUnit.getState() == 7 || recUnit.getState() == 8) { //se lo stato dell'unità è uguale a 6, 7 o 8
            canvas.drawBitmap(recUnit.getRecUnitLvl2State3(), recUnit.getX(), recUnit.getY(), paint); //disegna il terzo stato dell'unità di riciclo aggiornata
        }
    }

    private void drawUpgradedNonRecyclingRecUnit(RecUnit recUnit, Canvas canvas) {

        if (!recUnit.getIsRecycling()) { //se l'unità non sta riciclando
            canvas.drawBitmap(recUnit.getRecUnitLvl2(), recUnit.getX(), recUnit.getY(), paint); //disegna l'unità di riciclo di base aggiornata
        }
    }

    private void drawJunk(ArrayList<Junk> junkArrayList, Canvas canvas) {

        //per ogni rifiuto
        for (int x = 0; x < junkArrayList.size(); x++) {
            Junk junk = junkArrayList.get(x);

            drawDraggedJunk(junk, canvas); //disegna il rifiuto che viene trascinato (l'ombra opaca, il rifiuto nella sua posizione originale trasparente)
            drawFallingJunk(junk, canvas); //disegna i rifiuti
            drawGameOverPopup(junk, canvas); //disegna il pop-up di fine partita
        }
    }

    private void drawDraggedJunk(Junk junk, Canvas canvas) {
        //se il rifiuto viene trascinato
        if (junk.getBeingDragged()) {
            canvas.drawBitmap(junk.getFallingObject(), junk.getX(), junk.getY(), transparentPaint); //disegna il rifiuto trasparente nella sua posizione originale
            canvas.drawBitmap(junk.getFallingObject(), junk.getDragX(), junk.getDragY(), paint); //e disegna l'ombra del rifiuto che viene trascinato
        }
    }

    private void drawFallingJunk(Junk junk, Canvas canvas) {
        //se il rifiuto non viene trascinato
        if (!junk.getBeingDragged()) {
            canvas.drawBitmap(junk.getFallingObject(), junk.getX(), junk.getY(), paint); //disegna il rifiuto in chiaro nella sua posizione originale
        }
    }

    private void drawGameOverPopup(Junk junk, Canvas canvas) {
        //se il rifiuto supera la linea blu di sopra
        if (junk.getY() < spawnY) {

            if (isGameOver) { //e se la variabile isGameOver è true
                try {
                    thread.sleep(1500); //metti il thread in pausa per 1,5 secondi
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //disegna a schermo il pop-up di fine partita
                canvas.drawBitmap(gameOver.getImageBitmap(), gameOver.getX(), gameOver.getY(), paint);
                if(!MultigiocatoreActivity.unoVSunoClassico && !MultigiocatoreActivity.unoVSunoPowerUp) {
                    canvas.drawBitmap(gameOver.getImageBitmap2(), gameOver.getX() + (int) (170 * screenRatioX), gameOver.getY() + (int) (350 * screenRatioY), paint);
                    canvas.drawBitmap(gameOver.getImageBitmap3(), gameOver.getX() + (int) (170 * screenRatioX), gameOver.getY() + (int) (500 * screenRatioY), paint);
                    canvas.drawText("RICOMINCIA", gameOver.getX() + gameOver.getWidth() + (int) (200 * screenRatioX), gameOver.getY() + (int) (450 * screenRatioY), paint);
                    canvas.drawText("ESCI", gameOver.getX() + gameOver.getWidth() + (int) (200 * screenRatioX), gameOver.getY() + (int) (600 * screenRatioY), paint);
                }else{
                    canvas.drawBitmap(gameOver.getImageBitmap4(), gameOver.getX() + (int) (170 * screenRatioX), gameOver.getY() + (int) (420 * screenRatioY), paint);
                    canvas.drawText("VEDI", gameOver.getX() + gameOver.getWidth() + (int) (220 * screenRatioX), gameOver.getY() + (int) (450 * screenRatioY), paint);
                    canvas.drawText("RISULTATI", gameOver.getX() + gameOver.getWidth() + (int) (220 * screenRatioX), gameOver.getY() + (int) (550 * screenRatioY), paint);
                }
                isPlaying = false; //non verrà più rieseguito il corpo del metodo run() (simile a pause())
            }

            isGameOver = true; //variabile che consente di interagire col pop-up di fine partita (assegnare true alla fine di questo corpo consente di ottenere una buona animazione)
        }
    }

    private void drawRecUnitPopups(ArrayList<RecUnit> recUnitArrayList, ArrayList<InfoImages> infoImagesArrayList, Canvas canvas) {
        //per ogni unità di riciclo
        for (int x = 0; x <= recUnitArrayList.size() - 1; x++) {
            RecUnit recUnit = recUnitArrayList.get(x);
            InfoImages infoImages = infoImagesArrayList.get(x);

            drawLockedRecUnitPopup(recUnit, canvas); //disegna i pop-up delle unità non sbloccate
            drawUnlockedRecUnitPopup(recUnit, infoImages, x, canvas); //disegna i pop-up delle unità sbloccate
        }
    }

    private void drawLockedRecUnitPopup(RecUnit recUnit, Canvas canvas) {
        //se l'unità non è sbloccata
        if (!recUnit.getIsUnlocked()) {

            //e se l'unità sta per essere sbloccata (è stata toccata l'unità di riciclo con sufficienti sunnyPoints)
            if (recUnit.getIsUnlocking()) {

                //disegna a schermo il pop-up per sbloccare l'unità
                canvas.drawBitmap(confirmBuilding.getImageBitmap(), confirmBuilding.getX(), confirmBuilding.getY(), paint);
                confirmBuilding.drawConfirmBuildingText(confirmBuilding.getX() + (int) (200 * screenRatioX), confirmBuilding.getY() + (int) (250 * screenRatioY), otherTextInfoPaint, canvas);
                canvas.drawBitmap(confirmBuilding.getImageBitmap2(), confirmBuilding.getX() + (int) (180 * screenRatioX), confirmBuilding.getY() + (int) (350 * screenRatioY), paint);
                canvas.drawBitmap(confirmBuilding.getImageBitmap3(), confirmBuilding.getX() + (int) (500 * screenRatioX), confirmBuilding.getY() + (int) (350 * screenRatioY), paint);
                pauseFlag = true; //il gioco verrà messo in pausa
            }

        }
    }

    private void drawUnlockedRecUnitPopup(RecUnit recUnit, InfoImages infoImages, int recUnitIndex, Canvas canvas) {
        //se l'unità è sbloccata
        if (recUnit.getIsUnlocked()) {
            drawUnlockedRecUnitInfo(recUnit, infoImages, recUnitIndex, canvas); //mostra le informazioni riguardanti le unità di riciclo (escluso l'inceneritore)
            drawIncineratorInfo(recUnit, infoImages, recUnitIndex, canvas); //mostra le informazioni riguardanti l'inceneritore
            drawLvl1MaterialInfo(infoImages, canvas); //mostra le informazioni riguardanti i materiali di grado 1
            drawLvl2MaterialInfo(infoImages, canvas); //mostra le informazioni riguardanti i materiali di grado 2
            drawLvl3MaterialInfo(infoImages, canvas); //mostra le informazioni riguardanti i materiali di grado 3
        }
    }

    private void drawUnlockedRecUnitInfo(RecUnit recUnit, InfoImages infoImages, int recUnitIndex, Canvas canvas) {
        //se si vogliono consultare le informazioni di un'unità tranne che l'inceneritore (si è cliccato sull'unità)
        if (recUnit.getIsCheckingInfo() && recUnitIndex != recUnitList.size() - 1) {
            stopEffects(); //ferma gli effetti di gioco
            pauseFlag = true; //il gioco verrà messo in pausa
            drawRecUnitInfoBackground(canvas); //mostra il background delle informazioni delle unità di riciclo
            drawRecUnitInfoText(recUnit, canvas); //mostra il testo delle informazioni delle unità di riciclo
            drawMaterialRect(infoImages, canvas); //mostra il rettangolo e il materiale di grado 1
            drawNonUpgradedRecUnitInfo(recUnit, infoImages, canvas); //mostra le informazioni delle unità di riciclo non aggiornate
            drawUpgradedRecUnitInfo(recUnit, infoImages, canvas); //mostra le informazioni delle unità di riciclo aggiornate
            drawUnitSunnyPointsInfo(recUnit, infoImages, canvas); //mostra le icone e gli unitPoints necessari per ottenere n sunnyPoints
        }
    }

    private void drawRecUnitInfoBackground(Canvas canvas) {
        canvas.drawBitmap(unitInfo.getImageBitmap(), unitInfo.getX(), unitInfo.getY(), paint); //disegna a schermo il background delle informazioni dell'unità di riciclo
    }

    private void drawRecUnitInfoText(RecUnit recUnit, Canvas canvas) {
        //disegna a schermo il testo riguardante le informazioni
        canvas.drawText("Tipo unità: " + recUnit.getUnitType(), unitInfo.getX() + (int) (490 * screenRatioX), unitInfo.getY() + (int) (290 * screenRatioY), textInfoPaint);
        canvas.drawText("Livello usura: " + recUnit.getRecycledUnitUpgraded() + "/" + recUnit.getMaxRecycledUnitUpgraded(), unitInfo.getX() + (int) (490 * screenRatioX), unitInfo.getY() + (int) (370 * screenRatioY), textInfoPaint);
        canvas.drawText("Totale riciclati: " + recUnit.getRecycledUnit(), unitInfo.getX() + (int) (490 * screenRatioX), unitInfo.getY() + (int) (410 * screenRatioY), textInfoPaint);
    }

    private void drawMaterialRect(InfoImages infoImages, Canvas canvas) {
        //disegna a schermo il primo materiale di ciascuna unità di riciclo
        canvas.drawRect(unitInfo.getX() + (int) (209.31 * screenRatioX), unitInfo.getY() + (int) (529 * screenRatioY), unitInfo.getX() + (int) (407 * screenRatioX), unitInfo.getY() + (int) (691 * screenRatioY), strokePaint);
        canvas.drawBitmap(infoImages.getMaterial_lvl1(), unitInfo.getX() + (int) (218 * screenRatioX), unitInfo.getY() + (int) (536 * screenRatioY), paint);
    }

    private void drawNonUpgradedRecUnitInfo(RecUnit recUnit, InfoImages infoImages, Canvas canvas) {
        //se l'unità non è aggiornata
        if (!recUnit.getIsUpgraded()) {
            //disegna a schermo le informazioni per l'unità non aggiornata
            canvas.drawBitmap(infoImages.getImageBitmap(), infoImages.getX(), infoImages.getY(), paint);
            canvas.drawText("Livello: 1", unitInfo.getX() + (int) (490 * screenRatioX), unitInfo.getY() + (int) (330 * screenRatioY), textInfoPaint);
            canvas.drawBitmap(upgrade.getImageBitmap(), upgrade.getX(), upgrade.getY(), paint);
            canvas.drawBitmap(unitPoints.getImageBitmap(), unitPoints.getX(), unitPoints.getY(), paint);
            canvas.drawText(String.valueOf(recUnit.getUpgradePrice()), unitPoints.getX() - (int) (49.47 * screenRatioX), unitPoints.getY() + unitPoints.getHeight() * 7 / 8, paint);
        }
    }

    private void drawUpgradedRecUnitInfo(RecUnit recUnit, InfoImages infoImages, Canvas canvas) {
        //se l'unità è aggiornata
        if (recUnit.getIsUpgraded()) {
            //disegna a schermo le informazioni per l'unità aggiornata
            canvas.drawBitmap(infoImages.getUpgradedImageBitmap(), infoImages.getX(), infoImages.getY(), paint);
            canvas.drawText("Livello: 2", unitInfo.getX() + (int) (490 * screenRatioX), unitInfo.getY() + (int) (330 * screenRatioY), textInfoPaint);
            canvas.drawRect(unitInfo.getX() + (int) (445 * screenRatioX), unitInfo.getY() + (int) (529 * screenRatioY), unitInfo.getX() + (int) (643 * screenRatioX), unitInfo.getY() + (int) (691 * screenRatioY), strokePaint);
            canvas.drawRect(unitInfo.getX() + (int) (681 * screenRatioX), unitInfo.getY() + (int) (529 * screenRatioY), unitInfo.getX() + (int) (879 * screenRatioX), unitInfo.getY() + (int) (691 * screenRatioY), strokePaint);
            canvas.drawBitmap(infoImages.getMaterial_lvl2(), unitInfo.getX() + (int) (454 * screenRatioX), unitInfo.getY() + (int) (536 * screenRatioY), paint);
            canvas.drawBitmap(infoImages.getMaterial_lvl3(), unitInfo.getX() + (int) (690 * screenRatioX), unitInfo.getY() + (int) (536 * screenRatioY), paint);
        }
    }

    private void drawUnitSunnyPointsInfo(RecUnit recUnit, InfoImages infoImages, Canvas canvas) {
        //per ciascun elemento della lista unitPointsInfoList
        for (int i = 0; i < unitPointsInfoList.size(); i++) {
            drawUnitSunnyPoints(recUnit, infoImages, i, canvas); //disegna l'icona degli unitPoints e dei sunnyPoints, assieme al numero di unitPoints necessari per ottenere n sunnyPoints
        }
    }

    private void drawUnitSunnyPoints(RecUnit recUnit, InfoImages infoImages, int unitPointsIndex, Canvas canvas) {
        //se si tratta del primo elemento (oppure se l'unità è aggiornata)
        if (unitPointsIndex == 0 || unitPointsIndex > 0 && recUnit.getIsUpgraded()) {
            RecImages unitPoints = unitPointsInfoList.get(unitPointsIndex);
            RecImages sunnyPoints = sunnyPointsInfoList.get(unitPointsIndex);
            drawUnitSunnyPoints(unitPoints, sunnyPoints, canvas); //disegna a schermo gli unitPoints e i sunnyPoints nella posizione predefinita
            drawRedUnitSunnyPointsText(recUnit, infoImages, unitPoints, sunnyPoints, unitPointsIndex, canvas); //disegna il numero di unitPoints e di sunnyPoints di colore rosso se si hanno unitPoints sufficienti
            drawBlackUnitSunnyPointsText(recUnit, infoImages, unitPoints, sunnyPoints, unitPointsIndex, canvas); //oppure disegnali di colore nero
        }
    }

    private void drawUnitSunnyPoints(RecImages unitPoints, RecImages sunnyPoints, Canvas canvas) {
        //disegna a schermo gli unitPoints e i sunnyPoints nella posizione designata
        canvas.drawBitmap(unitPoints.getImageBitmap(), unitPoints.getX(), unitPoints.getY(), paint);
        canvas.drawBitmap(sunnyPoints.getImageBitmap(), sunnyPoints.getX(), sunnyPoints.getY(), paint);

    }

    private void drawRedUnitSunnyPointsText(RecUnit recUnit, InfoImages infoImages, RecImages unitPoints, RecImages sunnyPoints, int unitPointsIndex, Canvas canvas) {
        //se gli unitPoints dell'unità di riciclo sono inferiori degli unitPoints richiesti per costruire il materiale
        if (recUnit.getUnitPoints() < infoImages.getUnitPoints(unitPointsIndex)) {
            //disegna il numero degli unitPoints richiesti e dei sunnyPoints ottenibili di colore rosso
            canvas.drawText(String.valueOf(infoImages.getUnitPoints(unitPointsIndex)), sunnyPoints.getX() + sunnyPoints.getWidth() + (int) (7.61 * screenRatioX), unitPoints.getY() + unitPoints.getHeight() * 7 / 8, redPaint);
            canvas.drawText(String.valueOf(infoImages.getSunnyPoints(unitPointsIndex)), sunnyPoints.getX() + sunnyPoints.getWidth() + (int) (7.61 * screenRatioX), sunnyPoints.getY() + sunnyPoints.getHeight() * 7 / 8, redPaint);
        }
    }

    private void drawBlackUnitSunnyPointsText(RecUnit recUnit, InfoImages infoImages, RecImages unitPoints, RecImages sunnyPoints, int unitPointsIndex, Canvas canvas) {
        //se gli unitPoints dell'unità di riciclo sono sufficienti per costruire il materiale
        if (recUnit.getUnitPoints() >= infoImages.getUnitPoints(unitPointsIndex)) {
            //disegnali di colore nero
            canvas.drawText(String.valueOf(infoImages.getUnitPoints(unitPointsIndex)), sunnyPoints.getX() + sunnyPoints.getWidth() + (int) (7.61 * screenRatioX), unitPoints.getY() + unitPoints.getHeight() * 7 / 8, paint);
            canvas.drawText(String.valueOf(infoImages.getSunnyPoints(unitPointsIndex)), sunnyPoints.getX() + sunnyPoints.getWidth() + (int) (7.61 * screenRatioX), sunnyPoints.getY() + sunnyPoints.getHeight() * 7 / 8, paint);
        }
    }

    private void drawIncineratorInfo(RecUnit recUnit, InfoImages infoImages, int recUnitIndex, Canvas canvas) {
        //se si vogliono consultare le informazioni dell'inceneritore
        if (recUnit.getIsCheckingInfo() && recUnitIndex == recUnitList.size() - 1) {
            stopEffects(); //ferma gli effetti
            pauseFlag = true; //il gioco verrà messo in pausa
            drawIncineratorInfo(recUnit, infoImages, canvas); //disegna le info riguardanti l'inceneritore
        }
    }

    private void drawIncineratorInfo(RecUnit recUnit, InfoImages infoImages, Canvas canvas) {
        //disegna a schermo tutte le informazioni riguardanti l'inceneritore
        canvas.drawBitmap(unitInfo.getImageBitmap(), unitInfo.getX(), unitInfo.getY(), paint);
        canvas.drawBitmap(infoImages.getImageBitmap(), infoImages.getX(), infoImages.getY(), paint);
        canvas.drawText("Tipo unità: " + recUnit.getUnitType(), unitInfo.getX() + (int) (490 * screenRatioX), unitInfo.getY() + (int) (320 * screenRatioY), textInfoPaint);
        canvas.drawText("Totale riciclati: " + recUnit.getRecycledUnit(), unitInfo.getX() + (int) (490 * screenRatioX), unitInfo.getY() + (int) (360 * screenRatioY), textInfoPaint);
        canvas.drawText("Costo di utilizzo: 2", unitInfo.getX() + (int) (490 * screenRatioX), unitInfo.getY() + (int) (400 * screenRatioY), textInfoPaint);
        canvas.drawText("4", unitInfo.getX() + (int) (280 * screenRatioX), unitInfo.getY() + (int) (590 * screenRatioY), paint);
        canvas.drawBitmap(sunnyPoints.getImageBitmap(), unitInfo.getX() + (int) (200 * screenRatioX), unitInfo.getY() + (int) (530 * screenRatioY), paint);
        canvas.drawBitmap(infoImages.getImageBitmap2(), unitInfo.getX() + (int) (360 * screenRatioX), unitInfo.getY() + (int) (490 * screenRatioY), paint);

    }

    private void drawLvl1MaterialInfo(InfoImages infoImages, Canvas canvas) {
        if (infoImages.getIsCheckingMaterialLvl1Info()) { //se si è prodotto il primo materiale
            //disegna a schermo il background e il testo inerente al primo materiale
            canvas.drawBitmap(materialInfo.getImageBitmap(), materialInfo.getX(), materialInfo.getY(), paint);
            infoImages.drawMaterialLvl1Text(materialInfo.getX() + (int) (220 * screenRatioX), materialInfo.getY() + (int) (420 * screenRatioY), otherTextInfoPaint, canvas);
            pauseFlag = true; //il gioco verrà messo in pausa
        }
    }

    private void drawLvl2MaterialInfo(InfoImages infoImages, Canvas canvas) {
        if (infoImages.getIsCheckingMaterialLvl2Info()) { //se si è prodotto il secondo materiale
            //disegna a schermo il background e il testo inerente al secondo materiale
            canvas.drawBitmap(materialInfo.getImageBitmap(), materialInfo.getX(), materialInfo.getY(), paint);
            infoImages.drawMaterialLvl2Text(materialInfo.getX() + (int) (220 * screenRatioX), materialInfo.getY() + (int) (420 * screenRatioY), otherTextInfoPaint, canvas);
            pauseFlag = true; //il gioco verrà messo in pausa

        }
    }

    private void drawLvl3MaterialInfo(InfoImages infoImages, Canvas canvas) {
        if (infoImages.getIsCheckingMaterialLvl3Info()) { //se si è prodotto il terzo materiale
            //disegna a schermo il background e il testo inerente al terzo materiale
            canvas.drawBitmap(materialInfo.getImageBitmap(), materialInfo.getX(), materialInfo.getY(), paint);
            infoImages.drawMaterialLvl3Text(materialInfo.getX() + (int) (220 * screenRatioX), materialInfo.getY() + (int) (420 * screenRatioY), otherTextInfoPaint, canvas);
            pauseFlag = true; //il gioco verrà messo in pausa
        }
    }

    private void drawGameBar(Canvas canvas) {
        //disegna a schermo la barra di sopra (con l'icona dei sunnyPoints, delle missioni, ecc.)
        canvas.drawBitmap(background.greenRect, background.getX(), background.getY(), paint);
        canvas.drawBitmap(Bitmap.createScaledBitmap(sunnyPoints.getImageBitmap(), (int) (sunnyPoints.getWidth() * 1.5), (int) (sunnyPoints.getHeight() * 1.5), true), sunnyPoints.getX(), sunnyPoints.getY(), paint);
        canvas.drawText(String.valueOf(sunnyPoints.getSunnyPoints()), sunnyPoints.getX() + sunnyPoints.getWidth() * 2, sunnyPoints.getY() + sunnyPoints.getHeight() * 6 / 5, paint);
        canvas.drawText("Punti: " + (gameBar.getScore()), sunnyPoints.getX() + (int) (sunnyPoints.getWidth() * 10), sunnyPoints.getY() + sunnyPoints.getHeight() * 6 / 5, otherTextInfoPaint);
        canvas.drawBitmap(missioni.getImageBitmap(), missioni.getX() * 33 / 2, missioni.getY() - (float) (10 * screenRatioY), paint);
        if (!MultigiocatoreActivity.unoVSunoClassico && !MultigiocatoreActivity.unoVSunoPowerUp) {
            canvas.drawText("Difficoltà:", sunnyPoints.getX() + (int) (sunnyPoints.getWidth() * 3.5), (float) (sunnyPoints.getY() + sunnyPoints.getHeight() * 0.8), textInfoPaint);
            canvas.drawText(DifficoltaActivity.difficoltà, sunnyPoints.getX() + (int) (sunnyPoints.getWidth() * 3.5), (float) (sunnyPoints.getY() + sunnyPoints.getHeight() * 1.4), textInfoPaint);
        }else if(MultigiocatoreActivity.unoVSunoClassico) {
            canvas.drawText("Avversario:", sunnyPoints.getX() + (int) (sunnyPoints.getWidth() * 3.5), (float) (sunnyPoints.getY() + sunnyPoints.getHeight() * 0.8), textInfoPaint);
            canvas.drawText(avversario, sunnyPoints.getX() + (int) (sunnyPoints.getWidth() * 3.5), (float) (sunnyPoints.getY() + sunnyPoints.getHeight() * 1.4), textInfoPaint);
        }else if(MultigiocatoreActivity.unoVSunoPowerUp) {
                canvas.drawText("Avversario:", sunnyPoints.getX() + (int) (sunnyPoints.getWidth() * 3.5), (float) (sunnyPoints.getY() + sunnyPoints.getHeight() * 0.8), textInfoPaint);
                canvas.drawText(avversario, sunnyPoints.getX() + (int) (sunnyPoints.getWidth() * 3.5), (float) (sunnyPoints.getY() + sunnyPoints.getHeight() * 1.4), textInfoPaint);
            }
        }

    private void drawMissions(ArrayList<Missioni> missionsArrayList, Canvas canvas) {
        //se l'icona delle missioni è stata cliccata
        if (missioni.isClicked()) {
            pauseFlag = true; //il gioco verrà messo in pausa
            drawMissionsBackground(canvas); //disegna il background delle missioni
            drawMissionsInfo(missionsArrayList, canvas); //disegna il testo delle missioni
        }
    }

    public void drawMissionsBackground(Canvas canvas) {
        //disegna il background delle missioni
        canvas.drawBitmap(gameBar.getMissioniRect(), 0, gameBar.getHeight() * 3, paint);
        canvas.drawText("MISSIONI", missioni.getWidth() * 8 / 3, missioni.getHeight() * 7 / 2, paint);
    }

    public void drawMissionsInfo(ArrayList<Missioni> missionsArrayList, Canvas canvas) {
        //per ogni missione, disegna il testo e gli obiettivi inerenti alla missione
        for (int m = 0; m < missionsArrayList.size(); m++) {
            Missioni mission = missionsArrayList.get(m);
            drawMissionInfo(mission, canvas);
        }
    }

    public void drawMissionInfo(Missioni mission, Canvas canvas) {
        if (mission.getMissionType() == 0) {
            canvas.drawText(mission.getDescrizione(), missioni.getWidth() * 2, missioni.getHeight() * 9 / 2, otherTextInfoPaint);
            if (mission.getTotJunkRec() < mission.getGoalJunkRec()) {
                canvas.drawText("Obiettivo: " + mission.getTotJunkRec() + "/" + mission.getGoalJunkRec(), missioni.getWidth() * 2, missioni.getHeight() * 10 / 2, otherTextInfoPaint);
            } else {
                canvas.drawText("Completata!", missioni.getWidth() * 2, missioni.getHeight() * 10 / 2, otherTextInfoPaint);
            }
        }

        if (mission.getMissionType() == 1) {
            canvas.drawText(mission.getDescrizione(), missioni.getWidth() * 2, missioni.getHeight() * 12 / 2, otherTextInfoPaint);
            if (mission.getTotRecUpgr() < mission.getGoalRecUpgr()) {
                canvas.drawText("Obiettivo: " + mission.getTotRecUpgr() + "/" + mission.getGoalRecUpgr(), missioni.getWidth() * 2, missioni.getHeight() * 13 / 2, otherTextInfoPaint);
            } else {
                canvas.drawText("Completata!", missioni.getWidth() * 2, missioni.getHeight() * 13 / 2, otherTextInfoPaint);
            }
        }

        if (mission.getMissionType() == 2) {
            canvas.drawText(mission.getDescrizione(), missioni.getWidth() * 2, missioni.getHeight() * 15 / 2, otherTextInfoPaint);

            if (mission.getTotSunnyAccum() < mission.getGoalSunnyAccum()) {
                canvas.drawText("Obiettivo: " + mission.getTotSunnyAccum() + "/" + mission.getGoalSunnyAccum(), missioni.getWidth() * 2, missioni.getHeight() * 16 / 2, otherTextInfoPaint);
            } else {
                canvas.drawText("Completata!", missioni.getWidth() * 2, missioni.getHeight() * 16 / 2, otherTextInfoPaint);
            }
        }

        if (mission.getMissionType() == 3) {
            canvas.drawText(mission.getDescrizione(), missioni.getWidth() * 2, missioni.getHeight() * 18 / 2, otherTextInfoPaint);

            if (mission.getTotUnitPointsUsed() < mission.getGoalUnitPointsUsed()) {
                canvas.drawText("Obiettivo: " + mission.getTotUnitPointsUsed() + "/" + mission.getGoalUnitPointsUsed(), missioni.getWidth() * 2, missioni.getHeight() * 19 / 2, otherTextInfoPaint);
            } else {
                canvas.drawText("Completata!", missioni.getWidth() * 2, missioni.getHeight() * 19 / 2, otherTextInfoPaint);
            }
        }
    }

    public void drawPause(Canvas canvas) {
        drawPauseMenu(canvas); //disegna il menù di pausa
        drawPauseIcon(canvas); //disegna l'icona di pausa di base
    }

    public void drawPauseMenu(Canvas canvas) {
        //se il bottone della pausa è stato cliccato
        if (pause.isClicked()) {
            isPlaying = false; //non verrà più rieseguito il corpo del metodo run() (simile a pause())
            stopEffects(); //ferma gli effetti
            usePreferences(); //imposta l'audio e gli effetti (assieme alle icone relative) in base a ciò che è stato fatto precedentemente (disattivazione della musica e/o audio nel menù principale)

            //visualizza i testi e le icone della pausa
            canvas.drawBitmap(pause.getImageBitmap2(), pause.getX() * 31, pause.getY(), paint);
            canvas.drawBitmap(gameBar.getPausaRect(), gameBar.getWidth() * 2 / 9, gameBar.getHeight() * 1 / 4, paint);
            canvas.drawText("PAUSA", missioni.getWidth() * 3, (int) (missioni.getHeight() * 5.8), paint);

            if (gameBar.isMusicClicked() && GiocatoreSingoloActivity.b == true) {
                canvas.drawBitmap(gameBar.getMusicIcon(), gameBar.getWidth() * 8, gameBar.getHeight() * 17, paint);

            } else {
                canvas.drawBitmap(gameBar.getMusicIconRed(), gameBar.getWidth() * 8, gameBar.getHeight() * 17, paint);
                gameBar.setMusicClicked(false);
            }
            canvas.drawText("MUSICA", missioni.getWidth() * 9 / 2, missioni.getHeight() * 14 / 2, otherTextInfoPaint);

            if (gameBar.isAudioClicked() && GiocatoreSingoloActivity.b1 == true) {
                canvas.drawBitmap(gameBar.getAudioIcon(), gameBar.getWidth() * 8, gameBar.getHeight() * 21, paint);

            } else {
                canvas.drawBitmap(gameBar.getAudioIconRed(), gameBar.getWidth() * 8, gameBar.getHeight() * 21, paint);
                gameBar.setAudioClicked(false);
            }

            canvas.drawText("EFFETTI", missioni.getWidth() * 9 / 2, missioni.getHeight() * 17 / 2, otherTextInfoPaint);
            if(!MultigiocatoreActivity.unoVSunoClassico && !MultigiocatoreActivity.unoVSunoPowerUp) {
                canvas.drawBitmap(gameOver.getImageBitmap2(), gameBar.getWidth() * 8, gameBar.getHeight() * 25, paint);
                canvas.drawText("RICOMINCIA", missioni.getWidth() * 9 / 2, (int) (missioni.getHeight() * 9.83), otherTextInfoPaint);
                canvas.drawBitmap(gameBar.getSaveIcon(), gameBar.getWidth() * 8, gameBar.getHeight() * 29, paint);
                canvas.drawText("SALVA", missioni.getWidth() * 9 / 2, (int) (missioni.getHeight() * 11.35), otherTextInfoPaint);
            }
            canvas.drawBitmap(gameOver.getImageBitmap3(), gameBar.getWidth() * 8, gameBar.getHeight() * 33, paint);
            canvas.drawText("ESCI", missioni.getWidth() * 9 / 2, (int) (missioni.getHeight() * 12.73), otherTextInfoPaint);
        }
    }

    protected void usePreferences() {
        //questo metodo verrà sovrascritto nelle sottoclassi
    }

    public void drawPauseIcon(Canvas canvas) {
        //se il bottone della pausa non è stato cliccato
        if (!pause.isClicked()) {
            //disegna l'icona di base della pausa
            canvas.drawBitmap(pause.getImageBitmap(), pause.getX() * 31, pause.getY(), paint);
        }
    }


    //metodi da incapsulare in onTouchEvent()

    private void actionDownEvent(MotionEvent event) {
        //se l'utente tocca lo schermo
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            //definisci variabili rappresentanti le coordinate dell'evento
            int touchX = (int) event.getX();
            int touchY = (int) event.getY();
            nJunk = -1; //variabile definita per controllare il rifiuto da manipolare

            pauseIsTouched(touchX, touchY); //effettua un controllo sull'icona della pausa
            missionIsTouched(touchX, touchY); //effettua un controllo sull'icona delle missioni
            pauseHasBeenClicked(touchX, touchY); //effettua un controllo sul menù di pausa
            gameOverPopupIsShowing(touchX, touchY); //effettua un controllo sul pop-up di fine partita
            junkIsTouched(touchX, touchY); //effettua un controllo sui rifiuti
            recUnitControl(touchX, touchY); //effettua un controllo sulle unità di riciclo, assieme ai relativi pop-up
        }
    }

    private void pauseIsTouched(int touchX, int touchY) {
        boolean isTouchingPause = touchX >= pause.getX() * 32 && touchY >= pause.getY() && touchX < pause.getX() * 32 + pause.getWidth() && touchY < pause.getY() + pause.getHeight();

        //se l'utente sta toccando l'icona di pausa
        if (isTouchingPause && !(missioni.isClicked())) {

            if (!pause.isClicked() && isPlaying) { //se la pausa non è stata cliccata
                pause.setClicked(true); //la pausa ora è considerata cliccata

            } else if (pause.isClicked() && !isPlaying) { //se la pausa è stata cliccata
                pause.setClicked(false); //la pausa ora è considerata non cliccata
                resume(); //riprendi la partita
            }
        }
    }

    private void missionIsTouched(int touchX, int touchY) {
        boolean isTouchingMission = touchX >= missioni.getX() * 34 / 2 && touchY >= missioni.getY() - 10 && touchX < missioni.getX() * 34 / 2 + missioni.getWidth() && touchY < missioni.getY() - 10 + missioni.getHeight();

        if (isTouchingMission && !(pause.isClicked())) { //se l'utente sta toccando l'icona delle missioni

            if (!missioni.isClicked() && isPlaying) { //se l'icona non è stata cliccata
                missioni.setClicked(true); //l'icona è ora considerata cliccata

            } else if (missioni.isClicked() && !isPlaying) {//se l'icona è stata cliccata
                missioni.setClicked(false); //l'icona non è più considerata cliccata
                resume(); //riprendi la partita
            }
        }
    }

    private void pauseHasBeenClicked(int touchX, int touchY) {
        //se la pausa è stata cliccata
        if (pause.isClicked()) {
            musicIsTouched(touchX, touchY); //disattiva o attiva la musica nel caso in cui l'icona venga toccata
            audioIsTouched(touchX, touchY); //disattiva o attiva gli effetti nel caso in cui l'icona venga toccata
            restartIsTouched(touchX, touchY); //ricomincia la partita se viene toccata l'icona relativa
            saveIsTouched(touchX, touchY); //salva la partita se viene toccata l'icona relativa
            pauseExitIsTouched(touchX, touchY); //esci dal gioco se viene toccata l'icona relativa
        }
    }

    private void musicIsTouched(int touchX, int touchY) {
        boolean isTouchingMusic = touchX >= gameBar.getWidth() * 8 && touchY >= gameBar.getHeight() * 17 && touchX < gameBar.getWidth() * 8 + gameBar.getWidth() * 3 && touchY < gameBar.getHeight() * 17 + gameBar.getHeight() * 3;

        //se l'utente sta toccando l'icona della musica
        if (isTouchingMusic) {

            if (gameBar.isMusicClicked() && GiocatoreSingoloActivity.b == false) {
                gameBar.setMusicClicked(false); //l'icona non è considerata cliccata
            } else {
                gameBar.setMusicClicked(true); //l'icona è ora considerata cliccata
            }

        }
    }

    private void audioIsTouched(int touchX, int touchY) {
        boolean isTouchingAudio = touchX >= gameBar.getWidth() * 8 && touchY >= gameBar.getHeight() * 21 && touchX < gameBar.getWidth() * 8 + gameBar.getWidth() * 3 && touchY < gameBar.getHeight() * 21 + gameBar.getHeight() * 3;

        if (isTouchingAudio) { //se l'utente sta toccando l'icona degli effetti
            if (gameBar.isAudioClicked() && GiocatoreSingoloActivity.b1 == false) {
                gameBar.setAudioClicked(false); //l'icona non è considerata cliccata
            } else {
                gameBar.setAudioClicked(true);//l'icona è ora considerata cliccata
            }
        }
    }

    private void restartIsTouched(int touchX, int touchY) {
        boolean isTouchingRestart = touchX >= gameBar.getWidth() * 8 && touchY >= gameBar.getHeight() * 25 && touchX < gameBar.getWidth() * 8 + gameOver.getWidth() && touchY < gameBar.getHeight() * 25 + gameOver.getHeight();
        if(isTouchingRestart) {
            restart(); //ricomincia la partita
        }
    }

    private void saveIsTouched(int touchX, int touchY) {
        boolean isTouchingSave = touchX >= gameBar.getWidth() * 8 && touchY >= gameBar.getHeight() * 29 && touchX < gameBar.getWidth() * 8 + gameOver.getWidth() && touchY < gameBar.getHeight() * 29 + gameOver.getHeight();

        if (isTouchingSave) { //se è stato toccato il tasto per salvare
            if (LoginActivity.currentUser != null)
            save(); //salva la partita
        }
    }

    private void pauseExitIsTouched(int touchX, int touchY) {
        boolean isTouchingPauseExit = touchX >= gameBar.getWidth() * 8 && touchY >= gameBar.getHeight() * 33 && touchX < gameBar.getWidth() * 8 + gameOver.getWidth() && touchY < gameBar.getHeight() * 33 + gameOver.getHeight();
        if (isTouchingPauseExit) { //se è stato toccato il tasto per uscire
            if(MultigiocatoreActivity.unoVSunoClassico) {
                ref = database.getReference("rooms_uno_contro_uno_classico").child(ConnessioneActivity.roomName);
            }else if(MultigiocatoreActivity.unoVSunoPowerUp) {
                ref = database.getReference("rooms_uno_contro_uno_powerUp").child(ConnessioneActivity.roomName);
            }
            if(ConnessioneActivity.player1) {
                ref.child("playerExit").setValue(1);
            }else{
                ref.child("playerExit").setValue(2);
            }
            exit(); //esci dal gioco
            exit();
        }
    }

    private void gameOverPopupIsShowing(int touchX, int touchY) {
        //se è apparso il pop-up di fine partita
        if (isGameOver) {
            //se l'utente ha effettuato il login
            if (LoginActivity.currentUser != null)
                saveScores(); //salva il punteggio, assieme alla modalità e alla difficoltà scelta

            if(MultigiocatoreActivity.unoVSunoClassico || MultigiocatoreActivity.unoVSunoPowerUp){
                showResultIsTouched(touchX, touchY); //se viene toccata l'icona di "Vedi risultati", vai ai risultati
                setScore(); //setta lo score del giocatore
            }else{
                redoIsTouched(touchX, touchY); //se viene toccata l'icona "Ricomincia", ricomincia la partita
                gameOverExitIsTouched(touchX, touchY); //se viene toccata l'icona di "Esci", esci dal gioco
            }
        }
    }

    private void redoIsTouched(int touchX, int touchY) {
        boolean isTouchingRedo = (touchX >= gameOver.getX() + (int) (170 * screenRatioX) && touchY >= gameOver.getY() + (int) (350 * screenRatioY) && touchX < gameOver.getX() + (int) (170 * screenRatioX) + gameOver.getWidth() && touchY < gameOver.getY() + (int) (350 * screenRatioY) + gameOver.getHeight());
        //se è stato toccato il tasto di ricomincia
        if (isTouchingRedo) {
            restart(); //ricomincia la partita
        }
    }

    private void gameOverExitIsTouched(int touchX, int touchY) {
        boolean isTouchingGameOverExit = (touchX >= gameOver.getX() + (int) (170 * screenRatioX) && touchY >= gameOver.getY() + (int) (500 * screenRatioY) && touchX < gameOver.getX() + (int) (170 * screenRatioX) + gameOver.getWidth() && touchY < gameOver.getY() + (int) (500 * screenRatioY) + gameOver.getHeight());

        if (isTouchingGameOverExit) { //se è stato toccato il tasto per uscire
            exit(); //esci dal gioco
            exit();
        }
    }

    private void showResultIsTouched(int touchX, int touchY){
        boolean isTouchingShowResult = (touchX >= gameOver.getX() + (int) (170 * screenRatioX) && touchY >= gameOver.getY() + (int) (420 * screenRatioY) && touchX < gameOver.getX() + (int) (170 * screenRatioX) + gameOver.getWidth() && touchY < gameOver.getY() + (int) (420 * screenRatioY) + gameOver.getHeight());

        if(isTouchingShowResult){ //se è stata toccata l'icona per mostrare i risultati
            showResult();
        }
    }

    protected void junkIsTouched(int touchX, int touchY) {

        //per ciascun rifiuto presente sullo schermo
        for (int i = 0; i < junkList.size(); i++) {
            Junk junk = junkList.get(i);
            boolean isTouching = (touchX >= junk.getX() && touchY >= junk.getY() && touchX < junk.getX() + junk.getWidth() && touchY < junk.getY() + junk.getHeight());

            //se si sta toccando il rifiuto i-esimo
            if (isTouching && !(junk instanceof PowerUp)) {
                nJunk = i; //imposta la variabile nJunk
            } else if (isTouching && junk instanceof PowerUp && isPlaying) {
                activatePowerUp(junk, i);
            }
        }
    }

    protected void activatePowerUp(Junk junk, int junkIndex) {
        //questo metodo sarà sovrascritto da PowerUpGameView
    }

    private void recUnitControl(int touchX, int touchY) {
        //per ciascuna unità di riciclo
        for (int i = 0; i <= recUnitList.size() - 1; i++) {
            RecUnit recUnit = recUnitList.get(i);
            InfoImages infoImages = infoImagesList.get(i);

            recUnitIsTouched(recUnit, infoImages, touchX, touchY); //effettua controlli nel caso in cui l'unità di riciclo venga toccata
            recUnitInfoIsNotTouched(recUnit, touchX, touchY); //effettua controlli nel caso in cui l'utente clicca al di sopra del background delle informazioni
            recUnitIsGettingUnlocked(recUnit, touchX, touchY); //effettua controlli nel caso in cui venga mostrato il pop-up per sbloccare le unità di riciclo
            materialHasBeenTouched(infoImages, i, touchX, touchY); //effettua controlli nel caso in cui vengano mostrate le informazioni dei materiali costruiti
            recUnitHasBeenTouched(recUnit, infoImages, i, touchX, touchY); //effettua controlli nel caso in cui venga mostrato il pop-up delle informazioni delle unità di riciclo
            incineratorHasBeenTouched(recUnit, infoImages, i, touchX, touchY); //effettua controlli nel caso in cui venga mostrato il pop-up delle informazioni dell'inceneritore
        }
    }

    private void recUnitIsTouched(RecUnit recUnit, InfoImages infoImages, int touchX, int touchY) {
        //variabili booleane utilizzate per verificare che l'utente abbia toccato l'unità di riciclo e la barra di gioco di sopra
        boolean isTouchingRecUnit = (touchX >= recUnit.getX() && touchY >= recUnit.getY() && touchX < recUnit.getX() + recUnit.getWidth() && touchY < recUnit.getY() + recUnit.getHeight());
        boolean isTouchingGameBar = touchY < screenY * 0.06;

        //se l'utente ha toccato l'unità di riciclo
        if (isTouchingRecUnit && !isTouchingGameBar && !isGameOver && isPlaying) {

            //se l'unità di riciclo è sbloccata
            if (recUnit.getIsUnlocked()) {
                recUnit.setIsCheckingInfo(true); //imposta la variabile per visualizzare le informazioni dell'unità di riciclo

            } else if (!recUnit.getIsUnlocked() && sunnyPoints.getSunnyPoints() >= recUnit.getUnitPrice() && !missioni.isClicked() && !pause.isClicked()) { //se l'unità di riciclo non è stata sbloccata e si hanno sufficienti sunnyPoints per sbloccarla
                recUnit.setIsUnlocking(true); //imposta la variabile per visualizzare il pop-up per sbloccare l'unità
            }

        }
    }

    private void recUnitInfoIsNotTouched(RecUnit recUnit, int touchX, int touchY) {
        boolean isTouchingRecUnit = (touchX >= recUnit.getX() && touchY >= recUnit.getY() && touchX < recUnit.getX() + recUnit.getWidth() && touchY < recUnit.getY() + recUnit.getHeight());

        if (recUnit.getIsCheckingInfo() && touchY < unitInfo.getY() && !isTouchingRecUnit) { //se l'utente sta visualizzando le informazioni dell'unità di riciclo e tocca una parte dello schermo che non sia il background delle informazioni
            recUnit.setIsCheckingInfo(false); //imposta la variabile in maniera tale che le informazioni dell'unità di riciclo non siano più visualizzabili
            resume(); //riprendi la partita
        }
    }

    private void recUnitIsGettingUnlocked(RecUnit recUnit, int touchX, int touchY) {
        boolean isTouchingGameBar = touchY < screenY * 0.06;

        //se l'utente sta visualizzando il pop-up per sbloccare un'unità di riciclo
        if (recUnit.getIsUnlocking() && !isTouchingGameBar && !isGameOver) {
            yesButtonIsTouched(recUnit, touchX, touchY); //sblocca l'unità se il tasto "YES" viene cliccato
            noButtonIsTouched(recUnit, touchX, touchY); //non mostrare più il popup se viene cliccato il pulsante "NO"
        }
    }

    private void yesButtonIsTouched(RecUnit recUnit, int touchX, int touchY) {
        boolean isTouchingYesButton = (touchX >= confirmBuilding.getX() + (int) (180 * screenRatioX) && touchY >= confirmBuilding.getY() + (int) (350 * screenRatioY) && touchX < confirmBuilding.getX() + (int) (180 * screenRatioX) + confirmBuilding.getWidth() && touchY < confirmBuilding.getY() + (int) (350 * screenRatioY) + confirmBuilding.getHeight());

        //se è stato toccato il tasto di conferma
        if (isTouchingYesButton) {
            recUnit.setIsUnlockedToTrue(); //l'unità viene sbloccata
            sunnyPoints.setSunnyPoints(sunnyPoints.getSunnyPoints() - recUnit.getUnitPrice()); //i sunnyPoints vengono ridotti in base al costo dell'unità
            recUnit.setIsUnlocking(false); //il pop-up non sarà più visualizzato
            resume(); //riprendi la partita

        }
    }

    private void noButtonIsTouched(RecUnit recUnit, int touchX, int touchY) {
        boolean isTouchingNoButton = (touchX >= confirmBuilding.getX() + (int) (500 * screenRatioX) && touchY >= confirmBuilding.getY() + (int) (350 * screenRatioY) && touchX < confirmBuilding.getX() + (int) (500 * screenRatioX) + confirmBuilding.getWidth() && touchY < confirmBuilding.getY() + (int) (350 * screenRatioY) + confirmBuilding.getHeight());

        if (isTouchingNoButton) { //se è stato toccato il tasto di declino
            recUnit.setIsUnlocking(false); //il pop-up non sarà più visualizzato
            resume(); //riprendi la partita
        }
    }

    private void materialHasBeenTouched(InfoImages infoImages, int recUnitIndex, int touchX, int touchY) {
        //se si stanno visualizzando le informazioni riguardanti uno dei materiali
        if ((infoImages.getIsCheckingMaterialLvl1Info() || infoImages.getIsCheckingMaterialLvl2Info() || infoImages.getIsCheckingMaterialLvl3Info()) && recUnitIndex != recUnitList.size() - 1) {

            //definisci la variabile booleane per verificare che venga toccato il background delle informazioni
            boolean isTouchingMaterialInfo = (touchX >= materialInfo.getX() && touchY >= materialInfo.getY() && touchX < materialInfo.getX() + materialInfo.getWidth() && touchY < materialInfo.getY() + materialInfo.getHeight());

            if (!isTouchingMaterialInfo) { //se non è stato toccato il background
                //le informazioni riguardanti i materiali non saranno più visualizzate
                infoImages.setIsCheckingMaterialLvl1Info(false);
                infoImages.setIsCheckingMaterialLvl2Info(false);
                infoImages.setIsCheckingMaterialLvl3Info(false);
                resume(); //riprendi la partita
            }
        }
    }

    private void recUnitHasBeenTouched(RecUnit recUnit, InfoImages infoImages, int recUnitIndex, int touchX, int touchY) {
        //se si stanno visualizzando le informazioni di un'unità di riciclo
        if (recUnit.getIsCheckingInfo() && recUnitIndex != recUnitList.size() - 1) {

            upgradeIsTouched(recUnit, touchX, touchY); //aggiorna l'unità se il pulsante relativo viene toccato
            lvl1MaterialIsTouched(recUnit, infoImages, touchX, touchY); //scambia gli unitPoints per i sunnyPoints e mostra le informazioni del materiale di primo grado
            lvl2MaterialIsTouched(recUnit, infoImages, touchX, touchY); //scambia gli unitPoints per i sunnyPoints e mostra le informazioni del materiale di secondo grado
            lvl3MaterialIsTouched(recUnit, infoImages, touchX, touchY); //scambia gli unitPoints per i sunnyPoints e mostra le informazioni del materiale di terzo grado
        }
    }

    private void upgradeIsTouched(RecUnit recUnit, int touchX, int touchY) {
        boolean isTouchingUpgrade = (touchX >= upgrade.getX() && touchY >= upgrade.getY() && touchX < upgrade.getX() + upgrade.getWidth() && touchY < upgrade.getY() + upgrade.getHeight());

        //se è stato toccato il pulsante di aggiornamento dell'unità di riciclo e il numero di unitPoints è sufficiente
        if (isTouchingUpgrade && recUnit.getUnitPoints() >= recUnit.getUpgradePrice() && !recUnit.getIsUpgraded()) {
            recUnit.setIsUpgraded(true); //l'unità è ora aggiornata
            recUnit.setIsCheckingInfo(false); //le informazioni non saranno più visualizzate
            recUnit.reduceUnitPoints(recUnit.getUpgradePrice()); //riduci il numero di unitPoints in base al costo dell'aggiornamento

            listaMissioni.get(1).setTotRecUpgr(1); //incrementa di uno il numero delle stazioni aggiornata (per la missione)

            resume(); //riprendi la partita
        }
    }

    private void lvl1MaterialIsTouched(RecUnit recUnit, InfoImages infoImages, int touchX, int touchY) {
        boolean isTouchingLvl1Material = (touchX >= unitInfo.getX() + (int) (218 * screenRatioX) && touchY >= unitInfo.getY() + (int) (536 * screenRatioY) && touchX < unitInfo.getX() + (int) (218 * screenRatioX) + infoImages.getWidth() && touchY < unitInfo.getY() + (int) (536 * screenRatioY) + infoImages.getHeight());

        if (isTouchingLvl1Material && recUnit.getUnitPoints() >= infoImages.getUnitPoints(0)) { //se è stata toccata l'icona del primo materiale e si hanno unitPoints sufficienti
            recUnit.reduceUnitPoints(infoImages.getUnitPoints(0)); //riduci gli unitPoints del valore prestabilito
            sunnyPoints.setSunnyPoints(sunnyPoints.getSunnyPoints() + infoImages.getSunnyPoints(0)); //incrementa i sunnyPoints del valore prestabilito
            recUnit.setIsCheckingInfo(false); //le informazioni non saranno più visualizzate
            infoImages.setIsCheckingMaterialLvl1Info(true); //saranno visualizzate le informazioni riguardanti il materiale

            listaMissioni.get(2).setTotSunnyAccum(infoImages.getSunnyPoints(0)); //incrementa il numero dei sunnyPoints accumulati (per la missione)
            listaMissioni.get(3).setTotUnitPointsUsed(infoImages.getUnitPoints(0)); //incrementa il numero degli unitPoints spesi (per la missione)

            resume(); //riprendi la partita

        }
    }

    private void lvl2MaterialIsTouched(RecUnit recUnit, InfoImages infoImages, int touchX, int touchY) {
        boolean isTouchingLvl2Material = (touchX >= unitInfo.getX() + (int) (454 * screenRatioX) && touchY >= unitInfo.getY() + (int) (536 * screenRatioY) && touchX < unitInfo.getX() + (int) (454 * screenRatioX) + infoImages.getWidth() && touchY < unitInfo.getY() + (int) (536 * screenRatioY) + infoImages.getHeight());

        if (isTouchingLvl2Material && recUnit.getUnitPoints() >= infoImages.getUnitPoints(1) && recUnit.getIsUpgraded()) { //se è stata toccata l'icona del secondo materiale e si hanno unitPoints sufficienti (l'unità deve essere aggiornata)
            recUnit.reduceUnitPoints(infoImages.getUnitPoints(1)); //riduci gli unitPoints del valore prestabilito
            sunnyPoints.setSunnyPoints(sunnyPoints.getSunnyPoints() + infoImages.getSunnyPoints(1)); //incrementa i sunnyPoints del valore prestabilito
            recUnit.setIsCheckingInfo(false); //le informazioni non saranno più visualizzate
            infoImages.setIsCheckingMaterialLvl2Info(true); //saranno visualizzate le informazioni riguardanti il materiale

            listaMissioni.get(2).setTotSunnyAccum(infoImages.getSunnyPoints(1)); //incrementa il numero dei sunnyPoints accumulati (per la missione)
            listaMissioni.get(3).setTotUnitPointsUsed(infoImages.getUnitPoints(1)); //incrementa il numero degli unitPoints spesi (per la missione)

            resume(); //riprendi la partita

        }
    }

    private void lvl3MaterialIsTouched(RecUnit recUnit, InfoImages infoImages, int touchX, int touchY) {
        boolean isTouchingLvl3Material = (touchX >= unitInfo.getX() + (int) (690 * screenRatioX) && touchY >= unitInfo.getY() + (int) (536 * screenRatioY) && touchX < unitInfo.getX() + (int) (690 * screenRatioX) + infoImages.getWidth() && touchY < unitInfo.getY() + (int) (536 * screenRatioY) + infoImages.getHeight());

        if (isTouchingLvl3Material && recUnit.getUnitPoints() >= infoImages.getUnitPoints(2) && recUnit.getIsUpgraded()) { //se è stata toccata l'icona del terzo materiale e si hanno unitPoints sufficienti (l'unità deve essere aggiornata)
            recUnit.reduceUnitPoints(infoImages.getUnitPoints(2)); //riduci gli unitPoints del valore prestabilito
            sunnyPoints.setSunnyPoints(sunnyPoints.getSunnyPoints() + infoImages.getSunnyPoints(2)); //incrementa i sunnyPoints del valore prestabilito
            recUnit.setIsCheckingInfo(false); //le informazioni non saranno più visualizzate
            infoImages.setIsCheckingMaterialLvl3Info(true); //saranno visualizzate le informazioni riguardanti il materiale

            listaMissioni.get(2).setTotSunnyAccum(infoImages.getSunnyPoints(2)); //incrementa il numero dei sunnyPoints accumulati (per la missione)
            listaMissioni.get(3).setTotUnitPointsUsed(infoImages.getUnitPoints(2)); //incrementa il numero degli unitPoints spesi (per la missione)

            resume(); //riprendi la partita
        }
    }

    private void incineratorHasBeenTouched(RecUnit recUnit, InfoImages infoImages, int recUnitIndex, int touchX, int touchY) {
        //se si stanno visualizzando le informazioni dell'inceneritore
        if (recUnit.getIsCheckingInfo() && recUnitIndex == recUnitList.size() - 1) {
            //definisci la variabile booleane per verificare che venga toccato il pulsante del potenziamento dell'inceneritore
            boolean isTouchingPowerUp = (touchX >= unitInfo.getX() + (int) (360 * screenRatioX) && touchY >= unitInfo.getY() + (int) (500 * screenRatioY) && touchX < unitInfo.getX() + (int) (360 * screenRatioX) + infoImages.getWidth() && touchY < unitInfo.getY() + (int) (500 * screenRatioY) + infoImages.getHeight());

            //se è stato toccato il bottone dell'abilità dell'inceneritore, si hanno sunnyPoints sufficienti e l'inceneritore non sta già riciclando
            if (isTouchingPowerUp && sunnyPoints.getSunnyPoints() >= 4 && !recUnit.getIsRecycling()) {
                recUnit.setIsPoweredUp(true); //verrà applicata l'abilità dell'inceneritore
                recUnit.setIsCheckingInfo(false); //le informazioni dell'inceneritore non verranno più visualizzate
                recUnit.setIsRecycling(true); //l'inceneritore sta ora riciclando
                sunnyPoints.setSunnyPoints(sunnyPoints.getSunnyPoints() - 4); //verrà scalato il numero di sunnyPoints di 4
                resume(); //riprendi la partita
            }
        }
    }

    private void actionMoveEvent(MotionEvent event) {
        //se l'utente muove il dito mentre sta ancora toccando lo schermo
        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            //definisci le coordinate dell'evento (la posizione del dito)
            int x = (int) event.getX();
            int y = (int) event.getY();

            junkHasBeenTouched(x, y); //sposta l'ombra del rifiuto in base alla posizione del dito
        }
    }

    private void junkHasBeenTouched(int x, int y) {
        //se è stato precedentemente toccato un rifiuto
        if ((nJunk != -1 && isPlaying)) {
            Junk junk = junkList.get(nJunk);
            junk.setBeingDragged(true); //il rifiuto viene trascinato
            junk.setDragX(x - junk.getWidth() / 2); //la posizione dell'ombra lungo l'asse x
            junk.setDragY(y - junk.getHeight() / 2); //e l'asse y
        }
    }

    private void actionUpEvent(MotionEvent event) {
        //se il dito viene sollevato
        if (event.getAction() == MotionEvent.ACTION_UP) {

            //definsci le coordinate dell'evento (punto in cui è stato sollevato il dito)
            int x = (int) event.getX();
            int y = (int) event.getY();

            pauseMotionUp(x, y); //cambia le impostazioni di audio e musica
            junkIsBeingReleased(x, y); //rimuovi il rifiuto se viene trascinato su di un'unità di riciclo compatibile
        }
    }

    public void pauseMotionUp(int x, int y) {
        changeMusic(x, y);
        changeAudio(x, y);
    }

    protected void changeMusic(int x, int y) {

    }

    protected void changeAudio(int x, int y) {

    }

    private void junkIsBeingReleased(int x, int y) {
        //se è stato trascinato un rifiuto
        if ((nJunk != -1 && isPlaying)) {
            Junk junk = junkList.get(nJunk);
            junk.setBeingDragged(false); //il rifiuto non viene più trascinato

            startRecycleProcess(junk, x, y); //inizia il processo di riciclo se un rifiuto viene trascinato su di un'unità di riciclo compatibile
        }
    }

    private void startRecycleProcess(Junk junk, int x, int y) {
        recycleGlass(junk, x, y); //rimuovi il rifiuto di vetro se viene trascinato sull'unità di riciclo del vetro
        recyclePaper(junk, x, y); //stessa funzionalità, ma per la carta
        recycleAluminum(junk, x, y); //per l'alluminio
        recycleSteel(junk, x, y); //per l'acciaio
        recyclePlastic(junk, x, y); //per la plastica
        recycleEWaste(junk, x, y); //per i rifiuti tecnologici
        recycleAll(junk, x, y); //rimuovi il rifiuto se viene trascinato sull'inceneritore (bisogna avere sunnyPoints sufficienti)
    }

    private void recycleGlass(Junk junk, int x, int y) {
        //se il rifiuto è di vetro e viene rilasciato sull'unità di riciclo del vetro
        if (junk instanceof Glass && x >= recUnitList.get(0).getX() && y >= recUnitList.get(0).getY() &&
                x < recUnitList.get(0).getX() + recUnitList.get(0).getWidth() && y <= recUnitList.get(0).getY()
                + recUnitList.get(0).getHeight()) {

            //se i processi di riciclo non sono tutti occupati
            if ((recUnitList.get(0).getJunkBeingRecycled() < 2 && recUnitList.get(0).getIsUpgraded()) ||
                    (recUnitList.get(0).getJunkBeingRecycled() < 1 && !recUnitList.get(0).getIsUpgraded())) {
                junkList.remove(nJunk); //rimuovi il rifiuto
                recUnitList.get(0).setIsRecycling(true); //l'unità di riciclo sta ora riciclando
                recUnitList.get(0).junkBeingRecycledPlus(); //aumenta il numero di rifiuti che sono attualmente riciclati

                listaMissioni.get(0).setTotJunkRec(1); //aumenta di uno il numero dei rifiuti che è stato riciclato (per la missione)
            }

        }
    }

    private void recyclePaper(Junk junk, int x, int y) {
        //se il rifiuto è di carta e viene rilasciato sull'unità di riciclo della carta
        if (junk instanceof Paper && recUnitList.get(1).getIsUnlocked() && x >= recUnitList.get(1).getX()
                && y >= recUnitList.get(1).getY() && x < recUnitList.get(1).getX() + recUnitList.get(1).getWidth()
                && y <= recUnitList.get(1).getY() + recUnitList.get(1).getHeight()) {

            //se i processi di riciclo non sono tutti occupati
            if ((recUnitList.get(1).getJunkBeingRecycled() < 2 && recUnitList.get(1).getIsUpgraded()) ||
                    (recUnitList.get(1).getJunkBeingRecycled() < 1 && !recUnitList.get(1).getIsUpgraded())) {
                junkList.remove(nJunk); //rimuovi il rifiuto
                recUnitList.get(1).setIsRecycling(true); //l'unità di riciclo sta ora riciclando
                recUnitList.get(1).junkBeingRecycledPlus(); //aumenta il numero di rifiuti che sono attualmente riciclati

                listaMissioni.get(0).setTotJunkRec(1); //aumenta di uno il numero dei rifiuti che è stato riciclato (per la missione)
            }

        }
    }

    private void recycleAluminum(Junk junk, int x, int y) {
        //se il rifiuto è di alluminio e viene rilasciato sull'unità di riciclo dell'alluminio
        if (junk instanceof Aluminum && recUnitList.get(2).getIsUnlocked() && x >= recUnitList.get(2).getX()
                && y >= recUnitList.get(2).getY() && x < recUnitList.get(2).getX() + recUnitList.get(2).getWidth()
                && y <= recUnitList.get(2).getY() + recUnitList.get(2).getHeight()) {

            //se i processi di riciclo non sono tutti occupati
            if ((recUnitList.get(2).getJunkBeingRecycled() < 2 && recUnitList.get(2).getIsUpgraded()) ||
                    (recUnitList.get(2).getJunkBeingRecycled() < 1 && !recUnitList.get(2).getIsUpgraded())) {
                junkList.remove(nJunk); //rimuovi il rifiuto
                recUnitList.get(2).setIsRecycling(true); //l'unità di riciclo sta ora riciclando
                recUnitList.get(2).junkBeingRecycledPlus(); //aumenta il numero di rifiuti che sono attualmente riciclati

                listaMissioni.get(0).setTotJunkRec(1); //aumenta di uno il numero dei rifiuti che è stato riciclato (per la missione)
            }

        }
    }

    private void recycleSteel(Junk junk, int x, int y) {
        //se il rifiuto è di acciaio e viene rilasciato sull'unità di riciclo dell'acciaio
        if (junk instanceof Steel && recUnitList.get(3).getIsUnlocked() && x >= recUnitList.get(3).getX()
                && y >= recUnitList.get(3).getY() && x < recUnitList.get(3).getX() + recUnitList.get(3).getWidth()
                && y <= recUnitList.get(3).getY() + recUnitList.get(3).getHeight()) {

            //se i processi di riciclo non sono tutti occupati
            if ((recUnitList.get(3).getJunkBeingRecycled() < 2 && recUnitList.get(3).getIsUpgraded()) ||
                    (recUnitList.get(3).getJunkBeingRecycled() < 1 && !recUnitList.get(3).getIsUpgraded())) {
                junkList.remove(nJunk); //rimuovi il rifiuto
                recUnitList.get(3).setIsRecycling(true); //l'unità di riciclo sta ora riciclando
                recUnitList.get(3).junkBeingRecycledPlus(); //aumenta il numero di rifiuti che sono attualmente riciclati

                listaMissioni.get(0).setTotJunkRec(1); //aumenta di uno il numero dei rifiuti che è stato riciclato (per la missione)
            }

        }
    }

    private void recyclePlastic(Junk junk, int x, int y) {
        //se il rifiuto è di plastica e viene rilasciato sull'unità di riciclo della plastica
        if (junk instanceof Plastic && recUnitList.get(4).getIsUnlocked() && x >= recUnitList.get(4).getX()
                && y >= recUnitList.get(4).getY() && x < recUnitList.get(4).getX() + recUnitList.get(4).getWidth()
                && y <= recUnitList.get(4).getY() + recUnitList.get(4).getHeight()) {

            //se i processi di riciclo non sono tutti occupati
            if ((recUnitList.get(4).getJunkBeingRecycled() < 2 && recUnitList.get(4).getIsUpgraded()) ||
                    (recUnitList.get(4).getJunkBeingRecycled() < 1 && !recUnitList.get(4).getIsUpgraded())) {
                junkList.remove(nJunk); //rimuovi il rifiuto
                recUnitList.get(4).setIsRecycling(true); //l'unità di riciclo sta ora riciclando
                recUnitList.get(4).junkBeingRecycledPlus(); //aumenta il numero di rifiuti che sono attualmente riciclati

                listaMissioni.get(0).setTotJunkRec(1); //aumenta di uno il numero dei rifiuti che è stato riciclato (per la missione)
            }

        }
    }

    private void recycleEWaste(Junk junk, int x, int y) {
        //se il rifiuto appartiene ai rifiuti tecnologici e viene rilasciato sull'unità di riciclo dei rifiuti tecnologici
        if (junk instanceof EWaste && recUnitList.get(5).getIsUnlocked() && x >= recUnitList.get(5).getX()
                && y >= recUnitList.get(5).getY() && x < recUnitList.get(5).getX() + recUnitList.get(5).getWidth()
                && y <= recUnitList.get(5).getY() + recUnitList.get(5).getHeight()) {

            //se i processi di riciclo non sono tutti occupati
            if ((recUnitList.get(5).getJunkBeingRecycled() < 2 && recUnitList.get(5).getIsUpgraded()) ||
                    (recUnitList.get(5).getJunkBeingRecycled() < 1 && !recUnitList.get(5).getIsUpgraded())) {
                junkList.remove(nJunk); //rimuovi il rifiuto
                recUnitList.get(5).setIsRecycling(true); //l'unità di riciclo sta ora riciclando
                recUnitList.get(5).junkBeingRecycledPlus(); //aumenta il numero di rifiuti che sono attualmente riciclati

                listaMissioni.get(0).setTotJunkRec(1); //aumenta di uno il numero dei rifiuti che è stato riciclato (per la missione)
            }

        }
    }

    private void recycleAll(Junk junk, int x, int y) {
        //se il rifiuto viene trascinato sull'inceneritore
        if (x >= recUnitList.get(6).getX() && y >= recUnitList.get(6).getY()
                && x < recUnitList.get(6).getX() + recUnitList.get(6).getWidth() && y < recUnitList.get(6).getY() +
                recUnitList.get(6).getHeight() && !recUnitList.get(6).getIsRecycling()) {

            //se il numero di sunnyPoints è maggiore o uguale a 2
            if (sunnyPoints.getSunnyPoints() >= 2) {
                sunnyPoints.setSunnyPoints(sunnyPoints.getSunnyPoints() - 2); //riduci il numero di sunnyPoints di due unità
                junkList.remove(nJunk); //rimuovi il rifiuto
                recUnitList.get(6).setIsRecycling(true); //l'inceneritore sta ora riciclando
                recUnitList.get(6).junkBeingRecycledPlus(); //aumenta il numero dei rifiuti che sono attualmente riciclati
            }
        }
    }



    //metodi che verranno incapsulati in save()

    private void saveGeneralData() {
        //salva ai dati generici della partita (difficoltà, modalità, dati inerenti alle missioni...)
        ref = mainRef.child("general_data");
        ref.child("difficoltà").setValue(DifficoltaActivity.difficoltà);
        ref.child("modalità").setValue(GiocatoreSingoloActivity.modalità);
        ref.child("is_saved").setValue(true);
        saveMissionData(ref);
        ref.child("score").setValue(gameBar.getScore());
        ref.child("sunny_points").setValue(sunnyPoints.getSunnyPoints());
    }

    private void saveMissionData(DatabaseReference ref) {
        //salva ai dati delle missioni
        Missioni mission = listaMissioni.get(0);
        ref.child("missioni").child("tot_junk_rec").setValue(mission.getTotJunkRec());
        mission = listaMissioni.get(1);
        ref.child("missioni").child("tot_rec_upgr").setValue(mission.getTotRecUpgr());
        mission = listaMissioni.get(2);
        ref.child("missioni").child("tot_sunny_accum").setValue(mission.getTotSunnyAccum());
        mission = listaMissioni.get(3);
        ref.child("missioni").child("tot_unit_points_used").setValue(mission.getTotUnitPointsUsed());
    }

    private void saveRecUnitData() {
        ref = mainRef.child("rec_unit");

        //salva i dati inerenti alle unità di riciclo
        ref.child("recycling_speed").setValue(RecUnit.getRecyclingSpeed());
        saveGlassUnitData(ref);
        savePaperUnitData(ref);
        saveAluminumUnitData(ref);
        saveSteelUnitData(ref);
        savePlasticUnitData(ref);
        saveEWasteUnitData(ref);
        saveIncineratorData(ref);
    }

    private void saveRecUnit(DatabaseReference ref, RecUnit recUnit) {

        //salva i dati inerenti a ciascuna unità di riciclo
        ref.child("is_recycling").setValue(recUnit.getIsRecycling());
        ref.child("is_unlocked").setValue(recUnit.getIsUnlocked());
        ref.child("is_upgraded").setValue(recUnit.getIsUpgraded());
        ref.child("junk_being_recycled").setValue(recUnit.getJunkBeingRecycled());
        ref.child("rec_total").setValue(recUnit.getRecTotal());
        ref.child("rec_total_upgraded").setValue(recUnit.getRecTotalUpgraded());
        ref.child("recycled_unit").setValue(recUnit.getRecycledUnit());
        ref.child("recycled_unit_upgraded").setValue(recUnit.getRecycledUnitUpgraded());
        ref.child("state").setValue(recUnit.getState());
        ref.child("unit_points").setValue(recUnit.getUnitPoints());
    }

    private void saveGlassUnitData(DatabaseReference ref) {
        RecUnit glassUnit = recUnitList.get(0);
        ref = ref.child("glass_unit");
        saveRecUnit(ref, glassUnit); //salva i dati inerenti all'unità del vetro
    }

    private void savePaperUnitData(DatabaseReference ref) {
        RecUnit paperUnit = recUnitList.get(1);
        ref = ref.child("paper_unit");
        saveRecUnit(ref, paperUnit); //salva i dati inerenti all'unità della carta
    }

    private void saveAluminumUnitData(DatabaseReference ref) {
        RecUnit aluminumUnit = recUnitList.get(2);
        ref = ref.child("aluminum_unit");
        saveRecUnit(ref, aluminumUnit); //salva i dati inerenti all'unità dell'alluminio
    }

    private void saveSteelUnitData(DatabaseReference ref) {
        RecUnit steelUnit = recUnitList.get(3);
        ref = ref.child("steel_unit");
        saveRecUnit(ref, steelUnit); //salva i dati inerenti l'unità dell'acciaio
    }

    private void savePlasticUnitData(DatabaseReference ref) {
        RecUnit plasticUnit = recUnitList.get(4);
        ref = ref.child("plastic_unit");
        saveRecUnit(ref, plasticUnit); //salva i dati inerenti all'unità della plastica
    }

    private void saveEWasteUnitData(DatabaseReference ref) {
        RecUnit eWasteUnit = recUnitList.get(5);
        ref = ref.child("ewaste_unit");
        saveRecUnit(ref, eWasteUnit); //salva i dati inerenti all'unità dei rifiuti tecnologici
    }

    private void saveIncineratorData(DatabaseReference ref) {
        RecUnit incinerator = recUnitList.get(6);
        ref = ref.child("incinerator");
        saveRecUnit(ref, incinerator); //salva i dati inerenti all'inceneritore
    }

    protected void saveJunkData() {
        ref = mainRef.child("junk");

        //salva i dati inerenti ai rifiuti
        ref.child("distance").setValue(Junk.getDistance());
        ref.child("speed").setValue(Junk.getSpeed());
        ref.child("speed_increase").setValue(Junk.getSpeedIncrease());
        saveGlassData(ref);
        savePaperData(ref);
        saveAluminumData(ref);
        saveSteelData(ref);
        savePlasticData(ref);
        saveEWasteData(ref);
        saveHazarWasteData(ref);
        saveSlowDownData(ref);
        saveSpeedUpData(ref);
        saveAllJunk(ref);
    }

    private void saveGlassData(DatabaseReference ref) {
        ref = ref.child("glass");
        ref.child("tasso").setValue(Glass.getTasso()); //salva i dati inerenti ai rifiuti di vetro
    }

    private void savePaperData(DatabaseReference ref) {
        ref = ref.child("paper");

        //salva i dati inerenti ai rifiuti di carta
        ref.child("tasso").setValue(Paper.getTasso());
        ref.child("tasso_massimo_raggiunto").setValue(Paper.isTassoMassimoRaggiunto());
    }

    private void saveAluminumData(DatabaseReference ref) {
        ref = ref.child("aluminum");

        //salva i dati inerenti ai rifiuti di alluminio
        ref.child("tasso").setValue(Aluminum.getTasso());
        ref.child("tasso_massimo_raggiunto").setValue(Aluminum.isTassoMassimoRaggiunto());
    }

    private void saveSteelData(DatabaseReference ref) {
        ref = ref.child("steel");

        //salva i dati inerenti ai rifiuti di acciaio
        ref.child("tasso").setValue(Steel.getTasso());
        ref.child("tasso_massimo_raggiunto").setValue(Steel.isTassoMassimoRaggiunto());
    }

    private void savePlasticData(DatabaseReference ref) {
        ref = ref.child("plastic");

        //salva i dati inerenti ai rifiuti di plastica
        ref.child("tasso").setValue(Plastic.getTasso());
        ref.child("tasso_massimo_raggiunto").setValue(Plastic.isTassoMassimoRaggiunto());
    }

    private void saveEWasteData(DatabaseReference ref) {
        ref = ref.child("ewaste");

        //salva i dati inerenti ai rifiuti tecnologici
        ref.child("tasso").setValue(EWaste.getTasso());
        ref.child("tasso_massimo_raggiunto").setValue(EWaste.isTassoMassimoRaggiunto());
    }

    private void saveHazarWasteData(DatabaseReference ref) {
        ref = ref.child("hazar_waste");

        //salva i dati inerenti ai rifiuti pericolosi
        ref.child("tasso").setValue(HazarWaste.getTasso());
        ref.child("tasso_massimo_raggiunto").setValue(HazarWaste.isTassoMassimoRaggiunto());
    }

    //metodo che verrà sovrascritto dalla PowerUpGameView
    protected void saveSlowDownData(DatabaseReference ref) {
        ref = ref.child("slow_down");
    }

    //metodo che verrà sovrascritto dalla PowerUpGameView
    protected void saveSpeedUpData(DatabaseReference ref) {
        ref = ref.child("speed_up");
    }

    private void saveAllJunk(DatabaseReference ref) {
        int num_junk = 0;

        //per ogni rifiuto presente nella junkList
        for (int x = 0; x < junkList.size(); x++) {
            Junk junk = junkList.get(x);

            //salva i dati inerenti al rifiuto (posizione e tipologia)
            ref = ref.child("junk" + x);
            ref.child("x").setValue(junk.getX());
            ref.child("y").setValue(junk.getY());
            ref.child("type").setValue(junk.getJunkType());
            ref = ref.getParent();
            num_junk++; //aumenta di uno la variabile che conta il numero dei rifiuti
        }

        ref.child("num_junk").setValue(num_junk); //salva il numero dei rifiuti
    }

    private void saveScores() {
        myRef = database.getReference(LoginActivity.currentUser).child("score");
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //salva il punteggio, la modalità e la difficoltà di gioco
                index = Math.toIntExact(snapshot.getChildrenCount());
                if(!MultigiocatoreActivity.unoVSunoClassico && !MultigiocatoreActivity.unoVSunoPowerUp) {
                    myRef.child("score" + index).setValue("Modalità: " + GiocatoreSingoloActivity.modalità + "\n" + "Difficoltà: " + DifficoltaActivity.difficoltà + "\n" + "Punteggio: " + String.valueOf(gameBar.getScore()));
                }else{
                    myRef.child("score" + index).setValue("Modalità: " + MultigiocatoreActivity.modalità + "\n" + "Punteggio: " + String.valueOf(gameBar.getScore()));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setScore(){
        if(MultigiocatoreActivity.unoVSunoClassico) {
            if (ConnessioneActivity.player1) {
                ref = database.getReference("rooms_uno_contro_uno_classico").child(ConnessioneActivity.roomName).child("player1_isPlaying");
                ref.setValue(false);
                ref = null;
                ref = database.getReference("rooms_uno_contro_uno_classico").child(ConnessioneActivity.roomName);
                ref.child("score_player1").setValue(gameBar.getScore());

            } else {
                ref = database.getReference("rooms_uno_contro_uno_classico").child(ConnessioneActivity.roomName).child("player2_isPlaying");
                ref.setValue(false);
                ref = null;
                ref = database.getReference("rooms_uno_contro_uno_classico").child(ConnessioneActivity.roomName);
                ref.child("score_player2").setValue(gameBar.getScore());
            }
            ref = database.getReference("rooms_uno_contro_uno_classico").child(ConnessioneActivity.roomName).child("numero_giocatori");
            ref.setValue(0);
        }else if(MultigiocatoreActivity.unoVSunoPowerUp){
            if (ConnessioneActivity.player1) {
                ref = database.getReference("rooms_uno_contro_uno_powerUp").child(ConnessioneActivity.roomName).child("player1_isPlaying");
                ref.setValue(false);
                ref = null;
                ref = database.getReference("rooms_uno_contro_uno_powerUp").child(ConnessioneActivity.roomName);
                ref.child("score_player1").setValue(gameBar.getScore());

            } else {
                ref = database.getReference("rooms_uno_contro_uno_powerUp").child(ConnessioneActivity.roomName).child("player2_isPlaying");
                ref.setValue(false);
                ref = null;
                ref = database.getReference("rooms_uno_contro_uno_powerUp").child(ConnessioneActivity.roomName);
                ref.child("score_player2").setValue(gameBar.getScore());
            }
            ref = database.getReference("rooms_uno_contro_uno_powerUp").child(ConnessioneActivity.roomName).child("numero_giocatori");
            ref.setValue(0);
        }
    }


    private void retrieveData() {
        //se l'utente è loggato ed è stata precedentemente salvata una partita
        if (LoginActivity.currentUser != null && GiocatoreSingoloActivity.partitaSalvata) {
            retrieveGeneralData(); //prendi e imposta i dati generici della partita
            retrieveRecUnitData(); //prendi e imposta i dati inerenti alle unità di riciclo
            retrieveJunkData(); //prendi e imposta i dati inerenti ai rifiuti
        }
    }

    private void retrieveGeneralData() {
        ref = mainRef.child("general_data");
        ref.child("is_saved").setValue(false); //la partita non è più considerata salvata
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //prendi e imposta i dati generici della partita
                DifficoltaActivity.difficoltà = snapshot.child("difficoltà").getValue(String.class);
                GiocatoreSingoloActivity.modalità = snapshot.child("modalità").getValue(String.class);
                gameBar.setScore(snapshot.child("score").getValue(Integer.class));
                sunnyPoints.setSunnyPoints(snapshot.child("sunny_points").getValue(Integer.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        retrieveMissionsData(); //prendi e imposta i dati inerenti alle missioni
    }

    private void retrieveMissionsData() {
        ref = ref.child("missioni");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //prendi e imposta i dati inerenti alle missioni
                listaMissioni.get(0).setTotJunkRec(snapshot.child("tot_junk_rec").getValue(Integer.class));
                listaMissioni.get(1).setTotRecUpgr(snapshot.child("tot_rec_upgr").getValue(Integer.class));
                listaMissioni.get(2).setTotSunnyAccum(snapshot.child("tot_sunny_accum").getValue(Integer.class));
                listaMissioni.get(3).setTotUnitPointsUsed(snapshot.child("tot_unit_points_used").getValue(Integer.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void retrieveRecUnitData() {
        ref = mainRef.child("rec_unit");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //prendi e imposta la velocità di riciclo delle unità
                RecUnit.setRecyclingSpeed(snapshot.child("recycling_speed").getValue(Double.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //prendi e imposta i dati per ciascuna unità di riciclo
        retrieveRecUnit(recUnitList.get(0), ref);
        retrieveRecUnit(recUnitList.get(1), ref);
        retrieveRecUnit(recUnitList.get(2), ref);
        retrieveRecUnit(recUnitList.get(3), ref);
        retrieveRecUnit(recUnitList.get(4), ref);
        retrieveRecUnit(recUnitList.get(5), ref);
        retrieveRecUnit(recUnitList.get(6), ref);
    }

    private void retrieveRecUnit(RecUnit recUnit, DatabaseReference ref) {
        ref = ref.child(recUnit.getUnitType());
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //prendi e imposta tutti i dati inerenti alle unità di riciclo
                recUnit.setIsRecycling(snapshot.child("is_recycling").getValue(Boolean.class));
                recUnit.setIsUnlocked(snapshot.child("is_unlocked").getValue(Boolean.class));
                recUnit.setIsUpgraded(snapshot.child("is_upgraded").getValue(Boolean.class));
                recUnit.setJunkBeingRecycled(snapshot.child("junk_being_recycled").getValue(Integer.class));
                recUnit.setRecTotal(snapshot.child("rec_total").getValue(Integer.class));
                recUnit.setRecTotalUpgraded(snapshot.child("rec_total_upgraded").getValue(Integer.class));
                recUnit.setRecycledUnit(snapshot.child("recycled_unit").getValue(Integer.class));
                recUnit.setRecycledUnitUpgraded(snapshot.child("recycled_unit_upgraded").getValue(Integer.class));
                recUnit.setState(snapshot.child("state").getValue(Integer.class));
                recUnit.setUnitPoints(snapshot.child("unit_points").getValue(Integer.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void retrieveJunkData() {
        ref = mainRef.child("junk");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //prendi e imposta i dati inerenti ai rifiuti
                Junk.setDistance(snapshot.child("distance").getValue(Integer.class));
                Junk.setSpeed(snapshot.child("speed").getValue(Double.class));
                Junk.setSpeedIncrease(snapshot.child("speed_increase").getValue(Double.class));
                num_junk = snapshot.child("num_junk").getValue(Integer.class);
                retrieveAllJunk(snapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //prendi e imposti i dati inerenti a ciascuna tipologia di rifiuto
        retrieveGlassData(ref);
        retrievePaperData(ref);
        retrieveAluminumData(ref);
        retrieveSteelData(ref);
        retrievePlasticData(ref);
        retrieveEWasteData(ref);
        retrieveHazarWasteData(ref);
        retrieveSlowDownData(ref);
        retrieveSpeedUpData(ref);
    }

    private void retrieveGlassData(DatabaseReference ref) {
        ref = ref.child("glass");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Glass.setTasso(snapshot.child("tasso").getValue(Double.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void retrievePaperData(DatabaseReference ref) {
        ref = ref.child("paper");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Paper.setTasso(snapshot.child("tasso").getValue(Double.class));
                Paper.setTassoMassimoRaggiunto(snapshot.child("tasso_massimo_raggiunto").getValue(Boolean.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void retrieveAluminumData(DatabaseReference ref) {
        ref = ref.child("aluminum");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Aluminum.setTasso(snapshot.child("tasso").getValue(Double.class));
                Aluminum.setTassoMassimoRaggiunto(snapshot.child("tasso_massimo_raggiunto").getValue(Boolean.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void retrieveSteelData(DatabaseReference ref) {
        ref = ref.child("steel");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Steel.setTasso(snapshot.child("tasso").getValue(Double.class));
                Steel.setTassoMassimoRaggiunto(snapshot.child("tasso_massimo_raggiunto").getValue(Boolean.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void retrievePlasticData(DatabaseReference ref) {
        ref = ref.child("plastic");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Plastic.setTasso(snapshot.child("tasso").getValue(Double.class));
                Plastic.setTassoMassimoRaggiunto(snapshot.child("tasso_massimo_raggiunto").getValue(Boolean.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void retrieveEWasteData(DatabaseReference ref) {
        ref = ref.child("ewaste");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                EWaste.setTasso(snapshot.child("tasso").getValue(Double.class));
                EWaste.setTassoMassimoRaggiunto(snapshot.child("tasso_massimo_raggiunto").getValue(Boolean.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void retrieveHazarWasteData(DatabaseReference ref) {
        ref = ref.child("hazar_waste");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                HazarWaste.setTasso(snapshot.child("tasso").getValue(Double.class));
                HazarWaste.setTassoMassimoRaggiunto(snapshot.child("tasso_massimo_raggiunto").getValue(Boolean.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    //questo metodo verrà sovrascritto dalla PowerUpGameView
    protected void retrieveSlowDownData(DatabaseReference ref) {
        ref = ref.child("slow_down");
    }

    //questo metodo verrà sovrascritto dalla PowerUpGameView
    protected void retrieveSpeedUpData(DatabaseReference ref) {
        ref = ref.child("speed_up");
    }

    private void retrieveAllJunk(DataSnapshot snapshot) {
        //per ciascun rifiuto
        for (int i = 0; i < num_junk; i++) {
            //setta i dati inerenti al rifiuto
            DataSnapshot snap = snapshot.child("junk" + i);
            String junkType = snap.child("type").getValue(String.class);
            int x = snap.child("x").getValue(Integer.class);
            int y = snap.child("y").getValue(Integer.class);
            retrieveSingleJunk(junkType, x, y); //aggiungi il rifiuto in base alla tipologia
        }
    }

    private void retrieveSingleJunk(String junkType, int x, int y) {
        //in base alla tipologia di rifiuto precedentemente salvato
        if (junkType.equals("glass")) {
            junkList.add(new Glass(x, y, getResources())); //aggiungi un rifiuto di quella tipologia

        } else if (junkType.equals("paper")) {
            junkList.add(new Paper(x, y, getResources()));

        } else if (junkType.equals("aluminum")) {
            junkList.add(new Aluminum(x, y, getResources()));

        } else if (junkType.equals("steel")) {
            junkList.add(new Steel(x, y, getResources()));

        } else if (junkType.equals("plastic")) {
            junkList.add(new Plastic(x, y, getResources()));

        } else if (junkType.equals("ewaste")) {
            junkList.add(new EWaste(x, y, getResources()));

        } else if (junkType.equals("hazar_waste")) {
            junkList.add(new HazarWaste(x, y, getResources()));

        }

        //aggiungi i power up se questi sono stati salvati precedentemente nel database
        retrieveSingleSlowDown(junkType, x, y);
        retrieveSingleSpeedUp(junkType, x, y);
        retrieveSingleSunnyPow(junkType, x, y);
        retrieveSingleClearJunk(junkType, x, y);
    }

    protected void retrieveSingleSlowDown(String junkType, int x, int y) {
        //questo metodo verrà sovrascritto nella PowerUpGameView
    }

    protected void retrieveSingleSpeedUp(String junkType, int x, int y) {
        //questo metodo verrà sovrascritto nella PowerUpGameView
    }

    protected void retrieveSingleSunnyPow(String junkType, int x, int y) {
        //questo metodo verrà sovrascritto nella PowerUpGameView
    }

    protected void retrieveSingleClearJunk(String junkType, int x, int y) {
        //questo metodo verrà sovrascritto nella PowerUpGameView
    }
}