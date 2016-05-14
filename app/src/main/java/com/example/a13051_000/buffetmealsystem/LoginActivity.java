package com.example.a13051_000.buffetmealsystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by 13051_000 on 2016/4/26.
 */
public class LoginActivity extends BaseActivity {

    private Button button;
    private Button rightbutton;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        button= (Button) findViewById(R.id.loginbutton1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String loginnickname=((EditText)findViewById(R.id.logineditText4)).getText().toString();
                String loginnum=((EditText)findViewById(R.id.logineditText1)).getText().toString();
                String loginpwd=((EditText)findViewById(R.id.logineditText2)).getText().toString();
                String againpwd=((EditText)findViewById(R.id.logineditText3)).getText().toString();
                boolean login=false;
                if(loginpwd.equals(againpwd)){
                    login = true;
                }
                if(login){
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("user", loginnum);
                    params.put("password", loginpwd);
                    params.put("nickname",loginnickname);
                    String strUrlPath = "http://www.loushubin.cn/register.php";
                    String strResult = HttpUtils.submitPostData(strUrlPath, params, "utf-8");
                    String result = Json.parseJSONWithJOSNObject(strResult);
                    Toast.makeText(LoginActivity.this,"注册成功!",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getApplicationContext(),"密码输入不匹配，请重新输入",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
