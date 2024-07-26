package com.example.flightsearchapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "airport")      //además está la tabla favorite para insertar con dataStore
data class AirportEntity(
    @PrimaryKey
    val id: Int,

    @ColumnInfo(name = "iata_code")                 //Código IATA de 3 letras
    val iataCode: String,
    @ColumnInfo(name = "name")          //Nombre completo del aeropuerto
    val name: String,
    @ColumnInfo(name = "passengers")     //Cantidad de pasajeros por año
    val passengers: Int
)
