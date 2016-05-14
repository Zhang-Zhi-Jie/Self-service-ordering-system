package com.example.a13051_000.buffetmealsystem;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by shubin on 2016/5/14.
 */
public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        ActivityCollector.addActivity(this);
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }
}

