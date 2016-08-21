package com.example.a13051_000.buffetmealsystem.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import android.widget.TextView;

import com.example.a13051_000.buffetmealsystem.ExplosionField.ExplosionField;
import com.example.a13051_000.buffetmealsystem.Scan.ScanActivity;
import com.example.a13051_000.buffetmealsystem.Settings.SettingsActivity;
import com.example.a13051_000.buffetmealsystem.restaurant.MainActivity_r;

import com.example.a13051_000.buffetmealsystem.R;


/**
 * Created by 13051_000 on 2016/7/11.
 */
public class FragmentOrder extends Fragment  {

    private ExplosionField mExplosionField;
    ImageView imageView_h;
    ImageView imageView_click;
    TextView textView_order;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_order, container, false);
        mExplosionField = ExplosionField.attach2Window(getActivity());
        addListener(rootView.findViewById(R.id.root));
        imageView_click = (ImageView) rootView.findViewById(R.id.click);
        textView_order = (TextView) rootView.findViewById(R.id.text_order);
        textView_order.setText("您还未选座，点击选座并点餐");
        imageView_h = (ImageView) rootView.findViewById(R.id.hh);
        imageView_h.setImageResource(R.drawable.p5);
        if(ScanActivity.visible == true&& SettingsActivity.quit == false){
            textView_order.setText("继续点餐请点击");
            imageView_h.setImageResource(R.drawable.hh);
            imageView_click.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent1 = new Intent(getActivity(), MainActivity_r.class);
                    intent1.putExtra("seat_num",ScanActivity.seat_num);
                    getActivity().startActivity(intent1);
                }
            });
        }else {
            imageView_click.setOnClickListener(new View.OnClickListener() {
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
