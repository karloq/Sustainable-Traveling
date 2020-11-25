package com.example.group7_project;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SustainabilityPageActivity extends AppCompatActivity {
    ImageView picTree, picRanking;
    TextView rankText, greenTrees, goldTrees, leafs, leafsRemaining;
    int toNextLevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sustainability_page);

        // Connects the variables in the XML-file to this  class
        picTree = (ImageView) findViewById(R.id.treeLevel);
        picRanking = (ImageView) findViewById(R.id.picTravelRanking);
        rankText = (TextView) findViewById(R.id.text_scoreboard);
        greenTrees = (TextView) findViewById(R.id.nbGreenTrees);
        goldTrees = (TextView) findViewById(R.id.nbGoldenTrees);
        leafs = (TextView) findViewById(R.id.nbLeafs);
        leafsRemaining = (TextView) findViewById(R.id.levelDescription);

        // To be able to manage our global variables
        GlobalSustainabilityData globalSustainabilityData = (GlobalSustainabilityData) getApplicationContext();

        // Set rank level, tree level, leaf counter, green tree counter, gold tree counter & leafs remaining
        setRankLevel(globalSustainabilityData.getRank());
        setTreeLevel(globalSustainabilityData.getLeafCounter());
        greenTrees.setText(String.valueOf(globalSustainabilityData.getGreenTreeCounter()));
        goldTrees.setText(String.valueOf(globalSustainabilityData.getGoldTreeCounter()));
        leafs.setText(String.valueOf(globalSustainabilityData.getLeafCounter()));
        leafsRemaining.setText("För att nå nästa nivå behöver du samla " + String.valueOf(toNextLevel) + " miljöresor");

    }

    // Decides which tree level to show on the sustainability page
    // Needs to manually update the levels in case of new playing rules
    public void setTreeLevel(int nbLeafs){
        if(nbLeafs < 3){
            picTree.setImageResource(R.drawable.tree_1);
            toNextLevel = 3 - nbLeafs;
        } else if(nbLeafs < 6){
            picTree.setImageResource(R.drawable.tree_2);
            toNextLevel = 6 - nbLeafs;
        } else if(nbLeafs < 10){
            picTree.setImageResource(R.drawable.tree_3);
            toNextLevel = 10 - nbLeafs;
        } else if(nbLeafs < 15){
            picTree.setImageResource(R.drawable.tree_4);
            toNextLevel = 15 - nbLeafs;
        } else if(nbLeafs < 21){
            picTree.setImageResource(R.drawable.tree_5);
            toNextLevel = 21 - nbLeafs;
        } else if(nbLeafs < 31){
            picTree.setImageResource(R.drawable.tree_6);
            toNextLevel = 31 - nbLeafs;
        } else {
            picTree.setImageResource(R.drawable.tree_7);
            toNextLevel = 0;
        }
    }

    // Calculates the users rank in comparison to other users
    public void setRankLevel(int rankLevel){

        //TODO: Bestämma en hur vi ska räkna ut detta. Behövs det?

        switch(rankLevel){
            case 1: picRanking.setImageResource(R.drawable.toptravelers20); rankText.setText(R.string.sustainability_relative_20); break;
            case 2: picRanking.setImageResource(R.drawable.toptravelers40); rankText.setText(R.string.sustainability_relative_40); break;
            case 3: picRanking.setImageResource(R.drawable.toptravelers60); rankText.setText(R.string.sustainability_relative_60); break;
            case 4: picRanking.setImageResource(R.drawable.toptravelers80); rankText.setText(R.string.sustainability_relative_80); break;
            case 5: picRanking.setImageResource(R.drawable.toptravelers99); rankText.setText(R.string.sustainability_relative_99); break;
        }
    }
}