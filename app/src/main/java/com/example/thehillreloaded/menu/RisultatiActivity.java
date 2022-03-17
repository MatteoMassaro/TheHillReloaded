package com.example.thehillreloaded.menu;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.thehillreloaded.R;
import com.example.thehillreloaded.animazioni.Animazioni;
import com.example.thehillreloaded.gameplay.GameView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RisultatiActivity extends Animazioni {

    //variabili
    Button mostraRisultati, menu;
    TextView testo;
    boolean player1IsPlaying, player2IsPLaying, mostraRisultatiPlayer1, mostraRisultatiPlayer2;
    int scorePlayer1, scorePlayer2;
    FirebaseDatabase database;
    DatabaseReference ref, ref2;

    @Override
    public void onBackPressed() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_risultati);

        //Trova le view tramite l'id e le assegna alle variabili
        mostraRisultati = findViewById(R.id.mostraRisultati);
        menu = findViewById(R.id.menu);
        testo = findViewById(R.id.testo);

        //Imposta l'orientamento portrait come obbligatorio
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //Animazione pulsanti
        clickButtonAnimation(mostraRisultati);
        clickButtonAnimation(menu);

        database = FirebaseDatabase.getInstance();

        if(MultigiocatoreActivity.unoVSunoClassico) {
            ref = database.getReference("rooms_uno_contro_uno_classico").child(ConnessioneActivity.roomName);
        }else{
            ref = database.getReference("rooms_uno_contro_uno_powerUp").child(ConnessioneActivity.roomName);
        }
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    player1IsPlaying = snapshot.child("player1_isPlaying").getValue(Boolean.class);
                    ref.removeEventListener(this);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        if(MultigiocatoreActivity.unoVSunoClassico) {
            ref2 = database.getReference("rooms_uno_contro_uno_classico").child(ConnessioneActivity.roomName);
        }else{
            ref2 = database.getReference("rooms_uno_contro_uno_powerUp").child(ConnessioneActivity.roomName);
        }
        ref2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    player2IsPLaying = snapshot.child("player2_isPlaying").getValue(Boolean.class);
                    ref.removeEventListener(this);
                    if (GameView.playerExit == 0) {
                        if (!player1IsPlaying && !player2IsPLaying) {
                            mostraRisultati.setEnabled(true);
                        }
                    } else {
                        mostraRisultati.setEnabled(true);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        //Crea l'intent per passare all'activity successiva dopo la pressione del pulsante
        mostraRisultati.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()) {
                            scorePlayer1 = snapshot.child("score_player1").getValue(Integer.class);
                            ref.removeEventListener(this);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()) {
                            scorePlayer2 = snapshot.child("score_player2").getValue(Integer.class);
                            ref.removeEventListener(this);
                            if (GameView.playerExit == 0) {
                                if (ConnessioneActivity.player1) {
                                    if (scorePlayer1 > scorePlayer2) {
                                        testo.setText(R.string.vittoria);
                                    } else if (scorePlayer1 < scorePlayer2) {
                                        testo.setText(R.string.sconfitta);
                                    } else {
                                        testo.setText(R.string.pareggio);
                                    }
                                }

                                if (ConnessioneActivity.player2) {
                                    if (scorePlayer2 > scorePlayer1) {
                                        testo.setText(R.string.vittoria);
                                    } else if (scorePlayer2 < scorePlayer1) {
                                        testo.setText(R.string.sconfitta);
                                    } else {
                                        testo.setText(R.string.pareggio);
                                    }
                                }
                            } else {
                                if (ConnessioneActivity.player1 && GameView.playerExit == 1) {
                                    testo.setText(R.string.sconfitta);
                                } else if (ConnessioneActivity.player1 && GameView.playerExit == 2) {
                                    testo.setText(R.string.vittoria);
                                } else if (ConnessioneActivity.player2 && GameView.playerExit == 1) {
                                    testo.setText(R.string.vittoria);
                                } else {
                                    testo.setText(R.string.sconfitta);
                                }
                            }
                        }
                        menu.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });

        //Crea l'intent per passare all'activity successiva dopo la pressione del pulsante
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(RisultatiActivity.this, MenuActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
}