package com.example.thehillreloaded;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class LoginActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Imposta l'orientamento portrait come obbligatorio
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //Variabili
        Animation scaleUp, scaleDown;
        EditText username, password;
        TextView creaAccount;
        Button login;
        DBHelper myDB;

        //Trova le view tramite l'id e le assegna alle variabili
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        creaAccount = findViewById(R.id.creaAccount);
        login = findViewById(R.id.login);

        myDB = new DBHelper(this);

        //Setta le animazioni per la pressione dei pulsanti
        scaleUp = AnimationUtils.loadAnimation(this,R.anim.scale_up);
        scaleDown = AnimationUtils.loadAnimation(this,R.anim.scale_down);

        //Animazione pulsante login
        login.setOnTouchListener((view, motionEvent) -> {
            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                login.startAnimation(scaleDown);
            } else if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                login.startAnimation(scaleUp);
            }
            return false;
        });

        //Imposta metodo di callback quando la view viene cliccata
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nomeutente = username.getText().toString();
                String pass = password.getText().toString();

                //Controlla se l'utente abbia inserito le credenziali e ne verifica la correttezza
                if(nomeutente.equals("") || pass.equals("")){
                    Toast.makeText(LoginActivity.this,"Compila tutti i campi", Toast.LENGTH_SHORT).show();
                }else{
                    Boolean credenziali = myDB.controllaUsernamePassword(nomeutente, pass);
                    if(credenziali == true){
                        Toast.makeText(LoginActivity.this,"Benvenuto " + username.getText().toString(), Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(getApplicationContext(), MenuActivity.class);
                        startActivity(i);
                    }else {
                        Toast.makeText(LoginActivity.this,"Username o password errati", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        //Imposta metodo di callback quando la view viene cliccata
        creaAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), RegistrazioneActivity.class);
                startActivity(i);
            }
        });

    }

}