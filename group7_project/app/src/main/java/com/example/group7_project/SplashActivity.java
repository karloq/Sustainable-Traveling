package com.example.group7_project;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    /** Duration of wait in seconds/10 **/
    private final int SPLASH_DISPLAY_LENGTH = 5;

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String LEAF_COUNTER = "leafCounter";
    ImageView splashTree;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.splash_screen);

        splashTree = findViewById(R.id.splashScreenTree);
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        int nbTrees = sharedPreferences.getInt(LEAF_COUNTER,0);
        setSplashTree(nbTrees);

        /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                Intent mainIntent = new Intent(SplashActivity.this, MainActivity.class);
                SplashActivity.this.startActivity(mainIntent);
                SplashActivity.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH * 100);
    }

    public void setSplashTree(int nbLeafs) {
        if (nbLeafs < 3) {
            splashTree.setImageResource(R.drawable.tree_1);
        } else if (nbLeafs < 6) {
            splashTree.setImageResource(R.drawable.tree_2);
        } else if (nbLeafs < 10) {
            splashTree.setImageResource(R.drawable.tree_3);
        } else if (nbLeafs < 15) {
            splashTree.setImageResource(R.drawable.tree_4);
        } else if (nbLeafs < 21) {
            splashTree.setImageResource(R.drawable.tree_5);
        } else if (nbLeafs < 31) {
            splashTree.setImageResource(R.drawable.tree_6);
        } else {
            splashTree.setImageResource(R.drawable.tree_7);
        }
    }
}
