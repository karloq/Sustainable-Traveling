package com.example.group7_project;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.example.group7_project.model_stop.Stop;
import com.example.group7_project.model_trip.TripPlan;
import com.example.group7_project.model_trip.trip.Trip;
import com.example.group7_project.model_trip.trip.leg_list.leg.Destination;
import com.example.group7_project.model_trip.trip.leg_list.leg.Leg;
import com.example.group7_project.model_trip.trip.leg_list.leg.Origin;
import com.example.group7_project.model_trip.trip.leg_list.leg.Product;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TravelData {
    private static final String TAG = "TravelData";
    private static final String BASE_URL_STOP = "https://api.resrobot.se/v2/";
    private static final String BASE_URL_TRIP = "https://api.resrobot.se/v2/";
    public ArrayList travelList;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public TravelData(String from, String to) {
        //Trip trip = new Trip(from, to);
        //this.travelList = new ArrayList<Travel>(trip.getTravels());
        int from_id = fetchStopId(from);
        int to_id = fetchStopId(to);
        travelList = new ArrayList(fillTravelList(from, to));
    }

    public int fetchStopId(String stop){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL_STOP)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final int[] stopId = {0};
        StopAPI stopAPI = retrofit.create(StopAPI.class);
        Call<Stop> call = stopAPI.getStop();
        call.enqueue(new Callback<Stop>() {
            @Override
            public void onResponse(Call<Stop> call, Response<Stop> response) {
                Log.d(TAG, "successful stop-fetch");

                stopId[0] = response.body().getStoplocation().get(0).getId();
            }

            @Override
            public void onFailure(Call<Stop> call, Throwable t) {
                Log.e(TAG, "onFailure: Something went wrong: " + t.getMessage());
            }
        });
        return stopId[0];
    }

    public ArrayList fillTravelList(final String from_name, final String to_name){
        final ArrayList<Travel> temp = new ArrayList<>();


        String from = from_name;
        String to = to_name;
        boolean best = false;

        int from_id = fetchStopId(from_name);
        int to_id = fetchStopId(to_name);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL_TRIP)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        TripAPI tripAPI = retrofit.create(TripAPI.class);
        Call<TripPlan> call = tripAPI.getTrips();
        call.enqueue(new Callback<TripPlan>() {
            @Override
            public void onResponse(Call<TripPlan> call, Response<TripPlan> response) {
                Log.d(TAG, "successful trip-fetch");


                Trip trip;
                ArrayList<Leg> legs;
                Leg leg_1;
                Leg leg_2;
                Origin origin_1;
                Origin origin_2;
                Destination destination_1;
                Destination destination_2;
                Product product_1;
                Product product_2;
                int id = 0;
                boolean change;
                int line_1 = 0;
                int line_2 = 0;
                int duration_1 = 0;
                int duration_2 = 0;
                int duration_wait = 0;
                int score = 0;
                int departure;
                int arrival = 0;

                ArrayList<Trip> trip_array = response.body().getTrips();
                for (int i = 0; i < trip_array.size() ; i++) {
                    trip = trip_array.get(i);
                    legs = trip.getLeglist().getLegs();
                    leg_1 = legs.get(0);
                    origin_1 = leg_1.getOrigin();
                    product_1 = leg_1.getProduct();
                    destination_1 = leg_1.getDestination();
                    departure = timeToInt(origin_1.getTime());
                    line_1 = Integer.parseInt(product_1.getNum());
                    arrival = timeToInt(destination_1.getTime());
                    duration_1 = arrival - departure;
                    switch (legs.size()){
                        case 1:
                            duration_2 = 0;
                            duration_wait = 0;
                            change = false;
                            break;

                        case 2:
                            leg_2 = legs.get(1);
                            destination_2 = leg_2.getDestination();
                            origin_2 = leg_2.getOrigin();
                            int departure_2 =  timeToInt(origin_2.getTime());
                            duration_wait = departure_2 - arrival;
                            arrival = timeToInt(destination_2.getTime());
                            duration_2 = arrival - departure_2;
                            product_2 = leg_2.getProduct();
                            line_2 = Integer.parseInt(product_2.getNum());
                            change = true;
                        default:
                            continue;
                    }
                    temp.add(new Travel(id,change,line_1,line_2,
                            duration_1,duration_2, duration_wait,score,departure,arrival,
                            from_name, to_name));

                }
            }

            @Override
            public void onFailure(Call<TripPlan> call, Throwable t) {
                Log.e(TAG, "onFailure: Something went wrong: " + t.getMessage());
            }
        });
        return temp;
    }

    public static int timeToInt(String time){
        StringBuilder sb = new StringBuilder(time);
        return Integer.parseInt(sb.substring(0,2))*60 + Integer.parseInt(sb.substring(3,5));
    }


    public ArrayList getTravelList() {
        return travelList;
    }

    public void setTravelList(ArrayList travelList) {
        this.travelList = travelList;
    }

}

