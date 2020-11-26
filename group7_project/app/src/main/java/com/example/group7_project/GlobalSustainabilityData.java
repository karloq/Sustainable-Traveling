package com.example.group7_project;

import android.app.Application;

/*
Global class to store relevant data for the sustainability page
Actions from MainActivity/other will influence the variables
Hardcoded numbers before everything is implemented

To implement:
GlobalSustainabilityData variableName = (GlobalSustainabilityData) getApplicationContext(); in java class
variableName.set/get to get/set the global variables

*/
public class GlobalSustainabilityData extends Application {

    // Number of leafs collected during the month
    private int leafCounter;
    // Number of total collected green trees
    private int greenTreeCounter;
    // Number of total collected golden trees
    private int goldTreeCounter;
    // Rank of the users performance versus other users, 1 to 5
    private int rank;
    //Filter settings for sustainability trips
    private boolean sustainabilityFilter;

    public GlobalSustainabilityData() {
        leafCounter = 0;
        greenTreeCounter = 0;
        goldTreeCounter = 0;
        rank = 1;
        sustainabilityFilter = false;
    }

    /* SETTERS AND GETTERS */
    public int getLeafCounter() {return leafCounter;}
    public void setLeafCounter(int leafCounter) {this.leafCounter = leafCounter;}
    public int getGreenTreeCounter() {return greenTreeCounter;}
    public void setGreenTreeCounter(int greenTreeCounter) {this.greenTreeCounter = greenTreeCounter;}
    public int getGoldTreeCounter() {return goldTreeCounter;}
    public void setGoldTreeCounter(int goldTreeCounter) {this.goldTreeCounter = goldTreeCounter;}
    public int getRank() {return rank;}
    public void setRank(int rank) {this.rank = rank;}
    public boolean isSustainabilityFilter() {return sustainabilityFilter;}
    public void setSustainabilityFilter(boolean sustainabilityFilter) {this.sustainabilityFilter = sustainabilityFilter;}
}
