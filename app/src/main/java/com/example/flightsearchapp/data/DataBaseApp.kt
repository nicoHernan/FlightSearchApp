package com.example.flightsearchapp.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [AirportEntity::class],
    version = 1
)

abstract class DataBaseApp: RoomDatabase () {
    abstract fun flightDAO(): DaoApp
}