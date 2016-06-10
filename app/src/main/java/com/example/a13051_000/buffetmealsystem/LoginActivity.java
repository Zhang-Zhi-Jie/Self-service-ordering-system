package com.example.a13051_000.buffetmealsystem;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Message;
<<<<<<< HEAD
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
=======
>>>>>>> zzj/master
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
 * Created by 13051_000 on 2016/4/20.
 */
public class LoginActivity extends BaseActivity {
    private ProgressDialog progressDialog;
    private Button button;
    private String strResult;
    public static final int SHOW_RESPONSE = 0;
    private boolean AccessNetworkState(){
        ConnectivityManager connManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        Log.d("data1","AccessNetworkState");
        if(connManager.getActiveNetworkInfo() != null){
            return connManager.getActiveNetworkInfo().isAvailable();
        }
        else
            return false;
    }
    private android.os.Handler handler = new android.os.Handler() {
        public void handleMessage(Message message) {
            switch (message.what) {
                case SHOW_RESPONSE:
                    Gson gson = new Gson();
                    ResultFromServer resultFromServer = gson.fromJson(strResult,ResultFromServer.class);
                    String result = resultFromServer.getNickname();
                    progressDialog.dismiss();
                    Log.d("data1",result);
                    Log.d("data1",strResult);
                    if(resultFromServer.getStatus().equals("success")) {
                        Toast.makeText(LoginActivity.this, "注册成功!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                        startActivity(intent);
                    }
                    else if(resultFromServer.getStatus().equals("2")){
                        Toast.makeText(LoginActivity.this,"用户名已存在",Toast.LENGTH_SHORT).show();
                    }
                    else
                        Toast.makeText(LoginActivity.this,"注册失败,请检查注册信息",Toast.LENGTH_SHORT).show();
            }
        }
    };
    private Button rightbutton;
    public static final int SHOW_RESPONSE = 0;
    private Button button1;
    private TextView textView1;
    private static String sResult = "";
    private ProgressDialog progressDialog;
    private String number;
    private String pwd;
    //异步消息处理；；；
    private android.os.Handler handler = new android.os.Handler(){
        public void handleMessage(Message message){
            switch (message.what){
                case SHOW_RESPONSE:
                    String response = (String) message.obj;
                    sResult = response;
                    if (!(sResult == "")) {
                        String result = "";
                        try {
                            result = Json.parseJSONWithJOSNObject(sResult);
                        } catch (Exception e) {
                            Log.d("data1", e.toString());
                        }
                        progressDialog.dismiss();
                        if (!result.equals("null") && !(result == "")) {
                            Toast.makeText(getApplicationContext(), "您的用户名为:" + Json.parseJSONWithJOSNObject(sResult), Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            LoginActivity.this.startActivity(intent);
                        } else {
                            Toast.makeText(getApplicationContext(), "信息输入错误,请重新输入", Toast.LENGTH_SHORT).show();
                        }
                    }
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        button1= (Button) findViewById(R.id.button1);
        textView1= (TextView) findViewById(R.id.textView1);
        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(LoginActivity.this,RegisterActivity.class);
                LoginActivity.this.startActivity(intent1);
            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
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
                    List<NameValuePair> params = new ArrayList<NameValuePair>();
                    params.add(new BasicNameValuePair("user", loginnum));
                    params.add(new BasicNameValuePair("password", loginpwd));
                    params.add(new BasicNameValuePair("nickname", loginnickname));
                    String strUrlPath = "http://www.loushubin.cn/register.php";
                    Log.d("data1",String.valueOf(AccessNetworkState()));
                    if(!AccessNetworkState()){
                        Toast.makeText(LoginActivity.this,"网络未连接，请先连接网络",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        sendRequest(strUrlPath, params);
                //启动等待活动
                switch (view.getId()) {
                    case R.id.button1:
                        progressDialog = new ProgressDialog(LoginActivity.this);
                        progressDialog.setTitle("正在加载...");
                        progressDialog.setMessage("Loading...");
                        progressDialog.setCancelable(true);
                        progressDialog.show();
                    }

=======
                        Log.d("data1","Result:"+sResult);
                        //将数据加入请求当中
                        number = ((EditText) findViewById(R.id.editText1)).getText().toString();
                        pwd = ((EditText) findViewById(R.id.editText2)).getText().toString();
                        boolean flag = false;
                        String nickname = "";
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("user", number);
                        params.put("password", pwd);
                        String strUrlPath = "http://www.loushubin.cn/login_user.php";
                        //调用Thread，创建新线程进行网络请求
                        sendRequest(strUrlPath, params);
                }
            }
            //启动新的线程
            private void sendRequest(final String strUrlPath, final Map<String,String> params){
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String strResult =HttpUtils.submitPostData(strUrlPath,params,"utf-8");
                        Message message = new Message();
                        message.what = SHOW_RESPONSE;
                        message.obj = strResult;
                        handler.sendMessage(message);
                    }
                }).start();
            }
        });
    }
    private void sendRequest(final String strUrlPath,final List<NameValuePair> params){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    strResult = HttpUtils.submitPostData(strUrlPath, params, "utf-8");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Message message = new Message();
                message.what = SHOW_RESPONSE;
                handler.sendMessage(message);
            }
        }).start();
    }
}
}
