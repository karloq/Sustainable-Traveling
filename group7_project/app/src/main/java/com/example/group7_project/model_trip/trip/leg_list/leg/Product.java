package com.example.group7_project.model_trip.trip.leg_list.leg;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Product {

    @SerializedName("num")
    @Expose
    private String num;

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    @Override
    public String toString() {
        return "Product{" +
                "num='" + num + '\'' +
                '}';
    }
}
