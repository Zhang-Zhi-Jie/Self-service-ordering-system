package com.example.a13051_000.buffetmealsystem.Order;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a13051_000.buffetmealsystem.HttpUtils;
import com.example.a13051_000.buffetmealsystem.R;
import com.google.gson.Gson;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 13051_000 on 2016/6/16.
 */
public class EnsureOrderActivity extends AppCompatActivity {
    private TextView textshow;
    private Button buttonsure;
    private Button buttonback;
    private Intent intent1;
    String strResult;
    final int SHOW_RESPONSE = 0;
    ProgressDialog progressDialog;
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
    private android.os.Handler handler = new android.os.Handler() {
        public void handleMessage(Message message) {
            switch (message.what) {
                case SHOW_RESPONSE:
                    Gson gson = new Gson();
                    order_status order_status = gson.fromJson(strResult, order_status.class);
                    progressDialog.dismiss();
                    Log.d("data1", strResult);
                    Log.d("data1",order_status.getStatus());
                    if (order_status.getStatus().equals("0")) {
                        Toast.makeText(EnsureOrderActivity.this, "上传成功", Toast.LENGTH_SHORT).show();
                    } else
                        Toast.makeText(EnsureOrderActivity.this, "上传失败", Toast.LENGTH_SHORT).show();
            }
        }

        ;
    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ensureorder);
        textshow = (TextView) findViewById(R.id.sumpriceshow);
        intent1  = getIntent();
        String sumprice = intent1.getStringExtra("sumprice");
        textshow.setText("总价为"+sumprice +"请确认");
        buttonback = (Button) findViewById(R.id.button_back);
        buttonsure = (Button) findViewById(R.id.button_ensure);
        buttonback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentback = new Intent(EnsureOrderActivity.this,OrderDetailActivity.class);
                startActivity(intentback);
            }
        });

        buttonsure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = intent1.getStringExtra("id");
                String quantity = intent1.getStringExtra("quantity");
                while (id.charAt(0) == '0'){
                    id = id.substring(1,id.length());
                }
                String strUrlPath = "http://www.loushubin.cn/buyform.php?type=submit";
                String data = id +"/" +quantity;
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("order_detail", data));
                if (!AccessNetworkState()) {
                    Toast.makeText(EnsureOrderActivity.this, "网络未连接，请先连接网络", Toast.LENGTH_SHORT).show();
                } else {
                    sendRequest(strUrlPath, params);
                    //启动等待活动
                    progressDialog = new ProgressDialog(EnsureOrderActivity.this);
                    progressDialog.setTitle("正在加载...");
                    progressDialog.setMessage("Loading...");
                    progressDialog.setCancelable(true);
                    progressDialog.show();
                }
            }

        });
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
}