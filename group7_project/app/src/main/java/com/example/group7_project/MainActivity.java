package com.example.group7_project;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
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
import com.example.group7_project.model_stop.Stop;
import com.example.group7_project.model_trip.TripPlan;
import com.example.group7_project.model_trip.trip.Trip;
import com.example.group7_project.model_trip.trip.leg_list.leg.Destination;
import com.example.group7_project.model_trip.trip.leg_list.leg.Leg;
import com.example.group7_project.model_trip.trip.leg_list.leg.Origin;
import com.example.group7_project.model_trip.trip.leg_list.leg.Product;
import com.google.android.material.navigation.NavigationView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.*;

public class MainActivity extends AppCompatActivity {
    //Debug messages
    private static final String TAG = "MainActivity";
    //Point system
    private static Context context;
    public static final int ADD_FILTER_REQUEST = 1;
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
    public static final String FILTER_ON = "filterOn";
    public static final String EXTRA_MAIN_SUSFILTER =
            "com.example.group7_project.EXTRA_SUSFILTER";
    //Drawer
    private DrawerLayout drawer;
    boolean isDrawerOpen = false;
    //Content
    private ArrayList<Travel> mTravelList_filtered;
    private ArrayList<Travel> mTravelList_full;
    ArrayList<Travel> temp = new ArrayList<>();
    private static final String[] STOPS = new String[]{
            "Chalmers", "Brunnsparken",
            "Lindholmspiren", "Lindholmsallén",
            "Järntorget", "Eriksbergstorget",
            "Centralstationen", "Stenpiren"
    };
    private static ArrayList<String> electricTravel = new ArrayList<>(Arrays.asList(
            "55", "58", "59", "60", "62", "82", "83", "84", "86",         //Buss-linjer (start på 58)
            "90", "91", "92", "93", "94", "95", "97", "99",
            "114", "184", "185", "193", "194", "196", "197",
            "501", "502", "510", "513", "514", "515", "517",
            "518", "519", "751", "753", "755", "757", "758",
            "281", "282", "283", "284", "285", "285ÄLV", "286",     //Båt-linjer (start på 281)
            "286ÄLV", "296", "298", "299", "322", "326", "361",
            "362", "381", "847", "879", "899"
    ));
    //RecyclerView
    private RecyclerView mRecyclerView;
    private TravelAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    //UI
    private AutoCompleteTextView edittext_from;
    private AutoCompleteTextView edittext_to;
    private ImageButton button_filter;
    //Data
    GlobalSustainabilityData userData;
    //API
    private static final String BASE_URL_STOP = "https://api.resrobot.se/v2/";
    private static final String BASE_URL_TRIP = "https://api.resrobot.se/v2/";

    //========================= App methods ==========================================================//
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        userData = (GlobalSustainabilityData) getApplicationContext();

        MainActivity.context = getApplicationContext();

        edittext_from = findViewById(R.id.edittext_search_from);
        edittext_to = findViewById(R.id.edittext_search_to);

        String[] stops = STOPS;
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, STOPS);
        edittext_to.setAdapter(adapter);
        edittext_from.setAdapter(adapter);

        edittext_to.setOnKeyListener(new View.OnKeyListener() {

            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN)
                        && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    updateFilterInit();
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
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                        switch (item.getItemId()) {
                            case R.id.nav_sus:
                                Intent intent = new Intent(MainActivity.this,
                                        SustainabilityPageActivity.class);
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
            userData.setSustainabilityFilter(data.getBooleanExtra
                    (FilterActivity.EXTRA_SUSFILTER, false));

            Toast.makeText(this, "Filter added", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Filter not added", Toast.LENGTH_SHORT).show();
        }
        saveData();
    }
//===============================================================================================//

//========================= Recyclerview and content methods ====================================//

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
                boolean best = mAdapter.getTravel(position).getBest();
                if (best) {
                    userData.setLeafCounter(userData.getLeafCounter() + 1);
                    saveData();
                    Toast.makeText(MainActivity.this,
                            "Saved points",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this,
                            "Not the most sustainable option",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void createTravelList(String from, String to, ArrayList list) throws IOException {
        mTravelList_full = new ArrayList<>(list);
        mTravelList_filtered = new ArrayList<>();
    }

    public void updateFilterInit() {
        String to = edittext_to.getText().toString();
        String from = edittext_from.getText().toString();
        fetchStopId_1(from, to);
    }

    public void updateFilter(String from, String to) throws IOException {
        //TODO: Order according to time and sustainability
        if (mTravelList_filtered != null) {
            mTravelList_filtered.clear();
        }
        int maxscore = 0;
        Stack sus = new Stack();

        //TODO
        for (Travel travel : mTravelList_full) {
            String travelFrom = travel.getFrom();
            String travelTo = travel.getTo();
            if (travelFrom.contains(from) && travelTo.contains(to)) {
                if (userData.isSustainabilityFilter() && travel.getScore() > 0) {
                    mTravelList_filtered.add(travel);
                }
                if (!userData.isSustainabilityFilter()) {
                    mTravelList_filtered.add(travel);
                }
            }
        }
        fix_list();
        mTravelList_filtered = new ArrayList<>(mTravelList_full);
        mAdapter.notifyDataSetChanged();
    }

    /**
     * Sorts mTravelList_filtered after score of each travel
     */
    private void fix_list() {
        for (Travel t : mTravelList_filtered) {
            if (electricTravel.contains(t.getLine_1()) || electricTravel.contains(t.getLine_2()) || electricTravel.contains(t.getLine_3())) {
                t.setBest(true);
                t.setScore(t.getDeparture() - 5);
            } else {
                t.setScore(t.getDeparture());
            }
        }
        Collections.sort(mTravelList_filtered, Comparator.comparing(Travel::getScore));
    }
//===============================================================================================//

//=========================Data methods==========================================================//

    private void saveData() {
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
        editor.putBoolean(FILTER_ON, userData.isSustainabilityFilter());
        editor.apply();

        Toast.makeText(this, "Data saved", Toast.LENGTH_SHORT).show();

    }

    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        userData.setLeafCounter(sharedPreferences.getInt(LEAF_COUNTER, 0));
        userData.setGreenTreeCounter(sharedPreferences.getInt(GREEN_TREE_COUNTER, 0));
        userData.setGoldTreeCounter(sharedPreferences.getInt(GOLD_TREE_COUNTER, 0));
        userData.setRank(sharedPreferences.getInt(RANK, 1));
        userData.setCurrent_month(sharedPreferences.getInt(CURRENT_MOTNH, 0));
        userData.setLeafs_oct(sharedPreferences.getInt(LEAFS_OCT, 0));
        userData.setLeafs_nov(sharedPreferences.getInt(LEAFS_NOV, 0));
        userData.setLeafs_dec(sharedPreferences.getInt(LEAFS_DEC, 0));
        userData.setOctGreen(sharedPreferences.getBoolean(OCT_GREEN_CHECK, false));
        userData.setNovGreen(sharedPreferences.getBoolean(NOV_GREEN_CHECK, false));
        userData.setDecGreen(sharedPreferences.getBoolean(DEC_GREEN_CHECK, false));
        userData.setOctGold(sharedPreferences.getBoolean(OCT_GOLD_CHECK, false));
        userData.setNovGold(sharedPreferences.getBoolean(NOV_GOLD_CHECK, false));
        userData.setDecGold(sharedPreferences.getBoolean(DEC_GOLD_CHECK, false));
        userData.setSustainabilityFilter(sharedPreferences.getBoolean(FILTER_ON, false));
    }
//===============================================================================================//


    //=======================API methods ============================================================//
    public void fetchStopId_1(final String from, final String to) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL_STOP)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final int[] stopId = {0};
        StopAPI stopAPI = retrofit.create(StopAPI.class);
        Call<Stop> call = stopAPI.getStop(from + "(goteborg kn)", "json");
        call.enqueue(new Callback<Stop>() {
            @Override
            public void onResponse(Call<Stop> call, Response<Stop> response) {
                stopId[0] = response.body().getStoplocation().get(0).getId();
                fetchStopId_2(from, to, stopId[0]);

            }

            @Override
            public void onFailure(Call<Stop> call, Throwable t) {
                Log.e(TAG, "onFailure: Something went wrong: " + t.getMessage());
            }
        });
    }

    public void fetchStopId_2(final String from, final String to, final int from_id) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL_STOP)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final int[] stopId = {0};
        StopAPI stopAPI = retrofit.create(StopAPI.class);
        Call<Stop> call = stopAPI.getStop(to + "(goteborg kn)", "json");
        call.enqueue(new Callback<Stop>() {
            @Override
            public void onResponse(Call<Stop> call, Response<Stop> response) {
                stopId[0] = response.body().getStoplocation().get(0).getId();

                fillTravelList(from, to, from_id, stopId[0]);

            }

            @Override
            public void onFailure(Call<Stop> call, Throwable t) {
                Log.e(TAG, "onFailure: Something went wrong: " + t.getMessage());
            }
        });
    }

    public ArrayList<Travel> fillTravelList(final String from_name, final String to_name, int from_id, int to_id) {
        final ArrayList<Travel> temp = new ArrayList<>();


        String from = from_name;
        String to = to_name;
        boolean best = false;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL_TRIP)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        TripAPI tripAPI = retrofit.create(TripAPI.class);
        Call<TripPlan> call = tripAPI.getTrips(from_id, to_id, "json");
        call.enqueue(new Callback<TripPlan>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<TripPlan> call, Response<TripPlan> response) {
                Log.d(TAG, "successful trip-fetch");


                ArrayList<Trip> trip_array = response.body().getTrips();
                workWithTripList(trip_array, from_name, to_name);
            }

            @Override
            public void onFailure(Call<TripPlan> call, Throwable t) {
                Log.e(TAG, "onFailure: Something went wrong: " + t.getMessage());
            }
        });
        return temp;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void workWithTripList(ArrayList<Trip> trip_array, String from_name, String to_name) {
        Trip trip;
        ArrayList<Leg> legs;
        Leg leg_1;
        Leg leg_2;
        Leg leg_3;
        Origin origin_1;
        Origin origin_2;
        Origin origin_3;
        Destination destination_1;
        Destination destination_2;
        Destination destination_3;
        Product product_1;
        Product product_2;
        Product product_3;
        int id = 0;
        boolean change;
        String line_1 = "";
        String line_2 = "";
        String line_3 = "";
        int duration_1 = 0;
        int duration_2 = 0;
        int duration_3 = 0;
        int duration_wait_1 = 0;
        int duration_wait_2 = 0;
        int score = 0;
        int departure;
        int arrival = 0;
        boolean walk_1;
        boolean walk_2;


        for (int i = 0; i < trip_array.size(); i++) {
            trip = trip_array.get(i);
            legs = trip.getLeglist().getLegs();
            leg_1 = legs.get(0);
            origin_1 = leg_1.getOrigin();
            try {
                product_1 = leg_1.getProduct();
                line_1 = product_1.getNum();
            } catch (NullPointerException e) {
                line_1 = "WALK";
            }

            destination_1 = leg_1.getDestination();
            departure = timeToInt(origin_1.getTime());
            arrival = timeToInt(destination_1.getTime());
            duration_1 = arrival - departure;
            switch (legs.size()) {
                case 1:
                    duration_2 = 0;
                    duration_3 = 0;
                    duration_wait_1 = 0;
                    duration_wait_2 = 0;
                    change = false;
                    break;

                case 2:
                    leg_2 = legs.get(1);
                    destination_2 = leg_2.getDestination();
                    origin_2 = leg_2.getOrigin();
                    int departure_2 = timeToInt(origin_2.getTime());
                    duration_wait_1 = departure_2 - arrival;
                    arrival = timeToInt(destination_2.getTime());
                    duration_2 = arrival - departure_2;
                    try {
                        product_2 = leg_2.getProduct();
                        line_2 = product_2.getNum();
                    } catch (NullPointerException e) {
                        line_2 = "WALK";
                    }
                    change = true;
                    break;
                case 3:
                    leg_2 = legs.get(1);
                    destination_2 = leg_2.getDestination();
                    origin_2 = leg_2.getOrigin();
                    departure_2 = timeToInt(origin_2.getTime());
                    duration_wait_1 = departure_2 - arrival;
                    arrival = timeToInt(destination_2.getTime());
                    duration_2 = arrival - departure_2;
                    try {
                        product_2 = leg_2.getProduct();
                        line_2 = product_2.getNum();
                    } catch (NullPointerException e) {
                        line_2 = "WALK";
                    }
                    leg_3 = legs.get(2);
                    destination_3 = leg_3.getDestination();
                    origin_3 = leg_3.getOrigin();
                    int departure_3 = timeToInt(origin_3.getTime());
                    duration_wait_2 = departure_3 - arrival;
                    arrival = timeToInt(destination_3.getTime());
                    duration_2 = arrival - departure_3;
                    try {
                        product_3 = leg_3.getProduct();
                        line_3 = product_3.getNum();
                    } catch (NullPointerException e) {
                        line_3 = "WALK";
                    }
                    change = true;
                    break;
                default:
                    continue;
            }
            temp.add(new Travel(id, change, line_1, line_2, line_3,
                    duration_1, duration_2, duration_3, duration_wait_1, duration_wait_2, score, departure, arrival,
                    from_name, to_name));

        }
        try {
            createTravelList(from_name, to_name, temp);
        } catch (IOException e) {
            e.printStackTrace();
        }
        buildRecyclerView();
        try {
            updateFilter(from_name, to_name);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
//===============================================================================================//

    //================= Helper methods ==============================================================//
    public int timeToInt(String time) {
        StringBuilder sb = new StringBuilder(time);
        return Integer.parseInt(sb.substring(0, 2)) * 60 + Integer.parseInt(sb.substring(3, 5));
    }

    public static Context getAppContext() {
        return MainActivity.context;
    }

//===============================================================================================//
}
