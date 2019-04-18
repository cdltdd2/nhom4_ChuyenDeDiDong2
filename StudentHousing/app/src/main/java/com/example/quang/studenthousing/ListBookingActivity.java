package com.example.quang.studenthousing;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;
import android.widget.Toast;

import com.example.quang.studenthousing.adapter.ListViewBookingAdapter;
import com.example.quang.studenthousing.object.PersonBooking;
import com.example.quang.studenthousing.services.APIClient;
import com.example.quang.studenthousing.services.DataClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListBookingActivity extends AppCompatActivity {

    private ListView lvBooking;
    private Toolbar toolbar;
    private ArrayList<PersonBooking> arrBooking;
    private ListViewBookingAdapter adapter;
    private int idUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_booking);

        findID();
        initViews();
        loadData();
    }

    private void findID() {
        toolbar = findViewById(R.id.toolbarListBooking);
        lvBooking = findViewById(R.id.lvBooking);
    }

    private void initViews() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.list_booking);

        arrBooking = new ArrayList<>();
        adapter = new ListViewBookingAdapter(this,R.layout.item_person_booking,arrBooking);
        lvBooking.setAdapter(adapter);
    }

    private void loadData() {
        SharedPreferences pre = getSharedPreferences("studenthousing", MODE_PRIVATE);
        String user = pre.getString("user","");
        if (!user.equalsIgnoreCase("")){
            String[] arr = user.split("-");
            idUser = Integer.parseInt(arr[0]);

        }

        DataClient dataClient = APIClient.getData();
        Call<List<PersonBooking>> callBack = dataClient.getListBooking(idUser);
        callBack.enqueue(new Callback<List<PersonBooking>>() {
            @Override
            public void onResponse(Call<List<PersonBooking>> call, Response<List<PersonBooking>> response) {
                ArrayList<PersonBooking> arr = (ArrayList<PersonBooking>) response.body();
                if (arr.size() > 0){
                    arrBooking.clear();
                    for (int i = arr.size() - 1; i >= 0; i--){
                        arrBooking.add(arr.get(i));
                    }
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<PersonBooking>> call, Throwable t) {
                Toast.makeText(ListBookingActivity.this, t.getMessage()+"", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
