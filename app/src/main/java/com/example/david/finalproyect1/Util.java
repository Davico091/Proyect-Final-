package com.example.david.finalproyect1;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by David on 08/04/2016.
 */
public class Util {
    public static final String DBNAME="DBNOTE";
    public static void showMessage(Context context,String message){
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
    }
    public static String parseDate(String inputDate){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/YYYY");
        Date date = null;
        try{
             date = sdf.parse(inputDate);
        }
        catch (Exception e){
            Log.v("Error parse date","Error parse date");
        }
    return sdf.format(date);
    }
}
