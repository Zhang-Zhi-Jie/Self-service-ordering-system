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
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.a13051_000.buffetmealsystem.MainActivity;
import com.example.a13051_000.buffetmealsystem.R;
import com.example.a13051_000.buffetmealsystem.Scan.ScanActivity;
import com.example.a13051_000.buffetmealsystem.Settings.SettingsActivity;
import com.example.a13051_000.buffetmealsystem.restaurant.MainActivity_r;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 13051_000 on 2016/7/29.
 */
public class Drink_Restaurant extends AppCompatActivity implements AdapterView.OnItemClickListener{

    private Toolbar toolbar;
    private GridView gridview;
    private List<Map<String , Object>> list;

    public String[] img_text = { "可乐", "啤酒", "橙汁", "雪碧" };
    public int[] imgs = { R.drawable.y1, R.drawable.y2,
            R.drawable.y3, R.drawable.y4};

    protected void onCreate(Bundle savedInstanceState){
    super.onCreate(savedInstanceState);
    setContentView(R.layout.restaurant_drink);toolbar = (Toolbar) findViewById(R.id.tool_bar);
        toolbar.setTitle("饮品");
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
            Intent intent = new Intent(Drink_Restaurant.this, MainActivity.class);
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
        window.setContentView(R.layout.alertdialog_food_preview);
        TextView tv_title = (TextView) window.findViewById(R.id.tv_dialog_title);
        tv_title.setText(img_text[i]);
        ImageView imageview  = (ImageView) window.findViewById(R.id.show_food);
        imageview.setImageResource(imgs[i]);
        TextView textView_order = (TextView) window.findViewById(R.id.text_order);
        Button button_order = (Button) window.findViewById(R.id.button_order);
        textView_order.setText("您还未选座,请点击选座并点餐");
        button_order.setText("选座");
        if(ScanActivity.visible == true&& SettingsActivity.quit == false){
            textView_order.setText("继续点餐请点击");
            button_order.setText("继续点餐");
            button_order.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent1 = new Intent(Drink_Restaurant.this, MainActivity_r.class);
                    intent1.putExtra("seat_num",ScanActivity.seat_num);
                    Drink_Restaurant.this.startActivity(intent1);
                }
            });
        }else {

            button_order.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Drink_Restaurant.this, ScanActivity.class);
                    Drink_Restaurant.this.startActivity(intent);
                }
            });
        }
    }
}

