//ĐẠT: YÊU CẦU SERVER KIỂM TRA KẾT QUẢ LOGIN CÓ THÀNH CÔNG HAY KO
package com.example.quang.studenthousing.presenter.login;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.quang.studenthousing.MainActivity;
import com.example.quang.studenthousing.object.User;
import com.example.quang.studenthousing.services.APIClient;
import com.example.quang.studenthousing.services.DataClient;
import com.example.quang.studenthousing.view.login.ViewLogin;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PresenterLogicLogin implements PresenterImLogin{

    private ViewLogin viewLogin;

    public PresenterLogicLogin(ViewLogin viewLogin) {
        this.viewLogin = viewLogin;
    }


    @Override
    public void checkLogin(String user, String pass, final Context context) {
        DataClient dataClient = APIClient.getData();
        Call<List<User>> callBack = dataClient.login(user,pass);
        callBack.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                ArrayList<User> arrUser = (ArrayList<User>) response.body();
                if (arrUser.size() > 0){
                    String result = arrUser.get(0).getIDUSER()+"-"+arrUser.get(0).getUSER()
                            +"-"+arrUser.get(0).getPASSWORD()+"-"+arrUser.get(0).getNAME()
                            +"-"+arrUser.get(0).getPHONE()+"-"+arrUser.get(0).getPERMISSION();
                    viewLogin.loginSuccess(result);
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                viewLogin.loginFail();
            }
        });
    }
}
