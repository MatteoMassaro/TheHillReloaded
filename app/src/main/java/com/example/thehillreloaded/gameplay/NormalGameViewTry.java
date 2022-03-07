package com.example.thehillreloaded.gameplay;

import android.content.Context;
import android.content.Intent;
import com.example.thehillreloaded.menu.MenuActivity;
import com.example.thehillreloaded.menu.SchermataCaricamentoActivity;

public class NormalGameViewTry extends GameView {

    private NormalGameActivity normalGameActivity;

    public NormalGameViewTry(Context context, int screenX, int screenY, float density) {
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
    public void exit() {
        super.exit();

        //termina la partita e ritorna all'activity per scegliere la modalità di gioco
        normalGameActivity.finish();
        normalGameActivity.startActivity(new Intent(normalGameActivity, MenuActivity.class));
    }
}
