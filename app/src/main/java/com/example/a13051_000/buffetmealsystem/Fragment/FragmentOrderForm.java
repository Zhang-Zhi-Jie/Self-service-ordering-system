package com.example.a13051_000.buffetmealsystem.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.a13051_000.buffetmealsystem.R;

/**
 * Created by 13051_000 on 2016/7/24.
 */
public class FragmentOrderForm extends Fragment {
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_orderform, container, false);
        return rootView;
    }
}
