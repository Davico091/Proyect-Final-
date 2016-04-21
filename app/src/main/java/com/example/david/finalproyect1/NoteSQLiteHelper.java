package com.example.david.finalproyect1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by David on 11/04/2016.
 */
public class NoteSQLiteHelper extends SQLiteOpenHelper {

    private static NoteSQLiteHelper instance;

    String sqlCreate ="CREATE TABLE "+NoteContract.TABLE_NAME+" " +
            "("+NoteContract.COLUMN_NAME_ID+" INTEGER PRIMARY KEY AUTOINCREMENT," +
            NoteContract.COLUMN_NAME_TITLE+" TEXT," +
            NoteContract.COLUMN_NAME_CONTENT+" TEXT," +
            NoteContract.COLUMN_NAME_DATE+" TEXT)";

    public static NoteSQLiteHelper getInstance(final Context context){
        if(instance==null){
            instance = new NoteSQLiteHelper(context.getApplicationContext());
        }
        return instance;
    }

    public NoteSQLiteHelper(Context context) {
        super(context,NoteContract.TABLE_NAME, null, 3);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sqlCreate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+NoteContract.TABLE_NAME);
        db.execSQL(sqlCreate);
    }

    //methods for CRU




}
