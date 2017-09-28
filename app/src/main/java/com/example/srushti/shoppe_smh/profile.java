package com.example.srushti.shoppe_smh;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.apache.http.entity.StringEntity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class profile extends AppCompatActivity implements ActionBar.TabListener,wishlist.OnFragmentInteractionListener1,product_on_sell.OnFragmentInteractionListener{

    ActionBar mActionBar;
    ViewPager viewPager;
    String str=null;

    SectionPagerAdapter mSectionPagerAdapter;
    global_class globalClass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        viewPager= (ViewPager) findViewById(R.id.profile_viewpager);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#009688")));

        mSectionPagerAdapter=new SectionPagerAdapter(getSupportFragmentManager());

        mActionBar = getSupportActionBar();
        globalClass= (global_class) getApplicationContext();
        getSupportActionBar().setTitle(globalClass.getEmail());

        mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        for(int i=0;i<mSectionPagerAdapter.getCount();i++)
        {
            mActionBar.addTab(mActionBar.newTab().setText(mSectionPagerAdapter.getPageTitle(i)).setTabListener(profile.this));
        }
        viewPager.setAdapter(mSectionPagerAdapter);
        viewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                mActionBar.setSelectedNavigationItem(position);
            }
        });

    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public class SectionPagerAdapter extends FragmentPagerAdapter {
        public SectionPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position)
            {
                case 0:
                    return wishlist.newInstance(globalClass.getEmail(),"");
                case 1:
                    return product_on_sell.newInstance(globalClass.getEmail(),"");
                default:
                    return wishlist.newInstance(globalClass.getEmail(),"");
            }
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position)
            {
                case 0:
                    return "Wishlist";
                case 1:
                    return "For sell";
                case 2:
                    return "Wishlist";
            }
            return null;
        }

        @Override
        public int getCount() {
            return 2;
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }
}
