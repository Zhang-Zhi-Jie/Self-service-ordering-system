package com.example.a13051_000.buffetmealsystem.Order;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a13051_000.buffetmealsystem.R;

import static com.example.a13051_000.buffetmealsystem.R.id.tool_bar;

/**
 * Created by 13051_000 on 2016/6/12.
 */
public class OrderDetailActivity extends AppCompatActivity {
    private TextView showid;
    private TextView showname;
    private TextView showprice;
    private TextView showunit;
    private EditText editText_shu;
    private Button button_tj;
    private Toolbar toolbar;
    String quantity1;
    double sumprice;
    String price;
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        toolbar = (Toolbar) findViewById(tool_bar);
        toolbar.setTitle("点餐信息");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        showid = (TextView) findViewById(R.id.order_db_id);
        showname = (TextView) findViewById(R.id.order_db_name);
        showprice = (TextView) findViewById(R.id.order_db_price);
        showunit = (TextView) findViewById(R.id.order_db_create_at);
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        String name = intent.getStringExtra("name");
        price = intent.getStringExtra("price");
        String perunit = intent.getStringExtra("perunit");
        showid.setText(id + "");
        showname.setText(name + "");
        showprice.setText(price + "");
        showunit.setText(perunit + "");
        button_tj = (Button) findViewById(R.id.button_tijiao);
        button_tj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editText_shu = (EditText) findViewById(R.id.editTextshu);
                button_tj = (Button) findViewById(R.id.button_tijiao);
                quantity1 = editText_shu.getText().toString();
                double quantity = Double.valueOf(quantity1);
                if (quantity < 0) {
                    Toast.makeText(getApplicationContext(), "输入数量小于0，请重新输入", Toast.LENGTH_SHORT);
                } else {
                    double price1 = Double.valueOf(price);
                    sumprice = quantity * price1;
                    Intent intent = new Intent(OrderDetailActivity.this, EnsureOrderActivity.class);
                    String sumprice_str = String.valueOf(sumprice);
                    intent.putExtra("sumprice", sumprice_str);
                    intent.putExtra("id", id);
                    intent.putExtra("quantity",quantity1);
                    startActivity(intent);
                }
            }
        });
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}