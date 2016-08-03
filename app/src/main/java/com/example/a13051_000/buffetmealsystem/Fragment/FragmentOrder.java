package com.example.a13051_000.buffetmealsystem.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import android.widget.Toast;

import com.example.a13051_000.buffetmealsystem.HttpUtils;
import com.example.a13051_000.buffetmealsystem.Order.OrderDetailActivity;
import com.example.a13051_000.buffetmealsystem.Order.Result_Spoon_detail;
import com.example.a13051_000.buffetmealsystem.Status;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.a13051_000.buffetmealsystem.R;


/**
 * Created by 13051_000 on 2016/7/11.
 */
public class FragmentOrder extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    ListView menuList;
    String ID;
    String Name;
    String PRICE;
    String unit, sResult;
    Status list;
    String id;
    String name;
    String price;
    String perunit;
    ProgressDialog progressDialog;
    private SwipeRefreshLayout mSwipeLayout;
    private static final int REFRESH_COMPLETE = 0X110;
    private final int SHOW_RESPONSE = 0;

    private Handler handler = new android.os.Handler() {
        public void handleMessage(Message message) {
            switch (message.what) {

                case REFRESH_COMPLETE:
                    mSwipeLayout.setRefreshing(false);
                    break;

                case SHOW_RESPONSE:
                    String response = (String) message.obj;
                    sResult = response;
                    if (sResult != null && !sResult.equals(-1)) {
                        Gson gson = new Gson();
                        try {
                            list = gson.fromJson(sResult,Status.class);
                            Log.d("status",list.getStatus());
                        } catch (Exception e) {
                            Log.d("data1", e.toString());
                        }
                        //状态正确
                        if(list.getStatus().equals("0")) {
                            List<Result_Spoon_detail> result_spoon_details= list.getResult();
                            //菜品信息存储在result_soon_details这个list里面
                            Log.d("list",String.valueOf(result_spoon_details.size()));

                            final List<Map<String, Object>> listItems = new ArrayList<Map<String, Object>>();
                            String[] arg1 = new String[result_spoon_details.size()];
                            for(int i= 0;i<result_spoon_details.size();i++){
                                Map<String,Object> listitem = new HashMap<String,Object>();
                                listitem.put("ID",result_spoon_details.get(i).getId());
                                listitem.put("Name",result_spoon_details.get(i).getDish_name());
                                listitem.put("PRICE",result_spoon_details.get(i).getPrice());
                                listitem.put("unit",result_spoon_details.get(i).getUnit());
                                listItems.add(listitem);
                                arg1[i] = listitem.toString();
                            }
                            SimpleAdapter orderAdapter = new SimpleAdapter(getActivity(), listItems, R.layout.fragment_order_item_list_module, new String[]{"ID", "Name", "PRICE", "unit"},
                                    new int[]{R.id.order_db_id, R.id.order_db_name, R.id.order_db_price, R.id.order_db_create_at});

                            menuList.setAdapter(orderAdapter);
                            //      ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,arg1);
                            //     menuList.setAdapter(adapter);
                            progressDialog.dismiss();
                            Log.d("data_orderlist",listItems.toString());
                            String nick_name = "";
                            menuList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                    id= (String)listItems.get(i).get("ID");
                                    name= (String)listItems.get(i).get("Name");
                                    price= (String)listItems.get(i).get("PRICE");
                                    perunit= (String)listItems.get(i).get("unit");
                                    Intent intent = new Intent(getActivity(),OrderDetailActivity.class);
                                    intent.putExtra("id",id);
                                    intent.putExtra("name",name);
                                    intent.putExtra("price",price);
                                    intent.putExtra("perunit",perunit);
                                    startActivity(intent);
                                }
                            });

                        }

                    }
            }
        }
    };

    private boolean AccessNetworkState() {
        Context context = getContext();
        ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connManager.getActiveNetworkInfo() != null) {
            return connManager.getActiveNetworkInfo().isAvailable();
        } else
            return false;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_order, container, false);

        menuList = (ListView) rootView.findViewById(R.id.collect_order_menu_list);
        mSwipeLayout = (SwipeRefreshLayout)rootView.findViewById(R.id.id_swipe_ly);

        mSwipeLayout.setOnRefreshListener(this);
        mSwipeLayout.setColorSchemeColors(android.R.color.holo_blue_bright,
                android.R.color.holo_blue_bright,android.R.color.holo_blue_bright,android.R.color.holo_blue_bright);

        if (AccessNetworkState()) {
            String url = "http://www.loushubin.cn/get_order_form.php?restaurant_id=zhangsan";
            sendRequest(url);
            Log.d("data1","123");
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setTitle("正在加载...");
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(true);
            progressDialog.show();
        } else {
            Toast.makeText(getActivity(), "未连接到网络,请检查网络连接设置", Toast.LENGTH_SHORT).show();
        }
        return rootView;
    }
    private void sendRequest(final String strUrlPath) {
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
    public void onRefresh()
    {
        handler.sendEmptyMessageDelayed(REFRESH_COMPLETE, 2000);
    }
}
