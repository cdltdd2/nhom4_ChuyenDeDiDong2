package com.example.quang.studenthousing.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RegisterRequest {

    @SerializedName("IDUSER")
    @Expose
    private int idUser;
    @SerializedName("USER")
    @Expose
    private String user;
    @SerializedName("NAME")
    @Expose
    private String name;
    @SerializedName("PHONE")
    @Expose
    private String phone;
    @SerializedName("PERMISSION")
    @Expose
    private int permission;
    @SerializedName("IDREGISTER")
    @Expose
    private int idRegister;
    @SerializedName("CONFIRM")
    @Expose
    private int confirm;

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

    public int getIDREGISTER() {
        return idRegister;
    }

    public void setIDREGISTER(int iDREGISTER) {
        this.idRegister = iDREGISTER;
    }

    public int getCONFIRM() {
        return confirm;
    }

    public void setCONFIRM(int cONFIRM) {
        this.confirm = cONFIRM;
    }

}