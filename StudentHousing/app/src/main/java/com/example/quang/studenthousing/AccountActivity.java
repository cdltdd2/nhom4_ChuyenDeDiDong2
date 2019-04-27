//ĐẠT: XỬ LÝ ACCOUNT
//CHUYỂN GIAO DIỆN PHÙ HỢP CHO TỪNG LOẠI ACCOUNT
package com.example.quang.studenthousing;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.quang.studenthousing.view.login.LoginFragment;

public class AccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        //LOAD GIAO DIỆN LOGIN
        loadFragment(new LoginFragment());
    }

    public void loadFragment(Fragment fragment) {
        // load fragment
        if (getSupportFragmentManager().getBackStackEntryCount() > 0){
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction trans = manager.beginTransaction();
            trans.remove(manager.getFragments().get(manager.getFragments().size() - 1));
            trans.commit();
            manager.popBackStack();
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.panel, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    //KẾT QUẢ TRẢ VỀ SAU KHI THỰC HIỆN ACCOUNT ACTIVITY
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }

    //NẾU LÀ ACC USER THÌ VÀO MÀN HÌNH CHÍNH
    public void switchActivity(){
        startActivity(new Intent(AccountActivity.this,MainActivity.class));
        finish();
    }

    //NẾU LÀ ADMIN THÌ VÀO MÀN HÌNH ADMIN (MANAGER ACTIVITY)
    public void switchActivityManager(){
        startActivity(new Intent(AccountActivity.this,ManagerActivity.class));
        finish();
    }

    //QUAY LẠI
    @Override
    public void onBackPressed() {
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            super.onBackPressed();
        }
    }


}
