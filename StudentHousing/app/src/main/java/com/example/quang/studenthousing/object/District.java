package com.example.quang.studenthousing.object;

public class District {

    private String ma;
    private String name;
    private String type;
    private String maCity;

    public District(String ma, String name, String type, String maCity) {
        this.ma = ma;
        this.name = name;
        this.type = type;
        this.maCity = maCity;
    }

    public String getMa() {
        return ma;
    }

    public void setMa(String ma) {
        this.ma = ma;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMaCity() {
        return maCity;
    }

    public void setMaCity(String maCity) {
        this.maCity = maCity;
    }
}