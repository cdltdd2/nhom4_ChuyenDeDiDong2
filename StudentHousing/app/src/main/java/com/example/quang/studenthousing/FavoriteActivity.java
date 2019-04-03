package com.example.quang.studenthousing;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.quang.studenthousing.adapter.GridViewHouseAdapter;
import com.example.quang.studenthousing.object.Favorite;
import com.example.quang.studenthousing.object.House;
import com.example.quang.studenthousing.services.APIClient;
import com.example.quang.studenthousing.services.DataClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavoriteActivity extends AppCompatActivity
{

    private Toolbar toolbar;
    private GridView gvFavorite;
    private GridViewHouseAdapter adapter;
    private ArrayList<House> arrFavorite;
    private ArrayList<Favorite> arrFav;
    private int idUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        findId();
        initView();
    }

    private void findId() {
        toolbar = findViewById(R.id.toolbarFavorite);
        gvFavorite = findViewById(R.id.gvfavorite);
    }

    private void initView() {
        setSupportActionBar(toolbar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Objects.requireNonNull(getSupportActionBar()).setHomeButtonEnabled(true);
        }
        getSupportActionBar().setTitle(R.string.favorite);

        arrFavorite = new ArrayList<>();
        arrFav = new ArrayList<>();

        adapter = new GridViewHouseAdapter(FavoriteActivity.this,R.layout.item_house,arrFavorite);
        gvFavorite.setAdapter(adapter);

        gvFavorite.setOnItemClickListener(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

}
