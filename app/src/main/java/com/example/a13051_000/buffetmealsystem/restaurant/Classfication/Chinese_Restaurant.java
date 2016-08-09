package com.example.a13051_000.buffetmealsystem.restaurant.Classfication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.a13051_000.buffetmealsystem.MainActivity;
import com.example.a13051_000.buffetmealsystem.R;
import com.example.a13051_000.buffetmealsystem.restaurant.MainActivity_r;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 13051_000 on 2016/7/29.
 */
public class Chinese_Restaurant extends AppCompatActivity implements AdapterView.OnItemClickListener{
    private Toolbar toolbar;
    private ListView listView;
    private List<Map<String,Object>>dataList;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restaurant_chinese);

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        toolbar.setTitle("中式餐厅");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listView = (ListView) findViewById(R.id.chinese_list);
        dataList = new ArrayList<Map<String,Object>>();
        SimpleAdapter orderAdapter = new SimpleAdapter(Chinese_Restaurant.this,
                getData(),R.layout.restaurant_item_list,new String[]{"image","name"},new int[]{R.id.restaurant_image,R.id.restaurant_name});
        listView.setAdapter(orderAdapter);
        listView.setOnItemClickListener(this);

    }

    public  boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if(id == android.R.id.home){
            Intent intent = new Intent(Chinese_Restaurant.this, MainActivity.class);
            this.startActivity(intent);
            this.finish();
            overridePendingTransition(R.anim.fab_scale_up,R.anim.fab_scale_down);
        }
        return super.onOptionsItemSelected(item);
    }

    private List<Map<String,Object>> getData(){
      for(int i = 1 ;i < 2;i++){
          Map<String,Object>map = new HashMap<String,Object>();
          map.put("image",R.drawable.ic_launcher);
          map.put("name","北京烤鸭");
          dataList.add(map);
      }
      return dataList;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(Chinese_Restaurant.this, MainActivity_r.class);
        this.startActivity(intent);
    }
}
