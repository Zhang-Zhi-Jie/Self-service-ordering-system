package com.example.a13051_000.buffetmealsystem.Comment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.SimpleAdapter;

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

    private ListView listView_comment;
    private List<Map<String, Object>> listItems = new ArrayList<Map<String, Object>>();
    String comment;
    int star;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        toolbar.setTitle("评价");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ratingBar = (RatingBar) findViewById(R.id.ratingbar);
        editText = (EditText) findViewById(R.id.editText_comment);

        star = ratingBar.getNumStars();//星数
        comment = editText.getText().toString();//评价

        listView_comment = (ListView) findViewById(R.id.list_comment);
        Map<String, Object> listitem = new HashMap<String, Object>();

        SimpleAdapter simpleAdapter = new SimpleAdapter(this,
                listItems,
                R.layout.user_comment_item,
                new String[]{"comment_name","comment_content","comment_grade","comment_time"},
                new int[]{R.id.user_comment_name,R.id.user_comment_content,R.id.user_commnet_grade,R.id.user_comment_time});
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
