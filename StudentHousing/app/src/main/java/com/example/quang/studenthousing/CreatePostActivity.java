package com.example.quang.studenthousing;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import com.example.quang.studenthousing.adapter.GridViewImageAdapter;
import com.example.quang.studenthousing.object.City;
import com.example.quang.studenthousing.object.District;
import com.example.quang.studenthousing.object.Ward;
import com.google.android.gms.maps.model.LatLng;
import java.util.ArrayList;


public class CreatePostActivity extends AppCompatActivity implements View.OnClickListener
{

    private EditText edtTitle;
    private Spinner spinnerCity;
    private Spinner spinnerDistrict;
    private Spinner spinnerWard;
    private EditText edtStreet;
    private RadioGroup groupObject;
    private RadioButton radObjectMale;
    private RadioButton radObjectFemale;
    private RadioButton radObjectBoth;
    private EditText edtDesc;
    private EditText edtPhone;
    private SeekBar seekBarAcreage;
    private TextView tvAcreage;
    private SeekBar seekBarPrice;
    private TextView tvPrice;
    private EditText edtMaxPeo;
    private GridView gvImage;
    private Button btnBrowserImage;
    private Button btnCreate;
    private Button btnBack;
    private ArrayList<String> arrImage;
    private GridViewImageAdapter imageAdapter;

    private int idUser;
    private boolean checkClickCreate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);

        findID();
        getData();
        initViews();
        loadSpinner();

    }
    private void findID() {
        edtTitle = findViewById(R.id.edtTitleAddPost);
        spinnerCity = findViewById(R.id.spinnerCityAddPost);
        spinnerDistrict = findViewById(R.id.spinnerDistrictAddPost);
        spinnerWard = findViewById(R.id.spinnerWardAddPost);
        edtStreet = findViewById(R.id.edtStreetAddPost);
        groupObject = findViewById(R.id.groupObjectAddPost);
        radObjectMale = findViewById(R.id.radMaleAddPost);
        radObjectFemale = findViewById(R.id.radFemaleAddPost);
        radObjectBoth = findViewById(R.id.radBothAddPost);
        edtDesc = findViewById(R.id.edtDescAddPost);
        edtPhone = findViewById(R.id.edtPhoneAddPost);
        seekBarAcreage = findViewById(R.id.seekBarAcreageAddPost);
        tvAcreage = findViewById(R.id.tvAcreageAddPost);
        seekBarPrice = findViewById(R.id.seekBarPriceAddPost);
        tvPrice = findViewById(R.id.tvPriceAddPost);
        edtMaxPeo = findViewById(R.id.edtMaxpeoAddPost);
        gvImage = findViewById(R.id.gvImageAddPost);
        btnBrowserImage = findViewById(R.id.btnBrowserAddPost);
        btnCreate = findViewById(R.id.btnCreateAddPost);
        btnBack = findViewById(R.id.btnBackAddPost);
    }

    private void getData(){
        Intent intent = getIntent();
        idUser = intent.getIntExtra("idUser",0);
    }

    private void initViews()
    {
        //hien thi hinh anh
        arrImage = new ArrayList<>();
        imageAdapter = new GridViewImageAdapter(this,R.layout.item_gridview_image,arrImage);
        gvImage.setAdapter(imageAdapter);

        //dien tich
        seekBarAcreage.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                tvAcreage.setText(i + " " + getString(R.string.meter2));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        //gia phong
        seekBarPrice.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                double r = (double)i/(double)10;
                tvPrice.setText((double)Math.round(r*10)/10 + " " + getString(R.string.million));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        checkClickCreate = true;

        btnBrowserImage.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        btnCreate.setOnClickListener(this);
    }


    //state: chua ket noi db
    private void loadSpinner() {
        DatabaseUtils databaseUtils = new DatabaseUtils(this);
        final ArrayList<City> arrCity = databaseUtils.getCity();
        final ArrayList<District> arrDistrict = databaseUtils.getDistrict();
        final ArrayList<Ward> arrWard = databaseUtils.getWard();

        ArrayList<String> arrCityString = new ArrayList<>();
        ArrayList<String> arrDistrictString = new ArrayList<>();
        ArrayList<String> arrWardString = new ArrayList<>();

        for (int i = 0; i < arrCity.size(); i++) {
            arrCityString.add(arrCity.get(i).getName());
        }

        // spinner city
        ArrayAdapter<String> adapterCity = new ArrayAdapter<String>(this
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
                for (int index = 0; index < arrDistrict.size(); index++) {
                    if (arrDistrict.get(index).getMaCity().equalsIgnoreCase(arrCity.get(i).getMa())) {
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
                String maDistrict = null;
                for (District d : arrDistrict){
                    if (d.getName().equalsIgnoreCase(arrDistrictString.get(i))){
                        maDistrict = d.getMa();
                        break;
                    }
                }
                for (int index=0 ;index<arrWard.size() ;index++){
                    if (arrWard.get(index).getMaDistrict().equalsIgnoreCase(maDistrict)){
                        arrWardString.add(arrWard.get(index).getName());
                    }
                }
                adapterWard.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            //them hinh anh bai dang
            case R.id.btnBrowserAddPost:
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 123);
                break;

            //tao bai
            case R.id.btnCreateAddPost:
                //kiem tra trang thai nut tao bai co duoc click ko, neu ko thi tra ve man hinh tao bai, neu co thi thong tin chua dc dien thi chua dang bai dc
                if (!checkClickCreate) {
                    return;
                } else {
                    checkClickCreate = false;
                }

                //lay thong tin sau khi nhap
                String title = edtTitle.getText().toString();
                String street = edtStreet.getText().toString();
                String desc = edtDesc.getText().toString();
                String phone = edtPhone.getText().toString();
                String city = (String) spinnerCity.getSelectedItem();
                String distric = (String) spinnerDistrict.getSelectedItem();
                String ward = (String) spinnerWard.getSelectedItem();

                //kiem tra thong tin, neu dien thieu thong tin
                if (title.isEmpty() || street.isEmpty() || desc.isEmpty()
                        || phone.isEmpty() || edtMaxPeo.getText().toString().isEmpty()) {

                    //thong bao cho user biet can dien day du thong tin
                    Snackbar snackbar = Snackbar
                            .make(edtDesc, R.string.insert_info, Snackbar.LENGTH_LONG);
                    snackbar.show();
                    checkClickCreate = true;
                    return;
                }
        }
    }
}
