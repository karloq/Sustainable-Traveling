package com.example.group7_project;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SustainabilityStatsActivity extends AppCompatActivity {

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
}

