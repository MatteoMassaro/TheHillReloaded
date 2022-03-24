package com.example.thehillreloaded.menu;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.thehillreloaded.R;
import com.example.thehillreloaded.accesso.ModalitaAccessoActivity;
import com.example.thehillreloaded.animazioni.Animazioni;
import com.example.thehillreloaded.gameplay.GameView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RisultatiActivity extends Animazioni {

    //variabili
    Button mostraRisultati, aggiorna, menu;
    TextView testo, testoTuoPunteggio, testoPunteggioAvversario;
    ProgressBar caricamento;
    boolean player1IsPlaying, player2IsPLaying;
    int scorePlayer1, scorePlayer2, playerExit;
    FirebaseDatabase database;
    DatabaseReference ref, ref2, ref3;

    @Override
    public void onBackPressed() {
    }

    //Chiama l'animazione all'avvio dell'activity
    @Override
    protected void onStart(){
        super.onStart();
        SharedPreferences preferenze = getSharedPreferences("salva1",MODE_PRIVATE);
        boolean b = preferenze.getBoolean("musica",true);
        if(b) {
            if (VolumeActivity.flagMusic == 0) {
                MusicPlayer.playMusic(this, R.raw.menu_music);
                ModalitaAccessoActivity.isPlayingAudio = true;
                VolumeActivity.flagMusic = 1;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_risultati);

        //Trova le view tramite l'id e le assegna alle variabili
        mostraRisultati = findViewById(R.id.mostraRisultati);
        aggiorna = findViewById(R.id.aggiorna);
        menu = findViewById(R.id.menu);
        testo = findViewById(R.id.testo);
        testoTuoPunteggio = findViewById(R.id.testoTuoPunteggio);
        testoPunteggioAvversario = findViewById(R.id.testoPunteggioAvversario);
        caricamento = findViewById(R.id.caricamento);

        //Imposta l'orientamento portrait come obbligatorio
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //Animazione pulsanti
        clickButtonAnimation(mostraRisultati);
        clickButtonAnimation(aggiorna);
        clickButtonAnimation(menu);

        database = FirebaseDatabase.getInstance();

        aggiorna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(MultigiocatoreActivity.unoVSunoClassico) {
                    ref2 = database.getReference("rooms_uno_contro_uno_classico").child(ConnessioneActivity.roomName);
                }else{
                    ref2 = database.getReference("rooms_uno_contro_uno_powerUp").child(ConnessioneActivity.roomName);
                }
                ref2.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()) {
                            player1IsPlaying = snapshot.child("player1_isPlaying").getValue(Boolean.class);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                if(MultigiocatoreActivity.unoVSunoClassico) {
                    ref3 = database.getReference("rooms_uno_contro_uno_classico").child(ConnessioneActivity.roomName);
                }else{
                    ref3 = database.getReference("rooms_uno_contro_uno_powerUp").child(ConnessioneActivity.roomName);
                }
                ref3.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()) {
                            player2IsPLaying = snapshot.child("player2_isPlaying").getValue(Boolean.class);
                            playerExit = snapshot.child("playerExit").getValue(Integer.class);
                            if (playerExit == 0) {
                                if (!player1IsPlaying && !player2IsPLaying) {
                                    caricamento.setVisibility(View.GONE);
                                    mostraRisultati.setEnabled(true);
                                }
                            } else {
                                caricamento.setVisibility(View.GONE);
                                mostraRisultati.setEnabled(true);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });


        //Crea l'intent per passare all'activity successiva dopo la pressione del pulsante
        mostraRisultati.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(MultigiocatoreActivity.unoVSunoClassico) {
                    ref = database.getReference("rooms_uno_contro_uno_classico").child(ConnessioneActivity.roomName);
                }else{
                    ref = database.getReference("rooms_uno_contro_uno_powerUp").child(ConnessioneActivity.roomName);
                }
                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()) {
                            scorePlayer1 = snapshot.child("score_player1").getValue(Integer.class);
                        }
                        ref.removeEventListener(this);
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
                            if (playerExit == 0) {
                                if (ConnessioneActivity.player1) {
                                    if (scorePlayer1 > scorePlayer2) {
                                        testo.setText(R.string.vittoria);
                                        testoTuoPunteggio.setText(getResources().getString(R.string.tuo_punteggio) + " " + String.valueOf(scorePlayer1));
                                        testoPunteggioAvversario.setText(getResources().getString(R.string.punteggio_avversario) + String.valueOf(scorePlayer2));
                                    } else if (scorePlayer1 < scorePlayer2) {
                                        testo.setText(R.string.sconfitta);
                                        testoTuoPunteggio.setText(getResources().getString(R.string.tuo_punteggio) + " " + String.valueOf(scorePlayer1));
                                        testoPunteggioAvversario.setText(getResources().getString(R.string.punteggio_avversario) + String.valueOf(scorePlayer2));
                                    } else {
                                        testo.setText(R.string.pareggio);
                                        testoTuoPunteggio.setText(getResources().getString(R.string.tuo_punteggio) + " " + String.valueOf(scorePlayer1));
                                        testoPunteggioAvversario.setText(getResources().getString(R.string.punteggio_avversario) + " " + String.valueOf(scorePlayer2));
                                    }
                                }

                                if (ConnessioneActivity.player2) {
                                    if (scorePlayer2 > scorePlayer1) {
                                        testo.setText(R.string.vittoria);
                                        testoTuoPunteggio.setText(getResources().getString(R.string.tuo_punteggio) + " " + String.valueOf(scorePlayer2));
                                        testoPunteggioAvversario.setText(getResources().getString(R.string.punteggio_avversario) + " " + String.valueOf(scorePlayer1));
                                    } else if (scorePlayer2 < scorePlayer1) {
                                        testo.setText(R.string.sconfitta);
                                        testoTuoPunteggio.setText(getResources().getString(R.string.tuo_punteggio) + " " + String.valueOf(scorePlayer2));
                                        testoPunteggioAvversario.setText(getResources().getString(R.string.punteggio_avversario) + " " + String.valueOf(scorePlayer1));
                                    } else {
                                        testo.setText(R.string.pareggio);
                                        testoTuoPunteggio.setText(getResources().getString(R.string.tuo_punteggio) + " " + String.valueOf(scorePlayer2));
                                        testoPunteggioAvversario.setText(getResources().getString(R.string.punteggio_avversario) + " " + String.valueOf(scorePlayer1));
                                    }
                                }
                            } else {
                                if (ConnessioneActivity.player1 && playerExit == 1) {
                                    testo.setText(R.string.sconfitta);
                                    testoTuoPunteggio.setText(R.string.tuo_abbandono);
                                } else if (ConnessioneActivity.player1 && playerExit == 2) {
                                    testo.setText(R.string.vittoria);
                                    testoTuoPunteggio.setText(R.string.abbandono_avversario);
                                } else if (ConnessioneActivity.player2 && playerExit == 1) {
                                    testo.setText(R.string.vittoria);
                                    testoTuoPunteggio.setText(R.string.abbandono_avversario);
                                } else {
                                    testo.setText(R.string.sconfitta);
                                    testoTuoPunteggio.setText(R.string.tuo_abbandono);
                                }
                            }
                        }
                        ref.removeEventListener(this);
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