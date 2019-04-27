//DUY: CHỨC NĂNG ADMIN
package com.example.quang.studenthousing;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

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

    //menu trong giao diện admin
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //đăng xuất khỏi tài khoản admin
        if (id == R.id.action_logout){
            SharedPreferences pre = getSharedPreferences("studenthousing", MODE_PRIVATE);
            SharedPreferences.Editor edit=pre.edit();
            edit.putString("user","");
            edit.commit();
            startActivity(new Intent(this,AccountActivity.class));
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //khơi tạo menu trong admin
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //icon đăng xuất
        getMenuInflater().inflate(R.menu.menu_manager, menu);
        return true;
    }

}
