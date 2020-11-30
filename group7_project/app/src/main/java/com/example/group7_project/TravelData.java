package com.example.group7_project;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TravelData {
    public ArrayList travelList;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public TravelData() {
        this.travelList = new ArrayList<Travel>();
        addTravels(1);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void addTravels(int travelid){
        final LocalTime now = LocalTime.now();
        int time = (now.getHour()*60) + now.getMinute();
        switch (travelid) {
            case 1:
                travelList.add(new Travel(1, false, 60, 0, 9, 0, 0, 1,
                        time+3, time+12,
                        "Järntorget", "Centralstationen"));
                travelList.add(new Travel(2, false, 3, 0, 14, 0, 0, 0,
                        time+2, time+16,
                        "Järntorget", "Centralstationen"));
                travelList.add(new Travel(3, true, 241, 1337, 7, 5, 0, 0,
                        time+5, time+17,
                        "Järntorget", "Centralstationen"));
                travelList.add(new Travel(4, true, 50, 1337, 7, 7, 0, 0,
                        time+10, time+24,
                        "Järntorget", "Centralstationen"));
                break;
            default:
                travelList.add(new Travel(1, false, 60, 0, 9, 0, 0, 1,
                        time+3, time+12,
                        "Järntorget", "Centralstationen"));
        }
    }

    public ArrayList getTravelList() {
        return travelList;
    }

    public void setTravelList(ArrayList travelList) {
        this.travelList = travelList;
    }

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.resrobot.se/v2/trip?key=9caf98cd-80b7-4594-a7b8-950e463047af&")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    TravelAPI travelAPI = retrofit.create(TravelAPI.class);

    Call<List<Trip>> call = travelAPI.getTrips();

}

