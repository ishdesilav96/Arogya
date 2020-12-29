package com.example.arogya.models;

import android.net.Uri;

import java.util.List;

public class Member {

    String name;
    List<String> photo;
    String discription;
    String date;
    String time;
    Uri fbProfilePhoto;


    public Member() {
        this.name = name;
        this.photo = photo;
        this.discription = discription;
        this.date = date;
        this.time = time;
        this.fbProfilePhoto = fbProfilePhoto;
    }

    public Uri getFbProfilePhoto(Uri fbPhotoUri) {
        return fbProfilePhoto;
    }

    public void setFbProfilePhoto(Uri fbProfilePhoto) {
        this.fbProfilePhoto = fbProfilePhoto;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getPhoto() {
        return photo;
    }

    public void setPhoto(List<String> photo) {
        this.photo = photo;
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
