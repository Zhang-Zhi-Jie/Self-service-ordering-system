package com.example.a13051_000.buffetmealsystem;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a13051_000.buffetmealsystem.FileData.UserData;
import com.example.a13051_000.buffetmealsystem.Fragment.FragmentMain;
import com.example.a13051_000.buffetmealsystem.Fragment.FragmentOrder;
import com.example.a13051_000.buffetmealsystem.Fragment.FragmentForm.FragmentOrderForm;
import com.example.a13051_000.buffetmealsystem.Order.OrderCar;
import com.example.a13051_000.buffetmealsystem.Scan.ScanActivity;
import com.example.a13051_000.buffetmealsystem.Settings.SettingsActivity;
import com.example.a13051_000.buffetmealsystem.User.UserActivity;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private AppBarLayout appBarLayout;
    private ViewPager viewPager;
    private TabLayout tabLayout;

    private String titles[] = {"主页","点餐","订单"};
    private FragmentMain mainFragment = new FragmentMain();
    private FragmentOrder orderFragment = new FragmentOrder();
    private FragmentOrderForm orderFormFragment = new FragmentOrderForm();

    private ImageView imageView;
    private SearchView searchView;
    private ListView listView;
    private TextView edit;

    private Calendar cal;
    private int year;
    private int month;
    private int day;

    private static String sResult = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        viewPager = (ViewPager)findViewById(R.id.viewpager);
        tabLayout = (TabLayout)findViewById(R.id.tablyout);
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                if (position == 0) {
                    return mainFragment;
                } else if(position == 1){
                    return orderFragment;
                }
                else {
                    return orderFormFragment;
                }
            }

            @Override
            public int getCount() {
                return titles.length;
            }
            public CharSequence getPageTitle(int position){return titles[position];}
        });
        tabLayout.setupWithViewPager(viewPager);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intent = new Intent(MainActivity.this, OrderCar.class);
                MainActivity.this.startActivity(intent);
                overridePendingTransition(R.anim.fab_fade_in,R.anim.fab_fade_out);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);
        TextView showtime = (TextView) header.findViewById(R.id.showtime);
        TextView nickname = (TextView)header.findViewById(R.id.shownickname);
        imageView = (ImageView) header.findViewById(R.id.imageView);
        final String username = LoginActivity.userData.GetName();
        nickname.setText(""+username+"");
        nickname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(MainActivity.this,UserActivity.class);
                intent1.putExtra("nickname",username);
                MainActivity.this.startActivity(intent1);
                overridePendingTransition(R.anim.fab_fade_in,R.anim.fab_fade_out);
            }
        });
        edit = (TextView) header.findViewById(R.id.edit);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(MainActivity.this,UserActivity.class);
                intent2.putExtra("nickname",username);
                MainActivity.this.startActivity(intent2);
                overridePendingTransition(R.anim.fab_fade_in,R.anim.fab_fade_out);
            }
        });

        cal = Calendar.getInstance();
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH);
        day = cal.get(Calendar.DAY_OF_MONTH);
        showtime.setText(year+"年"+(month+1)+"月"+day+"日");
    }

    private static final int TIME_INTERVAL = 2000; // # milliseconds, desired time passed between two back presses.
    private long mBackPressed;

    @Override
    public void onBackPressed()
    {
        if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis())
        {
            super.onBackPressed();
            return;
        }
        else {
            Toast toast = Toast.makeText(MainActivity.this,"再按一次即可退出。",Toast.LENGTH_SHORT);
            View view = toast.getView();
            view.setBackgroundColor(Color.parseColor("#FF8C00"));
            toast.setView(view);
            toast.show();
        }

        mBackPressed = System.currentTimeMillis();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent1 = new Intent(MainActivity.this,SettingsActivity.class);
            this.startActivity(intent1);
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_manage) {
            Intent intent = new Intent(MainActivity.this,ToolActivity.class);
            MainActivity.this.startActivity(intent);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
