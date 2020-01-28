package com.example.bloodbagbb.Model;

import java.io.Serializable;

public class User implements Serializable {
    private String userId;
    private String name;
    private String email;
    private String contact;
    private String district;
    private String area;
    private String bloodGroup;

    public User() {

    }

    public User(String userId, String name, String email, String contact, String district, String area, String bloodGroup) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.contact = contact;
        this.district = district;
        this.area = area;
        this.bloodGroup = bloodGroup;
    }

    public User(String name, String email, String contact, String district, String area, String bloodGroup) {
        this.name = name;
        this.email = email;
        this.contact = contact;
        this.district = district;
        this.area = area;
        this.bloodGroup = bloodGroup;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", contact='" + contact + '\'' +
                ", district='" + district + '\'' +
                ", area='" + area + '\'' +
                ", bloodGroup='" + bloodGroup + '\'' +
                '}';
    }
}
