package com.thanhclub.musicdemo.ui;

import android.annotation.SuppressLint;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TableLayout;

import com.thanhclub.musicdemo.R;
import com.thanhclub.musicdemo.adapter.TabPagerApdapter;

import static com.thanhclub.musicdemo.R.id.tab_layout;

public class MainActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponents();
    }

    private void initComponents() {
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        viewPager.setAdapter(new TabPagerApdapter(getSupportFragmentManager()));
    }
}
