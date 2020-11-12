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
        allTravels = travelDao.getAllProjects();
    }

    //Executes on background thread

    public void insert(Travel travel){
        new InsertProjectAsynchTask(travelDao).execute(travel);
    }

    public void update(Travel travel){
        new UpdateProjectAsynchTask(travelDao).execute(travel);
    }


    public void delete(Travel travel){
        new DeleteProjectAsynchTask(travelDao).execute(travel);
    }

    public LiveData<List<Travel>> getAllProjects() {
        return allTravels;
    }

    private static class InsertProjectAsynchTask extends AsyncTask<Travel, Void, Void> {
        private TravelDao travelDao;

        private InsertProjectAsynchTask(TravelDao travelDao) {
            this.travelDao = travelDao;
        }

        @Override
        protected Void doInBackground(Travel... travels) {
            travelDao.insert(travels[0]);
            return null;
        }
    }

    private static class UpdateProjectAsynchTask extends AsyncTask<Travel, Void, Void> {
        private TravelDao travelDao;

        private UpdateProjectAsynchTask(TravelDao travelDao) {
            this.travelDao = travelDao;
        }

        @Override
        protected Void doInBackground(Travel... travels) {
            travelDao.update(travels[0]);
            return null;
        }
    }

    private static class DeleteProjectAsynchTask extends AsyncTask<Travel, Void, Void> {
        private TravelDao travelDao;

        private DeleteProjectAsynchTask(TravelDao projectDao) {
            this.travelDao = travelDao;
        }

        @Override
        protected Void doInBackground(Travel... travels) {
            travelDao.delete(travels[0]);
            return null;
        }
    }
}
