package com.example.cook;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by shubin on 2016/6/16.
 */
interface AsyncResponse{
    void processFinish(ResultFromServer output,Boolean clear);
}

public class Detail extends AppCompatActivity implements AdapterView.OnItemClickListener,AsyncResponse{
    protected String[] finish;
    protected List<String> id;
    protected Result result;
    private int num;
    protected ListView listView;
    protected Bundle savedInstance;
    @Override
    protected void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        this.savedInstance = savedInstance;
        setContentView(R.layout.activity_detail);
        listView = (ListView) findViewById(R.id.listView);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        result = (Result)bundle.getSerializable("listItems");
        getFinishStatus _getFinishStatus = new getFinishStatus(Detail.this);
        _getFinishStatus.detail = this;
        _getFinishStatus.execute(result.getOrder_num());
        initView(result,listView,false);
    }
    //  flag为是否二次加载
    public void initView(Result result,ListView listView,Boolean flag){
        Result.Order_detail order_detail = result.getOrder_detail();
        List<String> dish_name = order_detail.getDish_name();
        List<String> num = order_detail.getNum();

        if(!flag) {
            finish = new String[dish_name.size()];
            for (int i = 0; i < dish_name.size(); i++) {
                finish[i] = "加载中";
            }
        }
        id = order_detail.getId();
        final List<Map<String, Object>> listItems = new ArrayList<Map<String, Object>>();
        String[] arg1 = new String[dish_name.size()];
        for (int i = 0; i < dish_name.size(); i++) {
            Map<String, Object> listitem = new HashMap<String, Object>();
            listitem.put("order_num", result.getOrder_num());
            listitem.put("order_belong", dish_name.get(i));
            listitem.put("order_num", num.get(i));
            listitem.put("order_finish",finish[i]);
            //                          listitem.put("unit", result_spoon_details.get(i).getUnit());
            listItems.add(listitem);
            arg1[i] = listitem.toString();
        }
        SimpleAdapter orderAdapter = new SimpleAdapter(Detail.this, listItems, R.layout.item_list_module_2, new String[]{"order_num", "order_belong", "order_num","order_finish"},
                new int[]{R.id.order_db_id, R.id.order_db_name, R.id.order_db_num,R.id.order_db_finish});
        listView.setAdapter(orderAdapter);
        listView.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int j, long l) {
        final int num = j;
        new AlertDialog.Builder(Detail.this).setMessage("确定已完成？").setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                _package pack = new _package(result.getOrder_num(),id.get(num));
                 FinishGoods finishGoods = new FinishGoods(Detail.this);
                finishGoods.execute(pack);
            }
        }).show();
    }

    @Override
    public void processFinish(ResultFromServer output, Boolean clear) {
    }
}
class _package{
    private String good_id;
    private String order_num;

    public _package(String order_num,String good_id){
        this.good_id = good_id;
        this.order_num = order_num;
    }
    public void setGood_id(String good_id) {
        this.good_id = good_id;
    }

    public String getGood_id() {
        return good_id;
    }

    public void setOrder_num(String order_num) {
        this.order_num = order_num;
    }

    public String getOrder_num() {
        return order_num;
    }
}
class FinishGoods extends AsyncTask<_package,Integer,String> {
    Activity activity;
    Context context;
    ProgressDialog progressDialog;
    public static AsyncResponse delegate = null;
    private String order_num;
    private String good_id;
    private String url;
    private String url_finish="http://www.loushubin.cn/buyform.php?type=finish&order_num=";
    public FinishGoods(Activity activity) {
        this.activity = activity;
        this.context = activity;
    }

    protected String doInBackground(_package... param) {
        order_num = param[0].getOrder_num();
        good_id = param[0].getGood_id();
        String result = null;
        url = url_finish +order_num +"&id=" +good_id;
        try {
            result = HttpUtils.submitGetData(url, "utf-8");
        } catch (IOException e) {
            Log.d("getOrderFormError", e.toString());
        }
        Log.d("url",url);
        return result;
    }

    protected void onPreExecute() {
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Loading..");
        progressDialog.setMessage("加载中..");
        progressDialog.setCancelable(false);
        progressDialog.show();
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
            progressDialog.dismiss();
            Log.d("server_result", result);
            if(resultFromServer.getStatus().equals("0")) {
            Toast.makeText(context,"已完成一份",Toast.LENGTH_SHORT).show();
            }
            else if(resultFromServer.getStatus().equals("8")){
                Toast.makeText(context,"已全部完成",Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(context,"加载失败..",Toast.LENGTH_SHORT).show();
            }
//            delegate.processFinish(resultFromServer, true);
        }
    }
}

class getFinishStatus extends AsyncTask<String,Integer,ResultFromServer> {
    private String finish_query_url = "http://www.loushubin.cn/buyform.php?type=get_finish_status&order_num=";
    private Context context;
    private String result;
    public static AsyncResponse delegate = null;
    public Detail detail;
    @Override
    protected ResultFromServer doInBackground(String... params) {
        try {
            result = HttpUtils.submitGetData(finish_query_url + params[0], "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d("result",result);
        Gson gson = new Gson();
        ResultFromServer resultFromServer = new ResultFromServer();
        try {
            resultFromServer = gson.fromJson(result,ResultFromServer.class);
        } catch (Exception e) {
            Log.w("errorMessage", e.toString());
        }
        return resultFromServer;
    }

    @Override
    protected void onPostExecute(ResultFromServer resultFromServer) {
        Integer i = 0;
        super.onPostExecute(resultFromServer);
        for (Finish_status finish_status: resultFromServer.getFinish_status()
             ) {
            if (finish_status.getFinish_status_detail().getFinished_num().equals(finish_status.getFinish_status_detail().getTotal_num())){
                detail.finish[i] = "已完成";
            }
            else{
                detail.finish[i] = "未完成";
            }
            i++;
        }
        detail.initView(detail.result,detail.listView,true);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    public getFinishStatus(Context context) {
        super();
        this.context = context;
    }
}

