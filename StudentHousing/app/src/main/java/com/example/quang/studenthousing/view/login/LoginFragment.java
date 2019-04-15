package com.example.quang.studenthousing.view.login;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.example.quang.studenthousing.AccountActivity;
import com.example.quang.studenthousing.R;
import com.example.quang.studenthousing.presenter.login.PresenterLogicLogin;
import com.example.quang.studenthousing.view.register.RegisterFragment;
import com.facebook.login.LoginManager;
import static android.content.Context.MODE_PRIVATE;

public class LoginFragment extends Fragment implements View.OnClickListener, ViewLogin
{

    public static final int RequestSignInCode = 7;
    private EditText edtUser;
    private EditText edtPass;
    private TextView tvRegister;
    private Button btnLogin;
    private AccountActivity activity;
    private PresenterLogicLogin presenterLogicLogin;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_login,container,false);
        findID(view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        LoginManager.getInstance().logOut();
        initViews();
    }

    private void findID(View view) {
        edtUser = view.findViewById(R.id.edtUserNameLogin);
        edtPass = view.findViewById(R.id.edtPasswordLogin);
        tvRegister = view. findViewById(R.id.tvRegisterUser);
        btnLogin = view.findViewById(R.id.btnLogin);
    }

    private void initViews() {
        activity = (AccountActivity) getActivity();
        tvRegister.setOnClickListener(this);
        btnLogin.setOnClickListener(this);

        presenterLogicLogin = new PresenterLogicLogin(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tvRegisterUser:
                activity.loadFragment(new RegisterFragment());
                break;
            case R.id.btnLogin:
                String user = edtUser.getText().toString();
                String pass = edtPass.getText().toString();

                if(user.isEmpty() || pass.isEmpty()){
                    Snackbar snackbar = Snackbar
                            .make(edtPass, R.string.insert_info, Snackbar.LENGTH_LONG);
                    snackbar.show();
                    return;
                }
                presenterLogicLogin.checkLogin(user,pass,activity);
                break;

        }
    }

    @Override
    public void loginSuccess(String result) {
        saveUser(result);
    }

    @Override
    public void loginFail() {
        Snackbar snackbar = Snackbar
                .make(edtPass, R.string.fail, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    @Override
    public void loginError() {

    }

    private void saveUser(String infoUser)
    {
        SharedPreferences pre = activity.getSharedPreferences("studenthousing", MODE_PRIVATE);
        SharedPreferences.Editor edit=pre.edit();
        edit.putString("user",infoUser);
        edit.commit();

        String[] arr = infoUser.split("-");
        int permission = Integer.parseInt(arr[5]);
        if (permission == 0 || permission == 2){
            activity.switchActivity();
        }else if (permission == 1){
            activity.switchActivityManager();
        }
    }


}
