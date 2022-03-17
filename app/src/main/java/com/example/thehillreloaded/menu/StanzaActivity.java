package com.example.thehillreloaded.menu;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;


import androidx.annotation.NonNull;

import com.example.thehillreloaded.R;
import com.example.thehillreloaded.animazioni.Animazioni;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;
import java.util.concurrent.ThreadFactory;

public class StanzaActivity extends Animazioni {

    //variabili
    Button gioca;
    ImageView indietro;
    ProgressBar caricamento;
    FirebaseDatabase database;
    DatabaseReference ref;
    int giocatori;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stanza);

        //Trova le view tramite l'id e le assegna alle variabili
        gioca =  findViewById(R.id.gioca);
        indietro =  findViewById(R.id.indietro);
        caricamento =  findViewById(R.id.caricamento);

        //Imposta l'orientamento portrait come obbligatorio
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //Animazione pulsanti
        clickButtonAnimation(gioca);
        clickButtonAnimation(indietro);

        database = FirebaseDatabase.getInstance();

        //Crea l'intent per passare all'activity successiva dopo la pressione del pulsante
        indietro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(MultigiocatoreActivity.unoVSunoClassico) {
                    ref = database.getReference("rooms_uno_contro_uno_classico").child(ConnessioneActivity.roomName);
                }else{
                    ref = database.getReference("rooms_uno_contro_uno_powerUp").child(ConnessioneActivity.roomName);
                }
                ref.child("numero_giocatori").setValue(giocatori - 1);
                Intent i = new Intent(StanzaActivity.this, ConnessioneActivity.class);
                startActivity(i);
                finish();
            }
        });

        //Crea l'intent per passare all'activity successiva dopo la pressione del pulsante
        gioca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(MultigiocatoreActivity.unoVSunoClassico) {
                    if (ConnessioneActivity.player1) {
                        ref = database.getReference("rooms_uno_contro_uno_classico").child(ConnessioneActivity.roomName).child("player1_isPlaying");
                        ref.setValue(true);
                    } else {
                        ref = database.getReference("rooms_uno_contro_uno_classico").child(ConnessioneActivity.roomName).child("player2_isPlaying");
                        ref.setValue(true);
                    }
                    ref = null;
                    ref = database.getReference("rooms_uno_contro_uno_classico").child(ConnessioneActivity.roomName);
                    ref.child("score_player1").setValue(0);
                    ref.child("score_player2").setValue(0);
                    ref.child("playerExit").setValue(0);
                }else{
                    if (ConnessioneActivity.player1) {
                        ref = database.getReference("rooms_uno_contro_uno_powerUp").child(ConnessioneActivity.roomName).child("player1_isPlaying");
                        ref.setValue(true);
                    } else {
                        ref = database.getReference("rooms_uno_contro_uno_powerUp").child(ConnessioneActivity.roomName).child("player2_isPlaying");
                        ref.setValue(true);
                    }
                    ref = null;
                    ref = database.getReference("rooms_uno_contro_uno_powerUp").child(ConnessioneActivity.roomName);
                    ref.child("score_player1").setValue(0);
                    ref.child("score_player2").setValue(0);
                    ref.child("playerExit").setValue(0);
                }
                Intent i = new Intent(StanzaActivity.this, SchermataCaricamentoActivity.class);
                startActivity(i);
                finish();

            }
        });

        if(MultigiocatoreActivity.unoVSunoClassico) {
            ref = database.getReference("rooms_uno_contro_uno_classico").child(ConnessioneActivity.roomName);
        }else{
            ref = database.getReference("rooms_uno_contro_uno_powerUp").child(ConnessioneActivity.roomName);
        }
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    if(snapshot.exists()) {
                        giocatori = snapshot.child("numero_giocatori").getValue(Integer.class);
                        if (giocatori == 2) {
                            caricamento.setVisibility(View.GONE);
                            gioca.setEnabled(true);
                        } else {
                            caricamento.setVisibility(View.VISIBLE);
                            gioca.setEnabled(false);
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
    }
}