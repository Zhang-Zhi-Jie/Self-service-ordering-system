package com.example.cook;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.google.gson.Gson;

import org.apache.http.NameValuePair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.jpush.android.api.InstrumentedActivity;
import cn.jpush.android.api.JPushInterface;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{
    //网络连接检查函数:::
    private boolean AccessNetworkState(){
        ConnectivityManager connManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connManager.getActiveNetworkInfo() != null){
            return connManager.getActiveNetworkInfo().isAvailable();
        }
        else
            return false;
    }
    private static final int REFRESH_COMPLETE = 0X110;
    private ListView menulist;
    private SwipeRefreshLayout mSwipeLayout;

    private SimpleAdapter orderAdapter;
    ProgressDialog progressDialog;
    final int SHOW_RESPONSE = 0;
    private String sResult;
    private android.os.Handler handler = new android.os.Handler() {
        public void handleMessage(Message message) {
            switch (message.what) {
                case SHOW_RESPONSE:
                    String response = (String) message.obj;
                    sResult = response;
                    if (sResult != null && !sResult.equals(-1)) {
                        Gson gson = new Gson();
                        String result = "";
                        ResultFromServer resultFromServer = null;
                        Log.d("data1",sResult);
                        try {
                            resultFromServer = gson.fromJson(sResult, ResultFromServer.class);
                        } catch (Exception e) {
                            Log.d("data1", e.toString());
                        }
                        if (resultFromServer.getStatus().equals("0")) {
                            final List<Result> result1 = resultFromServer.getResult();
                            //菜品信息存储在result_soon_details这个list里面
                            Log.d("list", String.valueOf(result1.size()));

                            final List<Map<String, Object>> listItems = new ArrayList<Map<String, Object>>();
                            String[] arg1 = new String[result1.size()];
                            for (int i = 0; i < result1.size(); i++) {

                                long time;
                                int year,month,day;
                                time = Long.valueOf(result1.get(i).getOrder_num());
                                year = (int)(time/(100000000000L));
                                if(time/(10000000000L)%10==0){
                                    month = (int)(time/(1000000000L)%10);
                                }else {
                                    month =(int) (10 + time / (1000000000L) % 10);
                                }
                                if(time/(100000000L)%10==0){
                                    day = (int)(time/(10000000)%10);
                                }else{
                                    day = (int)((time/(100000000)%10)*10+(time/(10000000)%10));
                                }//将订单号分割成日期

                                Map<String, Object> listitem = new HashMap<String, Object>();
                                listitem.put("order_num", result1.get(i).getOrder_num());
                                listitem.put("order_belong", result1.get(i).getOrder_belong());
                                listitem.put("order_price", result1.get(i).getOrder_price());
                                listitem.put("order_year",year);
                                listitem.put("order_month",month);
                                listitem.put("order_day",day);
                                listitem.put("order_db_seat_num",result1.get(i).getSeat_num());
                                //                          listitem.put("unit", result_spoon_details.get(i).getUnit());
                                listItems.add(listitem);
                                arg1[i] = listitem.toString();
                            }
                            orderAdapter = new SimpleAdapter(MainActivity.this, listItems,R.layout.item_list_module, new String[]{"order_num", "order_belong", "order_price","order_year","order_month","order_day","order_db_seat_num"},
                                    new int[]{R.id.order_db_id, R.id.order_db_name, R.id.order_db_price,R.id.order_db_year,R.id.order_db_month,R.id.order_db_day,R.id.order_db_seat_num});
                            menulist.setAdapter(orderAdapter);
                            menulist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    Intent intent = new Intent(MainActivity.this,Detail.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putSerializable("listItems",result1.get(position));
                                    intent.putExtras(bundle);
                                    intent.putExtra("num",(String)listItems.get(position).get("order_num"));
                                    startActivity(intent);
                                }
                            });
                            progressDialog.dismiss();
                        }
                    }
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        menulist = (ListView) findViewById(R.id.listView);
        mSwipeLayout = (SwipeRefreshLayout)findViewById(R.id.swipe);
        mSwipeLayout.setOnRefreshListener(this);

        mSwipeLayout.setProgressBackgroundColorSchemeColor(Color.parseColor("#FF8C00"));
        mSwipeLayout.setColorSchemeColors(R.color.red,R.color.colorwhite,
                R.color.colorwhite, R.color.colorwhite);


        //添加界面布局:
        if(AccessNetworkState()) {
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setTitle("正在加载...");
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(true);
            progressDialog.show();
            sendRequest("http://www.loushubin.cn/buyform.php?type=get");
        }
        else
            Toast.makeText(this,"请检查网络连接...",Toast.LENGTH_LONG);
    }
    private void sendRequest(final String strUrlPath){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String strResult = null;
                try {
                    strResult = HttpUtils.submitGetData(strUrlPath,"utf-8");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Message message = new Message();
                message.what = SHOW_RESPONSE;
                message.obj = strResult;
                handler.sendMessage(message);
            }
        }).start();
    }

    private Handler mHandler = new Handler()
    {
        public void handleMessage(android.os.Message msg)
        {
            switch (msg.what)
            {
                case REFRESH_COMPLETE:
                    orderAdapter.notifyDataSetChanged();
                    mSwipeLayout.setRefreshing(false);
                    break;
            }
        };
    };

    @Override
    public void onRefresh() {
        mHandler.sendEmptyMessageDelayed(REFRESH_COMPLETE, 2000);
    }
}
