package com.jixin.towerofhanoi;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;


public class Utils {
    private static SharedPreferences sp;


    public static SharedPreferences getSp(Context context){
        if(sp==null){
            sp= context.getSharedPreferences(Constants.SharedPreferenceName, Context.MODE_PRIVATE);
        }
        return  sp;
    }
    private Drawable drawableRed;
    private Drawable drawableWhite;

}
