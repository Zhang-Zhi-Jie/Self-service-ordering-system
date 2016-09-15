package com.example.a13051_000.buffetmealsystem.Order;

import android.app.LauncherActivity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DrawableUtils;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a13051_000.buffetmealsystem.HttpUtils;
import com.example.a13051_000.buffetmealsystem.R;
import com.example.a13051_000.buffetmealsystem.ResultFromServer;
import com.example.a13051_000.buffetmealsystem.Sqlite.OrderForm;
import com.example.a13051_000.buffetmealsystem.Sqlite.OrderformDataSource;
import com.example.a13051_000.buffetmealsystem.restaurant.MainActivity_r;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.google.gson.Gson;
import com.google.gson.internal.Primitives;

import org.apache.http.NameValuePair;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.a13051_000.buffetmealsystem.R.id.tool_bar;

/**
 * Created by 13051_000 on 2016/6/12.
 */

interface AsyncResponse {
    void processFinish(Parse_Json_comments parse_json_comments);
}

public class  OrderDetailActivity extends AppCompatActivity implements AsyncResponse {
    private TextView showid;
    private TextView showname;
    private TextView showprice;
    private TextView showunit;
    private TextView material_food;
    private TextView feature_food;
    private TextView effect_food;
    private Button button_tj;
    private Toolbar toolbar;
    public static String url_get_comment = "http://www.loushubin.cn/comment.php?type=get&goods_id=";
    double sumprice;
    String price;
    String id;
    String name;
    String arg1;
    String arg2;
    String arg3;
    private ListView listView_comment;
    private List<Map<String, Object>> listItems = new ArrayList<Map<String, Object>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        SimpleDraweeView simpleDraweeView = (SimpleDraweeView) findViewById(R.id.fiv);
        toolbar = (Toolbar) findViewById(tool_bar);
        toolbar.setTitle("点餐信息");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        showid = (TextView) findViewById(R.id.order_db_id);
        showname = (TextView) findViewById(R.id.order_db_name);
        showprice = (TextView) findViewById(R.id.order_db_price);
        showunit = (TextView) findViewById(R.id.order_db_create_at);
        material_food = (TextView) findViewById(R.id.material_food);
        effect_food = (TextView) findViewById(R.id.effect_food);
        feature_food = (TextView) findViewById(R.id.feature_food);
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        name = intent.getStringExtra("name");
        price = intent.getStringExtra("price");
        arg1 = intent.getStringExtra("arg1");
        arg2 = intent.getStringExtra("arg2");
        arg3 = intent.getStringExtra("arg3");
        String perunit = intent.getStringExtra("perunit");
        showid.setText(id + "");
        showname.setText(name + "");
        showprice.setText(price + "");
        showunit.setText(perunit + "");
        material_food.setText(arg1 + "");
        feature_food.setText(arg2 + "");
        effect_food.setText(arg3 + "");
        while (id.startsWith("0")) {
            id = id.substring(1);
        }
        SubmitComment submitComment = new SubmitComment(OrderDetailActivity.this);
        submitComment.delegate = this;
        submitComment.execute(id);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentValues value = new ContentValues();
                value.put("name", name);
                value.put("price", sumprice);
                OrderForm orderForm = new OrderForm();
                orderForm.setDetail(name);
                orderForm.setPrice(price);
                orderForm.setId_server(id);
                OrderformDataSource orderFormDataSource = new OrderformDataSource(OrderDetailActivity.this);
                orderFormDataSource.open();
                orderFormDataSource.create(orderForm);
                Toast.makeText(getApplicationContext(), "已加入到购物车", Toast.LENGTH_SHORT).show();
                finish();
            }
        });


        ScrollView scrollView = (ScrollView) findViewById(R.id.scrollview);
//        scrollView.smoothScrollTo(0,0);
        listView_comment = (ListView) findViewById(R.id.list_comment);

        String url_photo = "http://www.loushubin.cn/getPhoto.php?name=" + id;
        Log.d("PhotoUrl", url_photo);
        Uri uri = Uri.parse(url_photo);
        simpleDraweeView.setImageURI(uri);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public static String timestampToDate(String beginDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(new Date(Long.valueOf(beginDate) * 1000L));
        return date;
    }//时间转换

    @Override
    public void processFinish(Parse_Json_comments parse_json_comments) {

        for (Data data :
                parse_json_comments.getDatas()) {
            Map<String, Object> listItem = new HashMap<String, Object>();
            listItem.put("comment_name", data.getUser_name());
            listItem.put("comment_content", data.getComment_content());
            listItem.put("comment_grade", data.getStar_level());
            listItem.put("comment_time", timestampToDate(data.getTime()));
            Log.d("comment", data.getTime());
            listItems.add(listItem);
        }

        Log.d("listitem", String.valueOf(listItems.size()));
        SimpleAdapter simpleAdapter = new SimpleAdapter(OrderDetailActivity.this,
                listItems,
                R.layout.user_comment_item,
                new String[]{"comment_name", "comment_content", "comment_grade", "comment_time"},
                new int[]{R.id.user_comment_name, R.id.user_comment_content, R.id.user_commnet_grade, R.id.user_comment_time});
        listView_comment.setAdapter(simpleAdapter);
    }
}


class SubmitComment extends AsyncTask<String, Void, Parse_Json_comments> {
    private Context context;
    private ProgressDialog progressDialog;
    AsyncResponse delegate = null;

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
    protected void onPostExecute(Parse_Json_comments parse_json_comments) {
        super.onPostExecute(parse_json_comments);
        progressDialog.dismiss();
        if (parse_json_comments != null && parse_json_comments.getStatus().equals("0")) {
//            Toast.makeText(context, "评论加载成功...", Toast.LENGTH_SHORT).show();
            if (parse_json_comments.getDatas() != null) {
                Log.d("inform", "parse");
                delegate.processFinish(parse_json_comments);
            }
        } else {
            Toast.makeText(context, "评论加载失败，请刷新重试.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected Parse_Json_comments doInBackground(String... params) {
        String strResult = null;
        try {
            strResult = HttpUtils.submitGetData(OrderDetailActivity.url_get_comment + params[0], "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d("result", strResult);
        Gson gson = new Gson();
        Parse_Json_comments parse_json_comments = new Parse_Json_comments();
        try {
            parse_json_comments = gson.fromJson(strResult, Parse_Json_comments.class);
        } catch (Exception e) {
            Log.d("errorMessage", e.toString());
        }
        return parse_json_comments;
    }


}
