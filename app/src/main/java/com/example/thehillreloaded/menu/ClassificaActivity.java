package com.example.thehillreloaded.menu;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.thehillreloaded.R;
import com.example.thehillreloaded.accesso.LoginActivity;
import com.example.thehillreloaded.animazioni.Animazioni;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ClassificaActivity extends Animazioni implements Serializable {

    //Variabili
    TextView punteggiPartite, avvisoAccesso;
    ImageView indietro, condividi;
    ListView listaPunteggi;
    Button classica, powerUp;
    FirebaseDatabase database;
    DatabaseReference myRef;
    List<String> scores;
    int bestScore, index;
    String username, modalità, classificaDisplay;
    ArrayList<String> classifica;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classifica);

        //Trova le view tramite l'id e le assegna alle variabili
        indietro = findViewById(R.id.indietro);
        condividi = findViewById(R.id.condividi);
        listaPunteggi = findViewById(R.id.listaPunteggi);
        classica = findViewById(R.id.classica);
        powerUp = findViewById(R.id.powerUp);
        punteggiPartite = findViewById(R.id.punteggiPartite);
        avvisoAccesso = findViewById(R.id.avvisoAccesso);

        //Imposta l'orientamento portrait come obbligatorio
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //Animazione pulsanti
        clickButtonAnimation(indietro);
        clickButtonAnimation(classica);
        clickButtonAnimation(powerUp);
        clickButtonAnimation(indietro);
        clickButtonAnimation(condividi);

        scores = new ArrayList<>();
        classifica = new ArrayList<>();

        //Crea l'intent per passare all'activity successiva dopo la pressione del pulsante
        indietro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ClassificaActivity.this, MenuActivity.class);
                Bundle b = ActivityOptions.makeSceneTransitionAnimation(ClassificaActivity.this).toBundle();
                startActivity(i, b);
                finish();
            }
        });

        condividi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                classifica.clear();
                index = listaPunteggi.getAdapter().getCount();
                if(modalità.equals("classica")){
                    classifica.add("CLASSIFICA PER LA MODALITÀ CLASSICA:" + "\n");
                }else{
                    classifica.add("CLASSIFICA PER LA MODALITÀ POWER-UP:" + "\n");
                }
                for(int i = 0; i < index; i++){
                    classifica.add(listaPunteggi.getItemAtPosition(i).toString() + "\n");
                }
                classificaDisplay = classifica.toString().substring(1,classifica.toString().length() - 1);
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND_MULTIPLE);
                shareIntent.putExtra(Intent.EXTRA_TEXT, classificaDisplay);
                shareIntent.setType("text/*");
                startActivity(Intent.createChooser(shareIntent, null));
            }
        });

        database = FirebaseDatabase.getInstance();

        classica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                avvisoAccesso.setText(R.string.modalità_classica);
                modalità = "classica";
                myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        scores.clear();
                        Iterable<DataSnapshot> users = dataSnapshot.getChildren();
                        for(DataSnapshot snapshot : users) {
                            if (snapshot.child("best_score_modalità_classica").exists()) {
                                bestScore = snapshot.child("best_score_modalità_classica").getValue(Integer.class);
                                username = snapshot.child("username").getValue(String.class);
                                scores.add("Punteggio: " + String.valueOf(bestScore) + "\n"+ "Username: " + username);
                                ArrayAdapter<String> adapter = new ArrayAdapter<>(ClassificaActivity.this, android.R.layout.simple_list_item_1, scores);
                                Collections.sort(scores);
                                Collections.reverse(scores);
                                adapter.notifyDataSetChanged();
                                listaPunteggi.setAdapter(adapter);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value
                        Log.w("", "Failed to read value.", error.toException());
                    }
                });
                condividi.setVisibility(View.VISIBLE);
            }
        });

        powerUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                modalità = "power-up";
                avvisoAccesso.setText(R.string.modalità_powerup);
                myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        scores.clear();
                        Iterable<DataSnapshot> users = dataSnapshot.getChildren();
                        for(DataSnapshot snapshot : users) {
                            if (snapshot.child("best_score_modalità_powerUp").exists()) {
                                bestScore = snapshot.child("best_score_modalità_powerUp").getValue(Integer.class);
                                username = snapshot.child("username").getValue(String.class);
                                scores.add("Punteggio: " + String.valueOf(bestScore) + "\n"+ "Username: " + username);
                                ArrayAdapter<String> adapter = new ArrayAdapter<>(ClassificaActivity.this, android.R.layout.simple_list_item_1, scores);
                                Collections.sort(scores);
                                Collections.reverse(scores);
                                adapter.notifyDataSetChanged();
                                listaPunteggi.setAdapter(adapter);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value
                        Log.w("", "Failed to read value.", error.toException());
                    }
                });
                condividi.setVisibility(View.VISIBLE);
            }
        });

        if (LoginActivity.currentUser != null) {
            avvisoAccesso.setText(R.string.seleziona_modalità);
            classica.setVisibility(View.VISIBLE);
            powerUp.setVisibility(View.VISIBLE);
            myRef = database.getReference();

        }
    }

}