package com.example.a13051_000.buffetmealsystem.Settings;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.a13051_000.buffetmealsystem.LoginActivity;
import com.example.a13051_000.buffetmealsystem.R;

/**
 * Created by 13051_000 on 2016/5/12.
 */
public class SettingsActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    private Toolbar toolbar = null;
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private Button button_quit;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        String[]data={"意见反馈","关于我们","检查更新"};
        adapter=new ArrayAdapter<String>(
                SettingsActivity.this,android.R.layout.simple_list_item_1,data);
        ListView listView= (ListView) findViewById(R.id.list_view);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);


        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        toolbar.setTitle("设置");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        button_quit = (Button) findViewById(R.id.button_quit);
        button_quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(SettingsActivity.this).
                setMessage("确定要退出账户吗？").setPositiveButton("确定", new DialogInterface.OnClickListener(){

                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(SettingsActivity.this,LoginActivity.class);
                        SettingsActivity.this.startActivity(intent);
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).show();
            }});
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
         if(i == 0){
             Intent intent = new Intent(SettingsActivity.this, Setting_suggestions.class);
             this.startActivity(intent);
         }else if(i == 1){
             Intent intent1 = new Intent(SettingsActivity.this,Setting_AboutUs.class);
             this.startActivity(intent1);
         }
    }

}
