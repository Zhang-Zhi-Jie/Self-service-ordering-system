package com.example.a13051_000.buffetmealsystem.Order;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a13051_000.buffetmealsystem.Fragment.FragmentForm.ResultFromServer;
import com.example.a13051_000.buffetmealsystem.HttpUtils;
import com.example.a13051_000.buffetmealsystem.MainActivity;
import com.example.a13051_000.buffetmealsystem.R;
import com.example.a13051_000.buffetmealsystem.Sqlite.OrderForm;
import com.example.a13051_000.buffetmealsystem.Sqlite.OrderformDataSource;
import com.google.gson.Gson;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * Created by 13051_000 on 2016/7/24.
 */
public class OrderCar extends AppCompatActivity {

    private Toolbar toolbar;
    private ListView listView;
    private TextView integral_sum;
    private TextView clear;
    private TextView submit;
    private CheckBox checkBox_select_all;
    private CheckBox checkBox_add;

    private SQLiteDatabase db;
    public static final String url_submit = "http://www.loushubin.cn/buyform.php?type=submit";
    private OrderCarAdapter orderCarAdapter;
    private OrderformDataSource orderformDataSource;
    private List<Test> data;
    private int sum = 0;
    private int[] sumInteger;
    private Context context;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_car);
        initView();
        orderformDataSource = new OrderformDataSource(OrderCar.this);
        orderformDataSource.open();
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        toolbar.setTitle("餐车");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        submit = (TextView) findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<OrderForm> orderForms = orderformDataSource.getAllForm();
                StringBuilder stringBuilder = new StringBuilder();
               HashMap<Integer,Boolean> isSelected =  OrderCarAdapter.getIsSelected();
                int i = 0;
                for (OrderForm orderform: orderForms
                     ) {
                    if (isSelected.get(i)) {
                        if (stringBuilder.length() == 0) {
                            stringBuilder.append(orderform.getId_server());
                            stringBuilder.append("/" + orderform.getNum());
                        } else {
                            stringBuilder.append("@" + orderform.getId_server());
                            stringBuilder.append("/" + orderform.getNum());
                        }
                    }
                    i++;
                }
                Log.d("submit_data",stringBuilder.toString());
                submit_data submitData = new submit_data(OrderCar.this);
                submitData.execute(stringBuilder.toString());

                data.clear();
                orderCarAdapter.notifyDataSetChanged();
                integral_sum.setText(0 + "");
                checkBox_select_all.setChecked(false);
                checkBox_add.setChecked(false);
                OrderformDataSource orderformDataSource1 = new OrderformDataSource(OrderCar.this);
                orderformDataSource1.open();
                orderformDataSource1.deleteAllOrderform();

            }
        });

    }


    private void initView() {
        context = this;
        data = new ArrayList<Test>();
        final OrderformDataSource orderformDataSource = new OrderformDataSource(OrderCar.this);
        orderformDataSource.open();
        List<OrderForm> orderForms = orderformDataSource.getAllForm();
        for (int i = 0; i < orderForms.size(); i++) {
            data.add(new Test(orderForms.get(i).getId_server(), orderForms.get(i).getDetail(), orderForms.get(i).getPrice()));
        }

        orderCarAdapter = new OrderCarAdapter(context, handler, data);
        sumInteger = new int[data.size() + 1];
        checkBox_add = (CheckBox) findViewById(R.id.checkbox_add);
        integral_sum = (TextView) findViewById(R.id.integer_sum);
        clear = (TextView) findViewById(R.id.clear);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                data.clear();
                orderCarAdapter.notifyDataSetChanged();
                integral_sum.setText(0 + "");
                checkBox_select_all.setChecked(false);
                checkBox_add.setChecked(false);
                OrderformDataSource orderformDataSource1 = new OrderformDataSource(OrderCar.this);
                orderformDataSource1.open();
                orderformDataSource1.deleteAllOrderform();
            }
        });
        checkBox_select_all = (CheckBox) findViewById(R.id.checkbox_select);
        checkBox_select_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<Integer, Boolean> isSelected = OrderCarAdapter
                        .getIsSelected();
                Iterator iterator = isSelected.entrySet().iterator();
                List<Boolean> array = new ArrayList<Boolean>();//列表中checkbox选中状态
                List<Integer> nums = new ArrayList<Integer>();//列表中商品数量
                while (iterator.hasNext()) {
                    HashMap.Entry entry = (HashMap.Entry) iterator.next();
                    Integer key = (Integer) entry.getKey();
                    Boolean val = (Boolean) entry.getValue();
                    array.add(val);
                }
                for (int i = 0; i < data.size(); i++) {
                    int num = data.get(i).getNum();
                    nums.add(num);
                }
                if (checkBox_select_all.isChecked()) {

                    for (int i = 0; i < data.size(); i++) {
                        OrderCarAdapter.getIsSelected().put(i, true);
                    }
                    checkBox_add.setChecked(true);
                    orderCarAdapter.notifyDataSetChanged();
                } else {
                    for (int i = 0; i < data.size(); i++) {
                        OrderCarAdapter.getIsSelected().put(i, false);
                    }
                    checkBox_add.setChecked(false);
                    orderCarAdapter.notifyDataSetChanged();
                    integral_sum.setText(0 + "");
                }
            }
        });
        listView = (ListView) findViewById(R.id.listview_car);
        listView.setAdapter(orderCarAdapter);
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private Handler handler = new Handler() {
        @SuppressWarnings("unchecked")
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 10) { //更改选中商品的总价格
                float price = (Float) msg.obj;
                if (price > 0) {
                    integral_sum.setText(price + "");
                } else {
                    integral_sum.setText("0");
                }
            } else if (msg.what == 11) {
                //列表选中状态
                List<Boolean> array = (List<Boolean>) msg.obj;
                if (Test.isAllFalse(array)) {
                    checkBox_select_all.setChecked(false);
                    checkBox_add.setChecked(false);
                }
                if (Test.isAllTrue(array)) {
                    checkBox_select_all.setChecked(true);
                    checkBox_add.setChecked(true);
                }
                if (Test.isHaveOneFasle(array)) {
                    checkBox_select_all.setChecked(false);
                }
                if (Test.isHaveOneTrue(array)) {
                    checkBox_add.setChecked(true);
                }
            }
        }
    };
}
class submit_data extends AsyncTask<String,Void,ResultFromServer>{
    private Activity activity;
    private Context context;
    private ProgressDialog progressDialog;
    public submit_data(Context context){
        this.context = context;
        this.activity = (Activity)context;
        progressDialog = new ProgressDialog(context);
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog.setCancelable(true);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
    }

    @Override
    protected void onPostExecute(ResultFromServer resultFromServer) {
        super.onPostExecute(resultFromServer);
        if (resultFromServer != null){
            if (resultFromServer.getStatus().equals("0")){
                Toast.makeText(context,"结算成功..",Toast.LENGTH_SHORT).show();
            }
            else Toast.makeText(context,"结算失败..",Toast.LENGTH_SHORT).show();
        }
        progressDialog.dismiss();
    }

    @Override
    protected ResultFromServer doInBackground(String... params) {
        String result = "";
        List<NameValuePair> valuePairs = new ArrayList<>();
        valuePairs.add(new BasicNameValuePair("order_detail",params[0]));
        try {
            result = HttpUtils.submitPostData(OrderCar.url_submit,valuePairs,"utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d("result",params[0]);
        Log.d("result", Objects.toString(result.length()));
        Gson gson = new Gson();
        ResultFromServer resultFromServer = gson.fromJson(result, ResultFromServer.class);
        return resultFromServer;
    }
}
