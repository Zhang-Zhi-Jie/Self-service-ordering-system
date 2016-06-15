package com.example.a13051_000.buffetmealsystem.xml.imagelayout;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.a13051_000.buffetmealsystem.FragmentAt;
import com.example.a13051_000.buffetmealsystem.R;

import java.util.ArrayList;

/**
 * Created by 13051_000 on 2016/6/14.
 */
public class SlideImageLayout {

    private ArrayList<ImageView> imageList = null;
    private FragmentAt activity = null;
    private ImageView[] imageViews = null;
    private ImageView imageView = null;
    private NewsXmlParser parser = null;
    private int pageIndex = 0;

    public SlideImageLayout(FragmentAt activity) {
        // TODO Auto-generated constructor stub
        this.activity=activity;
        imageList = new ArrayList<ImageView>();
        parser = new NewsXmlParser();
    }


    public View getSlideImageLayout(int index){
        // ����TextView��LinearLayout
        LinearLayout imageLinerLayout = new LinearLayout();
        LinearLayout.LayoutParams imageLinerLayoutParames = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1);

        ImageView iv = new ImageView(activity);
        iv.setBackgroundResource(index);
        iv.setOnClickListener(new ImageOnClickListener());
        imageLinerLayout.addView(iv,imageLinerLayoutParames);
        imageList.add(iv);

        return imageLinerLayout;
    }

    public View getLinearLayout(View view,int width,int height){
        LinearLayout linerLayout = new LinearLayout(activity);
        LinearLayout.LayoutParams linerLayoutParames = new LinearLayout.LayoutParams(width, height, 1);

        linerLayout.setPadding(10, 0, 10, 0);
        linerLayout.addView(view, linerLayoutParames);

        return linerLayout;
    }

    public void setCircleImageLayout(int size){
        imageViews = new ImageView[size];
    }

    public ImageView getCircleImageLayout(int index){
        imageView = new ImageView(activity);
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
            Toast.makeText(activity, parser.getSlideTitles()[pageIndex], Toast.LENGTH_SHORT).show();

        }
    }
}

