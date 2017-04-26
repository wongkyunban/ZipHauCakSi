package com.wong.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Set;

/**
 * Created by waterway on 16/7/23.
 */
public class LocalStorageUtils {
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private Context context;
    public LocalStorageUtils(Context context,String name){
        this.context = context;
        sp = context.getSharedPreferences(name,0);
        editor = sp.edit();
    }
    public void insertBoolean(String key,boolean value){
        editor.putBoolean(key,value);
        editor.commit();
    }

    public void insertString(String key,String value){
        editor.putString(key,value);
        editor.commit();
    }

    public void insertFloat(String key,float value){
        editor.putFloat(key,value);
        editor.commit();
    }

    public void insertInt(String key,int value){
        editor.putInt(key,value);
        editor.commit();
    }

    public void insertBoolean(String key,long value){
        editor.putLong(key,value);
        editor.commit();
    }

    public void insertStringSet(String key,Set<String> value){
        editor.putStringSet(key,value);
        editor.commit();
    }

    public boolean selectBoolean(String key){
        return sp.getBoolean(key,false);
    }

    public float selectFloat(String key){
        return sp.getFloat(key,0.0f);
    }

    public int selectInt(String key){
        return sp.getInt(key,0);
    }

    public long selectLong(String key){
        return sp.getLong(key,0);
    }

    public String selectString(String key){
        return sp.getString(key,null);
    }

    public Set<String> selectStringSet(String key){
        return sp.getStringSet(key,null);
    }
}
