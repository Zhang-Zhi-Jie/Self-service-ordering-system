package com.example.a13051_000.buffetmealsystem;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by 13051_000 on 2016/4/26.
 */
public class Topbar extends RelativeLayout{

    private Button leftButton,rightButton;
    private TextView tvTitle;//自定义两个button和Textview

    private int leftTextcolor;
    private Drawable leftBackground;
    private String leftText;

    private int rightTextcolor;
    private Drawable rightBackground;
    private String rightText;

    private float titleTextsize;
    private int titleTextcolor;
    private String title;

    private LayoutParams leftParams,rightParams,titleParams;


    private topbarClickListener listener;
    public interface topbarClickListener{
        public void leftClick();
        public void rightClick();
    }
    public void setOnTobarClickListener(topbarClickListener listener){
        this.listener=listener;
    }//自定义点击


    public Topbar(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray ta=context.obtainStyledAttributes(attrs,R.styleable.Topbar);

        leftTextcolor=ta.getColor(R.styleable.Topbar_leftTextcolor,0);
        leftBackground=ta.getDrawable(R.styleable.Topbar_leftBackground);
        leftText=ta.getString(R.styleable.Topbar_leftText);

        rightTextcolor=ta.getColor(R.styleable.Topbar_rightTextcolor,0);
        rightBackground=ta.getDrawable(R.styleable.Topbar_rightBackground);
        rightText=ta.getString(R.styleable.Topbar_rightText);

        titleTextsize=ta.getDimension(R.styleable.Topbar_titleTextsize,0);
        titleTextcolor=ta.getColor(R.styleable.Topbar_titleTextcolor,0);
        title=ta.getString(R.styleable.Topbar_title1);

        ta.recycle();

        leftButton=new Button(context);
        rightButton=new Button(context);
        tvTitle=new TextView(context);//自定义三个控件（组合模式）

        leftButton.setTextColor(leftTextcolor);
        leftButton.setBackground(leftBackground);
        leftButton.setText(leftText);
        
        rightButton.setTextColor(rightTextcolor);
        rightButton.setBackground(rightBackground);
        rightButton.setText(rightText);

        tvTitle.setTextColor(titleTextcolor);
        tvTitle.setTextSize(titleTextsize);
        tvTitle.setText(title);
        tvTitle.setGravity(Gravity.CENTER);

        leftParams=new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        leftParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT,TRUE);

        addView(leftButton,leftParams);

        rightParams=new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        rightParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT,TRUE);

        addView(rightButton,rightParams);

        titleParams=new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        titleParams.addRule(RelativeLayout.CENTER_IN_PARENT,TRUE);

        addView(tvTitle,titleParams);

        leftButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.leftClick();
            }
        });
        rightButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.rightClick();
            }
        });
    }
}
