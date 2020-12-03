package com.example.group7_project;

import com.example.group7_project.model_stop.Stop;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface StopAPI {

    String BASE_URL_STOP = "https://api.resrobot.se/v2/";

    @Headers("Content-Type: application/json")
    @GET("location.name?key=9caf98cd-80b7-4594-a7b8-950e463047af&input=chalmers&format=json")
    Call<Stop> getStop();
}
