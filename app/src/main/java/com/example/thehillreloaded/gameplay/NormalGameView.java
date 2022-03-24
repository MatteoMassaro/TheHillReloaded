package com.example.thehillreloaded.gameplay;

import static com.example.thehillreloaded.menu.MenuActivity.screenRatioX;
import static com.example.thehillreloaded.menu.MenuActivity.screenRatioY;
import static com.example.thehillreloaded.menu.MusicPlayer.isPlayingEffect;
import static com.example.thehillreloaded.menu.MusicPlayer.isPlayingMusic;
import static com.example.thehillreloaded.menu.MusicPlayer.stopMusic;

import android.content.Context;
import android.content.Intent;

import com.example.thehillreloaded.R;
import com.example.thehillreloaded.gameplay.imageclass.Missioni;
import com.example.thehillreloaded.gameplay.imageclass.RecImages;
import com.example.thehillreloaded.menu.GiocatoreSingoloActivity;
import com.example.thehillreloaded.menu.MenuActivity;
import com.example.thehillreloaded.menu.MusicPlayer;
import com.example.thehillreloaded.menu.RisultatiActivity;
import com.example.thehillreloaded.menu.SchermataCaricamentoActivity;

public class NormalGameView extends GameView {

    private NormalGameActivity normalGameActivity;
    private RecImages missioni;

    public NormalGameView(Context context, int screenX, int screenY, float density) {
        super(context, screenX, screenY, density);
        normalGameActivity = (NormalGameActivity) context;
        missioni = new Missioni((int) (30 * screenRatioX), (int) (10 * screenRatioY), getResources());
    }

    //metodo per ricominciare la partita
    @Override
    public void restart() {
        super.restart();

        //termina la partita e ricomincia dalla schermata di caricamento
        normalGameActivity.finish();
        normalGameActivity.startActivity(new Intent(normalGameActivity, SchermataCaricamentoActivity.class));
    }

    //metodo per uscire dal gioco e tornare nel menù
    @Override
    public void exit() {
        super.exit();

        //termina la partita e ritorna all'activity per scegliere la modalità di gioco
        normalGameActivity.finish();
        normalGameActivity.startActivity(new Intent(normalGameActivity, MenuActivity.class));
    }

    //metodo per uscire dal gioco e andare all'activity che mostra i risultati
    @Override
    public void showResult(){
        super.showResult();

        //termina la partita e porta all'activity che mostra i risultati
        normalGameActivity.finish();
        normalGameActivity.startActivity(new Intent(normalGameActivity, RisultatiActivity.class));
    }

    @Override
    protected void changeMusic(int x, int y) {
        if (x >= missioni.getWidth() * 3 && y >= (int) (missioni.getHeight() * 6.2) && x < missioni.getWidth() * 3 + gameBar.getWidth() * 10 && y < (int) (missioni.getHeight() * 6.2) + gameBar.getHeight() * 10 && pause.isClicked()) {
            if (gameBar.isMusicClicked() && GameActivity.b == false && !isPlayingMusic) {
                MusicPlayer.playMusic(this.normalGameActivity, R.raw.game_music);
                this.normalGameActivity.changeMusic(0);
            } else if (!gameBar.isMusicClicked() && GameActivity.b == false && !isPlayingMusic) {
                MusicPlayer.playMusic(this.normalGameActivity, R.raw.game_music);
                this.normalGameActivity.changeMusic(0);
            } else {
                stopMusic();
                this.normalGameActivity.changeMusic(1);
            }
        }
    }

    @Override
    protected void changeAudio(int x, int y) {
        if(x >= missioni.getWidth() * 3  && y >= (int) (missioni.getHeight() * 7.7) && x < missioni.getWidth() * 3 + gameBar.getWidth()*10 && y < (int) (missioni.getHeight() * 7.7) + gameBar.getHeight()*10 && pause.isClicked()){
            if(gameBar.isAudioClicked() && GameActivity.b1 == false && GameView.flag == 0) {
                this.normalGameActivity.changeAudio(0);
                GameView.flag = 1;
            }
            else if(!gameBar.isAudioClicked() && GameActivity.b1 == false && GameView.flag == 0){
                this.normalGameActivity.changeAudio(0);
                GameView.flag = 1;
            }
            else{
                this.normalGameActivity.changeAudio(1);
            }
        }
    }

    @Override
    protected void usePreferences() {
        normalGameActivity.preferences();
    }

}

