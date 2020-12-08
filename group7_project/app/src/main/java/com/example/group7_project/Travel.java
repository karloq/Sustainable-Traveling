package com.example.group7_project;


public class Travel{

    private int id;
    private boolean change;
    private String line_1;
    private String line_2;
    private String line_3;
    private int duration_1;
    private int duration_2;
    private int duration_3;
    private int duration_wait_1;
    private int duration_wait_2;
    private int score;
    private int departure;
    private int arrival;
    private String from;
    private String to;
    private boolean best;

    public Travel(int id, boolean change, String line_1, String line_2, String line_3,
                  int duration_1, int duration_2, int duration_3,
                  int duration_wait_1,int duration_wait_2,
                  int score,
                  int departure, int arrival,
                  String from, String to) {
        this.id = id;
        this.change = change;
        this.line_1 = line_1;
        this.line_2 = line_2;
        this.line_3 = line_3;
        this.duration_1 = duration_1;
        this.duration_2 = duration_2;
        this.duration_3 = duration_3;
        this.duration_wait_1 = duration_wait_1;
        this.duration_wait_2 = duration_wait_2;
        this.score = score;
        this.departure = departure;
        this.arrival = arrival;
        this.from = from;
        this.to = to;
        this.best = false;
    }

    public String getLine_1() {
        return line_1;
    }

    public void setLine_1(String line_1) {
        this.line_1 = line_1;
    }

    public String getLine_2() {
        return line_2;
    }

    public void setLine_2(String line_2) {
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

    public int getDuration_wait_1() {
        return duration_wait_1;
    }

    public void setDuration_wait_1(int duration_wait) {
        this.duration_wait_1 = duration_wait_1;
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

    public boolean isChange() {
        return change;
    }

    public void setChange(boolean change) {
        this.change = change;
    }

    public String getLine_3() {
        return line_3;
    }

    public void setLine_3(String line_3) {
        this.line_3 = line_3;
    }

    public int getDuration_3() {
        return duration_3;
    }

    public void setDuration_3(int duration_3) {
        this.duration_3 = duration_3;
    }

    public int getDuration_wait_2() {
        return duration_wait_2;
    }

    public void setDuration_wait_2(int duration_wait_2) {
        this.duration_wait_2 = duration_wait_2;
    }

    public boolean isBest() {
        return best;
    }
}

