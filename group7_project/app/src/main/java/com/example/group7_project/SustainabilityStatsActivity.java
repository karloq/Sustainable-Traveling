package com.example.group7_project;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SustainabilityStatsActivity extends AppCompatActivity {

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String LEAF_COUNTER = "leafCounter";
    public static final String GREEN_TREE_COUNTER = "greenTreeCounter";
    public static final String GOLD_TREE_COUNTER = "goldenTreeCounter";
    public static final String RANK = "rank";
    public static final String CURRENT_MOTNH = "current_month";
    public static final String LEAFS_OCT = "leafs_oct";
    public static final String LEAFS_NOV = "leafs_nov";
    public static final String LEAFS_DEC = "leafs_dec";
    public static final String OCT_GREEN_CHECK = "octGreen";
    public static final String NOV_GREEN_CHECK = "novGreen";
    public static final String DEC_GREEN_CHECK = "decGreen";
    public static final String OCT_GOLD_CHECK = "octGold";
    public static final String NOV_GOLD_CHECK = "novGold";
    public static final String DEC_GOLD_CHECK = "decGold";

    ProgressBar oct, nov, dec;
    TextView leafs_oct, leafs_nov, leafs_dec, leafs_total, green_total, gold_total, current_month;
    ImageButton button_back;
    Button next_month, clear_data;

    // To be able to manage our global variables
    GlobalSustainabilityData userData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sus_stats);

        oct = findViewById(R.id.pb_oct);
        nov = findViewById(R.id.pb_nov);
        dec = findViewById(R.id.pb_dec);
        leafs_oct = findViewById(R.id.pb_leafs_oct);
        leafs_nov = findViewById(R.id.pb_leafs_nov);
        leafs_dec = findViewById(R.id.pb_leafs_dec);
        leafs_total = findViewById(R.id.total_leafs);
        green_total = findViewById(R.id.total_green_trees);
        gold_total = findViewById(R.id.total_gold_trees);
        next_month = findViewById(R.id.button);
        current_month = findViewById(R.id.month);
        button_back = findViewById(R.id.button_stats_back);
        clear_data = findViewById(R.id.clear_data);

        // To be able to manage our global variables
        userData = (GlobalSustainabilityData) this.getApplicationContext();

        oct.setProgress(userData.getLeafs_oct());
        nov.setProgress(userData.getLeafs_nov());
        dec.setProgress(userData.getLeafs_dec());

        leafs_oct.setText(String.valueOf(userData.getLeafs_oct()));
        leafs_nov.setText(String.valueOf(userData.getLeafs_nov()));
        leafs_dec.setText(String.valueOf(userData.getLeafs_dec()));

        int nb_leafs = 188 + userData.getLeafs_oct() +
               userData.getLeafs_nov() + userData.getLeafs_dec();
        int nb_green_trees = 5 + getGreenTrees();
        int nb_gold_trees = 2 + getGoldTrees();

        leafs_total.setText(String.valueOf(nb_leafs));
        green_total.setText(String.valueOf(nb_green_trees));
        gold_total.setText(String.valueOf(nb_gold_trees));

        setTextCurrentMonth();

        // New month button
        next_month.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (userData.getCurrent_month()){
                    case 0: userData.setCurrent_month(1); break;
                    case 1: userData.setCurrent_month(2); break;
                    case 2: userData.setCurrent_month(0); break;
                }
                saveLeafsAndTrees();
                setTextCurrentMonth();
            }
        });

        // Back button handler
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Clear data handler
        clear_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userData.resetValues();
                finish();
                startActivity(getIntent());
                saveLeafsAndTrees();
            }
        });
    }

    public void setTextCurrentMonth(){
        switch (userData.getCurrent_month()){
            case 0: current_month.setText(String.valueOf("Current month: October")); break;
            case 1: current_month.setText(String.valueOf("Current month: November")); break;
            case 2: current_month.setText(String.valueOf("Current month: December")); break;
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

    public void saveLeafsAndTrees(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt(LEAF_COUNTER, userData.getLeafCounter());
        editor.putInt(GREEN_TREE_COUNTER, userData.getGreenTreeCounter());
        editor.putInt(GOLD_TREE_COUNTER, userData.getGoldTreeCounter());
        editor.putInt(RANK, userData.getRank());
        editor.putInt(CURRENT_MOTNH, userData.getCurrent_month());
        editor.putInt(LEAFS_OCT, userData.getLeafs_oct());
        editor.putInt(LEAFS_NOV, userData.getLeafs_nov());
        editor.putInt(LEAFS_DEC, userData.getLeafs_dec());
        editor.putBoolean(OCT_GREEN_CHECK, userData.isOctGreen());
        editor.putBoolean(NOV_GREEN_CHECK, userData.isNovGreen());
        editor.putBoolean(DEC_GREEN_CHECK, userData.isDecGreen());
        editor.putBoolean(OCT_GOLD_CHECK, userData.isOctGold());
        editor.putBoolean(NOV_GOLD_CHECK, userData.isNovGold());
        editor.putBoolean(DEC_GOLD_CHECK, userData.isDecGold());
        editor.apply();

    }
}

