package com.example.group7_project.model_trip.trip;

import com.example.group7_project.model_trip.trip.leg_list.LegList;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Trip {
    @SerializedName("LegList")
    @Expose
    private LegList leglist;

    public LegList getLeglist() {
        return leglist;
    }

    public void setLeglist(LegList leglist) {
        this.leglist = leglist;
    }

    @Override
    public String toString() {
        return "Trip{" +
                "leglist=" + leglist +
                '}';
    }
}
