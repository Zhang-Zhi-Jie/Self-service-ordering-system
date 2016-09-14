package com.example.cook;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.google.gson.Gson;
import com.tencent.android.tpush.XGPushConfig;
import com.tencent.android.tpush.XGPushManager;
import com.tencent.android.tpush.service.XGPushService;

import org.apache.http.NameValuePair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    //网络连接检查函数:::
    private boolean AccessNetworkState(){
        ConnectivityManager connManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connManager.getActiveNetworkInfo() != null){
            return connManager.getActiveNetworkInfo().isAvailable();
        }
        else
            return false;
    }
    private ListView menulist;
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
                                //                          listitem.put("unit", result_spoon_details.get(i).getUnit());
                                listItems.add(listitem);
                                arg1[i] = listitem.toString();
                            }
                            SimpleAdapter orderAdapter = new SimpleAdapter(MainActivity.this, listItems,R.layout.item_list_module, new String[]{"order_num", "order_belong", "order_price","order_year","order_month","order_day"},
                                    new int[]{R.id.order_db_id, R.id.order_db_name, R.id.order_db_price,R.id.order_db_year,R.id.order_db_month,R.id.order_db_day});
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
        // 开启logcat输出，方便debug，发布时请关闭
        XGPushConfig.enableDebug(this, true);
// 如果需要知道注册是否成功，请使用registerPush(getApplicationContext(), XGIOperateCallback)带callback版本
// 如果需要绑定账号，请使用registerPush(getApplicationContext(),account)版本
// 具体可参考详细的开发指南
// 传递的参数为ApplicationContext
        Context context = getApplicationContext();
        XGPushManager.registerPush(context);

// 2.36（不包括）之前的版本需要调用以下2行代码
        Intent service = new Intent(context, XGPushService.class);
        context.startService(service);


// 其它常用的API：
// 绑定账号（别名）注册：registerPush(context,account)或registerPush(context,account, XGIOperateCallback)，其中account为APP账号，可以为任意字符串（qq、openid或任意第三方），业务方一定要注意终端与后台保持一致。
// 取消绑定账号（别名）：registerPush(context,"*")，即account="*"为取消绑定，解绑后，该针对该账号的推送将失效
// 反注册（不再接收消息）：unregisterPush(context)
// 设置标签：setTag(context, tagName)
// 删除标签：deleteTag(context, tagName)







        setContentView(R.layout.activity_main);
        menulist = (ListView) findViewById(R.id.listView);

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
}
