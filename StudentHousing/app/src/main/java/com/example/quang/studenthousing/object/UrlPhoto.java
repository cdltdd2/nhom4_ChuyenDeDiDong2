package com.example.quang.studenthousing.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UrlPhoto
{

    @SerializedName("URL")
    @Expose
    private String url;

    public String getURL() {
        return url;
    }

    public void setURL(String uRL) {
        this.url = uRL;
    }

}