package com.example.flightsearchapp.data

import javax.inject.Inject

class RepositoryApp @Inject constructor(private val daoApp: DaoApp){

    //DB
     fun getAllAirportsDB(): List<AirportEntity> = daoApp
        .getAllAirports()

}