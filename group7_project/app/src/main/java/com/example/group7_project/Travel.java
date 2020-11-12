package com.example.group7_project;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "travel_table")
public class Travel {

    @PrimaryKey(autoGenerate = true)

    private int id;
    private int duration;
    private int score;
    private String departure;
    private String arrival;
    private String from;
    private String to;

    public Travel(int id, int duration, int score, String departure, String arrival, String from, String to) {
        this.id = id;
        this.duration = duration;
        this.score = score;
        this.departure = departure;
        this.arrival = arrival;
        this.from = from;
        this.to = to;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public String getArrival() {
        return arrival;
    }

    public void setArrival(String arrival) {
        this.arrival = arrival;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }
}

