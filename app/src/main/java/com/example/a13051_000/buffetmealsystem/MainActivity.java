package com.example.a13051_000.buffetmealsystem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity{
    private ImageView imageView_spoon;
    private ImageView imageView_user;
    private ImageView imageView_setting;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

      imageView_spoon= (ImageView) findViewById(R.id.image_spoon);
        imageView_user= (ImageView) findViewById(R.id.image_user);
        imageView_setting= (ImageView) findViewById(R.id.image_setting);

        imageView_spoon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(MainActivity.this,SpoonActivity.class);
                MainActivity.this.startActivity(intent1);
            }
        });
        imageView_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(MainActivity.this,UserActivity.class);
                MainActivity.this.startActivity(intent1);
            }
        });
        imageView_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(MainActivity.this,SettingsActivity.class);
                MainActivity.this.startActivity(intent1);
            }
        });

    }
}

