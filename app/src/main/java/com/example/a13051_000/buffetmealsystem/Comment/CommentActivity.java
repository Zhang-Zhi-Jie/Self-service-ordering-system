package com.example.a13051_000.buffetmealsystem.Comment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.a13051_000.buffetmealsystem.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 13051_000 on 2016/4/25.
 */
public class CommentActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private RatingBar ratingBar;
    private EditText editText;

    private TextView textView_name;

    String comment;
    String name;
    int star;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        toolbar.setTitle("评价");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        textView_name = (TextView) findViewById(R.id.textView_name);
        Intent intent = getIntent();
        name = intent.getStringExtra("dish_name");
        textView_name.setText("对 ["+name+"] 这一道菜进行一下点评吧！");

        ratingBar = (RatingBar) findViewById(R.id.ratingbar);
        editText = (EditText) findViewById(R.id.editText_comment);

        star = ratingBar.getNumStars();//星数
        comment = editText.getText().toString();//评价


    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
