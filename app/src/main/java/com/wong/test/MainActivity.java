package com.wong.test;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.wong.utils.Loading;

import org.w3c.dom.Text;
import org.xutils.common.Callback;
import org.xutils.ex.HttpException;
import org.xutils.http.RequestParams;
import org.xutils.http.annotation.HttpRequest;
import org.xutils.x;

public class MainActivity extends AppCompatActivity {

    private InputMethodManager imm;
    private Dialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView txt = (TextView)findViewById(R.id.ret);
        final EditText eurl = (EditText)findViewById(R.id.url);

        final EditText arg = (EditText)findViewById(R.id.arg);

    Button btn = (Button)findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //隐藏键盘
                imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                dialog = Loading.createLoadingDialog(MainActivity.this,"正在发送请求...");
                dialog.show();
                // String url = "http://192.168.0.68:8081/Mobile/MobileRoadSituationBuilding/GetRoadSituationBuildingData";
                String url = eurl.getText().toString();
                RequestParams params = new RequestParams(url); // 网址(请替换成实际的网址)
               // String value = "{\"organization_id\":\"1345689\"}"+UserData.getJsonToWebService();
                String argStr = arg.getText().toString();
                String value = argStr + UserData.getJsonToWebService();

                params.addBodyParameter("data", value);    // 参数(请替换成实际的参数与值)
                x.http().post(params, new Callback.CommonCallback<String>() {

                    @Override
                    public void onCancelled(Callback.CancelledException arg0) {

                    }

                    // 注意:如果是自己onSuccess回调方法里写了一些导致程序崩溃的代码，也会回调道该方法，因此可以用以下方法区分是网络错误还是其他错误
                    // 还有一点，网络超时也会也报成其他错误，还需具体打印出错误内容比较容易跟踪查看
                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {

                        Toast.makeText(x.app(), ex.getMessage(), Toast.LENGTH_LONG).show();
                        txt.setText(ex.getMessage());
                        if (ex instanceof HttpException) { // 网络错误
                            HttpException httpEx = (HttpException) ex;
                            int responseCode = httpEx.getCode();
                            String responseMsg = httpEx.getMessage();
                            String errorResult = httpEx.getResult();
                            txt.setText(ex.getMessage());
                            // ...
                        } else { // 其他错误
                            // ...
                        }
                        dialog.dismiss();

                    }

                    // 不管成功或者失败最后都会回调该接口
                    @Override
                    public void onFinished() {

                    }

                    @Override
                    public void onSuccess(String arg0) {
                        txt.setText(arg0);
                        dialog.dismiss();
                    }
                });


            }
        });

        eurl.setSelection(eurl.getText().length());
        arg.setSelection(arg.getText().length());


    }



}
