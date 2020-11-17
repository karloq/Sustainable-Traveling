package com.example.group7_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static final int ADD_FILTER_REQUEST = 1;

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

    private static final String[] STOPS = new String[]{
            "Chalmers", "Beväringsgatan"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edittext_from = findViewById(R.id.edittext_search_from);
        edittext_to = findViewById(R.id.edittext_search_to);

        String[] stops = STOPS;
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, STOPS);
        edittext_to.setAdapter(adapter);
        edittext_from.setAdapter(adapter);

        edittext_to.setOnKeyListener(new View.OnKeyListener(){

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

        createTravelList();
        buildRecyclerView();

        button_filter = findViewById(R.id.button_search_filter);
        button_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, FilterActivity.class);
                startActivityForResult(intent, ADD_FILTER_REQUEST);
            }
        });
    }

    private void createTravelList() {
        mTravelList_full = new ArrayList<>();
        mTravelList_filtered = new ArrayList<>();
        mTravelList_full.add(new Travel(1,6, 22, 0,
                "10:11 -", "10:33",
                "Chalmers", "Beväringsgatan"));
        mTravelList_full.add(new Travel(1,7, 20, 10,
                "10:13 -", "10:33",
                "Chalmers", "Beväringsgatan"));
    }

    private void buildRecyclerView() {
        mRecyclerView = findViewById(R.id.RecyclerView_main);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new TravelAdapter(mTravelList_filtered);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new TravelAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Toast toast = Toast. makeText(getApplicationContext(),
                        "You have clicked, but it's not implemented. Yet...",
                        Toast. LENGTH_SHORT);
                toast.show();
            }

            @Override
            public void onTrackClick(int position) {
                Toast toast = Toast. makeText(getApplicationContext(),
                        "You're trying to track, but it's not implemented. Yet...",
                        Toast. LENGTH_SHORT);
                toast.show();
            }
        });
    }

    public void updateFilter(){
        mTravelList_filtered.clear();

        String to = edittext_to.getText().toString().toLowerCase();
        String from = edittext_from.getText().toString().toLowerCase();

        for (Travel travel : mTravelList_full){
            String travelFrom = travel.getFrom().toLowerCase();
            String travelTo = travel.getTo().toLowerCase();
            if(travelFrom.contains(from) && travelTo.contains(to)){
                mTravelList_filtered.add(travel);
            }
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
        if(isDrawerOpen) {
            drawer.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == ADD_FILTER_REQUEST && resultCode == RESULT_OK){
            assert data != null;
            Toast.makeText(this, "Filter added", Toast.LENGTH_SHORT).show();
        } else{
            Toast.makeText(this, "Filter not added", Toast.LENGTH_SHORT).show();
        }
    }
}
