package com.example.group7_project;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
//Interface for handling database operations
public interface TravelDao {

    @Insert
    void insert(Travel travel);

    @Update
    void update(Travel travel);

    @Delete
    void delete(Travel travel);

    @Query("SELECT * FROM travel_table ORDER BY score DESC")
    LiveData<List<Travel>> getAllProjects();
}
