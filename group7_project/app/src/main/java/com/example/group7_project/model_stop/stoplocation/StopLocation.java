package com.example.group7_project.model_stop.stoplocation;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StopLocation {

    @SerializedName("id")
    @Expose
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "StopLocation{" +
                "id=" + id +
                '}';
    }
}
