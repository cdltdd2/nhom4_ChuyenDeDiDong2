package com.example.quang.studenthousing;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.AdapterView;
import android.widget.GridView;
import com.example.quang.studenthousing.adapter.GridViewHouseUploadedAdapter;
import com.example.quang.studenthousing.object.House;
import java.util.ArrayList;
import java.util.Objects;

public class PostUploadedActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private GridView gvPostUploaded;
    private GridViewHouseUploadedAdapter adapter;
    private ArrayList<House> arrPostUploaded;
    private int idUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_uploaded);

        findId();
        initView();
    }



    private void findId() {
        toolbar = findViewById(R.id.toolbarPostUploaded);
        gvPostUploaded = findViewById(R.id.gvPostUploaded);
    }

    private void initView() {
        setSupportActionBar(toolbar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Objects.requireNonNull(getSupportActionBar()).setHomeButtonEnabled(true);
        }
        getSupportActionBar().setTitle(R.string.uploaded);

        arrPostUploaded = new ArrayList<>();

        adapter = new GridViewHouseUploadedAdapter(this,R.layout.item_gridview_house_request,arrPostUploaded);
        gvPostUploaded.setAdapter(adapter);

        gvPostUploaded.setOnItemClickListener((AdapterView.OnItemClickListener) this);
    }

}
