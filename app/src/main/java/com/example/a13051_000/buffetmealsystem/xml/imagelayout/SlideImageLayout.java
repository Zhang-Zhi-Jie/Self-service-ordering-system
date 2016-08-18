package com.example.a13051_000.buffetmealsystem.xml.imagelayout;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a13051_000.buffetmealsystem.Fragment.FragmentMain;
import com.example.a13051_000.buffetmealsystem.R;
import com.example.a13051_000.buffetmealsystem.ScanActivity;
import com.example.a13051_000.buffetmealsystem.Settings.SettingsActivity;
import com.example.a13051_000.buffetmealsystem.restaurant.MainActivity_r;

import java.util.ArrayList;

/**
 * Created by 13051_000 on 2016/6/14.
 */
public class SlideImageLayout {

    private ArrayList<ImageView> imageList = null;
    private FragmentMain activity = null;
    private ImageView[] imageViews = null;
    private ImageView imageView = null;
    private NewsXmlParser parser = null;
    private int pageIndex = 0;

    public SlideImageLayout(FragmentMain activity) {
        // TODO Auto-generated constructor stub
        this.activity=activity;
        imageList = new ArrayList<ImageView>();
        parser = new NewsXmlParser();
    }


    public View getSlideImageLayout(int index){
        // ����TextView��LinearLayout
        LinearLayout imageLinerLayout = new LinearLayout(activity.getActivity());
        LinearLayout.LayoutParams imageLinerLayoutParames = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1);

        ImageView iv = new ImageView(activity.getActivity());
        iv.setBackgroundResource(index);
        iv.setOnClickListener(new ImageOnClickListener());
        imageLinerLayout.addView(iv,imageLinerLayoutParames);
        imageList.add(iv);

        return imageLinerLayout;
    }

    public View getLinearLayout(View view,int width,int height){
        LinearLayout linerLayout = new LinearLayout(activity.getActivity());
        LinearLayout.LayoutParams linerLayoutParames = new LinearLayout.LayoutParams(width, height, 1);

        linerLayout.setPadding(10, 0, 10, 0);
        linerLayout.addView(view, linerLayoutParames);

        return linerLayout;
    }

    public void setCircleImageLayout(int size){
        imageViews = new ImageView[size];
    }

    public ImageView getCircleImageLayout(int index){
        imageView = new ImageView(activity.getActivity());
        imageView.setLayoutParams(new ViewGroup.LayoutParams(10,10));
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);

        imageViews[index] = imageView;

        if (index == 0) {

            imageViews[index].setBackgroundResource(R.drawable.dot_selected);
        } else {
            imageViews[index].setBackgroundResource(R.drawable.dot_none);
        }

        return imageViews[index];
    }


    public void setPageIndex(int index){
        pageIndex = index;
    }

    private class ImageOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub


            AlertDialog alertDialog = new AlertDialog.Builder(activity.getActivity()).create();
            alertDialog.show();
            Window window = alertDialog.getWindow();
            window.setContentView(R.layout.alertdialog_food_preview);
            TextView tv_title = (TextView) window.findViewById(R.id.tv_dialog_title);
            tv_title.setText(parser.getSlideTitles()[pageIndex]);
            ImageView imageview  = (ImageView) window.findViewById(R.id.show_food);
            imageview.setImageResource(parser.getSlideImages()[pageIndex]);
            TextView textView_order = (TextView) window.findViewById(R.id.text_order);
            Button button_order = (Button) window.findViewById(R.id.button_order);
            textView_order.setText("您还未选座,请点击选座并点餐");
            button_order.setText("选座");
            if(ScanActivity.visible == true&& SettingsActivity.quit == false){
                textView_order.setText("继续点餐请点击");
                button_order.setText("继续点餐");
                button_order.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent1 = new Intent(activity.getActivity(), MainActivity_r.class);
                        intent1.putExtra("seat_num",ScanActivity.seat_num);
                        activity.getActivity().startActivity(intent1);
                    }
                });
            }else {

                button_order.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(activity.getActivity(), ScanActivity.class);
                        activity.getActivity().startActivity(intent);
                    }
                });
            }
            Toast.makeText(activity.getActivity(), parser.getSlideTitles()[pageIndex], Toast.LENGTH_SHORT).show();

        }
    }
}

