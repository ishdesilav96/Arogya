package com.example.arogya.manager.ServiceManager;

public class APIUtils {

    private APIUtils() {
    }

//    public static final String CONTENT_TYPE = "application/json";


    public static final String BASE_URL = "http://52.152.128.97:5000/";
    public static final String BASE_URL_2 = "http://researchazure.southeastasia.cloudapp.azure.com/";



    public static APIService getApiService() {
        return RetrofitManager.getClient(BASE_URL).create(APIService.class);
    }

    public static APIService getApiService_2() {
        return RetrofitManager.getClient(BASE_URL_2).create(APIService.class);
    }
}
