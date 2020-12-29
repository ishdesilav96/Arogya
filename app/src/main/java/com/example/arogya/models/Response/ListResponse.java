package com.example.arogya.models.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListResponse {

    @SerializedName("disease")
    @Expose
    private String disease;


    public String getDisease() {
        return disease;
    }

    public void setDisease(String lotNumber) {
        this.disease = disease;
    }
}
