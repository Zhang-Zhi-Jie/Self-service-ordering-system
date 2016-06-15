package com.example.a13051_000.buffetmealsystem;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by 13051_000 on 2016/5/10.
 */
public class FragmentUser  extends Fragment {
    private TextView nickname;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_user, container, false);
        nickname = (TextView) rootView.findViewById(R.id.shownickname);
        nickname.setText(LoginActivity.nickname+"");
        return rootView;


    }

}