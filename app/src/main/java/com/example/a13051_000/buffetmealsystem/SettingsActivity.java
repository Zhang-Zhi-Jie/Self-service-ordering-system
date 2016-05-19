package com.example.a13051_000.buffetmealsystem;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by 13051_000 on 2016/5/12.
 */
public class SettingsActivity extends AppCompatActivity {

    private String[] data={"a","b","c","d"};
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ArrayAdapter<String>adapter=new ArrayAdapter<String>(
                SettingsActivity.this,android.R.layout.simple_list_item_1,data);
        ListView listView= (ListView) findViewById(R.id.list_view);
        listView.setAdapter(adapter);
    }
}
