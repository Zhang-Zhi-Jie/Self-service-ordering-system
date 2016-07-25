package com.example.a13051_000.buffetmealsystem.Order;

import android.app.ProgressDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.a13051_000.buffetmealsystem.R;

/**
 * Created by 13051_000 on 2016/6/12.
 */
public class CollectOrderActivity extends AppCompatActivity{
    private ListView collectList;
    private TextView tv_bg;
    private String str;
    private Cursor cursor;
    protected  void onCreated(Bundle savedInstanceState){
       super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_collect);
   }


}
