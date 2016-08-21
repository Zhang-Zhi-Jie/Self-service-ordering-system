package com.example.a13051_000.buffetmealsystem.Comment;

import android.support.v7.app.AppCompatActivity;

import android.content.Intent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a13051_000.buffetmealsystem.HttpUtils;
import com.example.a13051_000.buffetmealsystem.R;
import com.example.a13051_000.buffetmealsystem.ResultFromServer;
import com.google.gson.Gson;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by 13051_000 on 2016/4/25.
 */
public class CommentActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private RatingBar ratingBar;
    private EditText editText;

    private TextView textView_name;
    private Button submit;
    private String id;
    private ListView listView_comment;
    public static final String url_comment = "http://www.loushubin.cn/comment.php?type=submit";
    private List<Map<String, Object>> listItems = new ArrayList<Map<String, Object>>();
    String comment;
    String name;

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

        submit = (Button) findViewById(R.id.submit);
        ratingBar = (RatingBar) findViewById(R.id.ratingbar);
        editText = (EditText) findViewById(R.id.editText_comment);
        id = intent.getStringExtra("id");
        listView_comment = (ListView) findViewById(R.id.list_comment);
        Map<String, Object> listitem = new HashMap<String, Object>();

        SimpleAdapter simpleAdapter = new SimpleAdapter(this,
                listItems,
                R.layout.user_comment_item,
                new String[]{"comment_name","comment_content","comment_grade","comment_time"},
                new int[]{R.id.user_comment_name,R.id.user_comment_content,R.id.user_commnet_grade,R.id.user_comment_time});

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Float star = ratingBar.getRating();//星数
                comment = editText.getText().toString();//评价
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("comment_goods_id", id));
                params.add(new BasicNameValuePair("comment_content", comment));
                params.add(new BasicNameValuePair("star_level",star.toString()));
                SubmitComment submitComment = new SubmitComment(CommentActivity.this);
                submitComment.execute(params);
            }
        });
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

class SubmitComment extends AsyncTask<List<NameValuePair>,Void,ResultFromServer>{
    private Context context;
    private ProgressDialog progressDialog;
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("请稍后..");
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(true);
        progressDialog.show();
    }

    public SubmitComment(Context context) {
        super();
        this.context = context;
    }

    @Override
    protected void onPostExecute(ResultFromServer resultFromServer) {
        super.onPostExecute(resultFromServer);
        progressDialog.dismiss();
        if (resultFromServer != null && resultFromServer.getStatus().equals("0")){
            Toast.makeText(context,"评论成功...",Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(context,"评论失败，请重试.",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected ResultFromServer doInBackground(List<NameValuePair>... params) {
        String strResult = null;
        try {
            strResult = HttpUtils.submitPostData(CommentActivity.url_comment,params[0],"utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d("result",strResult);
        Gson gson = new Gson();
        com.example.a13051_000.buffetmealsystem.ResultFromServer resultFromServer = new com.example.a13051_000.buffetmealsystem.ResultFromServer();
        try {
            resultFromServer = gson.fromJson(strResult,com.example.a13051_000.buffetmealsystem.ResultFromServer.class);
        } catch (Exception e) {
            Log.d("errorMessage", e.toString());
        }
        return resultFromServer;
    }
}
