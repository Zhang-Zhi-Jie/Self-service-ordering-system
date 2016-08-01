package com.example.a13051_000.buffetmealsystem.User;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.a13051_000.buffetmealsystem.R;

/**
 * Created by 13051_000 on 2016/8/1.
 */
public class User_information_change extends AppCompatActivity {

    private Toolbar toolbar;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_information_change);

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        toolbar.setTitle("用户信息");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if(id == android.R.id.home){
            finish();
            return true;
        }
        return onOptionsItemSelected(item);
    }
}
