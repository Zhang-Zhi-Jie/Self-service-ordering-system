package com.example.a13051_000.buffetmealsystem;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

/**
 * Created by 13051_000 on 2016/4/20.
 */
public class RegisterActivity extends BaseActivity {
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
                    Log.d("data1","正在执行处理操作");
                    String response = (String) message.obj;
                    sResult = response;
                    if (!sResult.isEmpty()) {
                        String result = "";
                        try {
                            result = Json.parseJSONWithJOSNObject(sResult);
                        } catch (Exception e) {
                            Log.d("data1", e.toString());
                        }
                        progressDialog.dismiss();
                        if (!result.equals("null") && !result.isEmpty()) {
                            Toast.makeText(getApplicationContext(), "您的用户名为:" + Json.parseJSONWithJOSNObject(sResult), Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                            RegisterActivity.this.startActivity(intent);
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
        setContentView(R.layout.activity_register);
        button1= (Button) findViewById(R.id.button1);
        textView1= (TextView) findViewById(R.id.textView1);
        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(RegisterActivity.this,LoginActivity.class);
                RegisterActivity.this.startActivity(intent1);
            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //启动等待活动
                switch (view.getId()) {
                    case R.id.button1:
                        Log.d("data1","正在进行Progress操作");
                        progressDialog = new ProgressDialog(RegisterActivity.this);
                        progressDialog.setTitle("正在加载...");
                        progressDialog.setMessage("Loading...");
                        progressDialog.setCancelable(true);
                        progressDialog.show();
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
                        Log.d("data1","正在发送数据");
                        Log.d("data1",params.toString());
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
}
