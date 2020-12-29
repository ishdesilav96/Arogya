package com.example.arogya.models.Requests;

public class ImageRequest {
//    @SerializedName("text")

    private String text,type,rootType;

    public ImageRequest(String text, String type, String rootType) {
        this.text = text;
        this.type = type;
        this.rootType = rootType;
    }
}
