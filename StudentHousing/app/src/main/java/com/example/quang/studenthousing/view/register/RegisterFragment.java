package com.example.quang.studenthousing.view.register;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.quang.studenthousing.AccountActivity;
import com.example.quang.studenthousing.R;

public class RegisterFragment extends Fragment
{

    private EditText edtUser;
    private EditText edtPass;
    private EditText edtConfirmPass;
    private EditText edtName;
    private EditText edtPhone;
    private Button btnRegister;
    private Button btnBack;
    private AccountActivity activity;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register,container,false);
        findID(view);
        return view;
    }
    private void findID(View view) {
        edtUser = view.findViewById(R.id.edtUserNameRegister);
        edtPass = view.findViewById(R.id.edtPasswordRegister);
        edtConfirmPass = view.findViewById(R.id.edtConfirmPasswordRegister);
        edtName = view.findViewById(R.id.edtNameRegister);
        edtPhone = view.findViewById(R.id.edtPhoneRegister);
        btnBack = view.findViewById(R.id.btnBackRegister);
        btnRegister = view.findViewById(R.id.btnRegister);
    }


}
