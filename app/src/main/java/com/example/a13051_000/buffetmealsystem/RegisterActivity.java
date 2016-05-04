package com.example.a13051_000.buffetmealsystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by 13051_000 on 2016/4/20.
 */
public class RegisterActivity extends AppCompatActivity {
    private Button button1;
    private TextView textView1;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        button1= (Button) findViewById(R.id.button1);
        textView1= (TextView) findViewById(R.id.textView1);
        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(RegisterActivity.this,LoginActivity.class);
                RegisterActivity.this.startActivity(intent1);
            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String number=((EditText)findViewById(R.id.editText1)).getText().toString();
                String pwd=((EditText)findViewById(R.id.editText2)).getText().toString();
                boolean flag=false;
                String nickname="";
                for(int i=0;i<Date.USER.length;i++){
                    if (number.equals(Date.USER[i][0])){
                        if(pwd.equals(Date.USER[i][1])){
                            nickname=Date.USER[i][2];
                            flag=true;
                            break;
                        }
                    }
                }
                if(flag){
                    Intent intent=new Intent(RegisterActivity.this,MainActivity.class);
                    RegisterActivity.this.startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(),"信息输入错误,请重新输入",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
