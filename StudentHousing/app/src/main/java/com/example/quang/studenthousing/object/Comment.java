package com.example.quang.studenthousing.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Comment {

    @SerializedName("IDCOMMENT")
    @Expose
    private int idComment;
    @SerializedName("TEXT")
    @Expose
    private String text;
    @SerializedName("IDUSER")
    @Expose
    private int idUser;
    @SerializedName("IDHOUSE")
    @Expose
    private int idHouse;
    @SerializedName("CREATED_AT")
    @Expose
    private String created_at;

    public int getIDCOMMENT() {
        return idComment;
    }

    public void setIDCOMMENT(int iDCOMMENT) {
        this.idComment = iDCOMMENT;
    }

    public String getTEXT() {
        return text;
    }

    public void setTEXT(String tEXT) {
        this.text = tEXT;
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

    public String getCREATEDAT() {
        return created_at;
    }

    public void setCREATEDAT(String cREATEDAT) {
        this.created_at = cREATEDAT;
    }

}