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

    //methods for CRUD

    public ArrayList<Note> getDBNotes(){

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+NoteContract.TABLE_NAME, null);
        ArrayList<Note> notes = new ArrayList<>();

        while (cursor.moveToNext()){
            Note note = new Note(cursor.getInt(cursor.getColumnIndex(NoteContract.COLUMN_NAME_ID)),
                                               cursor.getString(cursor.getColumnIndex(NoteContract.COLUMN_NAME_TITLE)),
                                               cursor.getString(cursor.getColumnIndex(NoteContract.COLUMN_NAME_CONTENT)),
                                               cursor.getString(cursor.getColumnIndex(NoteContract.COLUMN_NAME_DATE)));
            notes.add(note);
        }
        db.close();

        return notes;
    }

    public int  editNote(Note note){
        Log.v("object recived  ",note.toString());
        SQLiteDatabase db = getReadableDatabase();
        ContentValues edited_note = new ContentValues();
        edited_note.put("content",note.getContent());
        edited_note.put("title", note.getTitle());
        int status= db.update(NoteContract.TABLE_NAME,edited_note,NoteContract.COLUMN_NAME_ID+" = "+note.get_id(),null);
        db.close();
        return status;
    }
    public void addNewNote(Note note) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues new_note = new ContentValues();
        new_note.put("title", note.getTitle());
        new_note.put("content", note.getContent());
        new_note.put("date",Util.parseDate(new Date()));
        db.insert(NoteContract.TABLE_NAME, null, new_note);
        db.close();
    }

    public int deleteNote(ArrayList<Note> list_items_selected){
        int count =0;
        SQLiteDatabase db = getWritableDatabase();
        for(Note note : list_items_selected){
            db.delete(NoteContract.TABLE_NAME,NoteContract.COLUMN_NAME_ID+" = "+note.get_id(), null);
            count++;
        }
        db.close();
        return count;
    }

}
