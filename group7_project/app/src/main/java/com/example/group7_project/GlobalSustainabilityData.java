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

    // Number of total collected green trees
    private int greenTreeCounter = 0;
    // Number of total collected golden trees
    private int goldTreeCounter = 0;
    // Rank of the users performance versus other users, 1 to 5
    private int rank = 1;
    //Filter settings for sustainability trips
    private boolean sustainabilityFilter = false;
    // 0 = October, 1 = November, 2 = December
    private int current_month = 0;
    //
    private boolean octGreen = false, novGreen = false, decGreen = false, octGold = false,
            novGold = false, decGold = false;

    private int leafs_oct = 0;
    private int leafs_nov = 0;
    private int leafs_dec = 0;

    /* RESETS ALL VALUES */
    public void resetValues(){
        greenTreeCounter = goldTreeCounter = current_month = leafs_oct = leafs_nov = leafs_dec = 0;
        rank = 1;
        octGreen = novGreen = decGreen = octGold = novGold = decGold = false;
    }

    /* SETTERS AND GETTERS */
    public int getLeafCounter() {
        switch (getCurrent_month()){
            case 0: return getLeafs_oct();
            case 1: return getLeafs_nov();
            case 2: return getLeafs_dec();
            default:
                return 99;
        }
    }

    public void setLeafCounter(int leafCounter) {
        switch (getCurrent_month()){
            case 0: setLeafs_oct(leafCounter); break;
            case 1: setLeafs_nov(leafCounter); break;
            case 2: setLeafs_dec(leafCounter); break;
        }
    }

    public void setGreenTreeCounter(int add) {
        switch (getCurrent_month()){
            case 0: if(!octGreen){octGreen = true; this.greenTreeCounter = add;} break;
            case 1: if(!novGreen){novGreen = true; this.greenTreeCounter = add;} break;
            case 2: if(!decGreen){decGreen = true; this.greenTreeCounter = add;} break;
        }
    }

    public int getGreenTreeCounter() {return greenTreeCounter;}

    public void setGoldTreeCounter(int add) {
        switch (getCurrent_month()){
            case 0: if(!octGold){octGold = true; this.goldTreeCounter = add;} break;
            case 1: if(!novGold){novGold = true; this.goldTreeCounter = add;} break;
            case 2: if(!decGold){decGold = true; this.goldTreeCounter = add;} break;
        }
    }

    public int getGoldTreeCounter() {return goldTreeCounter;}

    public int getRank() {return rank;}
    public void setRank(int rank) {this.rank = rank;}
    public boolean isSustainabilityFilter() {return sustainabilityFilter;}
    public void setSustainabilityFilter(boolean sustainabilityFilter) {this.sustainabilityFilter = sustainabilityFilter;}
    public int getCurrent_month() {return current_month;}
    public void setCurrent_month(int current_month) {this.current_month = current_month;}
    public int getLeafs_oct() {return leafs_oct;}
    public void setLeafs_oct(int leafs_oct) {this.leafs_oct = leafs_oct;}
    public int getLeafs_nov() {return leafs_nov;}
    public void setLeafs_nov(int leafs_nov) {this.leafs_nov = leafs_nov;}
    public int getLeafs_dec() {return leafs_dec;}
    public void setLeafs_dec(int leafs_dec) {this.leafs_dec = leafs_dec;}
    public boolean isOctGreen() {return octGreen;}
    public void setOctGreen(boolean octGreen) {this.octGreen = octGreen;}
    public boolean isNovGreen() {return novGreen;}
    public void setNovGreen(boolean novGreen) {this.novGreen = novGreen;}
    public boolean isDecGreen() {return decGreen;}
    public void setDecGreen(boolean decGreen) {this.decGreen = decGreen;}
    public boolean isOctGold() {return octGold;}
    public void setOctGold(boolean octGold) {this.octGold = octGold;}
    public boolean isNovGold() {return novGold;}
    public void setNovGold(boolean novGold) {this.novGold = novGold;}
    public boolean isDecGold() {return decGold;}
    public void setDecGold(boolean decGold) {this.decGold = decGold;}


}
