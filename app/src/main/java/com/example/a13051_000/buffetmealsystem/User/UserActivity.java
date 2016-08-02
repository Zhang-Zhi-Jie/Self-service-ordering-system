package com.example.a13051_000.buffetmealsystem.User;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.a13051_000.buffetmealsystem.R;

/**
 * Created by 13051_000 on 2016/7/30.
 */
public class UserActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    private TextView textView;
    private Toolbar toolbar;
    private ImageView imageView;
    private ListView listView1;
    private ListView listView2;
    private ArrayAdapter<String>adapter1;
    private ArrayAdapter<String>adapter2;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        textView = (TextView) findViewById(R.id.shownickname);
        imageView = (ImageView) findViewById(R.id.imageView);
        listView1 = (ListView) findViewById(R.id.listview1);
        listView2 = (ListView) findViewById(R.id.listview2);

        toolbar.setTitle("用户管理");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        final String nickname = intent.getStringExtra("nickname");
        textView.setText(""+nickname+"");

        String []data1={"更改信息","修改密码"};
        String []data2={"我的消息"};
        adapter1 = new ArrayAdapter<String>(UserActivity.this,android.R.layout.simple_list_item_1,data1);
        adapter2 = new ArrayAdapter<String>(UserActivity.this,android.R.layout.simple_list_item_1,data2);
        listView1.setAdapter(adapter1);
        listView2.setAdapter(adapter2);
        listView1.setOnItemClickListener(this);
        listView2.setOnItemClickListener(this);

    }

    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if(id == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
           if(i == 0){
               Intent intent = new Intent(UserActivity.this,User_information_change.class);
               this.startActivity(intent);
           }
           if(i == 1){
               Intent intent1 = new Intent(UserActivity.this,User_password_change.class);
               this.startActivity(intent1);
           }
    }
}
