package com.example.a13051_000.buffetmealsystem.Fragment;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a13051_000.buffetmealsystem.R;
import com.example.a13051_000.buffetmealsystem.xml.imagelayout.NewsXmlParser;
import com.example.a13051_000.buffetmealsystem.xml.imagelayout.SlideImageLayout;

import java.util.ArrayList;


/**
 * Created by 13051_000 on 2016/7/11.
 */
public class FragmentMain extends Fragment {
    private ArrayList<View> imagePageViews = null;
    private ViewGroup main = null;
    private ViewPager viewPager = null;

    private int pageIndex = 0;
    private ViewGroup imageCircleView = null;
    private ImageView[] imageCircleViews = null;

    private TextView tvSlideTitle = null;

    private SlideImageLayout slideLayout = null;
    private NewsXmlParser parser = null;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        initeViews(rootView);
        return rootView;
    }
    private void initeViews(View view){
        imagePageViews = new ArrayList<View>();
        viewPager = (ViewPager)view.findViewById(R.id.image_slide_page);

        parser = new NewsXmlParser();
        int length = parser.getSlideImages().length;
        imageCircleView = (ViewGroup)view.findViewById(R.id.layout_circle_images);
        imageCircleViews = new ImageView[length];
        slideLayout = new SlideImageLayout(FragmentMain.this);
        slideLayout.setCircleImageLayout(length);

        for(int i = 0;i < length;i++){
            imagePageViews.add(slideLayout.getSlideImageLayout(parser.getSlideImages()[i]));
            imageCircleViews[i] = slideLayout.getCircleImageLayout(i);
            imageCircleView.addView(slideLayout.getLinearLayout(imageCircleViews[i], 10, 10));
        }

        tvSlideTitle = (TextView) view.findViewById(R.id.tvSlideTitle);
        tvSlideTitle.setText(parser.getSlideTitles()[0]);

        viewPager.setAdapter(new SlideImageAdapter());
        viewPager.setOnPageChangeListener(new ImagePageChangeListener());
    }
    private class SlideImageAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return imagePageViews.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public int getItemPosition(Object object) {
            // TODO Auto-generated method stub
            return super.getItemPosition(object);
        }

        @Override
        public void destroyItem(View arg0, int arg1, Object arg2) {
            // TODO Auto-generated method stub
            ((ViewPager) arg0).removeView(imagePageViews.get(arg1));
        }

        @Override
        public Object instantiateItem(View arg0, int arg1) {
            // TODO Auto-generated method stub
            ((ViewPager) arg0).addView(imagePageViews.get(arg1));

            return imagePageViews.get(arg1);
        }

        @Override
        public void restoreState(Parcelable arg0, ClassLoader arg1) {
            // TODO Auto-generated method stub

        }

        @Override
        public Parcelable saveState() {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public void startUpdate(View arg0) {
            // TODO Auto-generated method stub

        }

        @Override
        public void finishUpdate(View arg0) {
            // TODO Auto-generated method stub

        }
    }
    private class ImagePageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrollStateChanged(int arg0) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onPageSelected(int index) {
            pageIndex = index;
            slideLayout.setPageIndex(index);
            tvSlideTitle.setText(parser.getSlideTitles()[index]);

            for (int i = 0; i < imageCircleViews.length; i++) {
                imageCircleViews[index].setBackgroundResource(R.drawable.dot_selected);

                if (index != i) {
                    imageCircleViews[i].setBackgroundResource(R.drawable.dot_none);
                }
            }
        }
    }
}
