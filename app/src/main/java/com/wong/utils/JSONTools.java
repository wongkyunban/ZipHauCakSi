package com.wong.utils;

import android.content.Context;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.InputStream;

public class JSONTools{
    public static JSONObject getJSONObject(Context context,String fileName){
        try{
            InputStream is = context.getAssets().open(fileName);
            int size = is.available();
            byte[] buf = new byte[size];
            is.read(buf);
            is.close();
            String txt = new String(buf);
            JSONObject json = new JSONObject(txt);
            return json;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

}