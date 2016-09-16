package com.example.a13051_000.buffetmealsystem.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.example.a13051_000.buffetmealsystem.R;
import com.example.a13051_000.buffetmealsystem.Res_Sales_Promotion;
import com.example.a13051_000.buffetmealsystem.restaurant.Classfication.Drink_Restaurant;
import com.example.a13051_000.buffetmealsystem.restaurant.Classfication.Rice_Restaurant;
import com.example.a13051_000.buffetmealsystem.restaurant.Classfication.Ves_Restaurant;
import com.example.a13051_000.buffetmealsystem.restaurant.Classfication.Dessert_Restaurant;
import com.example.a13051_000.buffetmealsystem.restaurant.Classfication.Meat_Restaurant;
import com.example.a13051_000.buffetmealsystem.restaurant.Classfication.Soup_Restaurant;
import com.example.a13051_000.buffetmealsystem.xml.imagelayout.FoodXmlParser;
import com.example.a13051_000.buffetmealsystem.xml.imagelayout.SlideImageLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


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
    private FoodXmlParser parser = null;

    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;

    private ImageButton imageButton_ves;
    private ImageButton imageButton_meat;
    private ImageButton imageButton_dessert;
    private ImageButton imageButton_soup;
    private ImageButton imageButton_drink;
    private ImageButton imageButton_rice;

    private ListView listView_hot;
    private List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();


    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        initeViews(rootView);

        button1 = (Button) rootView.findViewById(R.id.button1);
        button2 = (Button) rootView.findViewById(R.id.button2);
        button3 = (Button) rootView.findViewById(R.id.button3);


        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                alertDialog.show();
                Window window = alertDialog.getWindow();
                window.setContentView(R.layout.alertdialog_describe_restaurant);
                TextView tv_title = (TextView) window.findViewById(R.id.tv_dialog_title);
                tv_title.setText("餐厅概述");
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                alertDialog.show();
                Window window = alertDialog.getWindow();
                window.setContentView(R.layout.des_pic_restaurant);
                TextView tv_title = (TextView) window.findViewById(R.id.tv_dialog_title);
                tv_title.setText("餐厅图片展示");
                ViewFlipper viewFlipper = (ViewFlipper) window.findViewById(R.id.viewflipper);
                int []res = {R.drawable.res1,R.drawable.res2,R.drawable.res3,R.drawable.res4};
                for(int i = 0; i<res.length;i++){
                    viewFlipper.addView(getImageView(res[i]));
                }
                viewFlipper.setInAnimation(getActivity(),R.anim.right_in);
                viewFlipper.setOutAnimation(getActivity(),R.anim.right_out);
                viewFlipper.setFlipInterval(3000);
                viewFlipper.startFlipping();
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 Intent intent = new Intent(getActivity(), Res_Sales_Promotion.class);
                 getActivity().startActivity(intent);
                 getActivity().overridePendingTransition(R.anim.fab_fade_in,R.anim.fab_fade_out);
            }
        });


        imageButton_ves = (ImageButton) rootView.findViewById(R.id.chinese);
        imageButton_meat = (ImageButton) rootView.findViewById(R.id.foreign);
        imageButton_soup = (ImageButton) rootView.findViewById(R.id.pizza);
        imageButton_drink = (ImageButton) rootView.findViewById(R.id.bar);
        imageButton_dessert = (ImageButton) rootView.findViewById(R.id.fastfood);
        imageButton_rice = (ImageButton) rootView.findViewById(R.id.rice);

        imageButton_ves.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(getActivity(), Ves_Restaurant.class);
                startActivity(intent1);
                getActivity().overridePendingTransition(R.anim.fab_scale_up,R.anim.fab_scale_down);
            }
        });
        imageButton_dessert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(getActivity(), Dessert_Restaurant.class);
                startActivity(intent2);
                getActivity().overridePendingTransition(R.anim.fab_scale_up,R.anim.fab_scale_down);
            }
        });
        imageButton_meat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent3 = new Intent(getActivity(), Meat_Restaurant.class);
                startActivity(intent3);
                getActivity().overridePendingTransition(R.anim.fab_scale_up,R.anim.fab_scale_down);
            }
        });
        imageButton_drink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent4 = new Intent(getActivity(),Drink_Restaurant.class);
                startActivity(intent4);
                getActivity().overridePendingTransition(R.anim.fab_scale_up,R.anim.fab_scale_down);
            }
        });
        imageButton_soup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent5 = new Intent(getActivity(),Soup_Restaurant.class);
                startActivity(intent5);
                getActivity().overridePendingTransition(R.anim.fab_scale_up,R.anim.fab_scale_down);
            }
        });
        imageButton_rice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent5 = new Intent(getActivity(),Rice_Restaurant.class);
                startActivity(intent5);
                getActivity().overridePendingTransition(R.anim.fab_scale_up,R.anim.fab_scale_down);
            }
        });//菜单预览


//        listView_hot = (ListView) rootView.findViewById(R.id.list_hot);

        return rootView;

    }

    private ImageView getImageView(int res){
        ImageView image = new ImageView(getActivity());
        image.setImageResource(res);
        return image;
    }


    private void initeViews(View view){
        imagePageViews = new ArrayList<View>();
        viewPager = (ViewPager)view.findViewById(R.id.image_slide_page);

        parser = new FoodXmlParser();
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
