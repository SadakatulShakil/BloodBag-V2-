package com.example.bloodbagbb.Model;

public class History {

    private String userId;
    private String pushId;
    private String donationDate;
    private String donationLacation;
    private String donationDescription;
    private String addingTime;

    public History() {

    }

    public History(String userId, String pushId, String addingTime,
                   String donationDate, String donationLacation,
                   String donationDescription) {
        this.userId = userId;
        this.pushId = pushId;
        this.addingTime = addingTime;
        this.donationDate = donationDate;
        this.donationLacation = donationLacation;
        this.donationDescription = donationDescription;
    }

    public History(String donationDate, String donationLacation, String donationDescription) {
        this.donationDate = donationDate;
        this.donationLacation = donationLacation;
        this.donationDescription = donationDescription;
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

    public String getAddingTime() {
        return addingTime;
    }

    public void setAddingTime(String addingTime) {
        this.addingTime = addingTime;
    }

    public String getDonationDate() {
        return donationDate;
    }

    public void setDonationDate(String donationDate) {
        this.donationDate = donationDate;
    }

    public String getDonationLacation() {
        return donationLacation;
    }

    public void setDonationLacation(String donationLacation) {
        this.donationLacation = donationLacation;
    }

    public String getDonationDescription() {
        return donationDescription;
    }

    public void setDonationDescription(String donationDescription) {
        this.donationDescription = donationDescription;
    }
}
