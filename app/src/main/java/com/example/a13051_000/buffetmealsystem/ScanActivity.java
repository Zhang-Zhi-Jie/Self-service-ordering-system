package com.example.a13051_000.buffetmealsystem;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by 13051_000 on 2016/5/12.
 */
public class ScanActivity extends AppCompatActivity {
    private final static int SCANNIN_GREQUEST_CODE = 1;

    private TextView mTextView ;

    private ImageView mImageView;

    protected  void onCreate(Bundle savedIntanceState){
        super.onCreate(savedIntanceState);
        setContentView(R.layout.activity_scan);
        mTextView = (TextView) findViewById(R.id.result);
        mImageView = (ImageView) findViewById(R.id.qrcode_bitmap);
        Topbar topbar = (Topbar) findViewById(R.id.topbar1);
        topbar.setOnTobarClickListener(new Topbar.topbarClickListener() {
            @Override
            public void leftClick() {
                Intent intent = new Intent(ScanActivity.this,MainActivity.class);
                startActivity(intent);
            }
            @Override
            public void rightClick() {

            }
        });
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case SCANNIN_GREQUEST_CODE:
                if(resultCode == RESULT_OK){
                    Bundle bundle = data.getExtras();

                    mTextView.setText(bundle.getString("result"));

                    mImageView.setImageBitmap((Bitmap) data.getParcelableExtra("bitmap"));
                }
                break;
        }
    }
}
