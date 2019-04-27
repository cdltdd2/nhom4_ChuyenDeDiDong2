//DUY: DANH SÁCH BÀI ĐÃ ĐĂNG
package com.example.quang.studenthousing;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import com.example.quang.studenthousing.adapter.GridViewHouseUploadedAdapter;
import com.example.quang.studenthousing.object.House;
import com.example.quang.studenthousing.services.APIClient;
import com.example.quang.studenthousing.services.DataClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostUploadedActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private Toolbar toolbar;
    private GridView gvPostUploaded;
    private GridViewHouseUploadedAdapter adapter;
    private ArrayList<House> arrPostUploaded;
    private int idUser;

    //chạy hàm này đầu tiên
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //đẩy giao diện bài đã đăng
        setContentView(R.layout.activity_post_uploaded);

        //chạy hàm này để dò id trong layout rồi ánh xạ cho biến khởi tạo phía trên
        findId();
        initView();

        //đẩy dữ liệu vào adapter
        loadData();
    }



    private void findId() {
        toolbar = findViewById(R.id.toolbarPostUploaded);
        gvPostUploaded = findViewById(R.id.gvPostUploaded);
    }

    private void initView() {
        //tool bar là thanh tiêu đề phía trên cùng chứa tiêu đề, menu
        setSupportActionBar(toolbar);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Objects.requireNonNull(getSupportActionBar()).setHomeButtonEnabled(true);
        }

        //đặt tiêu đề cho thanh toolbar
        getSupportActionBar().setTitle(R.string.uploaded);

        //tạo mới mảng chứa danh sách bài đã đăng
        arrPostUploaded = new ArrayList<>();

        //tạo mới adapter để chứa dữ liệu, giao diện của adapter là file "item_gridview_house_request.xml",
        // dữ liệu là mảng arrPostUploaded được đổ vào adapter này (mỗi adapter này là 1 bài đăng)
        adapter = new GridViewHouseUploadedAdapter(this,R.layout.item_gridview_house_request,arrPostUploaded);

        //đổ 1 bài đăng (adapter phía trên) vào gvPostUploaded (adapter gvPostUploaded là adapter cha)
        gvPostUploaded.setAdapter(adapter);

        //sự kiện khi click vào 1 bài đăng
        gvPostUploaded.setOnItemClickListener(this);
    }

    //lấy dữ liệu
    private void loadData() {
        //kiểm tra xem user hiện tại
        SharedPreferences pre = getSharedPreferences("studenthousing", MODE_PRIVATE);
        String user = pre.getString("user","");

        //lấy id user ở kí tự đầu tiên của mảng và parse (convert) sang int
        if (!user.equalsIgnoreCase("")){
            String[] arr = user.split("-");
            idUser = Integer.parseInt(arr[0]);
        }

        //gọi hàm này để lấy danh sách bài đã đăng
        getHouses();
    }

    //lấy danh sách bài đã đăng
    private void getHouses() {
        //gửi 1 yêu cầu lấy bài đã đăng lên server với tham số là id user
        DataClient dataClient = APIClient.getData();
        Call<List<House>> callBack = dataClient.getHouseUploaded(idUser);

        //kết quả trả về là 1 danh sách đối tượng bài đăng đã được convert từ json sang java
        callBack.enqueue(new Callback<List<House>>() {

            //server có kết quả trả về
            @Override
            public void onResponse(Call<List<House>> call, Response<List<House>> response) {
                ArrayList<House> arrHouse = (ArrayList<House>) response.body();
                arrPostUploaded.clear();
                if (arrHouse.size() > 0){
                    for (int i = arrHouse.size() - 1; i >= 0; i--){
                        if (arrHouse.get(i).getCHECKUP() == 1){
                            //những bài có check up = 1 là những bài đã đc admin duyệt
                            arrPostUploaded.add(arrHouse.get(i));
                        }
                    }
                    //cập nhật lại adapter
                    adapter.notifyDataSetChanged();
                }
            }

            //server bị lỗi
            @Override
            public void onFailure(Call<List<House>> call, Throwable t) {
            }
        });
    }

    //sự kiện click chọn 1 item trong menu chức năng
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //gán id của chức năng đó cho biến id
        int id = item.getItemId();

        //nếu id bằng với id home (màn hình chính) thì kết thúc giao diện bài đã đăng
        if (id == android.R.id.home){
            finish();
        }

        //đưa tới giao diện màn hình khi nhấn vào chức năng tương ứng
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
}
