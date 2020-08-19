package com.example.androidshaper.androidsurveyapp.RetrofitService;

import com.example.androidshaper.androidsurveyapp.RetrofitModel.ObjectDataList;
import com.google.gson.JsonArray;

import java.sql.Timestamp;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;

public interface OurRetrofitClient {
    @Headers("date:{MY_date}")
    @GET("getSurvey")
    Call<JsonArray> getAllQuestion(@Header("MY_date") Timestamp timestamp);
}
