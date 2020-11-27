package com.example.group7_project;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.Stack;

public class MainActivity extends AppCompatActivity {
    private static Context context;
    public static final int ADD_FILTER_REQUEST = 1;
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String LEAF_COUNTER = "leafCounter";
    public static final String GREEN_TREE_COUNTER = "greenTreeCounter";
    public static final String GOLD_TREE_COUNTER = "goldenTreeCounter";
    public static final String RANK = "rank";
    public static final String FILTER_ON = "filterOn";
    public static final String EXTRA_MAIN_SUSFILTER =
            "com.example.group7_project.EXTRA_SUSFILTER";

    private DrawerLayout drawer;
    boolean isDrawerOpen = false;

    private ArrayList<Travel> mTravelList_filtered;
    private ArrayList<Travel> mTravelList_full;

    private RecyclerView mRecyclerView;
    private TravelAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private AutoCompleteTextView edittext_from;
    private AutoCompleteTextView edittext_to;

    private ImageButton button_filter;

    GlobalSustainabilityData userData;

    private static final String[] STOPS = new String[]{
            "Chalmers", "Brunnsparken",
            "Lindholmspiren", "Lindholmsallén",
            "Järntorget", "Eriksbergstorget",
            "Centralstationen"
    };

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //TODO: Make splashscreen
        //TODO:
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userData = (GlobalSustainabilityData) getApplicationContext();

        MainActivity.context = getApplicationContext();

        edittext_from = findViewById(R.id.edittext_search_from);
        edittext_to = findViewById(R.id.edittext_search_to);

        String[] stops = STOPS;
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, STOPS);
        edittext_to.setAdapter(adapter);
        edittext_from.setAdapter(adapter);

        createTravelList();
        buildRecyclerView();

        edittext_to.setOnKeyListener(new View.OnKeyListener() {

            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    updateFilter();
                    return true;
                }
                return false;
            }
        });

        drawer = findViewById(R.id.drawer_layout);

        ImageButton open_drawer = findViewById(R.id.button_search_menu);
        open_drawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer.openDrawer(GravityCompat.START);
                isDrawerOpen = true;
            }
        });
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.nav_sus:
                        Intent intent = new Intent(MainActivity.this, SustainabilityPageActivity.class);
                        startActivity(intent);
                        break;
                    default:
                        Toast toast = Toast.makeText(getApplicationContext(),
                                "You have clicked, but it's not implemented. Yet...",
                                Toast.LENGTH_SHORT);
                        toast.show();
                }
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        button_filter = findViewById(R.id.button_search_filter);
        button_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, FilterActivity.class);
                intent.putExtra(EXTRA_MAIN_SUSFILTER, userData.isSustainabilityFilter());
                startActivityForResult(intent, ADD_FILTER_REQUEST);
            }
        });

        loadData();

    }

    public void updateFilter() {
        //TODO: Order according to time and sustainability
        mTravelList_filtered.clear();

        int maxscore = 0;
        Stack sus = new Stack();

        String to = edittext_to.getText().toString().toLowerCase();
        String from = edittext_from.getText().toString().toLowerCase();

        for (Travel travel : mTravelList_full) {
            String travelFrom = travel.getFrom().toLowerCase();
            String travelTo = travel.getTo().toLowerCase();
            if (travelFrom.contains(from) && travelTo.contains(to)) {
                if (userData.isSustainabilityFilter() && travel.getScore() > 0) {
                    mTravelList_filtered.add(travel);
                }
                if (!userData.isSustainabilityFilter()) {
                    mTravelList_filtered.add(travel);
                }
                if (travel.getScore() > maxscore) {
                    maxscore = travel.getScore();
                    sus.push(travel);
                }
            }
        }
        try {
            Travel susTravel = (Travel) sus.pop();
            susTravel.setBest(true);
        } catch (EmptyStackException e) {
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
        if (isDrawerOpen) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_FILTER_REQUEST && resultCode == RESULT_OK) {
            assert data != null;
            userData.setSustainabilityFilter(data.getBooleanExtra(FilterActivity.EXTRA_SUSFILTER, false));

            Toast.makeText(this, "Filter added", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Filter not added", Toast.LENGTH_SHORT).show();
        }
        saveData();
    }

    private void buildRecyclerView() {
        mRecyclerView = findViewById(R.id.RecyclerView_main);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new TravelAdapter(mTravelList_filtered);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        //TODO: Swipe to track travel
        //TODO: Pop up animation when growing tree
        mAdapter.setOnItemClickListener(new TravelAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Toast toast = Toast.makeText(getApplicationContext(),
                        "You have clicked, but it's not implemented. Yet...",
                        Toast.LENGTH_SHORT);
                toast.show();
            }

            @Override
            public void onLongItemClick(int position) {
                int score = mAdapter.getTravel(position).getScore();
                if(score > 0){
                    userData.setLeafCounter(userData.getLeafCounter()+score);
                    Toast.makeText(MainActivity.this, "Saved points", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Not the most sustainable option", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createTravelList() {
        //TODO: Add more travels with offsetted time
        //TODO: Add rest of travels
        final LocalTime now = LocalTime.now();
        int time = (now.getHour()*60) + now.getMinute();
        mTravelList_full = new ArrayList<>();
        mTravelList_filtered = new ArrayList<>();
        mTravelList_full.add(new Travel(1, false, 60, 0, 9, 0, 0, 1,
                time+3, time+12,
                "Järntorget", "Centralstationen"));
        mTravelList_full.add(new Travel(2, false, 3, 0, 14, 0, 0, 0,
                time+2, time+16,
                "Järntorget", "Centralstationen"));
        mTravelList_full.add(new Travel(3, true, 241, 1337, 7, 5, 0, 0,
                time+5, time+17,
                "Järntorget", "Centralstationen"));
        mTravelList_full.add(new Travel(4, true, 50, 1337, 7, 7, 0, 0,
                time+10, time+24,
                "Järntorget", "Centralstationen"));
    }

    private void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt(LEAF_COUNTER, userData.getLeafCounter());
        editor.putInt(GREEN_TREE_COUNTER, userData.getGreenTreeCounter());
        editor.putInt(GOLD_TREE_COUNTER, userData.getGoldTreeCounter());
        editor.putInt(RANK, userData.getRank());
        editor.putBoolean(FILTER_ON, userData.isSustainabilityFilter());

        editor.apply();

        Toast.makeText(this, "Data saved", Toast.LENGTH_SHORT).show();

    }

    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        userData.setLeafCounter(sharedPreferences.getInt(LEAF_COUNTER,0));
        userData.setGreenTreeCounter(sharedPreferences.getInt(GREEN_TREE_COUNTER,0));
        userData.setGoldTreeCounter(sharedPreferences.getInt(GOLD_TREE_COUNTER,0));
        userData.setRank(sharedPreferences.getInt(RANK,1));
        userData.setSustainabilityFilter(sharedPreferences.getBoolean(FILTER_ON, false));
    }

    public static Context getAppContext() {
        return MainActivity.context;
    }
}
