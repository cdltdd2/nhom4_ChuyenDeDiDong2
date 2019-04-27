//NGUYEN
package com.example.quang.studenthousing.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//cau hinh va khoi tao retrofit de co the gui request (url) len server
public class RetrofitClient
{
    public static Retrofit retrofit = null;
    public static Retrofit getClient(String baseUrl)
    {
        //OkHttpClient: phien ban sau cua http
        //connectTimeout: thoi gian doi connect server qua 10s
        //retry: khoi phục lai ket noi
        OkHttpClient builder = new OkHttpClient.Builder()
                .readTimeout(5000, TimeUnit.MILLISECONDS)
                .writeTimeout(5000,TimeUnit.MILLISECONDS)
                .connectTimeout(10000,TimeUnit.MILLISECONDS)
                .retryOnConnectionFailure(true)
                .build();

        //trong qua trinh chuyen co the xay ra loi thi line nay ho tro build code tot hon
        Gson gson = new GsonBuilder().setLenient().create();

        //baseUrl(baseUrl): khoi tao
        //client: set cai dat ben tren
        //addConverterFactory: chuyen tu json ve java thong qua gson

        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(builder)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        return retrofit;
    }
}
