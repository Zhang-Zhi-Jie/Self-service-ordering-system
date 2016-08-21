package com.example.a13051_000.buffetmealsystem.Order;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DrawableUtils;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a13051_000.buffetmealsystem.R;
import com.example.a13051_000.buffetmealsystem.Sqlite.OrderForm;
import com.example.a13051_000.buffetmealsystem.Sqlite.OrderformDataSource;
import com.example.a13051_000.buffetmealsystem.restaurant.MainActivity_r;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.a13051_000.buffetmealsystem.R.id.tool_bar;

/**
 * Created by 13051_000 on 2016/6/12.
 */
public class OrderDetailActivity extends AppCompatActivity {
    private TextView showid;
    private TextView showname;
    private TextView showprice;
    private TextView showunit;
    private Button button_tj;
    private Toolbar toolbar;
    double sumprice;
    String price;
    String id;
    String name;

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
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        name = intent.getStringExtra("name");
        price = intent.getStringExtra("price");
        String perunit = intent.getStringExtra("perunit");
        showid.setText(id + "");
        showname.setText(name + "");
        showprice.setText(price + "");
        showunit.setText(perunit + "");
        button_tj = (Button) findViewById(R.id.button_tijiao);
        button_tj.setOnClickListener(new View.OnClickListener() {
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
                Toast.makeText(getApplicationContext(),"已加入到购物车", Toast.LENGTH_SHORT).show();
                finish();


                listView_comment = (ListView) findViewById(R.id.list_comment);
                Map<String, Object> listitem = new HashMap<String, Object>();

                listItems.add(listitem);
                SimpleAdapter simpleAdapter = new SimpleAdapter(OrderDetailActivity.this,
                        listItems,
                        R.layout.user_comment_item,
                        new String[]{"comment_name","comment_content","comment_grade","comment_time"},
                        new int[]{R.id.user_comment_name,R.id.user_comment_content,R.id.user_commnet_grade,R.id.user_comment_time});
            }
        });
        while (id.startsWith("0")){
            id = id.substring(1);
        }
        String url_photo = "http://www.loushubin.cn/getPhoto.php?name="+id;
        Log.d("PhotoUrl",url_photo);
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

}