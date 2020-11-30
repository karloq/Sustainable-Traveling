package com.example.group7_project;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface TravelAPI {

    @GET("trip")
    Call<List<Trip>> getTrips();
}