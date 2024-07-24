package com.example.flightsearchapp.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface DaoApp {

    @Query("SELECT * FROM airport")
    fun getAllAirports(): List<AirportEntity>
}