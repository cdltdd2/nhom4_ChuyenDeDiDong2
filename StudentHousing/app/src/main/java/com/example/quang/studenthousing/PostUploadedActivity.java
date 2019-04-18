package com.example.quang.studenthousing;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import com.example.quang.studenthousing.adapter.GridViewHouseUploadedAdapter;
import com.example.quang.studenthousing.object.House;
import com.example.quang.studenthousing.services.APIClient;
import com.example.quang.studenthousing.services.DataClient;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostUploadedActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

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

        gvPostUploaded.setOnItemClickListener(this);
    }

    private void getHouses() {
        DataClient dataClient = APIClient.getData();
        Call<List<House>> callBack = dataClient.getHouseUploaded(idUser);
        callBack.enqueue(new Callback<List<House>>() {
            @Override
            public void onResponse(Call<List<House>> call, Response<List<House>> response) {
                ArrayList<House> arrHouse = (ArrayList<House>) response.body();
                arrPostUploaded.clear();
                if (arrHouse.size() > 0){
                    for (int i = arrHouse.size() - 1; i >= 0; i--){
                        if (arrHouse.get(i).getCHECKUP() == 1){
                            arrPostUploaded.add(arrHouse.get(i));
                        }
                    }
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<House>> call, Throwable t) {
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
}
