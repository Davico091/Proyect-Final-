package com.example.david.finalproyect1;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by David on 08/04/2016.
 */
public class Util {

    public static void showMessage(Context context,String message){
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
    }
}
