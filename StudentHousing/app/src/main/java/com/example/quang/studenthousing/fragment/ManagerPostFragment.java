package com.example.quang.studenthousing.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.example.quang.studenthousing.ManagerActivity;
import com.example.quang.studenthousing.R;
import com.example.quang.studenthousing.object.House;
import java.util.ArrayList;
import dmax.dialog.SpotsDialog;

public class ManagerPostFragment extends Fragment
{

    private GridView gvAllHousesRequest;
    private ArrayList<House> arrHouses;
    private SpotsDialog progressDialog;
    private ManagerActivity activity;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_manager_post,container,false);
        findID(view);
        return view;
    }

    private void findID(View view) {
        gvAllHousesRequest = view.findViewById(R.id.gvAllHousesRequest);
    }

}
