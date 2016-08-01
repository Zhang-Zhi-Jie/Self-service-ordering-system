package com.example.a13051_000.buffetmealsystem.restaurant.Classfication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.a13051_000.buffetmealsystem.MainActivity;
import com.example.a13051_000.buffetmealsystem.R;

/**
 * Created by 13051_000 on 2016/7/29.
 */
public class Bar extends AppCompatActivity {

    private Toolbar toolbar;

    protected void onCreate(Bundle savedInstanceState){
    super.onCreate(savedInstanceState);
    setContentView(R.layout.restaurant_bar);toolbar = (Toolbar) findViewById(R.id.tool_bar);
        toolbar.setTitle("酒吧");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public  boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if(id == android.R.id.home){
            Intent intent = new Intent(Bar.this, MainActivity.class);
            this.startActivity(intent);
            this.finish();
            overridePendingTransition(R.anim.fab_scale_up,R.anim.fab_scale_down);
        }
        return super.onOptionsItemSelected(item);
    }
}

