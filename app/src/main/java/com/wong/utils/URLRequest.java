package com.wong.utils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class URLRequest{
    public static String request(String path){
        String returnStr = "";
        URL url;
        HttpURLConnection connection = null;
        try {
            url = new URL(path);
            InputStream inputStream;
            inputStream = url.openConnection().getInputStream();
            InputStreamReader inputReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufReader = new BufferedReader(inputReader);
            StringBuilder sb = new StringBuilder();
            String line = "";
            while ((line = bufReader.readLine()) != null) {
                sb.append(line);
            }
            returnStr = sb.toString();
        } catch (IOException e) {
            returnStr = "-1";
            e.printStackTrace();
        }
        return returnStr;
    }
}