package com.example.a13051_000.buffetmealsystem.Order;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a13051_000.buffetmealsystem.R;
import com.example.a13051_000.buffetmealsystem.Order.Test;
import com.example.a13051_000.buffetmealsystem.Sqlite.MySQLiteHelper;
import com.example.a13051_000.buffetmealsystem.Sqlite.OrderForm;
import com.example.a13051_000.buffetmealsystem.Sqlite.OrderformDataSource;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by 13051_000 on 2016/8/7.
 */
public class OrderCarAdapter extends BaseAdapter {
    private Context context;
    private List<Test> list;
    private LayoutInflater inflater;
    private static HashMap<Integer, Boolean> isSelected;
    private static HashMap<Integer, Integer> numbers;
    private Handler handler;
    private OrderformDataSource orderformDataSource;
    int num;// 商品数量



    public static class ViewHolder{
        CheckBox ck_select;
        TextView textview_clear;
        TextView integer_sum;
        Button minus;
        Button plus;
        TextView number;
        TextView id;
        TextView name;
        TextView price;
    }

    public OrderCarAdapter(Context context, Handler handler, List<Test> data){
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.list = data;
        this.handler = handler;
        isSelected = new HashMap<Integer, Boolean>();
        numbers = new HashMap<Integer, Integer>();
        orderformDataSource = new OrderformDataSource(context);
        orderformDataSource.open();
        initDate();

    }

    private void initDate() {
        for (int i = 0; i < list.size(); i++) {
            getIsSelected().put(i, false);
            String id = Objects.toString(list.get(i).getId());
            getNumbers().put(i,(int)orderformDataSource.getForm(id).getNum());
        }
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if(view == null){
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.ordercar_item_list,null);
            holder.ck_select = (CheckBox) view.findViewById(R.id.ck_select);
            holder.textview_clear = (TextView) view.findViewById(R.id.clear);
            holder.integer_sum = (TextView) view.findViewById(R.id.integer_sum);
            holder.minus = (Button) view.findViewById(R.id.minus);
            holder.plus = (Button) view.findViewById(R.id.plus);
            holder.number = (TextView) view.findViewById(R.id.number);
            holder.id = (TextView) view.findViewById(R.id.showId);
            holder.name = (TextView) view.findViewById(R.id.showname);
            holder.price = (TextView) view.findViewById(R.id.showprice);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }

        init(holder, i);

        holder.ck_select.setChecked(getIsSelected().get(i));
        holder.number.setText(getNumbers().get(i).toString());
        if(getIsSelected().get(i)){
            holder.ck_select.setChecked(true);
        }else{
            holder.ck_select.setChecked(false);
        }

        String a = holder.number.getText().toString();


        Test test = list.get(i);
        holder.id.setText(test.getId());
        holder.name.setText((CharSequence)test.getName());
        holder.price.setText((CharSequence)test.getPrice());

        return view;
    }

    private void init(final ViewHolder holder, final int i) {
              holder.ck_select.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                  @Override
                  public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                      isSelected.put(i,true);
                      getIsSelected().put(i,b);
                      holder.ck_select.setChecked(getIsSelected().get(i));

                      handler.sendMessage(handler.obtainMessage(10,
                              getTotalPrice()));
                      num = (int)orderformDataSource.getForm(list.get(i).getId()).getNum();
                      list.get(i).setNum(num);
                      Iterator iterator = isSelected.entrySet().iterator();
                      List<Boolean>array = new ArrayList<Boolean>();
                      while(iterator.hasNext()){
                          HashMap.Entry entry = (HashMap.Entry)iterator.next();
                          Integer key = (Integer) entry.getKey();
                          Boolean val = (Boolean) entry.getValue();
                          array.add(val);
                      }
                      handler.sendMessage(handler.obtainMessage(11, array));
                      handler.sendMessage(handler.obtainMessage(10,
                              getTotalPrice()));
                  }
              });
        final Integer numString = (int)orderformDataSource.getForm(list.get(i).getId()).getNum();
        Log.d("numString",String.valueOf(numString));
        num = numString;
        holder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(numString == null || numString.equals("")){
                    num = (int)orderformDataSource.getForm(list.get(i).getId()).getNum();
                }else{
                    if(++num<1){
                        num--;
                        Toast.makeText(context,"请输入一个大于0的数字",Toast.LENGTH_SHORT).show();
                    }else{
                        holder.number.setText(String.valueOf(num));
                        list.get(i).setNum(num);
                        numbers.put(i,num);
                        orderformDataSource.addNum(String.valueOf(list.get(i).getId()));
                        handler.sendMessage(handler.obtainMessage(10,
                                getTotalPrice()));
                        Log.i("test","+:"+num);
                    }
                }
            }
        });
        holder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(numString == null || numString.equals("")){
                    num = (int)orderformDataSource.getForm(list.get(i).getId()).getNum();
                }else{
                    if(--num<1){
                        num++;
                        Log.i("test", "-:" + num);
                        Toast.makeText(context, "请输入一个大于0的数字",
                                Toast.LENGTH_SHORT).show();
                        Log.i("test", "-:" + num);
                    }else {
                        holder.number.setText(String.valueOf(num));
                        Log.i("test", "-:" + num);
                        list.get(i).setNum(num);
                        numbers.put(i,num);
                        orderformDataSource.subNum(String.valueOf(list.get(i).getId()));
                        handler.sendMessage(handler.obtainMessage(10,
                                getTotalPrice()));
                    }
                }
            }
        });
    }
    private float getTotalPrice() {
        Test bean;
        float totalPrice = 0;
        for (int i = 0; i < list.size(); i++) {
            bean = list.get(i);
            if (OrderCarAdapter.getIsSelected().get(i)) {
                totalPrice += bean.getNum()*Float.valueOf(bean.getPrice());
            }
        }
        return totalPrice;
    }

    public static HashMap<Integer,Boolean> getIsSelected() {
        return isSelected;
    }

    public static void setIsSelected(HashMap<Integer, Boolean> isSelected) {
        OrderCarAdapter.isSelected = isSelected;
    }
    public static HashMap<Integer,Integer> getNumbers() {
        return numbers;
    }
    public static void setNumbers(HashMap<Integer, Integer> numbers) {
        OrderCarAdapter.numbers = numbers;
    }
}
