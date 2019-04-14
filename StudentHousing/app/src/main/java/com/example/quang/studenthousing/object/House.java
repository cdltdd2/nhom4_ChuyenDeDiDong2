package com.example.quang.studenthousing.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class House implements Serializable
{
    //convert tư bien json sang java qa gson
    @SerializedName("IDHOUSE")
    @Expose
    private int idHouse;
    @SerializedName("TITLE")
    @Expose
    private String title;
    @SerializedName("ADDRESS")
    @Expose
    private String address;
    @SerializedName("OBJECT")
    @Expose
    private int object;
    @SerializedName("IMAGE")
    @Expose
    private String image;
    @SerializedName("DESC")
    @Expose
    private String desc;
    @SerializedName("CONTACT")
    @Expose
    private String contact;
    @SerializedName("ACREAGE")
    @Expose
    private float acreage;
    @SerializedName("PRICE")
    @Expose
    private float price;
    @SerializedName("MAXPEO")
    @Expose
    private String maxpeo;
    @SerializedName("CREATED_AT")
    @Expose
    private String createdat;
    @SerializedName("CHECK_UP")
    @Expose
    private int checkup;
    @SerializedName("STATE")
    @Expose
    private int state;
    @SerializedName("IDUNIT")
    @Expose
    private int idunit;
    @SerializedName("IDUSER")
    @Expose
    private int iduser;
    @SerializedName("LATLNG")
    @Expose
    private String latlng;

    public int getIDHOUSE() {
        return idHouse;
    }

    public void setIDHOUSE(int iDHOUSE) {
        this.idHouse = iDHOUSE;
    }

    public String getTITLE() {
        return title;
    }

    public void setTITLE(String tITLE) {
        this.title = tITLE;
    }

    public String getADDRESS() {
        return address;
    }

    public void setADDRESS(String aDDRESS) {
        this.address = aDDRESS;
    }

    public int getOBJECT() {
        return object;
    }

    public void setOBJECT(int oBJECT) {
        this.object = oBJECT;
    }

    public String getIMAGE() {
        return image;
    }

    public void setIMAGE(String iMAGE) {
        this.image = iMAGE;
    }

    public String getDESC() {
        return desc;
    }

    public void setDESC(String dESC) {
        this.desc = dESC;
    }

    public String getCONTACT() {
        return contact;
    }

    public void setCONTACT(String cONTACT) {
        this.contact = cONTACT;
    }

    public float getACREAGE() {
        return acreage;
    }

    public void setACREAGE(float aCREAGE) {
        this.acreage = aCREAGE;
    }

    public float getPRICE() {
        return price;
    }

    public void setPRICE(float pRICE) {
        this.price = pRICE;
    }

    public String getMAXPEO() {
        return maxpeo;
    }

    public void setMAXPEO(String mAXPEO) {
        this.maxpeo = mAXPEO;
    }

    public String getCREATEDAT() {
        return createdat;
    }

    public void setCREATEDAT(String cREATEDAT) {
        this.createdat = cREATEDAT;
    }

    public int getCHECKUP() {
        return checkup;
    }

    public void setCHECKUP(int cHECKUP) {
        this.checkup = cHECKUP;
    }

    public int getSTATE() {
        return state;
    }

    public void setSTATE(int sTATE) {
        this.state = sTATE;
    }

    public int getIDUNIT() {
        return idunit;
    }

    public void setIDUNIT(int iDUNIT) {
        this.idunit = iDUNIT;
    }

    public int getIDUSER() {
        return iduser;
    }

    public void setIDUSER(int iDUSER) {
        this.iduser = iDUSER;
    }

    public String getLatlng() {
        return latlng;
    }

    public void setLatlng(String latlng) {
        this.latlng = latlng;
    }
}