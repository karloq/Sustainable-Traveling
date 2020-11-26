package com.example.group7_project;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class SustainabilityPageActivity extends AppCompatActivity {
    ImageView picTree, picRanking;
    TextView rankText, greenTrees, goldTrees, leafs, leafsRemaining;
    ImageButton button_back;
    int toNextLevel;

    // Levels when a tree gets leveled-up
    private int tree_level_1 = 3;
    private int tree_level_2 = 6;
    private int tree_level_3 = 10;
    private int tree_level_4 = 15;
    private int tree_level_5 = 21;
    private int tree_level_6 = 31;

    // To be able to manage our global variables
    GlobalSustainabilityData globalSustainabilityData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sustainability_page);

        // Connects the variables in the XML-file to this  class
        picTree = findViewById(R.id.treeLevel);
        picRanking = findViewById(R.id.picTravelRanking);
        rankText = findViewById(R.id.text_scoreboard);
        greenTrees = findViewById(R.id.nbGreenTrees);
        goldTrees = findViewById(R.id.nbGoldenTrees);
        leafs = findViewById(R.id.nbLeafs);
        leafsRemaining = findViewById(R.id.levelDescription);
        button_back = findViewById(R.id.button_sustainability_back);

        // To be able to manage our global variables
        globalSustainabilityData = (GlobalSustainabilityData) this.getApplicationContext();

        // Set rank level, tree level, leaf counter, green tree counter, gold tree counter & leafs remaining
        update();

        // Once the tree is clicked, add 1 leaf
        picTree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                globalSustainabilityData.setLeafCounter(globalSustainabilityData.getLeafCounter()+1);
                update();
            }
        });

        // Once the bar is clicked, update rank
        picRanking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(globalSustainabilityData.getRank() == 5){
                    globalSustainabilityData.setRank(1);
                } else {
                    globalSustainabilityData.setRank(globalSustainabilityData.getRank() + 1);
                }
                update();
            }
        });

        // Back button handler
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    // Decides which tree level to show on the sustainability page
    // Needs to manually update the levels in case of new playing rules
    public void setTreeLevel(int nbLeafs){
        if(nbLeafs < tree_level_1){
            picTree.setImageResource(R.drawable.tree_1);
            toNextLevel = tree_level_1 - nbLeafs;
        } else if(nbLeafs < tree_level_2){
            picTree.setImageResource(R.drawable.tree_2);
            toNextLevel = tree_level_2 - nbLeafs;
        } else if(nbLeafs < tree_level_3){
            picTree.setImageResource(R.drawable.tree_3);
            toNextLevel = tree_level_3 - nbLeafs;
        } else if(nbLeafs < tree_level_4){
            picTree.setImageResource(R.drawable.tree_4);
            toNextLevel = tree_level_4 - nbLeafs;
        } else if(nbLeafs < tree_level_5){
            picTree.setImageResource(R.drawable.tree_5);
            toNextLevel = tree_level_5 - nbLeafs;
        } else if(nbLeafs < tree_level_6){
            picTree.setImageResource(R.drawable.tree_6);
            toNextLevel = tree_level_6 - nbLeafs;
        } else {
            picTree.setImageResource(R.drawable.tree_7);
            toNextLevel = 0;
        }
    }

    public void update(){

        if(globalSustainabilityData.getLeafCounter() == 21){
            globalSustainabilityData.setGreenTreeCounter(globalSustainabilityData.getGreenTreeCounter()+1);
        } else if(globalSustainabilityData.getLeafCounter() == 31){
            globalSustainabilityData.setGoldTreeCounter(globalSustainabilityData.getGoldTreeCounter()+1);
        }

        setTreeLevel(globalSustainabilityData.getLeafCounter());
        greenTrees.setText(String.valueOf(globalSustainabilityData.getGreenTreeCounter()));
        goldTrees.setText(String.valueOf(globalSustainabilityData.getGoldTreeCounter()));
        leafs.setText(String.valueOf(globalSustainabilityData.getLeafCounter()));

        String string = "För att nå nästa nivå behöver du samla " + toNextLevel + " miljöresor ";
        leafsRemaining.setText(string);

        setRankLevel(globalSustainabilityData.getRank());
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