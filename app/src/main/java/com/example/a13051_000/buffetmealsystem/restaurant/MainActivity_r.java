/*
 * Copyright 2015 chenupt
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.a13051_000.buffetmealsystem.restaurant;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.astuetz.PagerSlidingTabStrip;
import com.example.a13051_000.buffetmealsystem.MainActivity;
import com.example.a13051_000.buffetmealsystem.Order.OrderCar;
import com.example.a13051_000.buffetmealsystem.R;
import com.example.a13051_000.buffetmealsystem.restaurant.FragmentFood.FragmentDessert;
import com.example.a13051_000.buffetmealsystem.restaurant.FragmentFood.FragmentDrinks;
import com.example.a13051_000.buffetmealsystem.restaurant.FragmentFood.FragmentMeat;
import com.example.a13051_000.buffetmealsystem.restaurant.FragmentFood.FragmentSoup;
import com.example.a13051_000.buffetmealsystem.restaurant.FragmentFood.FragmentStableFood;
import com.example.a13051_000.buffetmealsystem.restaurant.FragmentFood.FragmentVegetable;
import com.google.common.collect.*;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import github.chenupt.multiplemodel.viewpager.ModelPagerAdapter;
import github.chenupt.multiplemodel.viewpager.PagerModelManager;

public class MainActivity_r extends AppCompatActivity {


    public static final String TAG = "MainActivity_r";

    private Toolbar toolbar;
    private TextView textView_finish;

    private DragTopLayout dragLayout;
    private ModelPagerAdapter adapter;
    private ViewPager viewPager;
    private PagerSlidingTabStrip pagerSlidingTabStrip;
    private TextView textView_seat_num;

    String seat_num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        pagerSlidingTabStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);

        toolbar.setTitle("餐厅菜单");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        textView_finish = (TextView) findViewById(R.id.finish);
        textView_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(MainActivity_r.this).setMessage("您确定已经点完餐了吗？").setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast toast = Toast.makeText(getApplicationContext(), "将转入到购物车", Toast.LENGTH_SHORT);
                        View view = toast.getView();
                        view.setBackgroundColor(Color.parseColor("#FF8C00"));
                        toast.setView(view);
                        toast.show();

                        Intent intent = new Intent(MainActivity_r.this, OrderCar.class);
                        MainActivity_r.this.startActivity(intent);
                        MainActivity_r.this.finish();
                    }
                }).show();
            }
        });

        PagerModelManager factory = new PagerModelManager();
        factory.addCommonFragment(getFragments(), getTitles());
        adapter = new ModelPagerAdapter(getSupportFragmentManager(), factory);
        viewPager.setAdapter(adapter);
        pagerSlidingTabStrip.setViewPager(viewPager);

        Intent intent = getIntent();
        seat_num = intent.getStringExtra("seat_num");
        textView_seat_num = (TextView) findViewById(R.id.show_seat_num);
        if (Integer.valueOf(seat_num) < 10) {
            textView_seat_num.setText("0000" + seat_num + "号");
        } else {
            textView_seat_num.setText("000" + seat_num + "号");
        }
    }

    private List<String> getTitles() {
        return Lists.newArrayList("   菜类   ", "   肉类   ", "   汤类   ", "   主食   ", "   甜品   ", "  酒水  ");
    }

    private List<Fragment> getFragments() {
        List<Fragment> list = new ArrayList<>();

        Fragment FragmentMeat = new FragmentMeat();
        Fragment FragmentVegetable = new FragmentVegetable();
        Fragment FragmentSoup = new FragmentSoup();
        Fragment FragmentStableFood = new FragmentStableFood();
        Fragment FragmentDessert = new FragmentDessert();
        Fragment FragmentDrinks = new FragmentDrinks();

        list.add(FragmentVegetable);
        list.add(FragmentMeat);
        list.add(FragmentSoup);
        list.add(FragmentStableFood);
        list.add(FragmentDessert);
        list.add(FragmentDrinks);
        return list;
    }

    public void onEvent(Boolean b) {
        dragLayout.setTouchMode(b);
    }

    @Override
    protected void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return onOptionsItemSelected(item);
    }
}
