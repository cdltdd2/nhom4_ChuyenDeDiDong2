package com.example.quang.studenthousing.object;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class IDBooking {

    @SerializedName("IDBOOK")
    @Expose
    private int iDBOOK;

    public int getIDBOOK() {
        return iDBOOK;
    }

    public void setIDBOOK(int iDBOOK) {
        this.iDBOOK = iDBOOK;
    }

}