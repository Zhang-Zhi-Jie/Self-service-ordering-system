package com.example.a13051_000.buffetmealsystem.restaurant.Classfication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.a13051_000.buffetmealsystem.MainActivity;
import com.example.a13051_000.buffetmealsystem.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 13051_000 on 2016/7/29.
 */
public class Meat_Restaurant extends AppCompatActivity implements AdapterView.OnItemClickListener{

    private Toolbar toolbar;
    private GridView gridview;
    private List<Map<String , Object>> list;
    public String[] img_text = { "宫保鸡丁", "虾盘", "玉米虾", "菠萝肉" };
    public int[] imgs = { R.drawable.r1, R.drawable.r2,
            R.drawable.r3, R.drawable.r4};

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restaurant_meat);
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        toolbar.setTitle("肉类");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        gridview = (GridView) findViewById(R.id.gridview);
        list = new ArrayList<Map<String, Object>>();
        SimpleAdapter simpleAdapter = new SimpleAdapter(this,getData(),R.layout.gridview_item,
                new String[]{"image","text"},new int[]{R.id.iv_item,R.id.tv_item});
        gridview.setAdapter(simpleAdapter);
        gridview.setOnItemClickListener(this);

    }
    private List<Map<String,Object>>getData() {
        for(int i = 0; i<imgs.length;i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("image",imgs[i]);
            map.put("text",img_text[i]);
            list.add(map);
        }
        return list;
    }

    public  boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if(id == android.R.id.home){
            Intent intent = new Intent(Meat_Restaurant.this, MainActivity.class);
            this.startActivity(intent);
            this.finish();
            overridePendingTransition(R.anim.fab_scale_up,R.anim.fab_scale_down);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.show();
        Window window = alertDialog.getWindow();
        window.setContentView(R.layout.food_preview);
        TextView tv_title = (TextView) window.findViewById(R.id.tv_dialog_title);
        tv_title.setText(img_text[i]);
        ImageView imageview  = (ImageView) window.findViewById(R.id.show_food);
        imageview.setImageResource(imgs[i]);
    }
}