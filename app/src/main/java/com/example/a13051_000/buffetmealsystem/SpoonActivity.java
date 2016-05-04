package com.example.a13051_000.buffetmealsystem;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by 13051_000 on 2016/5/4.
 */
public class SpoonActivity extends AppCompatActivity {
    private ImageView imageView_setting;
    private ImageView imageView_user;
    private ImageView imageView_main;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spoon);
        imageView_setting= (ImageView) findViewById(R.id.image_setting);
        imageView_user= (ImageView) findViewById(R.id.image_user);
        imageView_main= (ImageView) findViewById(R.id.image_main);

        imageView_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(SpoonActivity.this,SettingsActivity.class);
                SpoonActivity.this.startActivity(intent1);
            }
        });
        imageView_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(SpoonActivity.this,UserActivity.class);
                SpoonActivity.this.startActivity(intent1);
            }
        });
        imageView_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(SpoonActivity.this,MainActivity.class);
                SpoonActivity.this.startActivity(intent1);
            }
        });
    }
}
