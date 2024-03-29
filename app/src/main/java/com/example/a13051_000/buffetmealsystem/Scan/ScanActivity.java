package com.example.a13051_000.buffetmealsystem.Scan;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.view.MenuItem;

import com.example.a13051_000.buffetmealsystem.MainActivity;
import com.example.a13051_000.buffetmealsystem.R;
import com.example.a13051_000.buffetmealsystem.Seat.SeatNumber;
import com.example.a13051_000.buffetmealsystem.restaurant.MainActivity_r;
import com.example.a13051_000.buffetmealsystem.seat_info;
import com.mining.app.zxing.decoding.Intents;

/**
 * Created by 13051_000 on 2016/5/12.
 */
public class ScanActivity extends AppCompatActivity {
    private final static int SCANNIN_GREQUEST_CODE = 1;

    private TextView mTextView ;
    private Toolbar toolbar;

    public static Boolean visible = false;
    public static String seat_num;

    protected  void onCreate(Bundle savedIntanceState){
        super.onCreate(savedIntanceState);
        setContentView(R.layout.activity_scan);

        mTextView = (TextView) findViewById(R.id.result);

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        toolbar.setTitle("选座");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ImageButton mButton = (ImageButton)findViewById(R.id.imagebutton1);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(ScanActivity.this, MipcaActivityCapture.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivityForResult(intent, SCANNIN_GREQUEST_CODE);
            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            Intent intent = new Intent(ScanActivity.this,MainActivity.class);
            ScanActivity.this.startActivity(intent);
            ScanActivity.this.finish();
        }

        return super.onOptionsItemSelected(item);
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case SCANNIN_GREQUEST_CODE:
                if(resultCode == RESULT_OK){
                    Bundle bundle = data.getExtras();
                    int k;
                    SeatNumber seatNumber = new SeatNumber();
                    for(int i = 0;i< seatNumber.seat.length;i++){
                        if(bundle.getString("result").equals(seatNumber.seat[i])){

                            visible = true;
                            k = i+1;
                            seat_num = String.valueOf(k);
                            new AlertDialog.Builder(ScanActivity.this).setIcon(R.drawable.ic_assignment_turned_in_black_24dp).setMessage("选座成功，座位号为 000"+seat_num+" , 开始点餐吗？").
                                    setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            Intent intent = new Intent(ScanActivity.this, MainActivity_r.class);
                                            seat_info.seat_num = seat_num;
                                            intent.putExtra("seat_num",seat_num);
                                            ScanActivity.this.startActivity(intent);
                                            overridePendingTransition(R.anim.fab_scale_up,R.anim.fab_scale_down);
                                        }
                                    }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            }).show();
                        }
                    }

                }
                break;
        }
    }
}
