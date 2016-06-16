package com.example.menu;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by 13051_000 on 2016/5/10.
 */
public class LoginActivity extends AppCompatActivity {

    private Button button_register;

     protected  void  onCreate(Bundle savedInstanceState){
         super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_login);

         button_register= (Button) findViewById(R.id.button1);
     }

}
