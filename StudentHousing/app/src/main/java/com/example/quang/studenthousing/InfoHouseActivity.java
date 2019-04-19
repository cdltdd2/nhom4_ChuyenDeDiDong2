package com.example.quang.studenthousing;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.quang.studenthousing.adapter.GridViewPhotoAdapter;
import com.example.quang.studenthousing.adapter.ListViewCommentAdapter;
import com.example.quang.studenthousing.adapter.SlidingPhotoAdapter;
import com.example.quang.studenthousing.object.Comment;
import com.example.quang.studenthousing.object.CustomListView;
import com.example.quang.studenthousing.object.House;
import com.example.quang.studenthousing.object.UrlPhoto;
import com.example.quang.studenthousing.services.APIClient;
import com.example.quang.studenthousing.services.DataClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class InfoHouseActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener  {

    private Toolbar toolbar;
    private ImageView imHouse;
    private TextView tvTitle;
    private TextView tvAddress;
    private TextView tvPhone;
    private TextView tvEmail;
    private TextView tvPrice;
    private TextView tvAcreage;
    private TextView tvObject;;
    private TextView tvMaxPeo;
    private TextView tvStrongInfo;
    private TextView tvState;
    private FloatingActionButton btnAddFavorite;
    private GridView gvPhotoInfo;
    private GridViewPhotoAdapter adapterPhoto;
    private ArrayList<UrlPhoto> arrPhoto;
    private Dialog dialogSlidingPhoto;
    private EditText edtComment;
    private ImageButton btnSend;
    private CustomListView lvComment;
    private ArrayList<Comment> arrComment;
    private ListViewCommentAdapter adapterComment;
    private int permission;
    private House house;
    private int idUser;
    private int state;
    private MenuItem item;
    private boolean checkBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_house);

        findID();
        initViews();
        loadData();
    }

    private void loadData()
    {
        SharedPreferences pre = getSharedPreferences("studenthousing", MODE_PRIVATE);
        String user = pre.getString("user","");
        if (!user.equalsIgnoreCase("")){
            String[] arr = user.split("-");
            idUser = Integer.parseInt(arr[0]);
            permission = Integer.parseInt(arr[5]);

        }

        Intent intent = getIntent();
        house = (House) intent.getSerializableExtra("house");

        Glide.with(this).load(APIClient.BASE_URL+house.getIMAGE()).into(imHouse);
        tvTitle.setText(house.getTITLE());
        tvAddress.setText(house.getADDRESS());
        tvPhone.setText(house.getCONTACT());
        tvPrice.setText(getString(R.string.price) + ": "+house.getPRICE() + " " + getString(R.string.million_per_month));
        tvAcreage.setText(getString(R.string.acreage) + ": "+house.getACREAGE() + " " + getString(R.string.meter2));
        if (house.getOBJECT() == 1){
            tvObject.setText(R.string.object_male);
        }else if(house.getOBJECT() == 0){
            tvObject.setText(R.string.object_female);
        }else {
            tvObject.setText(R.string.object_both);
        }

        state = house.getSTATE();
        if (state == 1){
            tvState.setText(R.string.booked);
            tvState.setTextColor(Color.RED);
        }else {
            tvState.setText(R.string.un_book);
            tvState.setTextColor(Color.GREEN);
        }


        tvMaxPeo.setText(getString(R.string.max_people) + ": " + house.getMAXPEO());
        tvStrongInfo.setText(house.getDESC());

    }

    private void findID() {
        toolbar = findViewById(R.id.toolbar);
        imHouse = findViewById(R.id.imHouseInfo);
        tvTitle = findViewById(R.id.tvTitleInfo);
        tvAddress = findViewById(R.id.tvAddressInfo);
        tvPhone = findViewById(R.id.tvPhoneInfo);
        tvEmail = findViewById(R.id.tvEmailInfo);
        tvPrice = findViewById(R.id.tvPriceInfo);
        tvAcreage = findViewById(R.id.tvAcreageInfo);
        btnAddFavorite = findViewById(R.id.btnAddFavorite);
        tvObject = findViewById(R.id.tvObjectInfo);
        tvMaxPeo = findViewById(R.id.tvMaxPeoInfo);
        tvState = findViewById(R.id.tvStateInfo);
        tvStrongInfo = findViewById(R.id.tvStrongInfoInfo);
        gvPhotoInfo = findViewById(R.id.gvPhotoInfo);
        edtComment = findViewById(R.id.edtComment);
        btnSend = findViewById(R.id.btnSendComment);
        lvComment = findViewById(R.id.lvComments);


    }

    private void initViews() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.app_name);
        if(getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        arrPhoto = new ArrayList<>();
        adapterPhoto = new GridViewPhotoAdapter(this,R.layout.item_gridview_photo,arrPhoto);
        gvPhotoInfo.setAdapter(adapterPhoto);
        gvPhotoInfo.setOnItemClickListener(this);

        arrComment = new ArrayList<>();
        adapterComment = new ListViewCommentAdapter(this,R.layout.item_listview_comment,arrComment);
        lvComment.setAdapter(adapterComment);

        btnSend.setOnClickListener((View.OnClickListener) this);
        btnAddFavorite.setOnClickListener((View.OnClickListener) this);
    }



    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    //dat: dky va huy dky dat phong
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        if (id == android.R.id.home)
        {
            finish();
        }
        else if (id == R.id.action_book)
        {
            if (checkBook)
            {
                AlertDialog.Builder builder;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                {
                    builder = new AlertDialog.Builder(InfoHouseActivity.this, android.R.style.Theme_Material_Dialog_Alert);
                }
                else
                {
                    builder = new AlertDialog.Builder(InfoHouseActivity.this);
                }
                builder.setTitle(R.string.confirm)
                        .setMessage(R.string.delete_this_book)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface dialog, int which)
                            {
                                deleteBook();
                            }
                        })
                        .setNegativeButton(R.string.no, new DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface dialog, int which)
                            {
                                dialog.dismiss();
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
            else
            {
                AlertDialog.Builder builder;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                {
                    builder = new AlertDialog.Builder(InfoHouseActivity.this, android.R.style.Theme_Material_Dialog_Alert);
                }
                else
                {
                    builder = new AlertDialog.Builder(InfoHouseActivity.this);
                }
                builder.setTitle(R.string.confirm)
                        .setMessage(R.string.book_room)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which)
                            {
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
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_book_info, menu);
        item =  menu.getItem(0);

        SharedPreferences pre = getSharedPreferences("studenthousing", MODE_PRIVATE);
        String user = pre.getString("user","");
        if (!user.equalsIgnoreCase("")){
            String[] arr = user.split("-");
            idUser = Integer.parseInt(arr[0]);
            permission = Integer.parseInt(arr[5]);

        }
        Intent intent = getIntent();
        House h = (House) intent.getSerializableExtra("house");

        if (idUser == h.getIDUSER()){
            item.setVisible(false);
        }

        if (permission == 1){
            item.setVisible(false);
        }else {
            if (h.getSTATE() == 0){
                item.setIcon(R.drawable.icon_book_white);
                checkBook = false;
            }else {
                DataClient dataClient = APIClient.getData();
                Call<String> callBack = dataClient.checkUserIsBooker(idUser,h.getIDHOUSE());
                callBack.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if (response.body().equals("true")){
                            item.setIcon(R.drawable.icon_unbook_white);
                            checkBook = true;
                        }else if (response.body().equals("false")){
                            item.setVisible(false);
                            checkBook = false;
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Toast.makeText(InfoHouseActivity.this, "fail2", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }



        return true;
    }

    private void deleteBook(){
        DataClient dataClient = APIClient.getData();
        Call<String> callBack = dataClient.deleteBooking(idUser,house.getIDHOUSE());
        callBack.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.body().equals("success")){
                    Snackbar snackbar = Snackbar
                            .make(btnAddFavorite, R.string.success, Snackbar.LENGTH_LONG);
                    snackbar.show();
                    item.setIcon(R.drawable.icon_book_white);
                    checkBook = false;
                    tvState.setText(R.string.un_book);
                    tvState.setTextColor(Color.GREEN);
                    house.setSTATE(0);
                }else if (response.body().equals("fail")){
                    Snackbar snackbar = Snackbar
                            .make(btnAddFavorite, R.string.fail, Snackbar.LENGTH_LONG);
                    snackbar.show();
                    checkBook = true;
                    house.setSTATE(1);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(InfoHouseActivity.this, "fail", Toast.LENGTH_SHORT).show();

            }
        });
    }

    //dang ky phong
    private void bookRoom(){
        DataClient dataClient = APIClient.getData();
        Call<String> callBack = dataClient.bookRoom(idUser,house.getIDHOUSE());
        callBack.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.body().equals("success")){
                    Snackbar snackbar = Snackbar
                            .make(btnAddFavorite, R.string.book_success, Snackbar.LENGTH_LONG);
                    snackbar.show();
                    tvState.setText(R.string.booked);
                    tvState.setTextColor(Color.RED);
                    house.setSTATE(1);
                    item.setIcon(R.drawable.icon_unbook_white);
                    checkBook = true;
                }else if (response.body().equals("fail")){
                    Snackbar snackbar = Snackbar
                            .make(btnAddFavorite, R.string.book_fail, Snackbar.LENGTH_LONG);
                    snackbar.show();
                    house.setSTATE(0);
                    checkBook = false;
                }else if (response.body().equals("booked")){
                    Snackbar snackbar = Snackbar
                            .make(btnAddFavorite, R.string.someone_has_put, Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Snackbar snackbar = Snackbar
                        .make(btnAddFavorite, R.string.book_fail, Snackbar.LENGTH_LONG);
                snackbar.show();

            }
        });
    }



}
