package com.example.a13051_000.buffetmealsystem.Order;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.a13051_000.buffetmealsystem.MainActivity;
import com.example.a13051_000.buffetmealsystem.R;
import com.example.a13051_000.buffetmealsystem.Sqlite.OrderForm;
import com.example.a13051_000.buffetmealsystem.Sqlite.OrderFormDataSource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 13051_000 on 2016/7/24.
 */
public class OrderCar extends AppCompatActivity {

    private Toolbar toolbar;
    private ListView listView;
    private TextView textView;

    private DbHelper dbhelper;
    private SQLiteDatabase db;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_car);

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        toolbar.setTitle("餐车");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        listView = (ListView) findViewById(R.id.listview_car);
        OrderFormDataSource orderFormDataSource = new OrderFormDataSource(OrderCar.this);
        orderFormDataSource.open();
        List<OrderForm> orderForms = orderFormDataSource.getAllForm();
        final List<Map<String, Object>> listItems = new ArrayList<Map<String, Object>>();
        String[] arg1 = new String[orderForms.size()];
        for(int i= 0;i<orderForms.size();i++){
            Map<String,Object> listitem = new HashMap<String,Object>();
            listitem.put("num",orderForms.get(i).getNum());
            listitem.put("name",orderForms.get(i).getDetail());
            listitem.put("price",orderForms.get(i).getPrice());
            Log.d("id_server",orderForms.get(i).getId_server());
            listItems.add(listitem);
            arg1[i] = listitem.toString();
        }

        SimpleAdapter orderAdapter = new SimpleAdapter(OrderCar.this,
                listItems,
                R.layout.item_list_ordercar,
                new String[]{"num","name","price"},
                new int[]{R.id.showId,R.id.showname,R.id.showprice});
        listView.setAdapter(orderAdapter);

        textView = (TextView) findViewById(R.id.integral_sum);

    }


    public boolean onOptionsItemSelected(MenuItem item){
        int id =item.getItemId();
        if (id == android.R.id.home) {
            Intent intent = new Intent(OrderCar.this, MainActivity.class);
            this.startActivity(intent);
            this.finish();
            overridePendingTransition(R.anim.fab_fade_in,R.anim.fab_fade_out);
        }
        return super.onOptionsItemSelected(item);
    }


}
