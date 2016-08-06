package com.example.a13051_000.buffetmealsystem.restaurant.FragmentFood;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.a13051_000.buffetmealsystem.R;

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
        return rootView;
    }

}
