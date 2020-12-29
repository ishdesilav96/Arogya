package com.example.arogya.models;

public enum SnackType {

    ERROR("ERROR"),
    WARNING("WARNING"),
    NORMAL("NORMAL");


    private String key;

    SnackType(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
