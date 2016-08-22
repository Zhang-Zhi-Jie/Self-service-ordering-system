package com.example.cook;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by shubin on 2016/6/16.
 */
public class Detail extends AppCompatActivity implements AdapterView.OnItemClickListener{
    @Override
    protected void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_detail);
        ListView listView = (ListView) findViewById(R.id.listView);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        Result result = (Result)bundle.getSerializable("listItems");
        Result.Order_detail order_detail = result.getOrder_detail();
        List<String> dish_name = order_detail.getDish_name();
        List<String> num = order_detail.getNum();
        final List<Map<String, Object>> listItems = new ArrayList<Map<String, Object>>();
        String[] arg1 = new String[dish_name.size()];
        for (int i = 0; i < dish_name.size(); i++) {
            Map<String, Object> listitem = new HashMap<String, Object>();
            listitem.put("order_num", result.getOrder_num());
            listitem.put("order_belong", dish_name.get(i));
            listitem.put("order_price", num.get(i));
            //                          listitem.put("unit", result_spoon_details.get(i).getUnit());
            listItems.add(listitem);
            arg1[i] = listitem.toString();
        }
        SimpleAdapter orderAdapter = new SimpleAdapter(Detail.this, listItems, R.layout.item_list_module_2, new String[]{"order_num", "order_belong", "order_price"},
                new int[]{R.id.order_db_id, R.id.order_db_name, R.id.order_db_price});
        listView.setAdapter(orderAdapter);
        listView.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        new AlertDialog.Builder(Detail.this).setMessage("确定已完成？").setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        }).show();
    }
}
