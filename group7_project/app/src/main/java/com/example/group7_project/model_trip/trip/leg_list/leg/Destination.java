package com.example.group7_project.model_trip.trip.leg_list.leg;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Destination {

    @SerializedName("time")
    @Expose
    private String time;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Destination{" +
                "time='" + time + '\'' +
                '}';
    }
}
