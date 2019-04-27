//NGUYEN
package com.example.quang.studenthousing.services;

import com.example.quang.studenthousing.object.Comment;
import com.example.quang.studenthousing.object.Favorite;
import com.example.quang.studenthousing.object.House;
import com.example.quang.studenthousing.object.IDBooking;
import com.example.quang.studenthousing.object.PersonBooking;
import com.example.quang.studenthousing.object.RegisterRequest;
import com.example.quang.studenthousing.object.UrlPhoto;
import com.example.quang.studenthousing.object.User;
import io.reactivex.Observable;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/*
* quan ly nhung phuong thuc request len server: get, put, delete
* nhung phuong thuc nay gui di se nhan ve
*
* */
public interface DataClient
{
    //FormUrlEncoded: gui du lieu dang chuoi voi login la ten request(@field: tham so)
    @FormUrlEncoded
    @POST("login.php")
    Call<List<User>> login(@Field("user") String user
            , @Field("pass") String pass);

    @FormUrlEncoded
    @POST("register.php")
    Call<String> register(@Field("user") String user
            , @Field("pass") String pass, @Field("name") String name, @Field("phone") String phone);

    @GET("getAllHouses.php")
    Call<List<House>> getAllHouse();

    @FormUrlEncoded
    @POST("registerPoster.php")
    Call<String> registerPoster(@Field("id") int id);

    @FormUrlEncoded
    @POST("uploadImage.php")
    Call<String> uploadImage(@Field("imageCode")String imgCode,
                             @Field("imageName")String imgName);

    @FormUrlEncoded
    @POST("removeImage.php")
    Call<String> removeImage(@Field("imgPath")String imgPath);

    @FormUrlEncoded
    @POST("createPost.php")
    Call<List<House>> createPost(@Field("title") String title
            , @Field("address") String address, @Field("object") int object, @Field("image") String image
            , @Field("desc") String desc, @Field("contact") String contact
            , @Field("acreage") float acreage, @Field("price") float price
            , @Field("maxpeo") int maxpeo, @Field("created_at") String created_at
            , @Field("idUser") int idUser, @Field("latlng") String latlng);

    @FormUrlEncoded
    @POST("insertImageForHouse.php")
    Call<String> insertImageForHouse(@Field("url")String url, @Field("idHouse")int idHouse);

    @FormUrlEncoded
    @POST("updateCheckUpHouse.php")
    Call<String> updateCheckUpHouse(@Field("idHouse")int id, @Field("result")int result);

    @FormUrlEncoded
    @POST("updatePermissionUser.php")
    Call<String> updatePermissionUser(@Field("idUser")int id, @Field("result")int result);

    @GET("getListUserRegisterPort.php")
    Call<List<RegisterRequest>> getListUserRegister();

    @FormUrlEncoded
    @POST("getPhotoInfo.php")
    Call<List<UrlPhoto>> getPhotoInfo(@Field("idHouse")int id);

    @FormUrlEncoded
    @POST("insertComment.php")
    Call<String> insertComment(@Field("user")int idUser, @Field("house")int idHouser
            , @Field("text")String text, @Field("time")String time);

    @FormUrlEncoded
    @POST("getComment.php")
    Call<List<Comment>> getComment(@Field("house")int id);

    @FormUrlEncoded
    @POST("getFavoriteCount.php")
    Call<List<Favorite>> getFavCount(@Field("idUser")int id);

    @FormUrlEncoded
    @POST("addFavorite.php")
    Call<String> addFavorite(@Field("idUser")int idUser, @Field("idHouse")int idHouse);

    @FormUrlEncoded
    @POST("removeFavorite.php")
    Call<String> removeFavorite(@Field("idUser")int idUser, @Field("idHouse")int idHouser);

    @FormUrlEncoded
    @POST("getHouseFavorite.php")
    Call<List<House>> getHouseFavorite(@Field("id")int id);

    @FormUrlEncoded
    @POST("getHouseUploaded.php")
    Call<List<House>> getHouseUploaded(@Field("id")int id);

    @FormUrlEncoded
    @POST("updateInfoHouse.php")
    Call<String> updateInfoHouse(@Field("id") int id, @Field("title") String title
            , @Field("address") String address, @Field("object") int object
            , @Field("desc") String desc, @Field("contact") String contact
            , @Field("acreage") float acreage, @Field("price") float price
            , @Field("maxpeo") int maxpeo, @Field("latlng") String latlng, @Field("state") int state);

    @FormUrlEncoded
    @POST("deleteHouse.php")
    Call<String> deleteHouse(@Field("id") int id);

    @FormUrlEncoded
    @POST("bookRoom.php")
    Call<String> bookRoom(@Field("idUser") int idUser, @Field("idHouse") int idHouse);

    @FormUrlEncoded
    @POST("checkUserIsBooker.php")
    Call<String> checkUserIsBooker(@Field("idUser") int idUser, @Field("idHouse") int idHouse);

    @FormUrlEncoded
    @POST("deleteBooking.php")
    Call<String> deleteBooking(@Field("idUser") int idUser, @Field("idHouse") int idHouse);

    @FormUrlEncoded
    @POST("checkNewBooking.php")
    Call<List<IDBooking>> checkNewBooking(@Field("idUser") int idUser);

    @FormUrlEncoded
    @POST("updateCheckSeenBooking.php")
    Call<String> updateCheckSeen(@Field("idBooking") int id);

    @FormUrlEncoded
    @POST("getListPeopleBooking.php")
    Call<List<PersonBooking>> getListBooking(@Field("idUser") int id);
}
