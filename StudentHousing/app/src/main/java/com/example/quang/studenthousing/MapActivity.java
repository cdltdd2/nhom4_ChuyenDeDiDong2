package com.example.quang.studenthousing;

import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageButton;
import android.widget.TextView;
import com.example.quang.studenthousing.object.CustomEditTextLocation;
import com.example.quang.studenthousing.object.Favorite;
import com.example.quang.studenthousing.object.House;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import java.util.ArrayList;
import dmax.dialog.SpotsDialog;

public class MapActivity extends AppCompatActivity
{

    private GoogleMap mMap;
    private Location myLocation;
    private Marker myMarker;
    private Circle myCircle;
    private Geocoder geocoder;
    private ArrayList<House> arrHouses;
    private ImageButton btnBack;
    private ImageButton btnShowAll;
    private TextView tvCurrentAddress;

    private LatLng currentLocation;
    private SpotsDialog progressDialog;

    private CustomEditTextLocation edtLocation;
    private CustomEditTextLocation edtRadius;


    private int idUser;
    private House houseToPass = null;
    private ArrayList<Favorite> arrFav;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        findID();
        initViews();
    }

    private void findID() {
        edtLocation = findViewById(R.id.edtLocation);
        edtRadius = findViewById(R.id.edtRadius);
        btnBack = findViewById(R.id.btnBackMap);
        btnShowAll = findViewById(R.id.btnShowAllMap);
        tvCurrentAddress = findViewById(R.id.tvAddressCurrentMap);
    }

    private void initViews() {

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.myMap);
        mapFragment.getMapAsync((OnMapReadyCallback) this);

        progressDialog = new SpotsDialog(this, R.style.CustomProgressDialog);
        arrFav = new ArrayList<>();
    }


}
