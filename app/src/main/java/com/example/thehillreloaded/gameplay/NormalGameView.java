package com.example.thehillreloaded.gameplay;

import static com.example.thehillreloaded.menu.MusicPlayer.stopMusic;

import android.content.Context;
import android.content.Intent;

import com.example.thehillreloaded.R;
import com.example.thehillreloaded.menu.GiocatoreSingoloActivity;
import com.example.thehillreloaded.menu.MenuActivity;
import com.example.thehillreloaded.menu.MusicPlayer;
import com.example.thehillreloaded.menu.SchermataCaricamentoActivity;

public class NormalGameView extends GameView {

    private NormalGameActivity normalGameActivity;

    public NormalGameView(Context context, int screenX, int screenY, float density) {
        super(context, screenX, screenY, density);
        normalGameActivity = (NormalGameActivity) context;
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

    @Override
    protected void changeMusic(int x, int y) {
        if(x >= gameBar.getWidth() * 8 && y >= gameBar.getHeight() * 16 && x < gameBar.getWidth() * 8 + gameBar.getWidth()*3 && y < gameBar.getHeight() * 16 + gameBar.getHeight()*3 && pause.isClicked()){
            if(gameBar.isMusicClicked() && GiocatoreSingoloActivity.b == false) {
                MusicPlayer.playMusic(this.normalGameActivity, R.raw.game_music);
                this.normalGameActivity.changeMusic(0);
            }
            else {
                stopMusic();
                this.normalGameActivity.changeMusic(1);
            }
        }
    }

    @Override
    protected void changeAudio(int x, int y) {
        if(x >= gameBar.getWidth() * 8 && y >= gameBar.getHeight() * 20 && x < gameBar.getWidth() * 8 + gameBar.getWidth()*3 && y < gameBar.getHeight() * 20 + gameBar.getHeight()*3 && pause.isClicked()){
            if(gameBar.isAudioClicked() && GiocatoreSingoloActivity.b1 == false) {
                this.normalGameActivity.changeAudio(0);
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
