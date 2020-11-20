package com.example.group7_project;


public class Travel {

    private int id;
    private boolean change;
    private int line_1;
    private int line_2;
    private int duration_1;
    private int duration_2;
    private int duration_wait;
    private int score;
    private int departure;
    private int arrival;
    private String from;
    private String to;
    private boolean best;

    public Travel(int id, boolean change, int line_1, int line_2, int duration_1, int duration_2, int duration_wait,
                  int score, int departure, int arrival, String from, String to) {
        this.id = id;
        this.change = change;
        this.line_1 = line_1;
        this.duration_1 = duration_1;
        this.duration_2 = duration_2;
        this.duration_wait = duration_wait;
        this.score = score;
        this.departure = departure;
        this.arrival = arrival;
        this.from = from;
        this.to = to;
        this.best = false;
    }

    public int getLine_1() {
        return line_1;
    }

    public void setLine_1(int line_1) {
        this.line_1 = line_1;
    }

    public int getLine_2() {
        return line_2;
    }

    public void setLine_2(int line_2) {
        this.line_2 = line_2;
    }

    public int getDuration_1() {
        return duration_1;
    }

    public void setDuration_1(int duration_1) {
        this.duration_1 = duration_1;
    }

    public int getDuration_2() {
        return duration_2;
    }

    public void setDuration_2(int duration_2) {
        this.duration_2 = duration_2;
    }

    public int getDuration_wait() {
        return duration_wait;
    }

    public void setDuration_wait(int duration_wait) {
        this.duration_wait = duration_wait;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getDeparture() {
        return departure;
    }

    public void setDeparture(int departure) {
        this.departure = departure;
    }

    public int getArrival() {
        return arrival;
    }

    public void setArrival(int arrival) {
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

    public boolean getBest() {
        return best;
    }

    public void setBest(boolean best) {
        this.best = best;
    }
}

