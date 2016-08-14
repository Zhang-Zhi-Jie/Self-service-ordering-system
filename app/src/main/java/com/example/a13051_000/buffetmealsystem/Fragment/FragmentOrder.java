package com.example.a13051_000.buffetmealsystem.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import android.widget.TextView;
import android.widget.Toast;

import com.example.a13051_000.buffetmealsystem.ExplosionField.ExplosionField;
import com.example.a13051_000.buffetmealsystem.HttpUtils;
import com.example.a13051_000.buffetmealsystem.Order.OrderDetailActivity;
import com.example.a13051_000.buffetmealsystem.Order.Result_Spoon_detail;
import com.example.a13051_000.buffetmealsystem.ScanActivity;
import com.example.a13051_000.buffetmealsystem.Settings.SettingsActivity;
import com.example.a13051_000.buffetmealsystem.Status;
import com.example.a13051_000.buffetmealsystem.restaurant.MainActivity_r;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.a13051_000.buffetmealsystem.R;


/**
 * Created by 13051_000 on 2016/7/11.
 */
public class FragmentOrder extends Fragment  {

    private ExplosionField mExplosionField;
    ImageView imageView;
    TextView textView_begin;
    TextView textView_continue;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_order, container, false);
        mExplosionField = ExplosionField.attach2Window(getActivity());
        addListener(rootView.findViewById(R.id.root));
        imageView = (ImageView) rootView.findViewById(R.id.click);
        textView_begin = (TextView) rootView.findViewById(R.id.begin_order);
        textView_continue = (TextView) rootView.findViewById(R.id.continue_order);
        textView_continue.setVisibility(View.INVISIBLE);
        if(ScanActivity.visible == true&& SettingsActivity.quit == false){
            textView_begin.setVisibility(View.INVISIBLE);
            textView_continue.setVisibility(View.VISIBLE);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent1 = new Intent(getActivity(), MainActivity_r.class);
                    intent1.putExtra("seat_num",ScanActivity.seat_num);
                    getActivity().startActivity(intent1);
                }
            });
        }else {

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), ScanActivity.class);
                    getActivity().startActivity(intent);
                }
            });
        }
        return rootView;
    }

    private void addListener(View root) {
        if (root instanceof ViewGroup) {
            ViewGroup parent = (ViewGroup) root;
            for (int i = 0; i < parent.getChildCount(); i++) {
                addListener(parent.getChildAt(i));
            }
        } else {
            root.setClickable(true);
            root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mExplosionField.explode(v);
                    v.setOnClickListener(null);
                }
            });
        }
    }
}
