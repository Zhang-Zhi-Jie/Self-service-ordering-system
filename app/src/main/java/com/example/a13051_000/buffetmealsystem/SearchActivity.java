package com.example.a13051_000.buffetmealsystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by 13051_000 on 2016/5/12.
 */
public class SearchActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Topbar topbar = (Topbar) findViewById(R.id.topbar1);
        topbar.setOnTobarClickListener(new Topbar.topbarClickListener() {
            @Override
            public void leftClick() {
                Intent intent = new Intent(SearchActivity.this,MainActivity.class);
                startActivity(intent);
            }

            @Override
            public void rightClick() {

            }
        });
    }
}
