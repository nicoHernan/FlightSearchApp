package com.example.flightsearchapp.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite")
data class FavoritesEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    @ColumnInfo(name = "departure_code")
    var departureCode: String,
    @ColumnInfo(name = "destination_code")
    var destinationCode: String
)