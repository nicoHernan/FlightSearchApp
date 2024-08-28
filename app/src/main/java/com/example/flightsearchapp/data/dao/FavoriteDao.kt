package com.example.flightsearchapp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.flightsearchapp.data.entities.FavoritesEntity

@Dao
interface FavoriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavorite(favoritesEntity: FavoritesEntity)

    @Query("SELECT * FROM favorite")
    fun getFavorite(): List<FavoritesEntity>
}