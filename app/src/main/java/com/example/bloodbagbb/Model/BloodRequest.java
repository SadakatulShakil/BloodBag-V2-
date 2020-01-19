package com.example.bloodbagbb.Model;

public class BloodRequest {
    private String userId;
    private String pushId;
    private String endDate;
    private String bloodGroup;
    private String contact;
    private String district;
    private String area;
    private String type;
    private String reason;
    private String postingTime;

    public BloodRequest() {
    }

    public BloodRequest(String userId, String pushId, String endDate,
                        String bloodGroup, String contact, String district,
                        String area, String type, String reason, String postingTime) {
        this.userId = userId;
        this.pushId = pushId;
        this.endDate = endDate;
        this.bloodGroup = bloodGroup;
        this.contact = contact;
        this.district = district;
        this.area = area;
        this.type = type;
        this.reason = reason;
        this.postingTime = postingTime;
    }

    public BloodRequest(String endDate, String bloodGroup, String contact,
                        String district, String area, String type, String reason) {

        this.endDate = endDate;
        this.bloodGroup = bloodGroup;
        this.contact = contact;
        this.district = district;
        this.area = area;
        this.type = type;
        this.reason = reason;
    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPushId() {
        return pushId;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getPostingTime() {
        return postingTime;
    }

    public void setPostingTime(String postingTime) {
        this.postingTime = postingTime;
    }
}
