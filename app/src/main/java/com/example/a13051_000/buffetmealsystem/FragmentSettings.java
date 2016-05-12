package com.example.a13051_000.buffetmealsystem;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by 13051_000 on 2016/5/10.
 */
public class FragmentSettings  extends Fragment {

    public String[] data = {"意见反馈", "服务条款", "检查更新", "关于我们"};

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_settings, container, false);
    }
}
