package com.example.quang.studenthousing;

import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.quang.studenthousing.adapter.GridViewPhotoAdapter;
import com.example.quang.studenthousing.adapter.ListViewCommentAdapter;
import com.example.quang.studenthousing.object.Comment;
import com.example.quang.studenthousing.object.CustomListView;
import com.example.quang.studenthousing.object.UrlPhoto;
import java.util.ArrayList;


public class InfoHouseActivity extends AppCompatActivity implements AdapterView.OnItemClickListener  {

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_house);

        findID();
        initViews();

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
}
