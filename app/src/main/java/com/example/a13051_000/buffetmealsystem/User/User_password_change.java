package com.example.a13051_000.buffetmealsystem.User;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.a13051_000.buffetmealsystem.LoginActivity;
import com.example.a13051_000.buffetmealsystem.R;

/**
 * Created by 13051_000 on 2016/8/1.
 */
public class User_password_change extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText password_old;
    private EditText password_new;
    private Button button;


    protected  void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_password_change);

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        toolbar.setTitle("修改密码");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        password_old = (EditText) findViewById(R.id.password_old);
        password_new = (EditText) findViewById(R.id.password_new);
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(User_password_change.this).setMessage("确定要修改密码吗？").setPositiveButton("确定", new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).show();
            }
        });


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
