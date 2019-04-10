package com.example.quang.studenthousing.object;

public class Ward {
    private String id;
    private String name;
    private String type;
    private String maDistrict;

    public Ward(String id, String name, String type, String maDistrict) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.maDistrict = maDistrict;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getMaDistrict() {
        return maDistrict;
    }

    public void setMaDistrict(String maDistrict) {
        this.maDistrict = maDistrict;
    }
}