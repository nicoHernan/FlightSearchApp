package com.example.flightsearchapp.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [AirportEntity::class],
    version = 2,
    exportSchema = true
)

abstract class DataBaseApp: RoomDatabase () {
    abstract fun airportDAO(): DaoApp
}