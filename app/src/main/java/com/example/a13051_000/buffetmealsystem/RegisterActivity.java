package com.example.a13051_000.buffetmealsystem;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by 13051_000 on 2016/4/26.
 * 用多线程重写加载函数2016/02/15
 */
public class RegisterActivity extends BaseActivity {
    private ProgressDialog progressDialog;
<<<<<<< HEAD
    private String number;
    private String pwd;
    //网络连接检查函数:::
    private boolean AccessNetworkState(){
        ConnectivityManager connManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connManager.getActiveNetworkInfo() != null){
            return connManager.getActiveNetworkInfo().isAvailable();
        }
        else
            return false;
    }
    //异步消息处理；；；
    private android.os.Handler handler = new android.os.Handler(){
        public void handleMessage(Message message){
            switch (message.what){
                case SHOW_RESPONSE:
                    String response = (String) message.obj;
                    sResult = response;
                    if (!sResult.equals(-1)) {
                        Gson gson = new Gson();
                        String result = "";
                        ResultFromServer loginResult = null;
                        try {
                            loginResult = gson.fromJson(sResult,ResultFromServer.class);
                        } catch (Exception e) {
                            Log.d("data1", e.toString());
                        }
                        progressDialog.dismiss();
                        Log.d("data1",sResult);
                        String nick_name = "";
                        if (loginResult.getNick_name() != null) {
                            Toast.makeText(getApplicationContext(), "您的用户名为:" + loginResult.getNick_name(), Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                            RegisterActivity.this.startActivity(intent);
                        } else {
                            Toast.makeText(getApplicationContext(), "信息输入错误,请重新输入", Toast.LENGTH_SHORT).show();
                        }
                    }
=======
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
>>>>>>> zzj/master
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
<<<<<<< HEAD
                //启动等待活动
                switch (view.getId()) {
                    case R.id.button1:
                        if(AccessNetworkState()) {
                            progressDialog = new ProgressDialog(RegisterActivity.this);
                            progressDialog.setTitle("正在加载...");
                            progressDialog.setMessage("Loading...");
                            progressDialog.setCancelable(true);
                            progressDialog.show();
                            Log.d("data1", "Result:" + sResult);
                            //将数据加入请求当中
                            number = ((EditText) findViewById(R.id.editText1)).getText().toString();
                            pwd = ((EditText) findViewById(R.id.editText2)).getText().toString();
                            boolean flag = false;
                            String nickname = "";
                            List<NameValuePair> params = new ArrayList<NameValuePair>();
                            params.add(new BasicNameValuePair("user", number));
                            params.add(new BasicNameValuePair("password", pwd));
                            String strUrlPath = "http://www.loushubin.cn/login_user.php";
                            //调用Thread，创建新线程进行网络请求
                            sendRequest(strUrlPath, params);
                        }
                        else
                            Toast.makeText(RegisterActivity.this,"未连接到网络,请检查网络连接设置",Toast.LENGTH_SHORT).show();
                }
            }
            //启动新的线程
            private void sendRequest(final String strUrlPath, final List<NameValuePair> params){
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String strResult = null;
                        try {
                            strResult = HttpUtils.submitPostData(strUrlPath,params,"utf-8");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Message message = new Message();
                        message.what = SHOW_RESPONSE;
                        message.obj = strResult;
                        handler.sendMessage(message);
=======
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
>>>>>>> zzj/master
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
