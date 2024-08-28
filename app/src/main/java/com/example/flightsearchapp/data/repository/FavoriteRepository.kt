package com.example.flightsearchapp.data.repository

import com.example.flightsearchapp.data.dao.FavoriteDao
import com.example.flightsearchapp.data.entities.FavoritesEntity
import javax.inject.Inject

class FavoriteRepository @Inject constructor(
   private val favoriteDao: FavoriteDao
) {

    fun insertFavorite(favoritesEntity: FavoritesEntity) {
        favoriteDao.insertFavorite(favoritesEntity)
    }

    fun getFavorites(): List<FavoritesEntity> {
       return favoriteDao.getFavorite()
    }
}