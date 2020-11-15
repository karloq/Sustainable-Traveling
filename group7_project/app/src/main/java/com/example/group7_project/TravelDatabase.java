package com.example.group7_project;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.ArrayList;

@Database(entities = Travel.class, version = 1)
public abstract class TravelDatabase extends RoomDatabase {

    private static  TravelDatabase instance;

    public abstract TravelDao travelDao();


    //Makes sure database is singleton
    //Returns intance of database
    public static synchronized TravelDatabase getInstance(Context context){
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    TravelDatabase.class, "project_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private TravelDao travelDao;

        private PopulateDbAsyncTask(TravelDatabase db) {
            travelDao = db.travelDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            travelDao.insert(new Travel(1,
                    6,
                    47,
                    10,
                    "10:15 - ",
                    "11:02",
                    "Korsvägen",
                    "Kungälv"
            ));
            travelDao.insert(new Travel(2,
                    6,
                    47,
                    10,
                    "11:15 - ",
                    "12:02",
                    "Korsvägen",
                    "Kungälv"
            ));
            return null;
        }
    }
}
