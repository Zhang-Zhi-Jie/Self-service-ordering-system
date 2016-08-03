package com.example.menu;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuWrapperFactory;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
public class MenuUploadActivity extends AppCompatActivity {
    Button button;
    EditText name;
    EditText price;
    EditText unit;
    EditText passw;
    final int INTERNET_ERR = 1;
    ProgressDialog progressDialog;
    final int SHOW_RESPONSE = 0;
    String result;
    String str_name,str_price,str_unit,str_passw;
    private boolean AccessNetworkState(){
        ConnectivityManager connManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connManager.getActiveNetworkInfo() != null){
            return connManager.getActiveNetworkInfo().isAvailable();
        }
        else
            return false;
    }
  Handler handler = new Handler(){
      public void handleMessage(Message message){
          switch (message.what){
              case SHOW_RESPONSE:
                  result = (String) message.obj;
                  Gson gson = new Gson();
                  Status status = null;
                  Log.d("data1",result);
                  try {
                      status = gson.fromJson(result,Status.class);
                  }catch (Exception e){
                      e.printStackTrace();
                  }
                  progressDialog.dismiss();
                  if (status != null){
                      Toast.makeText(MenuUploadActivity.this,"上传成功",Toast.LENGTH_LONG).show();
                  }else {
                      Toast.makeText(MenuUploadActivity.this,"上传失败,请重试...",Toast.LENGTH_SHORT).show();
                  }
                  break;
              case INTERNET_ERR:
                  progressDialog.dismiss();
                  Toast.makeText(MenuUploadActivity.this,"请先连接网络...",Toast.LENGTH_LONG).show();
          }
      }
  };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        button = (Button) findViewById(R.id.loginbutton1);
        name = (EditText) findViewById(R.id.logineditText4);
        price = (EditText) findViewById(R.id.logineditText1);
        unit = (EditText) findViewById(R.id.logineditText2);
        passw = (EditText) findViewById(R.id.logineditText3);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str_name = name.getText().toString();
                str_price = price.getText().toString();
                str_unit = unit.getText().toString();
                str_passw = passw.getText().toString();
                progressDialog = new ProgressDialog(MenuUploadActivity.this);
                progressDialog.setTitle("正在加载...");
                progressDialog.setMessage("Loading...");
                progressDialog.setCancelable(true);
                progressDialog.show();
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("dish_name",str_name));
                params.add(new BasicNameValuePair("price",str_price));
                params.add(new BasicNameValuePair("unit",str_unit));
                params.add(new BasicNameValuePair("username",str_passw));
                Log.d("data1",str_passw);
                Log.d("data1","123");
                String strUrlPath = "http://www.loushubin.cn/order_form.php";
                //调用Thread，创建新线程进行网络请求
                sendRequest(strUrlPath, params);
            }
        });
    }
    private void sendRequest(final String strUrlPath, final List<NameValuePair> params){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String strResult = null;
                if (AccessNetworkState()){
                try {
                    strResult = HttpUtils.submitPostData(strUrlPath,params,"utf-8");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Message message = new Message();
                message.what = SHOW_RESPONSE;
                message.obj = strResult;
                handler.sendMessage(message);
            }
             else{
                    Message message = new Message();
                    message.what = INTERNET_ERR;
                    handler.sendMessage(message);
                }
            }
        }).start();
    }
}
