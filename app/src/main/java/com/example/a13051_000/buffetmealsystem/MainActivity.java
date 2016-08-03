package com.example.a13051_000.buffetmealsystem;

import android.content.Intent;
import android.os.Bundle;
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

import com.example.a13051_000.buffetmealsystem.Fragment.FragmentMain;
import com.example.a13051_000.buffetmealsystem.Fragment.FragmentOrder;
import com.example.a13051_000.buffetmealsystem.Fragment.FragmentForm.FragmentOrderForm;
import com.example.a13051_000.buffetmealsystem.Order.OrderCar;
import com.example.a13051_000.buffetmealsystem.Settings.SettingsActivity;
import com.example.a13051_000.buffetmealsystem.User.UserActivity;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ViewPager viewPager;
    private TabLayout tabLayout;

    private String titles[] = {"主页","点餐","订单"};
    private FragmentMain mainFragment = new FragmentMain();
    private FragmentOrder orderFragment = new FragmentOrder();
    private FragmentOrderForm orderFormFragment = new FragmentOrderForm();

    private ImageView imageView;
    private SearchView searchView;
    private ListView listView;

    private Calendar cal;
    private int year;
    private int month;
    private int day;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        viewPager = (ViewPager)findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tablyout);
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
                MainActivity.this.finish();
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
        final TextView nickname = (TextView)header.findViewById(R.id.shownickname);
        imageView = (ImageView) header.findViewById(R.id.imageView);
        final Intent intent = getIntent();
        final String username = intent.getStringExtra("nickname");
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

        cal = Calendar.getInstance();
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH);
        day = cal.get(Calendar.DAY_OF_MONTH);
        showtime.setText(year+"年"+(month+1)+"月"+day+"日");
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem menuItem = menu.findItem(R.id.ab_search);
        searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        MenuItemCompat.setOnActionExpandListener(menuItem, new MenuItemCompat.OnActionExpandListener() {//设置打开关闭动作监听
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                Toast.makeText(MainActivity.this, "onExpand", Toast.LENGTH_SHORT).show();
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                Toast.makeText(MainActivity.this, "Collapse", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
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
        if(id == R.id.action_sao){
            Intent intent = new Intent(MainActivity.this,ScanActivity.class);
            this.startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
