package com.example.group7_project;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;

public class TravelData {
    public ArrayList travelList;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public TravelData(String from, String to) {
        Trip trip = new Trip(from, to);
        this.travelList = new ArrayList<Travel>(trip.getTravels());
    }

    public ArrayList getTravelList() {
        return travelList;
    }

    public void setTravelList(ArrayList travelList) {
        this.travelList = travelList;
    }

}

