package com.example.arogya.manager.ServiceManager;

import com.example.arogya.models.Requests.ImageRequest;
import com.example.arogya.models.Requests.ListRequest;
import com.example.arogya.models.Requests.TextRequest;
import com.example.arogya.models.Response.ImageResponse;
import com.example.arogya.models.Response.ListResponse;
import com.example.arogya.models.Response.TextResponse;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {
    @Headers("Content-Type: application/json")
    @POST("expression/")
    Call<ImageResponse> imagePass(

            @Body ImageRequest body);


    @Headers("Content-Type: application/json")
    @POST("DiseasePrediction")
    Call<ListResponse> listPass(

            @Body ListRequest body);

    @Headers("Content-Type: application/json")
    @POST("SimilarityDetection")
    Call<TextResponse> textPass(

            @Body TextRequest body);

}
