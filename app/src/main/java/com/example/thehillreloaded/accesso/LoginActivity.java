package com.example.thehillreloaded.accesso;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.thehillreloaded.R;
import com.example.thehillreloaded.animazioni.Animazioni;
import com.example.thehillreloaded.menu.MenuActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;


public class LoginActivity extends Animazioni {


    TextInputEditText loginEmail;
    TextInputEditText loginPassword;
    Button login, creaAccount;
    ImageView indietro;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        loginEmail = findViewById(R.id.email);
        loginPassword = findViewById(R.id.password);
        creaAccount = findViewById(R.id.creaAccount);
        login = findViewById(R.id.login);
        indietro = findViewById(R.id.indietro);

        mAuth = FirebaseAuth.getInstance();

        login.setOnClickListener(view -> {
            loginUser();
        });
        creaAccount.setOnClickListener(view ->{
            startActivity(new Intent(LoginActivity.this, RegistrazioneActivity.class));
            finish();
        });

        //Crea l'intent per passare all'activity successiva dopo la pressione del pulsante
        indietro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this, ModalitaAccessoActivity.class);
                startActivity(i);
            }
        });

        //Animazione pulsanti
        clickButtonAnimation(login);
        clickButtonAnimation(creaAccount);
        clickButtonAnimation(indietro);
    }

    private void loginUser(){

        String email = Objects.requireNonNull(loginEmail.getText()).toString();
        String password = Objects.requireNonNull(loginPassword.getText()).toString();

        if (TextUtils.isEmpty(email)){
            loginEmail.setError("L'email non può essere vuota");
            loginEmail.requestFocus();
        }else if (TextUtils.isEmpty(password)){
            loginPassword.setError("La password non può essere vuota");
            loginPassword.requestFocus();
        }else{
            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(LoginActivity.this, R.string.accesso_effettuato, Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this, MenuActivity.class));
                        finish();
                    }else{
                        Toast.makeText(LoginActivity.this, R.string.errore + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

}