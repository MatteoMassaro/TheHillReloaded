package com.example.thehillreloaded;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context) {
        super(context,"Login.db",null,1);
    }

    //Crea la tabella utenti nel database
    @Override
    public void onCreate(SQLiteDatabase myDB) {
    myDB.execSQL("create Table utenti(username Text primary key, email Text, password Text)");
    }

    //Controlla se la tabella utenti esista già
    @Override
    public void onUpgrade(SQLiteDatabase myDB, int i, int i1) {
    myDB.execSQL("drop Table if exists utenti");
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
        Cursor coursor = myDB.rawQuery("select * from utenti where username = ?", new String[] {username});
        if (coursor.getCount()>0){
            return true;
        }else {
            return false;
        }
    }

    //Controlla esistenza mail
    public boolean controllaEsistenzaEmail(String email) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cursor = myDB.rawQuery("select * from utenti where email = ?", new String[]{email});
        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }


        //Controlla validità password
        public boolean controllaUsernamePassword(String username, String password){
            SQLiteDatabase myDB = this.getWritableDatabase();
            Cursor coursor = myDB.rawQuery("select * from utenti where username = ? and password = ?", new String[] {username,password});
            if (coursor.getCount()>0){
                return true;
            }else {
                return false;
            }
        }
}
