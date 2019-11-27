package com.gigaworks.tech.bible;

import android.content.Context;
import android.content.SharedPreferences;

public class AppPreferences {

    private SharedPreferences sharedPreferences;
    private Context ctx;
    private SharedPreferences.Editor editor;

    final private String SHARED_PREF_STRING = "com.example.arch1.testapplication";
    final static public String APP_BOOK_RESPONSE = "bookresponse";
    final static public String APP_LAST_READ_CHAPTER = "lastchapter";
    final static public String APP_SOUND_RESPONSE = "soundresponse";
    final static public String APP_TEXT_SIZE = "app.text.size";

    public AppPreferences(Context context) {
        ctx = context;
        sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_STRING, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public String getStringPreference(String key) {
        return sharedPreferences.getString(key, "");
    }

    public void setStringPreference(String key, String value) {
        editor.putString(key, value);
        editor.commit();
    }

    public static AppPreferences getInstance(Context context) {
        return new AppPreferences(context);
    }

    public boolean getBooleanPreference(String key) {
        return sharedPreferences.getBoolean(key, true);
    }

    public void setBooleanPreference(String key, Boolean value) {
        editor.putBoolean(key, value);
        editor.commit();
    }

    public int getIntegerPreference(String key) {
        return sharedPreferences.getInt(key,1);
    }

    public int getIntegerPreference(String key, int def) {
        return sharedPreferences.getInt(key,def);
    }

    public void setIntegerPreference(String key, int value) {
        editor.putInt(key,value);
        editor.commit();
    }

}
