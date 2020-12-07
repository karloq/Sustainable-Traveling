package com.example.group7_project.model_trip.trip.leg_list;

import com.example.group7_project.model_trip.trip.leg_list.leg.Leg;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class LegList {

    @SerializedName("Leg")
    @Expose
    private ArrayList<Leg> legs;

    public ArrayList<Leg> getLegs() {
        return legs;
    }

    public void setLegs(ArrayList<Leg> legs) {
        this.legs = legs;
    }

    @Override
    public String toString() {
        return "LegList{" +
                "leg=" + legs +
                '}';
    }
}
