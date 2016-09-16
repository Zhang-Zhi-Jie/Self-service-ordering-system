package com.example.a13051_000.buffetmealsystem.Fragment.FragmentForm;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.a13051_000.buffetmealsystem.Comment.CommentActivity;
import com.example.a13051_000.buffetmealsystem.HttpUtils;
import com.example.a13051_000.buffetmealsystem.R;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


/**
 * Created by shubin on 2016/6/16.
 */
public class Detail extends AppCompatActivity implements AdapterView.OnItemClickListener,AsyncResponse{
    private Toolbar toolbar;
    private String[] dishname;
    private String is_finished = "加载中";
    private List<String> id;
    private int num;
    private Result result;
    private ListView listView;
    private Result.Order_detail order_detail;
    public static boolean FormDetailAllFinish = true;
    @Override
    protected void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        setContentView(R.layout.order_form_detali);

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        toolbar.setTitle("订单具体");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listView = (ListView) findViewById(R.id.listView);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        result = (Result)bundle.getSerializable("listItems");
        order_detail = result.getOrder_detail();

        List<String> dish_name = order_detail.getDish_name();
        List<String> num = order_detail.getNum();
        List<Boolean> finish = order_detail.getFinish();

        id = order_detail.getId();
        final List<Map<String, Object>> listItems = new ArrayList<Map<String, Object>>();
        String[] arg1 = new String[dish_name.size()];
        dishname= new String[dish_name.size()];


        for (int i = 0; i < dish_name.size(); i++) {

            Map<String, Object> listitem = new HashMap<String, Object>();
            dishname[i]= dish_name.get(i);
            listitem.put("order_id", result.getOrder_num());
            listitem.put("order_name", dish_name.get(i));
            listitem.put("order_num", num.get(i));
            listitem.put("order_finish",is_finished);
            //                          listitem.put("unit", result_spoon_details.get(i).getUnit());
            listItems.add(listitem);
            arg1[i] = listitem.toString();
            getFinishStatus getFinishStatus_r = new getFinishStatus(this);
            getFinishStatus.delegate = this;
            getFinishStatus_r.execute(result.getOrder_num());
        }
        SimpleAdapter orderAdapter = new SimpleAdapter(Detail.this, listItems, R.layout.order_form_detail_item, new String[]{"order_id", "order_name", "order_num","order_finish"},
                new int[]{R.id.order_db_id, R.id.order_db_name, R.id.order_db_num,R.id.order_db_finish});
        listView.setAdapter(orderAdapter);
        listView.setOnItemClickListener(this);
    }

    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if(id == android.R.id.home){
            finish();
            return true;
        }
        return onOptionsItemSelected(item);
    }

    @Override
    public void processFinish(ResultFromServer output, Boolean clear) {

    }

    public void getFinishStatus(ResultFromServer result1) {
        if(result1.getFinish_status().get(this.num).getFinish_status_detail().getFinished_num().equals(result1.getFinish_status().get(this.num).getFinish_status_detail().getTotal_num())){
            order_detail.setFinish(this.num,true);
        }else order_detail.setFinish(this.num,false);
        this.num ++;
        Log.d("this.num", Objects.toString(this.num));
        if (this.num == order_detail.getNum().size()) {
            List<String> dish_name = order_detail.getDish_name();
            List<String> num = order_detail.getNum();
            List<Boolean> finish = order_detail.getFinish();
            id = order_detail.getId();
            Result.Order_detail output = null;
            final List<Map<String, Object>> listItems = new ArrayList<Map<String, Object>>();
            String[] arg1 = new String[dish_name.size()];
            dishname = new String[dish_name.size()];
            for (int i = 0; i < dish_name.size(); i++) {
                this.num = 0;
                if (result.getOrder_detail().getFinish().get(i)) {
                    Log.d("finish", is_finished);
                    is_finished = "已完成";
                } else
                    is_finished = "未完成";
                Map<String, Object> listitem = new HashMap<String, Object>();
                dishname[i] = dish_name.get(i);
                listitem.put("order_id", result.getOrder_num());
                listitem.put("order_name", dish_name.get(i));
                listitem.put("order_num", num.get(i));
                listitem.put("order_finish", is_finished);
                //                          listitem.put("unit", result_spoon_details.get(i).getUnit());
                listItems.add(listitem);
                arg1[i] = listitem.toString();
            }
            SimpleAdapter orderAdapter = new SimpleAdapter(Detail.this, listItems, R.layout.order_form_detail_item, new String[]{"order_id", "order_name", "order_num", "order_finish"},
                    new int[]{R.id.order_db_id, R.id.order_db_name, R.id.order_db_num, R.id.order_db_finish});
            listView.setAdapter(orderAdapter);
            listView.setOnItemClickListener(this);
        }
    }

    @Override

    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        final int position = i;
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.show();
        Window window = alertDialog.getWindow();
        window.setContentView(R.layout.alertdialog_form_detail);
        Button button_comment = (Button) window.findViewById(R.id.button_comment);
        button_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Detail.this,CommentActivity.class);
                intent.putExtra("dish_name",dishname[position]);
                intent.putExtra("id",id.get(position));
                Detail.this.startActivity(intent);
            }
        });
    }
}
class getFinishStatus extends AsyncTask<String,Integer,ResultFromServer> {
    private String finish_query_url = "http://www.loushubin.cn/buyform.php?type=get_finish_status&order_num=";
    private Context context;
    private String result;
    public static AsyncResponse delegate = null;

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
        super.onPostExecute(resultFromServer);
        delegate.getFinishStatus(resultFromServer);
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
