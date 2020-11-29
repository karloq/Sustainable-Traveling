package com.example.group7_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SustainabilityPageActivity extends AppCompatActivity {
    public ImageView picTree, picRanking;
    TextView rankText, greenTrees, goldTrees, leafs, leafsRemaining;
    ImageButton button_back;
    Button statistics_button;
    int toNextLevel;

    // Levels when a tree gets leveled-up
    private int tree_level_1 = 3;
    private int tree_level_2 = 6;
    private int tree_level_3 = 10;
    private int tree_level_4 = 15;
    private int tree_level_5 = 21;
    private int tree_level_6 = 31;


    // To be able to manage our global variables
    GlobalSustainabilityData userData;

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
        statistics_button = findViewById(R.id.stats);

        // To be able to manage our global variables
        userData = (GlobalSustainabilityData) this.getApplicationContext();

        // Set rank level, tree level, leaf counter, green tree counter, gold tree counter & leafs remaining
        update();

        // Once the tree is clicked, add 1 leaf
        picTree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userData.setLeafCounter(userData.getLeafCounter()+1);
                update();
            }
        });

        // Once the bar is clicked, update rank
        picRanking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userData.getRank() == 5){
                    userData.setRank(1);
                } else {
                    userData.setRank(userData.getRank() + 1);
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

        // Once "Statistics" is clicked, open stats
        statistics_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SustainabilityPageActivity.this, SustainabilityStatsActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onRestart(){
        super.onRestart();
        finish();
        startActivity(getIntent());}

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

        if(userData.getLeafCounter() == 21){
            userData.setGreenTreeCounter(userData.getGreenTreeCounter()+1);
        } else if(userData.getLeafCounter() == 31){
            userData.setGoldTreeCounter(userData.getGoldTreeCounter()+1);
        }

        setTreeLevel(userData.getLeafCounter());
        greenTrees.setText(String.valueOf(getGreenTrees()));
        goldTrees.setText(String.valueOf(getGoldTrees()));
        leafs.setText(String.valueOf(userData.getLeafCounter()));

        String string = "För att nå nästa nivå behöver du samla " + toNextLevel + " miljöresor ";
        leafsRemaining.setText(string);

        setRankLevel(userData.getRank());
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

    public int getGreenTrees(){
        int temp = 0;
        if(userData.getLeafs_oct() > 20){
            temp++;
        } if(userData.getLeafs_nov() > 20){
            temp++;
        } if(userData.getLeafs_dec() > 20){
            temp++;
        }
        return temp;
    }

    public int getGoldTrees(){
        int temp = 0;
        if(userData.getLeafs_oct() > 30){
            temp++;
        } if(userData.getLeafs_nov() > 30){
            temp++;
        } if(userData.getLeafs_dec() > 30){
            temp++;
        }
        return temp;
    }

    public int getTree_level_1() {return tree_level_1;}
    public int getTree_level_2() {return tree_level_2;}
    public int getTree_level_3() {return tree_level_3;}
    public int getTree_level_4() {return tree_level_4;}
    public int getTree_level_5() {return tree_level_5;}
    public int getTree_level_6() {return tree_level_6;}

}