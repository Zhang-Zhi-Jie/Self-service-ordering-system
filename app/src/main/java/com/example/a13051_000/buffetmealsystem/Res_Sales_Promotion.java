package com.example.a13051_000.buffetmealsystem;

import android.os.Bundle;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;



/**
 * Created by 13051_000 on 2016/8/12.
 */
public class Res_Sales_Promotion extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView textView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.des_sales_promotion);

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        toolbar.setTitle("促销活动");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        textView = (TextView) findViewById(R.id.show);
        textView.setText("使用此app点餐可减5元，谢谢。");

    }
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
