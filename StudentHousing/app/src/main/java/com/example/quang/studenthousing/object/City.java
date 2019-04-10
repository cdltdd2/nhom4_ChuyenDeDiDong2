package com.example.quang.studenthousing.object;

public class City {
    private String ma;
    private String name;
    private String type;

    public City(String ma, String name, String type) {
        this.ma = ma;
        this.name = name;
        this.type = type;
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
}
