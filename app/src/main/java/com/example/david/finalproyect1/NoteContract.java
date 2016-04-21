package com.example.david.finalproyect1;

import android.net.Uri;

/**
 * Created by David on 18/04/2016.
 */
public class NoteContract {
    //Constants for sqlite
    public static final String TABLE_NAME ="DBNOTE";
    public static final String COLUMN_NAME_ID ="_id";
    public static final String COLUMN_NAME_TITLE ="title";
    public static final String COLUMN_NAME_CONTENT ="content";
    public static final String COLUMN_NAME_DATE ="date";

    //Constants for contentProvider

    public static final Uri URI = Uri.parse("content://com.example.david.ContentProvider/note");
    public static final String MIME_DIR ="vnd.android.cursor.dir/com.example.david.finalproyect1";
    public static final String MIME_ITEM ="vnd.android.cursor.item/com.example.david.finalproyect1";


}
