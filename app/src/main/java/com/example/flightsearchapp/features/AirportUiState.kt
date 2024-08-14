package com.example.flightsearchapp.features

import com.example.flightsearchapp.data.entities.AirportEntity
import com.example.flightsearchapp.data.model.Airport
import com.example.flightsearchapp.model.FlightsModel

data class AirportUiState(
    val airportListUi: ArrayList<Airport> = ArrayList(),
    val nameUi:String = "",
    val listSuggestionsAirport: List<AirportEntity> = emptyList(),
    val flightsList: ArrayList<FlightsModel> = ArrayList()
)

