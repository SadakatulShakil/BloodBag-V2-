package com.example.bloodbagbb.Model;

public class CurrentAddress {
    private String userId;
    private String district;
    private String area;
    private String houseNo;
    private String roadNo;
    private String flatNo;

    public CurrentAddress() {
    }

    public CurrentAddress(String district, String area, String houseNo, String roadNo, String flatNo) {
        this.district = district;
        this.area = area;
        this.houseNo = houseNo;
        this.roadNo = roadNo;
        this.flatNo = flatNo;
    }

    public CurrentAddress(String userId, String district, String area, String houseNo, String roadNo, String flatNo) {
        this.userId = userId;
        this.district = district;
        this.area = area;
        this.houseNo = houseNo;
        this.roadNo = roadNo;
        this.flatNo = flatNo;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getHouseNo() {
        return houseNo;
    }

    public void setHouseNo(String houseNo) {
        this.houseNo = houseNo;
    }

    public String getRoadNo() {
        return roadNo;
    }

    public void setRoadNo(String roadNo) {
        this.roadNo = roadNo;
    }

    public String getFlatNo() {
        return flatNo;
    }

    public void setFlatNo(String flatNo) {
        this.flatNo = flatNo;
    }
}
