package com.example.flightsearchapp.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.flightsearchapp.data.entities.AirportEntity

@Dao
interface DaoApp {

    @Query("SELECT * FROM airport")
    fun getAllAirports(): List<AirportEntity>

    @Query("SELECT * FROM airport WHERE name LIKE :value || '%' OR iata_code LIKE :value || '%'")
    fun getSuggestionsAirport(value: String): List<AirportEntity>
}