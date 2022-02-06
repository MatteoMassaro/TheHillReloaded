package com.example.thehillreloaded.accesso;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.thehillreloaded.DatabaseHelper;
import com.example.thehillreloaded.R;
import com.example.thehillreloaded.animazioni.AnimazioniView;


public class RegistrazioneActivity extends AnimazioniView {

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrazione);

        //Variabili
        EditText username, email, password;
        Button accountCreato;
        Button registrazione;
        DatabaseHelper myDB;

        //Trova le view tramite l'id e le assegna alle variabili
        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        registrazione = findViewById(R.id.registrazione);
        accountCreato = findViewById(R.id.accountGiàCreato);

        myDB = new DatabaseHelper(this);

        //Animazione pulsante registrati
        clickButtonAnimation(registrazione);
        clickButtonAnimation(accountCreato);

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
                        if (!controllaEsistenzaUtente) {
                            if (mail.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")) {
                                if (!controllaEsistenzaMail) {
                                    boolean datiRegistrazione = myDB.inserisciDati(nomeutente, mail, pass);
                                    if (datiRegistrazione) {
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
                finish();
            }
        });
    }
}