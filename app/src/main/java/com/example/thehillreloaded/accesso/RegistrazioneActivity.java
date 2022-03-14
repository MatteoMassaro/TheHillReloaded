package com.example.thehillreloaded.accesso;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.thehillreloaded.R;
import com.example.thehillreloaded.animazioni.Animazioni;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class RegistrazioneActivity extends Animazioni {

    TextInputEditText regUsername;
    TextInputEditText regEmail;
    TextInputEditText regPassword;
    Button registrati, accountCreato;
    ImageView indietro;
    FirebaseDatabase database;
    DatabaseReference ref;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrazione);

        //Imposta l'orientamento portrait come obbligatorio
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //Trova le view tramite l'id e le assegna alle variabili
        regUsername = findViewById(R.id.username);
        regEmail = findViewById(R.id.email);
        regPassword = findViewById(R.id.password);
        accountCreato = findViewById(R.id.accountGiàCreato);
        registrati = findViewById(R.id.registrazione);
        indietro = findViewById(R.id.indietro);

        mAuth = FirebaseAuth.getInstance();

        database = FirebaseDatabase.getInstance();
        ref = database.getReference("account").child("accesso");

        registrati.setOnClickListener(view ->{
            createUser();
        });

        accountCreato.setOnClickListener(view ->{
            startActivity(new Intent(RegistrazioneActivity.this, LoginActivity.class));
            finish();
        });

        //Crea l'intent per passare all'activity successiva dopo la pressione del pulsante
        indietro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(RegistrazioneActivity.this, ModalitaAccessoActivity.class);
                startActivity(i);
            }
        });

        //Animazione pulsanti
        clickButtonAnimation(registrati);
        clickButtonAnimation(accountCreato);
        clickButtonAnimation(indietro);
    }

    private void createUser(){

        String username = regUsername.getText().toString();
        String email = regEmail.getText().toString();
        String password = regPassword.getText().toString();

        SharedPreferences.Editor editor = getSharedPreferences("accesso",MODE_PRIVATE).edit();
        editor.putString("username",username);
        editor.apply();

        if(TextUtils.isEmpty(username)){
            regUsername.setError("L'username non può essere vuoto");
            regUsername.requestFocus();
        }
        else if (TextUtils.isEmpty(email)){
            regEmail.setError("L'email non può essere vuota");
            regEmail.requestFocus();
        }else if (TextUtils.isEmpty(password)){
            regPassword.setError("La password non può essere vuota");
            regPassword.requestFocus();
        }else{
            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(task -> {
                if (task.isSuccessful()){
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference ref = database.getReference().child(mAuth.getCurrentUser().getUid()).child("general_data");
                    setDatabaseContent(ref);
                    Toast.makeText(RegistrazioneActivity.this, R.string.registrazione_effettuata, Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegistrazioneActivity.this, LoginActivity.class));
                    finish();
                }else{
                    Toast.makeText(RegistrazioneActivity.this, R.string.errore + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void setDatabaseContent(DatabaseReference ref) {
        ref.child("is_saved").setValue(false);
        ref.child("modalità").setValue("classica");
    }
}