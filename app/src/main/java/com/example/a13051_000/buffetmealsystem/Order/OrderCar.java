package com.example.a13051_000.buffetmealsystem.Order;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.a13051_000.buffetmealsystem.MainActivity;
import com.example.a13051_000.buffetmealsystem.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 13051_000 on 2016/7/24.
 */
public class OrderCar extends AppCompatActivity {

    private Toolbar toolbar;
    private ListView listView;
    private TextView textView;

    private DbHelper dbhelper;
    private SQLiteDatabase db;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_car);

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        toolbar.setTitle("餐车");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        listView = (ListView) findViewById(R.id.listview_car);

        dbhelper = new DbHelper(this,"Db_form",null,1);//创建数据库
        db = dbhelper.getReadableDatabase();//打开数据库
        Cursor c = db.query("tb_form",new String[]{"_id","name","price"},null,null,null);//定义一个游标，从tb_form表中查询数据

        SimpleCursorAdapter orderAdapter = new SimpleCursorAdapter(OrderCar.this,
                R.layout.item_list_ordercar,
                c,
                new String[]{"_id","name","price"},
                new int[]{R.id.showId,R.id.showname,R.id.showprice});
        listView.setAdapter(orderAdapter);

        textView = (TextView) findViewById(R.id.integral_sum);

    }


    public boolean onOptionsItemSelected(MenuItem item){
        int id =item.getItemId();
        if (id == android.R.id.home) {
            Intent intent = new Intent(OrderCar.this, MainActivity.class);
            this.startActivity(intent);
            this.finish();
            overridePendingTransition(R.anim.fab_fade_in,R.anim.fab_fade_out);
        }
        return super.onOptionsItemSelected(item);
    }


}
