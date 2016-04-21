package com.example.david.finalproyect1;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

import java.net.URI;
import java.util.concurrent.RecursiveTask;

/**
 * Created by USUARIO on 20/04/2016.
 */
public class MyContentProvider extends ContentProvider {

    private NoteSQLiteHelper sqLiteHelper;

    private static final UriMatcher URI_MATCHER;

    private static final int NOTE = 1;
    private static final int NOTE_ID = 2;

    static {
        URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
        URI_MATCHER.addURI(NoteContract.URI.getAuthority(),NoteContract.URI.getPath(),NOTE);
        URI_MATCHER.addURI(NoteContract.URI.getAuthority(),NoteContract.URI.getPath()+"/#",NOTE_ID);
    }


    @Override
    public boolean onCreate() {
        sqLiteHelper = NoteSQLiteHelper.getInstance(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        final int match = URI_MATCHER.match(uri);
        if(match!=UriMatcher.NO_MATCH){
            if(match==NOTE_ID){
                selection = "_id= "+uri.getLastPathSegment();
            }
            return sqLiteHelper.getReadableDatabase().query(NoteContract.TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
        }
        return null;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        switch (URI_MATCHER.match(uri)){
            case NOTE :
                return NoteContract.MIME_DIR;
            case NOTE_ID :
                return NoteContract.MIME_ITEM;
        }
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        if(URI_MATCHER.match(uri)==NOTE){
            final long id = sqLiteHelper.getWritableDatabase().insert(NoteContract.TABLE_NAME,null,values);
            return id != -1 ? ContentUris.withAppendedId(uri,id):null;
        }
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final int match = URI_MATCHER.match(uri);
        if(match!=UriMatcher.NO_MATCH){
            if(match==NOTE_ID){
                selection = "_id= "+uri.getLastPathSegment();
            }
            return  sqLiteHelper.getWritableDatabase().delete(NoteContract.TABLE_NAME, "_id =" + selection, null);
        }
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        final int match = URI_MATCHER.match(uri);
        if(match!=UriMatcher.NO_MATCH){
            if(match==NOTE_ID){
                selection = "_id= "+uri.getLastPathSegment();
            }
            return sqLiteHelper.getWritableDatabase().update(NoteContract.TABLE_NAME, values, "_id="+selection, null);
        }
        return 0;
    }
}
