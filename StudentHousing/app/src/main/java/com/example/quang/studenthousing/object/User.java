package com.example.quang.studenthousing.object;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("IDUSER")
    @Expose
    private int idUser;
    @SerializedName("USER")
    @Expose
    private String user;
    @SerializedName("PASSWORD")
    @Expose
    private String password;
    @SerializedName("NAME")
    @Expose
    private String name;
    @SerializedName("PHONE")
    @Expose
    private String phone;
    @SerializedName("PERMISSION")
    @Expose
    private int permission;

    public int getIDUSER() {
        return idUser;
    }

    public void setIDUSER(int iDUSER) {
        this.idUser = iDUSER;
    }

    public String getUSER() {
        return user;
    }

    public void setUSER(String uSER) {
        this.user = uSER;
    }

    public String getPASSWORD() {
        return password;
    }

    public void setPASSWORD(String pASSWORD) {
        this.password = pASSWORD;
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

    public int getPERMISSION() {
        return permission;
    }

    public void setPERMISSION(int pERMISSION) {
        this.permission = pERMISSION;
    }

}