package com.example.flightsearchapp.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.flightsearchapp.data.dao.DaoApp
import com.example.flightsearchapp.data.dao.FavoriteDao
import com.example.flightsearchapp.data.entities.AirportEntity
import com.example.flightsearchapp.data.entities.FavoritesEntity

@Database(
    entities = [AirportEntity::class, FavoritesEntity::class],
    version = 3,
    exportSchema = true
)

abstract class DataBaseApp: RoomDatabase () {
    abstract fun airportDAO(): DaoApp
    abstract fun favoriteDAO(): FavoriteDao
}