//DUY: ADMIN QUẢN LÝ BÀI ĐĂNG: CHO PHÉP HOẶC KO CHO PHÉP BÀI ĐÓ ĐC ĐĂNG
package com.example.quang.studenthousing.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import com.example.quang.studenthousing.InfoHouseActivity;
import com.example.quang.studenthousing.ManagerActivity;
import com.example.quang.studenthousing.R;
import com.example.quang.studenthousing.adapter.GridViewHouseRequestAdapter;
import com.example.quang.studenthousing.object.House;
import com.example.quang.studenthousing.services.APIClient;
import com.example.quang.studenthousing.services.DataClient;
import java.util.ArrayList;
import java.util.List;
import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManagerPostFragment extends Fragment implements AdapterView.OnItemClickListener {

    private GridView gvAllHousesRequest;
    private ArrayList<House> arrHouses;
    private SpotsDialog progressDialog;
    private ManagerActivity activity;
    private GridViewHouseRequestAdapter adapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_manager_post,container,false);
        findID(view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        initViews();
        loadData();
    }

    private void findID(View view) {
        gvAllHousesRequest = view.findViewById(R.id.gvAllHousesRequest);
    }

    private void initViews() {
        activity = (ManagerActivity) getActivity();
        progressDialog = new SpotsDialog(activity, R.style.CustomProgressDialog);
        arrHouses = new ArrayList<>();
        adapter = new GridViewHouseRequestAdapter(activity, R.layout.item_gridview_house_request,arrHouses);
        gvAllHousesRequest.setAdapter(adapter);
        gvAllHousesRequest.setOnItemClickListener(this);
    }

    //tai lai ds
    private void loadData() {
        progressDialog.show();
        DataClient dataClient = APIClient.getData();
        //LẤY DỮ LIỆU TẤT CẢ BÀI ĐĂNG
        Call<List<House>> callBack = dataClient.getAllHouse();
        callBack.enqueue(new Callback<List<House>>() {
            @Override
            public void onResponse(Call<List<House>> call, Response<List<House>> response) {
                ArrayList<House> arrHouse = (ArrayList<House>) response.body();
                //co du lieu
                if (arrHouse.size() > 0){
                    for (int i = arrHouse.size() - 1; i >= 0; i--){
                        //bai chua kiem duyet se co trang thai la 0
                        if (arrHouse.get(i).getCHECKUP() == 0){
                            //LỌC RA BÀI ĐĂNG CHƯA ĐC KIỂM DUYỆT VÀ THÊM VÀO DS CẦN DUYỆT
                            arrHouses.add(arrHouse.get(i));
                        }
                    }
                    adapter.notifyDataSetChanged();
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<List<House>> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }

    //NHẤN VÀO BÀI ĐĂNG ĐỂ ADMIN XEM THÔNG TIN CHI TIẾT BÀI ĐÓ
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(activity,InfoHouseActivity.class);
        intent.putExtra("house",arrHouses.get(i));
        startActivity(intent);
    }
}
