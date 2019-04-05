package com.example.quang.studenthousing.view.login;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.quang.studenthousing.AccountActivity;
import com.example.quang.studenthousing.R;

public class LoginFragment extends Fragment
{
    private EditText edtUser;
    private EditText edtPass;
    private TextView tvRegister;
    private Button btnLogin;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login,container,false);
        findID(view);
        return view;
    }

    private void findID(View view) {
        edtUser = view.findViewById(R.id.edtUserNameLogin);
        edtPass = view.findViewById(R.id.edtPasswordLogin);
        tvRegister = view. findViewById(R.id.tvRegisterUser);
        btnLogin = view.findViewById(R.id.btnLogin);
    }

}
