/**
 * *****************************************************************************
 * Copyright 2001 - 2013 Comit. All Rights Reserved.
 * 作者：yangxihong
 * 创建日期：Jun 13, 2013 12:23:37 PM
 * 修改日期：
 * 描述：
 * ****************************************************************************
 */
package com.wong.handle;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.Calendar;

/**
 * 创建人：yangxihong
 * <p/>
 * 创建日期：Jul 17, 2013 4:17:16 PM
 * <p/>
 * 文件名称：CrashHandler.java
 * <p/>
 * 功能描述：捕捉全局异常
 */
public class CrashHandler implements UncaughtExceptionHandler {

    private static final String TAG = "CrashHandler";
    private static CrashHandler crashHandler = null;

    private CrashHandler() {
    }

    public static CrashHandler getInstance() {
        if (crashHandler == null) {
            crashHandler = new CrashHandler();
        }
        return crashHandler;
    }

    public static void handleCrash() {
        Thread.setDefaultUncaughtExceptionHandler(CrashHandler.getInstance());
    }

    @Override
    public void uncaughtException(Thread thread, Throwable exception) {
        String message = getMessage(exception);
        Log.e(TAG, message);
        writeLog(message);
    }

    /**
     * @param throwable 异常
     * @return String
     */
    public static String getMessage(Throwable throwable) {
        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        throwable.printStackTrace(printWriter);
        printWriter.close();
        return writer.toString();
    }

    public static void writeLog(String message){
        File path = new File(getBootPath()+"/NewZF/");
        if (!path.exists()) {
            path.mkdirs();
        }
        File file = new File(path, "Crash_" + Calendar.getInstance().getTime().getTime() + ".txt");
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(file, true);
            out.write(("[" + "]" + message + "\r\n\r\n").getBytes());
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    private static String getBootPath() {
       /* if (Environment.getExternalStorageState()==) {

        } else {
            return Environment.getDownloadCacheDirectory().getPath();
        }*/
        return Environment.getExternalStorageDirectory().getPath();
    }

}
