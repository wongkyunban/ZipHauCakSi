package com.wong.utils;

import android.content.Context;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Properties;

public class ConfigUtils {

	public static Properties loadConfig(Context context, String file) {
		Properties properties = new Properties();
		try {
			InputStream s = context.getAssets().open(file);
			properties.load(s);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return properties;
	}

	public void saveConfig(Context context, String file, Properties properties) {
		try {
			FileOutputStream s = new FileOutputStream(file, false);
			properties.store(s, "");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
