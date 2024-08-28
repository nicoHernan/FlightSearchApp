package com.example.flightsearchapp.data.repository

import com.example.flightsearchapp.data.dao.DaoApp
import com.example.flightsearchapp.data.entities.AirportEntity
import javax.inject.Inject

class RepositoryApp @Inject constructor(private val daoApp: DaoApp){

    //DB
     fun getAllAirportsDB(): List<AirportEntity> = daoApp
        .getAllAirports()

    fun getSuggestionsAirports(value: String): List<AirportEntity> = daoApp.getSuggestionsAirport(value)

    fun getNameAirportByIataCode(value: String) = daoApp.getNameAirportByIataCode(value)
}