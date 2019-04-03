package com.example.quang.studenthousing;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;
import com.example.quang.studenthousing.adapter.ListViewBookingAdapter;
import com.example.quang.studenthousing.object.PersonBooking;
import java.util.ArrayList;

public class ListBookingActivity extends AppCompatActivity
{

    private ListView lvBooking;
    private Toolbar toolbar;
    private ArrayList<PersonBooking> arrBooking;
    private ListViewBookingAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_booking);

        findID();
        initViews();
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
}
