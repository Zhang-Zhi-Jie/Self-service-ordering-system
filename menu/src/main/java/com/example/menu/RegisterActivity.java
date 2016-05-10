package com.example.menu;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by 13051_000 on 2016/5/10.
 */
public class RegisterActivity extends AppCompatActivity {

    private Button button_register;

     protected  void  onCreate(Bundle savedInstanceState){
         super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_register);

         button_register= (Button) findViewById(R.id.button1);
     }

}
