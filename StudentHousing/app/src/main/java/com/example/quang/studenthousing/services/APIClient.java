package com.example.quang.studenthousing.services;

//chua duong dan
public class APIClient {
    public static final String BASE_URL = "http://toantic.com/api/StudentHousing/";

    //nhan va gui du lieu di, phuong thuc trung gian
    public static DataClient getData()
    {
        //create...: khoi tao noi chua du lieu
        return RetrofitClient.getClient(BASE_URL).create(DataClient.class);
    }
}
