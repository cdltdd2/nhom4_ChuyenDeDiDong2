package com.example.quang.studenthousing;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.quang.studenthousing.adapter.FragmentAdapter;

public class ManagerActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private FragmentAdapter fragmentAdapter;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);

        findId();
        initViews();
    }

    private void findId() {
        viewPager = findViewById(R.id.vpPager);
        toolbar = findViewById(R.id.toolbarManager);
    }

    private void initViews() {
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle(R.string.manager);

        fragmentAdapter = new FragmentAdapter(this, getSupportFragmentManager());
        viewPager.setAdapter(fragmentAdapter);

    }


}
