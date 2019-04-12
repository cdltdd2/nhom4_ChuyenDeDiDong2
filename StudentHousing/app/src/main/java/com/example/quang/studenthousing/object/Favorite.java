package com.example.quang.studenthousing.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

//neu k implement se xay ra NotSerializableException
public class Favorite implements Serializable
{
    /*@SerializedName: ánh xạ các khoá JSON với các trường dữ liệu.
    Để phù hợp với quy ước đặt tên kiểu camelCase của Java cho các thuộc tính thành viên của lớp
    @Expose chỉ ra rằng trường này nên được định nghĩa với JSON serialization hoặc deserialization*/

    @SerializedName("IDFAV")
    @Expose
    private int idFav;

    @SerializedName("IDUSER")
    @Expose
    private int idUser;

    @SerializedName("IDHOUSE")
    @Expose
    private int idHouse;

    public int getIDFAV() {
        return idFav;
    }

    public void setIDFAV(int iDFAV) {
        this.idFav = iDFAV;
    }

    public int getIDUSER() {
        return idUser;
    }

    public void setIDUSER(int iDUSER) {
        this.idUser = iDUSER;
    }

    public int getIDHOUSE() {
        return idHouse;
    }

    public void setIDHOUSE(int iDHOUSE) {
        this.idHouse = iDHOUSE;
    }

}