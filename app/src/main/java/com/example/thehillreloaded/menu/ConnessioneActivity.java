package com.example.thehillreloaded.menu;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.thehillreloaded.R;
import com.example.thehillreloaded.accesso.LoginActivity;
import com.example.thehillreloaded.animazioni.Animazioni;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class ConnessioneActivity extends Animazioni {

    //variabili
    Button  cerca, creaStanza;
    ImageView indietro;
    ListView listaDispositivi;
    List<String> roomsList;
    TextView status;
    String playerName = "";
    public static String roomName = "";
    FirebaseDatabase database;
    DatabaseReference ref, roomRef, roomsRef;
    int giocatori;
    public static boolean player1, player2 = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connessione);

        //Trova le view tramite l'id e le assegna alle variabili
        listaDispositivi = findViewById(R.id.listaDispositivi);
        status = findViewById(R.id.status);
        cerca =  findViewById(R.id.cerca);
        creaStanza =  findViewById(R.id.creaStanza);
        indietro =  findViewById(R.id.indietro);

        //Imposta l'orientamento portrait come obbligatorio
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //Animazione pulsanti
        clickButtonAnimation(cerca);
        clickButtonAnimation(creaStanza);
        clickButtonAnimation(indietro);

        roomsList = new ArrayList<>();

        if(LoginActivity.currentUser != null) {
            cerca.setVisibility(View.VISIBLE);
            creaStanza.setVisibility(View.VISIBLE);
            status.setText(R.string.cerca_giocatori);
            database = FirebaseDatabase.getInstance();
            ref = database.getReference(LoginActivity.currentUser);
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    playerName = dataSnapshot.child("username").getValue(String.class);
                    ref.removeEventListener(this);
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Log.w("", "Failed to read value.", error.toException());
                }
            });

        }else{
            status.setText(R.string.accedi_per_giocare);
            cerca.setVisibility(View.GONE);
            creaStanza.setVisibility(View.GONE);
        }

        //Crea l'intent per passare all'activity successiva dopo la pressione del pulsante
        cerca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                status.setText(R.string.cercando_giocatori);
                addRoomsEventListener();
            }
        });

        //Crea l'intent per passare all'activity successiva dopo la pressione del pulsante
        creaStanza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                player1 = true;
                player2 = false;
                roomName = playerName;
                roomRef = database.getReference("rooms").child(roomName).child("player1");
                addRoomEventListener();
                roomRef.setValue(playerName);
                roomRef = database.getReference("rooms").child(roomName).child("numero_giocatori");
                roomRef.setValue(1);
            }
        });

        listaDispositivi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                player1 = false;
                player2 = true;
                roomName = roomsList.get(i);
                roomRef = database.getReference("rooms").child(roomName).child("player2");
                addRoomEventListener();
                roomRef.setValue(playerName);
                roomRef = database.getReference("rooms").child(roomName).child("numero_giocatori");
                roomRef.setValue(2);
            }
        });


        //Crea l'intent per passare all'activity successiva dopo la pressione del pulsante
        indietro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ConnessioneActivity.this, MultigiocatoreActivity.class);
                startActivity(i);
                finish();
            }
        });

    }

    private void addRoomEventListener(){
        roomRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Intent i = new Intent(ConnessioneActivity.this, StanzaActivity.class);
                startActivity(i);
                roomRef.removeEventListener(this);
                finish();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void addRoomsEventListener(){
        roomsRef = database.getReference("rooms");
        roomsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                roomsList.clear();
                Iterable<DataSnapshot> rooms = snapshot.getChildren();
                for(DataSnapshot dataSnapshot : rooms) {
                    if(dataSnapshot.child("numero_giocatori").exists()) {
                        giocatori = dataSnapshot.child("numero_giocatori").getValue(Integer.class);
                    }
                    if (!dataSnapshot.getKey().equals(playerName) && giocatori == 1) {
                        roomsList.add(dataSnapshot.getKey());

                        ArrayAdapter<String> adapter = new ArrayAdapter<>(ConnessioneActivity.this, android.R.layout.simple_list_item_1, roomsList);
                        adapter.notifyDataSetChanged();
                        listaDispositivi.setAdapter(adapter);
                    }
                }
                if(!roomsList.isEmpty()){
                    status.setText(R.string.seleziona_giocatore);
                }else{
                    status.setText(R.string.nessun_giocatore);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
