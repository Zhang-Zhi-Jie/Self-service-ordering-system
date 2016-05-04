package com.example.a13051_000.buffetmealsystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by 13051_000 on 2016/5/4.
 */
public class SettingsActivity extends AppCompatActivity {

    private ImageView imageView_spoon;
    private ImageView imageView_user;
    private ImageView imageView_main;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        imageView_spoon= (ImageView) findViewById(R.id.image_spoon);
        imageView_user= (ImageView) findViewById(R.id.image_user);
        imageView_main= (ImageView) findViewById(R.id.image_setting);

        imageView_spoon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(SettingsActivity.this,SpoonActivity.class);
                SettingsActivity.this.startActivity(intent1);
            }
        });
        imageView_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(SettingsActivity.this,UserActivity.class);
                SettingsActivity.this.startActivity(intent1);
            }
        });
        imageView_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(SettingsActivity.this,MainActivity.class);
                SettingsActivity.this.startActivity(intent1);
            }
        });
    }

}
