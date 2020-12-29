package com.example.arogya.models;

import android.graphics.Bitmap;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

public class model {

    int image, dpImage;
    String discription, name, date, time;

    List<String> photo ;
    Uri fbPhotoUri;

    Bitmap bitmap;

    public model(String date,  String discription, String name, List<String> photo, String time,Uri fbPhotoUri)  {

        this.date = date;
        this.discription = discription;
        this.name = name;

        this.time = time;
        this.photo = photo;
        this.fbPhotoUri = fbPhotoUri;
    }

    public Uri getFbPhotoUri() {
        return fbPhotoUri;
    }

    public void setFbPhotoUri(Uri fbPhotoUri) {
        this.fbPhotoUri = fbPhotoUri;
    }

    public List<String> getPhoto() {
        return photo;
    }

    public void setPhoto(List<String> photo) {
        this.photo = photo;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }


    public  model(){

    }



    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public int getDpImage() {
        return dpImage;
    }

    public void setDpImage(int dpImage) {
        this.dpImage = dpImage;
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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