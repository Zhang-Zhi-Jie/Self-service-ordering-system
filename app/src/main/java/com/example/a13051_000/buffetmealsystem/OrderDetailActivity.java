package com.example.a13051_000.buffetmealsystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.jetbrains.annotations.Nullable;
import org.w3c.dom.Text;

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
    String quantity1;
    double sumprice;
    String price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        showid = (TextView) findViewById(R.id.order_db_id);
        showname = (TextView) findViewById(R.id.order_db_name);
        showprice = (TextView) findViewById(R.id.order_db_price);
        showunit = (TextView) findViewById(R.id.order_db_create_at);
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
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
                    String sumprice_str=String.valueOf(sumprice);
                    Intent intent = new Intent(OrderDetailActivity.this, EnsureOrderActivity.class);
                    intent.putExtra("sumprice", sumprice_str);
                    startActivity(intent);
                }
            }
        });
    }
}




