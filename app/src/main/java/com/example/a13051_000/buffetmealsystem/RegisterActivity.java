package com.example.a13051_000.buffetmealsystem;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.Gson;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 13051_000 on 2016/4/20.
 */
public class RegisterActivity extends BaseActivity {
    private Button button;
    private String strResult;

    private boolean AccessNetworkState() {
        ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        Log.d("data1", "AccessNetworkState");
        if (connManager.getActiveNetworkInfo() != null) {
            return connManager.getActiveNetworkInfo().isAvailable();
        } else
            return false;
    }

    private Button rightbutton;
    public static final int SHOW_RESPONSE = 0;
    private Button button1;
    private ImageView imageView;
    private static String sResult = "";
    private ProgressDialog progressDialog;
    private String number;
    private String pwd;
    //异步消息处理；；；
    private android.os.Handler handler = new android.os.Handler() {
        public void handleMessage(Message message) {
            switch (message.what) {
                case SHOW_RESPONSE:
                    Gson gson = new Gson();
                    ResultFromServer resultFromServer = gson.fromJson(strResult, ResultFromServer.class);
                    String result = resultFromServer.getNickname();
                    progressDialog.dismiss();
                    Log.d("data1", result);
                    Log.d("data1", strResult);
                    if (resultFromServer.getStatus().equals("success")) {
                        Toast toast = Toast.makeText(RegisterActivity.this, "注册成功!", Toast.LENGTH_SHORT);
                        View view = toast.getView();
                        view.setBackgroundColor(Color.parseColor("#FF8C00"));
                        toast.setView(view);
                        toast.show();

                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(intent);
                    } else if (resultFromServer.getStatus().equals("2")) {
                        Toast toast = Toast.makeText(RegisterActivity.this, "用户名已存在", Toast.LENGTH_SHORT);
                        View view = toast.getView();
                        view.setBackgroundColor(Color.parseColor("#FF8C00"));
                        toast.setView(view);
                        toast.show();

                    } else {
                        Toast toast = Toast.makeText(RegisterActivity.this, "注册失败,请检查注册信息", Toast.LENGTH_SHORT);
                        View view = toast.getView();
                        view.setBackgroundColor(Color.parseColor("#FF8C00"));
                        toast.setView(view);
                        toast.show();
                    }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.activity_register_menu);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener(){

            public boolean onMenuItemClick(MenuItem item){
                int menuItemId = item.getItemId();
                if(menuItemId == R.id.back){
                    Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                    RegisterActivity.this.startActivity(intent);
                }
                return true;
            }
        });

        button1 = (Button) findViewById(R.id.button1);
        button1 = (Button) findViewById(R.id.loginbutton1) ;
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String loginnickname = ((EditText) findViewById(R.id.logineditText4)).getText().toString();
                String loginnum = ((EditText) findViewById(R.id.logineditText1)).getText().toString();
                String loginpwd = ((EditText) findViewById(R.id.logineditText2)).getText().toString();
                String againpwd = ((EditText) findViewById(R.id.logineditText3)).getText().toString();
                boolean login = false;
                if (loginpwd.equals(againpwd)) {
                    login = true;
                }
                if (login) {
                    List<NameValuePair> params = new ArrayList<NameValuePair>();
                    params.add(new BasicNameValuePair("user", loginnum));
                    params.add(new BasicNameValuePair("password", loginpwd));
                    params.add(new BasicNameValuePair("nickname", loginnickname));
                    String strUrlPath = "http://www.loushubin.cn/register.php";
                    Log.d("data1", String.valueOf(AccessNetworkState()));
                    if (!AccessNetworkState()) {
                        Toast toast = Toast.makeText(RegisterActivity.this, "网络未连接，请先连接网络", Toast.LENGTH_SHORT);
                        View view1 = toast.getView();
                        view1.setBackgroundColor(Color.parseColor("#FF8C00"));
                        toast.setView(view1);
                        toast.show();
                    } else {
                        sendRequest(strUrlPath, params);
                        //启动等待活动
                        progressDialog = new ProgressDialog(RegisterActivity.this);
                        progressDialog.setTitle("正在加载...");
                        progressDialog.setMessage("Loading...");
                        progressDialog.setCancelable(true);
                        progressDialog.show();
                    }
                }
                //启动新的线程
            }

            private void sendRequest(final String strUrlPath, final List<NameValuePair> params) {
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
        });
    }
}
