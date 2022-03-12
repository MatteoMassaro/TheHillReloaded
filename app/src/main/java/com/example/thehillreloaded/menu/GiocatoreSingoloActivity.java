package com.example.thehillreloaded.menu;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import androidx.cardview.widget.CardView;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.thehillreloaded.R;
import com.example.thehillreloaded.accesso.LoginActivity;
import com.example.thehillreloaded.animazioni.Animazioni;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class GiocatoreSingoloActivity extends Animazioni implements View.OnClickListener{

    //Variabili
    public CardView modalitaClassica, modalitaPowerUp;
    public ImageView indietro, info;
    public static boolean classica, powerUp, partitaSalvata, accesso = false;
    public static boolean b, b1;
    public static String modalità, modalitàSalvata;
    FirebaseDatabase database;
    DatabaseReference ref;

    //Chiama l'animazione all'avvio dell'activity
    @Override
    protected void onStart(){
        super.onStart();
        runAnimationSlideIn(modalitaClassica);
        runAnimationSlideIn(modalitaPowerUp);
    }

    //Chiama l'animazione alla pausa dell'activity
    @Override
    protected void onPause(){
        super.onPause();
        runAnimationSlideOut(modalitaClassica);
        runAnimationSlideOut(modalitaPowerUp);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giocatoresingolo);

        //Imposta l'orientamento portrait come obbligatorio
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //Trova le view tramite l'id e le assegna alle variabili
        modalitaClassica = findViewById(R.id.modalitaClassica);
        modalitaPowerUp = findViewById(R.id.modalitaPowerUp);
        indietro = findViewById(R.id.indietro);
        info = findViewById(R.id.info);

        //Imposta metodo di callback quando la view viene cliccata
        modalitaClassica.setOnClickListener(this);
        modalitaPowerUp.setOnClickListener(this);
        indietro.setOnClickListener(this);
        info.setOnClickListener(this);

        //Animazione pulsanti
        clickButtonAnimation(modalitaClassica);
        clickButtonAnimation(modalitaPowerUp);
        clickButtonAnimation(indietro);
        clickButtonAnimation(info);

        if(LoginActivity.currentUser != null) {
            database = FirebaseDatabase.getInstance();
            ref = database.getReference(LoginActivity.currentUser).child("general_data");
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    partitaSalvata = dataSnapshot.child("is_saved").getValue(Boolean.class);
                    modalitàSalvata = dataSnapshot.child("modalità").getValue(String.class);
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Log.w("", "Failed to read value.", error.toException());
                }
            });
        }
    }

    //Crea l'intent per passare all'activity successiva dopo la pressione di un pulsante
    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        Intent i;
        switch (view.getId()){
            case R.id.modalitaClassica:
                MenuActivity.modalità = "Classica";
                classica = true;
                powerUp = false;
                modalità = "Classica";
                if(partitaSalvata && modalitàSalvata.equals(modalità)){
                    i = new Intent(this, PartitaSalvataActivity.class);
                    Bundle b = ActivityOptions.makeSceneTransitionAnimation(this).toBundle();
                    startActivity(i, b);
                    finish();
                }else {
                    i = new Intent(this, DifficoltaActivity.class);
                    Bundle b = ActivityOptions.makeSceneTransitionAnimation(this).toBundle();
                    startActivity(i, b);
                    finish();
                }
                break;

            case R.id.modalitaPowerUp:
                MenuActivity.modalità = "Power-up";
                powerUp = true;
                classica = false;
                modalità = "Power_up";
                if(partitaSalvata && modalitàSalvata.equals(modalità)){
                    i = new Intent(this, PartitaSalvataActivity.class);
                    Bundle b = ActivityOptions.makeSceneTransitionAnimation(this).toBundle();
                    startActivity(i, b);
                    finish();
                }else {
                    i = new Intent(this, DifficoltaActivity.class);
                    Bundle b = ActivityOptions.makeSceneTransitionAnimation(this).toBundle();
                    startActivity(i, b);
                    finish();
                }
                break;

            case R.id.indietro:
                i = new Intent(this, MenuActivity.class);
                Bundle b2 = ActivityOptions.makeSceneTransitionAnimation(this).toBundle();
                startActivity(i, b2);
                finish();
                break;

            case R.id.info:
                i = new Intent(this, InfoActivity.class);
                Bundle b3 = ActivityOptions.makeSceneTransitionAnimation(this).toBundle();
                startActivity(i, b3);
                finish();
                break;

        }
    }
}