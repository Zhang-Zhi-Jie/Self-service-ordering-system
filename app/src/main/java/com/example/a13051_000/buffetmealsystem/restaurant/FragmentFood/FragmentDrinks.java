package com.example.a13051_000.buffetmealsystem.restaurant.FragmentFood;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.a13051_000.buffetmealsystem.Order.OrderDetailActivity;
import com.example.a13051_000.buffetmealsystem.Order.Result_Spoon_detail;
import com.example.a13051_000.buffetmealsystem.R;
import com.example.a13051_000.buffetmealsystem.Sqlite.OrderFormDataSource_menu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.greenrobot.event.EventBus;
import github.chenupt.multiplemodel.ModelListAdapter;

/**
 * Created by 13051_000 on 2016/7/16.
 */
public class FragmentDrinks extends Fragment {
    private ListView listView;
    private ModelListAdapter adapter;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_drinks, container, false);
        ListView menuList = (ListView) rootView.findViewById(R.id.list_view);
        OrderFormDataSource_menu orderFormDataSource_menu = new OrderFormDataSource_menu(getContext());
        orderFormDataSource_menu.open();
        List<Result_Spoon_detail> result_spoon_details = orderFormDataSource_menu.getAllMenu();
        final List<Map<String, Object>> listItems = new ArrayList<Map<String, Object>>();
        String[] arg1 = new String[result_spoon_details.size()];
        for(int i= 0;i<result_spoon_details.size();i++){
            Log.d("classify",String.valueOf(i));
            if (result_spoon_details.get(i).getClassify().equals("6")) {
                Map<String, Object> listitem = new HashMap<String, Object>();
                listitem.put("ID", result_spoon_details.get(i).getId());
                listitem.put("Name", result_spoon_details.get(i).getDish_name());
                listitem.put("PRICE", result_spoon_details.get(i).getPrice());
                listitem.put("unit", result_spoon_details.get(i).getUnit());
                listItems.add(listitem);
                arg1[i] = listitem.toString();
            }
        }
        SimpleAdapter orderAdapter = new SimpleAdapter(getActivity(), listItems, R.layout.fragment_order_item_list_module, new String[]{"ID", "Name", "PRICE", "unit"},
                new int[]{R.id.order_db_id, R.id.order_db_name, R.id.order_db_price, R.id.order_db_create_at});
        menuList.setAdapter(orderAdapter);
        //      ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,arg1);
        //     menuList.setAdapter(adapter);
        Log.d("data_orderlist",listItems.toString());
        String nick_name = "";
        menuList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String id= (String)listItems.get(i).get("ID");
                String name= (String)listItems.get(i).get("Name");
                String price= (String)listItems.get(i).get("PRICE");
                String perunit= (String)listItems.get(i).get("unit");
                Intent intent = new Intent(getActivity(),OrderDetailActivity.class);
                intent.putExtra("id",id);
                intent.putExtra("name",name);
                intent.putExtra("price",price);
                intent.putExtra("perunit",perunit);
                startActivity(intent);
            }
        });
        return rootView;
    }

}
