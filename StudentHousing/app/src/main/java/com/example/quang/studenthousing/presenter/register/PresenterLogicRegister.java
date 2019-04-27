//LINH: YÊU CẦU SERVER KIỂM TRA TÀI KHOẢN ĐÃ ĐC ĐĂNG KÝ CHƯA VÀ KẾT QUẢ ĐĂNG KÝ
package com.example.quang.studenthousing.presenter.register;

import android.content.Context;
import android.widget.Toast;

import com.example.quang.studenthousing.object.User;
import com.example.quang.studenthousing.services.APIClient;
import com.example.quang.studenthousing.services.DataClient;
import com.example.quang.studenthousing.view.register.ViewRegister;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PresenterLogicRegister implements PresenterImRegister{

    private ViewRegister viewRegister;

    public PresenterLogicRegister(ViewRegister viewRegister) {
        this.viewRegister = viewRegister;
    }

    @Override
    public void checkRegister(String user, String pass, String name, String phone, final Context context) {
        DataClient dataClient = APIClient.getData();
        Call<String> callBack = dataClient.register(user,pass,name,phone);
        callBack.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.body().equals("exists")){
                    viewRegister.userExists();
                }else if (response.body().equals("success")){
                    viewRegister.registerSuccess();
                }else if (response.body().equals("fail")){
                    viewRegister.registerFail();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
