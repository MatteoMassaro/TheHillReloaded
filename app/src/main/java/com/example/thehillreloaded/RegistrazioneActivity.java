package com.example.thehillreloaded;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;



public class RegistrazioneActivity extends Animazioni {

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrazione);

        //Variabili
        EditText username, email, password;
        TextView accountCreato;
        Button registrazione;
        DBHelper myDB;

        //Trova le view tramite l'id e le assegna alle variabili
        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        registrazione = findViewById(R.id.registrazione);
        accountCreato = findViewById(R.id.accountGiàCreato);

        myDB = new DBHelper(this);

        //Animazione pulsante registrati
        clickButtonAnimation(registrazione);

        //Imposta metodo di callback quando la view viene cliccata
        registrazione.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nomeutente = username.getText().toString();
                String mail = email.getText().toString();
                String pass = password.getText().toString();

                //Controlla se l'utente abbia inserito le credenziali e ne verifica la correttezza
                if (nomeutente.equals("") || mail.equals("") || pass.equals("")) {
                    Toast.makeText(RegistrazioneActivity.this, "Compila tutti i campi", Toast.LENGTH_SHORT).show();
                } else {
                    boolean controllaEsistenzaUtente = myDB.controllaEsistenzaUsername(nomeutente);
                    boolean controllaEsistenzaMail = myDB.controllaEsistenzaEmail(mail);
                    if(nomeutente.matches("^(?=.{1,15}$)(?![_.])(?!.*[_.]{2})[a-zA-Z0-9._]+(?<![_.])$")) {
                        if (controllaEsistenzaUtente == false) {
                            if (mail.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")) {
                                if (controllaEsistenzaMail == false) {
                                    boolean datiRegistrazione = myDB.inserisciDati(nomeutente, mail, pass);
                                    if (datiRegistrazione == true) {
                                        Toast.makeText(RegistrazioneActivity.this, "Registrazione effettuata", Toast.LENGTH_SHORT).show();
                                        Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                                        startActivity(i);
                                    } else {
                                        Toast.makeText(RegistrazioneActivity.this, "Registrazione fallita", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(RegistrazioneActivity.this, "Email già esistente", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(RegistrazioneActivity.this, "Email non valida", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(RegistrazioneActivity.this, "Nome utente già in uso", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(RegistrazioneActivity.this, "Nome utente non valido", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        //Imposta metodo di callback quando la view viene cliccata
        accountCreato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(i);
            }
        });
    }
}