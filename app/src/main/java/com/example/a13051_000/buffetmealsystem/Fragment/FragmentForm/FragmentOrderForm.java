package com.example.a13051_000.buffetmealsystem.Fragment.FragmentForm;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.a13051_000.buffetmealsystem.HttpUtils;
import com.example.a13051_000.buffetmealsystem.R;
import com.google.gson.Gson;

import org.apache.http.NameValuePair;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 13051_000 on 2016/7/24.
 */
public class FragmentOrderForm extends Fragment implements AdapterView.OnItemLongClickListener{

    private ListView menulist;
    ProgressDialog progressDialog;
    final int SHOW_RESPONSE = 0;
    private String sResult;

    private boolean AccessNetworkState(){
        ConnectivityManager connManager = (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connManager.getActiveNetworkInfo() != null){
            return connManager.getActiveNetworkInfo().isAvailable();
        }
        else
            return false;
    }
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
                        if (resultFromServer != null && resultFromServer.getStatus().equals("0")) {
                            final List<Result> result1 = resultFromServer.getResult();
                            //菜品信息存储在result_soon_details这个list里面
                            Log.d("list_form", String.valueOf(result1.size()));

                            final List<Map<String, Object>> listItems = new ArrayList<Map<String, Object>>();
                            String[] arg1 = new String[result1.size()];
                            for (int i = 0; i < result1.size(); i++) {
                                Map<String, Object> listitem = new HashMap<String, Object>();
                                listitem.put("order_num", result1.get(i).getOrder_num());
                                listitem.put("order_belong", result1.get(i).getOrder_belong());
                                listitem.put("order_price", result1.get(i).getOrder_price());
                                //                          listitem.put("unit", result_spoon_details.get(i).getUnit());
                                listItems.add(listitem);
                                arg1[i] = listitem.toString();
                                Log.d("list_form_each",listitem.toString());
                            }
                            SimpleAdapter orderAdapter = new SimpleAdapter(getActivity(), listItems, R.layout.fragment_form_item_list_module, new String[]{"order_num", "order_belong", "order_price"},
                                    new int[]{R.id.order_db_id, R.id.order_db_name, R.id.order_db_price});
                            menulist.setAdapter(orderAdapter);


                            menulist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    Intent intent = new Intent(getActivity(),Detail.class);
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_orderform, container, false);
        menulist = (ListView)rootView.findViewById(R.id.listView);
        //添加界面布局:
        menulist.setOnItemLongClickListener(this);

       if(AccessNetworkState()) {
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setTitle("正在加载...");
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(true);
            progressDialog.show();
            sendRequest("http://www.loushubin.cn/buyform.php?type=get_user");
        }
        else
            Toast.makeText(getActivity(),"请检查网络连接...",Toast.LENGTH_LONG).show();
        return rootView;
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

    @Override
    public boolean onItemLongClick(final AdapterView<?> adapterView, final View view, final int position, long l) {
        new AlertDialog.Builder(getActivity()).setMessage("确定要删除这个订单吗？").setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        }).show();
            return true;
    }
}
