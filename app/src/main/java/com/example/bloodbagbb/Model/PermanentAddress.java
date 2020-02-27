package com.example.bloodbagbb.Model;

public class PermanentAddress {
    private String userId;
    private String division;
    private String district;
    private String upazilla;
    private String village;
    private String post_office;

    public PermanentAddress() {
    }

    public PermanentAddress(String division, String district, String upazilla, String village, String post_office) {
        this.division = division;
        this.district = district;
        this.upazilla = upazilla;
        this.village = village;
        this.post_office = post_office;
    }

    public PermanentAddress(String userId, String division, String district, String upazilla, String village, String post_office) {
        this.userId = userId;
        this.division = division;
        this.district = district;
        this.upazilla = upazilla;
        this.village = village;
        this.post_office = post_office;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getUpazilla() {
        return upazilla;
    }

    public void setUpazilla(String upazilla) {
        this.upazilla = upazilla;
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public String getPost_office() {
        return post_office;
    }

    public void setPost_office(String post_office) {
        this.post_office = post_office;
    }
}
