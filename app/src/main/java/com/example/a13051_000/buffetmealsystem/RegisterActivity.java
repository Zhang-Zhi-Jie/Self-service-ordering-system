package com.example.a13051_000.buffetmealsystem;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by 13051_000 on 2016/4/26.
 * 用多线程重写加载函数2016/02/15
 */
public class RegisterActivity extends BaseActivity {
    private ProgressDialog progressDialog;
    private Button button;

    private String strResult;
    public static final int SHOW_RESPONSE = 0;
    private android.os.Handler handler = new android.os.Handler() {
        public void handleMessage(Message message) {
            switch (message.what) {
                case SHOW_RESPONSE:
                    String result = Json.parseJSONWithJOSNObject(strResult);
                    progressDialog.dismiss();
                    Toast.makeText(RegisterActivity.this, "注册成功!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
            }
        }
    };

    private Button rightbutton;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Topbar topbar= (Topbar) findViewById(R.id.topbar1);
        topbar.setOnTobarClickListener(new Topbar.topbarClickListener() {
            @Override
            public void leftClick() {

            }
            @Override
            public void rightClick() {
               Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
                RegisterActivity.this.startActivity(intent);
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
                int num1 = loginnum.length();
                int num2 = loginpwd.length();
                if(num1<8||num2<8){
                    Toast.makeText(getApplicationContext(),"输入长度不小于8位",Toast.LENGTH_SHORT).show();
                }
                else if(loginpwd.equals(againpwd)){
                    login = true;
                }
                if(login) {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("user", loginnum);
                    params.put("password", loginpwd);
                    params.put("nickname", loginnickname);
                    String strUrlPath = "http://www.loushubin.cn/register.php";
                    sendRequest(strUrlPath,params);
                    progressDialog = new ProgressDialog(RegisterActivity.this);
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
