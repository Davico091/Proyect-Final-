package com.example.david.finalproyect1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by David on 11/04/2016.
 */
public class NoteSQLiteHelper extends SQLiteOpenHelper {

    String sqlCreate ="CREATE TABLE DBNOTE (id INTEGER PRIMARY KEY AUTOINCREMENT,title TEXT,content TEXT)";


    public NoteSQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sqlCreate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS DBNOTE");
        db.execSQL(sqlCreate);
    }
}
