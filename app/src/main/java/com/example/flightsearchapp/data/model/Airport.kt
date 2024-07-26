package com.example.flightsearchapp.data.model

data class Airport(
    val id: Int,
    val iataCode: String,      //Código IATA de 3 letras
    val name: String,           //Nombre completo del aeropuerto
    val passengers: Int          //Cantidad de pasajeros por año
)