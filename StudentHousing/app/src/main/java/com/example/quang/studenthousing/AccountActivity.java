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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void switchActivity(){
        startActivity(new Intent(AccountActivity.this,MainActivity.class));
        finish();
    }

    public void switchActivityManager(){
        startActivity(new Intent(AccountActivity.this,ManagerActivity.class));
        finish();
    }

    @Override
    public void onBackPressed() {
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            super.onBackPressed();
        }
    }


}
