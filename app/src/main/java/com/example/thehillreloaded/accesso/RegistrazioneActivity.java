package com.example.thehillreloaded.accesso;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.thehillreloaded.R;
import com.example.thehillreloaded.animazioni.Animazioni;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class RegistrazioneActivity extends Animazioni {

    TextInputEditText regUsername;
    TextInputEditText regEmail;
    TextInputEditText regPassword;
    Button registrati, accountCreato;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrazione);

        regUsername = findViewById(R.id.username);
        regEmail = findViewById(R.id.email);
        regPassword = findViewById(R.id.password);
        accountCreato = findViewById(R.id.accountGiàCreato);
        registrati = findViewById(R.id.registrazione);

        mAuth = FirebaseAuth.getInstance();

        registrati.setOnClickListener(view ->{
            createUser();
        });

        accountCreato.setOnClickListener(view ->{
            startActivity(new Intent(RegistrazioneActivity.this, LoginActivity.class));
            finish();
        });
    }

    private void createUser(){

        String username = regUsername.getText().toString();
        String email = regEmail.getText().toString();
        String password = regPassword.getText().toString();

        if (TextUtils.isEmpty(email)){
            regEmail.setError("L'email non può essere vuota");
            regEmail.requestFocus();
        }else if (TextUtils.isEmpty(password)){
            regPassword.setError("La password non può essere vuota");
            regPassword.requestFocus();
        }else{
            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(RegistrazioneActivity.this, R.string.registrazione_effettuata, Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(RegistrazioneActivity.this, LoginActivity.class));
                    }else{
                        Toast.makeText(RegistrazioneActivity.this, R.string.errore + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}