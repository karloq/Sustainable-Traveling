package com.example.group7_project;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class TravelRepository {
    private TravelDao travelDao;
    private LiveData<List<Travel>> allTravels;

    public TravelRepository(Application application) {
        TravelDatabase database = TravelDatabase.getInstance(application);
        travelDao = database.travelDao();
        allTravels = travelDao.getAllTravels();
    }

    //Executes on background thread

    public void insert(Travel travel){
        new InsertTravelAsynchTask(travelDao).execute(travel);
    }

    public void update(Travel travel){
        new UpdateTravelAsynchTask(travelDao).execute(travel);
    }


    public void delete(Travel travel){
        new DeleteTravelAsynchTask(travelDao).execute(travel);
    }

    public LiveData<List<Travel>> getAllTravels() {
        return allTravels;
    }

    private static class InsertTravelAsynchTask extends AsyncTask<Travel, Void, Void> {
        private TravelDao travelDao;

        private InsertTravelAsynchTask(TravelDao travelDao) {
            this.travelDao = travelDao;
        }

        @Override
        protected Void doInBackground(Travel... travels) {
            travelDao.insert(travels[0]);
            return null;
        }
    }

    private static class UpdateTravelAsynchTask extends AsyncTask<Travel, Void, Void> {
        private TravelDao travelDao;

        private UpdateTravelAsynchTask(TravelDao travelDao) {
            this.travelDao = travelDao;
        }

        @Override
        protected Void doInBackground(Travel... travels) {
            travelDao.update(travels[0]);
            return null;
        }
    }

    private static class DeleteTravelAsynchTask extends AsyncTask<Travel, Void, Void> {
        private TravelDao travelDao;

        private DeleteTravelAsynchTask(TravelDao travelDao) {
            this.travelDao = travelDao;
        }

        @Override
        protected Void doInBackground(Travel... travels) {
            travelDao.delete(travels[0]);
            return null;
        }
    }
}
