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
import java.util.ArrayList;
import dmax.dialog.SpotsDialog;

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
}
