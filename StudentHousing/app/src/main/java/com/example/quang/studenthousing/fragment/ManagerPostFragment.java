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
import java.util.ArrayList;
import dmax.dialog.SpotsDialog;


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


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(activity,InfoHouseActivity.class);
        intent.putExtra("house",arrHouses.get(i));
        startActivity(intent);
    }
}
