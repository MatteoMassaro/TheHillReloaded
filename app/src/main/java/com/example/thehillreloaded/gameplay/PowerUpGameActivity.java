package com.example.thehillreloaded.gameplay;

import android.graphics.Point;
import android.os.Bundle;
import android.util.DisplayMetrics;

public class PowerUpGameActivity extends GameActivity {

    //Variabili
    private PowerUpGameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        gameView = new PowerUpGameView(this, point.x, point.y, density);
        setContentView(gameView);
    }

    @Override
    protected void onPause() {
        super.onPause();
        gameView.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        gameView.resume();
    }

    @Override
    public void onBackPressed() {
    }
}