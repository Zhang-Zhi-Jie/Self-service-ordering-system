package com.example.a13051_000.buffetmealsystem.Fragment.FragmentForm;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.a13051_000.buffetmealsystem.Comment.CommentActivity;
import com.example.a13051_000.buffetmealsystem.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by shubin on 2016/6/16.
 */
public class Detail extends AppCompatActivity implements AdapterView.OnItemClickListener{
    private Toolbar toolbar;
    private String[] dishname;

    @Override
    protected void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        setContentView(R.layout.order_form_detali);

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        toolbar.setTitle("订单具体");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ListView listView = (ListView) findViewById(R.id.listView);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        Result result = (Result)bundle.getSerializable("listItems");
        Result.Order_detail order_detail = result.getOrder_detail();
        List<String> dish_name = order_detail.getDish_name();
        List<String> num = order_detail.getNum();
        final List<Map<String, Object>> listItems = new ArrayList<Map<String, Object>>();
        String[] arg1 = new String[dish_name.size()];
        dishname= new String[dish_name.size()];
        for (int i = 0; i < dish_name.size(); i++) {
            Map<String, Object> listitem = new HashMap<String, Object>();
            dishname[i]= dish_name.get(i);
            listitem.put("order_num", result.getOrder_num());
            listitem.put("order_belong", dish_name.get(i));
            listitem.put("order_price", num.get(i));
            //                          listitem.put("unit", result_spoon_details.get(i).getUnit());
            listItems.add(listitem);
            arg1[i] = listitem.toString();
        }
        SimpleAdapter orderAdapter = new SimpleAdapter(Detail.this, listItems, R.layout.order_form_item_list_module, new String[]{"order_num", "order_belong", "order_price"},
                new int[]{R.id.order_db_id, R.id.order_db_name, R.id.order_db_price});
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
    public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.show();
        Window window = alertDialog.getWindow();
        window.setContentView(R.layout.alertdialog_form_detail);
        Button button_comment = (Button) window.findViewById(R.id.button_comment);
        button_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Detail.this,CommentActivity.class);
                intent.putExtra("dish_name",dishname[i]);
                Detail.this.startActivity(intent);
            }
        });
    }
}
