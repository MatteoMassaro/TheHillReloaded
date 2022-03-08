package com.example.thehillreloaded.gameplay;

import android.graphics.Point;
import android.os.Bundle;
import android.util.DisplayMetrics;

public class NormalGameActivity extends GameActivity {

    //Variabili
    private NormalGameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Point point = new Point();
        getWindowManager().getDefaultDisplay().getSize(point);
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        float density = displayMetrics.density;

        gameView = new NormalGameView(this, point.x, point.y, density);
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
        super.onBackPressed();
        gameView.restart();
    }
}