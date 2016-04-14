package com.example.david.finalproyect1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by David on 11/04/2016.
 */
public class NoteSQLiteHelper extends SQLiteOpenHelper {
    private static NoteSQLiteHelper instance;
    String sqlCreate ="CREATE TABLE DBNOTE (id INTEGER PRIMARY KEY AUTOINCREMENT,title TEXT,content TEXT,date TEXT)";

    public static NoteSQLiteHelper getInstance(final Context context){
        if(instance==null){
            instance = new NoteSQLiteHelper(context.getApplicationContext());
        }
        return instance;
    }

    public NoteSQLiteHelper(Context context) {
        super(context, Util.DBNAME, null, 2);
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

    //methods for CRUD

    public ArrayList<Note> getDBNotes(){

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM DBNOTE", null);
        ArrayList<Note> notes = new ArrayList<>();

        while (cursor.moveToNext()){
            Note note = new Note(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3));
            notes.add(note);
        }
        db.close();

        return notes;
    }

    public void editNote(Note note){
        SQLiteDatabase db = getReadableDatabase();
        ContentValues edited_note = new ContentValues();
        edited_note.put("content",note.getContent());
        edited_note.put("title", note.getTitle());
        int status= db.update(Util.DBNAME,edited_note,"id = "+note.getId(),null);
        db.close();
    }
    public void addNewNote(Note note) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues new_note = new ContentValues();
        new_note.put("title", note.getTitle());
        new_note.put("content", note.getContent());
        new_note.put("date",Util.parseDate(new Date()));
        db.insert("DBNOTE", null, new_note);
        db.close();
    }

    public int deleteNote(ArrayList<Note> list_items_selected){
        int count =0;
        SQLiteDatabase db = getWritableDatabase();
        for(Note note : list_items_selected){
            db.delete(Util.DBNAME, "id="+note.getId(), null);
            count++;
        }
        db.close();
        return count;
    }

}
