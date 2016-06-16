package com.example.a13051_000.buffetmealsystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by 13051_000 on 2016/6/16.
 */
public class EnsureOrderActivity extends AppCompatActivity {
    private TextView textshow;
    private Button buttonsure;
    private Button buttonback;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ensureorder);
        textshow = (TextView) findViewById(R.id.sumpriceshow);
        Intent intent1  = getIntent();
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

            }
        });
    }
}
