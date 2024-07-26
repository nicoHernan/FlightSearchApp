package com.example.flightsearchapp.features

import com.example.flightsearchapp.data.model.Airport

data class AirportUiState(
    val airportListUi: ArrayList<Airport> = ArrayList(),
    val nameUi:String = ""
)

