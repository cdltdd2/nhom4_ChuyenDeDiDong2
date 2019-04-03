package com.example.quang.studenthousing;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.GridView;
import android.widget.SeekBar;
import android.widget.TextView;
import com.example.quang.studenthousing.adapter.GridViewImageAdapter;
import java.util.ArrayList;

public class CreatePostActivity extends AppCompatActivity
{

    private SeekBar seekBarAcreage;
    private TextView tvAcreage;
    private SeekBar seekBarPrice;
    private TextView tvPrice;
    private GridView gvImage;
    private Button btnBrowserImage;
    private Button btnCreate;
    private Button btnBack;
    private ArrayList<String> arrImage;
    private GridViewImageAdapter imageAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);

        findID();
        initViews();

    }
    private void findID() {

        seekBarAcreage = findViewById(R.id.seekBarAcreageAddPost);
        tvAcreage = findViewById(R.id.tvAcreageAddPost);
        seekBarPrice = findViewById(R.id.seekBarPriceAddPost);
        tvPrice = findViewById(R.id.tvPriceAddPost);
        gvImage = findViewById(R.id.gvImageAddPost);
        btnBrowserImage = findViewById(R.id.btnBrowserAddPost);
        btnCreate = findViewById(R.id.btnCreateAddPost);
        btnBack = findViewById(R.id.btnBackAddPost);
    }


    private void initViews() {
        arrImage = new ArrayList<>();
        imageAdapter = new GridViewImageAdapter(this,R.layout.item_gridview_image,arrImage);
        gvImage.setAdapter(imageAdapter);
    }

}
