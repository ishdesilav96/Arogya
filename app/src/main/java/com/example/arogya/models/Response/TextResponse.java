package com.example.arogya.models.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TextResponse {

    @SerializedName("response")
    @Expose
    private String response;


    public String getResponse() {
        return response;
    }

    public void setresult(String lotNumber) {
        this.response = response;
    }




    @SerializedName("sentence")
    @Expose

    private String sentence;


    public String getSentence() {
        return sentence;
    }

    public void setSentence(String sentence) {
        this.sentence = sentence;
    }

}
