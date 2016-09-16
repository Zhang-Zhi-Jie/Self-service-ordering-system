package com.example.a13051_000.buffetmealsystem.Fragment.FragmentForm;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a13051_000.buffetmealsystem.HttpUtils;
import com.example.a13051_000.buffetmealsystem.R;
import com.google.common.primitives.Booleans;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by 13051_000 on 2016/7/24.
 */
interface AsyncResponse{
    void processFinish(ResultFromServer output,Boolean clear);
    void getFinishStatus(ResultFromServer output);
}

public class FragmentOrderForm extends Fragment implements AdapterView.OnItemLongClickListener,AsyncResponse,SwipeRefreshLayout.OnRefreshListener{

    private static final int REFRESH_COMPLETE = 0X110;
    private SwipeRefreshLayout mSwipeLayout;
    private ListView menulist;
    private SimpleAdapter orderAdapter;

    ProgressDialog progressDialog;
    final int SHOW_RESPONSE = 0;
    private String sResult;
    private int num;
    private ResultFromServer output;
    private List<Result> result1 = null;
    static getOrderFrom getOrgerfor;
    private List<Map<String, Object>> listItems = new ArrayList<Map<String, Object>>();
    private String urlupdate = "http://www.loushubin.cn/buyform.php?type=update&id=";
    public final static String url_get = "http://www.loushubin.cn/buyform.php?type=get_user";
    private boolean AccessNetworkState(){
        ConnectivityManager connManager = (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connManager.getActiveNetworkInfo() != null){
            return connManager.getActiveNetworkInfo().isAvailable();
        }
        else
            return false;
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_orderform, container, false);
        menulist = (ListView)rootView.findViewById(R.id.listView);
        mSwipeLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.id_swipe_ly);
        mSwipeLayout.setOnRefreshListener(this);

        mSwipeLayout.setProgressBackgroundColorSchemeColor(Color.parseColor("#FF8C00"));
        mSwipeLayout.setColorSchemeColors(R.color.red,R.color.colorwhite,
                R.color.colorwhite, R.color.colorwhite);

        //添加界面布局:
        menulist.setOnItemLongClickListener(this);
        if(AccessNetworkState()) {
            getOrgerfor = new getOrderFrom(getActivity());
            getOrgerfor.delegate = this;
            getOrgerfor.execute(url_get);
        }
        else
            Toast.makeText(getActivity(),"请检查网络连接...",Toast.LENGTH_LONG).show();

        return rootView;
    }
    public void processFinish(ResultFromServer output, Boolean first){
        this.output = output;
        String finish = "";
        listItems.clear();
        if (output != null && output.getStatus().equals("0")) {
            result1 = output.getResult();
            if (result1 != null) {
                //菜品信息存储在result_soon_details这个list里面
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
                    if(first) {
                            finish = "加载中...";
                    }
                    else {
                        this.num = 0;
                        Log.d("finish",finish);
                        if (output.getResult().get(i).getOrder_finish()) {
                            Log.d("finish",finish);
                            finish = "已完成";
                        } else
                            finish = "未完成";
                    }
                    Map<String, Object> listitem = new HashMap<String, Object>();
                    listitem.put("order_num", result1.get(i).getOrder_num());
                    listitem.put("order_belong", result1.get(i).getOrder_belong());
                    listitem.put("order_price", result1.get(i).getOrder_price());
                    listitem.put("order_year",year);
                    listitem.put("order_month",month);
                    listitem.put("order_day",day);
                    listitem.put("order_seatnum",result1.get(i).getSeat_num());
                    listitem.put("order_finish",finish);
                    //test
                    if (first) {
                        getFinishStatus getFinishStatus_r = new getFinishStatus(getContext());
                        getFinishStatus.delegate = this;
                        getFinishStatus_r.execute(result1.get(i).getOrder_num());
                    }
                    //将数据填入
                    //                          listitem.put("unit", result_spoon_details.get(i).getUnit());
                    listItems.add(listitem);
                    arg1[i] = listitem.toString();
                    Log.d("list_form_each", listitem.toString());
                }
                if (getContext() != null) {
                   orderAdapter = new SimpleAdapter(getContext(), listItems, R.layout.fragment_form_item_list, new String[]{"order_num", "order_belong", "order_price","order_year","order_month","order_day","order_finish","order_seatnum"},
                            new int[]{R.id.order_db_id, R.id.order_db_name, R.id.order_db_price,R.id.order_db_year,R.id.order_db_month,R.id.order_db_day,R.id.order_db_finish,R.id.order_db_seat_num});
                    menulist.setAdapter(orderAdapter);


                    menulist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent = new Intent(getActivity(), Detail.class);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("listItems", result1.get(position));
                            intent.putExtras(bundle);
                            intent.putExtra("num", (String) listItems.get(position).get("order_num"));
                            startActivity(intent);
                        }
                    });
                }
            }
        }
    }

    @Override
    public void getFinishStatus(ResultFromServer output) {
        Boolean finish = true;
        listItems.get(this.num);
        for (Finish_status each:output.getFinish_status()
             ) {
            if (!each.getFinish_status_detail().getFinished_num().equals(each.getFinish_status_detail().getTotal_num())){
                finish = false;
            }
        }
        if (finish){
            this.output.getResult().get(this.num).setOrder_finish(true);
        }
        else
            this.output.getResult().get(this.num).setOrder_finish(false);
        this.num += 1;
        Log.d("num", Objects.toString(this.num));
        if (this.num == listItems.size()){
            this.processFinish(this.output,false);
        }
    }

    public boolean formDetailAllFinish(){

        return true;
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
                String order_num  = (String)listItems.get(position).get("order_num");
                String url_all = urlupdate + order_num;
                new updatePermission(getActivity()).execute(url_all);
            }
        }).show();
            return true;
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
class getOrderFrom extends AsyncTask<String,Integer,String> {
    Activity activity;
    Context context;
    public static AsyncResponse delegate = null;

    public getOrderFrom(Activity activity) {
        this.activity = activity;
        this.context = activity;
    }

    protected String doInBackground(String... urls) {
        String result = null;
        try {
            result = HttpUtils.submitGetData(urls[0], "utf-8");
        } catch (IOException e) {
            Log.d("getOrderFormError", e.toString());
        }
        return result;
    }


    protected void onPostExecute(String result) {
        Gson gson = new Gson();
        ResultFromServer resultFromServer = null;
        if (result != null && !result.equals(-1)) {
            try {
                resultFromServer = gson.fromJson(result, ResultFromServer.class);
            } catch (Exception e) {
                Log.d("data1", e.toString());
            }
            Log.d("server_result",result);
            delegate.processFinish(resultFromServer,true);
        }
    }
}
class updatePermission extends AsyncTask<String,Integer, com.example.a13051_000.buffetmealsystem.ResultFromServer>{
    private Activity activity;
    private Context context;
    private ProgressDialog progressDialog;
    public updatePermission(Activity activity){
        this.activity = activity;
        this.context = activity;
    }
    protected com.example.a13051_000.buffetmealsystem.ResultFromServer doInBackground(String... urls){
        String strResult = null;
        try {
            strResult = HttpUtils.submitGetData(urls[0],"utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d("result",strResult);
        Gson gson = new Gson();
        com.example.a13051_000.buffetmealsystem.ResultFromServer resultFromServer = new com.example.a13051_000.buffetmealsystem.ResultFromServer();
        try {
            resultFromServer = gson.fromJson(strResult,com.example.a13051_000.buffetmealsystem.ResultFromServer.class);
        } catch (Exception e) {
            Log.d("data1", e.toString());
        }
        return resultFromServer;
    }
    protected void onPreExecute(){
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("正在删除...");
        progressDialog.setTitle("Loading...");
        progressDialog.show();
    }
    protected void onPostExecute(com.example.a13051_000.buffetmealsystem.ResultFromServer resultFromServer){
        progressDialog.dismiss();
        new getOrderFrom(activity).execute(FragmentOrderForm.url_get);
        if (resultFromServer != null && resultFromServer.getStatus().equals("0")){
            Toast toast = Toast.makeText(context,"删除成功...",Toast.LENGTH_SHORT);
            View view = toast.getView();
            view.setBackgroundColor(Color.parseColor("#FF8C00"));
            toast.setView(view);
            toast.show();
        }
        else if(resultFromServer != null && resultFromServer.getStatus().equals("2")){
            Toast toast = Toast.makeText(context,"订单不存在...",Toast.LENGTH_SHORT);
            View view = toast.getView();
            view.setBackgroundColor(Color.parseColor("#FF8C00"));
            toast.setView(view);
            toast.show();
        }
        else{
            Toast toast = Toast.makeText(context,"删除失败，请重试.",Toast.LENGTH_SHORT);
            View view = toast.getView();
            view.setBackgroundColor(Color.parseColor("#FF8C00"));
            toast.setView(view);
            toast.show();

        }
    }

}

