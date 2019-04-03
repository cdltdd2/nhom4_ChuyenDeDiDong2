package com.example.quang.studenthousing;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.DatabaseUtils;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
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
import java.util.ArrayList;
import java.util.Objects;
import dmax.dialog.SpotsDialog;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private GridView gvAllHouses;
    private FloatingActionButton btnSearch;

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

    private SpotsDialog progressDialog;
    private Dialog dialogSort;
    private Dialog dialogSearch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        findID();
        initViews();
        initDialogSearch();
        initDialogSort();
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
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);

            }
        };
        toggle.syncState();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            gvAllHouses.setNestedScrollingEnabled(true);
        }


        progressDialog = new SpotsDialog(this, R.style.CustomProgressDialog);
    }

    private void initDialogSearch() {
        dialogSearch = new Dialog(this,android.R.style.Theme_Holo_Light_Dialog_NoActionBar_MinWidth);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            dialogSearch.getWindow().setType(WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY);
        else {
            dialogSearch.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        }
        dialogSearch.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogSearch.setContentView(R.layout.dialog_search);
        dialogSearch.setCancelable(false);


        final ArrayList<String> arrCityString = new ArrayList<>();
        final ArrayList<String> arrDistrictString = new ArrayList<>();
        final ArrayList<String> arrWardString = new ArrayList<>();
        arrDistrictString.add("Quận/Huyện");
        arrWardString.add("Xã/Phường");


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

    }

    private void initDialogSort() {
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
            public void onClick(View view)
            {

                dialogSort.dismiss();
            }
        });
    }



}
