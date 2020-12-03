package com.example.group7_project;

import com.example.group7_project.model_trip.TripPlan;

import retrofit2.Call;
import retrofit2.http.GET;

public interface TripAPI {
    String BASE_URL_TRIP = "https://api.resrobot.se/v2/";

    @GET("trip?key=9caf98cd-80b7-4594-a7b8-950e463047af&originId=740015573&destId=740025617&format=json")
    Call<TripPlan> getTrips();
}