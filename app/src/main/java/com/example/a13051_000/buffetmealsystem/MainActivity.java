package com.example.a13051_000.buffetmealsystem;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;

import org.jetbrains.annotations.Nullable;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private ImageView imageView_spoon;
    private ImageView imageView_user;
    private ImageView imageView_setting;

    // 定义Fragment页面
    private FragmentAt fragmentAt;
    private FragmentSpoon fragmentSpoon;
    private FragmentUser fragmentUser;
    private FragmentSettings fragmentSettings;

    // 定义布局对象
    private FrameLayout atFl, authFl, spaceFl, moreFl;

    // 定义图片组件对象
    private ImageView atIv, authIv, spaceIv, moreIv;

    // 定义按钮图片组件
    private ImageView toggleImageView, plusImageView;

    // 定义PopupWindow
    private PopupWindow popWindow;
    // 获取手机屏幕分辨率的类
    private DisplayMetrics dm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // Toolbar toolbar = (Toolbar) MainActivity.findViewById(R.id.toolbar);
       // setSupportActionBar(toolbar);

        ActivityCollector.addActivity(this);


        initView();

        initData();

        // 初始化默认为选中点击了“动态”按钮
        clickAtBtn();
    }

    /**
     * 初始化组件
     */
    private void initView() {
        // 实例化布局对象
        atFl = (FrameLayout) findViewById(R.id.layout_at);
        authFl = (FrameLayout) findViewById(R.id.layout_auth);
        spaceFl = (FrameLayout) findViewById(R.id.layout_space);
        moreFl = (FrameLayout) findViewById(R.id.layout_more);

        // 实例化图片组件对象
        atIv = (ImageView) findViewById(R.id.image_main);
        authIv = (ImageView) findViewById(R.id.image_spoon);
        spaceIv = (ImageView) findViewById(R.id.image_user);
        moreIv = (ImageView) findViewById(R.id.image_setting);

    }

    /**
     * 初始化数据
     */
    private void initData() {
        // 给布局对象设置监听
        atFl.setOnClickListener(this);
        authFl.setOnClickListener(this);
        spaceFl.setOnClickListener(this);
        moreFl.setOnClickListener(this);
    }

    /**
     * 点击事件
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            // 点击动态按钮
            case R.id.layout_at:
                clickAtBtn();
                break;
            // 点击与我相关按钮
            case R.id.layout_auth:
                clickAuthBtn();
                break;
            // 点击我的空间按钮
            case R.id.layout_space:
                clickSpaceBtn();
                break;
            // 点击更多按钮
            case R.id.layout_more:
                clickMoreBtn();
                break;
        }
    }


    private void clickAtBtn() {
        // 实例化Fragment页面
        fragmentAt = new FragmentAt();
        // 得到Fragment事务管理器
        FragmentTransaction fragmentTransaction = this
                .getSupportFragmentManager().beginTransaction();
        // 替换当前的页面
        fragmentTransaction.replace(R.id.frame_content, fragmentAt);
        // 事务管理提交
        fragmentTransaction.commit();
        // 改变选中状态
        atFl.setSelected(true);
        atIv.setSelected(true);

        authFl.setSelected(false);
        authIv.setSelected(false);

        spaceFl.setSelected(false);
        spaceIv.setSelected(false);

        moreFl.setSelected(false);
        moreIv.setSelected(false);
    }


    private void clickAuthBtn() {
        // 实例化Fragment页面
        fragmentSpoon = new FragmentSpoon();
        // 得到Fragment事务管理器
        FragmentTransaction fragmentTransaction = this
                .getSupportFragmentManager().beginTransaction();
        // 替换当前的页面
        fragmentTransaction.replace(R.id.frame_content, fragmentSpoon);
        // 事务管理提交
        fragmentTransaction.commit();

        atFl.setSelected(false);
        atIv.setSelected(false);

        authFl.setSelected(true);
        authIv.setSelected(true);

        spaceFl.setSelected(false);
        spaceIv.setSelected(false);

        moreFl.setSelected(false);
        moreIv.setSelected(false);
    }


    private void clickSpaceBtn() {
        // 实例化Fragment页面
        fragmentUser = new FragmentUser();
        // 得到Fragment事务管理器
        FragmentTransaction fragmentTransaction = this
                .getSupportFragmentManager().beginTransaction();
        // 替换当前的页面
        fragmentTransaction.replace(R.id.frame_content, fragmentUser);
        // 事务管理提交
        fragmentTransaction.commit();

        atFl.setSelected(false);
        atIv.setSelected(false);

        authFl.setSelected(false);
        authIv.setSelected(false);

        spaceFl.setSelected(true);
        spaceIv.setSelected(true);

        moreFl.setSelected(false);
        moreIv.setSelected(false);
    }


    private void clickMoreBtn() {
        // 实例化Fragment页面
        fragmentSettings = new FragmentSettings();
        // 得到Fragment事务管理器
        FragmentTransaction fragmentTransaction = this
                .getSupportFragmentManager().beginTransaction();
        // 替换当前的页面
        fragmentTransaction.replace(R.id.frame_content, fragmentSettings);
        // 事务管理提交
        fragmentTransaction.commit();

        atFl.setSelected(false);
        atIv.setSelected(false);

        authFl.setSelected(false);
        authIv.setSelected(false);

        spaceFl.setSelected(false);
        spaceIv.setSelected(false);

        moreFl.setSelected(true);
        moreIv.setSelected(true);
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }
    @Override
    public void onBackPressed(){
        ActivityCollector.finishAll();
    }

}



