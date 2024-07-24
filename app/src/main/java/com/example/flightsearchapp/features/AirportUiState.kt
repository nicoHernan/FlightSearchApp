package com.example.flightsearchapp.features

import com.example.flightsearchapp.data.model.Airport

data class AirportUiState(
    val airportListUi: ArrayList<Airport> = ArrayList(),
    val id: Int = 0,
    val iata_code: String = "",
    val name: String = "",
    val passengers: Int = 0
)

