package com.example.a13051_000.buffetmealsystem;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.http.NameValuePair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by 13051_000 on 2016/5/10.
 */
public class FragmentSpoon extends Fragment {
    ListView menuList;
    String ID;
    String Name;
    String PRICE;
    String unit, sResult;
    Status list;
    ProgressDialog progressDialog;
    private final int SHOW_RESPONSE = 0;
    private Handler handler = new android.os.Handler() {
        public void handleMessage(Message message) {
            switch (message.what) {
                case SHOW_RESPONSE:
                    String response = (String) message.obj;
                    sResult = response;
                    if (sResult != null && !sResult.equals(-1)) {
                        Gson gson = new Gson();
                        String result = "";
                        try {
                             list = gson.fromJson(sResult,Status.class);
                            Log.d("data1","123");
                            Log.d("data1",list.toString());
                        } catch (Exception e) {
                            Log.d("data1", e.toString());
                        }
                        List<Map<String, Objects>> listItems = new ArrayList<Map<String, Objects>>();
                        ListAdapter orderAdapter = new SimpleAdapter(getActivity(), listItems, R.layout.item_list_module, new String[]{ID, Name, PRICE, unit},
                                new int[]{R.id.order_db_id, R.id.order_db_name, R.id.order_db_price, R.id.order_db_create_at});
                        menuList.setAdapter(orderAdapter);
                        progressDialog.dismiss();
                        Log.d("data1", sResult);
                        String nick_name = "";
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_spoon, container, false);
        menuList = (ListView) rootView.findViewById(R.id.collect_order_menu_list);
        if (AccessNetworkState()) {
            String url = "http://www.loushubin.cn/get_order_form.php?restaurant_id=zhangsan";
            sendRequest(url);
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
}
