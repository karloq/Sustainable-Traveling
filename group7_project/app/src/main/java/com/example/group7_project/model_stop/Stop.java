package com.example.group7_project.model_stop;

import com.example.group7_project.model_stop.stoplocation.StopLocation;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Stop {

     @SerializedName("StopLocation")
     @Expose
    private ArrayList<StopLocation> stoplocation;

    public ArrayList<StopLocation> getStoplocation() {
        return stoplocation;
    }

    public void setStoplocation(ArrayList<StopLocation> stoplocation) {
        this.stoplocation = stoplocation;
    }

    @Override
    public String toString() {
        return "Stop{" +
                "stoplocation=" + stoplocation +
                '}';
    }
}
