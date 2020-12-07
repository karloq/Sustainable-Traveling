package com.example.group7_project.model_trip;

import com.example.group7_project.model_trip.trip.Trip;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class TripPlan {

    @SerializedName("Trip")
    @Expose
    private ArrayList<Trip> trips;

    public ArrayList<Trip> getTrips() {
        return trips;
    }

    public void setTrips(ArrayList<Trip> trips) {
        this.trips = trips;
    }

    @Override
    public String toString() {
        return "TripPlan{" +
                "trip=" + trips +
                '}';
    }
}
