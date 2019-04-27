package com.example.quang.studenthousing;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quang.studenthousing.object.CustomEditTextLocation;
import com.example.quang.studenthousing.object.Favorite;
import com.example.quang.studenthousing.object.House;
import com.example.quang.studenthousing.object.OnChangeLocation;
import com.example.quang.studenthousing.services.APIClient;
import com.example.quang.studenthousing.services.DataClient;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import dmax.dialog.SpotsDialog;
import io.reactivex.annotations.Nullable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, LocationListener, GoogleMap.OnInfoWindowClickListener, View.OnClickListener {

    private GoogleMap mMap;
    private Location myLocation;
    private Marker myMarker;
    private Circle myCircle;
    private Geocoder geocoder;
    private ArrayList<House> arrHouses;
    private ImageButton btnBack;
    private ImageButton btnShowAll;
    private TextView tvCurrentAddress;

    private CustomEditTextLocation edtLocation;
    private CustomEditTextLocation edtRadius;

    private LatLng currentLocation;
    private SpotsDialog progressDialog;

    private int idUser;
    private House houseToPass = null;
    private ArrayList<Favorite> arrFav;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        findID();
        initViews();
        loadData();
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
        mapFragment.getMapAsync(this);

        progressDialog = new SpotsDialog(this, R.style.CustomProgressDialog);

        edtLocation.setOnChangedLocationListener(new OnChangeLocation() {
            @Override
            public void changeLocation(String location) {
                GetLatLng getLatLng = new GetLatLng();
                getLatLng.execute(location);
            }
        });

        edtRadius.setOnChangedLocationListener(new OnChangeLocation() {
            @Override
            public void changeLocation(String location) {
                mMap.clear();
                Circle circle = mMap.addCircle(new CircleOptions()
                        .center(currentLocation)
                        .radius(Integer.parseInt(location))
                        .strokeColor(Color.parseColor("#70568ab7"))
                        .fillColor(Color.parseColor("#50568ab7")));

                for (int i = 0; i < arrHouses.size(); i++) {
                    LatLng lng = new LatLng(getLat(arrHouses.get(i).getLatlng()), getLng(arrHouses.get(i).getLatlng()));
                    double distance = distanceBetweenTwoPoint(currentLocation.latitude
                            , currentLocation.longitude, lng.latitude
                            , lng.longitude);

                    if (distance <= Double.parseDouble(location)) {
                        Marker marker = drawMarker(lng, arrHouses.get(i).getTITLE(), arrHouses.get(i).getADDRESS());
                    }
                }

            }
        });

        arrFav = new ArrayList<>();

        btnBack.setOnClickListener(this);
        btnShowAll.setOnClickListener(this);
    }

    private void loadData() {
        Intent intent = getIntent();
        if (intent.getSerializableExtra("arrHouse") != null) {
            arrHouses = (ArrayList<House>) intent.getSerializableExtra("arrHouse");
        } else {
            arrHouses = new ArrayList<>();
            progressDialog.show();
            DataClient dataClient = APIClient.getData();
            Call<List<House>> callBack = dataClient.getAllHouse();
            callBack.enqueue(new Callback<List<House>>() {
                @Override
                public void onResponse(Call<List<House>> call, Response<List<House>> response) {
                    ArrayList<House> arrHouse = (ArrayList<House>) response.body();
                    if (arrHouse.size() > 0) {
                        for (int i = arrHouse.size() - 1; i >= 0; i--) {
                            if (arrHouse.get(i).getCHECKUP() == 1) {
                                arrHouses.add(arrHouse.get(i));
                            }
                        }
                    }
                    progressDialog.dismiss();
                }

                @Override
                public void onFailure(Call<List<House>> call, Throwable t) {
                    progressDialog.dismiss();
                }
            });
        }
    }

    private double getLat(String latLng) {
        String[] arrStr = latLng.split("\\(");
        String[] arrStr2 = arrStr[1].split(",");

        return Double.parseDouble(arrStr2[0]);
    }

    private double getLng(String latLng) {
        String[] arrStr = latLng.split("\\)");
        String[] arrStr2 = arrStr[0].split(",");

        return Double.parseDouble(arrStr2[1]);
    }

    private double distanceBetweenTwoPoint(double lat_a, double lng_a, double lat_b, double lng_b) {
        double earthRadius = 3958.75;
        double latDiff = Math.toRadians(lat_b - lat_a);
        double lngDiff = Math.toRadians(lng_b - lng_a);
        double a = Math.sin(latDiff / 2) * Math.sin(latDiff / 2) +
                Math.cos(Math.toRadians(lat_a)) * Math.cos(Math.toRadians(lat_b)) *
                        Math.sin(lngDiff / 2) * Math.sin(lngDiff / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = earthRadius * c;

        int meterConversion = 1609;

        return new Double(distance * meterConversion).doubleValue();
    }

    private void initMap() {

        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        assert locationManager != null;
        Location lastLocation = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));
        LatLng begin = new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude());
//        try {
//            begin = new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude());
//        }catch (Exception e){
//            begin = new LatLng(10.852442, 106.758569);
//        }
        currentLocation = begin;
        mMap.moveCamera(CameraUpdateFactory.newLatLng(begin));
        CameraPosition position = new CameraPosition(begin, 14, 0, 0);
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(position));
        geocoder = new Geocoder(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.setOnInfoWindowClickListener(this);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);

        tvCurrentAddress.setText(currentAddress(begin));

        final ImageView img = (ImageView) findViewById(R.id.imViewCenter);
        mMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {
                img.setBackgroundResource(R.drawable.animation_idle);
                final AnimationDrawable animation = (AnimationDrawable) img.getBackground();
                animation.start();
                tvCurrentAddress.setText(currentAddress(mMap.getCameraPosition().target));
                currentLocation = mMap.getCameraPosition().target;
            }
        });

        mMap.setOnCameraMoveListener(new GoogleMap.OnCameraMoveListener() {
            @Override
            public void onCameraMove() {
                img.setBackgroundResource(R.drawable.animation_move);
                final AnimationDrawable animation = (AnimationDrawable) img.getBackground();
                animation.start();
            }
        });


    }

    private String currentAddress(LatLng latLng){
        Geocoder geocoder;
        List<Address> addresses = null;
        geocoder = new Geocoder(this, Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (addresses != null) {
            try {
                return addresses.get(0).getAddressLine(0);
            }catch (Exception e){
                return getString(R.string.address);
            }

        }
        return getString(R.string.address);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        String[] permissionList = {
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
        };
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (String p : permissionList) {
                if (checkSelfPermission(p) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(permissionList, 0);
                    return;
                }
            }
        }
        initMap();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                finish();
                return;
            }
        }
        initMap();
    }

    private Marker drawMarker(LatLng position, String title, String snippet) {
        Drawable circleDrawable = getResources().getDrawable(R.drawable.ic_location_pin);
        BitmapDescriptor markerIcon = getMarkerIconFromDrawable(circleDrawable);

        MarkerOptions options = new MarkerOptions();
        options.position(position);
        options.title(title);
        options.snippet(snippet);
        options.icon(markerIcon);

        return mMap.addMarker(options);
    }

    private BitmapDescriptor getMarkerIconFromDrawable(Drawable drawable) {
        Canvas canvas = new Canvas();
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        canvas.setBitmap(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnBackMap:
                finish();
                break;
            case R.id.btnShowAllMap:
                mMap.clear();
                mMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocation));
                CameraPosition position = new CameraPosition(currentLocation, 18, 0, 0);
                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(position));

                for (int i=0; i<arrHouses.size(); i++){
                    LatLng lng = new LatLng(getLat(arrHouses.get(i).getLatlng()), getLng(arrHouses.get(i).getLatlng()));
                    Marker marker = drawMarker(lng,arrHouses.get(i).getTITLE(), arrHouses.get(i).getADDRESS());
                }
                break;
        }
    }

    private class GetLatLng extends AsyncTask<String, Void, LatLng>{

        @Override
        protected LatLng doInBackground(String... strings) {
            return getLatLng(strings[0]);
        }

        @Override
        protected void onPostExecute(LatLng latLng) {
            super.onPostExecute(latLng);
            currentLocation = latLng;
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            CameraPosition position = new CameraPosition(latLng, 14, 0, 0);
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(position));
        }
    }

    //Hamf chuyen name sang latlng
    protected LatLng getLatLng(String address) {
        String add = Uri.encode(address);
        String uri = "https://maps.googleapis.com/maps/api/geocode/json?address="
                + add + "&" + "key=AIzaSyCjSClgIE-DZ4BULjj9L_qu2hQ9Vzi8osg";

        HttpGet httpGet = new HttpGet(uri);

        HttpClient client = new DefaultHttpClient();
        HttpResponse response;
        StringBuilder stringBuilder = new StringBuilder();

        try {
            response = client.execute(httpGet);
            HttpEntity entity = response.getEntity();

            InputStream stream = entity.getContent();

            int byteData;
            while ((byteData = stream.read()) != -1) {
                stringBuilder.append((char) byteData);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        double lat = 0.0, lng = 0.0;

        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(stringBuilder.toString());
            lng = ((JSONArray) jsonObject.get("results")).getJSONObject(0)
                    .getJSONObject("geometry").getJSONObject("location")
                    .getDouble("lng");
            lat = ((JSONArray) jsonObject.get("results")).getJSONObject(0)
                    .getJSONObject("geometry").getJSONObject("location")
                    .getDouble("lat");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        LatLng latLngAddress = new LatLng(lat,lng);
        return latLngAddress;
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }


    @Override
    public void onInfoWindowClick(Marker marker) {

        String title = marker.getTitle();
        for (House h: arrHouses){
            if (title.equals(h.getTITLE())){
                houseToPass = h;
                break;
            }
        }

        getFav();


    }

    private void getFav() {
        progressDialog.show();
        DataClient dataClient = APIClient.getData();
        Call<List<Favorite>> callBack = dataClient.getFavCount(idUser);
        callBack.enqueue(new Callback<List<Favorite>>() {
            @Override
            public void onResponse(Call<List<Favorite>> call, Response<List<Favorite>> response) {
                ArrayList<Favorite> arr = (ArrayList<Favorite>) response.body();
                arrFav.clear();
                if (arr.size() > 0){
                    for (int i = arr.size() - 1; i >= 0; i--){
                        arrFav.add(arr.get(i));
                    }
                }
                progressDialog.dismiss();

                if (houseToPass != null){
                    Intent intent = new Intent(MapsActivity.this, InfoHouseActivity.class);
                    intent.putExtra("house",houseToPass);
                    intent.putExtra("arrFav",arrFav);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<List<Favorite>> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }

}
