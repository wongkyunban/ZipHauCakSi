package com.wong.test;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.wong.handle.QuitHandler;
import com.wong.utils.JSONTools;
import com.wong.utils.Loading;
import com.wong.utils.MD5Utils;
import com.wong.utils.URLRequest;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class LoginActivity  extends Activity {

    private EditText zoengHou, matMaa;
    private Button btn_login;
    private JSONObject json;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        //透明状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //透明导航栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        //获取网络配置  --internet --local
        this.json = this.getConfig("internet");

        this.zoengHou = (EditText) findViewById(R.id.zoengHou);
        this.matMaa = (EditText) findViewById(R.id.matMaa);
        this.btn_login = (Button) findViewById(R.id.btn_login);

        //手机品牌
        UserData.setCellBrand(android.os.Build.BRAND);
        //android版本
        UserData.setAndroidVersion(android.os.Build.VERSION.RELEASE);
        //手机型号
        UserData.setCellModel(android.os.Build.MODEL);
        // 登录
        btn_login.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                try{
                    LoginTask task = new LoginTask();
                    task.execute(json.getString("loginURL"),zoengHou.getText().toString(),matMaa.getText().toString());

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });




    }

    //获取网络配置
    private JSONObject getConfig(String type){

        switch (type){
            case "internet":

                return JSONTools.getJSONObject(LoginActivity.this,"internetConfig.json");

            case "local":
                return JSONTools.getJSONObject(LoginActivity.this,"localConfig.json");
            default:
                break;
        }

        return null;
    }



    //返回键事件
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // 如果是返回键,直接返回到桌面
        if (keyCode == KeyEvent.KEYCODE_BACK
                || keyCode == KeyEvent.KEYCODE_HOME) {
            new QuitHandler().ExitApp(LoginActivity.this);
        }

        return super.onKeyDown(keyCode, event);
    }






    //登录异步线程
    class LoginTask extends AsyncTask<String, Integer , String>{

        private Dialog dialog;

        //该方法非在主线程运行，可进行耗时操作，不可更新UI界面，其他方法为主线程运行
        //params为execute输入的参数
        @Override
        protected String doInBackground(String... params) {
            String result = "登录成功！";

            try {
                String userName = java.net.URLEncoder.encode(params[1], "UTF-8");
                String password = java.net.URLEncoder.encode(
                        MD5Utils.md5To32(MD5Utils.md5To32(params[2])),
                        "UTF-8");
                result = URLRequest.request(params[0] + "/Account/AndroidLoginByCId?userName=" + userName + "&password=" + password + "&cid=cid&appid=appid&appKey=appKey&SoftwareVersion=test&EquipmentModel=test&SystemVersion=test&EquipmentBrand=test&timestamp="+UserData.getCryptoCurrentTime());
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                result = "用户名或密码输入错误";
            }

            return result;//将调用onPostExecute，并将result传给该回调方法
        }

        @Override
        protected void onPreExecute() {//该回调方法执行完毕后，将会调用doInBackground
            dialog = Loading.createLoadingDialog(LoginActivity.this,"正在登录...");
            dialog.show();

        }

        @Override
        protected void onPostExecute(String result) {//doInBackground结束后回调该方法，结束。
            dialog.dismiss();

            if(result.contains("UserId")){
                Toast.makeText(LoginActivity.this,"登录成功！",Toast.LENGTH_SHORT).show();
                String[] rs = result.split("#");


                    UserData.setUserId(Integer.parseInt(rs[1]));
                    UserData.setOrgId(Integer.parseInt(rs[2]));
                    UserData.setRoleId(Integer.parseInt(rs[3]));
                    UserData.setLoginUser(rs[4]);
                    UserData.setOrgName(rs[5]);
                    UserData.setModuleName(rs[6]);
                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                LoginActivity.this.startActivity(intent);
            }else{
                Toast.makeText(LoginActivity.this,"用户名或密码输入错误",Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected void onProgressUpdate(Integer... values) {//通知UI界面更新
        }

    }


}

