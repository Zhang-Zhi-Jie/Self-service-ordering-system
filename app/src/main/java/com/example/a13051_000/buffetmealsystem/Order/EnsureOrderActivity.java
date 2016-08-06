package com.example.a13051_000.buffetmealsystem.Order;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
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
import com.example.a13051_000.buffetmealsystem.Sqlite.OrderForm;
import com.example.a13051_000.buffetmealsystem.Sqlite.OrderFormDataSource;
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

    String sumprice, name,quantity,id;
    Bundle bundle;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ensureorder);
        textshow = (TextView) findViewById(R.id.sumpriceshow);
        Intent intent1 = getIntent();
        sumprice = intent1.getStringExtra("sumprice");
        name = intent1.getStringExtra("name");
        id = intent1.getStringExtra("id");
        quantity = intent1.getStringExtra("quantity");
        textshow.setText("总价为" + sumprice + "请确认");
        buttonback = (Button) findViewById(R.id.button_back);
        buttonsure = (Button) findViewById(R.id.button_ensure);

        bundle = this.getIntent().getExtras();//获取Intent数据

        buttonback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentback = new Intent(EnsureOrderActivity.this, OrderDetailActivity.class);
                startActivity(intentback);
            }
        });

        buttonsure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ContentValues value = new ContentValues();
                value.put("name", name);
                value.put("price", sumprice);
                OrderForm orderForm = new OrderForm();
                orderForm.setDetail(name);
                orderForm.setPrice(sumprice);
                orderForm.setNum(Long.valueOf(quantity));
                orderForm.setId_server(id);
                OrderFormDataSource orderFormDataSource = new OrderFormDataSource(EnsureOrderActivity.this);
                orderFormDataSource.open();
                orderFormDataSource.create(orderForm);

            }
        });
    }
}

