package com.example.a13051_000.buffetmealsystem.restaurant.FragmentFood;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Uri;
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
import com.example.a13051_000.buffetmealsystem.R;
import com.example.a13051_000.buffetmealsystem.Sqlite.OrderFormDataSource_menu;
import com.example.a13051_000.buffetmealsystem.Status;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 13051_000 on 2016/7/15.
 */
public class FragmentMeat extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

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
    String classify;
    ProgressDialog progressDialog;
    private HashMap<Integer,Integer> map;
    private static final int REFRESH_COMPLETE = 0X110;
    private final int SHOW_RESPONSE = 0;

    private Handler handler = new android.os.Handler() {
        public void handleMessage(Message message) {
            switch (message.what) {
                case SHOW_RESPONSE:
                    final String response = (String) message.obj;
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
                            final List<Result_Spoon_detail> result_spoon_details= list.getResult();
                            //菜品信息存储在result_soon_details这个list里
                            final List<Map<String, Object>> listItems = new ArrayList<Map<String, Object>>();
                            String[] arg1 = new String[result_spoon_details.size()];
                            OrderFormDataSource_menu orderFormDataSource_menu = new OrderFormDataSource_menu(getContext());
                            orderFormDataSource_menu.open();
                            orderFormDataSource_menu.deleteAll();
                            for(int i=0;i<result_spoon_details.size();i++){
                                orderFormDataSource_menu.create(result_spoon_details.get(i));
                            }
                            map = new HashMap<>();
                            for(int i= 0;i<result_spoon_details.size();i++){

                                if (result_spoon_details.get(i).getClassify().equals("1")) {

                                    String id = result_spoon_details.get(i).getId();
                                    while (id.startsWith("0")){
                                        id = id.substring(1);
                                    }
                                    String url_photo = "http://www.loushubin.cn/getPhoto.php?name="+id;
                                    Log.d("PhotoUrl",url_photo);
                                    Uri uri = Uri.parse(url_photo);
                                    map.put(listItems.size(),i);
                                    Map<String, Object> listitem = new HashMap<String, Object>();
                                    listitem.put("image",uri);
                                    listitem.put("ID", result_spoon_details.get(i).getId());
                                    listitem.put("Name", result_spoon_details.get(i).getDish_name());
                                    listitem.put("PRICE", result_spoon_details.get(i).getPrice());
                                    listitem.put("unit", result_spoon_details.get(i).getUnit());
                                    listItems.add(listitem);
                                    arg1[i] = listitem.toString();
                                }
                            }
                            SimpleAdapter orderAdapter = new SimpleAdapter(getActivity(), listItems, R.layout.fragment_res_item_list_module, new String[]{"image","ID", "Name", "PRICE", "unit"},
                                    new int[]{R.id.pic_show,R.id.order_db_id, R.id.order_db_name, R.id.order_db_price, R.id.order_db_create_at});
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
                                    intent.putExtra("arg1",result_spoon_details.get(map.get(i)).getArg1());
                                    intent.putExtra("arg2",result_spoon_details.get(map.get(i)).getArg2());
                                    intent.putExtra("arg3",result_spoon_details.get(map.get(i)).getArg3());
                                    Log.d("arg1",result_spoon_details.get(i).getArg1());
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
        View rootView = inflater.inflate(R.layout.fragment_meat, container, false);

        menuList = (ListView) rootView.findViewById(R.id.list_view);
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

