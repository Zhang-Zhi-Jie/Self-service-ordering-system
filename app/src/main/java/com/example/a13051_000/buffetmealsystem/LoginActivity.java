package com.example.a13051_000.buffetmealsystem;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Handler;
import java.util.logging.LogRecord;


/**
 * Created by 13051_000 on 2016/4/26.
 * 用多线程重写加载函数2016/02/15
 */
public class LoginActivity extends BaseActivity {
    private ProgressDialog progressDialog;
    private Button button;
<<<<<<< HEAD
    private String strResult;
    public static final int SHOW_RESPONSE = 0;
    private android.os.Handler handler = new android.os.Handler() {
        public void handleMessage(Message message) {
            switch (message.what) {
                case SHOW_RESPONSE:
                    String result = Json.parseJSONWithJOSNObject(strResult);
                    progressDialog.dismiss();
                    Toast.makeText(LoginActivity.this, "注册成功!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                    startActivity(intent);
            }
        }
    };
=======
    private Button rightbutton;
>>>>>>> zzj/master

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Topbar topbar= (Topbar) findViewById(R.id.topbar1);
        topbar.setOnTobarClickListener(new Topbar.topbarClickListener() {
            @Override
            public void leftClick() {

            }

            @Override
            public void rightClick() {
               Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
                LoginActivity.this.startActivity(intent);
            }
        });
        button= (Button) findViewById(R.id.loginbutton1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String loginnickname=((EditText)findViewById(R.id.logineditText4)).getText().toString();
                String loginnum=((EditText)findViewById(R.id.logineditText1)).getText().toString();
                String loginpwd=((EditText)findViewById(R.id.logineditText2)).getText().toString();
                String againpwd=((EditText)findViewById(R.id.logineditText3)).getText().toString();
                boolean login=false;
                if(loginpwd.equals(againpwd)){
                    login = true;
                }
                if(login) {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("user", loginnum);
                    params.put("password", loginpwd);
                    params.put("nickname", loginnickname);
                    String strUrlPath = "http://www.loushubin.cn/register.php";
                    sendRequest(strUrlPath,params);
                    progressDialog = new ProgressDialog(LoginActivity.this);
                    progressDialog.setTitle("正在加载...");
                    progressDialog.setMessage("Loading...");
                    progressDialog.setCancelable(true);
                    progressDialog.show();
                }
                    else{
                        Toast.makeText(getApplicationContext(),"密码输入不匹配，请重新输入",Toast.LENGTH_SHORT).show();
                    }

            }
        });
    }
    private void sendRequest(final String strUrlPath,final Map<String,String> params){
        new Thread(new Runnable() {
            @Override
            public void run() {
                strResult = HttpUtils.submitPostData(strUrlPath, params, "utf-8");
                Message message = new Message();
                message.what = SHOW_RESPONSE;
                handler.sendMessage(message);
            }
        }).start();
    }

}
