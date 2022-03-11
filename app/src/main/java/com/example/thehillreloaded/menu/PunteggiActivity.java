package com.example.thehillreloaded.menu;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.thehillreloaded.R;
import com.example.thehillreloaded.animazioni.Animazioni;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PunteggiActivity extends Animazioni {

    //Variabili
    TextView punteggiPartite;
    ImageView indietro;
    ListView listaPunteggi;
    FirebaseDatabase database;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_punteggi);

        //Trova le view tramite l'id e le assegna alle variabili
        indietro = findViewById(R.id.indietro);
        listaPunteggi = findViewById(R.id.listaPunteggi);
        punteggiPartite = findViewById(R.id.punteggiPartite);

        //Imposta l'orientamento portrait come obbligatorio
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //Animazione pulsanti
        clickButtonAnimation(indietro);

        //Crea l'intent per passare all'activity successiva dopo la pressione del pulsante
        indietro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PunteggiActivity.this, MenuActivity.class);
                Bundle b = ActivityOptions.makeSceneTransitionAnimation(PunteggiActivity.this).toBundle();
                startActivity(i, b);
                finish();
            }
        });

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("score");
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    dataSnapshot.getChildren();
                    long index = dataSnapshot.getChildrenCount();
                    String[] value = new String[(int) index];
                    for(int x = 0; x < index; x++) {
                        value[x] = dataSnapshot.child("score" + x).getValue(String.class);
                        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, value);
                        listaPunteggi.setAdapter(arrayAdapter);
                    }
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Log.w("", "Failed to read value.", error.toException());
                }
            });
        }
    }