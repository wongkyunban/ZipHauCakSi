package com.wong.utils;


import java.net.URLDecoder;
import java.net.URLEncoder;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

public class DESEncryption {

    //密钥设置,可根据服务器传过来的密钥设置。达到动态的效果。
    private String secret_key = "tesecret";

    public String getSecret_key() {
        return secret_key;
    }

    public void setSecret_key(String secret_key) {
        this.secret_key = secret_key;
    }

    //解密数据
    private String decrypt(String message, String key) throws Exception {
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        DESKeySpec desKeySpec = new DESKeySpec(key.getBytes("UTF-8"));
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
        IvParameterSpec iv = new IvParameterSpec(key.getBytes("UTF-8"));
        cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);

        byte digest[] = new byte[message.length() / 2];
        for (int i = 0; i < digest.length; i++) {
            String byteString = message.substring(2 * i, 2 * i + 2);
            int byteValue = Integer.parseInt(byteString, 16);
            digest[i] = (byte) byteValue;
        }

        byte[] retByte = cipher.doFinal(digest);
        return new String(retByte);
    }

    //加密数据
    private  byte[] encrypt(String message, String key) throws Exception {
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        DESKeySpec desKeySpec = new DESKeySpec(key.getBytes("UTF-8"));
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
        IvParameterSpec iv = new IvParameterSpec(key.getBytes("UTF-8"));
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
        return cipher.doFinal(message.getBytes("UTF-8"));
    }

    private  String toHexString(byte b[]) {
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            String plainText = Integer.toHexString(0xff & b[i]);
            if (plainText.length() < 2)
                plainText = "0" + plainText;
            hexString.append(plainText);
        }
        return hexString.toString();
    }

    public  String DESEncrypt(String msg) {
        String encryt_data;
        String encryt_code;
        try {
            encryt_data = URLEncoder.encode(msg, "utf-8").toLowerCase();
            encryt_code = toHexString(encrypt(encryt_data, secret_key)).toUpperCase();
            return encryt_code;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public  String DESDecrypt(String msg) {
        String decrypt_data;
        String decrypt_result;
        try {
            decrypt_data = decrypt(msg, secret_key);
            decrypt_result = URLDecoder.decode(decrypt_data, "utf-8");
            return decrypt_result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

