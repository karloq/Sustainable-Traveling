package com.example.group7_project.model_trip.trip.leg_list.leg;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Leg {
    @SerializedName("Origin")
    @Expose
    private Origin origin;

    @SerializedName("Destination")
    @Expose
    private Destination destination;

    @SerializedName("Product")
    @Expose
    private Product product;

    public Origin getOrigin() {
        return origin;
    }

    public void setOrigin(Origin origin) {
        this.origin = origin;
    }

    public Destination getDestination() {
        return destination;
    }

    public void setDestination(Destination destination) {
        this.destination = destination;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public String toString() {
        return "Leg{" +
                "origin=" + origin +
                ", destination=" + destination +
                ", product=" + product +
                '}';
    }
}
