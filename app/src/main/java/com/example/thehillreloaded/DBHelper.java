package com.example.thehillreloaded;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Login.db";
    private static final int DATABASE_VERSION = 1;
    // Creazione del database
    private static final String DATABASE_CREATE = "CREATE TABLE utenti(username Text primary key, email Text, password Text)";
    // Costruttore
    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    // Questo metodo viene chiamato durante la creazione del database
    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }
    // Questo metodo viene chiamato durante l'upgrade del database
    @Override
    public void onUpgrade( SQLiteDatabase database, int oldVersion, int newVersion ) {
        database.execSQL("DROP TABLE IF EXISTS utenti");
        onCreate(database);
    }

    //Inserisce i dati dell'utente nel database
    public boolean inserisciDati(String username, String email, String password){
        SQLiteDatabase myDB = this.getWritableDatabase();
        ContentValues valori = new ContentValues();
        valori.put("username", username);
        valori.put("email", email);
        valori.put("password", password);
        long result = myDB.insert("utenti", null, valori);

        if(result == -1){
            return false;
        }else{
            return true;
        }
    }

    //Controlla esistenza username
    public boolean controllaEsistenzaUsername(String username){
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor coursor = myDB.rawQuery("SELECT * FROM utenti WHERE username = ?", new String[] {username});
        if (coursor.getCount()>0){
            return true;
        }else {
            return false;
        }
    }

    //Controlla esistenza mail
    public boolean controllaEsistenzaEmail(String email) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cursor = myDB.rawQuery("SELECT * FROM utenti WHERE email = ?", new String[]{email});
        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }


    //Controlla validità password
    public boolean controllaUsernamePassword(String username, String password){
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor coursor = myDB.rawQuery("SELECT * FROM utenti WHERE username = ? and password = ?", new String[] {username,password});
        if (coursor.getCount()>0){
            return true;
        }else {
            return false;
        }
    }
}