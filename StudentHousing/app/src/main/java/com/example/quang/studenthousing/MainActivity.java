package com.example.quang.studenthousing;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quang.studenthousing.AccountActivity;
import com.example.quang.studenthousing.CreatePostActivity;
import com.example.quang.studenthousing.FavoriteActivity;
import com.example.quang.studenthousing.InfoHouseActivity;
import com.example.quang.studenthousing.ListBookingActivity;
import com.example.quang.studenthousing.MapActivity;
import com.example.quang.studenthousing.PostUploadedActivity;
import com.example.quang.studenthousing.R;
import com.example.quang.studenthousing.adapter.GridViewHouseAdapter;
import com.example.quang.studenthousing.object.City;
import com.example.quang.studenthousing.object.District;
import com.example.quang.studenthousing.object.Favorite;
import com.example.quang.studenthousing.object.House;
import com.example.quang.studenthousing.object.Ward;
import com.example.quang.studenthousing.services.APIClient;
import com.example.quang.studenthousing.services.AppService;
import com.example.quang.studenthousing.services.DataClient;
import com.example.quang.studenthousing.utils.DatabaseUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener
{

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private GridView gvAllHouses;
    private FloatingActionButton btnSearch;
    private GridViewHouseAdapter adapter;
    private ArrayList<House> arrHouses;

    // drawerlayout
    private TextView tvName;
    private TextView tvPhone;
    private ImageButton btnMap;
    private LinearLayout lnAllHouses;
    private LinearLayout lnFavorite;
    private Button btnCountFavorite;
    private LinearLayout lnSort;
    private LinearLayout lnLogout;
    private LinearLayout lnRegisterPoster;
    private LinearLayout lnAddPost;
    private LinearLayout lnUploaded;
    private LinearLayout lnBooked;


    private int idUser;
    private String user;
    private String pass;
    private String name;
    private String phone;
    private int permission;

    private SpotsDialog progressDialog;
    private Dialog dialogSort;
    private Dialog dialogSearch;

    private ArrayList<Favorite> arrFav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        findID();
        initViews();
        initDialogSearch();
        initDialogSort();
        initDialogSearch();

        if (!isMyServiceRunning(AppService.class)){
            Intent myService = new Intent(MainActivity.this, AppService.class);
            startService(myService);
        }
    }

    private boolean isMyServiceRunning(Class<?> serviceClass)
    {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);

        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE))
        {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    private void findID() {
        toolbar = findViewById(R.id.toolbarMain);
        drawerLayout = findViewById(R.id.drawerLayout);
        gvAllHouses = findViewById(R.id.gvAllHouses);
        btnSearch = findViewById(R.id.btnSearch);
        tvName = findViewById(R.id.tvName);
        tvPhone = findViewById(R.id.tvPhone);
        btnMap = findViewById(R.id.btnMap);
        lnAllHouses = findViewById(R.id.lnAllHouses);
        lnFavorite = findViewById(R.id.lnFavorite);
        btnCountFavorite = findViewById(R.id.btnCountFavorites);
        lnSort = findViewById(R.id.lnSort);
        lnLogout = findViewById(R.id.lnLogout);
        lnRegisterPoster = findViewById(R.id.lnRegisterPoster);
        lnAddPost = findViewById(R.id.lnAddPost);
        lnUploaded = findViewById(R.id.lnUploaded);
        lnBooked = findViewById(R.id.lnBooked);
    }

    // duy: load du lieu o man hinh chinh
    private void loadData()
    {
        SharedPreferences pre = getSharedPreferences("studenthousing", MODE_PRIVATE);
        String user = pre.getString("user","");
        //kiem tra permission la 0 hoac 2: neu 0 la user thuong, 2 la user da dky dang bai
        if (!user.equalsIgnoreCase("")){
            String[] arr = user.split("-");
            idUser = Integer.parseInt(arr[0]);
            this.user = arr[1];
            pass = arr[2];
            name = arr[3];
            phone = arr[4];
            permission = Integer.parseInt(arr[5]);

            tvName.setText(name);

            //lay sdt
            if (phone.equalsIgnoreCase("null")){
                tvPhone.setText(R.string.no_phone);
            }else {
                tvPhone.setText(phone);
            }

        }

        //khoi tao mang ds bai
        arrHouses = new ArrayList<>();

        //khoi tao mang ds bai yeu thich cua user nay
        arrFav = new ArrayList<>();

        //do du lieu bai dang (item house) vao adapter
        adapter = new GridViewHouseAdapter(MainActivity.this,R.layout.item_house,arrHouses);
        gvAllHouses.setAdapter(adapter);

        //request lay ds bai
        getHouses();
    }

    /*
     * xóa mảng danh sach yeu thich sau do tai lai dữ liệu app thông qua đối tượng progressDialog của thư viện SpotsDialog(dialog dấu chấm)
     * lấy dữ liệu
     *dismiss: đóng dialog
     * */
    private void getFav()
    {
        arrFav.clear();
        progressDialog.show();
        DataClient dataClient = APIClient.getData();
        Call<List<Favorite>> callBack = dataClient.getFavCount(idUser);
        callBack.enqueue(new Callback<List<Favorite>>()
        {
            @Override
            public void onResponse(Call<List<Favorite>> call, Response<List<Favorite>> response)
            {
                ArrayList<Favorite> arr = (ArrayList<Favorite>) response.body();
                if (arr.size() > 0){
                    for (int i = arr.size() - 1; i >= 0; i--){
                        arrFav.add(arr.get(i));
                    }
                }
                btnCountFavorite.setText(arrFav.size()+"");
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<List<Favorite>> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }

    //lay danh sach bai
    private void getHouses()
    {
        //tai lai ds
        progressDialog.show();
        DataClient dataClient = APIClient.getData();
        //request len server lay danh sach tat ca bai dang
        Call<List<House>> callBack = dataClient.getAllHouse();

        //tra ve ket qua dang json sau do parse sang doi tuong java bang gson
        callBack.enqueue(new Callback<List<House>>()
        {
            //truong hop co ket qua tra ve
            @Override
            public void onResponse(Call<List<House>> call, Response<List<House>> response)
            {
                ArrayList<House> arrHouse = (ArrayList<House>) response.body();
                //co du lieu
                if (arrHouse.size() > 0){
                    //xoa du lieu mang
                    arrHouses.clear();

                    //chay vong lap lay tung item house
                    for (int i = arrHouse.size() - 1; i >= 0; i--){
                        //bai da dc admin duyet se co checkup la 1
                        if (arrHouse.get(i).getCHECKUP() == 1){
                            //them vao mang house
                            arrHouses.add(arrHouse.get(i));
                        }
                    }

                    //kiem tra co thay doi gi ko
                    adapter.notifyDataSetChanged();
                }
                progressDialog.dismiss();
            }

            //truong hop co loi thi ko tai lai ds
            @Override
            public void onFailure(Call<List<House>> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }

    private void initViews() {
        setSupportActionBar(toolbar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Objects.requireNonNull(getSupportActionBar()).setHomeButtonEnabled(true);
        }

        getSupportActionBar().setTitle(R.string.all_houses);

        toggle = new ActionBarDrawerToggle(this,drawerLayout,0,0){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getFav();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);

            }
        };
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        btnSearch.setOnClickListener(this);
        lnAllHouses.setOnClickListener(this);
        lnFavorite.setOnClickListener(this);
        lnSort.setOnClickListener(this);
        lnLogout.setOnClickListener(this);
        lnRegisterPoster.setOnClickListener(this);
        lnAddPost.setOnClickListener(this);
        lnUploaded.setOnClickListener(this);
        lnBooked.setOnClickListener(this);
        gvAllHouses.setOnItemClickListener(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            gvAllHouses.setNestedScrollingEnabled(true);
        }


        progressDialog = new SpotsDialog(this, R.style.CustomProgressDialog);
    }

    private void initDialogSort()
    {
        dialogSort = new Dialog(this,android.R.style.Theme_Holo_Light_Dialog_NoActionBar_MinWidth);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            dialogSort.getWindow().setType(WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY);
        else {
            dialogSort.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        }
        dialogSort.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogSort.setContentView(R.layout.dialog_sort);
        dialogSort.setCancelable(true);

        RadioGroup groupType = dialogSort.findViewById(R.id.groupTypeSort);
        RadioGroup groupStyle = dialogSort.findViewById(R.id.groupStyleSort);
        RadioButton radPrice = dialogSort.findViewById(R.id.radPrice);
        RadioButton radAcreage = dialogSort.findViewById(R.id.radAcreage);
        RadioButton radTurnDown = dialogSort.findViewById(R.id.radTurnDown);
        RadioButton radTurnUp = dialogSort.findViewById(R.id.radTurnUp);
        TextView tvCancel = dialogSort.findViewById(R.id.tvCancelDialog);
        TextView tvDone = dialogSort.findViewById(R.id.tvDoneDialog);

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                dialogSort.dismiss();
            }
        });

        tvDone.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                int type;
                int style;
                if (radPrice.isChecked())   style = 1;
                else    style = 0;

                if (radTurnDown.isChecked()) type = 0;
                else type = 1;

                sort(type,style);
                dialogSort.dismiss();
            }
        });
    }

    private void sort(int type, int style){
        //type = 1 : low to high
        //type = 0 : high to low

        //style = 1 : price
        //style = 0 : acreage

        if (type == 1 && style == 1){
            for (int i=0; i<arrHouses.size(); i++){
                for (int j=0; j<arrHouses.size(); j++){
                    if (arrHouses.get(i).getPRICE() < arrHouses.get(j).getPRICE())
                        Collections.swap(arrHouses,i,j);
                }
            }
        }else if (type == 0 && style == 1){
            for (int i=0; i<arrHouses.size(); i++){
                for (int j=0; j<arrHouses.size(); j++){
                    if (arrHouses.get(i).getPRICE() > arrHouses.get(j).getPRICE())
                        Collections.swap(arrHouses,i,j);
                }
            }
        }else if (type == 1 && style == 0){
            for (int i=0; i<arrHouses.size(); i++){
                for (int j=0; j<arrHouses.size(); j++){
                    if (arrHouses.get(i).getACREAGE() < arrHouses.get(j).getACREAGE())
                        Collections.swap(arrHouses,i,j);
                }
            }
        }else if (type == 0 && style == 0){
            for (int i=0; i<arrHouses.size(); i++){
                for (int j=0; j<arrHouses.size(); j++){
                    if (arrHouses.get(i).getACREAGE() > arrHouses.get(j).getACREAGE())
                        Collections.swap(arrHouses,i,j);
                }
            }
        }

        adapter.notifyDataSetChanged();
    }

    private void initDialogSearch()
    {
        dialogSearch = new Dialog(this,android.R.style.Theme_Holo_Light_Dialog_NoActionBar_MinWidth);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            dialogSearch.getWindow().setType(WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY);
        else {
            dialogSearch.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        }
        dialogSearch.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogSearch.setContentView(R.layout.dialog_search);
        dialogSearch.setCancelable(false);

        DatabaseUtils databaseUtils = new DatabaseUtils(this);
        final ArrayList<City> arrCity = databaseUtils.getCity();
        final ArrayList<District> arrDistrict = databaseUtils.getDistrict();
        final ArrayList<Ward> arrWard = databaseUtils.getWard();

        final ArrayList<String> arrCityString = new ArrayList<>();
        final ArrayList<String> arrDistrictString = new ArrayList<>();
        final ArrayList<String> arrWardString = new ArrayList<>();
        arrDistrictString.add("Quận/Huyện");
        arrWardString.add("Xã/Phường");
        for (int i=0; i<arrCity.size();i++){
            arrCityString.add(arrCity.get(i).getName());
        }

        ImageView imDismiss = dialogSearch.findViewById(R.id.btnDimissDialogSearch);
        final Spinner spinnerCity = dialogSearch.findViewById(R.id.spinnerCity);
        final Spinner spinnerDistrict = dialogSearch.findViewById(R.id.spinnerDistrict);
        final Spinner spinnerWard = dialogSearch.findViewById(R.id.spinnerWard);
        final TextView tvPriceFrom = dialogSearch.findViewById(R.id.tvPriceFrom);
        final TextView tvPriceTo = dialogSearch.findViewById(R.id.tvPriceTo);
        final TextView tvAcreageFrom = dialogSearch.findViewById(R.id.tvAcreageFrom);
        final TextView tvAcreageTo = dialogSearch.findViewById(R.id.tvAcreageTo);
        final SeekBar seekBarPriceFrom = dialogSearch.findViewById(R.id.seekBarPriceFrom);
        final SeekBar seekBarPriceTo = dialogSearch.findViewById(R.id.seekBarPriceTo);
        final SeekBar seekBarAcreageFrom = dialogSearch.findViewById(R.id.seekBarAcreageFrom);
        final SeekBar seekBarAcreageTo = dialogSearch.findViewById(R.id.seekBarAcreageTo);
        Button btnSearch = dialogSearch.findViewById(R.id.btnSearchDialogSearch);
        final CheckBox cbPrice = dialogSearch.findViewById(R.id.cbPriceDialogSearch);
        final CheckBox cbAcreage = dialogSearch.findViewById(R.id.cbAcreageDialogSearch);
        final LinearLayout lnPrice = dialogSearch.findViewById(R.id.lnPrice);
        final LinearLayout lnAcreage = dialogSearch.findViewById(R.id.lnAcreage);

        // spinner city
        ArrayAdapter<String> adapterCity=new ArrayAdapter<String>(this
                , android.R.layout.simple_spinner_item, arrCityString);
        //phải gọi lệnh này để hiển thị danh sách cho Spinner
        adapterCity.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        //Thiết lập adapter cho Spinner
        spinnerCity.setAdapter(adapterCity);


        // spinner district
        final ArrayAdapter<String> adapterDistrict = new ArrayAdapter<String>(this
                , android.R.layout.simple_spinner_item, arrDistrictString);
        //phải gọi lệnh này để hiển thị danh sách cho Spinner
        adapterDistrict.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        //Thiết lập adapter cho Spinner
        spinnerDistrict.setAdapter(adapterDistrict);


        // spinner ward
        final ArrayAdapter<String> adapterWard = new ArrayAdapter<String>(this
                , android.R.layout.simple_spinner_item, arrWardString);
        //phải gọi lệnh này để hiển thị danh sách cho Spinner
        adapterWard.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        //Thiết lập adapter cho Spinner
        spinnerWard.setAdapter(adapterWard);



        spinnerCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String str = arrCityString.get(i);
                arrDistrictString.clear();
                arrDistrictString.add("Quận/Huyện");
                for (int index=0 ;index<arrDistrict.size() ;index++){
                    if (arrDistrict.get(index).getMaCity().equalsIgnoreCase(arrCity.get(i).getMa())){
                        arrDistrictString.add(arrDistrict.get(index).getName());
                    }
                }
                adapterDistrict.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                arrWardString.clear();
                arrWardString.add("Xã/Phường");
                String maDistrict = null;
                for (District d : arrDistrict){
                    if (d.getName().equalsIgnoreCase(arrDistrictString.get(i))){
                        maDistrict = d.getMa();
                        break;
                    }
                }
                for (int index=0 ;index<arrWard.size() ;index++){
                    if (i > 0 && arrWard.get(index).getMaDistrict().equalsIgnoreCase(maDistrict)){
                        arrWardString.add(arrWard.get(index).getName());
                    }
                }
                adapterWard.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        imDismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogSearch.dismiss();
            }
        });

        cbPrice.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    lnPrice.setVisibility(View.VISIBLE);
                }else {
                    lnPrice.setVisibility(View.GONE);
                }
            }
        });

        cbAcreage.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    lnAcreage.setVisibility(View.VISIBLE);
                }else {
                    lnAcreage.setVisibility(View.GONE);
                }
            }
        });

        seekBarPriceFrom.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                double r = (double)i/(double)1000;
                tvPriceFrom.setText((double)Math.round(r*10)/10 + " " + getString(R.string.million));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        seekBarPriceTo.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                tvPriceTo.setText(i + " " + getString(R.string.million));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        seekBarAcreageFrom.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                tvAcreageFrom.setText(i + " " + Html.fromHtml("m<sup>2</sup>"));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        seekBarAcreageTo.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                tvAcreageTo.setText(i + " " + Html.fromHtml("m<sup>2</sup>"));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                String city = spinnerCity.getSelectedItem().toString();
                String district = "";
                String ward = "";
                if (!spinnerDistrict.getSelectedItem().equals("Quận/Huyện"))
                {
                    district = spinnerDistrict.getSelectedItem().toString();
                }

                if (!spinnerDistrict.getSelectedItem().equals("Xã/Phường")){
                    ward = spinnerWard.getSelectedItem().toString();
                }

                ArrayList<House> arrContainCity = new ArrayList<>();
                for (House h: arrHouses){
                    if (h.getADDRESS().contains(city)){
                        arrContainCity.add(h);
                    }
                }

                ArrayList<House> arrContainDistrict = new ArrayList<>();
                for (House h: arrContainCity){
                    if (h.getADDRESS().contains(district)){
                        arrContainDistrict.add(h);
                    }
                }

                ArrayList<House> arrNewHouse = new ArrayList<>();
                for (House h: arrContainDistrict){
                    if (h.getADDRESS().contains(ward)){
                        arrNewHouse.add(h);
                    }
                }

                ArrayList<House> arrHouseFocusPrice = new ArrayList<>();
                if (cbPrice.isChecked()){

                    String[] arrStrPriceFrom = tvPriceFrom.getText().toString().split(" ");
                    String[] arrStrPriceTo = tvPriceTo.getText().toString().split(" ");
                    float currentPriceFrom = Float.parseFloat(arrStrPriceFrom[0]);
                    float currentPriceTo = Float.parseFloat(arrStrPriceTo[0]);

                    for (int i=0; i<arrNewHouse.size(); i++){
                        if (arrNewHouse.get(i).getPRICE()>= currentPriceFrom
                                && arrNewHouse.get(i).getPRICE() <= currentPriceTo){
                            arrHouseFocusPrice.add(arrNewHouse.get(i));
                        }
                    }
                }else {
                    arrHouseFocusPrice.addAll(arrNewHouse);
                }

                ArrayList<House> arrHouseFocusAcreage = new ArrayList<>();
                if (cbAcreage.isChecked()){

                    String[] arrStrAcreageFrom = tvAcreageFrom.getText().toString().split(" ");
                    String[] arrStrAcreageTo = tvAcreageTo.getText().toString().split(" ");
                    float currentAcreageFrom = Float.parseFloat(arrStrAcreageFrom[0]);
                    float currentAcreageTo = Float.parseFloat(arrStrAcreageTo[0]);

                    for (int i=0; i<arrHouseFocusPrice.size();i++){
                        if (arrHouseFocusPrice.get(i).getACREAGE() >= currentAcreageFrom
                                && arrHouseFocusPrice.get(i).getACREAGE() <= currentAcreageTo){
                            arrHouseFocusAcreage.add(arrHouseFocusPrice.get(i));
                        }
                    }
                }else {
                    arrHouseFocusAcreage.addAll(arrHouseFocusPrice);
                }

                arrHouses.clear();
                arrHouses.addAll(arrHouseFocusAcreage);

                adapter = new GridViewHouseAdapter(MainActivity.this,R.layout.item_house,arrHouses);
                gvAllHouses.setAdapter(adapter);
                dialogSearch.dismiss();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        if (toggle.onOptionsItemSelected(item))
        {
            return true;
        }
        else if (id == R.id.action_refesh)
        {
            getHouses();
            return true;
        }
        else if (id == R.id.action_sort)
        {
            dialogSort.show();
        }
        return super.onOptionsItemSelected(item);
    }

    //toan
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @SuppressLint({"ResourceAsColor", "NewApi"})
    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            //toan: map
            case R.id.btnMap:
                Intent i = new Intent(this, MapActivity.class);
                i.putExtra("arrHouse", arrHouses);
                startActivity(i);
                break;

            //duy: ds nha
            case R.id.lnAllHouses:
                drawerLayout.closeDrawers();
                getHouses();
                break;

            //nguyen: yeu thich
            case R.id.lnFavorite:
                startActivity(new Intent(this, FavoriteActivity.class));
                drawerLayout.closeDrawers();
                break;

            //nguyen: sap xep
            case R.id.lnSort:
                dialogSort.show();
                drawerLayout.closeDrawers();
                break;

                //duy: bai da dang
            case R.id.lnUploaded:
                if (permission == 0){
                    drawerLayout.closeDrawers();
                    needPermissionPoster();
                }else if (permission == 2){
                    startActivity(new Intent(this, PostUploadedActivity.class));
                }
                drawerLayout.closeDrawers();
                break;


            case R.id.lnLogout:
                SharedPreferences pre = getSharedPreferences("studenthousing", MODE_PRIVATE);
                SharedPreferences.Editor edit = pre.edit();
                edit.putString("user", "");
                edit.commit();
                if (isMyServiceRunning(AppService.class)) {
                    Intent myService = new Intent(MainActivity.this, AppService.class);
                    stopService(myService);
                }
                startActivity(new Intent(this, AccountActivity.class));
                finish();

                break;

            //dat: dki dang bai
            case R.id.lnRegisterPoster:
                if (permission == 0){
                    showDialogCofirmRegisterPoster();
                }else if (permission == 2){
                    Snackbar snackbar = Snackbar
                            .make(toolbar, R.string.noti_poster, Snackbar.LENGTH_LONG);
                    snackbar.show();
                    drawerLayout.closeDrawers();
                }

                break;

            case R.id.btnSearch:
                dialogSearch.show();
                break;

                //duy: danh sach lich su dat phong
            case R.id.lnBooked:
                startActivity(new Intent(this, ListBookingActivity.class));
                break;
        }
    }

    //dat: ktra user da dki dang bai chua
    @SuppressLint("NewApi")
    private void needPermissionPoster(){
        Snackbar.make(toolbar, R.string.need_permission, Snackbar.LENGTH_LONG)
                .setAction(R.string.register, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showDialogCofirmRegisterPoster();
                    }
                }).setActionTextColor(getColor(R.color.btn)).show();
    }

    //dat: dialog xac nhan dki dang bai
    private void showDialogCofirmRegisterPoster(){
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(MainActivity.this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(MainActivity.this);
        }
        builder.setTitle(R.string.confirm)
                .setMessage(R.string.verify_register_poster)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        registerPoster(idUser);
                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    //dat: dki dang bai
    private void registerPoster(int id){
        DataClient dataClient = APIClient.getData();
        Call<String> callBack = dataClient.registerPoster(id);
        callBack.enqueue(new Callback<String>()
        {
             @Override
             public void onResponse(Call<String> call, Response<String> response) {
                 if (response.body().equals("exists")) {
                     Snackbar snackbar = Snackbar
                             .make(toolbar, R.string.verify_exists, Snackbar.LENGTH_LONG);
                     snackbar.show();
                 } else if (response.body().equals("success")) {
                     Snackbar snackbar = Snackbar
                             .make(toolbar, R.string.verify_success, Snackbar.LENGTH_LONG);
                     snackbar.show();
                 } else if (response.body().equals("fail")) {
                     Snackbar snackbar = Snackbar
                             .make(toolbar, R.string.verify_fail, Snackbar.LENGTH_LONG);
                     snackbar.show();
                 }
             }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    //dat: chuyen sang activity infor house khi nhân vao item house o man hinh chinh
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(this, InfoHouseActivity.class);
        intent.putExtra("house",arrHouses.get(i));
        intent.putExtra("arrFav",arrFav);
        startActivity(intent);
    }

    //dat: sau khi tam ngung se tai lai danh sach nha
    @Override
    protected void onResume() {
        super.onResume();
        getHouses();
    }
}
