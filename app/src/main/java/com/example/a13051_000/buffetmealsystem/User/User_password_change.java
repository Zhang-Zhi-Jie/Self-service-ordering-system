package com.example.a13051_000.buffetmealsystem.User;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.a13051_000.buffetmealsystem.ActivityCollector;
import com.example.a13051_000.buffetmealsystem.Fragment.FragmentForm.ResultFromServer;
import com.example.a13051_000.buffetmealsystem.HttpUtils;
import com.example.a13051_000.buffetmealsystem.LoginActivity;
import com.example.a13051_000.buffetmealsystem.R;
import com.google.gson.Gson;

import java.io.IOException;

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
        final String url_alter = "http://www.loushubin.cn/user_info.php?type=alterPass";
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
                        String passw_new = password_new.getText().toString();
                        String passw_old = password_old.getText().toString();
                        ParamContaner paramContaner = new ParamContaner(url_alter,passw_new,passw_old);
                        new UpToServer(User_password_change.this).execute(paramContaner);
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
class ParamContaner{
    public String new_pass;
    public String old_pass;
    public String url;
    public ParamContaner(String url,String new_pass, String old_pass){
        this.new_pass = new_pass;
        this.old_pass = old_pass;
        this.url = url;
    }
}
class UpToServer extends AsyncTask<ParamContaner,Integer,ResultFromServer>{
    Activity activity;
    Context context;
    private ProgressDialog progressDialog;
    public UpToServer(Activity activity){
        this.activity = activity;
        this.context = activity;
    }
    protected ResultFromServer doInBackground(ParamContaner... param) {
        Gson gson = new Gson();
        ResultFromServer resultFromServer = new ResultFromServer();
        String result = null;
        String url_total = param[0].url+"&new_pass="+param[0].new_pass+"&old_pass="+param[0].old_pass;
        Log.d("url",url_total);
        try {
            result = HttpUtils.submitGetData(url_total,"utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d("result",result);
        if(result!= null) {
            resultFromServer = gson.fromJson(result, ResultFromServer.class);
        }
        return resultFromServer;

    }
    protected void onPreExecute(){
        progressDialog = new ProgressDialog(this.context);
        progressDialog.setTitle("Loading...");
        progressDialog.setMessage("正在修改..");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }
    protected void onPostExecute(ResultFromServer resultFromSever){
        if (resultFromSever.getStatus() != null && resultFromSever.getStatus().equals("0")){
            Toast.makeText(context,"修改成功..",Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(context,"修改失败..",Toast.LENGTH_SHORT).show();
        }
        progressDialog.dismiss();

    }
}
