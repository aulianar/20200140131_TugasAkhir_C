package com.example.parkingapp.helper;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.parkingapp.R;

public class CommonHelper {
    public static final CommonHelper shared = new CommonHelper();

    public void setPrefs(Context context, String key, String value) {
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key, value);
        editor.commit();
        Log.i("main", "configured");
    }

    public String getPrefs(Context context, String key) {
        SharedPreferences sharedPref = ((Activity) context).getPreferences(Context.MODE_PRIVATE);
        Log.i("main", sharedPref.getString(key, ""));
        return sharedPref.getString(key, "");
    }
}
