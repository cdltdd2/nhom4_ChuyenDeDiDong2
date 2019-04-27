//DUY: ADMIN QUẢN LÝ TÀI KHOẢN ĐĂNG BÀI, CHO PHÉP HOẶC KO CHO PHÉP TÀI KHOẢN ĐÓ ĐĂNG BÀI
package com.example.quang.studenthousing.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.quang.studenthousing.ManagerActivity;
import com.example.quang.studenthousing.R;
import com.example.quang.studenthousing.adapter.ListviewUserRegisterAdapter;
import com.example.quang.studenthousing.object.RegisterRequest;
import com.example.quang.studenthousing.services.APIClient;
import com.example.quang.studenthousing.services.DataClient;

import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManagerUserFragment extends Fragment
{

    private ListView lvUserRegister;
    private ListviewUserRegisterAdapter adapter;
    private ArrayList<RegisterRequest> arrRequest;
    private ManagerActivity activity;
    private SpotsDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_manager_user,container,false);
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
        lvUserRegister = view.findViewById(R.id.lvUserRegister);
    }

    private void initViews() {
        activity = (ManagerActivity) getActivity();
        arrRequest = new ArrayList<>();
        progressDialog = new SpotsDialog(activity, R.style.CustomProgressDialog);
        adapter = new ListviewUserRegisterAdapter(activity,R.layout.item_listview_user,arrRequest);
        lvUserRegister.setAdapter(adapter);
    }

    private void loadData(){
        progressDialog.show();
        DataClient dataClient = APIClient.getData();

        //request server lay ds user dang ky dang bai
        Call<List<RegisterRequest>> callBack = dataClient.getListUserRegister();
        callBack.enqueue(new Callback<List<RegisterRequest>>() {
            @Override
            public void onResponse(Call<List<RegisterRequest>> call, Response<List<RegisterRequest>> response) {
                ArrayList<RegisterRequest> arr = (ArrayList<RegisterRequest>) response.body();
                if (arr.size() > 0){
                    for (int i = arr.size() - 1; i >= 0; i--){
                        //ttang thai chua dong y se co gia tri la 0 dong thoi 0 cung la permission cua user thuong
                        //user da dc dky roi se co permission la 2 va chi dc dki bang bai 1 lan
                        if (arr.get(i).getCONFIRM() == 0)
                        {
                            arrRequest.add(arr.get(i));
                        }
                    }
                    adapter.notifyDataSetChanged();
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<List<RegisterRequest>> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }
}
