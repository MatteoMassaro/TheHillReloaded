package com.example.thehillreloaded.gameplay;

import static com.example.thehillreloaded.menu.DifficoltaActivity.tassoDifficolta;
import static com.example.thehillreloaded.menu.MenuActivity.screenRatioX;
import static com.example.thehillreloaded.menu.MusicPlayer.stopMusic;

import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;

import androidx.annotation.NonNull;

import com.example.thehillreloaded.R;
import com.example.thehillreloaded.gameplay.falling_objects.Aluminum;
import com.example.thehillreloaded.gameplay.falling_objects.ClearJunk;
import com.example.thehillreloaded.gameplay.falling_objects.EWaste;
import com.example.thehillreloaded.gameplay.falling_objects.Glass;
import com.example.thehillreloaded.gameplay.falling_objects.HazarWaste;
import com.example.thehillreloaded.gameplay.falling_objects.Junk;
import com.example.thehillreloaded.gameplay.falling_objects.Paper;
import com.example.thehillreloaded.gameplay.falling_objects.Plastic;
import com.example.thehillreloaded.gameplay.falling_objects.PowerUp;
import com.example.thehillreloaded.gameplay.falling_objects.SlowDown;
import com.example.thehillreloaded.gameplay.falling_objects.SpeedUp;
import com.example.thehillreloaded.gameplay.falling_objects.Steel;
import com.example.thehillreloaded.gameplay.falling_objects.SunnyPow;
import com.example.thehillreloaded.menu.GiocatoreSingoloActivity;
import com.example.thehillreloaded.menu.MenuActivity;
import com.example.thehillreloaded.menu.MusicPlayer;
import com.example.thehillreloaded.menu.RisultatiActivity;
import com.example.thehillreloaded.menu.SchermataCaricamentoActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Random;

public class PowerUpGameView extends GameView {

    private PowerUpGameActivity powerUpGameActivity;

    public PowerUpGameView(Context context, int screenX, int screenY, float density) {
        super(context, screenX, screenY, density);

        powerUpGameActivity = (PowerUpGameActivity) context;
        //se non si sta ripartendo da una partita salvata
        if(!GiocatoreSingoloActivity.partitaSalvata) {
            //imposta i valori dei power-up in base alla difficoltà scelta
            PowerUp.setTasso(PowerUp.getTasso() / tassoDifficolta);
            SlowDown.setDuration((int) (SlowDown.getDuration() / tassoDifficolta));
            SpeedUp.setDuration((int) (SpeedUp.getDuration() / tassoDifficolta));
        }
    }

    //metodo per ricominciare la partita
    @Override
    public void restart() {
        super.restart();
        PowerUp.resetTasso();
        SlowDown.resetValues();
        SpeedUp.resetValues();

        //termina la partita e ricomincia dalla schermata di caricamento
        powerUpGameActivity.finish();
        powerUpGameActivity.startActivity(new Intent(powerUpGameActivity, SchermataCaricamentoActivity.class));
    }

    //metodo per uscire dal gioco e tornare nel menù
    public void exit() {
        super.exit();
        PowerUp.resetTasso();
        SlowDown.resetValues();
        SpeedUp.resetValues();

        //termina la partita e ritorna all'activity per scegliere la modalità di gioco
        powerUpGameActivity.finish();
        powerUpGameActivity.startActivity(new Intent(powerUpGameActivity, MenuActivity.class));
    }

    //metodo per uscire dal gioco e andare all'activity che mostra i risultati
    public void showResult() {
        super.showResult();
        PowerUp.resetTasso();
        SlowDown.resetValues();
        SpeedUp.resetValues();

        //termina la partita e porta all'activity che mostra i risultati
        powerUpGameActivity.finish();
        powerUpGameActivity.startActivity(new Intent(powerUpGameActivity, RisultatiActivity.class));
    }


    @Override
    protected void spawnJunk(ArrayList<Junk> junkArrayList) {
        //se la distanza percorsa dagli oggetti Junk è sufficiente
        if (Junk.distanceIsEnough()) {
            Random random = new Random();
            double tassoTotale = Glass.getTasso() + Paper.getTasso() + Aluminum.getTasso() + Steel.getTasso() + Plastic.getTasso() + EWaste.getTasso() + HazarWaste.getTasso() + PowerUp.getTasso() * 4;
            double num = tassoTotale * random.nextDouble();

            //esegui controlli sul numero ottenuto, in base al tasso di spawn dei rifiuti, e aggiungi un rifiuto alla lista junkList
            if (num <= PowerUp.getTasso()) {
                //aggiungi il power up per rallentare la caduta dei rifiuti
                SlowDown slowDown = new SlowDown(0, 0, getResources());
                junkList.add(new SlowDown((random.nextInt(spawnBoundX - slowDown.getWidth()) + (int) (25 * screenRatioX)), spawnY, getResources()));

            } else if (num > PowerUp.getTasso() && num <= PowerUp.getTasso() * 2) {
                //aggiungi il power up per velocizzare i processi di smaltimento dei rifiuti
                SpeedUp speedUp = new SpeedUp(0, 0, getResources());
                junkList.add(new SpeedUp((random.nextInt(spawnBoundX - speedUp.getWidth()) + (int) (25 * screenRatioX)), spawnY, getResources()));

            } else if (num > PowerUp.getTasso() * 2 && num <= PowerUp.getTasso() * 3) {
                //aggiungi il power up per ricevere due sunnyPoints extra
                SunnyPow sunnyPow = new SunnyPow(0, 0, getResources());
                junkList.add(new SunnyPow((random.nextInt(spawnBoundX - sunnyPow.getWidth()) + (int) (25 * screenRatioX)), spawnY, getResources()));

            } else if (num > PowerUp.getTasso() * 3 && num <= PowerUp.getTasso() * 4) {
                //aggiungi il power up per rimuovere due rifiuti istantaneamente
                ClearJunk clearJunk = new ClearJunk(0, 0, getResources());
                junkList.add(new ClearJunk((random.nextInt(spawnBoundX - clearJunk.getWidth()) + (int) (25 * screenRatioX)), spawnY, getResources()));

            } else if (num > PowerUp.getTasso() * 4 && num <= Glass.getTasso() + PowerUp.getTasso() * 4) {
                //aggiungi un rifiuto di vetro
                Glass glass = new Glass(0, 0, getResources());
                junkList.add(new Glass((random.nextInt(spawnBoundX - glass.getWidth()) + (int) (25 * screenRatioX)), spawnY, getResources()));

            } else if (num > Glass.getTasso() + PowerUp.getTasso() * 4 && num <= Glass.getTasso() + Paper.getTasso() + PowerUp.getTasso() * 4) {
                //aggiungi un rifiuto di carta
                Paper paper = new Paper(0, 0, getResources());
                junkList.add(new Paper((random.nextInt(spawnBoundX - paper.getWidth()) + (int) (25 * screenRatioX)), spawnY, getResources()));

            } else if (num > Glass.getTasso() + Paper.getTasso() + PowerUp.getTasso() * 4 && num <= Glass.getTasso() + Paper.getTasso() + Aluminum.getTasso() + PowerUp.getTasso() * 4) {
                //aggiungi un rifiuto di alluminio
                Aluminum aluminum = new Aluminum(0, 0, getResources());
                junkList.add(new Aluminum((random.nextInt(spawnBoundX - aluminum.getWidth()) + (int) (25 * screenRatioX)), spawnY, getResources()));

            } else if (num > Glass.getTasso() + Paper.getTasso() + Aluminum.getTasso() + PowerUp.getTasso() * 4 && num <= Glass.getTasso() + Paper.getTasso() + Aluminum.getTasso() + HazarWaste.getTasso() + PowerUp.getTasso() * 4) {
                //aggiungi un rifiuto pericoloso
                HazarWaste hazarWaste = new HazarWaste(0, 0, getResources());
                junkList.add(new HazarWaste((random.nextInt(spawnBoundX - hazarWaste.getWidth()) + (int) (25 * screenRatioX)), spawnY, getResources()));

            } else if (num > Glass.getTasso() + Paper.getTasso() + Aluminum.getTasso() + HazarWaste.getTasso() + PowerUp.getTasso() * 4 && num <= tassoTotale - Plastic.getTasso() - EWaste.getTasso()) {
                //aggiungi un rifiuto di acciaio
                Steel steel = new Steel(0, 0, getResources());
                junkList.add(new Steel((random.nextInt(spawnBoundX - steel.getWidth()) + (int) (25 * screenRatioX)), spawnY, getResources()));

            } else if (num > tassoTotale - Plastic.getTasso() - EWaste.getTasso() && num <= tassoTotale - EWaste.getTasso()) {
                //aggiungi un rifiuto di plastica
                Plastic plastic = new Plastic(0, 0, getResources());
                junkList.add(new Plastic((random.nextInt(spawnBoundX - plastic.getWidth()) + (int) (25 * screenRatioX)), spawnY, getResources()));

            } else {
                //aggiungi un rifiuto tecnologico
                EWaste eWaste = new EWaste(0, 0, getResources());
                junkList.add(new EWaste((random.nextInt(spawnBoundX - eWaste.getWidth()) + (int) (25 * screenRatioX)), spawnY, getResources()));
            }
        }
    }

    @Override
    protected void updateJunkValues() {
        //controlla se i potenziamenti sono ancora attivi (se sono stati attivati precedentemente)
        SlowDown.checkIfPowerIsUp();
        SpeedUp.checkIfPowerIsUp();
        super.updateJunkValues();
    }

    @Override
    protected void activatePowerUp(Junk junk, int junkIndex) {
        ((PowerUp) junk).applyPowerUp(); //applica il power up
        junkList.remove(junkIndex); //rimuovi il power up dallo schermo

        if (junk instanceof SunnyPow) { //se si tratta del power up dei sunnyPoints
            sunnyPoints.setSunnyPoints(sunnyPoints.getSunnyPoints()+2); //aumenta il numero dei sunnyPoints di due

        } else if (junk instanceof ClearJunk) { //se si tratta del power up ClearJunk
            Random random = new Random();
            int size = junkList.size();
            int firstJunk = random.nextInt(size);
            int secondJunk = random.nextInt(size);
            int amountOfPowerUps = 0;

            for (int x = 0; x < junkList.size(); x++) {

                if (junkList.get(x) instanceof PowerUp) {
                    amountOfPowerUps++;
                }
            }

            if (size - amountOfPowerUps >= 2) { //se si hanno almeno due rifiuti sullo schermo

                // finché le variabili firstJunk e secondJunk sono uguali o sono tali da trovare solo potenziamenti
                while (junkList.get(firstJunk) instanceof PowerUp || junkList.get(secondJunk) instanceof PowerUp || firstJunk == secondJunk) {
                    firstJunk = random.nextInt(size); //ricalcolale randomicamente
                    secondJunk = random.nextInt(size);
                }

                if (firstJunk > secondJunk) { //se firstJunk è maggiore di secondJunk
                    junkList.remove(firstJunk); //rimuovi i rifiuti all'indice firstJunk
                    junkList.remove(secondJunk); //e secondJunk

                } else { //altrimenti
                    junkList.remove(secondJunk); //rimuovi i rifiuti all'indice secondJunk
                    junkList.remove(firstJunk); //e firstJunk
                }

            } else if (size - amountOfPowerUps == 1) { //se si ha un solo rifiuto sullo schermo

                // finché l'oggetto all'indice firstJunk è un power up
                while (junkList.get(firstJunk) instanceof PowerUp) {
                    firstJunk = random.nextInt(size); //ricalcola la variabile firstJunk
                }

                junkList.remove(firstJunk); //rimuovi il rifiuto all'indice firstJunk
            }
        }
    }

    @Override
    protected void changeMusic(int x, int y) {
        if(x >= gameBar.getWidth() * 8 && y >= gameBar.getHeight() * 16 && x < gameBar.getWidth() * 8 + gameBar.getWidth()*3 && y < gameBar.getHeight() * 16 + gameBar.getHeight()*3 && pause.isClicked()){
            if(gameBar.isMusicClicked() && GiocatoreSingoloActivity.b == false) {
                MusicPlayer.playMusic(this.powerUpGameActivity, R.raw.game_music);
                this.powerUpGameActivity.changeMusic(0);
            }
            else {
                stopMusic();
                this.powerUpGameActivity.changeMusic(1);
            }
        }
    }

    @Override
    protected void changeAudio(int x, int y) {
        if(x >= gameBar.getWidth() * 8 && y >= gameBar.getHeight() * 20 && x < gameBar.getWidth() * 8 + gameBar.getWidth()*3 && y < gameBar.getHeight() * 20 + gameBar.getHeight()*3 && pause.isClicked()){
            if(gameBar.isAudioClicked() && GiocatoreSingoloActivity.b1 == false) {
                this.powerUpGameActivity.changeAudio(0);
            }
            else{
                this.powerUpGameActivity.changeAudio(1);
            }
        }
    }

    @Override
    protected void usePreferences() {
        powerUpGameActivity.preferences();
    }

    @Override
    protected void saveSlowDownData(DatabaseReference ref) {
        //salva i valori inerenti al power-up SlowDown
        ref.child("current_duration").setValue(SlowDown.getCurrentDuration());
        ref.child("is_powered_up").setValue(SlowDown.getIsPoweredUp());
    }

    @Override
    protected void saveSpeedUpData(DatabaseReference ref) {
        //salva i valori inerenti al power-up SpeedUp
        ref.child("current_duration").setValue(SpeedUp.getCurrentDuration());
        ref.child("is_powered_up").setValue(SpeedUp.getIsPoweredUp());
    }

    @Override
    protected void retrieveSlowDownData(DatabaseReference ref) {
        super.retrieveSlowDownData(ref);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //prendi e imposta i valori inerenti al power-up SlowDown
                SlowDown.setCurrentDuration(snapshot.child("current_duration").getValue(Integer.class));
                SlowDown.setIsPoweredUp(snapshot.child("is_powered_up").getValue(Boolean.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    protected void retrieveSpeedUpData(DatabaseReference ref) {
        super.retrieveSlowDownData(ref);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //prendi e imposta i valori inerenti al power-up SpeedUp
                SpeedUp.setCurrentDuration(snapshot.child("current_duration").getValue(Integer.class));
                SpeedUp.setIsPoweredUp(snapshot.child("is_powered_up").getValue(Boolean.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    //metodi che servono per riportare a schermo i power-up di una partita precedentemente salvata

    @Override
    protected void retrieveSingleSlowDown(String junkType, int x, int y) {
        if(junkType.equals("slow_down")) {
            junkList.add(new SlowDown(x, y, getResources()));
        }
    }

    @Override
    protected void retrieveSingleSpeedUp(String junkType, int x, int y) {
        if(junkType.equals("speed_up")) {
            junkList.add(new SpeedUp(x, y, getResources()));
        }
    }

    @Override
    protected void retrieveSingleSunnyPow(String junkType, int x, int y) {
        if(junkType.equals("sunny_pow")) {
            junkList.add(new SunnyPow(x, y, getResources()));
        }
    }

    @Override
    protected void retrieveSingleClearJunk(String junkType, int x, int y) {
        if(junkType.equals("clear_junk")) {
            junkList.add(new ClearJunk(x, y, getResources()));
        }
    }
}
