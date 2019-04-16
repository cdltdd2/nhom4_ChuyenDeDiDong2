package com.example.quang.studenthousing.object;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PersonBooking {

    @SerializedName("IDUSER")
    @Expose
    private int iduser;
    @SerializedName("NAME")
    @Expose
    private String name;
    @SerializedName("PHONE")
    @Expose
    private String phone;
    @SerializedName("IDHOUSE")
    @Expose
    private int idhouse;
    @SerializedName("TITLE")
    @Expose
    private String title;


    public int getIDUSER() {
        return iduser;
    }

    public void setIDUSER(int iDUSER) {
        this.iduser = iDUSER;
    }

    public String getNAME() {
        return name;
    }

    public void setNAME(String nAME) {
        this.name = nAME;
    }

    public String getPHONE() {
        return phone;
    }

    public void setPHONE(String pHONE) {
        this.phone = pHONE;
    }

    public int getIDHOUSE() {
        return idhouse;
    }

    public void setIDHOUSE(int iDHOUSE) {
        this.idhouse = iDHOUSE;
    }

    public String getTITLE() {
        return title;
    }

    public void setTITLE(String tITLE) {
        this.title = tITLE;
    }

}