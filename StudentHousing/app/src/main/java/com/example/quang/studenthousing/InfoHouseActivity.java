/*
* LINH: THÔNG TIN CHI TIẾT BÀI ĐĂNG (COMMENT, DIỆN TÍCH, GIÁ, ĐỊA CHỈ, YÊU CẦU,...)
* TOÀN: VỊ TRÍ BÀI ĐĂNG TRÊN MAP
* NGUYÊN: ĐÁNH DẤU BÀI YÊU THÍCH
* ĐẠT: ĐĂNG KÝ ĐẶT PHÒNG
*
* */
package com.example.quang.studenthousing;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
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
import android.util.Log;
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
import com.example.quang.studenthousing.object.Favorite;
import com.example.quang.studenthousing.object.House;
import com.example.quang.studenthousing.object.UrlPhoto;
import com.example.quang.studenthousing.services.APIClient;
import com.example.quang.studenthousing.services.DataClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InfoHouseActivity extends AppCompatActivity implements AdapterView.OnItemClickListener,OnMapReadyCallback, View.OnClickListener {

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
    private LatLng latLngLocation;

    private GoogleMap mMap;
    private ArrayList<Favorite> arrFav;
    private boolean checkFav;
    private int state;
    private MenuItem item;
    private boolean checkBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //tải giao diện thông tin chi tiết bài đăng
        setContentView(R.layout.activity_info_house);
        //gán id
        findID();
        //khởi tạo view và tải dữ liệu
        initViews();
        loadData();
    }

    private void loadData()
    {
        //SharedPreferences lưu các dữ liệu thô dưới dạng các cặp khóa và giá trị (key-value) vào các tập tin của ứng dụng.
        // Bạn cũng có thể chọn một chế độ lưu trữ riêng tư (PRIVATE) mà các ứng dụng khác không thể truy cập vào các tập tin này,
        //truy cập user hiện tại dg sử dụng ứng dụng
        SharedPreferences pre = getSharedPreferences("studenthousing", MODE_PRIVATE);
        String user = pre.getString("user","");
        if (!user.equalsIgnoreCase("")){
            //cắt chuỗi để lấy id user (vị trí đầu tiên trog mảng) và quyền user (vị trí thứ 5 trog mảng)
            String[] arr = user.split("-");
            idUser = Integer.parseInt(arr[0]);
            permission = Integer.parseInt(arr[5]);

        }

        //intent là ý định muốn truy cập ứng dụng hoặc hoat động nào khác bên ngoài
        Intent intent = getIntent();
        house = (House) intent.getSerializableExtra("house");
        //lấy ảnh đại diện để hiển thị cho bài đăng
        Glide.with(this).load(APIClient.BASE_URL+house.getIMAGE()).into(imHouse);
        //lấy các thông tin khác của bài đăng
        tvTitle.setText(house.getTITLE());
        tvAddress.setText(house.getADDRESS());
        tvPhone.setText(house.getCONTACT());
        tvPrice.setText(getString(R.string.price) + ": "+house.getPRICE() + " " + getString(R.string.million_per_month));
        tvAcreage.setText(getString(R.string.acreage) + ": "+house.getACREAGE() + " " + getString(R.string.meter2));

        //nếu đối tượng thuê là nam sẽ có giá trị la 1
        if (house.getOBJECT() == 1){
            tvObject.setText(R.string.object_male);
        }else if(house.getOBJECT() == 0){
            //neu doi tuong thue la nu se co gia tri la 2
            tvObject.setText(R.string.object_female);
        }else {
            //nguoc lại se la ca nam va nu deu dc thue
            tvObject.setText(R.string.object_both);
        }

        //trạng thái phòng: còn trống hoặc đã có người đăng ký
        state = house.getSTATE();
        if (state == 1){
            //da có người dky se co gtri la 1
            tvState.setText(R.string.booked);
            tvState.setTextColor(Color.RED);
        }else {
            //nguoc lai se la còn trống
            tvState.setText(R.string.un_book);
            tvState.setTextColor(Color.GREEN);
        }
        //get toa do vi tri cho thue nha
        latLngLocation = new LatLng(getLat(house.getLatlng()), getLng(house.getLatlng()));

        //LINH: số lượng người đc ở trong 1 phòng tối đa
        tvMaxPeo.setText(getString(R.string.max_people) + ": " + house.getMAXPEO());
        //thông tin mô tả khác
        tvStrongInfo.setText(house.getDESC());

        //hình ảnh bài đăng
        getPhoto();

        //bình luận bài đăng
        getComment();

        //NGUYÊN: trạng thái yêu thích
        checkFav = false;

        if (permission == 1){
            btnAddFavorite.setVisibility(View.GONE);
        }else {
            arrFav = (ArrayList<Favorite>) intent.getSerializableExtra("arrFav");
            for (Favorite f: arrFav){
                if (f.getIDHOUSE() == house.getIDHOUSE()){
                    btnAddFavorite.setImageResource(R.drawable.icon_remove_favorite);
                    checkFav = true;
                    break;
                }
            }
        }

    }

    //LINH
    private void getComment() {
        arrComment.clear();
        DataClient dataClient = APIClient.getData();
        //yêu cầu server trả về danh sách bình luận của bài đăng đó
        Call<List<Comment>> callBack = dataClient.getComment(house.getIDHOUSE());
        //server trả về dữ liệu dạng json sau đó được convert thành đối tượng java bằng gson
        callBack.enqueue(new Callback<List<Comment>>() {
            //server có dữ liệu để phản hồi lại
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                ArrayList<Comment> arr = (ArrayList<Comment>) response.body();
                if (arr.size() > 0){
                    for (int i = arr.size() - 1; i >= 0; i--){
                        //hiển thị binh luan
                        arrComment.add(arr.get(i));
                    }
                    //refresh lại giao diện nếu có thay đổi
                    adapterComment.notifyDataSetChanged();
                }
            }

            //server có lỗi chẳng hạn như bị mất mạng
            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
            }
        });
    }

    //LINH
    private void getPhoto(){
        DataClient dataClient = APIClient.getData();
        //yêu cầu server trả về hình ảnh của bài đăng đó
        Call<List<UrlPhoto>> callBack = dataClient.getPhotoInfo(house.getIDHOUSE());
        callBack.enqueue(new Callback<List<UrlPhoto>>() {
            @Override
            public void onResponse(Call<List<UrlPhoto>> call, Response<List<UrlPhoto>> response) {
                ArrayList<UrlPhoto> arr = (ArrayList<UrlPhoto>) response.body();
                if (arr.size() > 0){
                    for (int i = arr.size() - 1; i >= 0; i--){
                        arrPhoto.add(arr.get(i));
                    }
                    adapterPhoto.notifyDataSetChanged();
                }
            }
            @Override
            public void onFailure(Call<List<UrlPhoto>> call, Throwable t) {
                Toast.makeText(InfoHouseActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
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

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapInfo);
        mapFragment.getMapAsync(this);
    }

    //LINH
    private void initViews() {
        setSupportActionBar(toolbar);
        //đặt tiêu đề giao diện là tên app
        getSupportActionBar().setTitle(R.string.app_name);
        if(getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        arrPhoto = new ArrayList<>();
        adapterPhoto = new GridViewPhotoAdapter(this,R.layout.item_gridview_photo,arrPhoto);

        //đổ dữ liệu hình ảnh vào adapter photo
        gvPhotoInfo.setAdapter(adapterPhoto);

        //lắng nghe sự kiện click vào ảnh, sau khi click vào ảnh nhỏ sẽ hiển thị ra ảnh to
        gvPhotoInfo.setOnItemClickListener(this);

        arrComment = new ArrayList<>();
        //đổ dữ liệu binh luận vào adapter comment
        adapterComment = new ListViewCommentAdapter(this,R.layout.item_listview_comment,arrComment);
        lvComment.setAdapter(adapterComment);

        //lắng nge sự kiện click gửi binh luận
        btnSend.setOnClickListener(this);
        //lắng nge sự kiện click yêu thích
        btnAddFavorite.setOnClickListener(this);
    }

    //xem ảnh theo dạng sliding, vuốt qua trái, phải để xem ảnh khác
    private void initDialogSliding(int currentPhoto) {
        dialogSlidingPhoto = new Dialog(this,android.R.style.Theme_Holo_Light_Dialog_NoActionBar_MinWidth);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            dialogSlidingPhoto.getWindow().setType(WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY);
        else {
            dialogSlidingPhoto.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        }
        dialogSlidingPhoto.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogSlidingPhoto.setContentView(R.layout.dialog_slide_photo);
        dialogSlidingPhoto.setCancelable(true);

        ViewPager viewPager = dialogSlidingPhoto.findViewById(R.id.viewpager);
        ImageButton btnDimiss = dialogSlidingPhoto.findViewById(R.id.btnDimissDialogSliding);
        SlidingPhotoAdapter adapterSliding = new SlidingPhotoAdapter(getApplicationContext(),arrPhoto);
        viewPager.setAdapter(adapterSliding);
        viewPager.setCurrentItem(currentPhoto);

        btnDimiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogSlidingPhoto.dismiss();
            }
        });

        dialogSlidingPhoto.show();
    }

    //ĐẠT: lựa chọn khi đặt phòng
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        //nhấn quay lại để hiển thị giao diện màn hình chính
        if (id == android.R.id.home)
        {
            finish();
        }
        //nếu nhấn icon đặt phòng
        else if (id == R.id.action_book)
        {
            //nếu đã đặt phòng này rôif
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
                //hiển thị dialog xác nhận muốn hủy đăng ký
                builder.setTitle(R.string.confirm)
                        .setMessage(R.string.delete_this_book)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener()
                        {
                            //sau khi nhấn có để hủy đăng ký thì sẽ thực hiện hàm deleteBook để hủy đặt
                            public void onClick(DialogInterface dialog, int which)
                            {
                                deleteBook();
                            }
                        })
                        .setNegativeButton(R.string.no, new DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface dialog, int which)
                            {
                                //ngược lại, nếu nhấn không để hủy đặt sẽ tắt dialog
                                dialog.dismiss();
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
            else //nếu chưa đặt phòng
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
                //hiển thị dialog xác nhận đặt phòng
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

    //ĐẠT: đối tượng hiển thị
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_book_info, menu);
        item =  menu.getItem(0);

        SharedPreferences pre = getSharedPreferences("studenthousing", MODE_PRIVATE);
        String user = pre.getString("user","");
        if (!user.equalsIgnoreCase("")){
            //cắt chuỗi để lấy id user và quyền user
            String[] arr = user.split("-");
            idUser = Integer.parseInt(arr[0]);
            permission = Integer.parseInt(arr[5]);

        }
        Intent intent = getIntent();
        House h = (House) intent.getSerializableExtra("house");

        //nếu user hiên tại dg xem bài viết này là người đăng bài thì sẽ ko hiển thị icon đặt phòng
        if (idUser == h.getIDUSER()){
            item.setVisible(false);
        }

        //nếu user hiện tại dg xem bài viết này là admin thì sẽ ko hiển thị icon đặt phòng
        if (permission == 1){
            item.setVisible(false);
        }else {
            //nếu phòng này chưa có ai đặt sẽ có icon dấu check màu trắng
            if (h.getSTATE() == 0){
                item.setIcon(R.drawable.icon_book_white);
                checkBook = false;
            }else {
                //kiểm tra xem user (user thường) dg xem bài viết có phải là người đã đặt phòng này hay ko
                DataClient dataClient = APIClient.getData();
                //yêu cầu thực hiện kiểm tra user có phải là người đặt hay ko trên server
                Call<String> callBack = dataClient.checkUserIsBooker(idUser,h.getIDHOUSE());
                callBack.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if (response.body().equals("true")){
                            //nếu ng này là ng đặt phòng bài viết này thì sẽ hiển thị icon hủy đặt phòng
                            item.setIcon(R.drawable.icon_unbook_white);
                            checkBook = true;
                        }else if (response.body().equals("false")){
                            //nếu ngược lại, người này ko phải là người đặt phòng này thì ko hiển thị icon gì hết, ko dc đặt phòng bài này nữa
                            item.setVisible(false);
                            checkBook = false;
                        }
                    }

                    //nếu server bị lỗi sẽ hiển thị toast
                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Toast.makeText(InfoHouseActivity.this, "fail2", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }



        return true;
    }

    //ĐẠT: Xử lý sau khi hủy đặt phòng
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
                    //đặt lại trạng thái phòng la còn trống
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

    //ĐẠT: xử lý đặt phòng
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

    //LINH: BẤM vào ảnh nhỏ bất kỳ để hiển thị slide ảnh
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        initDialogSliding(i);
    }

    //TOÀN: hiển thị map
    @Override
    public void onMapReady(GoogleMap googleMap)
    {
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

        LatLng begin = latLngLocation;
        mMap.moveCamera(CameraUpdateFactory.newLatLng(begin));
        CameraPosition position = new CameraPosition(begin,18,0,0);
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(position));

        Drawable circleDrawable = getResources().getDrawable(R.drawable.ic_location_pin);
        BitmapDescriptor markerIcon = getMarkerIconFromDrawable(circleDrawable);

        MarkerOptions options = new MarkerOptions();
        options.position(begin);
        options.title(house.getTITLE());
        options.snippet(house.getADDRESS());
        options.icon(markerIcon);

        mMap.addMarker(options);
    }
    //TOÀN: MARKER ĐÁNH DẤU TRÊN MAP
    private BitmapDescriptor getMarkerIconFromDrawable(Drawable drawable) {
        Canvas canvas = new Canvas();
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        canvas.setBitmap(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    //TOÀN: LẤY VĨ ĐỘ
    private double getLat(String latLng){
        String[] arrStr = latLng.split("\\(");
        String[] arrStr2 = arrStr[1].split(",");
        Log.d("LAT", "getLat: " + arrStr2[0]);
        return Double.parseDouble(arrStr2[0]);
    }

    //TOÀN: LÂY KINH ĐỘ
    private double getLng(String latLng){
        String[] arrStr = latLng.split("\\)");
        String[] arrStr2 = arrStr[0].split(",");
        return Double.parseDouble(arrStr2[1]);
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.btnSendComment:
                String text = edtComment.getText().toString();
                if (text.isEmpty()){
                    return;
                }

                String currentTime = Calendar.getInstance().getTime().toString();
                insertComment(text,house.getIDUSER(),house.getIDHOUSE(), currentTime);
                edtComment.setText("");
                break;

            //nguyen
            case R.id.btnAddFavorite:
                if (checkFav){
                    checkFav = false;
                    btnAddFavorite.setImageResource(R.drawable.icon_add_favorite);
                    removeFavorite();
                }else {
                    checkFav = true;
                    btnAddFavorite.setImageResource(R.drawable.icon_remove_favorite);
                    addFavorite();
                }
                break;
        }
    }
    //NGUYEN
    private void addFavorite(){
        DataClient dataClient = APIClient.getData();
        Call<String> callBack = dataClient.addFavorite(idUser,house.getIDHOUSE());
        callBack.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.body().equals("success")){
                    Snackbar snackbar = Snackbar
                            .make(btnAddFavorite, R.string.add_favorite_success, Snackbar.LENGTH_LONG);
                    snackbar.show();
                }else if (response.body().equals("fail")){
                    Toast.makeText(InfoHouseActivity.this, "fail", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(InfoHouseActivity.this, "fail2", Toast.LENGTH_SHORT).show();

            }
        });
    }
    //NGUYEN
    private void removeFavorite(){
        DataClient dataClient = APIClient.getData();
        Call<String> callBack = dataClient.removeFavorite(idUser,house.getIDHOUSE());
        callBack.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.body().equals("success")){
                    Snackbar snackbar = Snackbar
                            .make(btnAddFavorite, R.string.remove_favorite_success, Snackbar.LENGTH_LONG);
                    snackbar.show();
                }else if (response.body().equals("fail")){
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
            }
        });
    }

    //LINH: THÊM CMMT
    private void insertComment(String text, int idUser, int idHouse, String time){
        DataClient dataClient = APIClient.getData();
        Call<String> callBack = dataClient.insertComment(idUser,idHouse,text, time);
        callBack.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.body().equals("success")){
                    getComment();
                }else if (response.body().equals("fail")){

                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(InfoHouseActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
